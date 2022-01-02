/**
 * ext.yongc.plm.change.mvc.YJAffectedItemsTableBuilder.java
 * @Author yge
 * 2017年8月10日下午4:21:49
 * Comment 
 */
package ext.yongc.plm.change.mvc;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import wt.change2.Changeable2;
import wt.epm.EPMDocument;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.util.WTException;

import com.ptc.core.ui.resources.ComponentMode;
import com.ptc.jca.mvc.components.JcaColumnConfig;
import com.ptc.jca.mvc.components.JcaComponentParams;
import com.ptc.jca.mvc.components.JcaTableConfig;
import com.ptc.mvc.components.ColumnConfig;
import com.ptc.mvc.components.ComponentBuilder;
import com.ptc.mvc.components.ComponentConfig;
import com.ptc.mvc.components.ComponentConfigFactory;
import com.ptc.mvc.components.ComponentParams;
import com.ptc.mvc.components.TypeBased;
import com.ptc.mvc.util.ClientMessageSource;
import com.ptc.netmarkets.object.mvc.helper.ItemIdVersion;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.netmarkets.util.beans.NmHelperBean;
import com.ptc.windchill.enterprise.change2.ChangeLinkAttributeHelper;
import com.ptc.windchill.enterprise.change2.beans.ChangeWizardBean;
import com.ptc.windchill.enterprise.change2.mvc.builders.tables.AffectedItemsTableBuilder;
import com.ptc.windchill.enterprise.change2.search.AffectedObjectsPickerConfig;

import ext.yongc.plm.change.search.YJAffectedObjectsPickerConfig;
import ext.yongc.plm.constants.IBAConstants;

@TypeBased({"wt.change2.ChangeActivity2"})
@ComponentBuilder({"yichange.YJAffectedItemsTableBuilder"})
public class YJAffectedItemsTableBuilder extends AffectedItemsTableBuilder{
	Logger log = LogR.getLogger(YJAffectedItemsTableBuilder.class.getName());
	
