/* bcwti
 *
 * Copyright (c) 2010 Parametric Technology Corporation (PTC). All Rights Reserved.
 *
 * This software is the confidential and proprietary information of PTC
 * and is subject to the terms of a software license agreement. You shall
 * not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement.
 *
 * ecwti
 */
package com.ptc.core.htmlcomp.components;


import com.ptc.core.components.descriptor.ComponentDescriptor;
import com.ptc.core.components.descriptor.ComponentViewModel;
import com.ptc.core.components.descriptor.DescriptorConstants;
import com.ptc.core.components.descriptor.DescriptorConstants.ColumnIdentifiers;
import com.ptc.core.components.descriptor.DescriptorConstants.ColumnProperties;
import com.ptc.core.components.descriptor.DescriptorConstants.DescriptorProperties;
import com.ptc.core.htmlcomp.tableview.ConfigurableTableHelper;
import com.ptc.core.htmlcomp.tableview.TableColumnDefinition;
import com.ptc.core.htmlcomp.tableview.TableViewDescriptor;
import com.ptc.core.htmlcomp.tableview.TableViewDescriptorHelper;
import com.ptc.core.htmlcomp.util.SLProvider;
import com.ptc.core.meta.descriptor.common.impl.AttributeDefinitionDescriptor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Set;

import org.apache.log4j.Logger;

import wt.fc.ObjectIdentifier;
import wt.fc.PersistenceHelper;
import wt.fc.ReferenceFactory;
import wt.log4j.LogR;
import wt.session.SessionHelper;
import wt.util.WTException;

/**
 * Encapsulates the logic to look up configurable views based
 * on a component descriptor, and to update the component
 * descriptor with view information if available
 *
 * <BR><BR><B>Supported API: </B>false
 * <BR><BR><B>Extendable: </B>false
 *
 * @version   1.0
 **/
public class TableViewBean {

    protected static final String CACHED_VIEW_MODEL = "JCA.cachedTableViews";
    private static final Logger log;
    private int added_cols_debug = 0;

    static {
        log = LogR.getLogger(TableViewBean.class.getName());
    }
    private ComponentDescriptor descriptor;
    private Locale locale;
    private boolean newView;
    private boolean localizeViewNames;
    private TableViewDescriptor savedSearchView;
    private String tableID;

    public ComponentDescriptor getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(ComponentDescriptor cd) {
        descriptor = cd;
        String table_id = descriptor.getConfigurableTableId();
        if (table_id == null) {
            tableID = descriptor.getId();
        } else {
            tableID = table_id;
        }
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale loc) {
        locale = loc;
    }

    public boolean isLocalizeViewNames() {
        return localizeViewNames;
    }

    public void setLocalizeViewNames(boolean localizeViewNames) {
        this.localizeViewNames = localizeViewNames;
    }

    protected String getTableID() {
        return tableID;
    }

    protected TableViewDescriptor getSavedSearchDescriptor() {
        return savedSearchView;
    }

    public ComponentViewModel getModel() throws WTException {
        if (descriptor == null) {
            throw new IllegalStateException("Descriptor was null for bean: " + this);
        }

        if (!isTableConfigurable()) {
            log.error("Could not find configurable table with id: " + tableID);
            return null;
        }

        ConfigurableTableViewModel result = (ConfigurableTableViewModel) descriptor.getProperty(CACHED_VIEW_MODEL);
        if (result != null) {
            try{
                //We are caching the ConfigurableTableViewModel on the descriptor, so that it can be reused across various
                //chunks while doing DU processing for a table/tree. The ConfigurableTableViewModel internally hold the descriptor
                //populated from the view and there are high chances that it become dirty in a DU processing cycle.
                //We need to get a clean copy to be supplied for each DU processing cycle and hence doing clone here
                return result.clone();
            }catch(Exception xe){
            }
        }

        result = new ConfigurableTableViewModel();
        try {
            result.setConfigurableTable(ConfigurableTableHelper.getConfigurableTable(tableID));
            result.setComponentDescriptor(descriptor);

            init();

            result.setActiveView(getActiveView());
            if (newView) {
                result.setActiveViewChanged(true);
            }
            processActiveView(result);
            processAllViews(result);
            descriptor.setProperty(CACHED_VIEW_MODEL, result.clone());
        } catch (Exception e) {
            log.error("Error initializing configurable table view model.", e);
        }
        return result;
    }
    /**
     * Easy access for testing...
     * @return is this table configurable
     */
    protected boolean isTableConfigurable() {
        return ConfigurableTableHelper.isTableConfigurable(tableID);
    }

