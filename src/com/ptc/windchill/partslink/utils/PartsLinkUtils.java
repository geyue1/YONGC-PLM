/* bcwti
 *
 * Copyright (c) 2013 Parametric Technology Corporation (PTC). All Rights Reserved.
 *
 * This software is the confidential and proprietary information of PTC
 * and is subject to the terms of a software license agreement. You shall
 * not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement.
 *
 * ecwti
 */
package com.ptc.windchill.partslink.utils;

import static com.ptc.windchill.partslink.PartslinkConstants.RequestParameters.PARAM_OBJECT_REF;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import wt.access.NotAuthorizedException;
import wt.facade.suma.SumaFacade;
import wt.facade.suma.SumaFacadeIfc;
import wt.fc.EnumeratedType;
import wt.iba.definition.AbstractAttributeDefinition;
import wt.indexsearch.IndexSearchUtils;
import wt.log4j.LogR;
import wt.org.WTUser;
import wt.preference.PreferenceClient;
import wt.preference.PreferenceHelper;
import wt.session.SessionHelper;
import wt.units.FloatingPointWithUnits;
import wt.util.WTException;
import wt.util.WTStandardDateFormat;

import com.ptc.core.lwc.common.view.AttributeDefinitionReadView;
import com.ptc.core.lwc.common.view.DatatypeReadView;
import com.ptc.core.lwc.common.view.PropertyHolderHelper;
import com.ptc.core.lwc.common.view.TypeDefinitionReadView;
import com.ptc.core.meta.common.AttributeTypeIdentifier;
import com.ptc.core.meta.common.FloatingPoint;
import com.ptc.core.meta.common.Hyperlink;
import com.ptc.core.meta.common.TypeInstanceIdentifier;
import com.ptc.core.meta.server.IBAModel;
import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.roleAccess.NmRoleAccessHelper;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.csm.client.helpers.CSMTypeDefHelper;
import com.ptc.windchill.enterprise.search.server.SearchHelper;
import com.ptc.windchill.partslink.PartslinkConstants;
import com.ptc.windchill.partslink.PartslinkConstants.AttributeDataType;
import com.ptc.windchill.partslink.PartslinkConstants.TextCompareType;

/**
 * The Utils Class for PartsLink which contains various common utility methods.
 */
public class PartsLinkUtils {

    /** The Constant logger. */
    private static final Logger logger = LogR.getLogger(PartsLinkUtils.class.getName());

    /** The Constant PREFERENCE_CLIENT. */
    private static final String PREFERENCE_CLIENT = PreferenceClient.WINDCHILL_CLIENT_NAME;

    /** The Constant TABLE_MAX_SIZE. */
    private static final String TABLE_MAX_SIZE = "/Tables/DataSourceClientSideLimit";

    /**
     * Given a String value for a date of the form "yyyy-MM-dd" e.g., "2003-11-13"
     * get a Timestamp object. If the user has defined a time zone preference
     * and the user's locale can be determined then create a Timestamp that
     * accounts for that information. If either the user's locale or time
     * zone can't be determined then use the server's locale and time zone.
     * If no time zone is defined for the server then default to Greenwich
     * Mean Time.
     *
     * @param value the value
     * @param locale the locale
     * @return Timestamp
     * @throws WTException the wT exception
     */
    public static Timestamp getTimestamp(final String value, final Locale locale) throws WTException {
        final TimeZone localTimeZone = SearchHelper.getLocalTimeZone();
        return getTimestamp(value, locale, localTimeZone);
    }

    public static Timestamp getTimestamp(final String value, final Locale locale, final TimeZone localTimeZone) throws WTException {
        if ((value == null) || value.trim().equals("")) {
            return null;
        }

        //TimeZone localTimeZone = SearchHelper.getLocalTimeZone();

        if (logger.isDebugEnabled()) {
            logger.debug("getTimestamp -> localTimeZone = PLACE_HOLDER_TEXT" + localTimeZone
                    + "PLACE_HOLDER_TEXT, locale = " + locale + ", value = PLACE_HOLDER_TEXT" + value
                    + "PLACE_HOLDER_TEXT");
        }

        // String pattern = "yyyy-MM-dd";
        final String pattern_JCA = ResourceBundle.getBundle("com.ptc.core.ui.componentRB", locale).getString(
                "CALENDAR_PARSE_DATE");

        Date date = null;
        try {
            date = WTStandardDateFormat.parse(value, pattern_JCA, locale, localTimeZone);
        } catch (final ParseException pe) {
            try {
                date = WTStandardDateFormat.parse(value, WTStandardDateFormat.WF_STANDARD_DATE_ONLY_FORMAT,
                        locale, localTimeZone);
            } catch (final ParseException pe2) {
                try {
                    date = WTStandardDateFormat.parse(value, WTStandardDateFormat.LONG_STANDARD_DATE_FORMAT,
                            locale, localTimeZone);
                } catch (final ParseException pe3) {
                    throw new WTException(pe);
                }
            }
        }
        final Timestamp timestamp = new Timestamp(date.getTime());

        if (logger.isDebugEnabled()) {
            logger.debug("getTimestamp -> timestamp = " + timestamp);
        }

        return timestamp;
    }