	 ClientMessageSource messageChange2ClientResource = getMessageSource("com.ptc.windchill.enterprise.change2.change2ClientResource");
	public ComponentConfig buildComponentConfig(ComponentParams paramComponentParams)
		    throws WTException
		  {
		    ComponentConfigFactory configFactory = getComponentConfigFactory();
		    JcaTableConfig tableConfig = (JcaTableConfig)configFactory.newTableConfig();
		    ChangeWizardBean localChangeWizardBean = ChangeWizardBean.getChangeWizardBean(paramComponentParams);
		    int i = 0;

		    String str1 = "";
		    String str2 = "";
		    String str3 = "";
		    if ((ComponentMode.CREATE.toString().equals(localChangeWizardBean.getChangeMode())) || (ComponentMode.EDIT.toString().equals(localChangeWizardBean.getChangeMode())))
		    {
		      str1 = "changeTask.affectedItems.table.create_edit";
		      str2 = "changeTask.affectedItems.row.actions";
		      str3 = "change_affectedItems_edit";
		      i = 1;
		    } else {
		      str1 = "changeTask.affectedItems.table.view";
		      str2 = "changeTask.affectedItems.row.actions.view";
		      str3 = "change_affectedItems";
		    }


		    NmHelperBean helperBean = ((JcaComponentParams)paramComponentParams).getHelperBean();
		    NmCommandBean commandBean = helperBean.getNmCommandBean();
		    Object obj = commandBean.getActionOid().getRefObject();
		    boolean flag = false;
		    if((obj instanceof EPMDocument) ||(obj instanceof WTPart)){
		    	flag = true;
		    }
		    
		    paramComponentParams.setAttribute("changeTableId", "changeTask_affectedItems_table");
		    tableConfig.setId("changeTask_affectedItems_table");
		    tableConfig.setConfigurable(true);
		    tableConfig.setLabel(this.messageChange2ClientResource.getMessage("AFFECTED_ITEMS_TABLE"));
		    tableConfig.setSelectable(true);
		    tableConfig.setActionModel("yjchange.affectedItems");
		    tableConfig.setInitialRows(true);
		    tableConfig.setType(Changeable2.class.getName());
		    tableConfig.addComponent(configFactory.newColumnConfig("statusFamily_General", true));

		    tableConfig.addComponent(configFactory.newColumnConfig("statusFamily_Share", true));

		    tableConfig.addComponent(configFactory.newColumnConfig("statusFamily_Change", true));

		    tableConfig.addComponents(ItemIdVersion.getColumnConfigs(configFactory));
		    
		    if(flag){
	        	tableConfig.addComponent(configFactory.newColumnConfig(IBAConstants.CNAME, true));
	        	tableConfig.addComponent(configFactory.newColumnConfig("DRAWINGNO", true));
	        	tableConfig.addComponent(configFactory.newColumnConfig(IBAConstants.MATERIAL, true));
	        }else{
	        	tableConfig.addComponent(configFactory.newColumnConfig(IBAConstants.FILENAME, true));
	        	tableConfig.addComponent(configFactory.newColumnConfig(IBAConstants.FILEID, true));
	        	tableConfig.addComponent(configFactory.newColumnConfig(IBAConstants.PRODUCTMODEL, true));
	        	tableConfig.addComponent(configFactory.newColumnConfig(IBAConstants.PRODUCTNAME, true));
	        }
		    
			tableConfig.addComponent(configFactory.newColumnConfig("creator", true));
		    JcaColumnConfig columnConfig = (JcaColumnConfig)configFactory.newColumnConfig("change_tableData", false);
		    addSupportedDispositionProperty(columnConfig);
		    columnConfig.setDescriptorProperty("supportAnnotations", Boolean.valueOf(true));
		    columnConfig.setDataStoreOnly(true);
		    tableConfig.addComponent(columnConfig);

		    tableConfig.addComponent(configFactory.newColumnConfig("infoPageAction", true));

		    columnConfig = (JcaColumnConfig)configFactory.newColumnConfig("nmActions", true);
		    columnConfig.setComponentMode(ComponentMode.valueOf(localChangeWizardBean.getChangeMode()));
		    columnConfig.setDescriptorProperty("actionModel", str2);
		    tableConfig.addComponent(columnConfig);

		    tableConfig.addComponent(configFactory.newColumnConfig("name", true));
		    ColumnConfig localColumnConfig1 = configFactory.newColumnConfig("lifeCycleState", true);
		    localColumnConfig1.setHidden(true);
		    tableConfig.addComponent(configFactory.newColumnConfig("lifeCycleState", true));

		    ColumnConfig localColumnConfig2 = configFactory.newColumnConfig("thePersistInfo.modifyStamp", true);
		    localColumnConfig2.setHidden(false);
		    tableConfig.addComponent(localColumnConfig2);

		    ColumnConfig localColumnConfig3 = configFactory.newColumnConfig("modifier.name", true);
		    localColumnConfig3.setHidden(false);
		    tableConfig.addComponent(localColumnConfig3);

		    addDispositionColumns(configFactory, tableConfig, localChangeWizardBean);

		    columnConfig = (JcaColumnConfig)configFactory.newColumnConfig("aadDescription", true);
		    columnConfig.setComponentMode(ComponentMode.valueOf(localChangeWizardBean.getChangeMode()));
		    columnConfig.setVariableHeight(true);
		    tableConfig.addComponent(columnConfig);

		    ColumnConfig localColumnConfig4 = configFactory.newColumnConfig("changeIntent", true);
		    localColumnConfig4.setComponentMode(ComponentMode.valueOf(localChangeWizardBean.getChangeMode()));
		    tableConfig.addComponent(localColumnConfig4);

		    tableConfig.addComponent(configFactory.newColumnConfig("associatedAnnotations", this.messageChange2ClientResource.getMessage("ANNOTATION_SET_LABEL"), true));

		    addDataColumns(tableConfig, configFactory);
		    String str4 = ChangeLinkAttributeHelper.createDataFieldString(getDispositionComponentIds());
		    paramComponentParams.setAttribute("supportedDispositionProperties", str4);
		    
		    if (i != 0) {
		   
		      tableConfig.setToolbarAutoSuggestPickerConfig(new YJAffectedObjectsPickerConfig());
		    }

		    if (ComponentMode.CREATE.toString().equals(localChangeWizardBean.getChangeMode())) {
		      List list = new ArrayList(1);
		      list.add("initiallySelectedPlugin");
		      tableConfig.setPtypes(list);
		    }

		    tableConfig.setView("/changetask/affectedItemsTable.jsp");
		    tableConfig.setHelpContext(str3);
		    if (log.isDebugEnabled()) {
		      log.debug(new StringBuilder().append("Table id is :-- ").append(tableConfig.getId()).toString());
		    }
		    return tableConfig;
		  }
	
	private void addDispositionColumns(ComponentConfigFactory paramComponentConfigFactory, JcaTableConfig paramJcaTableConfig, ChangeWizardBean paramChangeWizardBean)
	  {
	    List<String> localList = getDispositionComponentIds();
	    for (String str : localList) {
	      JcaColumnConfig localJcaColumnConfig = (JcaColumnConfig)paramComponentConfigFactory.newColumnConfig(str, true);
	      localJcaColumnConfig.setComponentMode(ComponentMode.valueOf(paramChangeWizardBean.getChangeMode()));
	      paramJcaTableConfig.addComponent(localJcaColumnConfig);
	    }
	  }
}