    protected void init() throws WTException {
        if (descriptor.isSingleViewOnly()) {
            // ensure view is loaded as a user view
            TableViewDescriptorHelper.getTableUserOnlyView(tableID, SessionHelper.getLocale());
        }
        String saved_search_id = descriptor.getProperty(DescriptorConstants.TableProperties.SAVED_SEARCH_VIEW_ID, "");
        if (!"".equals(saved_search_id)) {
            savedSearchView = TableViewDescriptorHelper.getViewById(saved_search_id, /*bypass access control*/ true);
            if (savedSearchView == null) {
                throw new WTException("Could not find saved search view for id: " + saved_search_id);
            }
            if (log.isDebugEnabled()) {
            	log.debug("Found saved search view for id: " + saved_search_id);
            }
        }
    }

    protected TableViewDescriptor getActiveView() throws WTException {
        TableViewDescriptor currentView = TableViewDescriptorHelper.getCurrentActiveView(tableID, locale);
        if (isInitialSavedSearchView()) {
            if (log.isDebugEnabled()) {
                log.debug("Ignoring current active view, using saved search view instead!");
            }
            currentView = savedSearchView;
        }
        if (descriptor.getProperty(DescriptorConstants.TableProperties.MVC_VIEW_ID) != null) {
            newView = true;
                if (log.isDebugEnabled()) {
                    log.debug("MVC view id found, won't update it again");
                }
        } else {
            //This property gets set when the user selects a new view
            String new_view = descriptor.getProperty(DescriptorConstants.TableProperties.VIEW_ID, (String) null);
            if (new_view != null) {
                ReferenceFactory rf = new ReferenceFactory();
                ObjectIdentifier oid = (ObjectIdentifier) rf.getReference(new_view).getKey();
                if (!oid.equals(PersistenceHelper.getObjectIdentifier(currentView))) {
                    newView = true;
                    if (savedSearchView != null && oid.equals(PersistenceHelper.getObjectIdentifier(savedSearchView))) {
                        if (log.isDebugEnabled()) {
                            log.debug("Switched to saved search view, not persisting active view. Table: " + tableID);
                        }
                        currentView = savedSearchView;
                    } else {
                        TableViewDescriptorHelper.setNewActiveView(new_view);
                        currentView = TableViewDescriptorHelper.getTableView(new_view, SessionHelper.getLocale());
                    }
                    if (log.isDebugEnabled()) {
                        log.debug("Changed active view to: " + currentView.getName());
                    }
                }
            }
            if (descriptor != null && currentView != null && currentView.getGroupBy()!=null) {
                descriptor.setProperty(DescriptorConstants.TableProperties.GROUP_BY, currentView.getGroupBy());
            }
        }
        return currentView;
    }

    protected boolean isInitialSavedSearchView() throws WTException {
        return descriptor.getProperty(DescriptorConstants.TableProperties.INITIAL_SAVED_SEARCH, false);
    }

