package com.ptc.windchill.partslink.client.mvc.builders;

import java.util.Set;

import org.apache.log4j.Logger;

import wt.log4j.LogR;
import wt.util.WTException;
import wt.util.WTMessage;

import com.ptc.core.htmlcomp.components.AbstractConfigurableTableBuilder;
import com.ptc.core.htmlcomp.components.ConfigurableTableBuilder;
import com.ptc.core.htmlcomp.tableview.ConfigurableTable;
import com.ptc.mvc.components.ColumnConfig;
import com.ptc.mvc.components.ComponentBuilder;
import com.ptc.mvc.components.ComponentConfig;
import com.ptc.mvc.components.ComponentConfigFactory;
import com.ptc.mvc.components.ComponentParams;
import com.ptc.mvc.components.TableConfig;
import com.ptc.windchill.partslink.PartslinkConstants;
import com.ptc.windchill.partslink.partslinkClientResource;
import com.ptc.windchill.partslink.model.RefineBean;
import com.ptc.windchill.partslink.model.RefineModel;

/**
 * The Class RefineSearchAttributeTableBuilder.
 */
@ComponentBuilder("partslink.refine.attributeTableBuilder")
public class RefineSearchAttributeTableBuilder extends AbstractConfigurableTableBuilder implements
        ConfigurableTableBuilder {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogR.getLogger(RefineSearchAttributeTableBuilder.class.getName());

    /** The Constant RESOURCE. */
    private static final String RESOURCE = partslinkClientResource.class.getName();

    /** The Constant CSM_ATTRIBUTE_DISPLAY_NAME. */
    public static final String CSM_ATTRIBUTE_DISPLAY_NAME = "Attribute";

    /** The Constant SEARCH_UI_OPERATOR_COLUMN. */
    public static final String SEARCH_UI_OPERATOR_COLUMN = "Operators";

    /** The Constant SEARCH_UI_VALUE_COLUMN. */
    public static final String SEARCH_UI_VALUE_COLUMN = "Value";

    /** The Constant SEARCH_UI_RANGE_COLUMN. */
    public static final String SEARCH_UI_RANGE_COLUMN = "Range";

    /** The Constant SEARCH_UI_UNITS_COLUMN. */
    public static final String SEARCH_UI_UNITS_COLUMN = "Units";

    /*
     * (non-Javadoc)
     * 
     * @see com.ptc.mvc.components.ComponentDataBuilder#buildComponentData(com.ptc.mvc.components.ComponentConfig,
     * com.ptc.mvc.components.ComponentParams)
     */
    @Override
    public Object buildComponentData(ComponentConfig config, ComponentParams params) throws Exception {
        LOGGER.debug("IN RefineSearchUIBuilder.buildComponentData()");

        RefineModel model = (RefineModel) params.getAttribute(PartslinkConstants.Model_IDS.REFINE_MODEL);
        Set<RefineBean> attBeans = model.getRefineBeanSet();

        LOGGER.debug("OUT RefineSearchUIBuilder.buildComponentData()");

        return attBeans;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ptc.mvc.components.ComponentConfigBuilder#buildComponentConfig(com.ptc.mvc.components.ComponentParams)
     */
    @Override
    public ComponentConfig buildComponentConfig(ComponentParams params) throws WTException {
        LOGGER.debug("IN RefineSearchUIBuilder.buildComponentConfig()");

        RefineModel model = (RefineModel) params.getAttribute(PartslinkConstants.Model_IDS.REFINE_MODEL);
        String nodeDisplayName = model.getNodeDisplayName();

        ComponentConfigFactory factory = getComponentConfigFactory();
        TableConfig table = getComponentConfigFactory().newTableConfig();
        table.setLabel(WTMessage.getLocalizedMessage(RESOURCE, partslinkClientResource.REFINE_SEARCH_TBL_TITLE, null)
                + nodeDisplayName);
        table.setHelpContext("WCClassReuseSearchCriteriaRefine");

        // Add columns to the table
        ColumnConfig attributeColumn = factory.newColumnConfig(CSM_ATTRIBUTE_DISPLAY_NAME, true);
        attributeColumn.setLabel(WTMessage.getLocalizedMessage(RESOURCE,
                partslinkClientResource.CSM_ATTRIBUTE_COLUMN_HEADER, null));
        attributeColumn.setDataUtilityId("searchUIAttributeColumnUtility");
        table.addComponent(attributeColumn);

        ColumnConfig operatorsColumn = factory.newColumnConfig(SEARCH_UI_OPERATOR_COLUMN, false);
        operatorsColumn.setLabel(WTMessage.getLocalizedMessage(RESOURCE,
                partslinkClientResource.CSM_OPERATORS_COLUMN_HEADER, null));
        operatorsColumn.setDataUtilityId("searchUIOperatorColumnUtility");
        table.addComponent(operatorsColumn);

        ColumnConfig valueColumn = factory.newColumnConfig(SEARCH_UI_VALUE_COLUMN, false);
        valueColumn.setLabel(WTMessage.getLocalizedMessage(RESOURCE, partslinkClientResource.CSM_VALUE_COLUMN_HEADER,
                null));
        valueColumn.setDataUtilityId("searchUIValueColumnUtility");
        table.addComponent(valueColumn);

        ColumnConfig rangeColumn = factory.newColumnConfig(SEARCH_UI_RANGE_COLUMN, false);
        rangeColumn.setLabel(WTMessage.getLocalizedMessage(RESOURCE, partslinkClientResource.CSM_RANGE_COLUMN_HEADER,
                null));
        rangeColumn.setDataUtilityId("searchUIRangeColumnUtility");
        table.addComponent(rangeColumn);

        ColumnConfig unitsColumn = factory.newColumnConfig(SEARCH_UI_UNITS_COLUMN, false);
        unitsColumn.setLabel(WTMessage.getLocalizedMessage(RESOURCE, partslinkClientResource.CSM_UNITS_COLUMN_HEADER,
                null));
        unitsColumn.setDataUtilityId("searchUIUnitsColumnUtility");
        unitsColumn.setWidth(100);
        table.addComponent(unitsColumn);

        if (!model.getRefineSchematicURL().equals("")) {
            table.setActionModel("partsLinkRefineViewSchematic");
        }

        LOGGER.debug("OUT RefineSearchUIBuilder.buildComponentConfig()");

        return table;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ptc.core.htmlcomp.components.ConfigurableTableBuilder#buildConfigurableTable(java.lang.String)
     */
    @Override
    public ConfigurableTable buildConfigurableTable(String id) throws WTException {
        return null;
    }

}
