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
package com.ptc.jca.mvc.components;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import wt.util.WTException;

import com.ptc.core.components.descriptor.ComponentDescriptor;
import com.ptc.core.components.forms.CreateAndEditModelGetter;
import com.ptc.core.ui.resources.ComponentMode;
import com.ptc.core.ui.resources.ComponentType;
import com.ptc.mvc.components.AbstractComponentBuilder;
import com.ptc.mvc.components.ComponentBuilder;
import com.ptc.mvc.components.ComponentConfig;
import com.ptc.mvc.components.ComponentId;
import com.ptc.mvc.components.ComponentParams;
import com.ptc.mvc.components.CustomizableViewConfig;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.netmarkets.util.misc.NmContext;

/**
 * A base builder for a component that display attributes.
 * 
 * 
 * Though the component builder id is "attributesTable," it is recommended that subclasses return AttributePanelConfigs.
 * It is possible to return AttributeTableConfigs, but that class is deprecated.
 * </br></br>
 * The panels produced by subclasses of this builder have the following properties:
 * <ul>
 * <li> Component Mode
 *    <ul>
 *    <li> ComponentMode.CREATE will be used if the panel is included in a wizard whose InitializeItemTag specifies
 *         the operation CreateAndEditWizBean.CREATE </li>
 *    <li> ComponentMode.EDIT will be used if the panel is included in a wizard whose InitializeItemTag specifies
 *         the operation CreateAndEditWizBean.EDIT </li>
 *    <li> Otherwise, ComponentMode.VIEW will be used </li>
 *    </ul>
 * </li>
 * <li> Component Type
 *    <ul>
 *    <li> ComponentType is set to INFO_ATTRIBUTES_TABLE if ComponentMode is VIEW </li>
 *    <li> Otherwise, ComponentType is set to ComponentType.TABLE </li>
 *    </ul>
 * </li>
 * <li> Datum Object
 *     <ul>
 *     <li> If the  component mode is VIEW, a Persistable for the context object will be returned by the
 *          getComponentData() method</li>
 *     <li> If the component mode is CREATE, a TypeInstance for a new object of the selected type will be returned.
 *          The selected type is indicated by the TypeInstanceIdentifer stored in the request data by the initializeItem
 *          tag of the wizard.  This identifier has the key CreateAndEditWizBean.ITEM_TIID_PARAMETER_NAME. </li>
 *     <li> If the component mode is edit, a TypeInstance for the object being edited (working copy if Workable)
 *          will be returned. A reference to the object being edited is stored in the request data with the key
 *          CreateAndEditWizBean.WORKING_COPY_REF_PARAMETER_NAME if the object is a Workable or
 *          CreateAndEditWizBean. EDIT_OBJ_REF_PARAMETER_NAME if not.</li>
 *     </ul>
 *  </li>
 *  <li>View JSP
 *     <ul>
 *     <li> codebase/WEB-INF/jsp/components/attributePanel.jsp </li>
 *     </ul>
 *  </ul>
 *  </li>
 * <br/>
 * <img src="../../../core/components/doc-files/ComponentBuilders.PNG"/>
 * <BR>
 * 
 * <BR>
 * <B>Supported API: </B>true <BR>
 * <BR>
 * <B>Extendable: </B>true<BR>
 */
@ComponentBuilder(ComponentId.ATTRIBUTES_ID)
public abstract class AbstractAttributesComponentBuilder extends AbstractComponentBuilder {

    private static final Logger LOG = wt.log4j.LogR.getLogger(AbstractAttributesComponentBuilder.class.getName());

    @SuppressWarnings("unchecked")
    @Override
    public final Object buildComponentData(ComponentConfig config, ComponentParams params) throws Exception {
        ComponentMode mode = config.getComponentMode();
        if (LOG.isDebugEnabled()) {
            LOG.debug("ComponentMode :: " + mode);
        }
        // get the data based on the ComponentMode
        if (mode.equals(ComponentMode.CREATE) || mode.equals(ComponentMode.EDIT)) {
            JcaComponentParams jcaParams = (JcaComponentParams) params;
            ComponentDescriptor cdesc = jcaParams.getDescriptor();
            NmCommandBean commandBean = jcaParams.getHelperBean().getNmCommandBean();
            NmContext nmContext = jcaParams.getHelperBean().getNmContextBean().getContext();
            ArrayList atts = CreateAndEditModelGetter.getItemAttributes(cdesc, commandBean, nmContext);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Object Attributes  :: " + atts);
            }
            return atts;
        } else {
            Object contextObject = params.getContextObject();
            if (LOG.isDebugEnabled()) {
                LOG.debug("contextObject  :: " + contextObject);
            }
            return contextObject;
        }
    }

    @Override
    public final ComponentConfig buildComponentConfig(ComponentParams params) throws WTException {

        ComponentMode mode = getComponentMode(params);
        if (LOG.isDebugEnabled()) {
            LOG.debug("ComponentMode : " + mode);
        }

        CustomizableViewConfig config = buildAttributesComponentConfig(params);
        config.setComponentMode(mode);

        ComponentType type = getComponentType(params, mode);
        if (LOG.isDebugEnabled()) {
            LOG.debug("ComponentType : " + type);
        }
        if (type != null) {
            config.setComponentType(type);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("config : " + config);
        }
        return config;
    }

    /**
     * Get the ComponentMode for this panel <BR>
     * <BR>
     * <B>Supported API: </B>true<BR>
     * 
     * @param params
     * @return component mode
     */
    protected final ComponentMode getComponentMode(ComponentParams params) {

        return JcaComponentParamsUtils.getInstance().getComponentMode((JcaComponentParams) params);
    }

    /**
     * Get the ComponentType for this panel  <BR>
     * <BR>
     * <B>Supported API: </B>true<BR>
     * 
     * @param params
     * @return component mode
     */
    protected ComponentType getComponentType(ComponentParams params, ComponentMode mode) {
        // The default ComponentType for AttributePanel is ComponentType.TABLE so
        // using that value here to avoid regressions.  Not sure why that is the default
        // ComponentType??
        if (mode.equals(ComponentMode.VIEW)) {
            return ComponentType.INFO_ATTRIBUTES_TABLE;
        }
        return null;
    }

    /**
     * Builds the ComponentConfig for the component <BR>
     * <BR>
     * <B>Supported API: </B>true<BR>
     * 
     * @param params
     * @return
     * @throws WTException
     */
    protected abstract CustomizableViewConfig buildAttributesComponentConfig(ComponentParams params) throws WTException;
}