    protected void processActiveView(ConfigurableTableViewModel model) throws WTException {

        TableViewDescriptor active = model.getActiveView();
        TableDescriptorWrapper tdWrapper = new TableDescriptorWrapper(model);
        List<ComponentDescriptor> buffer = tdWrapper.getColumnComponentDescriptors();

        // We now need to go back over the original ComponentDescriptor looking for columns marked as Datastore-only.
        // These columns need to be added into the new ComponentDescriptor if they are not already present.
        outterLoop: for (ComponentDescriptor oldCD : descriptor.getDescriptors()) {

            // we don't care about columns which are not flagged as DATA_STORE_ONLY.
            if (! oldCD.getProperty(ColumnProperties.DATA_STORE_ONLY, false)) {
                continue;
            }

            String id = oldCD.getId();
            // check if this column already exists in the new list.
            for (ComponentDescriptor newCD : buffer) {
                if (newCD.getId().equals(id)) {
                    continue outterLoop;
                }
            }
            buffer.add(oldCD);
        }

        if (log.isDebugEnabled()) {
            int removed_cols = ((descriptor.getDescriptors().size() + added_cols_debug) - buffer.size());
            if (removed_cols > 0) {
                log.debug(removed_cols + " columns were removed. View: " + active.getName());
            }
            if (added_cols_debug > 0) {
                log.debug(added_cols_debug + " columns were added. View: " + active.getName());
            }
        }
        descriptor.setDescriptors(buffer);
    }

    /**
     * Given the table column definition, return its component descriptor (creating it if necessary).
     * @param model
     * @param tcd
     * @return
     * @throws WTException
     */
    protected ComponentDescriptor getCompDescriptor(ConfigurableTableViewModel model, TableColumnDefinition tcd) throws WTException {
        String id = model.getJcaId(tcd);

        // is the descriptor already defined in the JSP?
        ComponentDescriptor cd = descriptor.getDescriptor(id);
        if (cd == null) {
            // if no descriptor was defined in the JSP, create one
            cd = model.createNewDescriptor(tcd, id, descriptor);
            added_cols_debug++;
        }
        cd.setProperty(DescriptorConstants.ColumnProperties.WIDTH, Integer.valueOf(tcd.getWidth()));
        if(tcd.getWidth()>0){
            cd.setProperty(DescriptorConstants.ColumnProperty.DO_NOT_CALCULATE, true);
        }
        cd.setProperty(DescriptorConstants.ColumnProperties.HIDDEN, tcd.isHidden());

        // All actions columns should be dataStoreOnly.
        if (DescriptorConstants.ColumnIdentifiers.NM_ACTIONS.equals(tcd.getName())) {
            cd.setProperty(DescriptorConstants.ColumnProperties.DATA_STORE_ONLY, true);
        }

        initializeLabel(cd, model, tcd);
        return cd;
    }

    protected ComponentDescriptor getSLCompDescriptor(ConfigurableTableViewModel model, TableColumnDefinition tcd)
            throws WTException {

        return getSLCompDescriptor(model, tcd, model.getJcaId(tcd));
    }

    protected ComponentDescriptor getSLCompDescriptor(ConfigurableTableViewModel model, TableColumnDefinition tcd, String id)
            throws WTException {

        // is the descriptor already defined in the JSP?
        ComponentDescriptor cd = descriptor.getDescriptor(id);
        if (cd == null) {
            // if no descriptor was defined in the JSP, create one
            cd = model.createNewDescriptor(tcd, id, descriptor);
            added_cols_debug++;
        }
        cd.setProperty(DescriptorConstants.ColumnProperties.WIDTH, Integer.valueOf(tcd.getWidth()));
        if (tcd.getWidth() > 0) {
            cd.setProperty(DescriptorConstants.ColumnProperty.DO_NOT_CALCULATE, true);
        }
        cd.setProperty(DescriptorConstants.ColumnProperties.HIDDEN, tcd.isHidden());

        // All actions columns should be dataStoreOnly.
        if (DescriptorConstants.ColumnIdentifiers.NM_ACTIONS.equals(tcd.getName())) {
            cd.setProperty(DescriptorConstants.ColumnProperties.DATA_STORE_ONLY, true);
        }
        // initializeLabel(cd, model, tcd);
        if (cd.getLabel() == null) {
            AttributeDefinitionDescriptor ati = SLProvider.getSLDefinitionDescriptor(id);
            String label = ati.getDisplay();
            if (label != null) {
                cd.setProperty(DescriptorConstants.ColumnProperties.TABLE_VIEW_LABEL, label);
            }
        }
        // set the Security label data utility to ensure non-security labeled objects display correct information and
		// disabled security labels display correct information
        cd.setProperty(DescriptorProperties.DATA_UTILITY_ID,"SECURITYLABEL_DU");
        return cd;
    }

