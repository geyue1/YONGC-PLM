package com.ptc.windchill.partslink.validator;

import com.ptc.windchill.partslink.model.FindSimilarBean;
import com.ptc.windchill.partslink.model.RefineBean;
import com.ptc.netmarkets.util.beans.NmCommandBean;

/**
 * The Class NoOpValidator.
 */
public class NoOpValidator implements Validator {

	/* (non-Javadoc)
	 * @see com.ptc.windchill.partslink.validation.Validator#isValidInput(com.ptc.windchill.partslink.model.RefineBean)
	 */
	@Override
	public boolean isValidInput(RefineBean refineBean, NmCommandBean commandBean) { 		
		return true;
	}

	@Override
	public boolean isValidInput(FindSimilarBean findSimilarBean, NmCommandBean commandBean) {
		return true;
	}
	
}
