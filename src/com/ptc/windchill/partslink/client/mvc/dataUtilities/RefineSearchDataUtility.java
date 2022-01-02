package com.ptc.windchill.partslink.client.mvc.dataUtilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.apache.log4j.Logger;

import wt.log4j.LogR;
import wt.util.WTException;

import com.ptc.core.components.descriptor.ColumnDescriptor;
import com.ptc.core.components.descriptor.ColumnDescriptorFactory;
import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.factory.AbstractDataUtility;
import com.ptc.core.components.rendering.guicomponents.ComboBox;
import com.ptc.core.components.rendering.guicomponents.GUIComponentArray;
import com.ptc.core.components.rendering.guicomponents.TextBox;
import com.ptc.core.components.rendering.guicomponents.TextDisplayComponent;
import com.ptc.windchill.partslink.PartslinkConstants;
import com.ptc.windchill.partslink.PartslinkConstants.AttributeDataType;
import com.ptc.windchill.partslink.client.mvc.builders.RefineSearchAttributeTableBuilder;
import com.ptc.windchill.partslink.model.RefineBean;

/**
 * The Class RefineSearchDataUtility.
 */
public class RefineSearchDataUtility extends AbstractDataUtility {

    /** The Constant logger. */
    private static final Logger logger = LogR.getLogger(RefineSearchDataUtility.class.getName());

    /*
     * (non-Javadoc)
     * 
     * @see com.ptc.core.components.descriptor.DataUtility#getDataValue(java.lang.String, java.lang.Object,
     * com.ptc.core.components.descriptor.ModelContext)
     */
    @Override
    public Object getDataValue(String component_id, Object datum, ModelContext mc) throws WTException {
        boolean isDebugEnabled = logger.isDebugEnabled();
        if (isDebugEnabled) {
            logger.debug("IN RefineSearchDataUtility.getDataValue()");
        }
        // Get Refine search bean object using datum
        RefineBean refineSearchBean = (RefineBean) datum;

        if (isDebugEnabled) {
            logger.debug("Refine bean obtained in refine search data utility is : " + refineSearchBean);
        }
        GUIComponentArray guiComponentArray = new GUIComponentArray();

        String attrId = refineSearchBean.getAttrId();
        if (isDebugEnabled) {
            logger.debug("Component id obtained in refine search data utility is : " + component_id);
        }
        // Attribute name column
        if (RefineSearchAttributeTableBuilder.CSM_ATTRIBUTE_DISPLAY_NAME.equals(component_id)) {

            StringBuffer html = new StringBuffer(
                    "<a href=\"#\"  class=\"attribute\" target=\"_blank\" onclick=\"wfWindowOpen('"
                            + refineSearchBean.getDescUrl()
                            + "' ,"
                            +
                            " target='_blank','menubar=0,toolbar=0,width=650,height=150,resizable=1,scrollbars=1');return false; \">");
            html.append(refineSearchBean.getAttrDisplayName());
            html.append("</a>");
            TextDisplayComponent tdc = new TextDisplayComponent("attribute");
            tdc.setCheckXSS(false);

            tdc.addStyleClass(refineSearchBean.isError() ? "attribute-nameError" : "RefineAttribute-name");
            tdc.setTooltip(refineSearchBean.getAttrDisplayName());
            tdc.setValue(html.toString());
            guiComponentArray.addGUIComponent(tdc);
            return guiComponentArray;
        }
        else if (RefineSearchAttributeTableBuilder.SEARCH_UI_OPERATOR_COLUMN.equals(component_id)) {

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
                        + refineSearchBean.getAttrId().toString() + "', this);");
            }
            else if (refineSearchBean.getAttrDataType() == AttributeDataType.BOOLEAN) {
                operatorComboBox.addJsAction("onChange", "PTC.partslink.refine.addTextToField(this, '"
                        + refineSearchBean.getAttrId().toString() + "');");

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
                        + refineSearchBean.getAttrId().toString() + "');");
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

