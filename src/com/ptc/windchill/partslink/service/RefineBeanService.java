package com.ptc.windchill.partslink.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.SortedSet;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import wt.facade.suma.sumaResource;
import wt.log4j.LogR;
import wt.session.SessionHelper;
import wt.util.HTMLEncoder;
import wt.util.InstalledProperties;
import wt.util.NumericToolkitUtil;
import wt.util.WTException;
import wt.util.WTMessage;

import com.ptc.core.lwc.client.util.PropertyDefinitionHelper;
import com.ptc.core.lwc.common.view.AttributeDefinitionReadView;
import com.ptc.core.lwc.common.view.ConstraintDefinitionReadView;
import com.ptc.core.lwc.common.view.ConstraintRuleDefinitionReadView;
import com.ptc.core.lwc.common.view.EnumerationEntryReadView;
import com.ptc.core.lwc.common.view.PropertyHolderHelper;
import com.ptc.core.lwc.common.view.PropertyValueReadView;
import com.ptc.core.lwc.common.view.TypeDefinitionReadView;
import com.ptc.core.lwc.server.LWCBasicConstraint;
import com.ptc.core.lwc.server.LWCEnumerationBasedConstraint;
import com.ptc.core.meta.common.NumericToolkit;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.csm.client.helpers.CSMTypeDefHelper;
import com.ptc.windchill.partslink.PartslinkConstants;
import com.ptc.windchill.partslink.PartslinkConstants.AttributeDataType;
import com.ptc.windchill.partslink.facet.Facet;
import com.ptc.windchill.partslink.facet.StatsInfo;
import com.ptc.windchill.partslink.model.PartslinkPropertyModel;
import com.ptc.windchill.partslink.model.RefineBean;
import com.ptc.windchill.partslink.units.PartsLinkUnitsHelper;
import com.ptc.windchill.partslink.units.RealNumberWithUnitsHelper;
import com.ptc.windchill.partslink.utils.PartsLinkUtils;
import com.ptc.windchill.partslink.validator.Validator;
import com.ptc.windchill.partslink.validator.ValidatorFactory;

/**
 * The Class RefineBeanService.
 */
public class RefineBeanService {

	/** The Constant logger. */
	private static final Logger logger = LogR.getLogger(RefineBeanService.class.getName());

