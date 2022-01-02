/**
 * ext.rdc.standard.mvc.ClassificationTreeBuilder.java
 * @Author yge
 * 2019年8月30日上午9:42:22
 * Comment 
 */
package ext.rdc.standard.mvc;

import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTMessage;

import com.ptc.core.components.descriptor.DescriptorConstants;
import com.ptc.jca.mvc.components.JcaComponentConfig;
import com.ptc.mvc.components.ColumnConfig;
import com.ptc.mvc.components.ComponentBuilder;
import com.ptc.mvc.components.ComponentConfig;
import com.ptc.mvc.components.ComponentParams;
import com.ptc.mvc.components.TableConfig;
import com.ptc.mvc.components.TreeConfig;
import com.ptc.windchill.csm.client.csmClientResource;
import com.ptc.windchill.csm.client.mvc.builders.AbstractTreeBuilder;
import com.ptc.windchill.csm.common.CsmConstants;

@ComponentBuilder("ext.rdc.standard.mvc.ClassificationTreeBuilder")
public class ClassificationTreeBuilder extends AbstractTreeBuilder{
	private static final String CLASSIFICATION_TREE_HELP_KEY = "WCClassReuseClassTree";
	 private static final String COLUMN_ID = "name";
	@Override
	public ComponentConfig buildComponentConfig(ComponentParams arg0)
			throws WTException {

		  TableConfig table = getComponentConfigFactory().newTreeConfig();
	        table.setId("rdc.CLF.tree");
	        ((TreeConfig) table).setNodeColumn(COLUMN_ID);
//	        ((TreeConfig) table).setLabel(WTMessage.getLocalizedMessage(CsmConstants.CSM_CLIENT_RESOURCE,
//	                csmClientResource.CLASSIFICATION_TREE_TITLE, null, SessionHelper.getLocale()));
	        ((TreeConfig) table).setLabel("123");
	        table.setSingleSelect(false);
	        ((JcaComponentConfig) table).setDescriptorProperty(DescriptorConstants.TableProperties.SELECTABLE, "true");
	        ((JcaComponentConfig) table).setDescriptorProperty(DescriptorConstants.TableTreeProperties.EXPANSION,
	                DescriptorConstants.TableTreeProperties.ONE_EXPAND);

	        ColumnConfig col = getComponentConfigFactory().newColumnConfig(
	                COLUMN_ID,
	                WTMessage.getLocalizedMessage(CsmConstants.CSM_CLIENT_RESOURCE,
	                        csmClientResource.CLASSIFICATION_TREE_COLUMN_NAME, null, SessionHelper.getLocale()), true);
	        col.setDataUtilityId(getDataUtilityId(COLUMN_ID));
	        col.setWidth(415);
	        table.addComponent(col);


//		ColumnConfig actionColumn = getComponentConfigFactory().newColumnConfig(
//				DescriptorConstants.ColumnIdentifiers.NM_ACTIONS, false);
//		actionColumn.setActionModel("csm_clfnodetree_row_toolbar");
//		table.addComponent(actionColumn);
//		table.setActionModel("csm_clfnodetree_actions_toolbar");
		table.setHelpContext(CLASSIFICATION_TREE_HELP_KEY); // Added as part of Story : B-108624 /Help links: Map the help URL provided by Pubs in the actual UI

		return table;
	}


	@Override
	public String getDataUtilityId(String arg0) {
		return "ClfTreeNodeDataUtility";
	}
}
