/**
 * ext.rdc.standard.bean.ReplacePartPanelBean.java
 * @Author yge
 * 2019年10月11日下午2:17:20
 * Comment 
 */
package ext.rdc.standard.bean;

import com.ptc.core.components.rendering.GuiComponent;
import com.ptc.core.components.rendering.guicomponents.GUIComponentArray;
import com.ptc.core.components.rendering.guicomponents.PushButton;
import com.ptc.core.components.rendering.guicomponents.RadioButton;
import com.ptc.core.components.rendering.guicomponents.RadioButtonGroup;
import com.ptc.core.components.rendering.guicomponents.TextBox;
import com.ptc.netmarkets.model.NmObject;
import com.ptc.netmarkets.model.NmSimpleOid;

import ext.rdc.standard.constants.CusContant;

public class ReplacePartPanelBean extends NmObject{

	 private static final long serialVersionUID = 1L;
	 
	 private static GuiComponent attr0 = new  TextBox();
	 private static GuiComponent attr1 = new RadioButtonGroup();
	 private static GuiComponent attr2 = new RadioButtonGroup();
	 private static GuiComponent attr3 = new RadioButtonGroup();
	 private static GuiComponent attr4 = new RadioButtonGroup();
	 private static GuiComponent attr5 = new RadioButtonGroup();
	 private static GuiComponent attr6 = new RadioButtonGroup();
	 private static GuiComponent attr7 = new RadioButtonGroup();
	 private static GuiComponent attr8 = new GUIComponentArray();
	 
