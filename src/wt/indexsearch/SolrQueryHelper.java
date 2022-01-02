/*
 * bcwti
 * Copyright (c) 2010 Parametric Technology Corporation (PTC). All Rights Reserved.
 * This software is the confidential and proprietary information of PTC
 * and is subject to the terms of a software license agreement. You shall
 * not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement.
 * ecwti
 */
package wt.indexsearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import wt.doc.WTDocument;
import wt.epm.EPMDocument;
import wt.index.IndexConstants;
import wt.index.SolrIndexConstants;
import wt.index.SolrService;
import wt.indexsearch.xmlSearch.XMLQueryHelper;
import wt.indexsearch.xmlSearch.XMLQueryParseException;
import wt.log4j.LogR;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.session.SessionHelper;
import wt.type.TypedUtility;
import wt.util.WTException;
import wt.util.WTProperties;
import wt.util.WTPropertyVetoException;

import com.ptc.core.meta.common.AttributeTypeIdentifier;
import com.ptc.core.meta.common.TypeIdentifier;

/**
 * Generates Solr QueryString from keyword querySring
 */
public class SolrQueryHelper {
    private IndexSearchInput indexSearchInput;

    private WTUser currentUser;

    private SolrService solrService;

    private String coreNames;

    private String queryTerm;

    private String searchOperator = " AND ";

    private int queryType;

    private TranslationSolrQueryHelper translationHelper = null;

    private static final String CLASSNAME = SolrQueryHelper.class.getName();

    private static final Logger indexLogger = LogR.getLogger(CLASSNAME);

    private static final TypeIdentifier partTypeID = TypedUtility.getTypeIdentifier(WTPart.class.getName());

    private static final TypeIdentifier documentTypeID = TypedUtility.getTypeIdentifier(WTDocument.class.getName());

    private static final TypeIdentifier empDocumentTypeID = TypedUtility.getTypeIdentifier(EPMDocument.class.getName());

    private static final boolean ENABLE_NAME_ATTR_ENUM_CONSTRAINT;

    private static final int SOLR_MAX_BOOLEAN_CLAUSES;

    private static final String TOO_MANY_SOLR_BOOLEAN_CLAUSES = "Too many solr boolean clauses";

    private static final String OR = " OR ";
    
    private static final String ESCAPED_DOUBLEQUOTE = "\\\"";
    
    private static final String DOUBLEQUOTE = "\"";

    static {
        try {
            ENABLE_NAME_ATTR_ENUM_CONSTRAINT = WTProperties.getLocalProperties().getProperty(
                    "com.ptc.EnableEnumeratedName", false);
            SOLR_MAX_BOOLEAN_CLAUSES = WTProperties.getLocalProperties().getProperty("com.ptc.MaxSolrBooleanClauses",
                    1024);
        } catch (IOException e) {
            indexLogger.error("Error during initialization: " + e.getLocalizedMessage(), e);
            throw new ExceptionInInitializerError(e);
        }
    }

    // for unit testing
    protected SolrQueryHelper(String searchOperator) throws WTException {
        this.searchOperator = searchOperator;
    }

    protected void setTranslationHelper(TranslationSolrQueryHelper translationHelper) {
        this.translationHelper = translationHelper;
    }

    public SolrQueryHelper(SolrService solrService, IndexSearchInput indexSearchInput, WTUser currentUser,
            String coreNames) throws WTException {
        super();
        this.solrService = solrService;
        this.indexSearchInput = indexSearchInput;
        this.currentUser = currentUser;
        this.coreNames = coreNames;
        this.queryTerm = indexSearchInput.getSearchString();

        if (indexSearchInput.andOrOperator.equals("|")) {
            searchOperator = OR;
        }
        queryType = indexSearchInput.getDefaultQueryType(this.currentUser);
        indexLogger.debug("SolrQueryHelper: queryType is " + queryType);

        this.translationHelper = new TranslationSolrQueryHelper();
    }

