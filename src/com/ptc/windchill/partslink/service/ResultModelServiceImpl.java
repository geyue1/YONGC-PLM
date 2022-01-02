package com.ptc.windchill.partslink.service;

import java.util.List;

import org.apache.log4j.Logger;

import wt.log4j.LogR;
import wt.util.WTException;

import com.infoengine.object.factory.Element;
import com.ptc.windchill.partslink.PartslinkConstants.SearchType;
import com.ptc.windchill.partslink.model.IndexSearchResultModel;
import com.ptc.windchill.partslink.model.ResultModel;

/**
 * The Class ResultModelService.
 */
public class ResultModelServiceImpl implements ResultModelService {

	/** The Constant logger. */
	private static final Logger logger = LogR.getLogger(ResultModelServiceImpl.class.getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ptc.windchill.partslink.service.ResultModelService#getResultModel(com.ptc.windchill.partslink.model.RefineModel
	 * )
	 */
	@Override
	public ResultModel query(ResultModel resultModel) throws WTException {
		logger.debug("Creating ResultModel");
		long resultCount = getResultCount(resultModel);
		resultModel.setResultCount(resultCount);
		if (logger.isDebugEnabled()) {
			logger.debug("result count=" + resultCount);
			logger.debug("Returning ResultModel");
		}
		return resultModel;
	}

	/**
	 * <BR>
	 * <BR>
	 * <B>Supported API: </B>false
	 * 
	 * Gets the result count.
	 * 
	 * @param resultModel
	 *            the result model
	 * @return the result count
	 * @throws WTException
	 *             the wT exception
	 */
	public static long getResultCount(ResultModel resultModel) throws WTException {
		IndexResultModelService clfService = new IndexResultModelServiceImpl();
		
		List<String> queries = resultModel.getFilterQueries();
		String[] filterQueries = new String[queries.size()];
		queries.toArray(filterQueries);

		String[] stats = null;
		if (resultModel.getStats() != null && resultModel.getStats().size() > 0) {
			stats = new String[resultModel.getStats().size()];
			resultModel.getStats().toArray(stats);
		}

		String[] facets = null;
		if (resultModel.getFacetFields() != null && resultModel.getFacetFields().size() > 0) {
			facets = new String[resultModel.getFacetFields().size()];
			resultModel.getFacetFields().toArray(facets);
		}
		
		SearchType searchType = resultModel.getSearchType();

		IndexSearchResultModel result = clfService.getResultCount(searchType, filterQueries, facets, stats);
		resultModel.setFacetFieldMap(result.getFacetFieldMap());
		resultModel.setStatsInfoMap(result.getStatsInfoMap());

		return result.getNumFound();
	}

	/**
	 * <BR>
	 * <BR>
	 * <B>Supported API: </B>false
	 * 
	 * Gets the results.
	 * 
	 * @param model
	 *            the model
	 * @return the results
	 * @throws WTException
	 *             the wT exception
	 */
	@Override
	public List<Element> getResults(ResultModel model) throws WTException {
		return getResults(model, 0, 5000);		
	}

	/**
	 * <BR>
	 * <BR>
	 * <B>Supported API: </B>false
	 * 
	 * Gets the results.
	 * 
	 * @param model
	 *            the model
	 * @return the results
	 * @throws WTException
	 *             the wT exception
	 */
	@Override
	public List<Element> getResults(ResultModel model, int start, int rows) throws WTException {
		boolean isDebug = logger.isDebugEnabled();
		long resultFetchTime = 0;
		if (isDebug) {
			resultFetchTime = System.currentTimeMillis();
			logger.debug("start="+start+", rows="+rows);
		}

		List<Element> elements = null;

		if(rows>0) {
			IndexResultModelService clfService = new IndexResultModelServiceImpl();
			List<String> queries = model.getFilterQueries();
			String[] filterQueries = new String[queries.size()];
			queries.toArray(filterQueries);

			IndexSearchResultModel result = clfService.getResults(filterQueries, start, rows);

			elements = result.getElements();
		}

		if (isDebug) {
			logger.debug("Results were fetched in time " + (System.currentTimeMillis() - resultFetchTime));
		}

		return elements;
	}

}
