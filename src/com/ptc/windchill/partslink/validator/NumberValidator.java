package com.ptc.windchill.partslink.validator;

import java.text.ParseException;
import java.util.Locale;

import org.apache.log4j.Logger;

import wt.log4j.LogR;
import wt.util.WTContext;

import com.ptc.core.meta.common.NumericToolkit;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.partslink.PartslinkConstants;
import com.ptc.windchill.partslink.PartslinkConstants.AttributeDataType;
import com.ptc.windchill.partslink.model.FindSimilarBean;
import com.ptc.windchill.partslink.model.RefineBean;
import com.ptc.windchill.partslink.units.PartsLinkUnitsHelper;

/**
 * The Class NumberValidator.
 */
public class NumberValidator implements Validator {

    /** The Constant logger. */
    private static final Logger logger = LogR.getLogger(NumberValidator.class.getName());

    /*
     * (non-Javadoc)
     * 
     * @see com.ptc.windchill.partslink.validation.Validator#isValidInput(com.ptc.windchill.partslink.model.RefineBean,
     * com.ptc.netmarkets.util.beans.NmCommandBean)
     */
    @Override
    public boolean isValidInput(RefineBean refineBean, NmCommandBean commandBean) {
        boolean isDebugEnabled = logger.isDebugEnabled();
        boolean isValid = false;
        if (isDebugEnabled) {
            logger.debug("Refine Bean operator is : " + refineBean.getOperator());
            logger.debug("Refine Bean attribute unit is : " + refineBean.getAttrUnits());
        }
        if (PartslinkConstants.NumericOperators.RANGE.equals(refineBean.getOperator())) {
            if (isDebugEnabled) {
                logger.debug("Refine Bean high value is : " + refineBean.getRangeHi());
                logger.debug("Refine Bean low value is : " + refineBean.getRangeLow());
            }
            isValid = checkInput(refineBean.getRangeHi(), refineBean.getAttrUnits(), refineBean.getAttrDataType())
                    && checkInput(refineBean.getRangeLow(), refineBean.getAttrUnits(), refineBean.getAttrDataType());
        } else {
            if (isDebugEnabled) {
                logger.debug("Refine Bean high value is : " + refineBean.getRangeHi());
            }
            isValid = checkInput(refineBean.getValue(), refineBean.getAttrUnits(), refineBean.getAttrDataType());
        }
        if (isDebugEnabled) {
            logger.debug("The result of input validation is : " + isValid);
        }
        return isValid;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Checks if is numeric.
     * 
     * @param str
     *            the str
     * @return true, if is numeric
     */
    public static boolean isNumeric(String str, AttributeDataType dataType)
    {
        Locale locale = WTContext.getContext().getLocale();
        Number validNumber = null;

        if (str != null) {
            if (str.isEmpty()) {
                return true;
            }

            // Check through NumericToolkit if the given string is valid
            // For some locale like French number may contain characters in decimal separator
            switch (dataType) {
            case LONG:
            case FLOATING_POINT:
            case FLOATING_POINT_WITH_UNITS:
                try {
                    // NumericToolkit's toNumber method invalidates + sign and throws ParseException
                    str = str.charAt(0) == '+' ? str.substring(1, str.length()) : str;
                    validNumber = NumericToolkit.toNumber(str, false, locale);
                } catch (ParseException e) {
                    return false;
                }
                break;
            }

            if (validNumber != null)
                return true;

            try {
                if (dataType == AttributeDataType.LONG) {
                    Long.parseLong(str);
                } else {
                    Double.parseDouble(str);
                }
            } catch (NumberFormatException nfe)
            {
                return false;
            }
        }
        return true;
    }

    private boolean checkInput(String inputValue, String attrUnit, AttributeDataType dataType) {
        String value = inputValue.trim();
        if (isNumeric(value, dataType)) {
            return true;
        } else {
            String[] parsedInput = parseInputValue(value);
            if (parsedInput != null && parsedInput.length == 2) {
                if (isNumeric(parsedInput[0], dataType) && PartsLinkUnitsHelper.isValidUnit(parsedInput[1])
                        && PartsLinkUnitsHelper.isCompatibleUnit(parsedInput[1], attrUnit))
                {
                    return true;
                }
            }
            else {
                return false;
            }
        }
        return false;
    }

    private String[] parseInputValue(String inputValue) {
        boolean isDebugEnabled = logger.isDebugEnabled();
        String[] returnValues = null;
        if (isDebugEnabled) {
            logger.debug("parseInputValue :: Input value is  : " + inputValue);
        }
        if (inputValue != null && !inputValue.trim().isEmpty()) {
            int index = inputValue.indexOf(" ");
            if (index != -1) {
                returnValues = inputValue.split(" ");
            }
        }
        if (isDebugEnabled) {
            logger.debug("parseInputValue :: Parsed values are  : " + returnValues);
        }
        return returnValues;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ptc.windchill.partslink.validation.Validator#isValidInput(com.ptc.windchill.partslink.model.RefineBean,
     * com.ptc.netmarkets.util.beans.NmCommandBean)
     */
    @Override
    public boolean isValidInput(FindSimilarBean findSimilarBean, NmCommandBean commandBean) {

        boolean isDebugEnabled = logger.isDebugEnabled();

        logger.debug("IN ==> NumberValidator.isValidInput() : For Find Similar ");

        boolean isValid = true;

        if ((findSimilarBean.getModifierValue() != null) && (!findSimilarBean.getModifierValue().isEmpty())) {
            isValid = isNumeric(findSimilarBean.getModifierValue(), findSimilarBean.getAttrDataType());
            if (isValid == false)
                return isValid;
        }
        if ((findSimilarBean.getValue() != null) && (!findSimilarBean.getValue().isEmpty())) {
            isValid = checkInput(findSimilarBean.getValue(), findSimilarBean.getAttrUnits(),
                    findSimilarBean.getAttrDataType());
            if (isValid == false)
                return isValid;
        }

        logger.debug("IN ==> NumberValidator.isValidInput() : For Find Similar");

        return isValid;
    }

}