    public Object[] setUpQuery() throws WTException {
        List<String> keywordQueries = getKeywordQuery(queryTerm);
        int noOfQueries = keywordQueries.size();
        Object[] queries = new Object[noOfQueries];
        // if there are many queries to determine the result, then every query should fetch max possible result from
        // Solr
        // there won't be paging possible in this case
        boolean setMaxRows = noOfQueries > 1 || IndexSearchUtils.isScopedSearch(queryTerm.toLowerCase().trim());
        // This can set value only to true, not to false as IndexService could have set it to true
        if (setMaxRows) {
            indexSearchInput.setQueryForMaxDoc(true);
        }
        int i = 0;
        for (String keywordQuery : keywordQueries) {
            queries[i++] = setUpQuery(keywordQuery, setMaxRows);
        }
        return queries;
    }

    /**
     * Set up the query so that it can be used to perform the search.
     * 
     * @throws WTException
     *             If an error occurs.
     */
    public Object setUpQuery(String keywordQuery, boolean setMaxRows) throws WTException {

        String businessTypes = indexSearchInput.getBusinessTypes();
        indexLogger.debug("Business types are " + businessTypes);

        boolean indexableQuery = businessTypes.equals("wt.index.Indexable");
        if (indexableQuery) {
            return getIndexableTypeQuery(keywordQuery);
        }

        StringBuilder queryString = new StringBuilder();
        if(keywordQuery!=null && keywordQuery.length()>0)        	
        {
            // The query should have a parameter with +() to emphasize that the
            // parameter is a required condition in the
            // search results ignoring the + outside the parenthesis will return funny results
            // (believe me or ask Mayur :P.)
          	queryString.append("+(");
        	queryString.append(keywordQuery); 
        }

        // add additional search criteria for SIMPLE, ADVANCED and PARAMETRIC MODE as well
        String searchFieldWhereClause = indexSearchInput.getSearchFields();
        if (searchFieldWhereClause != null) {
        	if(queryString.length()>0){
        		queryString.append(searchOperator);
        	}else{
        		queryString.append("+(");
        	}
            queryString.append(searchFieldWhereClause);
        }

        if(queryString.length()>0){
            queryString.append(")");
        }
        indexLogger.debug("Query String after searchFields: " + queryString.toString());

        // For scoped search do not add commonWhereClause in search criteria
        if (queryType != IndexSearchUtils.XML_QUERY) {
            String commonWhereClause = indexSearchInput.getCommonWhereClause();
            if (commonWhereClause != null) {
                queryString.append(" AND ");
                queryString.append(commonWhereClause);
            }

            indexLogger.debug("Query String after commonWhereClause: " + queryString.toString());
            queryString.append(getRelevancyCriteria(queryTerm, queryType));
            indexLogger.debug("Query String with rank boosting terms: " + queryString.toString());
        }

        if (enableNameAttributeEnumConstraint() && isOverMaxSolrBooleanClauses(queryString.toString())) {
            throw new WTException(TOO_MANY_SOLR_BOOLEAN_CLAUSES);
        }

        Object query = getSolrQuery(queryString.toString());

        // Add Businesstypes
        /*
         * When 'With Any Criteria (OR)' is selected, keyword criteria is ORed with whereClause so we need to
         * additionally apply businessType filter query When whereClause is null, then businessType criteria won't be
         * applied through whereClause, so we have to put in additional filter query When 'With All Criteria (AND)' is
         * selected and we have whereClause then all target types (businessTypes) are considered while processing type
         * wise whereClause through SolrWhereClauseHandler, hence we don't need to additionally apply businessType
         * filter query. For common whereClause, however, we need to apply this filter
         */
        // indexable target type means all types, i. e. no criteria on business type
        if (searchOperator.equals(OR) || searchFieldWhereClause == null
                || indexSearchInput.isCommonWhereClauseExists()) {
            if (businessTypes != null && businessTypes.trim().length() > 0) {
                String[] btArr = businessTypes.split(",");
                StringBuilder typeFilter = new StringBuilder("businessType:(");

                for (int i = 0; i < btArr.length; i++) {
                    if (i > 0) {
                        typeFilter.append(OR);
                    }
                    typeFilter.append(btArr[i]);
                }
                typeFilter.append(')');
                addFilterQuery(query, typeFilter.toString());
            }
        }

        if (queryType != IndexSearchUtils.XML_QUERY) {
            // add all the container references
            String containerRefs = indexSearchInput.getContainerReference();
            indexLogger.debug("Container references are:" + containerRefs);
            if (containerRefs != null && containerRefs.trim().length() > 0) {
                StringBuilder containerFilter = new StringBuilder();
                StringTokenizer st = new StringTokenizer(containerRefs, ",");
                int i = 0;
                while (st.hasMoreTokens()) {
                    String tok = st.nextToken();
                    indexLogger.debug("Adding containerRef " + tok);
                    if (i++ > 0) {
                        containerFilter.append(OR);
                    }
                    containerFilter.append("containerReference:" + tok);

                    if (indexSearchInput.isIncludeShared()) {
                        containerFilter.append(OR);
                        containerFilter.append("sharedContainerReference:" + tok);
                    }
                }
                addFilterQuery(query, containerFilter.toString());
            }

            addCores(query);
            addSortCriteria(query);

            // Set the query for spellchecker. This can be different from query term if query term contains special
            // characters like quotes.
            // Skip suggestions for XML and Parametric queries.
            // Remove the quotes.
            if ((queryType != IndexSearchUtils.XML_QUERY) && (queryType != IndexSearchUtils.PARAMETRIC_KEYWORD)) {
                String suggestionQuery = indexSearchInput.getSearchString();
                if (suggestionQuery.indexOf("\"") != -1) {
                    suggestionQuery = suggestionQuery.replaceAll("\"", "");
                }
                setToQuery(query, "spellcheck.q", suggestionQuery);
                addFetchFields(query);
            }
        }

        setStart(query, indexSearchInput.getStart());
        if (setMaxRows) {
            setRows(query, IndexSearchInput.MAX_DOCUMENTS);
            try {
                indexSearchInput.setMaxDocs(IndexSearchInput.MAX_DOCUMENTS);
            } catch (WTPropertyVetoException e) {
                indexLogger.debug("exception while setting max docs to IndexSearchInput", e);
            }

        } else {
            setRows(query, indexSearchInput.getMaxDocs());
        }

        indexLogger.debug("Solr Query =" + query);
        // return the query now with all of the search attributes set up and ready
        return query;
    }

