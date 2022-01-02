/**
 * ext.yongc.plm.part.mvc.CreateWGJTableBuilder.java
 * @Author yge
 * 2017年11月2日下午3:29:17
 * Comment 
 */
package ext.yongc.plm.part.mvc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import wt.epm.EPMDocument;
import wt.epm.build.EPMBuildRule;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.WTObject;
import wt.part.WTPart;
import wt.part.WTPartHelper;
import wt.part.WTPartStandardConfigSpec;
import wt.part.WTPartUsageLink;
import wt.util.WTException;
import wt.util.WTProperties;
import wt.vc.views.ViewHelper;

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
import ext.yongc.plm.report.BOMReportItem;
import ext.yongc.plm.util.IBAUtil;
import ext.yongc.plm.util.StringUtil;


@ComponentBuilder("yjpart.CreateWGJTableBuilder")
public class CreateWGJTableBuilder extends AbstractComponentBuilder{
	private static WTPartStandardConfigSpec standardConfigSpec = null;
	static{
		try {
				standardConfigSpec = 
						WTPartStandardConfigSpec.newWTPartStandardConfigSpec(
								ViewHelper.service.getView("Design"), null);
			}catch (WTException e) {
			e.printStackTrace();
		}
		
	}
	/* (non-Javadoc)
	 * @see com.ptc.mvc.components.ComponentDataBuilder#buildComponentData(com.ptc.mvc.components.ComponentConfig, com.ptc.mvc.components.ComponentParams)
	 */
	@Override
	public Object buildComponentData(ComponentConfig arg0, ComponentParams arg1)
			throws Exception {
		  boolean enforce = wt.session.SessionServerHelper.manager.setAccessEnforced(false);
		   List<WTObject> list = new ArrayList<WTObject>();
		   NmHelperBean helper = ((JcaComponentParams) arg1).getHelperBean();
	        NmCommandBean commandBean = helper.getNmCommandBean();
	        Object obj = commandBean.getActionOid().getRefObject();
	        wt.session.SessionServerHelper.manager.setAccessEnforced(enforce);
	        if(obj instanceof WTPart){
	        	WTPart part = (WTPart)obj;
	        	list= filterWGJ(getAllChildPart(part));
	        }
	        
	      
//	        if(obj instanceof WTPart){
//	        	WTPart part = (WTPart)obj;
//	        	list.add(part);
//	        	 QueryResult qr=PersistenceHelper.manager.navigate(part, EPMBuildRule.BUILD_SOURCE_ROLE, EPMBuildRule.class);
//	        	 while(qr.hasMoreElements()){
//	        		 EPMDocument epm = (EPMDocument)qr.nextElement();
//	        		 list.add(epm);
//	        	 }
//	        }
		return list;
	}

	/* (non-Javadoc)
	 * @see com.ptc.mvc.components.ComponentConfigBuilder#buildComponentConfig(com.ptc.mvc.components.ComponentParams)
	 */
	@Override
	public ComponentConfig buildComponentConfig(ComponentParams arg0)
			throws WTException {
		 ComponentConfigFactory factory = getComponentConfigFactory();
	        TableConfig tableConfig = factory.newTableConfig();
	        tableConfig.setId("CreateWGJTableBuilder.table");
	        tableConfig.setLabel("外购件");
	        tableConfig.setSelectable(true);
	        tableConfig.setShowCount(true);
	        tableConfig.setSingleSelect(false);
	    
//	        tableConfig.setType("wt.part.WTPart");
	       tableConfig.setActionModel("CreateWGJTable.actions");
	       
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
	 
	 
	  private List getAllChildPart(WTPart part) throws WTException{
		  List result = new ArrayList();
		  result.add(part);
			 QueryResult qqr=PersistenceHelper.manager.navigate(part, EPMBuildRule.BUILD_SOURCE_ROLE, EPMBuildRule.class);
        	 while(qqr.hasMoreElements()){
        		 EPMDocument epm = (EPMDocument)qqr.nextElement();
        		 result.add(epm);
        	 }

		  QueryResult qs = WTPartHelper.service.getUsesWTParts(part, standardConfigSpec);
		  while (qs.hasMoreElements()) {
			  Object obj[] = (Object[]) qs.nextElement();
			  if (obj[1] instanceof WTPart) {
				  WTPart child = (WTPart) obj[1];
				  WTPartUsageLink alink = (WTPartUsageLink) obj[0];
				  result.addAll(getAllChildPart(child));
			  }
		  }
		  return result;
	  }
	  
	  /**
		  * 获取产品结构中所有正在工作的外购件
		  * 外购件规则：图纸代号为15位
		  * @description TODO
		  * @param part
		  * @return
		  * @throws WTException
		  * @author yge  2017年11月27日上午10:13:49
		  */
	  private List filterWGJ(List list)throws WTException{
		  List result = new ArrayList();
		  String state = null;
		  String daihao = null;
		  IBAUtil iba = null;
		  Object obj = null;
		  for(int i=0;i<list.size();i++){
			  obj = list.get(i);
			  if(obj instanceof WTPart){
				  WTPart part = (WTPart)obj;
				  iba = new IBAUtil(part);
				  state = part.getState().toString();
				  daihao = iba.getIBAValue(IBAConstants.DRAWINGNO);
				  if(state.equalsIgnoreCase("INWORK") && StringUtil.isNotEmpty(daihao) && daihao.trim().length()==15){
					  result.add(part);
				  }
			  }else if(obj instanceof EPMDocument){
				  EPMDocument epm = (EPMDocument)obj;
				  iba= new IBAUtil(epm);
				  state = epm.getState().toString();
				  daihao = iba.getIBAValue(IBAConstants.DRAWINGNO);
				  if(state.equalsIgnoreCase("INWORK") && StringUtil.isNotEmpty(daihao) && daihao.trim().length()==15){
					  result.add(epm);
				  }
			  }
		  }
		  
		  return result;
	  }
}
