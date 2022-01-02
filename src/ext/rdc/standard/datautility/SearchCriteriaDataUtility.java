/**
 * ext.rdc.standard.datautility.SearchCriteriaDataUtility.java
 * @Author yge
 * 2019年9月8日下午6:21:53
 * Comment 
 */
package ext.rdc.standard.datautility;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import wt.log4j.LogR;
import wt.util.HTMLEncoder;
import wt.util.WTException;

import com.ptc.carambola.rendering.HTMLComponent;
import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.factory.AbstractDataUtility;
import com.ptc.core.components.rendering.guicomponents.CheckBox;
import com.ptc.core.components.rendering.guicomponents.ComboBox;
import com.ptc.core.components.rendering.guicomponents.GUIComponentArray;
import com.ptc.core.components.rendering.guicomponents.HTMLDivComponent;
import com.ptc.core.components.rendering.guicomponents.HTMLGuiComponent;
import com.ptc.core.components.rendering.guicomponents.Label;
import com.ptc.core.components.rendering.guicomponents.TextBox;
import com.ptc.core.components.rendering.guicomponents.TextDisplayComponent;
import com.ptc.windchill.partslink.PartslinkConstants;
import com.ptc.windchill.partslink.PartslinkConstants.AttributeDataType;
import com.ptc.windchill.partslink.model.RefineBean;

import ext.rdc.standard.util.PropertiesUtil;

public class SearchCriteriaDataUtility extends AbstractDataUtility{
	
	 private static final Logger logger = LogR.getLogger(SearchCriteriaDataUtility.class.getName());
	 
	 private static PropertiesUtil proUtil = null;
	 
	 static{
		 proUtil = new PropertiesUtil("codebase"+File.separator+"ext"+File.separator+"rdc"+File.separator+
				 "standard"+File.separator+"listClassNodeValue.properties");
	 }