    /**
     * Set up the query for Indexable target type so that it can be used to perform the search.
     * 
     * @throws WTException
     *             If an error occurs.
     */
    public Object getIndexableTypeQuery(String keywordQuery) throws WTException {
        Object query = getSolrQuery(keywordQuery);
        addCores(query);
        addSortCriteria(query);
        addFetchFields(query);

        setStart(query, indexSearchInput.getStart());
        setRows(query, indexSearchInput.getMaxDocs());

        indexLogger.debug("Solr Query =" + query);
        // return the query now with all of the search attributes set up and ready
        return query;
    }

    private void addCores(Object query) throws WTException {
        // Removing the shards parameter in case of single core configuration.
        // SpellCheck Functionality is not working in case of multicore configuration in Solr 1.4.
        // TODO remove this code when we move to Solr 1.5 and make the appropriate changes in the query sent to Solr.
        if ((indexSearchInput.getCollections()).size() > 1) {
            setToQuery(query, "shards", coreNames);
        }
    }

    private void addFetchFields(Object query) throws WTException {
        List<String> fetchFields = indexSearchInput.getFetchFieldsList();
        if (fetchFields != null) {
            for (String solrField : fetchFields) {
                addField(query, solrField);
            }
        }
    }

    /**
     * Generate solr query string from keyword
     * 
     * @param keyword
     *            Keyword entered by the user
     * @return List of Solr Queries to be merged to form a single Solr Query.
     */
    private List<String> getKeywordQuery(String keyword) throws WTException {
        List<String> queryList = new ArrayList<String>();
        // For Advanced Users, we are not going to build the query term
        // (also not going convert to lower case as this would restrict the use of special operators like "AND", "OR",
        // "NOT", etc.)
        // An advanced user must take care that he uses lowercase in his Wildcard query string)
        if (queryType == IndexSearchUtils.SEARCH_ADVANCED) {
            // SPR 2026484: For advanced user just append whatever is entered as user is advanced and supposed to know
            // all abt solr query syntax, special chars etc
            queryList.add(keyword);
            return queryList;
        }

        keyword = keyword.toLowerCase().trim();

        String queryField = null;
        if (IndexSearchUtils.isScopedSearch(keyword)) {
            // xml Query e.g. xml:state:city:name(pune)
            indexLogger.debug("XML Query");
            try {
                queryType = IndexSearchUtils.XML_QUERY;
                XMLQueryHelper xmlSearchHelper = new XMLQueryHelper();
                xmlSearchHelper.setXMLQueryString(keyword);
                return xmlSearchHelper.getSolrQuery();
            } catch (XMLQueryParseException xmlParseException) {
                throw new WTException(xmlParseException);
            }
        }
        if (keyword.contains("=")) {
            // parametric search query e.g. name=pune
            indexLogger.debug("keyword contains '='");
            List<Object> determinedQuery = new ArrayList<Object>();
            determinedQuery = IndexSearchUtils.determineQueryType(keyword, queryType);
            queryField = determinedQuery.get(0).toString();
            queryType = (Integer) determinedQuery.get(1);
        }

        keyword = IndexSearchUtils.escapeLuceneChars(keyword);
        indexLogger.debug("keyword after escape : " + keyword);
        if (queryType == IndexSearchUtils.PARAMETRIC_KEYWORD) {
            indexLogger.debug("Parametric Query");
            keyword = IndexSearchUtils.buildQueryTerm(keyword, searchOperator);
            String langField = getTransQueryField(queryField);
            queryList.add(IndexSearchUtils.getSolrCriteriaFromKeyword(keyword, langField));
            return queryList;
        }

        String query = getLangugeQuery(keyword, IndexConstants.INDEXING_LANGUAGE_LIST);

        queryList.add(query + getEnumInternalValuesQuery());
        return queryList;
    }

