/**
 * ext.yongc.plm.promotion.mvc.CollectReviewDocStepTableBuilder.java
 * @Author yge
 * 2017年7月6日下午4:05:44
 * Comment 
 */
package ext.yongc.plm.promotion.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import wt.log4j.LogR;
import wt.maturity.Promotable;
import wt.util.WTException;

import com.ptc.core.htmlcomp.components.ConfigurableTableBuilder;
import com.ptc.core.htmlcomp.tableview.ConfigurableTable;
import com.ptc.jca.mvc.components.JcaComponentParams;
import com.ptc.jca.mvc.components.JcaTableConfig;
import com.ptc.mvc.components.ColumnConfig;
import com.ptc.mvc.components.ComponentBuilder;
import com.ptc.mvc.components.ComponentConfig;
import com.ptc.mvc.components.ComponentConfigFactory;
import com.ptc.mvc.components.ComponentParams;
import com.ptc.mvc.components.TableConfig;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.enterprise.maturity.PromotionRequestHelper;
import com.ptc.windchill.enterprise.maturity.commands.PromotionItemQueryCommands;
import com.ptc.windchill.enterprise.maturity.mvc.AsyncPromotionObjectsWizardTableBuilder;
import com.ptc.windchill.enterprise.maturity.tableViews.PromotionObjectsWizardTableView;

import ext.yongc.plm.constants.IBAConstants;
import ext.yongc.plm.constants.TypeConstants;
import ext.yongc.plm.promotion.search.AddYJPromotionObjectPickerConfig;

