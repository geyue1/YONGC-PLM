/**
 * ext.yongc.plm.promotion.mvc.PromotionTaskObjectsTableBulder.java
 * @Author yge
 * 2017年7月7日下午3:34:31
 * Comment 
 */
package ext.yongc.plm.promotion.mvc;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import wt.epm.EPMDocument;
import wt.fc.Persistable;
import wt.fc.QueryResult;
import wt.log4j.LogR;
import wt.maturity.PromotionNotice;
import wt.session.SessionServerHelper;
import wt.util.WTException;
import wt.workflow.definer.WfDefinerHelper;
import wt.workflow.engine.WfActivity;
import wt.workflow.engine.WfProcess;
import wt.workflow.work.WorkItem;

import com.ptc.core.meta.common.impl.TypeIdentifierUtilityHelper;
import com.ptc.core.ui.resources.ComponentMode;
import com.ptc.core.ui.validation.UIValidationStatus;
import com.ptc.jca.mvc.components.JcaColumnConfig;
import com.ptc.jca.mvc.components.JcaComponentParams;
import com.ptc.jca.mvc.components.JcaTableConfig;
import com.ptc.mvc.components.AbstractComponentBuilder;
import com.ptc.mvc.components.ColumnConfig;
import com.ptc.mvc.components.ComponentBuilder;
import com.ptc.mvc.components.ComponentConfig;
import com.ptc.mvc.components.ComponentConfigFactory;
import com.ptc.mvc.components.ComponentParams;
import com.ptc.mvc.components.TableConfig;
import com.ptc.mvc.util.ClientMessageSource;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.netmarkets.util.beans.NmHelperBean;

import ext.yongc.plm.constants.IBAConstants;
import ext.yongc.plm.constants.TypeConstants;
import ext.yongc.plm.constants.WorkflowConstants;
import ext.yongc.plm.resource.YJPromotionRB;
import ext.yongc.plm.util.IBAUtil;
import ext.yongc.plm.util.PromotionUtil;
import ext.yongc.plm.util.StringUtil;
import ext.yongc.plm.util.WorkflowUtil;

@ComponentBuilder("yjpromotion.PromotionTaskObjectsTableBuilder")
public class PromotionTaskObjectsTableBuilder extends AbstractComponentBuilder{

	
      private static final Logger log = LogR.getLogger(PromotionTaskObjectsTableBuilder.class.getName());
	