    /**
     * Get Translation Query field for the given query field. Return same query field if translations are not done.
     * 
     * @param queryField
     * @return
     */
    private String getTransQueryField(String queryField) {
        queryField = translationHelper.getTransQueryField(queryField);
        return queryField;
    }

    private boolean appendOperator(boolean appendOperator, StringBuilder queryBuilder, String searchOp) {
        if (appendOperator) {
            queryBuilder.append(searchOp);
        } else {
            appendOperator = true;
        }
        return appendOperator;
    }

    /**
     * Loops through all the configured indexing languages and creates a Solr query to match the keyword against all
     * languages.
     * 
     * @param token
     *            the token
     * @param INDEXING_LANGUAGE_LIST
     *            the indexing language list
     * @return the lang sub query
     * @throws WTException
     *             the wT exception
     */
    private String getLangSubQuery(String token, List<String> INDEXING_LANGUAGE_LIST)
            throws WTException {

        StringBuilder result = new StringBuilder();
        String tokenSansLeadingWildcard = IndexSearchUtils.removeLeadingWildcard(token);

        // check if valid token
        if (!tokenSansLeadingWildcard.isEmpty()) {
            result.append("(");
            // 1. handle leading wildcard token
            if (!token.equals(tokenSansLeadingWildcard)) {
                appendLeadingWildcardSubquery(result, token);
                result.append(OR);
            }

            // 2. handle normal token
            token = tokenSansLeadingWildcard;
            appendNormalTokenSubQuery(result, token, INDEXING_LANGUAGE_LIST);

            result.append(")");
        }

        return result.toString();
    }

