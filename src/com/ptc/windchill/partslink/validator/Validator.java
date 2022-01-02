package com.ptc.windchill.partslink.validator;


import com.ptc.windchill.partslink.model.FindSimilarBean;
import com.ptc.windchill.partslink.model.RefineBean;
import com.ptc.netmarkets.util.beans.NmCommandBean;

/**
 * The Interface Validator.
 */
public interface Validator {

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Checks if is valid input.
	 *
	 * @param refineBean - the refine bean
	 * @param commandBean - the command bean
	 * @return true, if is valid input
	 */
	public boolean isValidInput(RefineBean refineBean, NmCommandBean commandBean);

	
	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Checks if is valid input.
	 *
	 * @param findSimialrBean - the refine bean
	 * @param commandBean - the command bean
	 * @return true, if is valid input
	 */
	public boolean isValidInput(FindSimilarBean findSimilarBean, NmCommandBean commandBean);

}
