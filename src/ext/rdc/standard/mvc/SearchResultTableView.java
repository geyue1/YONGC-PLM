/**
 * ext.rdc.standard.mvc.SearchResultTableView.java
 * @Author yge
 * 2019年9月19日上午10:29:16
 * Comment 
 */
package ext.rdc.standard.mvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import wt.part.WTPart;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.util.WTRuntimeException;
import com.ptc.core.htmlcomp.createtableview.Attribute;

import com.ptc.core.components.descriptor.DescriptorConstants.ColumnIdentifiers;
import com.ptc.core.htmlcomp.components.JCAConfigurableTable;
import com.ptc.core.htmlcomp.tableview.SortColumnDescriptor;
import com.ptc.core.htmlcomp.tableview.TableColumnDefinition;
import com.ptc.core.htmlcomp.tableview.TableViewDescriptor;

public class SearchResultTableView extends JCAConfigurableTable{

	/* (non-Javadoc)
	 * @see com.ptc.core.htmlcomp.tableview.ConfigurableTable#getClassTypes()
	 */
	@Override
	public Class[] getClassTypes() {
		return new Class[]{WTPart.class};
	}

	/* (non-Javadoc)
	 * @see com.ptc.core.htmlcomp.tableview.ConfigurableTable#getDefaultSortColumn()
	 */
	@Override
	public String getDefaultSortColumn() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ptc.core.htmlcomp.tableview.ConfigurableTable#getLabel(java.util.Locale)
	 */
	@Override
	public String getLabel(Locale arg0) {
		return "标准件搜索结果";
	}

	/* (non-Javadoc)
	 * @see com.ptc.core.htmlcomp.tableview.ConfigurableTable#getOOTBActiveViewName()
	 */
	@Override
	public String getOOTBActiveViewName() {
		return "测试用视图";
	}

	/* (non-Javadoc)
	 * @see com.ptc.core.htmlcomp.tableview.ConfigurableTable#getOOTBTableViews(java.lang.String, java.util.Locale)
	 */
	@Override
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
	          //columns.add(TableColumnDefinition.newTableColumnDefinition(ColumnIdentifiers.LAST_MODIFIED, /*lockable*/false));      

	          String name = "标准件搜索视图1";
	          String description = "标准件搜索视图1";

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
//	          result.add(desc);
	       
	       } catch (WTPropertyVetoException vpe) {
	          throw new WTRuntimeException(vpe);
	       }
	       
	       return result;
	}

	/* (non-Javadoc)
	 * @see com.ptc.core.htmlcomp.tableview.ConfigurableTable#getSpecialTableColumnsAttrDefinition(java.util.Locale)
	 */
	@Override
	public List getSpecialTableColumnsAttrDefinition(Locale locale) {
		 List result =  new ArrayList();
		 result.add(new Attribute.TextAttribute("surface","表面处理", locale));
		 result.add(new Attribute.TextAttribute("supplier","供货厂家" , locale));
		 result.add(new Attribute.TextAttribute("recommendedUsage","推荐使用情况" , locale));
	     return result;      
	}

}
