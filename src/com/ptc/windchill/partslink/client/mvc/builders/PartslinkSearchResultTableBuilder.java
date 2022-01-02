package com.ptc.windchill.partslink.client.mvc.builders;

import static wt.index.IndexConstants.IDENTIFIER_FACTORY;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import wt.access.AccessControlHelper;
import wt.access.AccessPermission;
import wt.fc.Persistable;
import wt.fc.ReferenceFactory;
import wt.fc.WTReference;
import wt.fc.collections.CollectionsHelper;
import wt.fc.collections.WTArrayList;
import wt.fc.collections.WTKeyedHashMap;
import wt.fc.collections.WTKeyedMap;
import wt.log4j.LogR;
import wt.session.SessionHelper;
import wt.util.HTMLEncoder;
import wt.util.InstalledProperties;
import wt.util.WTContext;
import wt.util.WTException;
import wt.util.WTMessage;
import wt.util.WTPropertyVetoException;

import com.infoengine.object.factory.Att;
import com.infoengine.object.factory.Element;
import com.ptc.core.command.common.bean.entity.PrepareEntityCommand;
import com.ptc.core.components.descriptor.DescriptorConstants;
import com.ptc.core.lwc.common.view.AttributeDefinitionReadView;
import com.ptc.core.lwc.common.view.PropertyHolderHelper;
import com.ptc.core.lwc.common.view.TypeDefinitionReadView;
import com.ptc.core.meta.common.AttributeTypeIdentifier;
import com.ptc.core.meta.common.DisplayOperationIdentifier;
import com.ptc.core.meta.common.IllegalFormatException;
import com.ptc.core.meta.common.TypeIdentifier;
import com.ptc.core.meta.common.TypeInstanceIdentifier;
import com.ptc.core.meta.container.common.AttributeContainerSpec;
import com.ptc.core.meta.server.TypeIdentifierUtility;
import com.ptc.core.meta.type.common.TypeInstance;
import com.ptc.jca.mvc.components.JcaColumnConfig;
import com.ptc.jca.mvc.components.JcaComponentParams;
import com.ptc.mvc.components.AbstractComponentBuilder;
import com.ptc.mvc.components.ColumnConfig;
import com.ptc.mvc.components.ComponentConfig;
import com.ptc.mvc.components.ComponentConfigFactory;
import com.ptc.mvc.components.ComponentParams;
import com.ptc.mvc.components.TableConfig;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.netmarkets.util.beans.NmHelperBean;
import com.ptc.windchill.csm.client.helpers.CSMTypeDefHelper;
import com.ptc.windchill.partslink.PartslinkConstants;
import com.ptc.windchill.partslink.partslinkClientResource;
import com.ptc.windchill.partslink.model.PartslinkPropertyModel;
import com.ptc.windchill.partslink.model.ResultModel;
import com.ptc.windchill.partslink.service.ResultModelServiceImpl;

/**
 * The Class ClassificationSearchResultTableBuilder.
 * 
 * @deprecated replaced by PartslinkResultsConfigBuilder and PartslinkResultsDataBuilder.
 */
@Deprecated
public class PartslinkSearchResultTableBuilder extends AbstractComponentBuilder {

	private static final Logger logger = LogR.getLogger(PartslinkSearchResultTableBuilder.class.getName());

	ThreadLocal<List<String>> attributeList = new ThreadLocal<List<String>>();

