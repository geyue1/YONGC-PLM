package com.ptc.windchill.partslink.client.mvc.builders;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import wt.log4j.LogR;
import wt.util.WTException;

import com.infoengine.object.factory.Element;
import com.ptc.windchill.partslink.PartslinkConstants;
import com.ptc.windchill.partslink.model.ResultModel;
import com.ptc.windchill.partslink.service.ResultModelService;
import com.ptc.windchill.partslink.service.ResultModelServiceImpl;
import com.ptc.windchill.partslink.utils.PartsLinkUtils;

/**
 * The Class PartslinkResultsStream.
 */
public class PartslinkResultsStream {

    /** The Constant logger. */
    private static final Logger logger = LogR.getLogger(PartslinkResultsStream.class.getName());
    
	/** The request. */
	private HttpServletRequest request = null;
	
	/* holds results fetched from Solr */
	/** The search results. */
	private List<Element> searchResults;
	
	// variables for getting next page from results
	/** The chunk size. */
	private int chunkSize = 100;
	
	/** The from index. */
	private int fromIndex = 0;	
	
	/** The result model. */
	private ResultModel resultModel=null;	
	
    /** The result model service. */
    private ResultModelService resultModelService = new ResultModelServiceImpl();
	
    // variables for fetching results from solr
	/** The start. */
    private int start = 0;
	
	/** The rows. */
	private int rows = 2001;
	    
	/** The b has next. */
	boolean bHasNext = true; 
			
	/**
	 * Instantiates a new partslink results stream.
	 *
	 * @param request the request
	 * @throws WTException the wT exception
	 */
	public PartslinkResultsStream(HttpServletRequest request) throws WTException {
		this.request = request;
		//this.searchResults = (List<Element>) request.getAttribute("searchresult");
		
        resultModel = (ResultModel) request.getSession().getAttribute(
                PartslinkConstants.Model_IDS.RESULT_MODEL);
	
        if(resultModel==null) {
        	throw new WTException("Result model is null");
        }
        
        // fetch results one more than table size limit
        this.rows = getResultTableSizePref() + 1; 
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Next page.
	 *
	 * @return the list
	 * @throws WTException the wT exception
	 */
	public List<Element> nextPage() throws WTException{
		List<Element> elements = nextPageFromResults();
		if(elements==null) {
			// elements is null so fetch next results from Solr
			fetchSearchResults();
			if(searchResults!=null) {
				// got results so get elements
				elements = nextPageFromResults();
			}
		}		
		return elements;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Checks for next.
	 *
	 * @return true, if successful
	 */
	public boolean hasNext() {
		return bHasNext;		
	}
	
	// All Methods below are private/protected
	
	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Gets the result table size pref.
	 *
	 * @return the result table size pref
	 * @throws WTException the wT exception
	 */
	protected int getResultTableSizePref() throws WTException {
		return PartsLinkUtils.getResultTableSizePref() ;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Fetch search results.
	 *
	 * @throws WTException the wT exception
	 */
	protected void fetchSearchResults() throws WTException {
        if (resultModel != null && bHasNext) {        	
            searchResults = resultModelService.getResults(resultModel, start, rows);
            // increment start for next fetch
            if(searchResults==null || searchResults.size()==0) {
            	logger.debug("Reached end of results");
            	bHasNext = false;
            	searchResults = null;            	            	
            } else {
            	start = start + searchResults.size();
            	logger.debug("searchResults.size="+searchResults.size());
            }
        }
        else {
            logger.error("resultModel retrieved from request is null.");
        }
        // reset fromIndex for nextPageFromResults
        fromIndex = 0;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Next page from results.
	 *
	 * @return the list
	 */
	protected List<Element> nextPageFromResults(){
		// return if searchResults is null
		if(searchResults==null) {
			return null;
		}
		int size = searchResults.size();
		if(fromIndex>=size) {
			return null;
		}
		int toIndex = fromIndex+chunkSize;
		if(toIndex > size) {
			toIndex = size;
		}
		List<Element> results = searchResults.subList(fromIndex, toIndex);
		fromIndex = toIndex;
		return results;
	}
	
	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Sets the search results.
	 *
	 * @param searchResults the new search results
	 */
	protected void setSearchResults(List<Element> searchResults) {
		this.searchResults = searchResults;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Sets the chunk size.
	 *
	 * @param chunkSize the new chunk size
	 */
	protected void setChunkSize(int chunkSize) {
		this.chunkSize = chunkSize;
	}
	
	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Sets the result model service.
	 *
	 * @param resultModelService the new result model service
	 */
	protected void setResultModelService(ResultModelService resultModelService) {
		this.resultModelService = resultModelService;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Gets the start.
	 *
	 * @return the start
	 */
	protected int getStart() {
		return start;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Gets the rows.
	 *
	 * @return the rows
	 */
	protected int getRows() {
		return rows;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Sets the rows.
	 *
	 * @param rows the new rows
	 */
	protected void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Sets the result model.
	 *
	 * @param resultModel the new result model
	 */
	protected void setResultModel(ResultModel resultModel) {
		this.resultModel = resultModel;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Sets the start.
	 *
	 * @param start the new start
	 */
	protected void setStart(int start) {
		this.start = start;
	}
	
	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Sets the checks for next.
	 *
	 * @param bHasNext the new checks for next
	 */
	protected void setHasNext(boolean bHasNext){
		this.bHasNext = bHasNext;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Gets the from index.
	 *
	 * @return the from index
	 */
	protected int getFromIndex() {
		return fromIndex;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Sets the from index.
	 *
	 * @param fromIndex the new from index
	 */
	protected void setFromIndex(int fromIndex) {
		this.fromIndex = fromIndex;
	}
	
}
