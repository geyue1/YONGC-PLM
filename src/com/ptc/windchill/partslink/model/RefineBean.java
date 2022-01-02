package com.ptc.windchill.partslink.model;

import java.util.ArrayList;

import com.ptc.core.lwc.common.view.AttributeDefinitionReadView;
import com.ptc.windchill.partslink.PartslinkConstants.AttributeDataType;
import com.ptc.windchill.partslink.fqp.FQPBean;

/**
 * The Class RefineSearchBean.
 */
public class RefineBean implements FQPBean {

    /** The attr def read view. */
    private AttributeDefinitionReadView attrDefReadView;

    /** The attr id. */
    private String attrId;

    /** The attr display name. */
    private String attrDisplayName;

    /** The attr data type. */
    private AttributeDataType attrDataType;

    /** The attr units. */
    private String attrUnits;

    /** The attr facets. */
    private String attrFacets;

    /** The desc url. */
    private String descUrl;

    /** The range value. */
    private String rangeValue;

    /** The value. */
    private String value = "";

    /** The range hi. */
    private String rangeHi = "";

    /** The range low. */
    private String rangeLow = "";

    /** The operator. */
    private String operator = "";

    /** Flag to indicate whether or not to append implicit wild-card with attribute value */
    private boolean searchWithImplicitWC = false;

    /** flag for error. */
    private boolean error = false;

    private boolean isEnabled = true;

    /** internal values for a combo box.**/
    private ArrayList<String> operatorColumnValues = new ArrayList<String>();
    
    /** display values for a combo box.**/
    private ArrayList<String> operatorColumnDisplayValues = new ArrayList<String>();

    private boolean isNullValueBean = false;
    
    /**internal value for enumerated attributes.**/
    private String internalValue = "";
    
    public String getInternalValue() {
        return internalValue;
    }

    public void setInternalValue(String internalValue) {
        this.internalValue = internalValue;
    }

    public boolean isNullValueBean() {
        return isNullValueBean;
    }

    public void setNullValueBean(boolean isNullValueBean) {
        this.isNullValueBean = isNullValueBean;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public ArrayList<String> getOperatorColumnValues() {
        return operatorColumnValues;
    }

    public void setOperatorColumnValues(ArrayList<String> operatorColumnValues) {
        this.operatorColumnValues = operatorColumnValues;
    }

    public ArrayList<String> getOperatorColumnDisplayValues() {
        return operatorColumnDisplayValues;
    }

    public void setOperatorColumnDisplayValues(ArrayList<String> operatorColumnDisplayValues) {
        this.operatorColumnDisplayValues = operatorColumnDisplayValues;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the attr data type.
     * 
     * @return the attr data type
     */
    public AttributeDataType getAttrDataType() {
        return attrDataType;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the attr data type.
     * 
     * @param attrDataType
     *            the new attr data type
     */
    public void setAttrDataType(AttributeDataType attrDataType) {
        this.attrDataType = attrDataType;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the range value.
     * 
     * @return the range value
     */
    public String getRangeValue() {
        return rangeValue;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the range value.
     * 
     * @param rangeValue
     *            the new range value
     */
    public void setRangeValue(String rangeValue) {
        this.rangeValue = rangeValue;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the attr def read view.
     * 
     * @return the attr def read view
     */
    public AttributeDefinitionReadView getAttrDefReadView() {
        return attrDefReadView;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the attr def read view.
     * 
     * @param attrDefReadView
     *            the new attr def read view
     */
    public void setAttrDefReadView(AttributeDefinitionReadView attrDefReadView) {
        this.attrDefReadView = attrDefReadView;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the attr id.
     * 
     * @return the attr id
     */
    public String getAttrId() {
        return attrId;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the attr id.
     * 
     * @param attributeId
     *            the new attr id
     */
    public void setAttrId(String attributeId) {
        this.attrId = attributeId;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the desc url.
     * 
     * @return the desc url
     */
    public String getDescUrl() {
        return descUrl;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the desc url.
     * 
     * @param descUrl
     *            the new desc url
     */
    public void setDescUrl(String descUrl) {
        this.descUrl = descUrl;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the attr display name.
     * 
     * @return the attr display name
     */
    public String getAttrDisplayName() {
        return attrDisplayName;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the attr display name.
     * 
     * @param attrDisplayName
     *            the new attr display name
     */
    public void setAttrDisplayName(String attrDisplayName) {
        this.attrDisplayName = attrDisplayName;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the attr units.
     * 
     * @return the attr units
     */
    public String getAttrUnits() {
        return attrUnits;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the attr units.
     * 
     * @param attrUnits
     *            the new attr units
     */
    public void setAttrUnits(String attrUnits) {
        if (attrUnits != null)
            this.attrUnits = attrUnits;
        else
            this.attrUnits = "";

    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the attr faucets.
     * 
     * @return the attr faucets
     */
    public String getAttrFaucets() {
        return attrFacets;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the attr facets.
     * 
     * @param attrFacets
     *            the new attr facets
     */
    public void setAttrFacets(String attrFacets) {
        this.attrFacets = attrFacets;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the value.
     * 
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the value.
     * 
     * @param value
     *            the new value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the range hi.
     * 
     * @return the range hi
     */
    public String getRangeHi() {
        return rangeHi;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the range hi.
     * 
     * @param rangeHi
     *            the new range hi
     */
    public void setRangeHi(String rangeHi) {
        this.rangeHi = rangeHi;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the range low.
     * 
     * @return the range low
     */
    public String getRangeLow() {
        return rangeLow;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the range low.
     * 
     * @param rangeLow
     *            the new range low
     */
    public void setRangeLow(String rangeLow) {
        this.rangeLow = rangeLow;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the operator.
     * 
     * @return the operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the operator.
     * 
     * @param operator
     *            the new operator
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Returns flag indicating whether or not to append implicit wild-card with attribute value
     * 
     * @return - true/false to/not to append implicit wild-card with attribute value
     */
    public boolean isSearchWithImplicitWC() {
        return searchWithImplicitWC;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets flag indicating whether or not to append implicit wild-card with attribute value
     * 
     * @param searchWithImplicitWC
     *            - true/false to/not to append implicit wild-card with attribute value
     */
    public void setSearchWithImplicitWC(boolean searchWithImplicitWC) {
        this.searchWithImplicitWC = searchWithImplicitWC;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Checks if is error.
     * 
     * @return true, if is error
     */
    public boolean isError() {
        return error;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the error.
     * 
     * @param error
     *            the new error
     */
    public void setError(boolean error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "RefineBean [attrDisplayName=" + attrDisplayName + ", attrID=" + attrId + ", attrDataType="
                + attrDataType + ", attrUnits=" + attrUnits + ", rangeHi=" + rangeHi + ", rangeLow=" + rangeLow
                + ", value=" + value + ", operatorColumnValues=" + operatorColumnValues + ", operatorColumnDisplayValues=" + operatorColumnDisplayValues + ", operator=" + operator
                + "]";
    }
}
