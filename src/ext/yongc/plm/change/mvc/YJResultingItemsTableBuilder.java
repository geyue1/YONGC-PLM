/**
 * ext.yongc.plm.change.mvc.YJResultingItemsTableBuilder.java
 * @Author yge
 * 2017年8月14日下午2:10:12
 * Comment 
 */
package ext.yongc.plm.change.mvc;

import java.rmi.RemoteException;

import wt.change2.Changeable2;
import wt.change2.WTChangeOrder2;
import wt.util.WTException;

import com.ptc.core.meta.common.impl.TypeIdentifierUtilityHelper;
import com.ptc.core.ui.resources.ComponentMode;
import com.ptc.jca.mvc.components.JcaColumnConfig;
import com.ptc.jca.mvc.components.JcaTableConfig;
import com.ptc.mvc.components.ColumnConfig;
import com.ptc.mvc.components.ComponentBuilder;
import com.ptc.mvc.components.ComponentConfig;
import com.ptc.mvc.components.ComponentConfigFactory;
import com.ptc.mvc.components.ComponentParams;
import com.ptc.mvc.components.TypeBased;
import com.ptc.netmarkets.object.mvc.helper.ItemIdVersion;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.netmarkets.util.beans.NmHelperBean;
import com.ptc.windchill.enterprise.change2.beans.ChangeWizardBean;
import com.ptc.windchill.enterprise.change2.mvc.builders.tables.ResultingItemsTableBuilder;
import com.ptc.jca.mvc.components.JcaComponentParams;

import ext.yongc.plm.constants.IBAConstants;
import ext.yongc.plm.constants.TypeConstants;

