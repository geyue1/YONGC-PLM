/**
 * ext.yongc.plm.change.mvc.YJWizardImplementationPlanTableBuilder.java
 * @Author yge
 * 2017年8月10日下午6:21:42
 * Comment 
 */
package ext.yongc.plm.change.mvc;

import wt.change2.VersionableChangeItem;
import wt.epm.EPMDocument;
import wt.part.WTPart;
import wt.util.WTException;

import com.ptc.core.components.beans.CreateAndEditWizBean;
import com.ptc.jca.mvc.components.JcaComponentParams;
import com.ptc.jca.mvc.components.JcaTableConfig;
import com.ptc.jca.mvc.components.JcaColumnConfig;
import com.ptc.mvc.components.ColumnConfig;
import com.ptc.mvc.components.ComponentBuilder;
import com.ptc.mvc.components.ComponentConfig;
import com.ptc.mvc.components.ComponentConfigFactory;
import com.ptc.mvc.components.ComponentParams;
import com.ptc.mvc.components.TableConfig;
import com.ptc.mvc.components.ds.DataSourceMode;
import com.ptc.mvc.util.ClientMessageSource;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.netmarkets.util.beans.NmHelperBean;
import com.ptc.windchill.enterprise.change2.mvc.builders.tables.WizardImplementationPlanTableBuilder;

import ext.yongc.plm.constants.IBAConstants;

@ComponentBuilder({"yjchange.wizardImplementationPlan"})
public class YJWizardImplementationPlanTableBuilder extends WizardImplementationPlanTableBuilder{
	  private final ClientMessageSource messageSource = getMessageSource("com.ptc.windchill.enterprise.change2.change2ClientResource");
	  private final ClientMessageSource component_resource = getMessageSource("com.ptc.core.ui.componentRB");
	  
