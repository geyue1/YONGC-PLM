/**
 * ext.yongc.plm.doc.search.YJSearchDocTableView.java
 * @Author yge
 * 2017年9月21日上午9:49:28
 * Comment 
 */
package ext.yongc.plm.doc.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import wt.doc.WTDocument;
import wt.util.WTException;

import com.ptc.core.components.descriptor.DescriptorConstants.ColumnIdentifiers;
import com.ptc.core.htmlcomp.components.JCAConfigurableTable;
import com.ptc.core.htmlcomp.tableview.TableColumnDefinition;
import com.ptc.core.htmlcomp.tableview.TableViewDescriptor;

import ext.yongc.plm.constants.IBAConstants;

public class YJSearchDocTableView extends JCAConfigurableTable{
	
	protected static String[] COLUMNS = {
        ColumnIdentifiers.SHARE_STATUS_FAMILY,
        ColumnIdentifiers.CHANGE_STATUS_FAMILY,
        ColumnIdentifiers.GENERAL_STATUS_FAMILY,
        ColumnIdentifiers.ICON,
        ColumnIdentifiers.FORMAT_ICON,
        ColumnIdentifiers.NUMBER,
        ColumnIdentifiers.VERSION,
        ColumnIdentifiers.NAME,
        "IBA|"+IBAConstants.FILEID, 
        "IBA|"+IBAConstants.FILENAME, 
        "IBA|"+IBAConstants.PRODUCTMODEL, 
        "IBA|"+IBAConstants.PRODUCTNAME, 
        ColumnIdentifiers.STATE,
        ColumnIdentifiers.CREATED_BY,
        ColumnIdentifiers.CREATED,
        ColumnIdentifiers.MODIFIED_BY,
        ColumnIdentifiers.LAST_MODIFIED
      
    
};
	public Class[] getClassTypes() {
		return new Class[] { WTDocument.class };
	}

	public String getDefaultSortColumn() {
		return "IBA|com.shenzhenair.EONumber";
	}

	public String getLabel(Locale arg0) {
		return "123";
	}

	public String getOOTBActiveViewName() {
		return "Default";
	}

	public List getOOTBTableViews(String arg0, Locale arg1) throws WTException {
		ArrayList<TableViewDescriptor> result = new ArrayList<TableViewDescriptor>(1);
        String name = "yj.search.doc.table.view";
        String description = getViewResourceEntryKey("com.ptc.core.ui.tableRB", "ALL_TABLEVIEW_DESCRIPTION");
        TableViewDescriptor desc = TableViewDescriptor.newTableViewDescriptor(name, arg0, false, false,
                getTableColumns(), null, true, description);
        result.add(desc);

        return result;
	}
    protected Vector<TableColumnDefinition> getTableColumns() throws WTException
    {
        Vector<TableColumnDefinition> columns = new Vector<TableColumnDefinition>(COLUMNS.length);
        for (String column : COLUMNS)
        {
        	
            columns.add(TableColumnDefinition.newTableColumnDefinition(column, false));
        }
        return columns;
    }
	public List getSpecialTableColumnsAttrDefinition(Locale arg0) {
		return Collections.EMPTY_LIST;
	}
}
