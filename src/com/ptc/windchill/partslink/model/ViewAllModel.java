package com.ptc.windchill.partslink.model;

import java.util.List;

public class ViewAllModel {

	/** The filter queries. */
	private List<String> filterQueries;
	
	/** The facet fields list*/
	private List<String> facetFields;
	
	/** The stats fields list*/
	private List<String> stats;
	
	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Gets the filter queries.
	 *
	 * @return the filter queries
	 */
	public List<String> getFilterQueries() {
		return filterQueries;
	}
	
	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Sets the filter queries.
	 *
	 * @param filterQueries the new filter queries
	 */
	public void setFilterQueries(List<String> filterQueries) {
		this.filterQueries = filterQueries;
	}
	
	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Gets the facet field list.
	 *
	 * @return the facet field list
	 */
	public List<String> getFacetFields() {
		return facetFields;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Sets the facet field list.
	 *
	 * @param facetFields the facet field list
	 */
	public void setFacetFields(List<String> facetFields) {
		this.facetFields = facetFields;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Gets the stats field list.
	 *
	 * @return the stats field list
	 */
	public List<String> getStats() {
		return stats;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Sets the stats field list.
	 *
	 * @param stats the stats field list
	 */
	public void setStats(List<String> stats) {
		this.stats = stats;
	}

	@Override
	public String toString() {
		return "ViewAllModel : [ " +
			   " filterQueries = " + filterQueries +
			   " stats = " + stats +
			   " facetFileds = " + facetFields + "]";

	}
	
	
}
