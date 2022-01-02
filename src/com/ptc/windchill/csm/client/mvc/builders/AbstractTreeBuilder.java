package com.ptc.windchill.csm.client.mvc.builders;

import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTMessage;

import com.ptc.core.components.descriptor.DescriptorConstants;
import com.ptc.jca.mvc.components.JcaComponentConfig;
import com.ptc.mvc.components.AbstractComponentBuilder;
import com.ptc.mvc.components.ColumnConfig;
import com.ptc.mvc.components.ComponentConfig;
import com.ptc.mvc.components.ComponentParams;
import com.ptc.mvc.components.TableConfig;
import com.ptc.mvc.components.TreeConfig;
import com.ptc.windchill.csm.client.csmClientResource;
import com.ptc.windchill.csm.client.treeHandler.ClassificationTreeHandler;
import com.ptc.windchill.csm.common.CsmConstants;

public abstract class AbstractTreeBuilder extends AbstractComponentBuilder {

    private static final String COLUMN_ID = "name";

    @Override
    public Object buildComponentData(ComponentConfig arg0, ComponentParams arg1)
            throws Exception {
        return new ClassificationTreeHandler();
    }

    @Override
    public ComponentConfig buildComponentConfig(ComponentParams arg0)
            throws WTException {

        TableConfig table = getComponentConfigFactory().newTreeConfig();
        table.setId("CLF.tree");
        ((TreeConfig) table).setNodeColumn(COLUMN_ID);
        ((TreeConfig) table).setLabel(WTMessage.getLocalizedMessage(CsmConstants.CSM_CLIENT_RESOURCE,
                csmClientResource.CLASSIFICATION_TREE_TITLE, null, SessionHelper.getLocale()));

        table.setSingleSelect(false);
        ((JcaComponentConfig) table).setDescriptorProperty(DescriptorConstants.TableProperties.SELECTABLE, "true");
        ((JcaComponentConfig) table).setDescriptorProperty(DescriptorConstants.TableTreeProperties.EXPANSION,
                DescriptorConstants.TableTreeProperties.ONE_EXPAND);

        ColumnConfig col = getComponentConfigFactory().newColumnConfig(
                COLUMN_ID,
                WTMessage.getLocalizedMessage(CsmConstants.CSM_CLIENT_RESOURCE,
                        csmClientResource.CLASSIFICATION_TREE_COLUMN_NAME, null, SessionHelper.getLocale()), true);
        col.setDataUtilityId(getDataUtilityId(COLUMN_ID));
        col.setWidth(415);
        table.addComponent(col);

        return table;
    }

    public abstract String getDataUtilityId(String columnId);
}