@ComponentBuilder("yjpromotion.CollectReviewDocStepTableBuilder")
public class CollectReviewDocStepTableBuilder extends AsyncPromotionObjectsWizardTableBuilder
implements ConfigurableTableBuilder{

	
	private Logger log = LogR.getLogger(CollectReviewDocStepTableBuilder.class.getName());
	
	/* (non-Javadoc)
	 * @see com.ptc.mvc.components.ComponentDataBuilder#buildComponentData(com.ptc.mvc.components.ComponentConfig, com.ptc.mvc.components.ComponentParams)
	 */
	@Override
	public Object buildComponentData(ComponentConfig paramComponentConfig, ComponentParams paramComponentParams)
			throws Exception {
		log.debug(" ********* enter buildComponentData ******* ");
		ArrayList list = new ArrayList();
	    if ((paramComponentParams instanceof JcaComponentParams)) {
	      JcaComponentParams componentParams = (JcaComponentParams)paramComponentParams;
	      NmCommandBean commandBean = componentParams.getHelperBean().getNmCommandBean();
	      HashMap hashMap = commandBean.getText();
	      log.debug("commandBean.getActionOid().getRefObject() ----------->"+commandBean.getActionOid().getRefObject()); 
	      log.debug("list2 ----------->"+commandBean.getRequestAttribute("oid"));
	      commandBean.addToMap(hashMap, "TABLE_ID", paramComponentConfig.getId(), true);
	      List list2 = PromotionItemQueryCommands.getPromotionItems(commandBean);
	      log.debug("list2 ----------->"+list2);
	      boolean bool = Boolean.valueOf(commandBean.getTextParameter("selectPromotionStateUpdate")).booleanValue();
	      if (!bool) {
	        PromotionRequestHelper.getDataUtilityBean(list2);
	      }
	      return list2;
      }
	    log.debug("list ----------->"+list);
       return list;
	}

	/* (non-Javadoc)
	 * @see com.ptc.mvc.components.ComponentConfigBuilder#buildComponentConfig(com.ptc.mvc.components.ComponentParams)
	 */
	@Override
	public ComponentConfig buildComponentConfig(ComponentParams arg0)
			throws WTException {
		log.debug(" enter buildComponentConfig ===>");
		 ComponentConfigFactory configFactory = getComponentConfigFactory();
		  TableConfig tableConfig = configFactory.newTableConfig();
		  tableConfig.setType(Promotable.class.getName());
		  tableConfig.setId("promotionRequest.promotionObjects");
		  tableConfig.setSelectable(true);
		  tableConfig.setConfigurable(true);
		  String promotionType =(String) arg0.getParameter("promotionType");
		  log.debug("promotionType==========>"+promotionType);
		  boolean is3D = false;
		  boolean isBOM = false;
		  if(TypeConstants.THREEDDesignPromotionNotice.equalsIgnoreCase(promotionType)){
			  is3D = true;
			  tableConfig.setLabel("三维设计文件审签");
			  tableConfig.setActionModel("yjpromotion collectReviewDocStep table");
			  tableConfig.setToolbarAutoSuggestPickerConfig(new AddYJPromotionObjectPickerConfig());
		  }else if(TypeConstants.MBOMPromotionNotice.equalsIgnoreCase(promotionType)){
			  isBOM = true;
			  tableConfig.setLabel("MBOM");
//			  tableConfig.setSelectable(false);
			  tableConfig.setActionModel("yjpromotion collectReviewDocStep table");
		  }else{
			  tableConfig.setLabel("收集文件审签");
			  tableConfig.setActionModel("yjpromotion collectReviewDocStep table");
			  tableConfig.setToolbarAutoSuggestPickerConfig(new AddYJPromotionObjectPickerConfig());
		  }
		  
		  if ((tableConfig instanceof JcaTableConfig)) {
		      ((JcaTableConfig)tableConfig).setDescriptorProperty("referenceType", "OR");
		      ((JcaTableConfig)tableConfig).setInitialRows(true);
		    }
		
		  
		    addColumn("promotionStatus", tableConfig, configFactory);
		    addColumn("statusFamily_General", tableConfig, configFactory);
		    addColumn("statusFamily_Share", tableConfig, configFactory);
		    addColumn("latestStatus", tableConfig, configFactory);
		    addColumn("promotionMessageStatus", tableConfig, configFactory);
		    addColumn("promotionInitialSelectionStatus", tableConfig, configFactory);
		    addColumn("type_icon", tableConfig, configFactory);
		    addColumn("number", tableConfig, configFactory);
		    addColumn("orgid", tableConfig, configFactory);
		    addColumn("version", tableConfig, configFactory);
		    addColumn("name", tableConfig, configFactory);
		    if(is3D || isBOM){
		    	  addColumn(IBAConstants.CNAME, tableConfig, configFactory);
		    	  addColumn("DRAWINGNO", tableConfig, configFactory);
		    	  addColumn(IBAConstants.MATERIAL, tableConfig, configFactory);
		    }else{
		    	addColumn(IBAConstants.FILENAME, tableConfig, configFactory);
		    	addColumn(IBAConstants.FILEID, tableConfig, configFactory);
		    	addColumn(IBAConstants.PRODUCTMODEL, tableConfig, configFactory);
		    	addColumn(IBAConstants.PRODUCTNAME, tableConfig, configFactory);
		    }
		    addColumn("creator", tableConfig, configFactory);
		    addColumn("state", tableConfig, configFactory);
	        addColumn("promotableStates", tableConfig, configFactory);
		    ColumnConfig columnConfig1 = configFactory.newColumnConfig("selectedForPromoteStatus", false);
		    columnConfig1.setHidden(true);
		    columnConfig1.setDataStoreOnly(true);
		    tableConfig.addComponent(columnConfig1);

		    ArrayList list = new ArrayList(2);
		    list.add("promotionTablePlugin");
		    list.add("initiallySelectedPlugin");
		    ((JcaTableConfig)tableConfig).setPtypes(list);

		    ColumnConfig columnConfig2 = configFactory.newColumnConfig("promoteMsgTypeData", false);
		    columnConfig2.setDataStoreOnly(true);
		    tableConfig.addComponent(columnConfig2);

		    ColumnConfig columnConfig3 = configFactory.newColumnConfig("promoteStatesData", false);
		    columnConfig3.setDataStoreOnly(true);
		    tableConfig.addComponent(columnConfig3);

		    ColumnConfig columnConfig4 = configFactory.newColumnConfig("promotionInitialSelectionType", false);
		    columnConfig4.setDataStoreOnly(true);
		    tableConfig.addComponent(columnConfig4);
		    log.debug(" exit buildComponentConfig ===>");
		 return tableConfig;
	}

	/* (non-Javadoc)
	 * @see com.ptc.core.htmlcomp.components.ConfigurableTableBuilder#buildConfigurableTable(java.lang.String)
	 */
	@Override
	public ConfigurableTable buildConfigurableTable(String arg0)
			throws WTException {
		return new PromotionObjectsWizardTableView();
	}

	 private void addColumn(String paramString, TableConfig paramTableConfig, ComponentConfigFactory paramComponentConfigFactory) {
		    ColumnConfig columnConfig = paramComponentConfigFactory.newColumnConfig(paramString, true);
		    paramTableConfig.addComponent(columnConfig);
		  }
}

