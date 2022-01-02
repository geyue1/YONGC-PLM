/**
 * ext.yongc.plm.change.mvc.YJResultingDataTable.java
 * @Author yge
 * 2017年8月15日下午1:41:56
 * Comment 
 */
package ext.yongc.plm.change.mvc;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import wt.change2.Changeable2;
import wt.change2.WTChangeActivity2;
import wt.change2.WTChangeOrder2;
import wt.epm.EPMDocument;
import wt.fc.Persistable;
import wt.fc.WTObject;
import wt.iba.value.IBAHolder;
import wt.maturity.PromotionNotice;
import wt.session.SessionServerHelper;
import wt.util.WTException;
import wt.workflow.definer.WfDefinerHelper;
import wt.workflow.engine.WfActivity;
import wt.workflow.engine.WfProcess;
import wt.workflow.work.WorkItem;

import com.ptc.core.htmlcomp.components.ConfigurableTableBuilder;
import com.ptc.core.htmlcomp.tableview.ConfigurableTable;
import com.ptc.core.meta.common.impl.TypeIdentifierUtilityHelper;
import com.ptc.core.ui.resources.ComponentMode;
import com.ptc.jca.mvc.components.AbstractJcaComponentConfig;
import com.ptc.jca.mvc.components.JcaColumnConfig;
import com.ptc.jca.mvc.components.JcaTableConfig;
import com.ptc.mvc.components.AbstractComponentBuilder;
import com.ptc.mvc.components.ColumnConfig;
import com.ptc.mvc.components.ComponentBuilder;
import com.ptc.mvc.components.ComponentConfig;
import com.ptc.mvc.components.ComponentConfigFactory;
import com.ptc.mvc.components.ComponentParams;
import com.ptc.mvc.components.TableConfig;
import com.ptc.netmarkets.object.mvc.helper.ItemIdVersion;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.netmarkets.util.beans.NmHelperBean;
import com.ptc.windchill.enterprise.change2.beans.ChangeWizardBean;
import com.ptc.windchill.enterprise.change2.commands.ChangeItemQueryCommands;
import com.ptc.windchill.enterprise.change2.mvc.builders.tables.AbstractChangeTableBuilder;
import com.ptc.windchill.enterprise.change2.mvc.builders.tables.ResultingItemsTableBuilder;
import com.ptc.windchill.enterprise.change2.tableViews.WorkflowTaskResultingItemsTableViews;
import com.ptc.jca.mvc.components.JcaComponentParams;

import ext.yongc.plm.constants.IBAConstants;
import ext.yongc.plm.constants.TypeConstants;
import ext.yongc.plm.constants.WorkflowConstants;
import ext.yongc.plm.util.ChangeUtil;
import ext.yongc.plm.util.IBAUtil;
import ext.yongc.plm.util.StringUtil;
import ext.yongc.plm.util.WorkflowUtil;

@ComponentBuilder({"yjchange.YJResultingDataTable"})
public class YJResultingDataTable extends AbstractComponentBuilder {

	private boolean containWA(String wa,String gongyi){
		if(StringUtil.isEmpty(gongyi)){
			return false;
		}else if(gongyi.contains(",")){
			String[] array = gongyi.split(",");
			for(String s : array){
				if(StringUtil.isNotEmpty(s) && s.trim().equals(wa)){
					return true;
				}
			}
		}else if(!gongyi.contains(",")){
			return gongyi.trim().equals(wa.trim());
		}
		
		return false;
	}

	@Override
	public Object buildComponentData(ComponentConfig arg0, ComponentParams arg1)
			throws Exception {
		boolean forced = SessionServerHelper.manager.setAccessEnforced(false);
		try{
			 NmHelperBean helperBean = ((JcaComponentParams) arg1).getHelperBean();
			   NmCommandBean commandBean = helperBean.getNmCommandBean();
			   Object obj = commandBean.getActionOid().getRefObject();
			   if(obj instanceof WorkItem){
		        	WorkItem item = (WorkItem)obj;
		        	WfActivity wa = (WfActivity) item.getSource().getObject();
		        	WfProcess process = WorkflowUtil.getProcessByObject(obj);
						Persistable p = (Persistable) process.getContext().getValue(WfDefinerHelper.PRIMARY_BUSINESS_OBJECT);
						if(p instanceof WTChangeOrder2){
							WTChangeOrder2 ecn = (WTChangeOrder2)p;
							List<WTObject> resultList = ChangeUtil.getChangeAfter(ecn);
							List<WTObject> gongyiList = new ArrayList<WTObject>();
							String type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(ecn).getTypename();
							if(type.indexOf(TypeConstants.ECN_3DDesign)>-1|| type.indexOf(TypeConstants.ECN_MBOM)>-1){
								String waName = wa.getName();
								for(int i=0;i<resultList.size();i++){
									WTObject object = resultList.get(i);
									IBAUtil iba2 = new IBAUtil((IBAHolder)object);
									String gongyi = iba2.getIBAValue(IBAConstants.GONGYI_TYPE);
									if (!StringUtil.isEmpty(gongyi)&& !StringUtil.isEmpty(waName)&&containWA(waName,gongyi)){
										gongyiList.add(object);
									}

								}
							}
							if(gongyiList.size()>0){
								return gongyiList;
							}else{
								return resultList;
							}
						}
		        }
		}catch(Exception e){
			
		}finally{
			SessionServerHelper.manager.setAccessEnforced(forced);
		}
		
		return new ArrayList();
	}