	private static final String ACCESS_RESOURCE = "wt.access.accessResource";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ptc.mvc.components.ComponentDataBuilder#buildComponentData(com.ptc.mvc.components.ComponentConfig,
	 * com.ptc.mvc.components.ComponentParams)
	 */
	@Override
	public List<Element> buildComponentData(ComponentConfig config, ComponentParams params) throws WTException {

		long t1 = System.currentTimeMillis();

		List results = new ArrayList<>();
		//		List<Element> aclObjects = new ArrayList<Element>();

		// Factory for reference creation.
		final ReferenceFactory REF_FACTORY = new ReferenceFactory();
		NmHelperBean helper = ((JcaComponentParams) params).getHelperBean();
		NmCommandBean nmCommandBean = helper.getNmCommandBean();
		HttpServletRequest request = nmCommandBean.getRequest();

		long buildCompDataTime = 0;
		if (logger.isDebugEnabled()) {
			buildCompDataTime = System.currentTimeMillis();
		}

		try
		{
			List<Element> searchResults = (List<Element>) request.getAttribute("searchresult");

			ResultModel resultModel = (ResultModel) request.getSession().getAttribute(PartslinkConstants.Model_IDS.RESULT_MODEL);

			if (logger.isDebugEnabled()) {
				logger.debug("resultModel:: " + resultModel);
			}

			if (resultModel != null) {
				searchResults = new ResultModelServiceImpl().getResults(resultModel);
			}
			else {
				logger.error("resultModel retrieved from request is null.");
			}			

			WTArrayList versionRefs = new WTArrayList(100, CollectionsHelper.VERSION_FOREIGN_KEY);

			if (searchResults != null) {
				for (Element eleResult : searchResults) {
					String partKeys = (String) eleResult.getValue("obid");
					try {
						WTReference wtRef = REF_FACTORY.getReference(partKeys);
						//versionRefs.clear();
						versionRefs.add(wtRef);
					}
					catch (Exception e) {
						// TODO: handle exception
					}                    
				}


				WTArrayList[] accessList = getAccess(versionRefs);

				if(accessList[1]!=null) {
					results = getTypeInstances(accessList[1]);
				}

				if(accessList[0]!=null){
					addSecuredElements(results, accessList[0]);
				}

			}
			else {
				logger.debug("searchResults is empty.");
			}
		} catch (WTException wexp) {
			logger.error("Exception occurred while building classification search result table data");
			throw wexp;
		}

		//		if (results != null && aclObjects != null && aclObjects.size() > 0) {
		//			results.addAll(aclObjects);
		//		}

		if (logger.isDebugEnabled()) {
			logger.debug("Classification result table component data built in time "
					+ (System.currentTimeMillis() - buildCompDataTime));
		}

		long t2 = System.currentTimeMillis();
		logger.debug("Total time = "+(t2-t1));

		return results;
	}

	private void addSecuredElements(List results, WTArrayList wtArrayList) {
		// TODO Auto-generated method stub

	}

	private List getTypeInstances(WTArrayList wtArrayList) throws WTException {

		List<Object> result = new ArrayList<Object>();
		Map<TypeIdentifier, List<TypeInstanceIdentifier>> typeIdWiseMap = getTypeIdWiseMap(wtArrayList);

		for(Map.Entry<TypeIdentifier, List<TypeInstanceIdentifier>> entry: typeIdWiseMap.entrySet()){
			TypeIdentifier typeId = entry.getKey();
			List<TypeInstanceIdentifier> tiiList = entry.getValue();
			TypeInstanceIdentifier[] tiiArr = new TypeInstanceIdentifier[tiiList.size()];
			tiiArr = tiiList.toArray(tiiArr);
			TypeInstance[] tiArr = getTypeInstances(tiiArr, getFilter(typeId));
			for(TypeInstance ti:tiArr) {
				result.add(ti);
			}
		}
		return result;
	}

	private Map<TypeIdentifier, List<TypeInstanceIdentifier>> getTypeIdWiseMap(
			WTArrayList wtArrayList) throws WTException {

		Map<TypeIdentifier, List<TypeInstanceIdentifier>> result = new HashMap<TypeIdentifier, List<TypeInstanceIdentifier>>();

		wtArrayList.inflate();

		Iterator persistables = wtArrayList.persistableIterator();
		while(persistables.hasNext()) {
			Persistable persistable = (Persistable)persistables.next();

			TypeIdentifier type_id = TypeIdentifierUtility.getTypeIdentifier(persistable);
			TypeInstanceIdentifier tii = TypeIdentifierUtility.getTypeInstanceIdentifier(persistable);

			List<TypeInstanceIdentifier> tiiList = result.get(type_id);
			if(tiiList==null) {
				tiiList = new ArrayList<TypeInstanceIdentifier>();
				result.put(type_id, tiiList);
			}
			tiiList.add(tii);
		}
		return result;
	}

	private WTArrayList[] getAccess(WTArrayList versionRefs) throws WTException {


		WTArrayList noAccessObjects = new WTArrayList(100);
		WTArrayList accessibleObjects = new WTArrayList(100);

		/*
        WTArrayList list = new WTArrayList(1, CollectionsHelper.VERSION_FOREIGN_KEY);
        boolean hasAccess = false;
        for(Object obj:versionRefs) {
        	WTReference wtRef = (WTReference)obj;
            list.clear();
            list.add(wtRef);
            hasAccess = AccessControlHelper.manager.hasAccess(versionRefs, AccessPermission.READ);
			if(hasAccess){
				accessibleObjects.add(wtRef);
			}else {
				noAccessObjects.add(wtRef);
			}
        }
		 */

		WTKeyedHashMap accessMap = AccessControlHelper.manager.getAccess(versionRefs, AccessPermission.READ);
		Set<WTKeyedMap.WTEntry> entrySet = accessMap.entrySet();
		for(WTKeyedMap.WTEntry e:entrySet) {
			if((Boolean)e.getValue()){
				accessibleObjects.add(e.getKey());
			}else {
				noAccessObjects.add(e.getKey());
			}
		}        

		WTArrayList[] result = new WTArrayList[2];
		if(noAccessObjects.size()>0) {
			result[0] = noAccessObjects;
		}
		if(accessibleObjects.size()>0) {
			result[1] = accessibleObjects;
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ptc.mvc.components.ComponentConfigBuilder#buildComponentConfig(com.ptc.mvc.components.ComponentParams)
	 */
	@Override
	public ComponentConfig buildComponentConfig(ComponentParams params) throws WTException {

		long buildCompConfigTime = 0;
		if (logger.isDebugEnabled()) {
			buildCompConfigTime = System.currentTimeMillis();
		}        


		NmHelperBean helper = ((JcaComponentParams) params).getHelperBean();
		NmCommandBean nmCommandBean = helper.getNmCommandBean();
		HttpServletRequest request = nmCommandBean.getRequest();

		String clfNode = getParameterValue(request, PartslinkConstants.RequestParameters.PARAM_CLASS_INTERNAL_NAME);
		String objectType = getParameterValue(request, PartslinkConstants.RequestParameters.PARAM_OBJECT_TYPE);

		initAttributeList(objectType);

		if (clfNode == null || clfNode.isEmpty()) {
			throw new IllegalArgumentException("Classification node is required.");
		}

		if (objectType == null || objectType.isEmpty()) {
			throw new IllegalArgumentException("Object type is required.");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Got Classification node as " + clfNode);
		}

		TypeDefinitionReadView clfNodeReadView = CSMTypeDefHelper.getClassificationTypeDefView(clfNode);

		if (clfNodeReadView == null) {
			throw new WTException("Could not featch classification node for " + clfNode);
		}

		Collection<AttributeDefinitionReadView> typeAttributes = clfNodeReadView.getAllAttributes();
		ComponentConfigFactory factory = getComponentConfigFactory();
		TableConfig table = factory.newTableConfig();
		table.setType(objectType);
		String title = WTMessage.getLocalizedMessage(PartslinkConstants.RESOURCE,
				partslinkClientResource.RESULT_TABLE_TITLE, null, SessionHelper.getLocale());
		table.setLabel(title + " : " + PropertyHolderHelper.getDisplayName(clfNodeReadView, SessionHelper.getLocale()));
		table.setId("partslink.resultsTable");
		table.setActionModel("partsLinkTableActions");
		table.setHelpContext("WCClassReuseResultsView");

		List<ColumnConfig> defaultColumnConfigs = getDefaultColumnConfigs(objectType, factory);
		if (defaultColumnConfigs != null && !defaultColumnConfigs.isEmpty()) {
			table.addComponents(defaultColumnConfigs);
		}

		ColumnConfig rightClickColumnConfig = getComponentConfigFactory().newColumnConfig(
				DescriptorConstants.ColumnIdentifiers.NM_ACTIONS, false);
		table.addComponent(rightClickColumnConfig);

		for (AttributeDefinitionReadView attributeDefReadView : typeAttributes) {
			if (!attributeDefReadView.isSystemHidden()) {

				String attributeId = attributeDefReadView.getIBARefView().getLogicalIdentifier();
				if (attributeId == null || attributeId.isEmpty()) {
					AttributeTypeIdentifier ati = attributeDefReadView.getAttributeTypeIdentifier();
					ati = ati.getWithNewContext(TypeIdentifierUtility.getTypeIdentifier(objectType));
					attributeId = ati.toExternalForm();
				}

				addToAttributeList(attributeId);

				ColumnConfig clfAttribColumnConfig = factory.newColumnConfig(attributeId, HTMLEncoder
						.encodeForHTMLContent(PropertyHolderHelper.getDisplayName(attributeDefReadView,
								SessionHelper.getLocale())), true);
				table.addComponent(clfAttribColumnConfig);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Classification result table component config built in time "
					+ (System.currentTimeMillis() - buildCompConfigTime));
		}

		return table;
	}

	private void addToAttributeList(String attribute) {
		List<String> attributes = attributeList.get();
		attributes.add(attribute);
	}

	private void initAttributeList(String objectType) {
		List<String> attributes = new ArrayList<String>();
		attributeList.set(attributes);

		List<String> defaultColumns = PartslinkPropertyModel.getInstance().getDefaultColumns(objectType);        
		attributes.addAll(defaultColumns);

	}

	/**
	 * Gets the specified parameter value from request.
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param paramName
	 *            the parameter name
	 * @return parameter value
	 */
	private String getParameterValue(HttpServletRequest request, String paramName) {
		String paramValue = "";
		Object reqAttribute = request.getAttribute(paramName);

		if (reqAttribute instanceof String) {
			paramValue = (String) reqAttribute;
		}

		if (paramValue == null || "".equals(paramValue.trim())) {
			paramValue = request.getParameter(paramName);
		}

		return paramValue;
	}

	/**
	 * Gets the default column configurations.
	 * 
	 * @param objectType
	 *            the object type
	 * @param factory
	 *            the factory
	 * @return the default column configurations
	 */
	protected List<ColumnConfig> getDefaultColumnConfigs(String objectType, ComponentConfigFactory factory)
			throws WTException {
		List<String> defaultColumns = PartslinkPropertyModel.getInstance().getDefaultColumns(objectType);
		List<ColumnConfig> defaultColumnConfigs = new ArrayList<ColumnConfig>();

		if (defaultColumns != null && !defaultColumns.isEmpty()) {

			for (String col : defaultColumns) {

				if (DescriptorConstants.ColumnIdentifiers.ICON.equalsIgnoreCase(col)) {
					ColumnConfig columnConfig = factory.newColumnConfig(col, false);
					((JcaColumnConfig) columnConfig).setDescriptorProperty(
							DescriptorConstants.ColumnProperties.DISPLAY_LABEL, "false");
					defaultColumnConfigs.add(columnConfig);
				}
				else if (DescriptorConstants.ColumnIdentifiers.GENERAL_STATUS_FAMILY.equalsIgnoreCase(col)) {
					ColumnConfig columnConfig = factory.newColumnConfig(col, false);
					((JcaColumnConfig) columnConfig).setDescriptorProperty(
							DescriptorConstants.ColumnProperties.DISPLAY_LABEL, "false");
					defaultColumnConfigs.add(columnConfig);
				}
				else if (PartslinkConstants.ResultTable.SMALL_THUMBNAIL.equalsIgnoreCase(col)) {
					ColumnConfig columnConfig = factory.newColumnConfig(col, false);
					((JcaColumnConfig) columnConfig).setDescriptorProperty(
							DescriptorConstants.ColumnProperties.DISPLAY_LABEL, "false");
					columnConfig.setLabel(WTMessage.getLocalizedMessage(PartslinkConstants.RESOURCE,
							partslinkClientResource.RESULTTABLE_THUMBNAIL_COL, null, SessionHelper.getLocale()));
					defaultColumnConfigs.add(columnConfig);
				}
				else if (DescriptorConstants.ColumnIdentifiers.INFO_ACTION.equalsIgnoreCase(col)) {
					ColumnConfig columnConfig = factory.newColumnConfig(col, false);
					((JcaColumnConfig) columnConfig).setDescriptorProperty(
							DescriptorConstants.ColumnProperties.DISPLAY_LABEL, "false");
					defaultColumnConfigs.add(columnConfig);
				}
				else if (PartslinkConstants.ResultTable.FINDSIMILAR_ICON.equalsIgnoreCase(col)) {
					ColumnConfig columnConfig = factory.newColumnConfig(col, false);
					((JcaColumnConfig) columnConfig).setDescriptorProperty(
							DescriptorConstants.ColumnProperties.DISPLAY_LABEL, "false");
					columnConfig.setLabel(WTMessage.getLocalizedMessage(PartslinkConstants.RESOURCE,
							partslinkClientResource.RESULTTABLE_FINDSIMILAR_COL, null, SessionHelper.getLocale()));
					defaultColumnConfigs.add(columnConfig);
				}
				else if (DescriptorConstants.ColumnIdentifiers.VERSION.equalsIgnoreCase(col)) {
					ColumnConfig columnConfig = factory.newColumnConfig(col, true);

					defaultColumnConfigs.add(columnConfig);
				}
				else if (PartslinkConstants.ResultTable.SOURCING_STATUS.equalsIgnoreCase(col)) {
					if (InstalledProperties.isInstalled(InstalledProperties.SUMA)) {
						ColumnConfig columnConfig = factory.newColumnConfig(col, true);
						columnConfig.setLabel(WTMessage.getLocalizedMessage(PartslinkConstants.RESOURCE,
								partslinkClientResource.RESULTTABLE_SOURCING_STATUS_COL, null,
								SessionHelper.getLocale()));
						defaultColumnConfigs.add(columnConfig);
					}
				}
				else if (PartslinkConstants.ResultTable.REVISION.equalsIgnoreCase(col)) {
					ColumnConfig columnConfig = factory.newColumnConfig(col, true);
					columnConfig.setLabel(WTMessage.getLocalizedMessage(PartslinkConstants.RESOURCE,
							partslinkClientResource.RESULTTABLE_REVISION_COL, null, SessionHelper.getLocale()));
					defaultColumnConfigs.add(columnConfig);
				}
				else if (PartslinkConstants.ResultTable.VIEW.equalsIgnoreCase(col)) {
					ColumnConfig columnConfig = factory.newColumnConfig(col, true);
					columnConfig.setLabel(WTMessage.getLocalizedMessage(PartslinkConstants.RESOURCE,
							partslinkClientResource.RESULTTABLE_VIEW_COL, null, SessionHelper.getLocale()));
					defaultColumnConfigs.add(columnConfig);
				}
				else {
					ColumnConfig columnConfig = factory.newColumnConfig(col, true);

					defaultColumnConfigs.add(columnConfig);
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Default column list is " + defaultColumnConfigs);
		}

		return defaultColumnConfigs;
	}

	/**
	 * Returns AttributeContainerSpec for the specified TypeIdentifier.
	 * 
	 * @return AttributeContainerSpec - Attribute Container Specification
	 * @throws IllegalFormatException
	 *             If there were problems with command format.
	 * @throws WTException
	 *             If there were problems while getting Attribute Container Specification.
	 */
	private AttributeContainerSpec getFilter(TypeIdentifier id) throws IllegalFormatException, WTException {
		AttributeContainerSpec filter = null;

		if (filter == null) {
			filter = new AttributeContainerSpec();
			filter.setIncludeArgumentsDefault(false);
			filter.setIncludeConstraintsDefault(false);
			filter.setIncludeDescriptorsDefault(false);
			filter.setNextOperation(new DisplayOperationIdentifier());

			List<String> attributes = attributeList.get();
			for(String attribute:attributes) {
				AttributeTypeIdentifier ati = null;
				try{
					ati = (AttributeTypeIdentifier) IDENTIFIER_FACTORY.get(attribute, id);
				} catch(Exception e) {}
				if(ati!=null) {
					filter.putEntry(ati);
				}
			}

			/*
            IndexSearchUtils.putIndexedAttributes(filter, id);

            // add some additional attributes here
            try {
                AttributeTypeIdentifier softAttributes = (AttributeTypeIdentifier) IDENTIFIER_FACTORY.get(
                        "ALL_SOFT_ATTRIBUTES", id);
                AttributeTypeIdentifier viewName = (AttributeTypeIdentifier) IDENTIFIER_FACTORY.get("view", id);
                filter.putEntry(softAttributes);
                filter.putEntry(viewName);
            } catch (LogicalAttributeFormatException lafe) {
                // ignore this error and continue, most indexed objects are typed so if this isn't defined, then skip
                logger.debug("Logical attribute format exception", lafe);
            }

			 */
		}

		if (logger.isDebugEnabled()) {
			logger.debug("attContainerSpec filter for type : " + id.getTypename() + " is " + filter);
		}

		return filter;
	}

	/**
	 * Returns the type instance for the specified TypeInstanceIdentifier and AttributeContainerSpec.
	 * 
	 * @return TypeInstance - type instance
	 * @throws IllegalFormatException
	 *             If there were problems with command format.
	 * @throws WTException
	 *             If there were problems while getting type instance.
	 */
	private TypeInstance[] getTypeInstances(TypeInstanceIdentifier[] tiIds, AttributeContainerSpec filter)
			throws IllegalFormatException, WTException {
		// get a type instance
		PrepareEntityCommand p_command = new PrepareEntityCommand();

		long typeInstanceTime = 0;
		if (logger.isDebugEnabled()) {
			typeInstanceTime = System.currentTimeMillis();
		}

		try {
			p_command.setSources(tiIds);
			p_command.setFilter(filter);
			p_command.setLocale(WTContext.getContext().getLocale());
		} catch (WTPropertyVetoException e) {
			throw new WTException(e);
		}

		p_command = (PrepareEntityCommand) p_command.execute();
		TypeInstance[] ti = p_command.getResults();

		if (logger.isDebugEnabled()) {
			logger.debug("TypeInstance retrieved in time " + (System.currentTimeMillis() - typeInstanceTime));
		}

		return ti;
	}

	/**
	 * Returns Info Engine element with secured information as the value for all the columns specified.
	 * 
	 * @return Element - IE Element populated with (Secured Information) as the value
	 * @throws WTException
	 *             If there were problems creating IE Element with (Secured Information).
	 */
	private Element getSecuredElement(ComponentConfig config, WTReference wtRef, Element eleResult) throws WTException {

		long aclEleCreationTime = 0;
		if (logger.isDebugEnabled()) {
			aclEleCreationTime = System.currentTimeMillis();
		}
		Element eleSecured = new Element();

		try {
			String strSecuredInformation;
			try {
				strSecuredInformation = (new WTMessage(ACCESS_RESOURCE, wt.access.accessResource.SECURED_INFORMATION, null))
						.getLocalizedMessage(SessionHelper.getLocale());
			} catch (Exception exception) {
				logger.error(exception, exception);
				strSecuredInformation = "(Secured information)";
			}

			if (config != null) {
				eleSecured.addAtt(new Att("objectid", eleResult.getValue("obid")));

				for (ComponentConfig compConfig : config.getComponents()) {
					if (compConfig instanceof JcaColumnConfig) {
						String colName = compConfig.getId() != null ? compConfig.getId() : "";

						if (PartslinkPropertyModel.getInstance().getDisplayNameNumber()
								&& DescriptorConstants.ColumnIdentifiers.NAME.equalsIgnoreCase(colName)) {
							eleSecured.addAtt(eleResult.getAtt(DescriptorConstants.ColumnIdentifiers.NAME));
						}
						else if (PartslinkPropertyModel.getInstance().getDisplayNameNumber()
								&& DescriptorConstants.ColumnIdentifiers.NUMBER.equalsIgnoreCase(colName)) {
							eleSecured.addAtt(eleResult.getAtt(DescriptorConstants.ColumnIdentifiers.NUMBER));
						}
						else if (DescriptorConstants.ColumnIdentifiers.ICON.equalsIgnoreCase(colName)) {
							eleSecured.addAtt(new Att(DescriptorConstants.ColumnIdentifiers.ICON, ""));
							eleSecured.addAtt(new Att("objectIcon", "netmarkets/images/warning_16x16.gif"));
							eleSecured.addAtt(new Att("objectTooltip", strSecuredInformation));
						}
						else {
							eleSecured.addAtt(new Att(colName, strSecuredInformation));
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception occurred while populating ie element with secured information.", e);
			throw (new WTException("Exception occurred while populating ie element with secured information."));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ACL secure information element creation time "
					+ (System.currentTimeMillis() - aclEleCreationTime));
		}

		return eleSecured;
	}

}
