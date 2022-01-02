/**
 * ext.rdc.standard.mvc.SearchCriteriaPanelBuilder.java
 * @Author yge
 * 2019年9月7日下午4:10:20
 * Comment 
 */
package ext.rdc.standard.mvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import javax.servlet.http.HttpServletRequest;

import wt.fc.ReferenceFactory;
import wt.session.SessionHelper;
import wt.util.WTException;

import com.ptc.core.lwc.common.PropertyDefinitionConstants;
import com.ptc.core.lwc.common.view.GroupDefinitionReadView;
import com.ptc.core.lwc.common.view.PropertyHolderHelper;
import com.ptc.core.lwc.server.LWCStaticGroupDefinition;
import com.ptc.jca.mvc.components.JcaComponentParams;
import com.ptc.mvc.components.AbstractComponentBuilder;
import com.ptc.mvc.components.AttributeConfig;
import com.ptc.mvc.components.AttributePanelConfig;
import com.ptc.mvc.components.ComponentBuilder;
import com.ptc.mvc.components.ComponentConfig;
import com.ptc.mvc.components.ComponentConfigFactory;
import com.ptc.mvc.components.ComponentParams;
import com.ptc.mvc.components.GroupConfig;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.netmarkets.util.beans.NmHelperBean;
import com.ptc.windchill.partslink.PartslinkConstants;
import com.ptc.windchill.partslink.model.RefineBean;
import com.ptc.windchill.partslink.model.RefineModel;

import ext.rdc.standard.util.SearchUtil;

@ComponentBuilder("ext.rdc.standard.mvc.SearchCriteriaPanelBuilder")
public class SearchCriteriaPanelBuilder extends AbstractComponentBuilder{
	private static ReferenceFactory rf = new ReferenceFactory();
	
	 @Override
	    public Object buildComponentData(ComponentConfig config, ComponentParams params) throws Exception {
		       RefineModel model = (RefineModel) params.getAttribute(PartslinkConstants.Model_IDS.REFINE_MODEL);
		       Set<RefineBean> attBeans = model.getRefineBeanSet();
		       Map<String,RefineBean> map = new HashMap<String,RefineBean>();
		       for(RefineBean bean:attBeans){
		    	   map.put(bean.getAttrId(), bean);
		       }
		       return map;
	 }
	    
	    @Override
	    public ComponentConfig buildComponentConfig(ComponentParams params) throws WTException {
	    	NmHelperBean helper = ((JcaComponentParams) params).getHelperBean();
			NmCommandBean nmCommandBean = helper.getNmCommandBean();
			HttpServletRequest request = nmCommandBean.getRequest();
	   	    RefineModel model =(RefineModel) request.getSession().getAttribute(PartslinkConstants.Model_IDS.REFINE_MODEL);
	   	    params.setAttribute(PartslinkConstants.Model_IDS.REFINE_MODEL, model);
	   	    
	    	ComponentConfigFactory factory = getComponentConfigFactory();
	        AttributePanelConfig attrPanelConfig = factory.newAttributePanelConfig();
	        
	        GroupConfig group1 = factory.newGroupConfig("groupAttr_basicAttr", "基本属性", 1);
	        AttributeConfig configName = factory.newAttributeConfig("name", "名称", 0, 0);
	        configName.setDataUtilityId("SearchCriteriaDataUtility");
	        AttributeConfig configNumber = factory.newAttributeConfig("number", "编号", 1, 0);
	        configNumber.setDataUtilityId("SearchCriteriaDataUtility");
	        group1.addComponent(configName);
	        group1.addComponent(configNumber);
	        attrPanelConfig.addComponent(group1);
	        
	        Map<String,Set<RefineBean>> map = SearchUtil.getGroupRefineBean(model);
	        Set<String> groupSet = map.keySet();
	        int groupIndex = 2;
	        for(String groupId:groupSet){
	        	SortedSet<RefineBean> refineBeanSet = (SortedSet<RefineBean>) map.get(groupId);
	        	LWCStaticGroupDefinition group = (LWCStaticGroupDefinition)rf.getReference("OR:com.ptc.core.lwc.server.LWCStaticGroupDefinition:"+groupId).getObject();
	        	 GroupDefinitionReadView groupRV = SearchUtil.getGroupFromType(group.getName(),  model.getTypeDefRV());
	        	 String groupName = PropertyHolderHelper.getPropertyValue(groupRV, SessionHelper.getLocale(), PropertyDefinitionConstants.DISPLAY_NAME_PROPERTY);
	        	
	        	 GroupConfig groupConfig = factory.newGroupConfig("groupAttr_"+groupIndex, groupName, groupIndex);
	        	 int rowIndex = 0;
	        	 for(RefineBean bean:refineBeanSet){
	        		 AttributeConfig config = factory.newAttributeConfig(bean.getAttrId(), bean.getAttrDisplayName(), rowIndex, 0);
	        		 config.setDataUtilityId("SearchCriteriaDataUtility");
	        		 groupConfig.addComponent(config);
//	        		 groupConfig.addComponent(factory.newAttributeConfig("attrDisplayName", "", i, 0));
	        		 rowIndex++;
	        	 }
	        	 attrPanelConfig.addComponent(groupConfig);
	        	 
	        	 groupIndex++;
	        }
	        
	        return attrPanelConfig;
	    }

}
