/**
 * ext.yongc.plm.part.mvc.CreatePartMaterialLinkTableBuilder.java
 * @Author yge
 * 2017年9月13日下午4:04:52
 * Comment 
 */
package ext.yongc.plm.part.mvc;

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
import ext.yongc.plm.part.link.PartMaterialLink;
import ext.yongc.plm.util.StringUtil;

@ComponentBuilder("yjpart.PartMaterialLinkTableBuilder")
public class CreatePartMaterialLinkTableBuilder extends AbstractComponentBuilder{

	/* (non-Javadoc)
	 * @see com.ptc.mvc.components.ComponentDataBuilder#buildComponentData(com.ptc.mvc.components.ComponentConfig, com.ptc.mvc.components.ComponentParams)
	 */
	@Override
	public Object buildComponentData(ComponentConfig arg0, ComponentParams arg1)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
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
	        if(obj instanceof WTPart){
	        	part = (WTPart)obj;
	        	label = part.getName().toUpperCase();
	        }
	        
		 ComponentConfigFactory factory = getComponentConfigFactory();
	        TableConfig tableConfig = factory.newTableConfig();
	        tableConfig.setId("PartMaterialLinkTableBuilder.table");
	        if(StringUtil.isNotEmpty(label)){
	        	 tableConfig.setLabel(label+"-原材料链接");
	        }else{
	        	 tableConfig.setLabel("原材料链接");
	        }
	       
	        tableConfig.setShowCount(true);
	        tableConfig.setSelectable(true);
	        tableConfig.setSingleSelect(false);
	        tableConfig.setType(WTObject.class.getName());
	        tableConfig.setActionModel("yjpart.partMaterialLinkTable.action");
	        
	  
	        addColumn("type_icon", tableConfig, factory,null);
	        ColumnConfig columnConfig1 = factory.newColumnConfig("roleBNumber", true);
	        columnConfig1.setLabel("原材料编码");
	        columnConfig1.setDataUtilityId("CreatePartMaterialLinkTableDataUtility");
	        tableConfig.addComponent(columnConfig1);
//		    addColumn("roleBNumber", tableConfig, factory,null);
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