    /**
     * Method to translate the attribute id from "attribute_t" to "attribute_facet".
     *
     * @param attrId the attr id
     * @return the string
     */
    public static String translateToFacetId(final String attrId) {
        String facetId = attrId;
        if (attrId != null && attrId.contains("_t")) {
            facetId = attrId.replace("_t", "_facet");
        }

        return facetId;
    }

    /**
     * Returns attribute type in the form of defined attribute type enum{@link PartslinkConstants#AttributeDataTypes}.
     *
     * @param datatype the datatype
     * @return enum - Type of the attribute in the form of enum
     */

    public static AttributeDataType getAttributeType(final String datatype) {
        AttributeDataType attributeType = AttributeDataType.NOTYPE;

        if (Boolean.class.getName().equals(datatype)) {
            attributeType = AttributeDataType.BOOLEAN;
        }
        else if (Timestamp.class.getName().equals(datatype)) {
            attributeType = AttributeDataType.TIMESTAMP;
        }
        else if (Long.class.getName().equals(datatype)) {
            attributeType = AttributeDataType.LONG;
        }
        else if (FloatingPoint.class.getName().equals(datatype)) {
            attributeType = AttributeDataType.FLOATING_POINT;
        }
        else if (FloatingPointWithUnits.class.getName().equals(datatype)) {
            attributeType = AttributeDataType.FLOATING_POINT_WITH_UNITS;
        }
        else if (TypeInstanceIdentifier.class.getName().equals(datatype)) {
            attributeType = AttributeDataType.REFERENCE;
        }
        else if (String.class.getName().equals(datatype)) {
            attributeType = AttributeDataType.STRING;
        }
        else if (Hyperlink.class.getName().equals(datatype)) {
            attributeType = AttributeDataType.HYPERLINK;
        }

        return attributeType;
    }

    /**
     * Returns the csm suma types filter query.
     *
     * @param partTypes the part types
     * @return suma type filter query
     */
    public static String getCsmSumaTypesFilterQuery(final String partTypes) {
        final StringBuilder fq = new StringBuilder();
        final String[] types = partTypes.split(",");

        for (String str : types) {
            if (str.equals("Part")) {
                str = "WTPart";
            } else if (str.equals("Manufacture")) {
                str = "ManufacturerPart";
            } else if (str.equals("Vendor")) {
                str = "VendorPart";
            }

            if (fq.toString().isEmpty()) {
                fq.append(str);
            } else {
                fq.append(" | ");
                fq.append(str);
            }
        }

        final String fqq = PartslinkConstants.IndexFields.CSM_SUMA_TYPE + ":(" + fq.toString() + ")";

        return fqq;
    }

    /**
     * Method to retrieve sourcing status internal name and display name in a form of map.
     *
     * @param locale
     *            - locale
     * @return - a map containing internal name of sourcing status as a key and display name as a value.
     */
    public static HashMap<String, String> getSourcingStatusMap(final Locale locale) {
        final SumaFacadeIfc sumaFacade = SumaFacade.getInstance();
        final EnumeratedType[] axlPreferenceSet = sumaFacade.getAXLPreferenceSet();
        final HashMap<String, String> legal_values = new HashMap<String, String>(axlPreferenceSet.length);

        if (axlPreferenceSet != null) {
            for (final EnumeratedType axlPreference : axlPreferenceSet) {
                String enumerated_key = axlPreference.getStringValue();
                final int index = enumerated_key.lastIndexOf(".");
                if (index > 0) {
                    enumerated_key = enumerated_key.substring(index + 1);
                }
                final String enumerated_display = axlPreference.getDisplay(locale);
                legal_values.put(enumerated_key, enumerated_display);
            }
        }

        return legal_values;
    }

