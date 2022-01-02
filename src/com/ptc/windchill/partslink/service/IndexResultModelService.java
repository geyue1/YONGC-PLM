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

import wt.util.WTException;

import com.ptc.windchill.partslink.PartslinkConstants.SearchType;
import com.ptc.windchill.partslink.model.IndexSearchResultModel;

/**
 * The interface ClassificationQueryService.
 */
public interface IndexResultModelService {

	/**
	 * <BR>
	 * <BR>
	 * <B>Supported API: </B>false
	 * 
	 * Gets the facets.
	 * 
	 * @param facetFields
	 *            the facet fields
	 * @return the facets
	 * @throws WTException
	 *             throws WTException, if any.
	 */
	public IndexSearchResultModel getFacets(String[] facetFields) throws WTException;

	/**
	 * <BR>
	 * <BR>
	 * <B>Supported API: </B>false
	 * 
	 * Gets the facets with filter queries.
	 * 
	 * @param facetFields
	 *            the facet fields
	 * @param filterQueries
	 *            the filter queries
	 * @return the facets with filter queries
	 * @throws WTException
	 *             throws WTException, if any.
	 */
	public IndexSearchResultModel getFacetsWithFilterQueries(String[] facetFields, String[] filterQueries)
			throws WTException;

	/**
	 * <BR>
	 * <BR>
	 * <B>Supported API: </B>false
	 * 
	 * Gets the facets and stats with filter query.
	 * 
	 * @param facetFields
	 *            the facet fields
	 * @param filterQueries
	 *            the filter queries
	 * @param stats
	 *            the stats
	 * @return the facets and stats with filter query
	 * @throws WTException
	 *             throws WTException, if any.
	 */
	public IndexSearchResultModel getFacetsAndStatsWithFilterQuery(String[] facetFields, String[] filterQueries,
			String[] stats) throws WTException;

	/**
	 * <BR>
	 * <BR>
	 * <B>Supported API: </B>false
	 * 
	 * Gets the stats.
	 * 
	 * @param stats
	 *            the stats
	 * @return the stats
	 * @throws WTException
	 *             throws WTException, if any.
	 */
	public IndexSearchResultModel getStats(String[] stats) throws WTException;

	/**
	 * <BR>
	 * <BR>
	 * <B>Supported API: </B>false
	 * 
	 * Gets the facets and stats.
	 * 
	 * @param facetFields
	 *            the facet fields
	 * @param stats
	 *            the stats
	 * @return the facets and stats
	 * @throws WTException
	 *             throws WTException, if any.
	 */
	public IndexSearchResultModel getFacetsAndStats(String[] facetFields, String[] stats) throws WTException;

	/**
	 * <BR>
	 * <BR>
	 * <B>Supported API: </B>false
	 * 
	 * Gets the stats with filter queries.
	 * 
	 * @param stats
	 *            the stats
	 * @param filterQueries
	 *            the filter queries
	 * @return the stats with filter queries
	 * @throws WTException
	 *             throws WTException, if any.
	 */
	public IndexSearchResultModel getStatsWithFilterQueries(String[] stats, String[] filterQueries) throws WTException;

	/**
	 * <BR>
	 * <BR>
	 * <B>Supported API: </B>false
	 * 
	 * Refine search.
	 * 
	 * @return the index result model
	 * @throws WTException
	 *             throws WTException, if any.
	 */
	public IndexSearchResultModel refineSearch() throws WTException;

	/**
	 * <BR>
	 * <BR>
	 * <B>Supported API: </B>false
	 * 
	 * Gets the result count.
	 *
	 * @param filterQueries the filter queries
	 * @param facetFields the facet fields
	 * @param stats the stats
	 * @return the result count
	 * @throws WTException throws WTException, if any.
	 */
	public IndexSearchResultModel getResultCount( SearchType searchType, String[] filterQueries,
			String[] facetFields, String[] stats) throws WTException;

	/**
	 * <BR>
	 * <BR>
	 * <B>Supported API: </B>false
	 * 
	 * Gets the results.
	 * 
	 * @param filterQueries
	 *            the filter queries
	 * @return the results
	 * @throws WTException
	 *             throws WTException, if any.
	 */
	public IndexSearchResultModel getResults(String[] filterQueries) throws WTException;

	/**
	 * <BR>
	 * <BR>
	 * <B>Supported API: </B>false.
	 *
	 * @param filterQueries - the filter queries
	 * @param idField - id filed
	 * @param lstFields - list of fields for Solr query
	 * @param coreName - name of the core against which search is to be executed
	 * @return the results
	 * @throws WTException - throws WTException, if any.
	 */
	public IndexSearchResultModel getResults(String[] filterQueries, String idField, String[] lstFields, String coreName)
			throws WTException;

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Gets the results from row number start. Get the number of rows given by rows.
	 *
	 * @param filterQueries the filter queries
	 * @param start the start of result set
	 * @param rows the number of rows
	 * @return the results
	 * @throws WTException the wT exception
	 */
	public IndexSearchResultModel getResults(String[] filterQueries, int start,
			int rows) throws WTException;

}
