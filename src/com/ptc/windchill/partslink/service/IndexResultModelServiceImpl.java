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

import org.apache.log4j.Logger;

import wt.log4j.LogR;
import wt.util.WTException;
import wt.util.WTMessage;

import com.ptc.windchill.partslink.PartslinkConstants;
import com.ptc.windchill.partslink.PartslinkConstants.SearchType;
import com.ptc.windchill.partslink.partslinkClientResource;
import com.ptc.windchill.partslink.model.IndexSearchResultModel;

/**
 * The Class IndexResultModelServiceImpl.
 */
public class IndexResultModelServiceImpl implements IndexResultModelService {

	/** The Constant RESOURCE. */
	private static final String RESOURCE = "com.ptc.windchill.partslink.partslinkClientResource";

	/** The Constant logger. */
	private static final Logger logger = LogR.getLogger(IndexResultModelServiceImpl.class.getName());

	/**
	 * Set up the search parameters map required for building solr query.
	 * 
	 * @param clfSearchParams
	 *            the new query params
	 * @throws WTException
	 *             the wT exception
	 */
	private void setQueryParams(ClassificationSearchParams clfSearchParams) throws WTException {
		clfSearchParams.setQueryString("*:*");
		clfSearchParams.setStart(0);
		clfSearchParams.setRows(0);
		logger.debug("Query params are : " + clfSearchParams.toString());
	}

	/**
	 * This method returns the Result of map of facet fields with the facet count map and stats field.
	 * 
	 * @param clfSearchParams
	 *            the clf search params
	 * @return Result of map of facet fields with count map and stats fields.
	 * @throws WTException
	 *             the wT exception
	 */
	private IndexSearchResultModel getFacets(ClassificationSearchParams clfSearchParams) throws WTException {

		setQueryParams(clfSearchParams);
		ClassificationSolrService fc = new ClassificationSolrService();
		fc.setClfSearchParams(clfSearchParams);
		fc.setCoreName(PartslinkConstants.ClfSearchConstants.CLF_CORE);
		return fc.search();
	}

	/**
	 * This method returns the Result of map of facet fields with the facet count map. It can be used when only facet
	 * fields needs to be passed.
	 * 
	 * <br>
	 * Facet fields should be passed as a sting array. For e.g. <br>
	 * String[] facetFields = new String[]{"ptcClassification"};
	 * 
	 * @param facetFields
	 *            the facet fields
	 * @return Result of map of facet fields with count map.
	 * @throws WTException
	 *             the wT exception
	 */
	public IndexSearchResultModel getFacets(String[] facetFields) throws WTException {
		if (facetFields == null || facetFields.length == 0)
			throw new WTException(new WTMessage(RESOURCE, partslinkClientResource.QUERY_PARAMS_NOT_SET, null));

		ClassificationSearchParams clfSearchParams = new ClassificationSearchParams();
		clfSearchParams.setFacetFields(facetFields);
		return getFacets(clfSearchParams);
	}

	/**
	 * This method returns the Result of map of facet fields with the facet count map. It can be used when both facet
	 * fields and filter queries needs to be passed. <br>
	 * <br>
	 * Facet fields should be passed as a string array. For e.g. <br>
	 * String[] facetFields = new String[]{"ptcClassification"}; <br>
	 * <br>
	 * Filter queries should be passed as a string array. For e.g. <br>
	 * String[] filterQueries = new String[]{"ptcClassification:org.rnd.wtpartWTPart"}; <br>
	 * filterQueries array consist of String in format filterQueryField:filterValue
	 * 
	 * @param facetFields
	 *            the facet fields
	 * @param filterQueries
	 *            the filter queries
	 * @return Result of map of facet fields with count map.
	 * @throws WTException
	 *             the wT exception
	 */
	public IndexSearchResultModel getFacetsWithFilterQueries(String[] facetFields, String[] filterQueries)
			throws WTException {
		if (facetFields == null || facetFields.length == 0 || filterQueries == null || filterQueries.length == 0)
			throw new WTException(new WTMessage(RESOURCE, partslinkClientResource.QUERY_PARAMS_NOT_SET, null));

		ClassificationSearchParams clfSearchParams = new ClassificationSearchParams();
		clfSearchParams.setFacetFields(facetFields);
		clfSearchParams.setFilterQueries(filterQueries);
		return getFacets(clfSearchParams);
	}