    /**
     * Escapes special meaning characters (i.e. + - ! ( ) { } [ ] ^ " ~ : \ (* ? for non Wildcard only)) of Solr query
     *
     * @param strText
     *            - Text to be escaped for specified special characters
     * @param specialChars
     *            - Special characters to be escaped with back slash i.e. \
     * @return - Escaped text.
     * @throws WTException
     *             - throws WTException, if there is any.
     */
    public static String escapeSpclChars(final String strText, final char[] specialChars) throws WTException {
        final StringBuilder escapedText = new StringBuilder();

        try {
            if (strText != null && !strText.isEmpty() && specialChars != null && specialChars.length > 0) {

                char strChar;

                for (int len = 0; len < strText.length(); len++) {
                    strChar = strText.charAt(len);

                    for (final char specialChar : specialChars) {
                        if (specialChar == strChar) {
                            escapedText.append("\\");
                        }
                    }

                    escapedText.append(strChar);
                }
            }
        } catch (final Exception exp) {
            logger.debug("Exception occurred in escapeSpclChars " + exp);
            throw exp;
        }

        return escapedText.toString();
    }

    /**
     * Quotes special meaning characters of regular expression except * and ?.
     *
     * @param regExText the reg ex text
     * @return - Quoted regular expression string.
     * @throws WTException - throws WTException, if there is any.
     */
    public static String quotePattern(final String regExText) throws WTException {
        String strRegEx = regExText;
        final List<String> strTokens = new ArrayList<String>();

        try {
            if (strRegEx != null && !strRegEx.isEmpty()) {
                int starIndex = -1;
                int queIndex = -1;
                int fromIndex = 0;

                while (true) {
                    starIndex = strRegEx.indexOf('*', fromIndex);
                    queIndex = strRegEx.indexOf('?', fromIndex);

                    if (Math.max(starIndex, queIndex) != -1) {
                        if (starIndex != -1 && (queIndex == -1 || starIndex < queIndex)) {
                            if (starIndex > fromIndex) {
                                strTokens.add(strRegEx.substring(fromIndex, starIndex));
                            }
                            strTokens.add("*");

                            fromIndex = starIndex + 1;
                        }
                        else if (queIndex != -1 && (starIndex == -1 || queIndex < starIndex)) {
                            if (queIndex > fromIndex) {
                                strTokens.add(strRegEx.substring(fromIndex, queIndex));
                            }
                            strTokens.add("?");

                            fromIndex = queIndex + 1;
                        }
                    }
                    else {
                        strTokens.add(strRegEx.substring(fromIndex));
                        break;
                    }
                }

                if (strTokens.size() > 0) {
                    final StringBuilder regEx = new StringBuilder();

                    for (final String token : strTokens) {
                        if (token != null && !token.isEmpty()) {
                            if ("*".equals(token) || "?".equals(token)) {
                                regEx.append(token);
                            }
                            else {
                                regEx.append(Pattern.quote(token));
                            }
                        }
                    }

                    strRegEx = regEx.toString();
                }
            }
        } catch (final Exception exp) {
            logger.debug("Exception occurred in quotePattern " + exp);
            throw exp;
        }

        return strRegEx;
    }

