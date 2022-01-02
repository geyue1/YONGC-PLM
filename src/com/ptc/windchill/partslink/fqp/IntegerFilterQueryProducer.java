package com.ptc.windchill.partslink.fqp;

import java.text.ParseException;

import org.apache.log4j.Logger;

import wt.indexsearch.IndexSearchUtils;
import wt.log4j.LogR;
import wt.util.WTException;

import com.ptc.windchill.partslink.PartslinkConstants;

public class IntegerFilterQueryProducer extends AbstractFilterQueryProducer {

	private static final Logger logger = LogR.getLogger(IntegerFilterQueryProducer.class.getName());

	@Override
	public String produceFilterQuery(FQPBean bean) throws WTException, ParseException {

		if (logger.isDebugEnabled()) {
			logger.debug("Entered IntegerFilterQueryProducer.produceFilterQuery.");
		}

		String result = null;
		String value = bean.getValue();
		String attrId = bean.getAttrId();
		String operator = bean.getOperator();
		StringBuilder sb = new StringBuilder();

		if (logger.isDebugEnabled()) {
			logger.debug("AttributeId :: " + attrId);
			logger.debug("Value :: " + value);
			logger.debug("operator :: " + operator);
		}

		switch (operator) {

		case PartslinkConstants.NumericOperators.RANGE:
			String lowValue = bean.getRangeLow();
			String highValue = bean.getRangeHi();

			if (lowValue != null && !lowValue.trim().isEmpty()
					&& highValue != null && !highValue.trim().isEmpty()) {
				generateQuery(sb, attrId, lowValue, highValue);
				result = sb.toString();
			}

			break;

		case PartslinkConstants.NumericOperators.GREATER_THAN_EQUAL:

			if (value != null && !value.trim().isEmpty()) {
				generateQuery(sb, attrId, value, "*");
				result = sb.toString();
			}
			break;

		case PartslinkConstants.NumericOperators.LESS_THAN_EQUAL:
			if (value != null && !value.trim().isEmpty()) {
				generateQuery(sb, attrId, "*", value);
				result = sb.toString();
			}
			break;

		case PartslinkConstants.NumericOperators.EQUAL:
			if (value != null && !value.trim().isEmpty()) {
				sb.append(bean.getAttrId()).append(":(");
				sb.append(IndexSearchUtils.escapeLuceneChars(value)).append(")");
				result = sb.toString();
			}
			break;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("result :: " + result);
			logger.debug("Exiting IntegerFilterQueryProducer.produceFilterQuery.");
		}

		return result;
	}

	/**
	 * Generate Query.
	 * 
	 * @param sb
	 *            - the StringBuilder
	 * @param attrId
	 *            - the attribute id
	 * @param lower
	 *            - the lower value
	 * @param upper
	 *            - the upper value
	 */
	private void generateQuery(StringBuilder sb, String attrId, String lower,
			String upper) {
		lower = IndexSearchUtils.escapeLuceneChars(lower);
		upper = IndexSearchUtils.escapeLuceneChars(upper);
		sb.append(attrId).append(":").append("[").append(lower).append(" TO ").append(upper).append("]");
	}

}