    /**
     * Appends leading wildcard token subquery
     * 
     * @param queryBuilder
     * @param token
     * @throws WTException
     */
    private void appendLeadingWildcardSubquery(StringBuilder queryBuilder, String token) throws WTException {
        // String termForQuery = IndexSearchUtils.getLeadingTrailingWildcardQuery(token);

        String query = IndexSearchUtils.getQueryTerm(token, SolrIndexConstants.LEADING_WILDCARD_FIELD,
                searchOperator);

        String translationQuery = translationHelper.buildLeadingWildcardTranslationSubQuery(token);

        String result = query + OR + translationQuery;
        queryBuilder.append(result);
    }

    /**
     * Get language specific queries for all languages marked against the language list in wt.properties file.
     * 
     * @param keyword
     *            The keyword entered by the user
     * @param INDEXING_LANGUAGE_LIST
     *            Language list defined in the wt.properties file.
     * @return Solr Query for all configured indexing languages for the same keyword.
     * @throws WTException
     */
    protected String getLangugeQuery(String keyword, List<String> INDEXING_LANGUAGE_LIST) throws WTException {

        StringBuilder result = new StringBuilder();
        boolean appendOperator = false;

        keyword = IndexSearchUtils.removeLooseWildcard(keyword);
        String[] tokens;
        // TODO: should split on same chars like word delimiter tokenizer
        
        int firstPosofDoubleQuote = keyword.indexOf(ESCAPED_DOUBLEQUOTE);
        int lastPosofDoubleQuote = keyword.lastIndexOf(ESCAPED_DOUBLEQUOTE);

        // If double quotes is present at the start and end of the keyword then
        // it's a phase query. In this case keyword should not be split in words
        if (firstPosofDoubleQuote == 0 && lastPosofDoubleQuote == keyword.length() - 2) {
            // Each double quote is bypassed by appending slash but for phase
            // query the double quote at the start and end should not be
            // bypassed
            keyword = keyword.substring(1, keyword.length() - 2) + DOUBLEQUOTE;
            tokens = new String[] { keyword };
        } else {

            tokens = keyword.split("\\s+");

        }
        boolean appendParen = tokens.length > 1;
        if (appendParen) {
            result.append("(");
        }
        for (String token : tokens) {

            String query = getLangSubQuery(token, INDEXING_LANGUAGE_LIST);
            appendOperator = appendOperator(appendOperator, result, searchOperator);
            result.append(query);

            /*
             * boolean hasStrippedLeadingWildcardTokens = false;
             * 
             * String leadingWildcardToken = null; String strippedLeadingWildcardToken = null; String normalToken =
             * null;
             * 
             * String termForQuery = token; // handle * in CJK - SPR 1820275 // SOLR does not return "abc*" result set
             * as super set of "abc" // So this will make OR query like ( abc* OR abc ), so we get superset result set.
             * // termForQuery = IndexSearchUtils.getLeadingTrailingWildcardQuery(token);
             * 
             * if (token.charAt(0) == '*' || token.charAt(0) == '?') { // leading wildcard query leadingWildcardToken =
             * termForQuery; // SPR 2127950: If a single character wildcard token like * or ? makes its way into the
             * code, then avoid // stripping to prevent errors. // Single character wildcard queries will be searched
             * only against the LEADING_WILDCARD_FIELD // strippedLeadingWildcardTokens will have all the tokens with
             * the leading wildcards stripped. However, // if the token is a single character wildcard, // there will
             * not be a corresponding entry in the list. If there is an entry in the list, set the flag // for query
             * generation to be done below. if (token.length() != 1) { strippedLeadingWildcardToken =
             * IndexSearchUtils.removeLeadingTralingWildCard(token); hasStrippedLeadingWildcardTokens = true; } } else {
             * termForQuery = IndexSearchUtils.getLeadingTrailingWildcardQuery(token); normalToken = termForQuery; }
             * 
             * String query = leadingWildcardToken == null ? null : IndexSearchUtils.getQueryTerm(leadingWildcardToken,
             * SolrIndexConstants.LEADING_WILDCARD_FIELD, searchOperator); String normalTerm = normalToken == null ?
             * null : getLangSubQuery(normalToken, INDEXING_LANGUAGE_LIST); // Generate this part of the query only if
             * there are entries in the stripped list. // If there are stripped entries then that means there are
             * leading wildcard tokens. All additional checks // can be // ignored. if
             * (hasStrippedLeadingWildcardTokens) { query = "(" + query + OR +
             * getLangSubQuery(strippedLeadingWildcardToken, INDEXING_LANGUAGE_LIST) + ")"; }
             * 
             * if (normalTerm != null && !normalTerm.isEmpty()) { if (query != null) { query = query + searchOperator +
             * normalTerm; } else { query = normalTerm; } }
             * 
             * appendOperator(appendOperator, result, searchOperator); result.append(query);
             */
        }
        if (appendParen) {
            result.append(")");
        }

        indexLogger.debug("Language Query=" + result);
        return result.toString();
    }