    /**
     * Compares two text for matches like startswith, equals and wildcard i.e. *=zero or more any character and ?=one or
     * more any character.
     *
     * @param strText - Text to be compared
     * @param strCompareWith - Text to be compared with
     * @param compareType - Startswith or Equals compare when wildcard is disabled.
     * @return - true if the text matches, false otherwise.
     * @throws WTException - throws WTException, if there is any.
     */
    public static boolean compareText(final String strText, final String strCompareWith,
            final TextCompareType compareType) throws WTException {
        boolean isMatches = false;
        String strRegEx = strCompareWith;

        try {
            if (strText != null && !strText.isEmpty()) {

                if (compareType == TextCompareType.WILDCARD) {
                    if (strRegEx != null && !strRegEx.isEmpty()) {

                        strRegEx = quotePattern(strRegEx);

                        // To convert Solr wild card characters i.e. * and ? with equivalent java regEx pattern
                        strRegEx = strRegEx.replace("*", ".*");

                        // ? has different meaning i.e in java regEx ? = zero or one occurrence of any character
                        // (but .{1} = one occurrence of any character)
                        // in Solr, ? = one occurrence of any character
                        strRegEx = strRegEx.replace("?", ".{1}");

                        if (logger.isDebugEnabled()) {
                            logger.debug("strRegEx= " + strRegEx);
                        }

                        isMatches = strText.matches(strRegEx);
                    }
                }
                else if (compareType == TextCompareType.STARTSWITH) {
                    isMatches = strText.startsWith(strCompareWith);
                }
                else if (compareType == TextCompareType.EQUALS) {
                    isMatches = strText.equals(strCompareWith);
                }
            }

            logger.debug("isMatches= " + isMatches);

        } catch (final Exception exp) {
            logger.debug("Exception occurred while comparing text with regular expression" + exp);
            throw exp;
        }

        return isMatches;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     *
     * Generate solr attribute id.
     *
     * @param attributeDefinitionReadView
     *            the attribute definition read view
     * @return the string
     * @throws WTException
     *             the wT exception
     */
    public static String generateSolrAttributeID(final AttributeDefinitionReadView attributeDefinitionReadView)
            throws WTException {
        // get IBA id name
        final AttributeTypeIdentifier ati = attributeDefinitionReadView.getAttributeTypeIdentifier();
        final AbstractAttributeDefinition def = IBAModel.getIBADefinitionByHid(ati.getAttributeName());
        final String ibaAttNameWithId = IndexSearchUtils.getIbaName(def);

        // append suffix
        final DatatypeReadView dataTypeRV = attributeDefinitionReadView.getDatatype();
        // get solr data type
        String solrDataType = IndexSearchUtils.getIbaTypeFromDataType(dataTypeRV.getName());
        if (solrDataType.equals(IndexSearchUtils.FLOAT)) {
            solrDataType = IndexSearchUtils.DOUBLE;
        }
        // get solr dynamic filed suffix. e.g. _t, _d, etc.
        final String suffix = IndexSearchUtils.getSolrDynamicFieldIndexSuffix(solrDataType);
        final String attrId = ibaAttNameWithId + suffix; // final attribute id as indexed in solr

        return attrId;
    }

    /**
     * <B>Supported API: </B>false <BR>
     * Gets the numeric operators.
     *
     * @return the numeric operators
     */
    public static ArrayList<String> getNumericOperators() {
        final ArrayList<String> operators = new ArrayList<String>();
        operators.add(PartslinkConstants.NumericOperators.EQUAL);
        operators.add(PartslinkConstants.NumericOperators.GREATER_THAN_EQUAL);
        operators.add(PartslinkConstants.NumericOperators.LESS_THAN_EQUAL);
        operators.add(PartslinkConstants.NumericOperators.RANGE);
        return operators;
    }

    /**
     * <B>Supported API: </B>false <BR>
     * Gets the numeric modifiers.
     *
     * @return list of numeric modifiers.
     */
    public static ArrayList<String> getNumericModifiers() {

        final ArrayList<String> Values = new ArrayList<String>();
        Values.add(PartslinkConstants.NumericOperators.ABSOLUTE);
        Values.add(PartslinkConstants.NumericOperators.PERCENT);
        return Values;
    }

    /**
     * <B>Supported API: </B>false <BR>
     *
     * Gets the class query.
     *
     * @param classInternalName
     *            the class internal name
     * @return the class query
     */
    public static String getClassificationQuery(final String classInternalName) {
        final String fq = PartslinkConstants.IndexFields.PTC_CLASSIFICATION + ":("
                + IndexSearchUtils.escapeLuceneChars(classInternalName) + ")";
        return fq;
    }

    /**
     * Method to create command bean from the input request.
     *
     * @param request the request
     * @return the nm command bean
     * @throws WTException the wT exception
     */
    public static NmCommandBean getNmCommandBean(final HttpServletRequest request) throws WTException {
        final NmCommandBean commandBean = new NmCommandBean();
        commandBean.setRequest(request);
        return commandBean;
    }

    /**
     * Converts the GMT Time in local TimeZone.
     *
     * @param value the value
     * @return the date time in local tme zone
     */
    public static String getDateTimeInLocalTimeZone(final String value) {

        // This API is required because while fetching value for DateTimeStampAttr from DB
        // the Dates are getting converted to GMT.
        // So to convert the date from GMt to Local Time Zone this API is required.
        // So the display value for DateTiemStamp attribute on the result Page and Find Similar UI is same.
        // and the correct value are getting queried on Solr queries as well.
        logger.debug("FindSimilarBeanService ==> getDateTimeInLocalTmeZone :: IN ");
        TimeZone localTimeZone = null;

        try {
            localTimeZone = SearchHelper.getLocalTimeZone();
            String actualdate = null;

            final String dateFormat = "yyyy-MM-dd HH:mm:ss.SSS";

            final SimpleDateFormat simpleDateFmt = new SimpleDateFormat(dateFormat);
            simpleDateFmt.setTimeZone(TimeZone.getTimeZone("GMT"));

            final SimpleDateFormat requiredDateFmt = new SimpleDateFormat(dateFormat);
            requiredDateFmt.setTimeZone(localTimeZone);

            Date inptdate = null;
            inptdate = simpleDateFmt.parse(value);
            actualdate = requiredDateFmt.format(inptdate);

            return actualdate;

        } catch (WTException | ParseException e1) {
            logger.error(e1.getLocalizedMessage(), e1);
            return null;
        }
    }

    /**
     * Gets the date time in local time zone.
     *
     * @param inptdate the inptdate
     * @return the date time in local time zone
     */
    public static String getDateTimeInLocalTimeZone(final Date inputdate) {
        logger.debug("FindSimilarBeanService ==> getDateTimeInLocalTmeZone :: IN ");
        TimeZone localTimeZone = null;

        try {
            localTimeZone = SearchHelper.getLocalTimeZone();
            String actualdate = null;

            String dateFormat = "yyyy-MM-dd HH:mm:ss.SSS";

            final Locale locale = SessionHelper.getLocale();

            // String pattern = "yyyy-MM-dd";
            final String pattern_JCA = ResourceBundle.getBundle("com.ptc.core.ui.componentRB", locale).getString("CALENDAR_PARSE_DATE");
            if(pattern_JCA!=null) {
                dateFormat = pattern_JCA;
            }

            final SimpleDateFormat requiredDateFmt = new SimpleDateFormat(dateFormat);
            requiredDateFmt.setTimeZone(localTimeZone);

            actualdate = requiredDateFmt.format(inputdate);
            return actualdate;

        } catch (final WTException e1) {
            logger.error(e1.getLocalizedMessage(), e1);
            return null;
        }
    }

    /**
     * Combines one or more filtered queries with the provided operator.
     *
     * @param filterQueries - list of filterQueries
     * @param Operator - operator to be used to combine queries
     * @return - combined query with the provided operator
     */
    public static String combineFQs(final List<String> filterQueries, final String Operator) {
        final StringBuilder fqAll = new StringBuilder();
        final StringBuilder fq = new StringBuilder();
        boolean appendUnaryOperator = false;

        if (filterQueries != null && filterQueries.size() > 0) {
            for (final String filterQuery : filterQueries) {
                if (appendUnaryOperator) {
                    fq.append(Operator);
                } else {
                    appendUnaryOperator = true;
                }
                fq.append(filterQuery);
            }
        }

        if (fq.length() > 0) {
            fqAll.append("( ");
            fqAll.append(fq);
            fqAll.append(" )");
        }

        return fqAll.toString();
    }

    /**
     * Returns the internal name of the root node.
     *
     * @return the string - internal name of the root node
     * @throws NotAuthorizedException the not authorized exception
     * @throws WTException the wT exception
     */
    public static String getClfRootNodeName() throws NotAuthorizedException, WTException {

        final boolean isDebugEnabled = logger.isDebugEnabled();
        String rootNodeInfo = null;

        final Set<TypeDefinitionReadView> rootViews = (Set<TypeDefinitionReadView>) CSMTypeDefHelper.getRootTypeDefViews();
        if (rootViews == null || rootViews.size() == 0) {
            return null;
        }
        for (final TypeDefinitionReadView tdRV : rootViews) {
            if (tdRV.isDeleted()) {
                continue;
            }
            rootNodeInfo = PropertyHolderHelper.getName(tdRV);
        }

        if (isDebugEnabled) {
            logger.debug("Node returning from fetchRootNode is :  " + rootNodeInfo);
        }

        return rootNodeInfo;
    }

    /**
     * Returns the display name of the root node.
     *
     * @param locale the locale
     * @return the string - display name of the root node
     * @throws NotAuthorizedException the not authorized exception
     * @throws WTException the WT exception
     */
    public static String getClfRootNodeDisplayName(final Locale locale) throws NotAuthorizedException, WTException {

        final boolean isDebugEnabled = logger.isDebugEnabled();
        String rootNodeInfo = null;

        final Set<TypeDefinitionReadView> rootViews = (Set<TypeDefinitionReadView>) CSMTypeDefHelper.getRootTypeDefViews();
        if (rootViews == null || rootViews.size() == 0) {
            return null;
        }
        for (final TypeDefinitionReadView tdRV : rootViews) {
            if (tdRV.isDeleted()) {
                continue;
            }
            rootNodeInfo = PropertyHolderHelper.getDisplayName(tdRV, locale);
        }

        if (isDebugEnabled) {
            logger.debug("Node returning from fetchRootNode is :  " + rootNodeInfo);
        }

        return rootNodeInfo;
    }

    /**
     * Returns the display name of the classification node having the internal name specified.
     * 
     * @param String the internal name of the classification node
     * @param locale the locale
     * @return the string - display name of the root node
     * @throws NotAuthorizedException the not authorized exception
     * @throws WTException the WT exception
     */
    public static String getClfNodeDisplayName(final String classIntName, final Locale locale)
            throws NotAuthorizedException, WTException {

        final boolean isDebugEnabled = logger.isDebugEnabled();
        String clfNodeDispName = null;

        final TypeDefinitionReadView nodeRV = CSMTypeDefHelper.getClassificationTypeDefView(classIntName);

        if (nodeRV != null) {
            clfNodeDispName = PropertyHolderHelper.getDisplayName(nodeRV, locale);
        }

        if (isDebugEnabled) {
            logger.debug("Node internal name: " + classIntName + ", Node display name: " + clfNodeDispName);
        }

        return clfNodeDispName;
    }

    /**
     * Return the maximum number of objects that should be shown in a table using data sources
     * Returns -1 (unlimited) if
     * no preference is define.
     *
     * @return the result table size pref
     * @throws WTException the wT exception
     */
    public static int getResultTableSizePref () throws WTException
    {
        final WTUser currUser = (WTUser) SessionHelper.getPrincipal ();
        final Integer size = (Integer) PreferenceHelper.service.getValue (null, TABLE_MAX_SIZE, PREFERENCE_CLIENT, currUser);
        if (size == null)
        {
            return - 1;
        }
        return size;
    }

    /**
     * <BR><BR><B>Supported API: </B>false
     *
     * Gets the NmOid from commandBean.
     *
     * @param commandBean the command bean
     * @return the nm oid
     * @throws WTException the WTException
     */
    public static NmOid getNmOid(final NmCommandBean commandBean) throws WTException {
        NmOid nmOid = null;
        String objectId = commandBean.getTextParameter(PARAM_OBJECT_REF);
        if(objectId!=null) {
            if(!objectId.startsWith("OR")) {
                objectId = "OR:"+objectId;
            }
            nmOid = new NmOid(objectId);
        }
        if(nmOid==null) {
            nmOid = commandBean.getActionOid();
        }
        return nmOid;
    }

    /**
     * Parses the boolean value.
     *
     * Returns Boolean object for matching boolean value. Matched localized boolean value Returns null if nothing
     * matched
     *
     * @param value
     *            the value
     * @return the boolean
     */
    public static Boolean parseBooleanValue(String value) {

        Boolean bValue = null; // return null if nothing matched

        // check null
        if (value == null) {
            return null;
        }

        value = value.trim();
        // check standard values
        if ("true".equalsIgnoreCase(value)) {
            bValue = Boolean.TRUE;
        }
        else if ("false".equalsIgnoreCase(value)) {
            bValue = Boolean.FALSE;
        }
        else if ("1".equalsIgnoreCase(value)) {
            bValue = Boolean.TRUE;
        }
        else if ("0".equalsIgnoreCase(value)) {
            bValue = Boolean.FALSE;
        }
        // check localized values
        else if (PartslinkConstants.BooleanOperators.TRUE.getDisplayValue().equalsIgnoreCase(value)) {
            bValue = Boolean.TRUE;
        } else if (PartslinkConstants.BooleanOperators.FALSE.getDisplayValue().equalsIgnoreCase(value)) {
            bValue = Boolean.FALSE;
        }

        return bValue;
    }

    /**
     * returns weather given action id is accessible by user profile.
     */
    public static boolean shouldRenderActionOnRoleBasedCheck(String actionId) {
        boolean shouldRender = true;
        try {
            WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
            shouldRender = NmRoleAccessHelper.service.shouldRender(user, null, actionId);
        } catch (WTException wte) {
            shouldRender = false;
        }
        return shouldRender;
    }

}
