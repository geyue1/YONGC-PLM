/**
 * ext.rdc.standard.mvc.ReplacePartPanelBuilder.java
 * @Author yge
 * 2019年10月11日下午1:53:04
 * Comment 
 */
package ext.rdc.standard.mvc;

import wt.util.WTException;

import com.ptc.mvc.components.AbstractComponentBuilder;
import com.ptc.mvc.components.AttributeConfig;
import com.ptc.mvc.components.AttributePanelConfig;
import com.ptc.mvc.components.ComponentBuilder;
import com.ptc.mvc.components.ComponentConfig;
import com.ptc.mvc.components.ComponentConfigFactory;
import com.ptc.mvc.components.ComponentParams;
import com.ptc.mvc.components.GroupConfig;

import ext.rdc.standard.bean.ReplacePartPanelBean;
import ext.rdc.standard.constants.CusContant;

@ComponentBuilder("ext.rdc.standard.mvc.ReplacePartPanelBuilder")
public class ReplacePartPanelBuilder extends AbstractComponentBuilder{

	 @Override
	   public Object buildComponentData(ComponentConfig config, ComponentParams params) throws Exception {
		   return new ReplacePartPanelBean();
	  }
	  @Override
	  public ComponentConfig buildComponentConfig(ComponentParams params) throws WTException {
		    ComponentConfigFactory factory = getComponentConfigFactory();
	        AttributePanelConfig attrPanelConfig = factory.newAttributePanelConfig();
	        
	        GroupConfig group = factory.newGroupConfig("replacePartAttrGroup", "搜索标准件替代方案", 1);
	        AttributeConfig number = factory.newAttributeConfig("attr0", "编号", 0, 0);
	        group.addComponent(number);
	        
	        AttributeConfig attr1 = factory.newAttributeConfig("attr1", "表面防护是否相同", 1, 0);
	        AttributeConfig attr2 = factory.newAttributeConfig("attr2", "推荐使用情况", 1, 1);
	        group.addComponent(attr1);
	        group.addComponent(attr2);
	        
	        AttributeConfig attr3 = factory.newAttributeConfig("attr3", "机械性能等级是否相同", 2, 0);
	        AttributeConfig attr4 = factory.newAttributeConfig("attr4", "开发状态", 2, 1);
	        group.addComponent(attr3);
	        group.addComponent(attr4);
	        
	        AttributeConfig attr5 = factory.newAttributeConfig("attr5", "长度尺寸是否相同", 3, 0);
	        AttributeConfig attr6 = factory.newAttributeConfig("attr6", "螺距", 3, 1);
	        group.addComponent(attr5);
	        group.addComponent(attr6);
	        
	        AttributeConfig attr7 = factory.newAttributeConfig("attr7", "颜色", 4, 0);
	        group.addComponent(attr7);
	        
	        group.addComponent(factory.newAttributeConfig("attr", "", 5, 0));
	        AttributeConfig attr8 = factory.newAttributeConfig("attr8", "", 5, 1);
	        group.addComponent(attr8);
	        
	   	    attrPanelConfig.addComponent(group);
	        return attrPanelConfig;
	  }
}
