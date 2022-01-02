/* bcwti
 *
 * Copyright (c) 2010 Parametric Technology Corporation (PTC). All Rights Reserved.
 *
 * This software is the confidential and proprietary information of PTC
 * and is subject to the terms of a software license agreement. You shall
 * not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement.
 *
 * ecwti
 */

package com.ptc.core.htmlcomp.tableview;

import com.ptc.core.htmlcomp.components.ConfigurableTableBuilder;
import com.ptc.core.htmlcomp.components.JCAConfigurableTableAdapter;
import com.ptc.core.htmlcomp.components.JCAMappable;
import com.ptc.mvc.components.ComponentBuilderResolver;
import com.ptc.mvc.components.ComponentConfigBuilder;


public class ConfigurableTableHelper {

    private static final org.apache.log4j.Logger log = wt.log4j.LogR.getLogger(ConfigurableTableHelper.class.getName());

    private static final class Singleton {
        private static final ConfigurableTableHelper instance = new ConfigurableTableHelper();
    }

    public static ConfigurableTableHelper getInstance() {
        return Singleton.instance;
    }

    private final ConfigurableTableFactory factory;
    private ComponentBuilderResolver resolver;
    private static String confgiurableLinkTableId;

    @SuppressWarnings("deprecation")
    private ConfigurableTableHelper() {
        ConfigurableTableFactory c = null;
        try {
            c = new ConfigurableTableFactory();
        }
        catch (Exception e) {
            log.error("Unable to initialize configurable table factory", e);
        }
        factory = c;
    }

    /**
     * Get the configurable table for the given id. The configurable table can either be
     * registered in Windchill application context, or by an MVC builder implementation
     * @param tableId
     * @return ConfigurableTable
     */
    public static ConfigurableTable getConfigurableTable(final String tableId) {
        confgiurableLinkTableId = tableId;
        return getInstance().get(tableId);
    }

    /**
     * Get the configurable table for the given id for a JSTable. A JSTable configurable table is ONLY registered in
     * Windchill application context so this method should not be used for non-JSTable.
     * 
     * @param tableId
     * @return ConfigurableTable
     */
    public static ConfigurableTable getJSTConfigurableTable(final String tableid) {
        return getInstance().factory.getConfigurableTable(tableid);
    }

    private ConfigurableTable get(String tableId) {
        if (log.isDebugEnabled()) {
            log.debug(" Getting configurableTable instance for tableId: " + tableId);
        }
        if (resolver != null) {
            try {
                ComponentConfigBuilder builder = resolver.resolveConfigBuilder(tableId, null);
                if (builder instanceof ConfigurableTableBuilder) {
                    if (log.isTraceEnabled()) {
                        log.trace("Found ConfigurableTableBuilder, NOT checking application context. ID: " + tableId);
                    }
                    ConfigurableTable jcaTable = ((ConfigurableTableBuilder)builder).buildConfigurableTable(tableId);
                    if (jcaTable instanceof JCAMappable) {
                        return new JCAConfigurableTableAdapter((JCAMappable)jcaTable);
                    }
                    return jcaTable;
                }
            }
            catch (Exception e) {
                log.error("Unable to build configurable table from MVC layer: " + tableId, e);
            }
        }
        return factory.getConfigurableTable(tableId);
    }

    public static boolean isTableConfigurable(String tableId){
        return getInstance().isConfigurable(tableId);
    }

    private boolean isConfigurable(String tableId) {
        ConfigurableTable ct = getConfigurableTable(tableId);
        boolean isConfigurable = (ct != null) ? true : false;
        if (log.isDebugEnabled()) {
            log.debug(" Table <" + tableId + "> is configurable: " + isConfigurable);
        }
        return isConfigurable;
    }

    /**
     * Enables the lookup of MVC table configurations, to determine if they have
     * configurable tables. This property is typically set via spring configuration
     * @param resolver
     */
    public void setResolver(ComponentBuilderResolver resolver) {
        this.resolver = resolver;
    }
    
    public static String getConfgiurableLinkTableId() { 
        return confgiurableLinkTableId;
    }
}