	/**
	 * <BR>
	 * <BR>
	 * <B>Supported API: </B>false
	 * 
	 * Creates the refine bean.
	 * 
	 * @param attributeDefinitionReadView - the attribute definition read view
	 * @param commandBean - the command bean
	 * @param attributeId - the Attribute id that needs to be set for the refine bean. If null, attribute id would be generated internally.
	 * @param populateValues - flag to indicate whether to populate values for the beans.
	 * @return the refine bean
	 * @throws WTException
	 */
	public static RefineBean createAndValidateRefineBean (
			AttributeDefinitionReadView attributeDefinitionReadView, NmCommandBean commandBean, String attributeId, boolean populateValues)
					throws WTException {
		boolean isDebugEnabled = logger.isDebugEnabled();

		Locale locale = commandBean.getLocale();
		if (locale == null) {
			locale = SessionHelper.getLocale();
		}

		RefineBean refineBean = new RefineBean();
		refineBean.setAttrDefReadView(attributeDefinitionReadView);
		String attrDataType = attributeDefinitionReadView.getDatatype().getName();
		refineBean.setAttrDataType(PartsLinkUtils.getAttributeType(attrDataType));
		refineBean.setAttrDisplayName(HTMLEncoder.encodeForHTMLContent(PropertyHolderHelper.getDisplayName(
				attributeDefinitionReadView, locale)));
		refineBean.setAttrFacets("");

		String descUrl = null;
		try {
			if (attributeDefinitionReadView.getPropertyValueByName("description") != null) {
				String descValue = attributeDefinitionReadView.getPropertyValueByName("description").getValueAsString(locale, true);

				if(descValue==null){
					descValue = "";
				}

				descUrl = "netmarkets/jsp/partslink/propertyHelp.jsp?description=" + URLEncoder.encode(descValue, "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getLocalizedMessage(), e);
		}

		if (descUrl != null) {
			refineBean.setDescUrl(descUrl);
		} else {
			refineBean.setDescUrl("");
		}

		refineBean.setAttrUnits(attributeDefinitionReadView.getDisplayUnits(PropertyDefinitionHelper.getMeasurementSystem()));
		String attrId = null;
		if (attributeId != null && !attributeId.isEmpty()) {
			attrId = attributeId;
			refineBean.setAttrId(attrId);
		} else {
			attrId = PartsLinkUtils.generateSolrAttributeID(attributeDefinitionReadView);
			refineBean.setAttrId(attrId);
		}

		if (isDebugEnabled) {
			logger.debug("attrId = " + attrId + ", displayName = " + refineBean.getAttrDisplayName() + ", attrDataType = " + attrDataType);
		}

		HashMap text = commandBean.getText();
		HashMap combo = commandBean.getComboBox();
		String operator = null;

		if (populateValues) {
			// read values from command bean
			String value = (String) text.get(attrId);
			String internalValue = (String) text.get(attrId + "_internalValues");

			String rangeHi = (String) text.get(PartslinkConstants.RefineSearch.ID_PREFIX_RANGE_HI + attrId);
			String rangeLow = (String) text.get(PartslinkConstants.RefineSearch.ID_PREFIX_RANGE_LOW + attrId);

			// set user input values
			if (value != null) {
				refineBean.setValue(value);
			}

			if (internalValue != null && internalValue.trim().length() > 0)
			{
			    refineBean.setInternalValue(internalValue);
			}
			
			if (rangeHi != null) {
				refineBean.setRangeHi(rangeHi);
			}

			if (rangeLow != null) {
				refineBean.setRangeLow(rangeLow);
			}

			ArrayList operatorList = ((ArrayList) combo.get(PartslinkConstants.RefineSearch.ID_PREFIX_OPERATOR + attrId));
			if (operatorList != null) {
				operator = (String) operatorList.get(0);
			}

			if (operator != null) {
				refineBean.setOperator(operator);
			}

			if (isDebugEnabled) {
				logger.debug("attrId = " + attrId + ", value = " + value + ", rangeHi = " + rangeHi + ", rangeLow = " + rangeLow + ", operator = " + operator);
			}
		}

		if (operator != null) {
			refineBean.setOperator(operator);
		} else {
			if (refineBean.getAttrDataType() == AttributeDataType.LONG
					|| refineBean.getAttrDataType() == AttributeDataType.FLOATING_POINT
					|| refineBean.getAttrDataType() == AttributeDataType.FLOATING_POINT_WITH_UNITS
					|| refineBean.getAttrDataType() == AttributeDataType.TIMESTAMP) {
				refineBean.setOperator(PartslinkConstants.NumericOperators.EQUAL);
			}
		}

		// validate bean
		validateBean(refineBean, commandBean);

		return refineBean;
	}

	/**
	 * Method to create refine bean for "Name" attribute.
	 * @param commandBean - the command bean.
	 * @param populateValue - flag to indicate whether to populate value for the bean.
	 * @return - refine bean.
	 * @throws WTException
	 */
	public static RefineBean createNameRefineBean (NmCommandBean commandBean, boolean populateValue) throws WTException {
		TypeDefinitionReadView wtPartTypeDefReadView = CSMTypeDefHelper.getTypeDefView("wt.part.WTPart");

		String attributeName = "name";
		AttributeDefinitionReadView attrReadView = wtPartTypeDefReadView.getAttributeByName(attributeName);
		RefineBean refineBean = createAndValidateRefineBean(attrReadView, commandBean, attributeName, populateValue);

		return refineBean;
	}

	/**
	 * Method to create refine bean for "Number" attribute.
	 * @param commandBean - the command bean.
	 * @param populateValue - flag to indicate whether to populate value for the bean.
	 * @return - refine bean.
	 * @throws WTException
	 */
	public static RefineBean createNumberRefineBean (NmCommandBean commandBean, boolean populateValue) throws WTException {
		TypeDefinitionReadView wtPartTypeDefReadView = CSMTypeDefHelper.getTypeDefView("wt.part.WTPart");

		String attributeName = "number";
		AttributeDefinitionReadView attrReadView = wtPartTypeDefReadView.getAttributeByName(attributeName);
		RefineBean refineBean = createAndValidateRefineBean(attrReadView, commandBean, attributeName, populateValue);

		return refineBean;
	}

	/**
	 * Method to create Sourcing status refing bean.
	 * @param commandBean - the command bean.
	 * @return - refine bean
	 */
	public static RefineBean createSourcingStatusRefineBean (NmCommandBean commandBean) {
		RefineBean refineBean = new RefineBean();
		refineBean.setAttrId(PartslinkConstants.IndexFields.SOURCING_STATUS);
		refineBean.setAttrDataType(AttributeDataType.STRING);
		refineBean.setAttrUnits(null);
		String attrDisplayName = WTMessage.getLocalizedMessage(sumaResource.class.getName(), sumaResource.LBL_SOURCING_STATUS);
		refineBean.setAttrDisplayName(attrDisplayName);

		HashMap text = commandBean.getText();
		String value = (String) text.get(PartslinkConstants.IndexFields.SOURCING_STATUS);
		if (value != null) {
			refineBean.setValue(value);
		}

		// validate bean
		validateBean(refineBean, commandBean);

		return refineBean;
	}

	/**
	 * Method to set operator column values for refine bean.
	 * @param refineBean
	 * @param facet
	 * @param locale
	 * @throws WTException
	 */
	public static void setOperatorColumnValues(RefineBean refineBean, Facet facet, Locale locale) throws WTException {
		boolean isDebugEnabled = logger.isDebugEnabled();
		if (isDebugEnabled) {
			logger.debug("Refine bean in setOperatorColumnValues() :: " + refineBean + " facet is :: " + facet);
		}

		if (refineBean.getAttrDataType() == AttributeDataType.LONG
				|| refineBean.getAttrDataType() == AttributeDataType.FLOATING_POINT
				|| refineBean.getAttrDataType() == AttributeDataType.FLOATING_POINT_WITH_UNITS
				|| refineBean.getAttrDataType() == AttributeDataType.TIMESTAMP) {

			refineBean.setOperatorColumnValues(PartsLinkUtils.getNumericOperators());
		} else if (refineBean.getAttrDataType() == AttributeDataType.BOOLEAN) {
			refineBean.getOperatorColumnValues().clear();
			refineBean.getOperatorColumnValues().add(PartslinkConstants.NumericOperators.BLANK);
			if (facet != null) {
				for (String key : facet.getFacetCountMap().keySet())
					if (facet.getFacetCountMap().get(key) != 0) {
						refineBean.getOperatorColumnValues().add(key);
					}
			}
		} else if (refineBean.getAttrDataType() == AttributeDataType.STRING && facet != null) {
			refineBean.getOperatorColumnValues().clear();
			refineBean.getOperatorColumnValues().add(PartslinkConstants.NumericOperators.BLANK);
			refineBean.getOperatorColumnDisplayValues().clear();
			refineBean.getOperatorColumnDisplayValues().add(PartslinkConstants.NumericOperators.BLANK);
			
			if (InstalledProperties.isInstalled(InstalledProperties.SUMA) && refineBean.getAttrId().equals(PartslinkConstants.IndexFields.SOURCING_STATUS)) {
				// adding operator column values for sourcing status
				if (facet != null) {
					for (String key : facet.getFacetCountMap().keySet()) {
						if (facet.getFacetCountMap().get(key) != 0) {
							//sourcing status are indexed as <sourcingContext>#<sourcingStatus> in Solr. Hence, tokenizing it.
							StringTokenizer tokenizer = new StringTokenizer(key, PartslinkConstants.IndexFields.SRC_CONTEXT_AND_STATUS_DELIMITER);
							if (tokenizer.countTokens() == 2) {
								tokenizer.nextToken(); // sourcingContext
								String sourcingStatus = tokenizer.nextToken();
								HashMap<String, String> map = PartsLinkUtils.getSourcingStatusMap(locale);
								String displayValue = map.get(sourcingStatus);

								if (!refineBean.getOperatorColumnValues().contains(displayValue)) {
									refineBean.getOperatorColumnValues().add(displayValue);
								}
							}
						}
					}
				}
			} else {
				AttributeDefinitionReadView attributeDefinitionReadView = refineBean.getAttrDefReadView();
				if (attributeDefinitionReadView != null) {
					Collection<ConstraintDefinitionReadView> constraints = attributeDefinitionReadView.getAllConstraints();
					for (ConstraintDefinitionReadView constraint : constraints) {
						String constraintDefClassname = constraint.getDefClassname();

						if (LWCBasicConstraint.class.getName().equals(constraintDefClassname) || LWCEnumerationBasedConstraint.class.getName().equals(constraintDefClassname) ) { // basic and enumeration constraint
							ConstraintRuleDefinitionReadView ruleDefView = constraint.getRule();

							if (ruleDefView.getRuleClassname().contains("DiscreteSet")) {
								// TODO : this will check now for demo but proper check should be added for constraint checking
								// Facet facet = resultModel.getFacetFieldMap().get(refineBean.getAttrId());
								if (facet != null) {
									for (String key : facet.getFacetCountMap().keySet()) {
										if (facet.getFacetCountMap().get(key) != 0) {
										    refineBean.getOperatorColumnValues().add(key);

											if(LWCEnumerationBasedConstraint.class.getName().equals(constraintDefClassname))
											{
											    //handle case of deleted display values, in this case what value should be shown in drop down combo box.
											    if( (EnumerationEntryReadView)constraint.getEnumDef().getAllEnumerationEntries().get(key) == null )
											    {
											        refineBean.getOperatorColumnDisplayValues().add( key );
											        continue;
											    }

											    Collection properties = ((EnumerationEntryReadView)constraint.getEnumDef().getAllEnumerationEntries().get(key)).getAllProperties();
											    Iterator<PropertyValueReadView> itr = ((SortedSet<PropertyValueReadView>)properties).iterator();

											    while(itr.hasNext())
											    {
											        PropertyValueReadView property = itr.next();
											        if (property.getName().equalsIgnoreCase("displayName")) {
											            String displayNameValue = property.getValueAsString(locale, true);
											            refineBean.getOperatorColumnDisplayValues().add(displayNameValue);
											        }
											        else {
											            continue;
											        }
											    }
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Method to set range on the refine bean.
	 * @param refineBean
	 * @param statsInfo
	 * @param resultCount
	 * @throws WTException
	 */
	public static void setRanges(RefineBean refineBean, StatsInfo statsInfo, long resultCount) throws WTException {
		boolean isDebugEnabled = logger.isDebugEnabled();
		if (isDebugEnabled) {
			logger.debug("Refine bean in setRanges() :: " + refineBean + " stats is :: " + statsInfo);
		}
		if (statsInfo != null)
		{
			String baseUnit = PartsLinkUnitsHelper.getBaseUnitFromQoM(refineBean.getAttrDefReadView().getQoM());
			String rangeVal = "";
			if (statsInfo.getMin() == statsInfo.getMax()) {
				// only single value in Range

				if (refineBean.getAttrDataType() == AttributeDataType.LONG) {
					long value = (long) statsInfo.getMin();
					rangeVal = "( " + value + " )";
					if(resultCount==statsInfo.getCount()){
						refineBean.setValue(value + "");
						refineBean.setEnabled(PartslinkPropertyModel.getInstance().isEnableCommonValueAttribute());
					}
				} else if(refineBean.getAttrDataType() == AttributeDataType.FLOATING_POINT) {
					// nonunit real numbers needs to be translated according to
					// locale, the significant bits are calculated based on
					// English locale as the value received here is always of
					// English locale.
					String value = NumericToolkitUtil.doubleToString(statsInfo.getMin(),
		                    NumericToolkit.countSigFigs(Double.toString(statsInfo.getMin()), Locale.ENGLISH));
					rangeVal = "( " + value + " )";
					if(resultCount==statsInfo.getCount()){
						refineBean.setValue(value);
						refineBean.setEnabled(PartslinkPropertyModel.getInstance().isEnableCommonValueAttribute());
					}
				} else {
					// Real number with unit
					// do unit conversion
					String value = RealNumberWithUnitsHelper.convertValueFromBaseUnitToSpecifiedUnit(
							refineBean.getAttrUnits(), baseUnit, statsInfo.getMin());
					rangeVal = "( " + value + " )";
					if(resultCount==statsInfo.getCount()){
						refineBean.setValue(value);
						refineBean.setEnabled(PartslinkPropertyModel.getInstance().isEnableCommonValueAttribute());
					}
				}

			} else {
				// value range with multiple values

				if (refineBean.getAttrDataType() == AttributeDataType.LONG) {
					rangeVal = "( " + (long) statsInfo.getMin() + " ) - ( " + (long) statsInfo.getMax() + " )";
				} else if(refineBean.getAttrDataType() == AttributeDataType.FLOATING_POINT){
					// nonunit real numbers needs to be translated according to
					// locale, the significant bits are calculated based on
					// English locale as the value received here is always of
					// English locale.
					rangeVal = "( " + NumericToolkitUtil.doubleToString(statsInfo.getMin(),
		                    NumericToolkit.countSigFigs(Double.toString(statsInfo.getMin()), Locale.ENGLISH)) + " ) - " +
							"( " + NumericToolkitUtil.doubleToString(statsInfo.getMax(),
				                    NumericToolkit.countSigFigs(Double.toString(statsInfo.getMax()), Locale.ENGLISH)) + " )";
				} else {
					rangeVal = "( "
							+ RealNumberWithUnitsHelper.convertValueFromBaseUnitToSpecifiedUnit(
									refineBean.getAttrUnits(), baseUnit, statsInfo.getMin())
									+ " ) - ( "
									+ RealNumberWithUnitsHelper.convertValueFromBaseUnitToSpecifiedUnit(
											refineBean.getAttrUnits(), baseUnit, statsInfo.getMax()) + " )";
				}
			}
			refineBean.setRangeValue(rangeVal);
		} else {
			refineBean.setRangeValue("");
		}
	}

	/**
	 * <B>Supported API: </B>false
	 * <BR>
	 *
	 * Validate input.
	 *
	 * @param refineBean
	 *            - the refine bean
	 * @param commandBean
	 *            - the command bean
	 * @return - true if valid input, otherwise false.
	 */
	public static boolean validateBean(RefineBean refineBean, NmCommandBean commandBean) {
		boolean result = true;
		Validator validator = ValidatorFactory.getValidator(refineBean.getAttrDataType());
		result = validator.isValidInput(refineBean, commandBean);
		if (!result) {
			refineBean.setError(true);
		}
		return result;
	}

}
