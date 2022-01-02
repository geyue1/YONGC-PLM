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

import com.ptc.core.lwc.common.ScreenDefinitionName;
import com.ptc.jca.mvc.builders.TypedAttributesPanelBuilder;
import com.ptc.mvc.components.AttributePanelConfig;
import com.ptc.mvc.components.ComponentParams;
import com.ptc.mvc.components.TypeBased;
import com.ptc.mvc.components.TypedAttrLayOutFactory;
import com.ptc.netmarkets.util.beans.NmCommandBean;

@TypeBased(value = "wt.part.WTPart")
public class PartTypedAttributePanelBuilder extends TypedAttributesPanelBuilder {

    TypedAttrLayOutFactory tfactory;

    public void setTypedAttrLayOutFactory(TypedAttrLayOutFactory factory) {
        this.tfactory = factory;
    }

    /**
     * Returns the AttributePanelConfig for Typed Object
     * 
     * @see TypedAttrLayOutFactory#getAttributesPanelConfig
     */
    protected AttributePanelConfig buildAttributePanelConfig(
            ComponentParams params) throws WTException {

        NmCommandBean clientData = ((NmCommandBean) params.getAttribute("commandBean"));
        boolean isInsertScreen = false;

        if (clientData != null) {
            // get the insertNumber param to determine if needs to get the INSERT Part Layout            
            String insertNumber = (String)clientData.getRequest().getParameter("insertNumber");
            if (insertNumber != null && insertNumber.length() > 0) {
                isInsertScreen = true;
            }

        }                
        
        // "if" block takes care of retrieving INSERT layout whereas "else" block takes care of "CREATE" and "EDIT" part layout 
        if (isInsertScreen) {
            return tfactory.getAttributePanelConfig(getComponentConfigFactory(), params,
                    ScreenDefinitionName.INSERT);
        } else {
            super.setTypedAttrLayOutFactory(tfactory);
            return super.buildAttributePanelConfig(params);
        }

    }

}