	/* (non-Javadoc)
	 * @see com.ptc.core.components.descriptor.DataUtility#getDataValue(java.lang.String, java.lang.Object, com.ptc.core.components.descriptor.ModelContext)
	 */
	@Override
	 public Object getDataValue(String component_id, Object datum, ModelContext mc) throws WTException {
		//component_id= attrId
		logger.debug("component_id : "+component_id);
		logger.debug("datum : "+datum);
		
		Map<String,RefineBean> map = (Map<String,RefineBean>)datum;
		RefineBean refineSearchBean = map.get(component_id);
		logger.debug("Refine bean obtained in refine search data utility is : " + refineSearchBean);
		
		GUIComponentArray guiComponentArray = new GUIComponentArray();
		
		//设置属性名隐藏域，为合并搜索条件
		StringBuffer hiddenBox = new StringBuffer();
		hiddenBox.append("<input type=hidden name=displayName_RefineBean id=displayName_"+component_id);
		hiddenBox.append(" value="+refineSearchBean.getAttrDisplayName()+">");
	    guiComponentArray.addGUIComponent(new HTMLComponent(hiddenBox.toString()));
		
		 setOpteratorValue(guiComponentArray,refineSearchBean);
		 setSelectValue(guiComponentArray,refineSearchBean);
		 listSelectValue(guiComponentArray,refineSearchBean);
		 
		 String rangeValue = refineSearchBean.getRangeValue();
		 String unit = refineSearchBean.getAttrUnits();
		 TextDisplayComponent display = new TextDisplayComponent("rangeValueAndUnit");
		 StringBuffer sb = new StringBuffer("");
		 if(rangeValue!=null && !"".equals(rangeValue.trim())){
			 sb.append("(范围:"+rangeValue);
		 }
		 if(unit!=null && !"".equals(unit.trim())){
			 if("".equals(sb.toString().trim())){
				 sb.append("(");
			 }
			 sb.append(" ,  单位:"+unit+")");
		 }else{
			 if(!"".equals(sb.toString().trim())){
				 sb.append(")");
			 }
		 }
		 display.setId(PartslinkConstants.RefineSearch.ID_PREFIX_UNIT_COL + refineSearchBean.getAttrId().toString());
		 display.setTooltip(sb.toString());
		 display.setValue(sb.toString());
         guiComponentArray.addGUIComponent(display);
			
		return guiComponentArray;
	}
	private void setOpteratorValue(GUIComponentArray guiComponentArray,RefineBean refineSearchBean){
		//如果是string属性并且是合法值列表或是布尔类型时使用listSelectValue方法
		if(refineSearchBean.getAttrDataType() == AttributeDataType.STRING ||
				refineSearchBean.getAttrDataType() == AttributeDataType.BOOLEAN){
			return;
		}
		String attrId = refineSearchBean.getAttrId()+"_RDC";		
		ComboBox operatorComboBox = new ComboBox();
         operatorComboBox.setName(PartslinkConstants.RefineSearch.ID_PREFIX_OPERATOR + attrId);
         operatorComboBox.setId(PartslinkConstants.RefineSearch.ID_PREFIX_OPERATOR + attrId);
         operatorComboBox.setMultiSelect(false);
         operatorComboBox.setEnabled(true);
         operatorComboBox.setTooltip("");
         operatorComboBox.setSelected(refineSearchBean.getOperator());
         operatorComboBox.addStyleClass("width : auto");

         ArrayList<String> operators = refineSearchBean.getOperatorColumnValues();
         ArrayList<String> operatorDisplayValues = refineSearchBean.getOperatorColumnDisplayValues();

         ArrayList<String> internalValues = new ArrayList<String>(operators);
         ArrayList<String> displayValues;
         // Operators value was not populated for integer data type attributes.
         if (operatorDisplayValues.isEmpty()
                 || (operatorDisplayValues.size() == 1 && operatorDisplayValues
                         .contains(PartslinkConstants.NumericOperators.BLANK))) {
             displayValues = new ArrayList<String>(operators);
         } else {
             displayValues = new ArrayList<String>(operatorDisplayValues);
         }

         // Check data type of attribute definition read view and accordingly populate combo box
         if (refineSearchBean.getAttrDataType() == AttributeDataType.LONG
                 || refineSearchBean.getAttrDataType() == AttributeDataType.FLOATING_POINT
                 || refineSearchBean.getAttrDataType() == AttributeDataType.FLOATING_POINT_WITH_UNITS
                 || refineSearchBean.getAttrDataType() == AttributeDataType.TIMESTAMP) {
             operatorComboBox.addJsAction("onChange", "PTC.partslink.refine.makeVisible( '"
                     + attrId + "', this);");
             
             //viewSelectValue定义在codebase/netmarkets/jsp/ext/rdc/standard/search/searchAttributes.jsp中
             //功能是将用户选择的搜索条件显示在页面中
             operatorComboBox.appendJsAction("onChange", "viewSelectValue();");
         }
         else if (refineSearchBean.getAttrDataType() == AttributeDataType.BOOLEAN) {
             operatorComboBox.addJsAction("onChange", "PTC.partslink.refine.addTextToField(this, '"
                     + attrId + "');");

             displayValues = new ArrayList<String>();
             internalValues = new ArrayList<String>();
             displayValues.add(PartslinkConstants.NumericOperators.BLANK);
             internalValues.add(PartslinkConstants.NumericOperators.BLANK);

             for (String operator : operators) {
                 if (operator.equals(PartslinkConstants.BooleanOperators.TRUE.getValue())) {
                     displayValues.add(PartslinkConstants.BooleanOperators.TRUE.getDisplayValue());
                     internalValues.add(operator);
                 } else if (operator.equals(PartslinkConstants.BooleanOperators.FALSE.getValue())) {
                     displayValues.add(PartslinkConstants.BooleanOperators.FALSE.getDisplayValue());
                     internalValues.add(operator);
                 }
             }

         } else {
             operatorComboBox.addJsAction("onChange", "PTC.partslink.refine.addTextToField(this, '"
                     + attrId + "');");
         }

         if (operators.size() > 1) {
             ArrayList<OperatorValue> operatorValueList = new ArrayList<OperatorValue>();
             operatorValueList = sortOperatorValues(displayValues, internalValues);

             displayValues.clear();
             internalValues.clear();
             for (OperatorValue oprValue : operatorValueList) {
                 displayValues.add(oprValue.displayValue);
                 internalValues.add(oprValue.internalValue);
             }

             operatorComboBox.setValues(displayValues);
             operatorComboBox.setInternalValues(internalValues);

             if (!refineSearchBean.isEnabled()) {
                 operatorComboBox.setEditable(false);
             }
             guiComponentArray.addGUIComponent(operatorComboBox);
         }
	}
	private void setSelectValue(GUIComponentArray guiComponentArray,RefineBean refineSearchBean){
		 String toolTip = "";
         if (refineSearchBean.getAttrDataType() == AttributeDataType.TIMESTAMP) {
             toolTip = "yyyy-mm-dd";
         }
         String htmlId = refineSearchBean.getAttrId()+"_RDC";
        		 
         TextBox txtBoxDefault = new TextBox();
         txtBoxDefault.setName(refineSearchBean.getAttrId());
         //txtBoxDefault.setId(refineSearchBean.getAttrId());
         txtBoxDefault.setId(htmlId);
         txtBoxDefault.setValue(refineSearchBean.getValue());
         txtBoxDefault.addJsAction("onmouseover", "PTC.partslink.refine.refinePageTooltip(this)");
         if (refineSearchBean.getOperatorColumnValues().size() > 1) {
             txtBoxDefault.addJsAction("onkeypress", "PTC.partslink.checkEnumEnterPressed(this, '"
                     + htmlId + "', event,'REFINE_SEARCH');");
             txtBoxDefault.addJsAction("onblur", "PTC.partslink.refine.updateEnumeratedFieldValues(this, '"
                     + htmlId + "');");
         } else {
             txtBoxDefault.addJsAction("onkeypress", "PTC.partslink.checkEnterPressed(event,'REFINE_SEARCH');");
         }
         
         //viewSelectValue定义在codebase/netmarkets/jsp/ext/rdc/standard/search/searchAttributes.jsp中
         //功能是将用户选择的搜索条件显示在页面中
         txtBoxDefault.addJsAction("onchange", "viewSelectValue();");
         
         if (!refineSearchBean.isEnabled()) {
             // txtBoxDefault.setEditable(false);
             txtBoxDefault.setReadOnly(true);
         }
         txtBoxDefault.setEnabled(true);
         txtBoxDefault.setWidth(20);
         txtBoxDefault.setTooltip(toolTip);
         if (PartslinkConstants.NumericOperators.RANGE.equals(refineSearchBean.getOperator())) {
             txtBoxDefault.addStyleClass("hidden");
         }

         TextBox txtBox1 = new TextBox();
         txtBox1.setName(PartslinkConstants.RefineSearch.ID_PREFIX_RANGE_LOW
                 + htmlId);
         txtBox1.setId(PartslinkConstants.RefineSearch.ID_PREFIX_RANGE_LOW + htmlId);
         txtBox1.setValue(refineSearchBean.getRangeLow());
         txtBox1.addJsAction("onmouseover", "PTC.partslink.refine.refinePageTooltip(this)");
         txtBox1.addJsAction("onkeypress", "PTC.partslink.checkEnterPressed(event,'REFINE_SEARCH')");
         //viewSelectValue定义在codebase/netmarkets/jsp/ext/rdc/standard/search/searchAttributes.jsp中
         //功能是将用户选择的搜索条件显示在页面中
         txtBox1.addJsAction("onChange", "viewSelectValue();");
         txtBox1.setEditable(true);
         txtBox1.setEnabled(true);
         txtBox1.setWidth(20);
         txtBox1.setTooltip(toolTip);
         if (!PartslinkConstants.NumericOperators.RANGE.equals(refineSearchBean.getOperator())) {
             txtBox1.addStyleClass("hidden");
         }

         TextDisplayComponent tdc = new TextDisplayComponent("refineDo:Hiphen"
                 + htmlId);
         tdc.setId("refineDo:Hiphen" + htmlId);
         tdc.setName("refineDo:Hiphen" + htmlId);
         tdc.setValue(" - ");
         if (!PartslinkConstants.NumericOperators.RANGE.equals(refineSearchBean.getOperator())) {
             tdc.addStyleClass("hidden");
         }

         TextBox txtBox2 = new TextBox();
         txtBox2.setName(PartslinkConstants.RefineSearch.ID_PREFIX_RANGE_HI
                 + htmlId);
         txtBox2.setId(PartslinkConstants.RefineSearch.ID_PREFIX_RANGE_HI + htmlId);
         txtBox2.setValue(refineSearchBean.getRangeHi());
         txtBox2.addJsAction("onmouseover", "PTC.partslink.refine.refinePageTooltip(this)");
         txtBox2.addJsAction("onkeypress", "PTC.partslink.checkEnterPressed(event,'REFINE_SEARCH')");
         txtBox2.addJsAction("onChange", "viewSelectValue();");
         txtBox2.setEditable(true);
         txtBox2.setEnabled(true);
         txtBox2.setWidth(20);
         txtBox2.setTooltip(toolTip);
         if (!PartslinkConstants.NumericOperators.RANGE.equals(refineSearchBean.getOperator())) {
             txtBox2.addStyleClass("hidden");
         }

         guiComponentArray.addGUIComponent(txtBoxDefault);
         guiComponentArray.addGUIComponent(txtBox1);
         guiComponentArray.addGUIComponent(tdc);
         guiComponentArray.addGUIComponent(txtBox2);

         if (refineSearchBean.getOperatorColumnValues().size() > 1)
         {
             TextBox internalNames = new TextBox();
             internalNames.setName(refineSearchBean.getAttrId() + "_internalValues");
             internalNames.setId(htmlId + "_internalValues");
             internalNames.setValue(refineSearchBean.getInternalValue());
             internalNames.setEnabled(true);
             internalNames.setWidth(20);
             internalNames.addStyleClass("hidden");
             guiComponentArray.addGUIComponent(internalNames);
         }
	}
	private void listSelectValue(GUIComponentArray guiComponentArray,RefineBean refineSearchBean){
		logger.debug(" enter setCheckBoxValue");
		if(refineSearchBean.getAttrDataType() == AttributeDataType.STRING || 
				refineSearchBean.getAttrDataType() == AttributeDataType.BOOLEAN){
			String attrId = refineSearchBean.getAttrId();
			 ArrayList<String> operators = refineSearchBean.getOperatorColumnValues();
			 logger.debug("operators = >"+operators);
			 //根据配置文件对可选值进行排序
			 ArrayList<String> sortedOperators = new ArrayList<String>();
			 String keyValues = proUtil.getValueByKey(refineSearchBean.getAttrDisplayName());
			 logger.debug("key values in properties = >"+keyValues);
			 if(keyValues!=null && !"".equals(keyValues.trim())){
				 String[] array = keyValues.split(",");
				 for(String key:array){
					 if(key==null || "".equals(key.trim())){
						 continue;
					 }
					 if(operators.contains(key)){
						 sortedOperators.add(key);
						 operators.remove(key);
					 }
				 }
			 }
			 
			 sortedOperators.addAll(operators);
			 logger.debug("sortedOperators = >"+operators);
			 if(sortedOperators!=null && sortedOperators.size()>0){
				 if(sortedOperators.get(0)!=null && !"".equals(sortedOperators.get(0).trim()) || sortedOperators.size()>1){
					 guiComponentArray.addGUIComponent(new HTMLComponent("可选值： "));
				 }
				 for(String operator:sortedOperators){
					    if(operator==null || "".equals(operator.trim())){
					    	continue;
					    }
					    
					    if(refineSearchBean.getAttrDataType() == AttributeDataType.BOOLEAN){
					    	 if (operator.equals(PartslinkConstants.BooleanOperators.TRUE.getValue())) {
							    	operator = PartslinkConstants.BooleanOperators.TRUE.getDisplayValue();
				                 } else if (operator.equals(PartslinkConstants.BooleanOperators.FALSE.getValue())) {
				                	 operator = PartslinkConstants.BooleanOperators.FALSE.getDisplayValue();
				                 }
					    }
					   
					    
					    StringBuffer sb = new StringBuffer();
					    sb.append("<a class='attributePanel-label' style='width: 51px; white-space: nowrap;'");
					    sb.append(" onclick=\"javascript:var attrId='"+attrId+"';var operator='"+HTMLEncoder.encodeForHTMLAttribute(operator)+"';");
					    sb.append("selectOperatorValue(event,attrId,operator) ;");
					    sb.append("viewSelectValue(); \">");
					    sb.append("<font   style='color:#03447E'>");
					    sb.append(operator);
					    sb.append("</font>");
					    sb.append("</a>");
					    sb.append("&nbsp;&nbsp;");
					    HTMLComponent html = new HTMLComponent(sb.toString());
					    guiComponentArray.addGUIComponent(html);
				 }
			 }
			
		}
		logger.debug(" exit setCheckBoxValue");
	}
	 public ArrayList<OperatorValue> sortOperatorValues(ArrayList<String> displayValues, ArrayList<String> internalValues) {
	        ArrayList<OperatorValue> operatorValueList = new ArrayList<OperatorValue>();

	        if (displayValues != null && internalValues != null) {
	            Iterator displayValueItr = displayValues.iterator();
	            Iterator internalValueItr = internalValues.iterator();

	            while (displayValueItr.hasNext()) {
	                String displayValue = (String) displayValueItr.next();
	                String internalValue = (String) internalValueItr.next();
	                OperatorValue operatorValue = new OperatorValue(displayValue, internalValue);
	                operatorValueList.add(operatorValue);
	            }
	            Collections.sort(operatorValueList);
	        }
	        return operatorValueList;
	    }
	  public class OperatorValue implements Comparable {
	        private String displayValue = "";

	        private String internalValue = "";

	        public String getDisplayValue() {
	            return displayValue;
	        }

	        public String getInternalValue() {
	            return internalValue;
	        }

	        public OperatorValue(String displayValue, String internalValue) {
	            super();
	            this.displayValue = displayValue;
	            this.internalValue = internalValue;
	        }

	        public int compareTo(Object oprVal) {
	            if (oprVal != null && oprVal instanceof OperatorValue) {
	                return this.displayValue.compareTo(((OperatorValue) oprVal).displayValue);
	            }
	            return 0;
	        }

	        @Override
	        public String toString() {
	            return "OperatorValue [displayValue=" + displayValue + ", internalValue=" + internalValue + "]";
	        }
	    }
}

