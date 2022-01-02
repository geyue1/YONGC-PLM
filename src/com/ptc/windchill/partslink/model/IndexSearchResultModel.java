package com.ptc.windchill.partslink.model;

import java.util.List;
import java.util.Map;

import com.infoengine.object.factory.Element;
import com.ptc.windchill.partslink.facet.Facet;
import com.ptc.windchill.partslink.facet.StatsInfo;

/**
 * The Class IndexSearchResultModel.
 */
public class IndexSearchResultModel {

	/** The num found. */
	private long numFound;

	/** The facet field map. */
	private Map<String, Facet> facetFieldMap;	

	/** The stats info map. */
	private Map<String, StatsInfo> statsInfoMap;

	/** The elements. */
	private List<Element> elements;

	/**
	 * Instantiates a new index search result model.
	 */
	public IndexSearchResultModel(){}

	/**
	 * Instantiates a new index search result model.
	 *
	 * @param facetFieldMap the facet field map
	 * @param statsInfoMap the stats info map
	 */
	public IndexSearchResultModel(Map<String, Facet> facetFieldMap, Map<String, StatsInfo> statsInfoMap){
		this.facetFieldMap = facetFieldMap;
		this.statsInfoMap = statsInfoMap;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Gets the facet field map.
	 *
	 * @return the facet field map
	 */
	public Map<String, Facet> getFacetFieldMap() {
		return facetFieldMap;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Sets the facet field map.
	 *
	 * @param facetFieldMap the facet field map
	 */
	public void setFacetFieldMap(Map<String, Facet> facetFieldMap) {
		this.facetFieldMap = facetFieldMap;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Gets the stats info map.
	 *
	 * @return the stats info map
	 */
	public Map<String, StatsInfo> getStatsInfoMap() {
		return statsInfoMap;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Sets the stats info map.
	 *
	 * @param statsInfoMap the stats info map
	 */
	public void setStatsInfoMap(Map<String, StatsInfo> statsInfoMap) {
		this.statsInfoMap = statsInfoMap;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Gets the num found.
	 *
	 * @return the num found
	 */
	public long getNumFound() {
		return numFound;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Sets the num found.
	 *
	 * @param numFound the new num found
	 */
	public void setNumFound(long numFound) {
		this.numFound = numFound;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Gets the elements.
	 *
	 * @return the elements
	 */
	public List<Element> getElements() {
		return elements;
	}

	/**
	 * <BR><BR><B>Supported API: </B>false
	 * 
	 * Sets the elements.
	 *
	 * @param elements the new elements
	 */
	public void setElements(List<Element> elements) {
		this.elements = elements;
	}

}
