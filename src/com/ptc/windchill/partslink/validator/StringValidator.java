package com.ptc.windchill.partslink.validator;

import java.util.HashMap;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import wt.log4j.LogR;
import wt.session.SessionHelper;
import wt.util.InstalledProperties;
import wt.util.WTException;

import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.partslink.PartslinkConstants;
import com.ptc.windchill.partslink.model.FindSimilarBean;
import com.ptc.windchill.partslink.model.RefineBean;
import com.ptc.windchill.partslink.utils.PartsLinkUtils;

/**
 * Validator class for string datatype.
 *
 */
public class StringValidator implements Validator {

	/** The Constant logger. */
	private static final Logger logger = LogR.getLogger(StringValidator.class.getName());

	@Override
	public boolean isValidInput(RefineBean refineBean, NmCommandBean commandBean) {

		if (logger.isDebugEnabled()) {
			logger.debug("Entered StringValidator.isValidInput().");
		}

		Locale locale = null;
		try {
			locale = commandBean.getLocale();
			if (locale == null) {
				locale = SessionHelper.getLocale();
			}
		} catch (WTException e) {
			e.printStackTrace();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Attribute Id :: " + refineBean.getAttrId());
			logger.debug("Attribute Display Name :: " + refineBean.getAttrDisplayName());
			logger.debug("value :: " + refineBean.getValue());
		}

		boolean isValid = false;
		if (InstalledProperties.isInstalled(InstalledProperties.SUMA) && refineBean.getAttrId().equals(PartslinkConstants.IndexFields.SOURCING_STATUS)) {
			String value = refineBean.getValue();
			if (value != null && !value.trim().isEmpty()) {
				StringTokenizer tokenizer = new StringTokenizer(value, "|");

				while (tokenizer.hasMoreTokens()) {
					String token = tokenizer.nextToken();
					HashMap<String, String> map = PartsLinkUtils.getSourcingStatusMap(locale);

					Set<String> keys = map.keySet();
					for (String key : keys) {
						String temp = map.get(key);
						if (temp != null && temp.equalsIgnoreCase(token.trim())) {
							isValid = true;
							break;
						}
					}
				}

			} else {
				isValid = true;
			}
		} else {
			isValid = true;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isValid ::: " + isValid);
			logger.debug("Exiting StringValidator.isValidInput().");
		}

		return isValid;
	}

	@Override
	public boolean isValidInput(FindSimilarBean findSimilarBean, NmCommandBean commandBean) {
		return true;
	}

}
