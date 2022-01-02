package com.ptc.windchill.partslink.fqp;

import java.text.ParseException;

import org.apache.log4j.Logger;

import wt.log4j.LogR;
import wt.util.WTContext;
import wt.util.WTException;

import com.ptc.core.meta.common.NumericToolkit;
import com.ptc.windchill.partslink.PartslinkConstants;
import com.ptc.windchill.partslink.units.RealNumberWithUnitsHelper;

/**
 * The Class FloatingPointFilterQueryProducer.
 */
public class FloatingPointFilterQueryProducer extends AbstractFilterQueryProducer {

    /** The Constant logger. */
    private static final Logger logger = LogR.getLogger(FloatingPointFilterQueryProducer.class.getName());

    /*
     * (non-Javadoc)
     *
     * @see com.ptc.windchill.partslink.fqp.FilterQueryProducer#produceFilterQuery(com.ptc.windchill.partslink.model.
     * RefineSearchBean)
     */
    @Override
    public String produceFilterQuery(FQPBean bean) throws WTException, ParseException {

        boolean isDebugEnabled = logger.isDebugEnabled();

        String result = null;
        StringBuilder sb = new StringBuilder();
        String value = null;
        String operator = bean.getOperator();

        if (isDebugEnabled) {
            logger.debug("Operator selected for refine bean is : " + operator);
        }
        switch (operator) {

        case PartslinkConstants.NumericOperators.RANGE:
            String lowValue = bean.getRangeLow();
            String hiValue = bean.getRangeHi();

            if (lowValue != null && !"".equals(lowValue.trim()) && hiValue != null && !"".equals(hiValue.trim())) {
                String inputUnitLow = RealNumberWithUnitsHelper.extractUnit(lowValue);
                if (inputUnitLow == null) {
                    inputUnitLow = bean.getAttrUnits();
                }
                String inputUnitHigh = RealNumberWithUnitsHelper.extractUnit(hiValue);
                if (inputUnitHigh == null) {
                    inputUnitHigh = bean.getAttrUnits();
                }
                String lowerValue = RealNumberWithUnitsHelper.extractStringValue(lowValue);
                String upperValue = RealNumberWithUnitsHelper.extractStringValue(hiValue);

                if (lowValue != null && !"".equals(lowValue.trim()))
                {
                    lowerValue = lowerValue.charAt(0) == '+' ? lowerValue.substring(1, lowerValue.length()) : lowerValue;
                    lowValue = String.valueOf(NumericToolkit.toNumber(lowerValue, false, WTContext.getContext().getLocale()));
                }
                if (hiValue != null && !"".equals(hiValue.trim()))
                {
                    upperValue = upperValue.charAt(0) == '+' ? upperValue.substring(1, upperValue.length()) : upperValue;
                    hiValue = String.valueOf(NumericToolkit.toNumber(upperValue, false, WTContext.getContext().getLocale()));
                }

                // data received here is locale specific, so using english translated values.
                Double lower = Double.parseDouble(lowValue);
                Double upper = Double.parseDouble(hiValue);

                Double toleranceAdjustLow = RealNumberWithUnitsHelper.calculateTolerance(lowerValue);
                Double toleranceAdjustHigh = RealNumberWithUnitsHelper.calculateTolerance(upperValue);

                if (isDebugEnabled) {
                    logger.debug("Range Low value for refine bean is  : " + lowValue);
                    logger.debug("Range High value for refine bean is : " + hiValue);
                    logger.debug("Range Low value : " + lower + " toleranceAdjustLow: " + toleranceAdjustLow);
                    logger.debug("Range High value : " + upper + " toleranceAdjustHigh: " + toleranceAdjustHigh);
                }

                lower = lower - toleranceAdjustLow;
                upper = upper + toleranceAdjustHigh;

                Double lowerValueInDouble = RealNumberWithUnitsHelper.convertToBaseUnitValue(lower, inputUnitLow);
                Double upperValueInDouble = RealNumberWithUnitsHelper.convertToBaseUnitValue(upper, inputUnitHigh);

                if (isDebugEnabled) {
                    logger.debug("Range Low value after substracting tolerance for refine bean is  : " + lowerValueInDouble);
                    logger.debug("Range High value after adding tolerance for refine bean is : " + upperValueInDouble);
                }
                generateTerm(sb, bean.getAttrId(), lowerValueInDouble, upperValueInDouble);
            }

            else if (lowValue != null && !"".equals(lowValue.trim())) {
                generateTermBasedOnCriteria(bean, lowValue, sb, PartslinkConstants.NumericOperators.GREATER_THAN_EQUAL);
            }

            else if (hiValue != null && !"".equals(hiValue.trim())) {
                generateTermBasedOnCriteria(bean, hiValue, sb, PartslinkConstants.NumericOperators.LESS_THAN_EQUAL);
            }
            break;

        case PartslinkConstants.NumericOperators.GREATER_THAN_EQUAL:
            value = bean.getValue();
            if (isDebugEnabled) {
                logger.debug("GEQ Value for refine bean is  : " + value);
            }
            if (value != null && !"".equals(value.trim())) {
                generateTermBasedOnCriteria(bean, value, sb, PartslinkConstants.NumericOperators.GREATER_THAN_EQUAL);
            }
            break;

        case PartslinkConstants.NumericOperators.LESS_THAN_EQUAL:
            value = bean.getValue();
            if (isDebugEnabled) {
                logger.debug("LEQ Value for refine bean is  : " + value);
            }
            if (value != null && !"".equals(value.trim())) {
                generateTermBasedOnCriteria(bean, value, sb, PartslinkConstants.NumericOperators.LESS_THAN_EQUAL);
            }
            break;

        case PartslinkConstants.NumericOperators.EQUAL:
            value = bean.getValue();
            if (isDebugEnabled) {
                logger.debug("EQ Value for refine bean is  : " + value);
            }
            if (value != null && !"".equals(value.trim())) {
                generateTermBasedOnCriteria(bean, value, sb, PartslinkConstants.NumericOperators.EQUAL);
            }
            break;
            
             //修改OOTB逻辑，支持非条件，在solr中非符号为-
           case "-":
        	   logger.debug(" operator is -");
        	   value = bean.getValue();
               if (isDebugEnabled) {
                   logger.debug("EQ Value for refine bean is  : " + value);
               }
               if (value != null && !"".equals(value.trim())) {
                   generateTermBasedOnCriteria(bean, value, sb, "-");
               }
               break;
        	   
        }
        result = sb.length() > 0 ? sb.toString() : null;
        if (isDebugEnabled) {
            logger.debug("Filter query for floating point with units for attribute : " + bean.getAttrId() + " is : " + result);
        }
        return result;
    }

