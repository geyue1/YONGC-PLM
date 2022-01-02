/**
 * ext.yongc.plm.doc.mvc.CreateDeliveryPackageBuilder.java
 * @Author yge
 * 2017年12月26日上午11:32:45
 * Comment 
 */
package ext.yongc.plm.doc.mvc;

import java.util.ArrayList;
import java.util.List;

import wt.fc.WTObject;
import wt.part.WTPart;
import wt.util.WTException;

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

@ComponentBuilder("yjdoc.CreateDeliveryPackageBuilder")
public class CreateDeliveryPackageBuilder extends AbstractComponentBuilder{

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
	        wt.session.SessionServerHelper.manager.setAccessEnforced(enforce);
		return obj;
	}

	/* (non-Javadoc)
	 * @see com.ptc.mvc.components.ComponentConfigBuilder#buildComponentConfig(com.ptc.mvc.components.ComponentParams)
	 */
	@Override
	public ComponentConfig buildComponentConfig(ComponentParams arg0)
			throws WTException {
		 ComponentConfigFactory factory = getComponentConfigFactory();
	        TableConfig tableConfig = factory.newTableConfig();
	        tableConfig.setId("CreateDeliveryPackageBuilder.table");
	        tableConfig.setLabel("分发文件列表");
	        tableConfig.setSelectable(true);
	        tableConfig.setShowCount(true);
	        tableConfig.setSingleSelect(false);
	    
//	        tableConfig.setType("wt.part.WTPart");
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