    /**
     * Subquery for token that does not have leading wildcard but may have trailing wildcard
     * 
     * @param subQueryBuilder
     * @param token
     * @param INDEXING_LANGUAGE_LIST
     * @throws WTException
     */
    private void appendNormalTokenSubQuery(StringBuilder subQueryBuilder, String token,
            List<String> INDEXING_LANGUAGE_LIST)
            throws WTException {

        token = IndexSearchUtils.getLeadingTrailingWildcardQuery(token);

        boolean appendOperator = false;
        // add query on keywords_XX fields
        for (String lang : INDEXING_LANGUAGE_LIST) {
            appendOperator = appendOperator(appendOperator, subQueryBuilder, OR);

            // append language query term
            subQueryBuilder.append(IndexSearchUtils.getQueryTerm(token, SolrIndexConstants.CONTENT_FIELD + "_"
                    + lang));
        }

        // add query on translations
        String translationSubQuery = translationHelper.buildKeywordLangSubQuery(token);
        if (!translationSubQuery.isEmpty()) {
            subQueryBuilder.append(OR);
            subQueryBuilder.append(translationSubQuery);
        }

    }

    private String getRelevancyCriteria(String queryTerm, int queryType) {
        queryTerm = IndexSearchUtils.escapeLuceneChars(queryTerm);
        StringBuilder relevanceTerms = new StringBuilder();

        // For Advanced users and Parametric Keyword searches, search is done only on the relevant fields
        // Field-wise boosting is disabled in such cases
        if (queryType != IndexSearchUtils.SEARCH_ADVANCED && queryType != IndexSearchUtils.PARAMETRIC_KEYWORD
                && queryType != IndexSearchUtils.XML_QUERY) {
            relevanceTerms.append("name:(").append(queryTerm).append(")^20 ");
            relevanceTerms.append("number:(").append(queryTerm).append(")^20 ");
        }

        StringBuilder queryString = new StringBuilder();

        // leave the advanced query alone. don't add freshness relavancy. Technically we can add it tough!
        if (queryType != IndexSearchUtils.SEARCH_ADVANCED) {
            Object oBoostingTerms = indexSearchInput.getRankBoostingStrings();
            if (oBoostingTerms instanceof Enumeration) {
                Enumeration boostingTerms = (Enumeration) oBoostingTerms;
                while (boostingTerms.hasMoreElements()) {
                    String term = (String) boostingTerms.nextElement();
                    appendRelevanceTerm(term, relevanceTerms);
                }
            } else {
                String[] boostingTerms = (String[]) oBoostingTerms;
                for (String term : boostingTerms) {
                    appendRelevanceTerm(term, relevanceTerms);
                }
            }

            if (relevanceTerms.length() > 0) {
                queryString.append(" ( ");
                queryString.append(relevanceTerms);
                queryString.append(")");
            }
            // need to see if we can put this in solrconfig.
            // refer Function Query Wiki: http://wiki.apache.org/solr/FunctionQuery
            queryString.append(" _val_:\"recip(rord(modifyTimestamp),1,1000,1000)\"^2");
        }
        return queryString.toString();
    }