    protected void initializeLabel(ComponentDescriptor cd, ConfigurableTableViewModel model, TableColumnDefinition tcd)
            throws WTException {
        if (cd.getLabel() == null) {
            // if no label is defined on the column descriptor, attempt to look it up using the table view
            // information (configurable table, available attributes factory)
            String label = model.getLabel(tcd, descriptor, cd);
            if (label != null) {
                cd.setProperty(DescriptorConstants.ColumnProperties.TABLE_VIEW_LABEL, label);
            }
        }
    }

    protected void processAllViews(ConfigurableTableViewModel model) throws WTException {
        ComponentDescriptor cd = model.getComponentDescriptor();

        List all;
        if (cd.isSingleViewOnly()) {
            all = TableViewDescriptorHelper.getTableUserOnlyView(tableID, SessionHelper.getLocale());
        } else {
            all = TableViewDescriptorHelper.getTableViews(tableID, locale);
        }

        List<String> names = new ArrayList<String>(all.size());
        List<String> oids = new ArrayList<String>(all.size());
        ReferenceFactory rf = new ReferenceFactory();

        model.setAllViewNames(names);
        model.setAllViewIDs(oids);

        if (all != null) {
            for (Iterator i = all.iterator(); i.hasNext();) {
                TableViewDescriptor next = (TableViewDescriptor) i.next();
                names.add(getName(next));
                oids.add(rf.getReferenceString(next));
            }
        }

        if (savedSearchView != null) {
            String savedSearchOid = rf.getReferenceString(savedSearchView);
            if (oids.contains(savedSearchOid)) {
                if (log.isDebugEnabled()) {
                    log.debug("View list already contains saved search id, not adding it again. Table: " + cd.getId());
                }
            } else {
                names.add(getName(savedSearchView));
                oids.add(savedSearchOid);
            }
        }
    }

