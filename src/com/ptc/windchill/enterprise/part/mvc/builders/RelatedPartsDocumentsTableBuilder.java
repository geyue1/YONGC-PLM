package com.ptc.windchill.enterprise.part.mvc.builders;

import java.util.Stack;

import javax.servlet.ServletRequest;

import wt.part.WTPart;
import wt.part.WTProductInstance2;
import wt.util.WTException;

import com.ptc.core.components.descriptor.DescriptorConstants;
import com.ptc.core.components.descriptor.DescriptorConstants.ColumnIdentifiers;
import com.ptc.core.htmlcomp.components.AbstractConfigurableTableBuilder;
import com.ptc.core.htmlcomp.tableview.ConfigurableTable;
import com.ptc.core.ui.componentRB;
import com.ptc.core.ui.resources.ComponentMode;
import com.ptc.jca.mvc.components.JcaColumnConfig;
import com.ptc.jca.mvc.components.JcaComponentParams;
import com.ptc.jca.mvc.components.JcaTableConfig;
import com.ptc.mvc.components.ColumnConfig;
import com.ptc.mvc.components.ComponentBuilder;
import com.ptc.mvc.components.ComponentConfig;
import com.ptc.mvc.components.ComponentConfigFactory;
import com.ptc.mvc.components.ComponentParams;
import com.ptc.mvc.util.ClientMessageSource;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.netmarkets.util.beans.NmHelperBean;
import com.ptc.netmarkets.util.misc.NmAction;
import com.ptc.netmarkets.util.misc.NmContextItem;
import com.ptc.windchill.enterprise.part.partResource;
import com.ptc.windchill.enterprise.part.commands.PartDocServiceCommand;
import com.ptc.windchill.enterprise.part.views.PartsDocumentsRefTableViews;

@ComponentBuilder("relatedParts.DescribedByDocuments")
public class RelatedPartsDocumentsTableBuilder extends AbstractConfigurableTableBuilder{
	
	private static final String PARTREF_RESOURCE="com.ptc.windchill.enterprise.part.partResource";
	private static final String COMPONENT_RB_RESOURCE = "com.ptc.core.ui.componentRB";
	private static String INST_DESCRIBE_DOCS = "part.relatedPartInstancesDescribedByDocuments.list";
	private static String DESCRIBE_DOCS = "part.relatedPartsDescribedByDocuments.list";
	
	private ClientMessageSource msgSource = getMessageSource(PARTREF_RESOURCE);
	private ClientMessageSource msgSourceComponentRB = getMessageSource(COMPONENT_RB_RESOURCE);
	
	@Override
	public ConfigurableTable buildConfigurableTable(String tableId)
			throws WTException {
		//both are using the same table view.... no need of if-else....
		if (tableId.equalsIgnoreCase(INST_DESCRIBE_DOCS) || 
				(tableId.equalsIgnoreCase(DESCRIBE_DOCS))) {
			return new PartsDocumentsRefTableViews();
		} 
		return null;
	}

	@Override
	public Object buildComponentData(ComponentConfig config, ComponentParams params)
			throws Exception {
		
		NmHelperBean helper = ((JcaComponentParams) params).getHelperBean();
		ServletRequest request = helper.getRequest();
		request.setAttribute(NmAction.SHOW_CONTEXT_INFO, "false");
		
		NmCommandBean commandBean = helper.getNmCommandBean();
		Object o = commandBean.getPrimaryOid().getRefObject();
		
		if(o instanceof WTPart){
			return PartDocServiceCommand.getAssociatedDescribeDocuments((WTPart)o);
		}else if(o instanceof WTProductInstance2){
			return PartDocServiceCommand.getAssociatedDescribeDocuments((WTProductInstance2)o);
		}else {
			//sld not reach here....
			return null;
		}
	}

	@Override
	public ComponentConfig buildComponentConfig(ComponentParams params)
			throws WTException {
		
		String tableIdRef = "";
				ComponentConfigFactory factory = getComponentConfigFactory();
				NmHelperBean helper = ((JcaComponentParams) params).getHelperBean();
				NmCommandBean commandBean = helper.getNmCommandBean();
				JcaTableConfig table=(JcaTableConfig)factory.newTableConfig();
				
				boolean isPartInstance = false;
				
				Stack stk = commandBean.getContextBean().getContext().getContextItems();
				int stkSize = stk.size();
				for (int i=0;i<stkSize;i++) {
					NmContextItem ci = (NmContextItem)stk.get(i);
					if (ci.getAction().equalsIgnoreCase("relatedPartInstanceDocuments")) {
						isPartInstance = true;
					}
				}
				
				if (isPartInstance) {
					tableIdRef  = INST_DESCRIBE_DOCS;
				} else {
					tableIdRef  = DESCRIBE_DOCS;
				}
				
				table.setId(tableIdRef);
				table.setComponentMode(ComponentMode.VIEW);
				table.setLabel(msgSource.getMessage(partResource.DESCRIBED_BY_DOC_TABLE_HEADER));
				table.setConfigurable(true);
				table.setSelectable(true);
				table.setActionModel("relatedDocumentDescribesToolBar");
								
				ColumnConfig colTypeIcon = factory.newColumnConfig(ColumnIdentifiers.ICON,true);
				table.addComponent(colTypeIcon);
				
				ColumnConfig colNumber = factory.newColumnConfig(ColumnIdentifiers.NUMBER,true);
				table.addComponent(colNumber);
				
				JcaColumnConfig versionCol = (JcaColumnConfig)factory.newColumnConfig(ColumnIdentifiers.VERSION,true);
				table.addComponent(versionCol);
				
				ColumnConfig colInfoPageAction = factory.newColumnConfig("infoPageAction", false);
				colInfoPageAction.setDataUtilityId("infoPageAction");
				table.addComponent(colInfoPageAction);
				
				ColumnConfig colName = factory.newColumnConfig("name",true);
				colName.setLabel(msgSourceComponentRB.getMessage(componentRB.NAME));
				table.addComponent(colName);
				
				ColumnConfig colContainerName = factory.newColumnConfig("containerName",true);
				colContainerName.setLabel(msgSourceComponentRB.getMessage(componentRB.CONTAINER_NAME));
				table.addComponent(colContainerName);
				
				ColumnConfig colOrgId = factory.newColumnConfig(ColumnIdentifiers.ORG_ID,true);
				table.addComponent(colOrgId);
				
				ColumnConfig colState = factory.newColumnConfig(ColumnIdentifiers.STATE,true);
				colState.setLabel(msgSourceComponentRB.getMessage(componentRB.STATE));
				table.addComponent(colState);
				
				ColumnConfig lastModified = factory.newColumnConfig(ColumnIdentifiers.LAST_MODIFIED, true);
			    table.addComponent(lastModified);
			 // Empty action model so as not to show any RMC actions from the Related tab.
			    ColumnConfig colActions = factory.newColumnConfig(DescriptorConstants.ColumnIdentifiers.NM_ACTIONS, false);
				((JcaColumnConfig)colActions).setDescriptorProperty(DescriptorConstants.ActionProperties.ACTION_MODEL,"EMPTY_ACTION_MODEL"); 
				table.addComponent(colActions);
			    table.setShowCount(true);
				table.setHelpContext("doc_described_by_ref");
				
				return table;
	}

}