	/* (non-Javadoc)
	 * @see com.ptc.mvc.components.ComponentConfigBuilder#buildComponentConfig(com.ptc.mvc.components.ComponentParams)
	 */
	@Override
	public ComponentConfig buildComponentConfig(ComponentParams paramComponentParams)
			throws WTException {
		    NmHelperBean helper = ((JcaComponentParams) paramComponentParams).getHelperBean();
	        NmCommandBean commandBean = helper.getNmCommandBean();
	        Object obj = commandBean.getActionOid().getRefObject();
	        boolean isGongyi = false;
	        boolean isMBOM = false;
	        boolean is3D = false;
	        WTChangeOrder2 ecn=null;
	        if(obj instanceof WorkItem){
	         	WorkItem item = (WorkItem)obj;
	        	WfActivity wa = (WfActivity) item.getSource().getObject();
	        	WfProcess process = WorkflowUtil.getProcessByObject(item);
				Persistable p = (Persistable) process.getContext().getValue(WfDefinerHelper.PRIMARY_BUSINESS_OBJECT);
				if(p instanceof WTChangeOrder2){
					ecn = (WTChangeOrder2)p;
					try {
						String type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(ecn).getTypename();
						if(type.indexOf(TypeConstants.ECN_3DDesign)>-1 &&
								WorkflowConstants.ACTIVITY_gongyifenpei.equalsIgnoreCase(wa.getName())){
							isGongyi = true;
						}else if(type.indexOf(TypeConstants.ECN_MBOM)>-1){
							isMBOM = true;
						}
						if(type.indexOf(TypeConstants.ECN_3DDesign)>-1){
							is3D = true;
						}
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
	        	
	        }
		  ComponentConfigFactory configFactory = getComponentConfigFactory();
		    JcaTableConfig tableConfig = (JcaTableConfig)configFactory.newTableConfig();
		    JcaTableConfig tableConfig2 = (JcaTableConfig)configFactory.newTableConfig();
		 
		    ChangeWizardBean localChangeWizardBean = ChangeWizardBean.getChangeWizardBean(paramComponentParams);
		  

		    String str1 =""; //getChangeableTableId(commandBean);
		    paramComponentParams.setAttribute("changeTableId", str1);

		    if (!"changeTask_resultingItems_table".equals(str1)) {
		      paramComponentParams.setAttribute("chgWizTableId", "changeTask_resultingItems_table");
		    }
		   
		    tableConfig.setId("changeTask_resultingItems_table");
		    tableConfig2.setId("changeTask_resultingItems_table");
		    tableConfig2.setLabel("变更对象");

		    tableConfig.setSelectable(true);
		    tableConfig2.setSelectable(true);
		    tableConfig.setType(Changeable2.class.getName());
//		    String str2 = getActionsPrefix(str1);
//		    String str3 = str2 + ".table.view";
//		    String str4 = getRowActionsPrefix(str1);
		    String str5 = "change_resultingItems";
		    int i = 0;
		    if ((ComponentMode.CREATE.toString().equals(localChangeWizardBean.getChangeMode())) || (ComponentMode.EDIT.toString().equals(localChangeWizardBean.getChangeMode())))
		    {
//		      str3 = str2 + ".table.create_edit";
//		      str4 = str2 + ".row.actions";
		      str5 = "change_resultingItems_edit";
		      i = 1;
		    }

//		    tableConfig.setActionModel(str3);
		    if(isGongyi){
	        	 tableConfig.setActionModel("gongyi shenhe");
	        	 tableConfig2.setActionModel("gongyi shenhe");
	        }else if(isMBOM){
//	        	 tableConfig.setLabel("MBOM");
//	        	  tableConfig.setType("wt.part.WTPart");
	        }
		   	        
		    tableConfig2.addComponent(configFactory.newColumnConfig("statusFamily_General", true));

		    tableConfig2.addComponent(configFactory.newColumnConfig("statusFamily_Share", true));

		    tableConfig2.addComponent(configFactory.newColumnConfig("statusFamily_Change", true));

		    tableConfig2.addComponents(ItemIdVersion.getColumnConfigs(configFactory));

		    tableConfig2.addComponent(configFactory.newColumnConfig("launchIncorpHangingChange", true));

		    if (localChangeWizardBean.getChangeMode().equals(ComponentMode.VIEW.toString())) {
		    	ColumnConfig localObject = configFactory.newColumnConfig("compare", true);
		      ((ColumnConfig)localObject).setComponentMode(ComponentMode.VIEW);
		      tableConfig2.addComponent((ComponentConfig)localObject);
		    }

		    tableConfig2.setInitialRows(true);

		    Object localObject = (JcaColumnConfig)configFactory.newColumnConfig("change_tableData", false);
		    ((JcaColumnConfig)localObject).setDescriptorProperty("supportHangingChanges", Boolean.valueOf(true));
		    ((JcaColumnConfig)localObject).setDataStoreOnly(true);
//		    tableConfig.addComponent((ComponentConfig)localObject);

//		    tableConfig.addComponent(configFactory.newColumnConfig("infoPageAction", true));

//		    localObject = (JcaColumnConfig)configFactory.newColumnConfig("nmActions", true);
		    ((JcaColumnConfig)localObject).setComponentMode(ComponentMode.valueOf(localChangeWizardBean.getChangeMode()));

//		    if (doesActionModelExist(str4)) {
//		      ((JcaColumnConfig)localObject).setDescriptorProperty("actionModel", str4);
//		    }
		    ((JcaColumnConfig)localObject).setDataUtilityId("resultingTableRowActions");
//		    tableConfig.addComponent((ComponentConfig)localObject);
		    tableConfig2.addComponent((ComponentConfig)localObject);

		    localObject = (JcaColumnConfig)configFactory.newColumnConfig("name", true);
//		    tableConfig.addComponent((ComponentConfig)localObject);
		    tableConfig2.addComponent((ComponentConfig)localObject);

		    tableConfig2.addComponent(configFactory.newColumnConfig("infoPageAction", true));
		    ColumnConfig col1 =  configFactory.newColumnConfig(IBAConstants.GONGYI_TYPE, true);
	         col1.setAutoSize(true);
	         col1.setVariableHeight(true);
	        col1.setDataStoreOnly(false);
	        if(is3D){
	        	  col1.setDataUtilityId("PromotionTaskObjectsDataUtility");
	        	col1.setLabel(WorkflowConstants.ACTIVITY_gongyifenpei);
	        }else if(isMBOM){
	        	col1.setLabel("工艺路线");
	        }
	         
	       
	          tableConfig2.addComponent(col1);
	          if(is3D || isMBOM){
	        	  tableConfig2.addComponent(col1);
	          }

		    
		    if(is3D || isMBOM){
		    	 tableConfig2.addComponent(configFactory.newColumnConfig(IBAConstants.CNAME, true));
		    	 tableConfig2.addComponent(configFactory.newColumnConfig("DRAWINGNO", true));
		    	 tableConfig2.addComponent(configFactory.newColumnConfig(IBAConstants.MATERIAL, true));
		    }else{
		    	tableConfig2.addComponent(configFactory.newColumnConfig(IBAConstants.FILENAME, true));
		    	tableConfig2.addComponent(configFactory.newColumnConfig(IBAConstants.FILEID, true));
		    	tableConfig2.addComponent(configFactory.newColumnConfig(IBAConstants.PRODUCTMODEL, true));
		    	tableConfig2.addComponent(configFactory.newColumnConfig(IBAConstants.PRODUCTNAME, true));
		    }
		    
		    
		    localObject = (JcaColumnConfig)configFactory.newColumnConfig("state", true);
//		    tableConfig.addComponent((ComponentConfig)localObject);
		    tableConfig2.addComponent((ComponentConfig)localObject);
		    tableConfig2.addComponent(configFactory.newColumnConfig("creator", true));
//		    tableConfig.addComponent(configFactory.newColumnConfig("thePersistInfo.modifyStamp", true));
		    tableConfig2.addComponent(configFactory.newColumnConfig("thePersistInfo.modifyStamp", true));

//		    tableConfig.addComponent(configFactory.newColumnConfig("modifier.name", true));

		    localObject = (JcaColumnConfig)configFactory.newColumnConfig("crDescription", true);
		    ((JcaColumnConfig)localObject).setComponentMode(ComponentMode.valueOf(localChangeWizardBean.getChangeMode()));
		    ((JcaColumnConfig)localObject).setVariableHeight(true);
		    tableConfig2.addComponent((ComponentConfig)localObject);

//		    addDataColumns(tableConfig, configFactory);

//		    if (i != 0) {
//		      ResultingItemsPickerConfig localResultingItemsPickerConfig = new ResultingItemsPickerConfig();
//		      tableConfig.setToolbarAutoSuggestPickerConfig(localResultingItemsPickerConfig);
//		    }

//		    tableConfig.setView("/changetask/resultingItemsTable.jsp");
		    tableConfig.setHelpContext(str5);
		    tableConfig.setInitialRows(true);
		  
		    return tableConfig2;
	}


	public ConfigurableTable buildConfigurableTable(String arg0)
			throws WTException {
		return null;
	}

}
