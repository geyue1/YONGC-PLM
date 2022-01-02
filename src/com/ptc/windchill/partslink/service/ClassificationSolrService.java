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
package com.ptc.windchill.partslink.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import wt.index.SolrIndexConstants;
import wt.index.SolrService;
import wt.index.SolrServiceLocator;
import wt.log4j.LogR;
import wt.util.WTException;
import wt.util.WTMessage;

import com.infoengine.object.factory.Att;
import com.infoengine.object.factory.Element;
import com.ptc.windchill.partslink.PartslinkConstants.SearchType;
import com.ptc.windchill.partslink.partslinkClientResource;
import com.ptc.windchill.partslink.facet.Facet;
import com.ptc.windchill.partslink.facet.StatsInfo;
import com.ptc.windchill.partslink.model.IndexSearchResultModel;

/**
 * The Class ClassificationSolrService.
 */
public class ClassificationSolrService {

    /** The Constant RESOURCE. */
    private static final String RESOURCE = "com.ptc.windchill.partslink.partslinkClientResource";

    /** The Constant CLASSNAME. */
    private static final String CLASSNAME = ClassificationSolrService.class.getName();

    /** The Constant solrQueryTime. */
    private static final String solrQueryTime = "Solr Query";

    /** The performance data manager. */
    private static Class performanceDataManager = null;

    /** The Constant logger. */
    private static final Logger logger = LogR.getLogger(CLASSNAME);

    /** The clf search params. */
    private ClassificationSearchParams clfSearchParams;

    /** The core name. */
    private String coreName = null;
    
    /** Denotes the page from which Search is fired */
    private SearchType searchType = SearchType.UNSPECIFIED;

    /**
     * @return the searchType
     */
    public SearchType getSearchType() {
        return searchType;
    }