    /**
     *
     * @param bean
     * @param value
     * @param sb
     * @param criteria
     * @throws WTException
     * @throws ParseException
     */
    private void generateTermBasedOnCriteria(FQPBean bean, String value, StringBuilder sb, String criteria) throws WTException,
            ParseException {

        boolean isDebugEnabled = logger.isDebugEnabled();

        Object lowerValue = 0.0;
        Object upperValue = 0.0;

        String inputUnit = RealNumberWithUnitsHelper.extractUnit(value);
        value = RealNumberWithUnitsHelper.extractStringValue(value);
        String userLocaleValue = value;
        
        if (inputUnit == null) {
            inputUnit = bean.getAttrUnits();
        }

        if (bean.getValue() != null && !"".equals(bean.getValue().trim()))
        {
            userLocaleValue = value.charAt(0) == '+' ? value.substring(1, value.length()) : value;
            value = String.valueOf(NumericToolkit.toNumber(userLocaleValue, false, WTContext.getContext().getLocale()));
        }

        Double dValue = Double.parseDouble(value);
        Double toleranceAdjust = RealNumberWithUnitsHelper.calculateTolerance(userLocaleValue);

        if (criteria.equals(PartslinkConstants.NumericOperators.EQUAL)) {
            if (isDebugEnabled) {
                logger.debug("EQ value : " + dValue + " toleranceAdjust: " + toleranceAdjust);
            }
            Double lower = dValue - toleranceAdjust;
            Double upper = dValue + toleranceAdjust;
            lowerValue = RealNumberWithUnitsHelper.convertToBaseUnitValue(lower, inputUnit);
            upperValue = RealNumberWithUnitsHelper.convertToBaseUnitValue(upper, inputUnit);
            if (isDebugEnabled) {
                logger.debug("EQ Low value after substracting tolerance for refine bean is  : " + lowerValue);
                logger.debug("EQ High value after adding tolerance for refine bean is : " + upperValue);
            }
        }
        else if (criteria.equals(PartslinkConstants.NumericOperators.GREATER_THAN_EQUAL)) {
            if (isDebugEnabled) {
                logger.debug("GT value : " + dValue + " toleranceAdjust: " + toleranceAdjust);
            }
            Double lower = dValue - toleranceAdjust;
            lowerValue = RealNumberWithUnitsHelper.convertToBaseUnitValue(lower, inputUnit);
            upperValue = "*";
            if (isDebugEnabled) {
                logger.debug("GT Low value after substracting tolerance for refine bean is  : " + lowerValue);
                logger.debug("Gt High value after adding tolerance for refine bean is : " + upperValue);
            }
        }
        else if (criteria.equals(PartslinkConstants.NumericOperators.LESS_THAN_EQUAL)) {
            if (isDebugEnabled) {
                logger.debug("LT value : " + dValue + " toleranceAdjust: " + toleranceAdjust);
            }
            lowerValue = "*";
            Double upper = dValue + toleranceAdjust;
            upperValue = RealNumberWithUnitsHelper.convertToBaseUnitValue(upper, inputUnit);
            if (isDebugEnabled) {
                logger.debug("LT Low value after substracting tolerance for refine bean is  : " + lowerValue);
                logger.debug("LT High value after adding tolerance for refine bean is : " + upperValue);
            }
        }else if("-".equals(criteria)){
        	logger.debug("operator is -");
        	Double lower = dValue - toleranceAdjust;
            Double upper = dValue + toleranceAdjust;
            lowerValue = RealNumberWithUnitsHelper.convertToBaseUnitValue(lower, inputUnit);
            upperValue = RealNumberWithUnitsHelper.convertToBaseUnitValue(upper, inputUnit);
            if (isDebugEnabled) {
                logger.debug("EQ Low value after substracting tolerance for refine bean is  : " + lowerValue);
                logger.debug("EQ High value after adding tolerance for refine bean is : " + upperValue);
            }
        	generateTerm(sb, "-"+bean.getAttrId(), lowerValue, upperValue);
        	return;
        }
        generateTerm(sb, bean.getAttrId(), lowerValue, upperValue);
    }

    /**
     *
     * @param sb
     * @param attrId
     * @param lower
     * @param upper
     */
    private void generateTerm(StringBuilder sb, String attrId, Object lower, Object upper) {
        sb.append(attrId).append(":").append("[").append(lower).append(" TO ").append(upper).append("]");
    }
}
