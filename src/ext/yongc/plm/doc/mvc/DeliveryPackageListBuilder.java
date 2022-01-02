/**
 * ext.yongc.plm.doc.mvc.DeliveryPackageListBuilder.java
 * @Author yge
 * 2017年12月26日下午12:19:25
 * Comment 
 */
package ext.yongc.plm.doc.mvc;

import java.util.ArrayList;
import java.util.List;

import wt.doc.WTDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.workflow.definer.WfDefinerHelper;
import wt.workflow.engine.WfProcess;
import wt.workflow.work.WorkItem;

import com.ptc.jca.mvc.components.JcaComponentParams;
import com.ptc.mvc.components.AbstractComponentBuilder;
import com.ptc.mvc.components.ColumnConfig;
import com.ptc.mvc.components.ComponentBuilder;
import com.ptc.mvc.components.ComponentConfig;
import com.ptc.mvc.components.ComponentConfigFactory;
import com.ptc.mvc.components.ComponentParams;
import com.ptc.mvc.components.TableConfig;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.netmarkets.util.beans.NmHelperBean;

import ext.yongc.plm.constants.IBAConstants;
import ext.yongc.plm.doc.link.DocItemLink;
import ext.yongc.plm.util.WorkflowUtil;


@ComponentBuilder("yjdoc.DeliveryPackageListBuilder")
public class DeliveryPackageListBuilder extends AbstractComponentBuilder{

	/* (non-Javadoc)
	 * @see com.ptc.mvc.components.ComponentDataBuilder#buildComponentData(com.ptc.mvc.components.ComponentConfig, com.ptc.mvc.components.ComponentParams)
	 */
	@Override
	public Object buildComponentData(ComponentConfig arg0, ComponentParams arg1)
			throws Exception {
		 boolean enforce = wt.session.SessionServerHelper.manager.setAccessEnforced(false);
		  NmHelperBean helper = ((JcaComponentParams) arg1).getHelperBean();
	        NmCommandBean commandBean = helper.getNmCommandBean();
	        Object obj = commandBean.getActionOid().getRefObject();
	        List resultData = new ArrayList();
	        if(obj instanceof WTDocument){
	        	WTDocument doc = (WTDocument)obj;
	        	ReferenceFactory rf = new ReferenceFactory();
	        	QuerySpec qs = new QuerySpec(DocItemLink.class);
	        	SearchCondition sc = new SearchCondition(DocItemLink.class,DocItemLink.ROLE_AMASTERIDA2A2,
	        			"=",rf.getReferenceString(doc.getMaster()));
	        	SearchCondition sc2 = new SearchCondition(DocItemLink.class,DocItemLink.LINK_TYPE,
	        			"=",DocItemLink.LINK_TYPE_DeliveryPackage);
	        	qs.appendWhere(sc);
	        	qs.appendAnd();
	        	qs.appendWhere(sc2);
	        	QueryResult qr = PersistenceHelper.manager.find(qs);
	        	while(qr.hasMoreElements()){
	        		DocItemLink link = (DocItemLink)qr.nextElement();
	        		resultData.add(	rf.getReference(link.getRoleBida2a2()).getObject());
	        	}
	        }else if(obj instanceof WorkItem){
	        	WfProcess process = WorkflowUtil.getProcessByObject(obj);
	        	WTDocument doc = (WTDocument) process.getContext().getValue(WfDefinerHelper.PRIMARY_BUSINESS_OBJECT);
	        	ReferenceFactory rf = new ReferenceFactory();
	        	QuerySpec qs = new QuerySpec(DocItemLink.class);
	        	SearchCondition sc = new SearchCondition(DocItemLink.class,DocItemLink.ROLE_AMASTERIDA2A2,
	        			"=",rf.getReferenceString(doc.getMaster()));
	        	SearchCondition sc2 = new SearchCondition(DocItemLink.class,DocItemLink.LINK_TYPE,
	        			"=",DocItemLink.LINK_TYPE_DeliveryPackage);
	        	qs.appendWhere(sc);
	        	qs.appendAnd();
	        	qs.appendWhere(sc2);
	        	QueryResult qr = PersistenceHelper.manager.find(qs);
	        	while(qr.hasMoreElements()){
	        		DocItemLink link = (DocItemLink)qr.nextElement();
	        		resultData.add(	rf.getReference(link.getRoleBida2a2()).getObject());
	        	}
	        }
	        wt.session.SessionServerHelper.manager.setAccessEnforced(enforce);
			return resultData;
	}

	/* (non-Javadoc)
	 * @see com.ptc.mvc.components.ComponentConfigBuilder#buildComponentConfig(com.ptc.mvc.components.ComponentParams)
	 */
	@Override
	public ComponentConfig buildComponentConfig(ComponentParams arg0)
			throws WTException {
		ComponentConfigFactory factory = getComponentConfigFactory();
        TableConfig tableConfig = factory.newTableConfig();
        tableConfig.setId("DeliveryPackageListBuilder.table");
        tableConfig.setLabel("分发文件列表");
        tableConfig.setSelectable(true);
        tableConfig.setShowCount(true);
    
        tableConfig.setActionModel("CreateDeliveryPackage.actions");
       
        addColumn("statusFamily_Share", tableConfig, factory);
        addColumn("statusFamily_Change", tableConfig, factory);
        addColumn("statusFamily_General", tableConfig, factory);
        addColumn("type_icon", tableConfig, factory);
	    addColumn("number", tableConfig, factory);
	    addColumn("orgid", tableConfig, factory);
	    addColumn("infoPageAction", tableConfig, factory);
	    addColumn("name", tableConfig, factory);
	    addColumn("version", tableConfig, factory);
	   
	    	  addColumn(IBAConstants.GONGYI_TYPE, tableConfig, factory);
	    	  addColumn(IBAConstants.CNAME, tableConfig, factory);
	    	  addColumn("DRAWINGNO", tableConfig, factory);
	    	  addColumn(IBAConstants.MATERIAL, tableConfig, factory);

	  
	    addColumn("state", tableConfig, factory);
	    addColumn("creator", tableConfig, factory);
	    addColumn("thePersistInfo.modifyStamp", tableConfig, factory);
	    addColumn("modifier.name", tableConfig, factory);
       
	    
	return tableConfig;
}
 private void addColumn(String paramString, TableConfig paramTableConfig, ComponentConfigFactory paramComponentConfigFactory) {
	    ColumnConfig columnConfig = paramComponentConfigFactory.newColumnConfig(paramString, true);
	    columnConfig.setAutoSize(true);
	    paramTableConfig.addComponent(columnConfig);
	  }


}