    private String getName(TableViewDescriptor tvd) {
        String result;
        if (localizeViewNames || (tvd.getNameKey() == null)) {
            result = tvd.getName();
        } else {
            result = tvd.getNameKey();
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TableViewBean)) {
            return false;
        }
        TableViewBean other = (TableViewBean) o;
        return eq(descriptor, other.descriptor) && eq(locale, other.locale);
    }

    static boolean eq(Object o1, Object o2) {
        return (o1 == null ? (o2 == null) : (o1.equals(o2)));
    }

    @Override
    public int hashCode() {
        int result = 17;
        if (descriptor != null) {
            result = 37 * result + descriptor.hashCode();
        }
        if (locale != null) {
            result = 37 * result + locale.hashCode();
        }
        return result;
    }

    @Override
    public String toString() {
        return "TableViewBean [descriptor=\"" + descriptor + "\" locale=\"" + locale + "\"]";
    }

    /**
     * @deprecated Replaced by {@link TableViewUtils#getCurrentView(String)
     *
     * Returns the Name of the current selected View for the table id
     *  This name could be the resouce bundle entry that would deliver the localized name
     *
     * @param id The id of the table component
     * @return A String name, or "" if the table does not have current view
     * @throws WTException exception getting current view
     */
    @Deprecated
    public static String getCurrentView(String id) throws WTException {
        return TableViewUtils.getCurrentView(id);
    }

    //helper class that will expand the security labels group column to add individual columns
    class TableDescriptorWrapper {

        TableViewDescriptor activeView;

        ConfigurableTableViewModel model;

        TableColumnDefinition securityGrp_tcd;

        List<String> colIdList;

        List<ComponentDescriptor> colDescriptorList;

        TableDescriptorWrapper(ConfigurableTableViewModel model) throws WTException {
            this.model = model;
            activeView = model.getActiveView();

            int colSize = activeView.getTableColumnDefinition().size();
            colIdList = new ArrayList<String>(colSize);
            colDescriptorList = new ArrayList<ComponentDescriptor>(colSize);

            boolean needsActionCol = true;
            // update the descriptor with the components configured by the view
            for (ListIterator i = activeView.getTableColumnDefinition().listIterator(); i.hasNext();) {
                TableColumnDefinition tcd = (TableColumnDefinition) i.next();
                if (log.isDebugEnabled()) {
                     log.debug("persisted column : " + tcd.getName());
                }
                if (ColumnIdentifiers.NM_ACTIONS.equals(tcd.getName())) {
                    needsActionCol = false;
                }
                addColumn(tcd);
            }
            // when NmAction column is not populated from viewmodel,we add it explicitly
            if (needsActionCol) {
                ComponentDescriptor cd = model.getDescriptor(ColumnIdentifiers.NM_ACTIONS, descriptor);
                // mark this DATA_STORE_ONLY column
                cd.setProperty(ColumnProperties.DATA_STORE_ONLY, true);
                colIdList.add(ColumnIdentifiers.NM_ACTIONS);
                colDescriptorList.add(cd);
                added_cols_debug++;
                if (log.isDebugEnabled()) {
                    // log.debug("In process active view adding nmaction column explicitly: " + columnDescriptorList);
                }
            }
        }

        void addColumn(TableColumnDefinition tcd) throws WTException {

            String colName = tcd.getName();
            if ((SLProvider.ALL_SECURITY_LABELS.equals(colName)) && !SLProvider.isEnabled()) {
            	//do not add the column -- this is in reference to a security labels that have been disabled
            	return;
            }
            if (SLProvider.isEnabled() && SLProvider.isSLAttribute(colName)) {
                if (com.ptc.core.htmlcomp.util.SLProvider.ALL_SECURITY_LABELS.equals(colName)) {
                    colIdList.add(com.ptc.core.htmlcomp.util.SLProvider.ALL_SECURITY_LABELS);
                    getDescriptor().setProperty(DescriptorConstants.TableProperties.ALL_SECURITY_LABEL_COLUMNS_VISIBLE,
                            true);
                    securityGrp_tcd = tcd;
                } else {
                    String colID = model.getJcaId(tcd);
                    if (!colIdList.contains(colID)) {
                        ComponentDescriptor cd = getSLCompDescriptor(model, tcd);
                        colIdList.add(cd.getId());
                        colDescriptorList.add(cd);
                    }
                }
            } else {
                String colID = model.getJcaId(tcd);
                if (!colIdList.contains(colID)) {
                    ComponentDescriptor cd = getCompDescriptor(model, tcd);
                    colIdList.add(cd.getId());
                    colDescriptorList.add(cd);
                }
            }
        }

        List<ComponentDescriptor> getColumnComponentDescriptors() throws WTException {
            if (colIdList.contains(com.ptc.core.htmlcomp.util.SLProvider.ALL_SECURITY_LABELS)) {
                int index = colIdList.indexOf(com.ptc.core.htmlcomp.util.SLProvider.ALL_SECURITY_LABELS);
                // need to insert all the security columns here
                Set<String> slKeys = SLProvider.getSLAttributes(true);
                List<String> slKeysList = new ArrayList<String>();
                slKeysList.addAll(slKeys);
                // reverse the order so to be consistent with CADx tables
                Collections.reverse(slKeysList);

                for (String sKey : slKeysList) {
                    if (!colIdList.contains(sKey) /* not already added */) {
                        ComponentDescriptor cd = getSLCompDescriptor(model, securityGrp_tcd, sKey);
                        colDescriptorList.add(index, cd);
                    }
                }
            }

            return colDescriptorList;
        }
    }
}
