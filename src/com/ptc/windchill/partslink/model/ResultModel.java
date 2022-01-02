package com.ptc.windchill.partslink.model;

import java.util.List;
import java.util.Map;

import com.ptc.windchill.partslink.PartslinkConstants.SearchType;
import com.ptc.windchill.partslink.facet.Facet;
import com.ptc.windchill.partslink.facet.StatsInfo;

/**
 * The Class ResultModel.
 */
public class ResultModel {

    /** The class internal name. */
    private String classInternalName;

    /** The node display name. */
    private String nodeDisplayName;

    /** The filter queries. */
    private List<String> filterQueries;

    /** The result count. */
    private long resultCount;

    /** The facet field map. */
    private Map<String, Facet> facetFieldMap;

    /** The stats info map. */
    private Map<String, StatsInfo> statsInfoMap;

    /** The facet fields list */
    private List<String> facetFields;

    /** The stats fields list */
    private List<String> stats;
    
    /** Search type to indicate from which page the search was fired.*/
    private SearchType searchType = SearchType.UNSPECIFIED;
    
    /**
     * @return the searchType
     */
    public SearchType getSearchType() {
        return searchType;
    }

    /**
     * @param freeformSearch the searchType to set
     */
    public void setSearchType(SearchType freeformSearch) {
        this.searchType = freeformSearch;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the facet field list.
     * 
     * @return the facet field list
     */
    public List<String> getFacetFields() {
        return facetFields;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the facet field list.
     * 
     * @param facetFields
     *            the facet field list
     */
    public void setFacetFields(List<String> facetFields) {
        this.facetFields = facetFields;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the stats field list.
     * 
     * @return the stats field list
     */
    public List<String> getStats() {
        return stats;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the stats field list.
     * 
     * @param stats
     *            the stats field list
     */
    public void setStats(List<String> stats) {
        this.stats = stats;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the facet field map.
     * 
     * @return the facet field map
     */
    public Map<String, Facet> getFacetFieldMap() {
        return facetFieldMap;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the facet field map.
     * 
     * @param facetFieldMap
     *            the facet field map
     */
    public void setFacetFieldMap(Map<String, Facet> facetFieldMap) {
        this.facetFieldMap = facetFieldMap;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the stats info map.
     * 
     * @return the stats info map
     */
    public Map<String, StatsInfo> getStatsInfoMap() {
        return statsInfoMap;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the stats info map.
     * 
     * @param statsInfoMap
     *            the stats info map
     */
    public void setStatsInfoMap(Map<String, StatsInfo> statsInfoMap) {
        this.statsInfoMap = statsInfoMap;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the class internal name.
     * 
     * @return the class internal name
     */
    public String getClassInternalName() {
        return classInternalName;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the class internal name.
     * 
     * @param classInternalName
     *            the new class internal name
     */
    public void setClassInternalName(String classInternalName) {
        this.classInternalName = classInternalName;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the node display name.
     * 
     * @return the node display name
     */
    public String getNodeDisplayName() {
        return nodeDisplayName;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the node display name.
     * 
     * @param nodeDisplayName
     *            the new node display name
     */
    public void setNodeDisplayName(String nodeDisplayName) {
        this.nodeDisplayName = nodeDisplayName;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the filter queries.
     * 
     * @return the filter queries
     */
    public List<String> getFilterQueries() {
        return filterQueries;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the filter queries.
     * 
     * @param filterQueries
     *            the new filter queries
     */
    public void setFilterQueries(List<String> filterQueries) {
        this.filterQueries = filterQueries;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the result count.
     * 
     * @return the result count
     */
    public long getResultCount() {
        return resultCount;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the result count.
     * 
     * @param resultCount
     *            the new result count
     */
    public void setResultCount(long resultCount) {
        this.resultCount = resultCount;
    }

}
