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
package com.ptc.jca.mvc.builders;

import wt.util.WTException;

import com.ptc.core.lwc.common.ScreenDefinitionName;
import com.ptc.core.ui.resources.ComponentMode;
import com.ptc.jca.mvc.components.AbstractAttributesComponentBuilder;
import com.ptc.mvc.components.AttributePanelConfig;
import com.ptc.mvc.components.ComponentParams;
import com.ptc.mvc.components.CustomizableViewConfig;
import com.ptc.mvc.components.TypeBased;
import com.ptc.mvc.components.TypedAttrLayOutFactory;
import com.ptc.mvc.components.TypedAttrLayOutFactoryAware;
import com.ptc.mvc.util.UriFormat;

/**
 * This builder creates attribute panels for layouts defined in the Type and Attributes Manager for the following screen
 * types:
 * <ul>
 * <li>Information Page &#8211 More Attributes (ScreenDefinitionName.MORE_ATTRIBUTES)</li>
 * <li>Create New (ScreenDefinitinonName.CREATE)</li>
 * <li>Edit (ScreenDefinitionName.UPDATE)</li>
 * </ul>
 * These panels are displayed by the following actions defined for the object type "object":
 * <ul>
 * <li>"attributes" - used to display the component typically labeled "More Attributes" on information pages</li>
 * <li>"defineItemAttributesWizStep" - indirectly invokes this builder to create the "Attributes" panel in object
 * creation wizards</li>
 * <li>"editAttributesWizStep" - indirectly invokes this builder to create the "Attributes" panel in object edit wizards
 * </li>
 * </ul>
 * <br/ <br/>
 * <br/>
 * This builder overrides the Component Type set by the parent class as follows:
 * <ul>
 * <li>ComponentType is set to ComponentType.INFO_ATTRIBUTES_TABLE if ComponentMode is VIEW</li>
 * <li>Otherwise, ComponentType is set to ComponentType.WIZARD_ATTRIBUTES_TABLE</li>
 * </ul>
 * <br/>
 * See the javadoc of the parent class for the ComponentMode, view, and datum object used for these panels. <br/>
 * <img src="../../../core/components/doc-files/ComponentBuilders.PNG"/> <BR>
 * <BR>
 * <B>Supported API: </B>true <BR>
 * <BR>
 * <B>Extendable: </B>true<BR>
 *
 * @see AbstractAttributesComponentBuilder
 */
@TypeBased(value = "wt.type.TypeManaged")
public class TypedAttributesPanelBuilder extends AbstractAttributesComponentBuilder implements
        TypedAttrLayOutFactoryAware {

    public static final String JSON_VIEW = "/components/attributePanel_json.jsp";

    TypedAttrLayOutFactory tfactory = null;

    public void setTypedAttrLayOutFactory(TypedAttrLayOutFactory factory) {
        this.tfactory = factory;
    }

    public TypedAttrLayOutFactory getTypedAttrLayOutFactory() {
        return this.tfactory;
    }

    @Override
    protected final CustomizableViewConfig buildAttributesComponentConfig(ComponentParams params) throws WTException {
        return buildAttributePanelConfig(params);
    }

    /**
     * Returns the AttributePanelConfig for Typed Object
     *
     * @see TypedAttrLayOutFactory#getAttributePanelConfig
     */
    protected AttributePanelConfig buildAttributePanelConfig(ComponentParams params) throws WTException {

        AttributePanelConfig attrPanelConfig = null;
        ComponentMode mode = getComponentMode(params);
        if (mode == ComponentMode.CREATE) {
            attrPanelConfig = tfactory.getAttributePanelConfig(getComponentConfigFactory(), params,
                    ScreenDefinitionName.CREATE);
        } else if (mode == ComponentMode.EDIT) {
            attrPanelConfig = tfactory.getAttributePanelConfig(getComponentConfigFactory(), params,
                    ScreenDefinitionName.UPDATE);
        } else {

            attrPanelConfig = tfactory.getAttributePanelConfig(getComponentConfigFactory(), params,
                    ScreenDefinitionName.MORE_ATTRIBUTES);
        }

        // handle view settings
        setView(attrPanelConfig, params);

        return attrPanelConfig;
    }

    protected void setView(AttributePanelConfig attrPanelConfig, ComponentParams params) {
        if (UriFormat.JSON.equals(params.getFormat())) {
            if (attrPanelConfig.getView() == null) {
                // set the default jsonView if not set
                attrPanelConfig.setView(JSON_VIEW);
            }
        }
    }
}