    private void appendRelevanceTerm(String term, StringBuilder relevanceTerms) {
        // SPR 1888165
        String escaped = IndexSearchUtils.escapeLuceneChars(term);
        relevanceTerms.append("creator:(").append(escaped).append(")^2 modifier:(").append(escaped).append(")^2 ");
    }

    private Object getSolrQuery(String query) throws WTException {
        return solrService.getSolrQuery(query);
    }

    private Object addFilterQuery(Object query, String filter) throws WTException {
        return solrService.addFilterQuery(query, filter);
    }

    private Object setToQuery(Object query, String param, String value) throws WTException {
        return solrService.setToQuery(query, param, value);
    }

    private Object addField(Object query, String field) throws WTException {
        return solrService.addFieldToQuery(query, field);
    }

    private Object setRows(Object query, int rows) throws WTException {
        return solrService.setRowsToQuery(query, rows);
    }

    private Object setStart(Object query, int start) throws WTException {
        return solrService.setStart(query, start);
    }

    private void addSortCriteria(Object query) throws WTException {
        List<Object> sortAti = indexSearchInput.getSortAti();
        List sorted = indexSearchInput.getSortOrder();
        if (sortAti != null && sortAti.size() > 0) {
            for (int i = 0; i < sortAti.size(); i++) {
                Object sortAtt = sortAti.get(i);
                String attName = null;
                if (sortAtt instanceof String) {
                    attName = (String) sortAtt;
                } else {
                    // field name with _t
                    AttributeTypeIdentifier ati = (AttributeTypeIdentifier) sortAti.get(i);
                    attName = IndexSearchUtils.getIndexedAttributeName(ati,
                            IndexSearchUtils.getClassForAttributeType(ati));
                }
                indexLogger.debug("@@@ sort attribute name :" + attName);
                String order = (String) sorted.get(i);
                addSortField(query, attName, order);
            }
        }
    }

    private Object addSortField(Object query, String field, String order) throws WTException {
        return solrService.addSortField(query, field, order);
    }