	 public ReplacePartPanelBean() {
	        this.setOid(new NmSimpleOid());
	    }
	/**
	 * @return the serialversionuid
	 */
	public  long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * @return the attr0
	 */
	public  GuiComponent getAttr0() {
		((TextBox) attr0).setId("number");
		 ((TextBox) attr0).setName("number");
		 ((TextBox) attr0).setWidth(30);
		return attr0;
	}
	/**
	 * @return the attr1
	 */
	public  GuiComponent getAttr1() {
		 RadioButton btn1 = new RadioButton();
        btn1.setLabel("相同");
        btn1.setRenderLabel(true);
        btn1.setNameQualifier("same");
        btn1.setColumnName(CusContant.IBA_surfaceProtectionCode);
        btn1.setId("id11_"+CusContant.IBA_surfaceProtectionCode);
        ((RadioButtonGroup) attr1).addButton(btn1);
        
        RadioButton btn2 = new RadioButton();
        btn2.setLabel("不同");
        btn2.setRenderLabel(true);
        btn2.setNameQualifier("different");
        btn2.setColumnName(CusContant.IBA_surfaceProtectionCode);
        btn2.setId("id22_"+CusContant.IBA_surfaceProtectionCode);
        ((RadioButtonGroup) attr1).addButton(btn2);
	        
		return attr1;
	}
	/**
	 * @return the attr2
	 */
	public  GuiComponent getAttr2() {
		 RadioButton btn1 = new RadioButton();
	        btn1.setLabel("推荐使用");
	        btn1.setRenderLabel(true);
	        btn1.setNameQualifier("");
	        btn1.setColumnName(CusContant.IBA_recommendedUsage);
	        btn1.setId("id11_"+CusContant.IBA_recommendedUsage);
	        ((RadioButtonGroup) attr2).addButton(btn1);
	        
	        RadioButton btn2 = new RadioButton();
	        btn2.setLabel("限定使用");
	        btn2.setRenderLabel(true);
	        btn2.setNameQualifier("different");
	        btn2.setColumnName(CusContant.IBA_recommendedUsage);
	        btn2.setId("id22_"+CusContant.IBA_recommendedUsage);
	        ((RadioButtonGroup) attr2).addButton(btn2);
	        
	        RadioButton btn3 = new RadioButton();
	        btn3.setLabel("新项目禁止使用");
	        btn3.setRenderLabel(true);
	        btn3.setNameQualifier("different");
	        btn3.setColumnName(CusContant.IBA_recommendedUsage);
	        btn3.setId("id22_"+CusContant.IBA_recommendedUsage);
	        ((RadioButtonGroup) attr2).addButton(btn3);
		return attr2;
	}
	/**
	 * @return the attr3
	 */
	public  GuiComponent getAttr3() {
		 RadioButton btn1 = new RadioButton();
	        btn1.setLabel("相同");
	        btn1.setRenderLabel(true);
	        btn1.setNameQualifier("");
	        btn1.setColumnName(CusContant.IBA_MechanicalGrade);
	        btn1.setId("id11_"+CusContant.IBA_MechanicalGrade);
	        ((RadioButtonGroup) attr3).addButton(btn1);
	        
	        RadioButton btn2 = new RadioButton();
	        btn2.setLabel("不同");
	        btn2.setRenderLabel(true);
	        btn2.setNameQualifier("different");
	        btn2.setColumnName(CusContant.IBA_MechanicalGrade);
	        btn2.setId("id22_"+CusContant.IBA_MechanicalGrade);
	        ((RadioButtonGroup) attr3).addButton(btn2);
		return attr3;
	}
	/**
	 * @return the attr4
	 */
	public  GuiComponent getAttr4() {
		 RadioButton btn1 = new RadioButton();
	        btn1.setLabel("未认可");
	        btn1.setRenderLabel(true);
	        btn1.setNameQualifier("");
	        btn1.setColumnName(CusContant.IBA_exploitation);
	        btn1.setId("id11_"+CusContant.IBA_exploitation);
	        ((RadioButtonGroup) attr4).addButton(btn1);
	        
	        RadioButton btn2 = new RadioButton();
	        btn2.setLabel("认可中");
	        btn2.setRenderLabel(true);
	        btn2.setNameQualifier("different");
	        btn2.setColumnName(CusContant.IBA_exploitation);
	        btn2.setId("id22_"+CusContant.IBA_exploitation);
	        ((RadioButtonGroup) attr4).addButton(btn2);
	        
	        RadioButton btn3 = new RadioButton();
	        btn3.setLabel("已认可");
	        btn3.setRenderLabel(true);
	        btn3.setNameQualifier("different");
	        btn3.setColumnName(CusContant.IBA_exploitation);
	        btn3.setId("id22_"+CusContant.IBA_exploitation);
	        ((RadioButtonGroup) attr4).addButton(btn3);
		return attr4;
	}
	/**
	 * @return the attr5
	 */
	public  GuiComponent getAttr5() {
		 RadioButton btn1 = new RadioButton();
	        btn1.setLabel("相同");
	        btn1.setRenderLabel(true);
	        btn1.setNameQualifier("same");
	        btn1.setColumnName(CusContant.IBA_RivetBodyDiameter);
	        btn1.setId("id11_"+CusContant.IBA_RivetBodyDiameter);
	        ((RadioButtonGroup) attr5).addButton(btn1);
	        
	        RadioButton btn2 = new RadioButton();
	        btn2.setLabel("不同");
	        btn2.setRenderLabel(true);
	        btn2.setNameQualifier("different");
	        btn2.setColumnName(CusContant.IBA_RivetBodyDiameter);
	        btn2.setId("id22_"+CusContant.IBA_RivetBodyDiameter);
	        ((RadioButtonGroup) attr5).addButton(btn2);
		return attr5;
	}
	/**
	 * @return the attr6
	 */
	public  GuiComponent getAttr6() {
		RadioButton btn1 = new RadioButton();
        btn1.setLabel("相同");
        btn1.setRenderLabel(true);
        btn1.setNameQualifier("same");
        btn1.setColumnName(CusContant.IBA_pitch);
        btn1.setId("id11_"+CusContant.IBA_pitch);
        ((RadioButtonGroup) attr6).addButton(btn1);
        
        RadioButton btn2 = new RadioButton();
        btn2.setLabel("不同");
        btn2.setRenderLabel(true);
        btn2.setNameQualifier("different");
        btn2.setColumnName(CusContant.IBA_pitch);
        btn2.setId("id22_"+CusContant.IBA_pitch);
        ((RadioButtonGroup) attr6).addButton(btn2);
		return attr6;
	}
	/**
	 * @return the attr7
	 */
	public  GuiComponent getAttr7() {
		RadioButton btn1 = new RadioButton();
		btn1.setRenderLabelOnRight(true);
        btn1.setLabel("相同");
        btn1.setRenderLabel(true);
        btn1.setNameQualifier("same");
        btn1.setColumnName(CusContant.IBA_RubberStopperColor);
        btn1.setId("id11_"+CusContant.IBA_RubberStopperColor);
        ((RadioButtonGroup) attr7).addButton(btn1);
        
        RadioButton btn2 = new RadioButton();
        btn2.setRenderLabelOnRight(true);
        btn2.setLabel("不同");
        btn2.setRenderLabel(true);
        btn2.setNameQualifier("different");
        btn2.setColumnName(CusContant.IBA_RubberStopperColor);
        btn2.setId("id22_"+CusContant.IBA_RubberStopperColor);
        ((RadioButtonGroup) attr7).addButton(btn2);
		return attr7;
	}
	 
	public  GuiComponent getAttr8() {
		PushButton b1 = new PushButton("搜索");
		b1.setId("searchButton");
		b1.setName("searchButton");
		b1.addJsAction("onClick", "startSearch()");
		((GUIComponentArray)attr8).addGUIComponent(b1);
		
		PushButton b2 = new PushButton("清除");
		b2.setId("clearButton");
		b2.setName("clearButton");
		b2.addJsAction("onClick", "startClear()");
		((GUIComponentArray)attr8).addGUIComponent(b2);
		return attr8;
	}
	 
}