	/**
	 * This method returns the Result of map of facet fields with the facet count map and also map of stats. It can be
	 * used when facet fields, filter queries and stats field needs to be passed. <br>
	 * <br>
	 * Facet fields should be passed as a string array. For e.g. <br>
	 * String[] facetFields = new String[]{"ptcClassification"}; <br>
	 * <br>
	 * Stats fields should be passed as a string array. For e.g. <br>
	 * String[] statsFields = new String[]{"weight"}; <br>
	 * <br>
	 * Filter queries should be passed as a string array. For e.g. <br>
	 * String[] filterQueries = new String[]{"ptcClassification:org.rnd.wtpartWTPart"}; <br>
	 * filterQueries array consist of String in format filterQueryField:filterValue
	 * 
	 * @param facetFields
	 *            the facet fields
	 * @param filterQueries
	 *            the filter queries
	 * @param stats
	 *            the stats
	 * @return Result of map of facet fields with count map and map of stats
	 * @throws WTException
	 *             the wT exception
	 */
	public IndexSearchResultModel getFacetsAndStatsWithFilterQuery(String[] facetFields, String[] filterQueries,
			String[] stats) throws WTException {

		if (facetFields == null || facetFields.length == 0 || filterQueries == null || filterQueries.length == 0
				|| stats == null || stats.length == 0) {
			throw new WTException(new WTMessage(RESOURCE, partslinkClientResource.QUERY_PARAMS_NOT_SET, null));
		}

		ClassificationSearchParams clfSearchParams = new ClassificationSearchParams();
		clfSearchParams.setFacetFields(facetFields);
		clfSearchParams.setFilterQueries(filterQueries);
		clfSearchParams.setStatsFields(stats);
		return getFacets(clfSearchParams);
	}

	/**
	 * This method returns the Result of map of stats fields. It can be used when only stats fields needs to be passed.
	 * 
	 * <br>
	 * Stats fields should be passed as a sting array. For e.g. <br>
	 * String[] statsFields = new String[]{"weight"};
	 * 
	 * @param stats
	 *            the stats
	 * @return Result of map of stats fields.
	 * @throws WTException
	 *             the wT exception
	 */
	public IndexSearchResultModel getStats(String[] stats) throws WTException {
		if (stats == null || stats.length == 0)
			throw new WTException(new WTMessage(RESOURCE, partslinkClientResource.QUERY_PARAMS_NOT_SET, null));

		ClassificationSearchParams clfSearchParams = new ClassificationSearchParams();
		clfSearchParams.setStatsFields(stats);
		return getFacets(clfSearchParams);
	}

	/**
	 * This method returns the Result of map of facet fields with the facet count map and map of stats. It can be used
	 * when both facet fields and stats field needs to be passed. <br>
	 * <br>
	 * Facet fields should be passed as a string array. For e.g. <br>
	 * String[] facetFields = new String[]{"ptcClassification"}; <br>
	 * <br>
	 * Stats fields should be passed as a sting array. For e.g. <br>
	 * String[] statsFields = new String[]{"weight"};
	 * 
	 * @param facetFields
	 *            the facet fields
	 * @param stats
	 *            the stats
	 * @return Result of map of facet fields with count map and map of stats
	 * @throws WTException
	 *             the wT exception
	 */
	public IndexSearchResultModel getFacetsAndStats(String[] facetFields, String[] stats) throws WTException {
		if (facetFields == null || facetFields.length == 0 || stats == null || stats.length == 0)
			throw new WTException(new WTMessage(RESOURCE, partslinkClientResource.QUERY_PARAMS_NOT_SET, null));

		ClassificationSearchParams clfSearchParams = new ClassificationSearchParams();
		clfSearchParams.setFacetFields(facetFields);
		clfSearchParams.setStatsFields(stats);
		return getFacets(clfSearchParams);
	}

