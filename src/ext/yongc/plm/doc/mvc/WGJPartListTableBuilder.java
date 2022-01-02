/**
 * ext.yongc.plm.doc.mvc.WGJPartListTableBuilder.java
 * @Author yge
 * 2017年11月20日下午4:06:47
 * Comment 
 */
package ext.yongc.plm.doc.mvc;

import java.util.ArrayList;
import java.util.List;

import wt.doc.WTDocument;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.part.WTPart;
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
import ext.yongc.plm.part.link.PartMaterialLink;
import ext.yongc.plm.part.link.WGJPartLink;
import ext.yongc.plm.util.WorkflowUtil;

@ComponentBuilder("yjdoc.WGJPartListTableBuilder")
public class WGJPartListTableBuilder extends AbstractComponentBuilder{

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
	        	QuerySpec qs = new QuerySpec(WGJPartLink.class);
	        	SearchCondition sc = new SearchCondition(WGJPartLink.class,WGJPartLink.ROLE_AMASTERIDA2A2,
	        			"=",rf.getReferenceString(doc.getMaster()));
	        	qs.appendWhere(sc);
	        	QueryResult qr = PersistenceHelper.manager.find(qs);
	        	while(qr.hasMoreElements()){
	        		WGJPartLink link = (WGJPartLink)qr.nextElement();
	        		resultData.add(	rf.getReference(link.getRoleBida2a2()).getObject());
	        	}
	        }else if(obj instanceof WorkItem){
	        	WfProcess process = WorkflowUtil.getProcessByObject(obj);
	        	WTDocument doc = (WTDocument) process.getContext().getValue(WfDefinerHelper.PRIMARY_BUSINESS_OBJECT);
	        	ReferenceFactory rf = new ReferenceFactory();
	        	QuerySpec qs = new QuerySpec(WGJPartLink.class);
	        	SearchCondition sc = new SearchCondition(WGJPartLink.class,WGJPartLink.ROLE_AMASTERIDA2A2,
	        			"=",rf.getReferenceString(doc.getMaster()));
	        	qs.appendWhere(sc);
	        	QueryResult qr = PersistenceHelper.manager.find(qs);
	        	while(qr.hasMoreElements()){
	        		WGJPartLink link = (WGJPartLink)qr.nextElement();
	        		resultData.add(	rf.getReference(link.getRoleBida2a2()).getObject());
	        	}
	        }
	        wt.session.SessionServerHelper.manager.setAccessEnforced(enforce);
			return resultData;
	}

	@Override
	public ComponentConfig buildComponentConfig(ComponentParams arg0)
			throws WTException {
		 ComponentConfigFactory factory = getComponentConfigFactory();
	        TableConfig tableConfig = factory.newTableConfig();
	        tableConfig.setId("WGJPartListTableBuilder.table");
	        tableConfig.setLabel("外购件");
	        tableConfig.setSelectable(false);
	        tableConfig.setShowCount(true);
	    
	        tableConfig.setType("wt.part.WTPart");
//	       tableConfig.setActionModel("CreateWGJTable.actions");
	       
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
