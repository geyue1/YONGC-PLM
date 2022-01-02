package com.ptc.windchill.partslink.validator;

import com.ptc.windchill.partslink.PartslinkConstants.AttributeDataType;

/**
 * A factory for creating InputValidation objects.
 */
public class ValidatorFactory {

	/**
	 * <BR>
	 * <BR>
	 * <B>Supported API: </B>false
	 * 
	 * Gets the validator.
	 * 
	 * @param dataType
	 *            the data type
	 * @return the validator
	 */
	public static Validator getValidator(AttributeDataType dataType) {
		Validator result = new NoOpValidator();

		switch (dataType) {
		case LONG:
		case FLOATING_POINT:
		case FLOATING_POINT_WITH_UNITS:
			result = new NumberValidator();
			break;

		case TIMESTAMP:
			result = new TimeStampValidator();
			break;

		case STRING:
			result = new StringValidator();

			break;
		case BOOLEAN:
		case REFERENCE:
		case HYPERLINK:
			result = new NoOpValidator();
			break;
		}

		return result;
	}

}