      /* (non-Javadoc)
	 * @see com.ptc.mvc.components.ComponentDataBuilder#buildComponentData(com.ptc.mvc.components.ComponentConfig, com.ptc.mvc.components.ComponentParams)
	 */
	@Override
	public Object buildComponentData(ComponentConfig arg0, ComponentParams arg1)
			throws Exception {
		log.debug(" enter buildComponentData");
		   boolean enforce = wt.session.SessionServerHelper.manager.setAccessEnforced(false);
		   
		   NmHelperBean helper = ((JcaComponentParams) arg1).getHelperBean();
	        NmCommandBean commandBean = helper.getNmCommandBean();
	        Object obj = commandBean.getActionOid().getRefObject();
	        log.debug("obj ----------->"+obj);
	        PromotionNotice promotion = null;
	        if(obj instanceof WorkItem){
	        	WorkItem item = (WorkItem)obj;
	        	WfActivity wa = (WfActivity) item.getSource().getObject();
	        	WfProcess process = WorkflowUtil.getProcessByObject(obj);
					Persistable p = (Persistable) process.getContext().getValue(WfDefinerHelper.PRIMARY_BUSINESS_OBJECT);
					if(p instanceof PromotionNotice){
						promotion = (PromotionNotice)p;
					}
	        }else if(obj instanceof PromotionNotice){
	        	promotion = (PromotionNotice) obj;
	        }
	     
	        log.debug(" promotion --------------->"+promotion);
	        if(promotion!=null){
	        	String type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(promotion).getTypename();
	        	if(type.indexOf(TypeConstants.THREEDDesignPromotionNotice)>-1){
	        		return process3DDesignDoc(promotion, arg0, arg1);
	        	}else{
	        		return PromotionUtil.getLatestPromotionTargets(promotion);
	        	}
	        }
	     
		return new ArrayList();
	}
	private Object process3DDesignDoc(PromotionNotice promotion,ComponentConfig arg0, ComponentParams arg1) throws WTException{
		   List list = new ArrayList();
		   NmHelperBean helper = ((JcaComponentParams) arg1).getHelperBean();
	        NmCommandBean commandBean = helper.getNmCommandBean();
	        Object obj = commandBean.getActionOid().getRefObject();
	        log.debug("obj ----------->"+obj);
	        String waName = null;
	        if(obj instanceof WorkItem){
	        	WorkItem item = (WorkItem)obj;
	        	WfActivity wa = (WfActivity) item.getSource().getObject();
	        	waName = wa.getName();
	        }
	        
	        log.debug(" waName --------------->"+waName);
	        list = PromotionUtil.getLatestPromotionTargets(promotion);
	      
	        //钢结构焊接、钢结构、机加、布线、组装、表面、绝缘工艺节点
	        List tempList = new ArrayList();
	        for(Object o :list){
	        	if(o instanceof EPMDocument){
	        		IBAUtil iba = new IBAUtil((EPMDocument)o);
	        		String gongyi = iba.getIBAValue(IBAConstants.GONGYI_TYPE);
	        		if(!StringUtil.isEmpty(gongyi) ){
	        		   String[] array = gongyi.split(",");
	        		   for(String s:array){
	        			   if(StringUtil.isNotEmpty(s) &&!StringUtil.isEmpty(waName) && waName.equalsIgnoreCase(s)){
	        					tempList.add(o);
	        			   }
	        		   }
	        		}
	        	}
	        }
	        log.debug(" list -----------------"+list.size());
	        log.debug(" tempList -----------------"+tempList.size());
	        if(tempList!=null && tempList.size()>0){
	        	return tempList;
	        }
		return list;
	}
	/* (non-Javadoc)
	 * @see com.ptc.mvc.components.ComponentConfigBuilder#buildComponentConfig(com.ptc.mvc.components.ComponentParams)
	 */
	@Override
	public ComponentConfig buildComponentConfig(ComponentParams arg0)
			throws WTException {
		   NmHelperBean helper = ((JcaComponentParams) arg0).getHelperBean();
	        NmCommandBean commandBean = helper.getNmCommandBean();
	        Object obj = commandBean.getActionOid().getRefObject();
	        boolean isGongyi = false;
	        boolean isMBOM = false;
	        boolean is3D = false;
	        PromotionNotice promotion=null;
	        if(obj instanceof WorkItem){
	         	WorkItem item = (WorkItem)obj;
	        	WfActivity wa = (WfActivity) item.getSource().getObject();
	        	WfProcess process = WorkflowUtil.getProcessByObject(item);
				Persistable p = (Persistable) process.getContext().getValue(WfDefinerHelper.PRIMARY_BUSINESS_OBJECT);
				if(p instanceof PromotionNotice){
					promotion = (PromotionNotice)p;
					try {
						String type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(promotion).getTypename();
						if(type.indexOf(TypeConstants.THREEDDesignPromotionNotice)>-1 &&
								WorkflowConstants.ACTIVITY_gongyifenpei.equalsIgnoreCase(wa.getName())){
							isGongyi = true;
						}else if(type.indexOf(TypeConstants.MBOMPromotionNotice)>-1){
							isMBOM = true;
						}
						if(type.indexOf(TypeConstants.THREEDDesignPromotionNotice)>-1){
							is3D = true;
						}
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
	        	
	        }
	        
		   ComponentConfigFactory factory = getComponentConfigFactory();
	        TableConfig tableConfig = factory.newTableConfig();
	        tableConfig.setId("promotionRequest.promotionItems.table");
	        tableConfig.setLabel("文件审签列表");
	        tableConfig.setSelectable(isGongyi);
	        tableConfig.setShowCount(true);
	        
	        if(isGongyi){
	        	 tableConfig.setType(EPMDocument.class.getName());
	        	 tableConfig.setActionModel("gongyi shenhe");
	        	 ColumnConfig col1 =  factory.newColumnConfig(IBAConstants.GONGYI_TYPE, true);
	 	         col1.setAutoSize(true);
	 	         col1.setVariableHeight(true);
	 	        col1.setDataStoreOnly(false);
	 	         col1.setLabel(WorkflowConstants.ACTIVITY_gongyifenpei);
	 	         col1.setDataUtilityId("PromotionTaskObjectsDataUtility");
	 	          tableConfig.addComponent(col1);
	        }else if(isMBOM){
	        	 tableConfig.setLabel("MBOM");
	        	  tableConfig.setType("wt.part.WTPart");
	        }else{
	        	  tableConfig.setType("wt.fc.Persistable");
	        }
	       
	        addColumn("statusFamily_Share", tableConfig, factory);
	        addColumn("statusFamily_Change", tableConfig, factory);
	        addColumn("statusFamily_General", tableConfig, factory);
	        if(isMBOM){
	        	ColumnConfig columnConfig = factory.newColumnConfig("extFlag", true);
	        	columnConfig.setLabel("");
	        	columnConfig.setDataUtilityId("PromotionTaskObjectsDataUtility");
	        	 tableConfig.addComponent(columnConfig);
	        }
	        addColumn("type_icon", tableConfig, factory);
		    addColumn("number", tableConfig, factory);
		    addColumn("orgid", tableConfig, factory);
		    addColumn("infoPageAction", tableConfig, factory);
//		    if(isMBOM){
//		    	JcaColumnConfig columnConfig = (JcaColumnConfig)factory.newColumnConfig("nmActions", true);
//		    	columnConfig.setComponentMode(ComponentMode.EDIT);
//			    columnConfig.setDescriptorProperty("actionModel", "MBOM.mvcTable.rowAction");
//			    tableConfig.addComponent(columnConfig);
//		    }
		    addColumn("name", tableConfig, factory);
		    addColumn("version", tableConfig, factory);
		    if(is3D || isMBOM){
		    	  addColumn(IBAConstants.GONGYI_TYPE, tableConfig, factory);
		    	  addColumn(IBAConstants.CNAME, tableConfig, factory);
		    	  addColumn("DRAWINGNO", tableConfig, factory);
		    	  addColumn(IBAConstants.MATERIAL, tableConfig, factory);
		    }else{
		    	addColumn(IBAConstants.FILENAME, tableConfig, factory);
		    	addColumn(IBAConstants.FILEID, tableConfig, factory);
		    	addColumn(IBAConstants.PRODUCTMODEL, tableConfig, factory);
		    	addColumn(IBAConstants.PRODUCTNAME, tableConfig, factory);
		    }
		  
		    addColumn("state", tableConfig, factory);
		    addColumn("creator", tableConfig, factory);
		    addColumn("thePersistInfo.modifyStamp", tableConfig, factory);
		    addColumn("modifier.name", tableConfig, factory);
	       
		    
         log.debug(" tableConfig ---------->"+tableConfig);
		return tableConfig;
	}
	 private void addColumn(String paramString, TableConfig paramTableConfig, ComponentConfigFactory paramComponentConfigFactory) {
		    ColumnConfig columnConfig = paramComponentConfigFactory.newColumnConfig(paramString, true);
		    columnConfig.setAutoSize(true);
		    paramTableConfig.addComponent(columnConfig);
		  }
}