	  public ComponentConfig buildComponentConfig(ComponentParams paramComponentParams) throws WTException
	  {
	    ComponentConfigFactory configFactory = getComponentConfigFactory();
	    TableConfig tableConfig = configFactory.newTableConfig();
	    tableConfig.setId("changeNotice.wizardImplementationPlan.table");
	    boolean bool = isChangeTemplate(paramComponentParams);
	   // if (bool)
	    //  tableConfig.setActionModel("changeNotice.implementationPlan.table.create_edit_template");
	 //   else {
	    //  tableConfig.setActionModel("changeNotice.implementationPlan.table.create_edit");
	 //   }
	    tableConfig.setLabel(this.messageSource.getMessage("IMPLEMENTATION_PLAN_TABLE"));
	    tableConfig.setType("wt.change2.ChangeActivity2");
	    tableConfig.setHelpContext("change_implementationPlan_edit");
	    tableConfig.setSelectable(true);

	    
	    tableConfig.addComponent(configFactory.newColumnConfig("changeTask_type_icon", true));

	    ColumnConfig localColumnConfig1 = configFactory.newColumnConfig("changeTask_number", true);

	    localColumnConfig1.setLabel(this.component_resource.getMessage("NUMBER"));
	    ((JcaTableConfig)tableConfig).setDataSourceMode(DataSourceMode.DISABLED);
	    ((JcaTableConfig)tableConfig).setInitialRows(true);

	    tableConfig.addComponent(localColumnConfig1);

	    tableConfig.addComponent(configFactory.newColumnConfig("changeTask_organizationName", true));

	    ColumnConfig localColumnConfig2 = configFactory.newColumnConfig("changeTask_revision", true);
	    ((JcaColumnConfig)localColumnConfig2).setDescriptorProperty("display", "VERSION");
	    tableConfig.addComponent(localColumnConfig2);

	    tableConfig.addComponent(configFactory.newColumnConfig("changeTask_name", this.component_resource.getMessage("NAME"), true));
	    ColumnConfig nmActionColumnConfig = configFactory.newColumnConfig("changeTask_YJnmIconActions", false);
	    nmActionColumnConfig.setDataUtilityId("YJWizardImplementationPlanDataUtility");
	    tableConfig.addComponent(nmActionColumnConfig);
//	    tableConfig.addComponent(configFactory.newColumnConfig("changeTask_nmIconActions", false));

	    ColumnConfig localColumnConfig3 = configFactory.newColumnConfig();
	    localColumnConfig3.setId("nmActions");

	    NmHelperBean helperBean = ((JcaComponentParams)paramComponentParams).getHelperBean();
	    NmCommandBean commandBean = helperBean.getNmCommandBean();
	    Object obj = commandBean.getActionOid().getRefObject();
	    boolean flag = false;
	    if((obj instanceof EPMDocument) ||(obj instanceof WTPart)){
	    	flag = true;
	    }
	    
	    String str = CreateAndEditWizBean.getOperation(commandBean);
//	    if (bool)
//	      localColumnConfig3.setActionModel("edit implementation table template row actions");
//	    else if (ComponentMode.CREATE.toString().equals(str))
//	      localColumnConfig3.setActionModel("create implementation table row actions");
//	    else {
//	      localColumnConfig3.setActionModel("edit implementation table row actions");
//	    }
	    localColumnConfig3.setActionModel("yjchange edit implementation table row actions");
	    tableConfig.addComponent(localColumnConfig3);
	    if(flag){
//        	tableConfig.addComponent(configFactory.newColumnConfig(IBAConstants.CNAME, true));
//        	tableConfig.addComponent(configFactory.newColumnConfig("DRAWINGNO", true));
//        	tableConfig.addComponent(configFactory.newColumnConfig(IBAConstants.MATERIAL, true));
        }
	    tableConfig.addComponent(configFactory.newColumnConfig("changeTask_lifeCycleState", this.component_resource.getMessage("STATE"), true));
       
	    
	    ColumnConfig localColumnConfig4 = configFactory.newColumnConfig("changeTask_ROLE_ASSIGNEE", this.component_resource.getMessage("ROLE_ASSIGNEE"), true);
	    localColumnConfig4.setColumnWrapped(false);
	    localColumnConfig4.setVariableHeight(true);
	    localColumnConfig4.setExactWidth(true);
	    tableConfig.addComponent(localColumnConfig4);

	    ColumnConfig localColumnConfig5 = configFactory.newColumnConfig("changeTask_ROLE_REVIEWER", this.component_resource.getMessage("ROLE_REVIEWER"), true);
	    localColumnConfig5.setColumnWrapped(false);
	    localColumnConfig5.setVariableHeight(true);
	    localColumnConfig5.setExactWidth(true);
	    tableConfig.addComponent(localColumnConfig5);

	    tableConfig.addComponent(configFactory.newColumnConfig("changeTaskStatus", true));

	    tableConfig.addComponent(configFactory.newColumnConfig("changeTaskSequence", true));

	    tableConfig.addComponent(configFactory.newColumnConfig("changeTask_needDate", this.component_resource.getMessage("NEED_DATE"), true));

	
	    return tableConfig;
	  }
	
	private boolean isChangeTemplate(ComponentParams paramComponentParams)
	  {
	    boolean bool = false;
	    Object localObject1 = paramComponentParams.getContextObject();

	    Object localObject2 = paramComponentParams.getParameter("actionName");
	    String str = "";
	    if (localObject2 != null) {
	      str = localObject2.toString();
	      if (("createTemplate".equals(str)) || ("editTemplate".equals(str)))
	        bool = true;
	    }
	    else if ((localObject1 != null) && (VersionableChangeItem.class.isAssignableFrom(localObject1.getClass()))) {
	      VersionableChangeItem localVersionableChangeItem = (VersionableChangeItem)localObject1;
	      if ((localVersionableChangeItem.getTemplate() != null) && (localVersionableChangeItem.getTemplate().isTemplated())) {
	        bool = true;
	      }
	    }
	    return bool;
	  }
}