    /**
     * Create lucene query string by reversing look up the name attribute internal value based on the query term. When
     * the query term matches the name attribute's internal value of the specific type, the query string returned.
     * Otherwise, the empty string is returned.
     * 
     * @return The enumerated name attribute internal values query.
     * @throws WTException
     */
    private String getEnumInternalValuesQuery() throws WTException {
        StringBuilder enumInternalValues = new StringBuilder("");

        // types
        String businessTypes = indexSearchInput.getBusinessTypes();
        // only name attribute enumeration constraint enabled, then reverse lookup internal name values
        if (ENABLE_NAME_ATTR_ENUM_CONSTRAINT && businessTypes != null && businessTypes.trim().length() > 0) {

            // search term
            String[] tokens = queryTerm.split("\\s+");

            for (String token : tokens) {
                String[] btArr = businessTypes.split(",");

                // Build up the businessType field for each type, and specify which names to match
                for (String element : btArr) {
                    // Right now FESTO project only support part and document type and epmdocument
                    TypeIdentifier tid = TypedUtility.getTypeIdentifier(element);
                    if (tid.isDescendedFrom(partTypeID) || tid.isDescendedFrom(documentTypeID)
                            || tid.isDescendedFrom(empDocumentTypeID)) {
                        AttributeTypeIdentifier ati = TypedUtility.getAttributeTypeIdentifier("name", tid);
                        String regex = wildcardToRegex(token);
                        String[] internalNameValues = TypedUtility.reverseLookup(ati,
                                Pattern.compile(regex, Pattern.CASE_INSENSITIVE), SessionHelper.getLocale()).toArray(
                                new String[0]);

                        if (indexLogger.isTraceEnabled()) {
                            StringBuffer traceMsg = new StringBuffer();
                            for (String internalNameValue : internalNameValues) {
                                traceMsg.append(internalNameValue + ",");
                            }
                            indexLogger.trace("Internal name values = " + traceMsg);
                        }
                        if (internalNameValues != null && internalNameValues.length > 0
                                && !Arrays.equals(internalNameValues, new String[] { token })) {
                            // first construct the businessType field
                            enumInternalValues.append("OR (+businessType:(");
                            enumInternalValues.append(element);
                            // then the name attribute field
                            enumInternalValues.append(") +(name:(");
                            int count = 0;
                            // for each found internal value, add it to the query
                            for (String internalNameValue : internalNameValues) {
                                String strValue = IndexSearchUtils.translateToSolrValue(internalNameValue);
                                strValue = IndexSearchUtils.getLeadingTrailingWildcardQuery(IndexSearchUtils
                                        .escapeLuceneChars(strValue));
                                if (count++ > 0) {
                                    enumInternalValues.append(OR);
                                }
                                enumInternalValues.append(strValue);
                            }
                            enumInternalValues.append(")");

                            // also make keyword search in this name attribute internal value query. keyword could be
                            // wild card.
                            // that can search the non-enumerated name attribute value.
                            enumInternalValues.append(" name:");
                            String strValue = IndexSearchUtils.translateToSolrValue(token.toLowerCase());
                            strValue = IndexSearchUtils.getLeadingTrailingWildcardQuery(IndexSearchUtils
                                    .escapeLuceneChars(strValue));
                            enumInternalValues.append(strValue);

                            // all done, so close the search term
                            enumInternalValues.append("))");
                        }
                    }
                }
            }
        }
        indexLogger.debug(" getEnumInternalValuesFilter = " + enumInternalValues);
        return enumInternalValues.toString();
    }

    /**
     * Convert wild card string to regular expression string
     * 
     * @param wildcard
     * @return The regular expression string
     */
    private static String wildcardToRegex(String wildcard) {
        StringBuffer s = new StringBuffer(wildcard.length());
        s.append("\\b");
        for (int i = 0, is = wildcard.length(); i < is; i++) {
            char c = wildcard.charAt(i);
            switch (c) {
            case '*':
                s.append(".*");
                break;
            case '?':
                s.append(".");
                break;
            // escape special regexp-characters
            case '(':
            case ')':
            case '[':
            case ']':
            case '$':
            case '^':
            case '.':
            case '{':
            case '}':
            case '|':
            case '\\':
                s.append("\\");
                s.append(c);
                break;
            default:
                s.append(c);
                break;
            }
        }
        s.append("\\b");
        return (s.toString());
    }

    public static boolean enableNameAttributeEnumConstraint() {
        return ENABLE_NAME_ATTR_ENUM_CONSTRAINT;
    }

    public static boolean isOverMaxSolrBooleanClauses(String queryString) {
        String[] orQuery = queryString.split("OR");
        String[] andQuery = queryString.split("AND");

        return (orQuery.length + 1 + andQuery.length + 1) > SOLR_MAX_BOOLEAN_CLAUSES;
    }

    public static boolean isTooManySorlBooleanClausesMsg(String message) {
        return TOO_MANY_SOLR_BOOLEAN_CLAUSES.equals(message);
    }
}