	/**
	 * This method returns the Result of map of stats fields. It can be used when both stats fields and filter queries
	 * needs to be passed. <br>
	 * <br>
	 * Stats fields should be passed as a sting array. For e.g. <br>
	 * String[] statsFields = new String[]{"weight"}; <br>
	 * <br>
	 * Filter queries should be passed as a string array. For e.g. <br>
	 * String[] filterQueries = new String[]{"ptcClassification:org.rnd.wtpartWTPart"}; <br>
	 * filterQueries array consist of String in format filterQueryField:filterValue
	 * 
	 * @param stats
	 *            the stats
	 * @param filterQueries
	 *            the filter queries
	 * @return Result of map of stats fields.
	 * @throws WTException
	 *             the wT exception
	 */
	public IndexSearchResultModel getStatsWithFilterQueries(String[] stats, String[] filterQueries) throws WTException {
		if (stats == null || stats.length == 0 || filterQueries == null || filterQueries.length == 0)
			throw new WTException(new WTMessage(RESOURCE, partslinkClientResource.QUERY_PARAMS_NOT_SET, null));

		ClassificationSearchParams clfSearchParams = new ClassificationSearchParams();
		clfSearchParams.setStatsFields(stats);
		clfSearchParams.setFilterQueries(filterQueries);
		return getFacets(clfSearchParams);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ptc.windchill.partslink.service.IndexResultModelService#refineSearch()
	 */
	@Override
	public IndexSearchResultModel refineSearch() throws WTException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ptc.windchill.partslink.service.IndexResultModelService#getResultCount(java.lang.String[])
	 */
	@Override
	public IndexSearchResultModel getResultCount(SearchType searchType, String[] filterQueries, String[] facetFields, String[] stats)
			throws WTException {

		IndexSearchResultModel result = null;
		ClassificationSearchParams clfSearchParams = new ClassificationSearchParams();
		clfSearchParams.setFilterQueries(filterQueries);
		clfSearchParams.setFacetFields(facetFields);
		clfSearchParams.setStatsFields(stats);

		setQueryParams(clfSearchParams);
		ClassificationSolrService fc = new ClassificationSolrService();
		fc.setClfSearchParams(clfSearchParams);
		fc.setCoreName(PartslinkConstants.ClfSearchConstants.CLF_CORE);
		fc.setSearchType(searchType);
		result = fc.search();
		logger.debug("The map of facet fileds with count map is :" + result.getFacetFieldMap());
		return result;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ptc.windchill.partslink.service.IndexResultModelService#getResults(java.lang.String[])
	 */
	@Override
	public IndexSearchResultModel getResults(String[] filterQueries) throws WTException {
		return getResults(filterQueries, 0, 5000);		
	}

	/* (non-Javadoc)
	 * @see com.ptc.windchill.partslink.service.IndexResultModelService#getResults(java.lang.String[], int, int)
	 */
	@Override
	public IndexSearchResultModel getResults(String[] filterQueries, int start, int rows) throws WTException {
		IndexSearchResultModel result = null;
		ClassificationSearchParams clfSearchParams = new ClassificationSearchParams();
		setQueryParams(clfSearchParams);
		clfSearchParams.setStart(start);
		clfSearchParams.setRows(rows);
		clfSearchParams.setFilterQueries(filterQueries);
		clfSearchParams.setFields(new String[] { "obid", "ufid", "businessType", "score", "name", "number", "objectId"});

		ClassificationSolrService fc = new ClassificationSolrService();
		fc.setClfSearchParams(clfSearchParams);
		fc.setCoreName(PartslinkConstants.ClfSearchConstants.CLF_CORE);
		result = fc.search();

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ptc.windchill.partslink.service.IndexResultModelService#getResults(java.lang.String[])
	 */
	@Override
	public IndexSearchResultModel getResults(String[] filterQueries, String idField, String[] lstFields, String coreName)
			throws WTException {

		IndexSearchResultModel result = null;
		ClassificationSearchParams clfSearchParams = new ClassificationSearchParams();
		clfSearchParams.setIdField(idField);
		setQueryParams(clfSearchParams);
		clfSearchParams.setRows(5000);
		clfSearchParams.setFilterQueries(filterQueries);
		clfSearchParams.setFields(lstFields);

		ClassificationSolrService fc = new ClassificationSolrService();
		fc.setClfSearchParams(clfSearchParams);
		fc.setCoreName(coreName);
		result = fc.search();

		return result;
	}

}
