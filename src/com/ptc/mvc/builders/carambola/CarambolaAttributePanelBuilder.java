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
package com.ptc.mvc.builders.carambola;

import wt.util.WTException;

import com.ptc.carambola.customization.examples.attributePanel.AttributePanelExampleObject;
import com.ptc.core.ui.resources.ComponentMode;
import com.ptc.jca.mvc.components.JcaGroupConfig;
import com.ptc.mvc.components.AbstractComponentBuilder;
import com.ptc.mvc.components.AttributeConfig;
import com.ptc.mvc.components.AttributePanelConfig;
import com.ptc.mvc.components.ComponentBuilder;
import com.ptc.mvc.components.ComponentConfig;
import com.ptc.mvc.components.ComponentConfigFactory;
import com.ptc.mvc.components.ComponentId;
import com.ptc.mvc.components.ComponentParams;
import com.ptc.mvc.components.GroupConfig;
import com.ptc.mvc.components.TypeBased;

@TypeBased(value = "com.ptc.carambola.customization.examples.attributePanel.AttributePanelExampleObject")
@ComponentBuilder(ComponentId.ATTRIBUTES_ID)
public class CarambolaAttributePanelBuilder extends AbstractComponentBuilder {
    protected AttributePanelConfig buildAttributePanelConfig(ComponentParams params) throws WTException {
        ComponentConfigFactory factory = getComponentConfigFactory();
        AttributePanelConfig attrPanelConfig = factory.newAttributePanelConfig();

        // add first group
        GroupConfig groupConfig = factory.newGroupConfig("testGrp1", "First Group", 1);
        groupConfig.addComponent(factory.newAttributeConfig("name", "Name", 0, 0));
        groupConfig.addComponent(factory.newAttributeConfig("version", "Version", 0, 1));
        AttributeConfig health = factory.newAttributeConfig("healthBenefits", "Health Benefits", 1, 0);
        health.setColSpan(2);
        groupConfig.addComponent(health);
        groupConfig.addComponent(factory.newAttributeConfig("inputTextAreaField", "Text Area A", 2, 0));
        groupConfig.addComponent(factory.newAttributeConfig("textDisplayOne", "Text Display One", 2, 1));
        groupConfig.addComponent(factory.newAttributeConfig("textDisplayTwo", "Text Display Two", 3, 0));
        attrPanelConfig.addComponent(groupConfig);

        // add second group
        GroupConfig groupConfig2 = factory.newGroupConfig("testGrp2", "Second Group", 2);
        AttributeConfig description = factory.newAttributeConfig("description", "Description", 0, 0);
        description.setColSpan(3);
        groupConfig2.addComponent(description);
        groupConfig2.addComponent(factory.newAttributeConfig("displayType", "Display Type", 1, 0));
        groupConfig2.addComponent(factory.newAttributeConfig("displayIdentifier", "Display Identifier", 1, 1));
        groupConfig2.addComponent(factory.newAttributeConfig("inputTextField", "Textbox A", 1, 2));
        attrPanelConfig.addComponent(groupConfig2);

        // This group is a simple list layout - note that the row and col postion are not set
        GroupConfig groupConfig3 = factory.newGroupConfig("testGrp3", "Third Group", 3);
        groupConfig3.addComponent(factory.newAttributeConfig("dateField", "Example Date"));
        groupConfig3.addComponent(factory.newAttributeConfig("testBooleanField", "Example Boolean"));
        groupConfig3.addComponent(factory.newAttributeConfig("comboBoxField", "Example ComboBox"));
        groupConfig3.addComponent(factory.newAttributeConfig("guiArray", "Example GuiComponent Array"));
        groupConfig3.addComponent(factory.newAttributeConfig("radioButtonGroup", "Example Radio Button Group"));
        groupConfig3.addComponent(factory.newAttributeConfig("checkbox", "Example Checkbox"));
        attrPanelConfig.addComponent(groupConfig3);

        // This group is configured with renderOnTop
        GroupConfig groupConfig4 = factory.newGroupConfig("testGrp4", "Fourth Group", 4);
        ((JcaGroupConfig) groupConfig4).setRenderOnTop(true);
        groupConfig4.addComponent(factory.newAttributeConfig("exampleName", "Example Name", 0, 0));
        groupConfig4.addComponent(factory.newAttributeConfig("exampleVersion", "Example Version", 0, 1));
        groupConfig4.addComponent(factory.newAttributeConfig("renderOnTopExample", "Is Render on top", 0, 2));
        groupConfig4.addComponent(factory.newAttributeConfig("exampleDateField", "Example Date", 1, 0));
        AttributeConfig exampleTextField = factory.newAttributeConfig("exampleTextField", "Example TextBox", 1, 1); 
        exampleTextField.setColSpan(2);
        groupConfig4.addComponent(exampleTextField);
        AttributeConfig cultivationTechnique = factory.newAttributeConfig("cultivationTechnique", "Cultivation Technique");
        cultivationTechnique.setColSpan(3);
        groupConfig4.addComponent(cultivationTechnique);
        groupConfig4.addComponent(factory.newAttributeConfig("exampleComboBoxField", "Example ComboBox", 3,0));
        groupConfig4.addComponent(factory.newAttributeConfig("exampleRadioButtonGroup", "Example Radio Button Group",3,1));
        AttributeConfig exampleTextArea = factory.newAttributeConfig("exampleTextArea", "Example Text Area");
        exampleTextArea.setColSpan(3);
        groupConfig4.addComponent(exampleTextArea);
        attrPanelConfig.addComponent(groupConfig4);

        // This group is to test the hyperlinks in colspan
        GroupConfig groupConfig5 = factory.newGroupConfig("testGrp5", "Fifth Group", 5);
        groupConfig5.addComponent(factory.newAttributeConfig("name", "Example Name", 0, 0));
        groupConfig5.addComponent(factory.newAttributeConfig("exampleVersion", "Example Version", 0, 1));
        groupConfig5.addComponent(factory.newAttributeConfig("exampleURLLink", "Example URL", 1, 0));
        groupConfig5.addComponent(factory.newAttributeConfig("exampleMultiValueArea", "Example Multi Value", 2, 0));
        AttributeConfig spanUrl = factory.newAttributeConfig("exampleURLLink", "Colspan URL", 3,0);
        spanUrl.setColSpan(3);
        groupConfig5.addComponent(spanUrl);
        AttributeConfig spanMulti = factory.newAttributeConfig("exampleMultiValueArea", "Colspan Multi Value", 5,0);
        spanMulti.setColSpan(3);
        groupConfig5.addComponent(spanMulti);
        groupConfig5.addComponent(factory.newAttributeConfig("exampleMultiValueField", "Example Multi Field", 6, 0));
        AttributeConfig spanMultiField = factory.newAttributeConfig("exampleMultiValueField", "Colspan Multi Field", 7,0);
        spanMultiField.setColSpan(3);
        groupConfig5.addComponent(spanMultiField);
        AttributeConfig spanMultiUrl = factory.newAttributeConfig("exampleMultiValueURL", "Example Multi URL", 4, 0);
        spanMultiUrl.setColSpan(3);
        groupConfig5.addComponent(spanMultiUrl);      
        attrPanelConfig.addComponent(groupConfig5);

        // By default the attribute panel would be VIEW mode
        // this builder is used in examples for several modes, VIEW/CREATE/EDIT, so use mode off the request if found
        String mode = (String) params.getAttribute(ComponentMode.class.getName());
        if (mode != null) {
            attrPanelConfig.setComponentMode(ComponentMode.valueOf(mode));
        }

        // The next line would not be used in production code: it is needed to create the About This
        // Example links at the bottom of the example page.  In production we would use the default view
        // and not set the view explicitly.
        attrPanelConfig.setView("/carambola/attributePanel/attributePanelWithAbout.jsp");
        
        return attrPanelConfig;
    }

    @Override
    public Object buildComponentData(ComponentConfig config, ComponentParams params) throws Exception {
        return new AttributePanelExampleObject();
    }
    
    @Override
    public ComponentConfig buildComponentConfig(ComponentParams params) throws WTException {
        return buildAttributePanelConfig(params);
    }
}