            return guiComponentArray;
        }
        else if (RefineSearchAttributeTableBuilder.SEARCH_UI_VALUE_COLUMN.equals(component_id)) {
            String toolTip = "";
            if (refineSearchBean.getAttrDataType() == AttributeDataType.TIMESTAMP) {
                toolTip = "yyyy-mm-dd";
            }

            TextBox txtBoxDefault = new TextBox();
            txtBoxDefault.setName(refineSearchBean.getAttrId());
            txtBoxDefault.setId(refineSearchBean.getAttrId());
            txtBoxDefault.setValue(refineSearchBean.getValue());
            txtBoxDefault.addJsAction("onmouseover", "PTC.partslink.refine.refinePageTooltip(this)");
            if (refineSearchBean.getOperatorColumnValues().size() > 1) {
                txtBoxDefault.addJsAction("onkeypress", "PTC.partslink.checkEnumEnterPressed(this, '"
                        + refineSearchBean.getAttrId().toString() + "', event,'REFINE_SEARCH');");
                txtBoxDefault.addJsAction("onblur", "PTC.partslink.refine.updateEnumeratedFieldValues(this, '"
                        + refineSearchBean.getAttrId().toString() + "');");
            } else {
                txtBoxDefault.addJsAction("onkeypress", "PTC.partslink.checkEnterPressed(event,'REFINE_SEARCH');");
            }
            
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
                    + refineSearchBean.getAttrId().toString());
            txtBox1.setId(PartslinkConstants.RefineSearch.ID_PREFIX_RANGE_LOW + refineSearchBean.getAttrId().toString());
            txtBox1.setValue(refineSearchBean.getRangeLow());
            txtBox1.addJsAction("onmouseover", "PTC.partslink.refine.refinePageTooltip(this)");
            txtBox1.addJsAction("onkeypress", "PTC.partslink.checkEnterPressed(event,'REFINE_SEARCH')");
            txtBox1.setEditable(true);
            txtBox1.setEnabled(true);
            txtBox1.setWidth(20);
            txtBox1.setTooltip(toolTip);
            if (!PartslinkConstants.NumericOperators.RANGE.equals(refineSearchBean.getOperator())) {
                txtBox1.addStyleClass("hidden");
            }

            TextDisplayComponent tdc = new TextDisplayComponent("refineDo:Hiphen"
                    + refineSearchBean.getAttrId().toString());
            tdc.setId("refineDo:Hiphen" + refineSearchBean.getAttrId().toString());
            tdc.setName("refineDo:Hiphen" + refineSearchBean.getAttrId().toString());
            tdc.setValue(" - ");
            if (!PartslinkConstants.NumericOperators.RANGE.equals(refineSearchBean.getOperator())) {
                tdc.addStyleClass("hidden");
            }

            TextBox txtBox2 = new TextBox();
            txtBox2.setName(PartslinkConstants.RefineSearch.ID_PREFIX_RANGE_HI
                    + refineSearchBean.getAttrId().toString());
            txtBox2.setId(PartslinkConstants.RefineSearch.ID_PREFIX_RANGE_HI + refineSearchBean.getAttrId().toString());
            txtBox2.setValue(refineSearchBean.getRangeHi());
            txtBox2.addJsAction("onmouseover", "PTC.partslink.refine.refinePageTooltip(this)");
            txtBox2.addJsAction("onkeypress", "PTC.partslink.checkEnterPressed(event,'REFINE_SEARCH')");
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
                internalNames.setId(refineSearchBean.getAttrId() + "_internalValues");
                internalNames.setValue(refineSearchBean.getInternalValue());
                internalNames.setEnabled(true);
                internalNames.setWidth(20);
                internalNames.addStyleClass("hidden");
                guiComponentArray.addGUIComponent(internalNames);
            }

            return guiComponentArray;
        }
        else if (RefineSearchAttributeTableBuilder.SEARCH_UI_RANGE_COLUMN.equals(component_id)) {
            TextDisplayComponent tdcRange = new TextDisplayComponent("rangeColumnTDC");
            tdcRange.setValue(refineSearchBean.getRangeValue());
            tdcRange.setId(PartslinkConstants.RefineSearch.ID_PREFIX_RANGE_COL
                    + refineSearchBean.getAttrId().toString());
            tdcRange.setTooltip(refineSearchBean.getRangeValue());
            guiComponentArray.addGUIComponent(tdcRange);
            return guiComponentArray;
        }
        else if (RefineSearchAttributeTableBuilder.SEARCH_UI_UNITS_COLUMN.equals(component_id)) {
            TextDisplayComponent tdcUnits = new TextDisplayComponent("unitsColumnTDC");
            tdcUnits.setId(PartslinkConstants.RefineSearch.ID_PREFIX_UNIT_COL + refineSearchBean.getAttrId().toString());
            tdcUnits.setTooltip(refineSearchBean.getAttrUnits());
            tdcUnits.setValue(refineSearchBean.getAttrUnits());
            guiComponentArray.addGUIComponent(tdcUnits);
            return guiComponentArray;
        }
        return null;
    }

    @Override
    public ColumnDescriptor getColumnDescriptor(String component_id, ModelContext mc) {
        ColumnDescriptor colDescriptor = null;
        colDescriptor = ColumnDescriptorFactory.getInstance().newIconColumn(mc.getDescriptor());
        colDescriptor.setResizable(true);
        colDescriptor.setDisplayHeaderLabel(true);
        colDescriptor.setInlineStyle("text-align:left;");

        if (RefineSearchAttributeTableBuilder.CSM_ATTRIBUTE_DISPLAY_NAME.equalsIgnoreCase(component_id)) {
            colDescriptor.setWidth(200);
        } else if (RefineSearchAttributeTableBuilder.SEARCH_UI_OPERATOR_COLUMN.equalsIgnoreCase(component_id)) {
            colDescriptor.setWidth(150);
        } else if (RefineSearchAttributeTableBuilder.SEARCH_UI_VALUE_COLUMN.equalsIgnoreCase(component_id)) {
            colDescriptor.setWidth(400);
        } else if (RefineSearchAttributeTableBuilder.SEARCH_UI_RANGE_COLUMN.equalsIgnoreCase(component_id)) {
            colDescriptor.setWidth(100);
        } else {
            // else "Units"
            colDescriptor.setWidth(50);
        }

        return colDescriptor;
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
