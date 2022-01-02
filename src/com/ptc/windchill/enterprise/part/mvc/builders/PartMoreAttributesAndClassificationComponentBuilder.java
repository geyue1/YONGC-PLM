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
package com.ptc.windchill.enterprise.part.mvc.builders;

import wt.util.WTException;

import com.ptc.core.ui.resources.ComponentMode;
import com.ptc.core.ui.resources.ComponentType;
import com.ptc.mvc.components.AttributePanelConfig;
import com.ptc.mvc.components.ComponentBuilder;
import com.ptc.mvc.components.ComponentId;
import com.ptc.mvc.components.ComponentParams;
import com.ptc.mvc.components.TypeBased;

/**
 * Builder for the combined More Attributes layout / Classifications table.
 */
@ComponentBuilder(ComponentId.ATTRIBUTES_TABLE_ID)
@TypeBased("wt.part.WTPart")
public class PartMoreAttributesAndClassificationComponentBuilder extends
        PartTypedAttributePanelBuilder {

    /**
     * If a part has classifications associated with it, the classification table should be visible below the More
     * Attributes layout on the part info page Details tab.
     */
    @Override
    protected AttributePanelConfig buildAttributePanelConfig(
            ComponentParams params) throws WTException {
        AttributePanelConfig config = super.buildAttributePanelConfig(params);
        final String VIEW = "/part/partAttributePanel.jsp";

        if (ComponentMode.VIEW.equals(config.getComponentMode())
                && ComponentType.INFO_ATTRIBUTES_TABLE.equals(config.getComponentType())){
            config.setView("/part/attributesAndClassifications.jsp");
        }
        // SPR #2209737: Overriding the view for WTPart on CreatePartWizard.
        // This view contains a JS call that checks if the WTPart object selected has classification binding attribute
        // with some default value assigned, so to enable the Next step on the CreatePartWizard
        if (ComponentMode.CREATE.equals(config.getComponentMode())
                && ComponentType.WIZARD_ATTRIBUTES_TABLE.equals(config.getComponentType())){
            config.setView(VIEW);
        }
        return config;
    }
}
