/**
 * ext.yongc.plm.part.mvc.YJMaterialPartListTableBuilder.java
 * @Author yge
 * 2017年9月17日下午8:16:06
 * Comment 
 */
package ext.yongc.plm.part.mvc;

import java.util.ArrayList;
import java.util.List;

import wt.doc.WTDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.fc.WTObject;
import wt.part.WTPart;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;

import com.ptc.core.meta.common.impl.TypeIdentifierUtilityHelper;
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
import ext.yongc.plm.constants.TypeConstants;
import ext.yongc.plm.part.link.PartMaterialLink;
import ext.yongc.plm.util.PartUtil;
import ext.yongc.plm.util.StringUtil;

@ComponentBuilder("yjpart.YJMaterialPartListTableBuilder")
public class YJMaterialPartListTableBuilder extends AbstractComponentBuilder{

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
	        WTPart part = null;
	        List<PartMaterialLink> resultData = new ArrayList<PartMaterialLink>();
	        if(obj instanceof WTPart){
	        	part = (WTPart)obj;
	        	ReferenceFactory rf = new ReferenceFactory();
	        	QuerySpec qs = new QuerySpec(PartMaterialLink.class);
	        	SearchCondition sc = new SearchCondition(PartMaterialLink.class,PartMaterialLink.ROLE_AIDA2A2,
	        			"=",rf.getReferenceString(part));
	        	qs.appendWhere(sc);
	        	QueryResult qr = PersistenceHelper.manager.find(qs);
	        	while(qr.hasMoreElements()){
	        		resultData.add((PartMaterialLink)qr.nextElement());
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
		 boolean enforce = wt.session.SessionServerHelper.manager.setAccessEnforced(false);
		 NmHelperBean helper = ((JcaComponentParams) arg0).getHelperBean();
	        NmCommandBean commandBean = helper.getNmCommandBean();
	        Object obj = commandBean.getActionOid().getRefObject();
	        WTPart part = null;
	        String label = "";
	        if(obj instanceof WTPart){
	        	part = (WTPart)obj;
	        	label = part.getName().toUpperCase();
	        }
		ComponentConfigFactory factory = getComponentConfigFactory();
        TableConfig tableConfig = factory.newTableConfig();
        tableConfig.setId("YJMaterialPartListTableBuilder.table");
        if(StringUtil.isNotEmpty(label)){
        	 tableConfig.setLabel(label+"-原材料链接");
        }else{
        	 tableConfig.setLabel("原材料链接");
        }
       
        tableConfig.setShowCount(true);
        tableConfig.setSelectable(true);
        tableConfig.setSingleSelect(true);
        tableConfig.setType(PartMaterialLink.class.getName());
        tableConfig.setActionModel("yjpart.partMaterialLinkTable.action");
        
  
        addColumn("type_icon", tableConfig, factory,null);
        ColumnConfig columnConfig1 = factory.newColumnConfig("roleBname", true);
        columnConfig1.setLabel("原材料编码");
        tableConfig.addComponent(columnConfig1);
//	    addColumn("roleBNumber", tableConfig, factory,null);
	    addColumn("orgid", tableConfig, factory,null);
	
	    addColumn(IBAConstants.SHULIANG, tableConfig, factory,"CreatePartMaterialLinkTableDataUtility");
	    addColumn(IBAConstants.LLDWDM, tableConfig, factory,"CreatePartMaterialLinkTableDataUtility");
	    addColumn(IBAConstants.LLDWMC, tableConfig, factory,"CreatePartMaterialLinkTableDataUtility");
	    addColumn(IBAConstants.GONGC, tableConfig, factory,"CreatePartMaterialLinkTableDataUtility");
	    addColumn(IBAConstants.JBJLDW, tableConfig, factory,"CreatePartMaterialLinkTableDataUtility");
	    addColumn(IBAConstants.MJSL, tableConfig, factory,"CreatePartMaterialLinkTableDataUtility");
	    addColumn(IBAConstants.GYLX, tableConfig, factory,"CreatePartMaterialLinkTableDataUtility");
	    addColumn(IBAConstants.SLDW, tableConfig, factory,"CreatePartMaterialLinkTableDataUtility");
	    addColumn(IBAConstants.BANBENHAO, tableConfig, factory,"CreatePartMaterialLinkTableDataUtility");
	    addColumn(IBAConstants.FUZHU, tableConfig, factory,"CreatePartMaterialLinkTableDataUtility");
	    
	    //addColumn("creator", tableConfig, factory,null);
	    addColumn("thePersistInfo.modifyStamp", tableConfig, factory,null);
	    addColumn("modifier.name", tableConfig, factory,null);
	    wt.session.SessionServerHelper.manager.setAccessEnforced(enforce);
	  return tableConfig;
}

 private void addColumn(String paramString, TableConfig paramTableConfig, ComponentConfigFactory paramComponentConfigFactory,String datautilityId) {
	    ColumnConfig columnConfig = paramComponentConfigFactory.newColumnConfig(paramString, true);
	    columnConfig.setAutoSize(true);
	    if(StringUtil.isNotEmpty(datautilityId)){
	    	  columnConfig.setDataUtilityId(datautilityId);
	    }
	    paramTableConfig.addComponent(columnConfig);
	  }

}
