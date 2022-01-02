package com.ptc.windchill.partslink.validator;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import wt.log4j.LogR;
import wt.session.SessionHelper;
import wt.util.WTStandardDateFormat;

import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.enterprise.search.server.SearchHelper;
import com.ptc.windchill.partslink.PartslinkConstants;
import com.ptc.windchill.partslink.model.FindSimilarBean;
import com.ptc.windchill.partslink.model.RefineBean;

public class TimeStampValidator implements Validator {

	/** The Constant logger. */
	private static final Logger logger = LogR.getLogger(TimeStampValidator.class.getName());

	@Override
	public boolean isValidInput(RefineBean refineBean, NmCommandBean commandBean) {
		if (logger.isDebugEnabled()) {
			logger.debug("Entered TimeStampValidator.isValidInput().");
		}

		String value = refineBean.getValue();
		String rangeLow = refineBean.getRangeLow();
		String rangeHi = refineBean.getRangeHi();

		if (logger.isDebugEnabled()) {
			logger.debug("Attribute Id :: " + refineBean.getAttrId());
			logger.debug("Attribute Display Name :: " + refineBean.getAttrDisplayName());
			logger.debug("value :: " + value);
			logger.debug("rangeLow :: " + rangeLow);
			logger.debug("rangeHi :: " + rangeHi);
		}

		boolean isValid = true;

		try {
			TimeZone localTimeZone = SearchHelper.getLocalTimeZone();

			Locale locale = commandBean.getLocale();
			if (locale == null) {
				locale = SessionHelper.getLocale();
			}

			// String pattern = "yyyy-MM-dd";
			String pattern_JCA = ResourceBundle.getBundle("com.ptc.core.ui.componentRB", locale).getString("CALENDAR_PARSE_DATE");
			
			if(refineBean.getOperator() == PartslinkConstants.NumericOperators.EQUAL) {
				// validate value for EQUAL operator
				if (value != null && !value.trim().isEmpty()) {
					WTStandardDateFormat.parse(value, pattern_JCA, locale, localTimeZone);
				}				
			} else if(refineBean.getOperator() == PartslinkConstants.NumericOperators.RANGE) {
				// validate rangeLow and rangeHi for RANGE operator
				if (rangeLow != null && !rangeLow.trim().isEmpty()) {
					WTStandardDateFormat.parse(rangeLow, pattern_JCA, locale, localTimeZone);
				}

				if (rangeHi != null && !rangeHi.trim().isEmpty()) {
					WTStandardDateFormat.parse(rangeHi, pattern_JCA, locale, localTimeZone);
				}				
			}

		} catch (Exception e) {
			isValid = false;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isValid :: " + isValid);
			logger.debug("Exiting TimeStampValidator.isValidInput().");
		}

		return isValid;
	}

	
	@Override
	public boolean isValidInput(FindSimilarBean findSimilarBean, NmCommandBean commandBean) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Entered TimeStampValidator.isValidInput().");
		}
		
		String value = findSimilarBean.getValue();
		
		if (logger.isDebugEnabled()) {
			logger.debug("Attribute Id :: " + findSimilarBean.getAttrId());
			logger.debug("Attribute Display Name :: " + findSimilarBean.getAttrDisplayName());
			logger.debug("value :: " + value);
		}

		boolean isValid = true;

		try {
			TimeZone localTimeZone = SearchHelper.getLocalTimeZone();

			Locale locale = commandBean.getLocale();
			if (locale == null) {
				locale = SessionHelper.getLocale();
			}
			
			// String pattern = "yyyy-MM-dd";
			String pattern_JCA = ResourceBundle.getBundle("com.ptc.core.ui.componentRB", locale).getString("CALENDAR_PARSE_DATE");

			if (value != null && !value.trim().isEmpty()) {
				WTStandardDateFormat.parse(value, pattern_JCA, locale, localTimeZone);
			}

			
		} catch (Exception e) {
			isValid = false;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isValid :: " + isValid);
			logger.debug("Exiting FindSImialr TimeStampValidator.isValidInput().");
		}

		return isValid;

	}

}
