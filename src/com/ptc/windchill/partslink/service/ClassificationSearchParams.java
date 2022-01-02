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

/**
 * The Class ClassificationSearchParams.
 */
public class ClassificationSearchParams {

    /** The query string. */
    private String queryString;

    /** The start. */
    private int start = 0;

    /** The rows. */
    private int rows = 0;

    /** The facet fields. */
    private String[] facetFields = null;

    /** The filter queries. */
    private String[] filterQueries = null;

    /** The stats fields. */
    private String[] statsFields = null;

    /** The fields. */
    private String[] fields = null; // "obid,ufid,businessType,score";

    /** The id field for Solr query. */
    private String idField = "ufid";

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the fields.
     * 
     * @return the fields
     */
    public String[] getFields() {
        return fields;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the fields.
     * 
     * @param fields
     *            the new fields
     */
    public void setFields(String[] fields) {
        this.fields = fields;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the query string.
     * 
     * @return the query string
     */
    public String getQueryString() {
        return queryString;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the query string.
     * 
     * @param queryString
     *            the new query string
     */
    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the start.
     * 
     * @return the start
     */
    public int getStart() {
        return start;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the start.
     * 
     * @param start
     *            the new start
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the rows.
     * 
     * @return the rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the rows.
     * 
     * @param rows
     *            the new rows
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the facet fields.
     * 
     * @return the facet fields
     */
    public String[] getFacetFields() {
        return facetFields;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the facet fields.
     * 
     * @param facetFields
     *            the new facet fields
     */
    public void setFacetFields(String[] facetFields) {
        this.facetFields = facetFields;
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
    public String[] getFilterQueries() {
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
    public void setFilterQueries(String[] filterQueries) {
        this.filterQueries = filterQueries;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the stats fields.
     * 
     * @return the stats fields
     */
    public String[] getStatsFields() {
        return statsFields;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the stats fields.
     * 
     * @param statsFields
     *            the new stats fields
     */
    public void setStatsFields(String[] statsFields) {
        this.statsFields = statsFields;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Gets the id field for Solr query.
     * 
     * @return the id field for Solr query
     */
    public String getIdField() {
        return idField;
    }

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * Sets the id field for Solr query.
     * 
     * @param idField
     *            the id field for Solr query
     */
    public void setIdField(String idField) {
        this.idField = idField;
    }

}