    /**
     * @param searchType the searchType to set
     */
    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the core name.
     * 
     * @return the core name
     */
    public String getCoreName() {
        return coreName;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the core name.
     * 
     * @param coreName
     *            the new core name
     */
    public void setCoreName(String coreName) {
        this.coreName = coreName;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the clf search params.
     * 
     * @return the clf search params
     */
    public ClassificationSearchParams getClfSearchParams() {
        return clfSearchParams;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the clf search params.
     * 
     * @param clfSearchParams
     *            the new clf search params
     */
    public void setClfSearchParams(ClassificationSearchParams clfSearchParams) {
        this.clfSearchParams = clfSearchParams;
    }

    /**
     * Gets the SolrService instance.
     * 
     * @return SolrService instance
     * @throws WTException
     *             the wT exception
     */
    private SolrService getSolrService() throws WTException {
        if (this.coreName != null)
            return SolrServiceLocator.getSolrService(this.coreName, false);
        else
            return SolrServiceLocator.getSolrService(SolrIndexConstants.DEFAULT_CORE, false);
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the results.
     * 
     * @param result
     *            the result
     * @return the results
     * @throws WTException
     *             the wT exception
     */
    private Object getResults(Object result) throws WTException {
        return getSolrService().getResults(result);
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the facet field.
     * 
     * @param result
     *            the result
     * @param name
     *            the name
     * @return the facet field
     * @throws WTException
     *             the wT exception
     */
    private Object getFacetField(Object result, String name) throws WTException {
        return getSolrService().getFacetField(result, name);
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the facet count map.
     * 
     * @param facetField
     *            the facet field
     * @return the facet count map
     * @throws WTException
     *             the wT exception
     */
    private Map<String, Integer> getFacetCountMap(Object facetField) throws WTException {
        return getSolrService().getFacetCountMap(facetField);
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Adds the field.
     * 
     * @param query
     *            the query
     * @param fields
     *            the fields
     * @return the object
     * @throws WTException
     *             the wT exception
     */
    private Object addField(Object query, String[] fields) throws WTException {
        return getSolrService().setFields(query, fields);
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Adds the facet field.
     * 
     * @param query
     *            the query
     * @param fields
     *            the fields
     * @return the object
     * @throws WTException
     *             the wT exception
     */
    private Object addFacetField(Object query, String... fields) throws WTException {
        return getSolrService().addFacetField(query, fields);
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Adds the filter query.
     * 
     * @param query
     *            the query
     * @param fq
     *            the fq
     * @return the object
     * @throws WTException
     *             the wT exception
     */
    private Object addFilterQuery(Object query, String... fq) throws WTException {
        return getSolrService().addFilterQuery(query, fq);
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the facet Limit.
     * 
     * @param query
     *            the query
     * @param b
     *            the b
     * @return the object
     * @throws WTException
     *             the wT exception
     */
    private Object setFacetLimit(Object query, int limit) throws WTException {
        return getSolrService().setFacetLimit(query, limit);
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the facet.
     * 
     * @param query
     *            the query
     * @param b
     *            the b
     * @return the object
     * @throws WTException
     *             the wT exception
     */
    private Object setFacet(Object query, boolean b) throws WTException {
        return getSolrService().setFacet(query, b);
    }
    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the start.
     * 
     * @param query
     *            the query
     * @param start
     *            the start
     * @return the object
     * @throws WTException
     *             the wT exception
     */
    private Object setStart(Object query, int start) throws WTException {
        return getSolrService().setStart(query, start);
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the rows to query.
     * 
     * @param query
     *            the query
     * @param rows
     *            the rows
     * @return the object
     * @throws WTException
     *             the wT exception
     */
    private Object setRowsToQuery(Object query, int rows) throws WTException {
        return getSolrService().setRowsToQuery(query, rows);
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Adds the stats field facets.
     * 
     * @param query
     *            the query
     * @param field
     *            the field
     * @param facets
     *            the facets
     * @throws WTException
     *             the wT exception
     */
    private void addStatsFieldFacets(Object query, String field, String... facets) throws WTException {
        getSolrService().addStatsFieldFacets(query, field, facets);
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the stats info.
     * 
     * @param result
     *            the result
     * @param fieldName
     *            the field name
     * @return the stats info
     * @throws WTException
     *             the wT exception
     */
    private Map<String, Object> getStatsInfo(Object result, String fieldName) throws WTException {
        return getSolrService().getStatsInfo(result, fieldName);
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the get field statistics.
     * 
     * @param query
     *            the query
     * @param field
     *            the field
     * @throws WTException
     *             the wT exception
     */
    private void setGetFieldStatistics(Object query, String field) throws WTException {
        getSolrService().setGetFieldStatistics(query, field);
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Performs the search with the given query params and returns the facets count in the outputGroup.
     * 
     * @return the index result model
     * @throws WTException
     *             the wT exception
     */
    public IndexSearchResultModel search() throws WTException {
        // TODO Auto-generated method stub
        IndexSearchResultModel result = null;
        Object solrQuery = null;
        if (getClfSearchParams() != null) {
            try {
                long trackingTime = System.currentTimeMillis();

                solrQuery = setUpQuery();
                if (logger.isDebugEnabled()) {
                    logger.debug("Solr Query : " + solrQuery);
                }
                Object solrQueryResult = executeQuery(solrQuery);
                logger.debug("Executed executeQuery in " + (System.currentTimeMillis() - trackingTime));
                trackingTime = System.currentTimeMillis();

                // after response of the query process the response to get the facet information
                result = processQueryResult(solrQueryResult);

                logger.debug("Executed processQuery in " + (System.currentTimeMillis() - trackingTime));
            } catch (Exception exception) {
                switch (searchType) {
                    case FREEFORM_SEARCH:
                        logger.error("Failed to query Solr: " + exception.getMessage());
                        if (logger.isDebugEnabled()) {
                            logger.debug(exception);
                        }
                        break;
    
                    default:
                        logger.error("Failed to query Solr: ", exception);
                        break;
                }
                throw new WTException(exception, new WTMessage(RESOURCE, partslinkClientResource.INDEX_ENGINE_CONNECT,
                        null));
            }
        }
        return result;

    }

    /**
     * Processes the query response and extracts the facet information from the response of solr query.
     * 
     * @param queryResult
     *            : the response of the solr query
     * @return outputGroup
     * @throws WTException
     *             the wT exception
     */
    private IndexSearchResultModel processQueryResult(Object queryResult) throws WTException {

        IndexSearchResultModel result = new IndexSearchResultModel();
        setNumFound(queryResult, result);

        String[] facetFields = getClfSearchParams().getFacetFields();
        String[] statsFields = getClfSearchParams().getStatsFields();

        if (facetFields != null)
            logger.debug("Facet fields from the search input : " + facetFields.toString());
        if (statsFields != null)
            logger.debug("Stats fields from the search input : " + statsFields.toString());

        Element facetEntry = new Element("facet_fields");
        facetEntry.addMeta("facet", "true");
        Map<String, Facet> facetFieldMap = null;
        if (facetFields != null && facetFields.length > 0)
        {
            facetFieldMap = new HashMap<String, Facet>();
            for (String facet : facetFields)
            {
                Object facetFieldFromResult = getFacetField(queryResult, facet);
                Map<String, Integer> facetMap = getFacetCountMap(facetFieldFromResult);

                Facet facetField = new Facet(facet, facetMap);
                facetFieldMap.put(facet, facetField);

            }
        }
        Map<String, StatsInfo> statsInfoMap = null;
        if (statsFields != null && statsFields.length > 0)
        {
            statsInfoMap = new HashMap<String, StatsInfo>();
            for (String field : statsFields)
            {
                Map<String, Object> statsInfo = getStatsInfo(queryResult, field);
                StatsInfo stats = null;//new StatsInfo();
                if (statsInfo != null) {
                	stats = new StatsInfo();
                	stats.setStatsFieldName(field);
                    stats.setCount((Long) statsInfo.get("count"));
                    stats.setMax((Double) statsInfo.get("max"));
                    stats.setMin((Double) statsInfo.get("min"));
                    stats.setMissing((Long) statsInfo.get("missing"));
                    stats.setSum((Double) statsInfo.get("sum"));
                }
                statsInfoMap.put(field, stats);

            }
        }

        result.setFacetFieldMap(facetFieldMap);
        result.setStatsInfoMap(statsInfoMap);

        if (facetFieldMap != null)
            logger.debug("Facet field map obtained from response of query : " + facetFieldMap.toString());
        if (statsInfoMap != null)
            logger.debug("Stats info map obtained from response of query : " + statsInfoMap.toString());

        // iterate over result
        List<Element> elements = new ArrayList<Element>();
        Object sdl = getResults(queryResult);
        Iterator docs = (Iterator) getIteratorFromSdl(sdl);
        for (int i = 0; docs.hasNext(); i++) {
            // get the next result document
            Object docSummary = docs.next();
            String ufid = getField(clfSearchParams.getIdField(), docSummary).toString(); // TODO: test

            Element currentElement;
            currentElement = addNewEntry(ufid, docSummary);
            if (currentElement != null) {
                elements.add(currentElement);
            }

        }
        result.setElements(elements);

        return result;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Adds the new entry.
     * 
     * @param ufid
     *            the ufid
     * @param docSummary
     *            the doc summary
     * @return the element
     * @throws WTException
     *             the wT exception
     */
    private Element addNewEntry(String ufid, Object docSummary) throws WTException
    {
        Element newEntry = new Element();
        newEntry.setUfid(ufid);
        newEntry.addAtt(new Att("obid", ufid));

        String[] fields = clfSearchParams.getFields();

        for (String field : fields) {
            switch (field) {
            case "businessType":
                newEntry.addAtt(new Att("class", getField("businessType", docSummary)));
                break;
            case "score":
                newEntry.addAtt(new Att("rank", getField("score", docSummary)));
                break;
            default:
                newEntry.addAtt(new Att(field, getField(field, docSummary)));
            }

        }

        return newEntry;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the field.
     * 
     * @param fieldName
     *            the field name
     * @param docSummary
     *            the doc summary
     * @return the field
     */
    private Object getField(String fieldName, Object docSummary) {
        Object value = null;

        try {
            value = getFieldValue(docSummary, fieldName);
        } catch (Exception e) {
            value = "";
        }

        if (value == null) {
            value = "";
        }
        return value;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the field value.
     * 
     * @param solrDocument
     *            the solr document
     * @param fieldName
     *            the field name
     * @return the field value
     * @throws WTException
     *             the wT exception
     */
    private Object getFieldValue(Object solrDocument, String fieldName) throws WTException {
        return getSolrService().getFieldValue(solrDocument, fieldName);
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the iterator from sdl.
     * 
     * @param sdl
     *            the sdl
     * @return the iterator from sdl
     * @throws WTException
     *             the wT exception
     */
    private Iterator getIteratorFromSdl(Object sdl) throws WTException {
        return getSolrService().getIteratorFromSdl(sdl);
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the num found.
     * 
     * @param queryResult
     *            the query result
     * @param result
     *            the result
     * @throws WTException
     *             the wT exception
     */
    private void setNumFound(Object queryResult, IndexSearchResultModel result) throws WTException {
        Object solrResults = getSolrService().getResults(queryResult);
        long numFound = getSolrService().getNumFound(solrResults);
        result.setNumFound(numFound);
    }

    /**
     * This method builds the solr query to be executed.
     * 
     * @return SolrQuery object
     * @throws WTException
     *             the wT exception
     */
    private Object setUpQuery() throws WTException {

        StringBuilder queryString = new StringBuilder();
        queryString.append(getClfSearchParams().getQueryString());
        logger.debug("Query String is " + queryString.toString());
        Object query = getSolrService().getSolrQuery(queryString.toString());
        String[] fields = getClfSearchParams().getFields();
        String[] facetField = getClfSearchParams().getFacetFields();
        String[] filterQueries = getClfSearchParams().getFilterQueries();
        String[] statsFields = getClfSearchParams().getStatsFields();

        if (fields != null && fields.length > 0)
        {
            query = addField(query, fields);
        }
        if (facetField != null && facetField.length > 0)
        {
            query = addFacetField(query, facetField);
            query = setFacet(query, true);
            query = setFacetLimit(query,-1);
        }
        if (filterQueries != null && filterQueries.length > 0)
        {
            query = addFilterQuery(query, filterQueries);
        }
        if (statsFields != null && statsFields.length > 0)
        {
            for (String field : statsFields)
            {
                setGetFieldStatistics(query, field);
            }
        }
        query = setStart(query, getClfSearchParams().getStart());
        query = setRowsToQuery(query, getClfSearchParams().getRows());
        logger.debug("Solr query : " + query);
        return query;
    }

    /**
     * Executes the solr query passed as param.
     * 
     * @param query
     *            the query
     * @return QueryResponse object
     * @throws Exception
     *             the exception
     */
    private Object executeQuery(Object query) throws Exception {
        if (logger.isDebugEnabled()) {
            try {
                logger.debug(java.net.URLDecoder.decode(query.toString(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
            }
        }

        long start = System.currentTimeMillis();
        Object result = null;
        if (this.coreName != null)
            result = SolrServiceLocator.query(this.coreName, query);
        else
            result = SolrServiceLocator.query(SolrIndexConstants.DEFAULT_CORE, query);

        long end = System.currentTimeMillis();
        if (logger.isDebugEnabled())
            logger.debug("Solr query execution time= " + (end - start) + "ms");

        return result;
    }

}
