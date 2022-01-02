package com.ptc.windchill.partslink.client.mvc.dataUtilities;

import java.util.ArrayList;

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
import com.ptc.windchill.partslink.client.mvc.builders.FindSimilarSearchAttributeTableBuilder;
import com.ptc.windchill.partslink.model.FindSimilarBean;

/**
 * The Class FindSimilarSearchDataUitility.
 */
public class FindSimilarSearchDataUitility extends AbstractDataUtility {

    /** The Constant logger. */
    private static final Logger logger = LogR.getLogger(FindSimilarSearchDataUitility.class.getName());

    /*
     * (non-Javadoc)
     * 
     * @see com.ptc.core.components.descriptor.DataUtility#getDataValue(java.lang.String, java.lang.Object,
     * com.ptc.core.components.descriptor.ModelContext)
     */
    @Override
    public Object getDataValue(String component_id, Object datum, ModelContext mc) throws WTException {

        logger.debug("IN ==> FindSimilarSearchDataUitility.getDataValue()");

        // Get Find Similar search bean object using datum
        FindSimilarBean findSimilarSearchBean = (FindSimilarBean) datum;
        GUIComponentArray guiComponentArray = new GUIComponentArray();
        String attrId = findSimilarSearchBean.getAttrId();

        // Attribute name column
        if (FindSimilarSearchAttributeTableBuilder.CSM_ATTRIBUTE_DISPLAY_NAME.equals(component_id)) {

            StringBuffer html = new StringBuffer(
                    "<a href=\"#\"  class=\"attribute\" target=\"_blank\" onclick=\"wfWindowOpen('"
                            + findSimilarSearchBean.getDescUrl()
                            + "' ,"
                            +
                            " target='_blank','menubar=0,toolbar=0,width=650,height=150,resizable=1,scrollbars=1');return false; \">");
            html.append(findSimilarSearchBean.getAttrDisplayName());
            html.append("</a>");
            TextDisplayComponent tdc = new TextDisplayComponent("attribute");
            tdc.setCheckXSS(false);
            tdc.addStyleClass(findSimilarSearchBean.isError() ? "attribute-nameError" : "RefineAttribute-name");
            tdc.setTooltip(findSimilarSearchBean.getAttrDisplayName());
            tdc.setValue(html.toString());
            guiComponentArray.addGUIComponent(tdc);
            return guiComponentArray;

        }
        else if (FindSimilarSearchAttributeTableBuilder.SEARCH_UI_VALUE_COLUMN.equals(component_id)) {
            TextBox txtBoxDefault = new TextBox();
            txtBoxDefault.setName(findSimilarSearchBean.getAttrId());
            txtBoxDefault.setId(findSimilarSearchBean.getAttrId());
            txtBoxDefault.setValue(findSimilarSearchBean.getValue());
            txtBoxDefault.addJsAction("onmouseover", "PTC.partslink.refine.refinePageTooltip(this)");
            if (findSimilarSearchBean.getModifierColumnValues().size() > 1) {
                txtBoxDefault.addJsAction("onkeypress",
                        "PTC.partslink.checkEnumEnterPressed(this, '"
                                + findSimilarSearchBean.getAttrId().toString()
                                + "', event,'FIND_SIMILAR_SEARCH');");
                txtBoxDefault.addJsAction("onblur",
                        "PTC.partslink.refine.updateEnumeratedFieldValues(this, '"
                                + findSimilarSearchBean.getAttrId().toString()
                                + "');");
            } else {
                txtBoxDefault
                        .addJsAction("onkeypress",
                                "PTC.partslink.checkEnterPressed(event,'REFINE_SEARCH');");
            }
            txtBoxDefault.setEnabled(true);
            txtBoxDefault.setTooltip("");
            txtBoxDefault.setWidth(20);

            guiComponentArray.addGUIComponent(txtBoxDefault);

            if (findSimilarSearchBean.getModifierColumnValues().size() > 1) {
                TextBox internalValues = new TextBox();
                internalValues.setName(findSimilarSearchBean.getAttrId() + "_internalValues");
                internalValues.setId(findSimilarSearchBean.getAttrId() + "_internalValues");
                internalValues.setValue(findSimilarSearchBean.getInternalValue());
                internalValues.setEnabled(true);
                internalValues.setWidth(20);
                internalValues.addStyleClass("hidden");

                guiComponentArray.addGUIComponent(internalValues);
            }

            return guiComponentArray;
        }
        else if (FindSimilarSearchAttributeTableBuilder.SEARCH_UI_UNITS_COLUMN.equals(component_id)) {
            TextDisplayComponent tdcUnits = new TextDisplayComponent("unitsColumnTDC");
            tdcUnits.setId(PartslinkConstants.RefineSearch.ID_PREFIX_UNIT_COL
                    + findSimilarSearchBean.getAttrId().toString());
            tdcUnits.setTooltip(findSimilarSearchBean.getAttrUnits());
            tdcUnits.setValue(findSimilarSearchBean.getAttrUnits());
            guiComponentArray.addGUIComponent(tdcUnits);
            return guiComponentArray;
        }

        else if (FindSimilarSearchAttributeTableBuilder.SEARCH_UI_MODIFIER_COLUMN.equals(component_id)) {

            if (findSimilarSearchBean.getAttrDataType() == AttributeDataType.LONG
                    || findSimilarSearchBean.getAttrDataType() == AttributeDataType.FLOATING_POINT ||
                    findSimilarSearchBean.getAttrDataType() == AttributeDataType.FLOATING_POINT_WITH_UNITS) {
                // TextDisplayComponent for displaying MoreLess Sign
                TextDisplayComponent textdisplaycomponent = new TextDisplayComponent("MoreLess");
                textdisplaycomponent.setValue(PartslinkConstants.NumericOperators.MORE_LESS);
                textdisplaycomponent.addStyleClass(PartslinkConstants.CSSStyles.PARTSLINK_PPDATA_STYLE);
                guiComponentArray.addGUIComponent(textdisplaycomponent);
                TextBox textboxcomponent = new TextBox();
                textboxcomponent.setName(PartslinkConstants.findSimilar.ID_PREFIX_NUMERIC_MODIFIER
                        + findSimilarSearchBean.getAttrId().toString());
                textboxcomponent.setId(PartslinkConstants.findSimilar.ID_PREFIX_NUMERIC_MODIFIER
                        + findSimilarSearchBean.getAttrId().toString());
                textboxcomponent.setValue(findSimilarSearchBean.getModifierValue());
                textboxcomponent.addJsAction("onkeypress",
                        "PTC.partslink.checkEnterPressed(event,'FIND_SIMILAR_SEARCH');");
                guiComponentArray.addGUIComponent(textboxcomponent);
            }
            else {
                TextDisplayComponent textdisplaycomponent = new TextDisplayComponent("Adjust");
                textdisplaycomponent.setValue("     ");
                textdisplaycomponent.addStyleClass(PartslinkConstants.CSSStyles.PARTSLINK_PPDATA_STYLE);
                guiComponentArray.addGUIComponent(textdisplaycomponent);
            }

            ComboBox operatorComboBox = new ComboBox();
            operatorComboBox.setName(PartslinkConstants.RefineSearch.ID_PREFIX_OPERATOR + attrId);
            operatorComboBox.setId(PartslinkConstants.RefineSearch.ID_PREFIX_OPERATOR + attrId);
            operatorComboBox.setMultiSelect(false);
            operatorComboBox.setEnabled(true);
            operatorComboBox.setTooltip("");
            operatorComboBox.setSelected(findSimilarSearchBean.getModifier());

            ArrayList<String> modifiers = findSimilarSearchBean.getModifierColumnValues();
            ArrayList<String> modifierDisplayValues = findSimilarSearchBean.getModifierColumnDisplayValues();

            // the modifiers can be only blank in case of legal value list
            if (modifierDisplayValues.isEmpty() || (modifierDisplayValues.size() == 1 && modifierDisplayValues
                    .contains(PartslinkConstants.NumericOperators.BLANK)))
            {
                modifierDisplayValues = modifiers;
            }

            ArrayList<String> displayValues = modifierDisplayValues;
            ArrayList<String> internalValues = modifiers;

            if (modifiers.size() > 1) {
                if (findSimilarSearchBean.getAttrDataType() == AttributeDataType.BOOLEAN) {
                    if (findSimilarSearchBean.getModifier() != null) {

                        displayValues = new ArrayList<String>();
                        internalValues = new ArrayList<String>();
                        displayValues.add(PartslinkConstants.NumericOperators.BLANK);
                        internalValues.add(PartslinkConstants.NumericOperators.BLANK);

                        for (String modifier : modifiers) {
                            if (modifier.equals(PartslinkConstants.BooleanOperators.TRUE.getValue())) {
                                displayValues.add(PartslinkConstants.BooleanOperators.TRUE.getDisplayValue());
                                internalValues.add(modifier);
                            } else if (modifier.equals(PartslinkConstants.BooleanOperators.FALSE.getValue())) {
                                displayValues.add(PartslinkConstants.BooleanOperators.FALSE.getDisplayValue());
                                internalValues.add(modifier);
                            }
                        }

                        operatorComboBox.setSelected(findSimilarSearchBean.getModifier());
                    }
                    operatorComboBox.addJsAction("onChange", "PTC.partslink.refine.addTextToField(this, '"
                            + findSimilarSearchBean.getAttrId().toString() + "');");
                }
                if (findSimilarSearchBean.getAttrDataType().equals(AttributeDataType.STRING)) {
                    if (findSimilarSearchBean.getModifier() != null) {
                        operatorComboBox.setSelected(findSimilarSearchBean.getModifier());
                    }
                    operatorComboBox.addJsAction("onChange", "PTC.partslink.refine.addTextToField(this, '"
                            + findSimilarSearchBean.getAttrId().toString() + "');");
                }

                operatorComboBox.setValues(displayValues);
                operatorComboBox.setInternalValues(internalValues);

                guiComponentArray.addGUIComponent(operatorComboBox);
            }

            logger.debug("OUT ==>  FindSimilarSearchDataUitility.getDataValue()");

            return guiComponentArray;
        }

        return null;
    }

    @Override
    public ColumnDescriptor getColumnDescriptor(String component_id, ModelContext mc) {
        ColumnDescriptor colDiscriptor = null;
        colDiscriptor = ColumnDescriptorFactory.getInstance().newIconColumn(mc.getDescriptor());
        colDiscriptor.setResizable(true);
        colDiscriptor.setDisplayHeaderLabel(true);
        colDiscriptor.setInlineStyle("text-align:left;");

        if (FindSimilarSearchAttributeTableBuilder.CSM_ATTRIBUTE_DISPLAY_NAME.equalsIgnoreCase(component_id)
                || FindSimilarSearchAttributeTableBuilder.SEARCH_UI_VALUE_COLUMN.equalsIgnoreCase(component_id)
                || FindSimilarSearchAttributeTableBuilder.SEARCH_UI_MODIFIER_COLUMN.equalsIgnoreCase(component_id)) {
            colDiscriptor.setWidth(200);
        } else {
            colDiscriptor.setWidth(100);
        }
        return colDiscriptor;
    }
}
