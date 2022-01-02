/**
 * ext.yongc.plm.doc.mvc.YJGYLXDocTableBuilder.java
 * @Author yge
 * 2017年9月12日下午1:56:06
 * Comment 
 */
package ext.yongc.plm.doc.mvc;

import java.util.ArrayList;
import java.util.List;

import wt.doc.WTDocument;
import wt.epm.EPMDocument;
import wt.fc.QueryResult;
import wt.part.WTPart;
import wt.util.WTException;
import wt.vc.VersionControlHelper;

import com.ptc.core.meta.common.impl.TypeIdentifierUtilityHelper;
import com.ptc.jca.mvc.components.JcaColumnConfig;
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
import ext.yongc.plm.util.PartUtil;
import ext.yongc.plm.util.StringUtil;

@ComponentBuilder("yjdoc.YJGYLXDocTableBuilder")
public class YJGYLXDocTableBuilder extends AbstractComponentBuilder{

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
	        List<WTDocument> resultData = new ArrayList<WTDocument>();
	        if(obj instanceof WTPart){
	        	part = (WTPart)obj;
	        	List<WTDocument> list = PartUtil.getDescDocByPart(part);
	        	for(int i=0;i<list.size();i++){
	        		WTDocument doc = list.get(i);
	        		String type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(doc).getTypename();
	        		if(type.indexOf(TypeConstants.DOC_GYLX)>-1){
	        			//获取最新对象
	        			QueryResult qr = VersionControlHelper.service.allVersionsOf(doc.getMaster());
	            		while(qr.hasMoreElements()){
	            			doc = (WTDocument)qr.nextElement();
	            			break;
	            		}
	        			resultData.add(doc);
	        		}
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
		  NmHelperBean helper = ((JcaComponentParams) arg0).getHelperBean();
	        NmCommandBean commandBean = helper.getNmCommandBean();
	        Object obj = commandBean.getActionOid().getRefObject();
	        WTPart part = null;
	        String label = "";
	        List<WTDocument> resultData = new ArrayList<WTDocument>();
	        if(obj instanceof WTPart){
	        	part = (WTPart)obj;
	        	label = part.getName().toUpperCase();
	        }
		    ComponentConfigFactory factory = getComponentConfigFactory();
	        TableConfig tableConfig = factory.newTableConfig();
	        tableConfig.setId("YJGYLXDocTableBuilder.table");
	        if(StringUtil.isNotEmpty(label)){
	        	 tableConfig.setLabel(label+"-工艺路线");
	        }else{
	        	 tableConfig.setLabel("工艺路线");
	        }
	        tableConfig.setShowCount(true);
	        
	        addColumn("statusFamily_Share", tableConfig, factory);
	        addColumn("statusFamily_Change", tableConfig, factory);
	        addColumn("statusFamily_General", tableConfig, factory);
	        addColumn("type_icon", tableConfig, factory);
		    addColumn("number", tableConfig, factory);
		    addColumn("version", tableConfig, factory);
		    addColumn("orgid", tableConfig, factory);
		    JcaColumnConfig column = (JcaColumnConfig)factory.newColumnConfig("nmActions", true);
		    column.setActionModel("GYLX.mvcTable.rowAction");
            tableConfig.addComponent(column);
		    addColumn("name", tableConfig, factory);
		    
		    addColumn(IBAConstants.LEIXING, tableConfig, factory);
		    addColumn(IBAConstants.ANQUAN, tableConfig, factory);
		    addColumn(IBAConstants.GONGX, tableConfig, factory);
		    addColumn(IBAConstants.GXMC, tableConfig, factory);
		    addColumn(IBAConstants.GZZX, tableConfig, factory);
		    addColumn(IBAConstants.JDGS, tableConfig, factory);
		    addColumn(IBAConstants.JSQ, tableConfig, factory);
		    addColumn(IBAConstants.JGLX, tableConfig, factory);
		    addColumn(IBAConstants.KZM, tableConfig, factory);
		    addColumn(IBAConstants.RGGS, tableConfig, factory);
		    addColumn(IBAConstants.JGZQ, tableConfig, factory);
		    addColumn(IBAConstants.WXCGZ, tableConfig, factory);
		    addColumn(IBAConstants.WXGYS, tableConfig, factory);
		    addColumn(IBAConstants.BHSDJ, tableConfig, factory);
		    addColumn(IBAConstants.JHJHQ, tableConfig, factory);
		    
		   
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
