package com.ptc.windchill.enterprise.part.views;
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

import com.ptc.core.components.descriptor.DescriptorConstants.ColumnIdentifiers;
import com.ptc.core.htmlcomp.components.JCAConfigurableTable;
import com.ptc.core.htmlcomp.tableview.SortColumnDescriptor;
import com.ptc.core.htmlcomp.tableview.TableColumnDefinition;
import com.ptc.core.htmlcomp.tableview.TableViewDescriptor;
import com.ptc.windchill.enterprise.part.partResource;
import wt.doc.WTDocument;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.util.WTRuntimeException;
import wt.util.WTMessage;

/**
* Configures the default views for the project list. 
* Temporary location.
*
* <BR><BR><B>Supported API: </B>false
* <BR><BR><B>Extendable: </B>false
*
* @version   1.0
**/

public class PartsDocumentsDescTableViews extends JCAConfigurableTable {

  private static final String RESOURCE = "com.ptc.windchill.enterprise.part.partResource";

  public Class[] getClassTypes() {
     return new Class[]{WTDocument.class};
  }

  public String getLabel(Locale locale) {
     return WTMessage.getLocalizedMessage(RESOURCE, partResource.DESCRIBED_BY_DOC_TABLE_HEADER,
                                          null, locale);
  }

  public String getOOTBActiveViewName() {
     return getViewResourceEntryKey(RESOURCE, partResource.RELATED_DOC_TABLE_VIEW_DEFAULT_NAME);
  }

  public List getOOTBTableViews(String tableId, Locale locale) throws WTException {

     ArrayList result = new ArrayList();
     Vector columns = new Vector();         

     try {
        columns.add(TableColumnDefinition.newTableColumnDefinition(ColumnIdentifiers.ICON, /*lockable*/false));
        columns.add(TableColumnDefinition.newTableColumnDefinition(ColumnIdentifiers.NUMBER, /*lockable*/false));
        columns.add(TableColumnDefinition.newTableColumnDefinition(ColumnIdentifiers.VERSION, /*lockable*/false));
        columns.add(TableColumnDefinition.newTableColumnDefinition(ColumnIdentifiers.INFO_ACTION, /*lockable*/false));
        columns.add(TableColumnDefinition.newTableColumnDefinition(ColumnIdentifiers.NAME, /*lockable*/false));
        columns.add(TableColumnDefinition.newTableColumnDefinition(ColumnIdentifiers.CONTAINER_NAME, /*lockable*/false));
        columns.add(TableColumnDefinition.newTableColumnDefinition(ColumnIdentifiers.STATE, /*lockable*/false));
        columns.add(TableColumnDefinition.newTableColumnDefinition(ColumnIdentifiers.LAST_MODIFIED, /*lockable*/false));      

        String name = getViewResourceEntryKey(RESOURCE, partResource.RELATED_DOC_TABLE_VIEW_DEFAULT_NAME);
        String description = getViewResourceEntryKey(RESOURCE, partResource.RELATED_DOC_TABLE_VIEW_DEFAULT_DESCIP);

        ArrayList sortColumns = new ArrayList();
        SortColumnDescriptor sortColumn = new SortColumnDescriptor();
        sortColumn.setColumnId(ColumnIdentifiers.VERSION);  // Secondary Sort
        sortColumn.setOrder(SortColumnDescriptor.ASCENDING);
        sortColumns.add(sortColumn);
     
        sortColumn = new SortColumnDescriptor();
        sortColumn.setColumnId(ColumnIdentifiers.NUMBER);  // Primary Sort
        sortColumn.setOrder(SortColumnDescriptor.ASCENDING);
        sortColumns.add(sortColumn);
     
        TableViewDescriptor desc = TableViewDescriptor.newTableViewDescriptor(name, tableId, true, true, columns, null, true, description);
        desc.setColumnSortOrder(sortColumns);
        result.add(desc);
     
     } catch (WTPropertyVetoException vpe) {
        throw new WTRuntimeException(vpe);
     }
     
     return result;
  }   
  
  public List getSpecialTableColumnsAttrDefinition(Locale locale) {

     List result =  new ArrayList();
     return result;      
  }

  @Override
  public boolean isColumnLocked(String columnId) {
     return false;
  }

  public String getDefaultSortColumn() {
     return ColumnIdentifiers.NUMBER;
  }
  
  /*
   * Validates if a given attribute should be available in the "set columns"
   * Step of the create table view wizard
   */
  @Override
  public boolean isAttributeValidForColumnStep(String key) {
      return true;
  }
  
  @Override
  public boolean isDefaultSortingAscending() {
          return true;
  }

  @Override
  public boolean showChooseItemTypesStep() {
     return true;
  }

  @Override
  public boolean showFilteringStep() {
     return true;
  }

}