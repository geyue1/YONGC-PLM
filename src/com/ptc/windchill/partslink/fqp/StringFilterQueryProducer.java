package com.ptc.windchill.partslink.fqp;

import java.util.Collection;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import wt.indexsearch.IndexSearchUtils;
import wt.log4j.LogR;
import wt.util.WTException;

import com.ptc.windchill.partslink.PartslinkConstants;
import com.ptc.windchill.partslink.PartslinkConstants.QueryType;
import com.ptc.windchill.partslink.model.PartslinkPropertyModel;
import com.ptc.windchill.partslink.utils.PartsLinkUtils;

/**
 * The Class StringFilterQueryProducer.
 */
public class StringFilterQueryProducer extends AbstractFilterQueryProducer {

    /** The Constant logger. */
    private static final Logger logger = LogR.getLogger(StringFilterQueryProducer.class.getName());

    /**
     * Method to generate query for string attributes.
     */
    @Override
    public String produceFilterQuery(FQPBean bean) {
        String result = null;
        String value = bean.getValue();

        if (!bean.getInternalValue().isEmpty()) {
            value = bean.getInternalValue();
        }

        if (value != null && !value.trim().equals("")) {

            if (!PartslinkPropertyModel.getInstance().isFreeFormWildcardEnabled() && bean.isSearchWithImplicitWC()) {
                value = value + "*";
            }

            StringBuilder sb = new StringBuilder();
            if("-".equals(bean.getOperator())){
            	sb.append("-"+bean.getAttrId()).append(":(");
            }else{
            	sb.append(bean.getAttrId()).append(":(");
            }
            

            StringTokenizer strTokens = new StringTokenizer(value, "|");
            while (strTokens.hasMoreTokens()) {
                String token = strTokens.nextToken().trim();
                StringBuilder temp = new StringBuilder();
                token = IndexSearchUtils.escapeLuceneChars(token);
                if (token.contains("*")) {
                    // Wild card * encountered in the string.
                    temp.append(token);
                } else {
                    // encoding the string within double quotes.
                    temp.append("\"" + token + "\"");
                }
                sb.append(temp);
                if (strTokens.hasMoreTokens()) {
                    sb.append(" | ");
                } else {
                    sb.append(")");
                }
            }
            result = sb.toString();
        }

        return result;
    }

    /**
     * Creates Solr query.
     * 
     * @param fieldName
     *            - Solr filed name
     * @param searchWordsList
     *            - list of searchWords as Collection
     * @param queryType
     *            - QueryType i.e. WILDCARD, IMPLICIT_WILDCARD_ONLY OR NON_WILDCARD
     * @return Solr query
     */
    public static String createQuery(final String fieldName, final Collection<String> searchWordsList,
            final QueryType queryType)
            throws WTException {
        String[] searchWords = searchWordsList.toArray(new String[] {});
        return createQuery(fieldName, searchWords, queryType);
    }

    /**
     * Creates Solr query.
     * 
     * @param fieldName
     *            - Solr filed name
     * @param searchText
     *            - text to be searched
     * @param queryType
     *            - QueryType i.e. WILDCARD, IMPLICIT_WILDCARD_ONLY OR NON_WILDCARD
     * @return Solr query
     */
    public static String createQuery(final String fieldName, final String searchText, final QueryType queryType)
            throws WTException {
        String[] searchWords = { searchText };
        return createQuery(fieldName, searchWords, queryType);
    }

    /**
     * Creates Solr query.
     * 
     * @param fieldName
     *            - Solr filed name
     * @param searchWords
     *            - list of searchWords as String[] i.e. String array
     * @param queryType
     *            - QueryType i.e. WILDCARD, IMPLICIT_WILDCARD_ONLY OR NON_WILDCARD
     * @return Solr query
     */
    public static String createQuery(final String fieldName, final String[] searchWords, final QueryType queryType)
            throws WTException {
        return createQuery(fieldName, searchWords, " OR ", queryType);
    }

    /**
     * Creates Solr query.
     * 
     * @param fieldName
     *            - Solr filed name
     * @param searchWords
     *            - list of searchWords as String[] i.e. String array
     * @param operator
     *            - separator i.e. AND, OR etc.
     * @param queryType
     *            - QueryType i.e. WILDCARD, IMPLICIT_WILDCARD_ONLY OR NON_WILDCARD
     * @return Solr query
     */
    public static String createQuery(final String fieldName, final String[] searchWords, final String operator,
            final QueryType queryType) throws WTException {

        StringBuilder fqValues = new StringBuilder();
        StringBuilder fq = new StringBuilder();
        boolean appendUnaryOperator = false;

        for (String searchText : searchWords) {
            if (searchText != null && !searchText.isEmpty()) {

                if (appendUnaryOperator) {
                    fqValues.append(operator);
                } else {
                    appendUnaryOperator = true;
                }

                // to escape all special characters excluding wildcard characters (i.e. * and ?) those have special
                // meanings in Solr query
                searchText = PartsLinkUtils.escapeSpclChars(searchText, PartslinkConstants.FQ_SPCL_CHARS);

                // to also escape wildcard characters (i.e. * and ?) in Solr query
                if (queryType != QueryType.WILDCARD) {
                    searchText = PartsLinkUtils.escapeSpclChars(searchText, PartslinkConstants.FQ_WC_CHARS);
                }

                // Use implicit wild card for solr query
                if (queryType == QueryType.IMPLICIT_WILDCARD_ONLY) {
                    searchText = searchText + "*";
                }

                if (logger.isDebugEnabled()) {
                    logger.debug("createQuery with escaped searchText: " + searchText);
                }
            }

            fqValues.append(searchText);
        }

        if (fqValues.length() > 0) {
            fq.append(fieldName).append(":( ");
            fq.append(fqValues);
            fq.append(" )");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("createQuery multivalue Solr Query: " + fqValues);
            logger.debug("createQuery multivalue Solr Query: " + fq);
        }

        return fq.toString();
    }

}