@TypeBased({"wt.change2.ChangeActivity2"})
@ComponentBuilder({"yjchange.YJResultingItemsTable"})
public class YJResultingItemsTableBuilder extends ResultingItemsTableBuilder{
	public ComponentConfig buildComponentConfig(ComponentParams paramComponentParams)
		    throws WTException
		  {
		    ComponentConfigFactory configFactory = getComponentConfigFactory();
		    JcaTableConfig tableConfig = (JcaTableConfig)configFactory.newTableConfig();

		    ChangeWizardBean localChangeWizardBean = ChangeWizardBean.getChangeWizardBean(paramComponentParams);
		    NmHelperBean helperBean = ((JcaComponentParams)paramComponentParams).getHelperBean();
		    NmCommandBean commandBean = helperBean.getNmCommandBean();
		    
		    Object obj = commandBean.getActionOid().getRefObject();
	        boolean isMBOM = false;
	        boolean is3D = false;
	        WTChangeOrder2 ecn=null;
	        if(obj instanceof WTChangeOrder2){
	        	ecn = (WTChangeOrder2)obj;
	        	try {
					String type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(ecn).getTypename();
					if(type.indexOf(TypeConstants.ECN_3DDesign)>-1){
						is3D = true;
					}else if(type.indexOf(TypeConstants.ECN_MBOM)>-1){
						isMBOM = true;
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
	        }

		    String str1 = getChangeableTableId(commandBean);
		    paramComponentParams.setAttribute("changeTableId", str1);

		    if (!"changeTask_resultingItems_table".equals(str1)) {
		      paramComponentParams.setAttribute("chgWizTableId", "changeTask_resultingItems_table");
		    }
		    tableConfig.setId(str1);
		    tableConfig.setLabel(getChangeableLabel(commandBean));

		    tableConfig.setSelectable(true);
		    tableConfig.setType(Changeable2.class.getName());
		    String str2 = getActionsPrefix(str1);
		    String str3 = str2 + ".table.view";
		    String str4 = getRowActionsPrefix(str1);
		    String str5 = "change_resultingItems";
		    int i = 0;
		    if ((ComponentMode.CREATE.toString().equals(localChangeWizardBean.getChangeMode())) || (ComponentMode.EDIT.toString().equals(localChangeWizardBean.getChangeMode())))
		    {
		      str3 = str2 + ".table.create_edit";
		      str4 = str2 + ".row.actions";
		      str5 = "change_resultingItems_edit";
		      i = 1;
		    }

//		    tableConfig.setActionModel(str3);

		    tableConfig.addComponent(configFactory.newColumnConfig("statusFamily_General", true));

		    tableConfig.addComponent(configFactory.newColumnConfig("statusFamily_Share", true));

		    tableConfig.addComponent(configFactory.newColumnConfig("statusFamily_Change", true));

		    tableConfig.addComponents(ItemIdVersion.getColumnConfigs(configFactory));

		    tableConfig.addComponent(configFactory.newColumnConfig("launchIncorpHangingChange", true));

		    if (localChangeWizardBean.getChangeMode().equals(ComponentMode.VIEW.toString())) {
		    	ColumnConfig localObject = configFactory.newColumnConfig("compare", true);
		      ((ColumnConfig)localObject).setComponentMode(ComponentMode.VIEW);
		      tableConfig.addComponent((ComponentConfig)localObject);
		    }

		    tableConfig.setInitialRows(true);

		    Object localObject = (JcaColumnConfig)configFactory.newColumnConfig("change_tableData", false);
		    ((JcaColumnConfig)localObject).setDescriptorProperty("supportHangingChanges", Boolean.valueOf(true));
		    ((JcaColumnConfig)localObject).setDataStoreOnly(true);
		    tableConfig.addComponent((ComponentConfig)localObject);

		    tableConfig.addComponent(configFactory.newColumnConfig("infoPageAction", true));
		    
		    

		    localObject = (JcaColumnConfig)configFactory.newColumnConfig("nmActions", true);
		    ((JcaColumnConfig)localObject).setComponentMode(ComponentMode.valueOf(localChangeWizardBean.getChangeMode()));

//		    if (doesActionModelExist(str4)) {
//		      ((JcaColumnConfig)localObject).setDescriptorProperty("actionModel", str4);
//		    }
		    ((JcaColumnConfig)localObject).setDataUtilityId("resultingTableRowActions");
		    tableConfig.addComponent((ComponentConfig)localObject);

		    localObject = (JcaColumnConfig)configFactory.newColumnConfig("name", true);
		    tableConfig.addComponent((ComponentConfig)localObject);
		    
		    if(is3D || isMBOM){
		    	tableConfig.addComponent(configFactory.newColumnConfig(IBAConstants.CNAME, true));
		    	tableConfig.addComponent(configFactory.newColumnConfig("DRAWINGNO", true));
		    	tableConfig.addComponent(configFactory.newColumnConfig(IBAConstants.MATERIAL, true));
		    }else{
		    	tableConfig.addComponent(configFactory.newColumnConfig(IBAConstants.FILENAME, true));
	        	tableConfig.addComponent(configFactory.newColumnConfig(IBAConstants.FILEID, true));
	        	tableConfig.addComponent(configFactory.newColumnConfig(IBAConstants.PRODUCTMODEL, true));
	        	tableConfig.addComponent(configFactory.newColumnConfig(IBAConstants.PRODUCTNAME, true));
		    }

		    localObject = (JcaColumnConfig)configFactory.newColumnConfig("lifeCycleState", true);
		    tableConfig.addComponent((ComponentConfig)localObject);
		    tableConfig.addComponent(configFactory.newColumnConfig("creator", true));
		    tableConfig.addComponent(configFactory.newColumnConfig("thePersistInfo.modifyStamp", true));

		    tableConfig.addComponent(configFactory.newColumnConfig("modifier.name", true));

		    localObject = (JcaColumnConfig)configFactory.newColumnConfig("crDescription", true);
		    ((JcaColumnConfig)localObject).setComponentMode(ComponentMode.valueOf(localChangeWizardBean.getChangeMode()));
		    ((JcaColumnConfig)localObject).setVariableHeight(true);
		    tableConfig.addComponent((ComponentConfig)localObject);

		    addDataColumns(tableConfig, configFactory);

//		    if (i != 0) {
//		      ResultingItemsPickerConfig localResultingItemsPickerConfig = new ResultingItemsPickerConfig();
//		      tableConfig.setToolbarAutoSuggestPickerConfig(localResultingItemsPickerConfig);
//		    }

		    tableConfig.setView("/changetask/resultingItemsTable.jsp");
		    tableConfig.setHelpContext(str5);
		    tableConfig.setInitialRows(true);
		  
		    return tableConfig;
		  }
}
