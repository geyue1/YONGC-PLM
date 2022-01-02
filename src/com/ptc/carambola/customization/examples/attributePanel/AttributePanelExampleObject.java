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
package com.ptc.carambola.customization.examples.attributePanel;

import java.util.ArrayList;
import java.util.Date;

import com.ptc.core.components.rendering.GuiComponent;
import com.ptc.core.components.rendering.guicomponents.AttributeInputComponent;
import com.ptc.core.components.rendering.guicomponents.CheckBox;
import com.ptc.core.components.rendering.guicomponents.ComboBox;
import com.ptc.core.components.rendering.guicomponents.GUIComponentArray;
import com.ptc.core.components.rendering.guicomponents.GuiComponentUtil.Delimiter;
import com.ptc.core.components.rendering.guicomponents.Label;
import com.ptc.core.components.rendering.guicomponents.MultiValuedInputComponent;
import com.ptc.core.components.rendering.guicomponents.RadioButton;
import com.ptc.core.components.rendering.guicomponents.RadioButtonGroup;
import com.ptc.core.components.rendering.guicomponents.StringInputComponent;
import com.ptc.core.components.rendering.guicomponents.TextArea;
import com.ptc.core.components.rendering.guicomponents.TextBox;
import com.ptc.core.components.rendering.guicomponents.TextDisplayComponent;
import com.ptc.core.components.rendering.guicomponents.UrlInputComponent;
import com.ptc.netmarkets.model.NmObject;
import com.ptc.netmarkets.model.NmSimpleOid;

public class AttributePanelExampleObject extends NmObject {
    private static final long serialVersionUID = 1L;

    private static final String name = "Attribute Panel Example";

    private static final String version = "A.2.1";

    private static final String healthBenefits = "Star fruit is one of the 'superfruits', it is rich in antioxidants and vitamin C, and low in sugar, sodium and acid. Star fruit is a potent source of both primary and secondary polyphenolic antioxidants.";

    private static final String description = "Carambola or Starfruit is the fruit of Averrhoa carambola, a species of tree native to Indonesia, India and Sri Lanka. The tree and fruit is popular throughout Southeast Asia, Malaysia and parts of East Asia. It is also grown throughout the tropics such as in Trinidad, Guyana SA and in the United States, in south Florida, most parts of Brazil and Hawaii. It is closely related to the bilimbi. The star shaped cross section gives the carambola its other common name, Star fruit.";

    private static final String displayType = "Example Panel Object";

    private static final String displayIdentifier = "Averrhoa carambola";

    private static final GuiComponent inputTextField = new TextBox();

    private static final GuiComponent inputTextAreaField = new TextArea();

    private static final GuiComponent comboBoxField = new ComboBox();

    private static final GuiComponent checkbox = new CheckBox();

    private static final boolean testBooleanField = true;

    private static final Date dateField = new Date();

    private static GuiComponent radioButtonGroup;

    private static GUIComponentArray guiArray;

    private static GuiComponent textDisplayOne;

    private static GuiComponent textDisplayTwo;
    
    private static GuiComponent exampleURLLink = new UrlInputComponent(); 
          
    private static GuiComponent exampleMultiValueField;
    
    private static GuiComponent exampleMultiValueArea;
    
    private static GuiComponent exampleMultiValueURL;

    /* renderOnTop example attributes below */
    private static final String exampleName = "Attribute Panel Example with render on top configured";

    private static final String exampleVersion = "A.1";

    private static final TextArea exampleTextArea = new TextArea();

    private static final String cultivationTechnique = "The carambola is a tropical and subtropical fruit. It can be grown at up to 4,000 feet (1,200 m) in elevation. It prefers full sun exposure, but requires enough humidity and a total of 70 inches or more of rainfall a year. It does not have a soil type preference, but it requires good drainage. Carambola trees are planted at least 20 feet (6.1 m) from each other and typically are fertilized three times a year. The tree grows rapidly and typically produces fruit at four or five years of age. The large amount of rain during spring actually reduces the amount of fruit, but, in ideal conditions, carambola can produce from 200 to 400 pounds (91 to 180 kg) of fruit a year. The fruit is harvested mainly during the months of June, July, and August, but sometimes year-round.";

    private static final boolean renderOnTopExample = true;

    private static final Date exampleDateField = new Date();

    private static final GuiComponent exampleCheckbox = new CheckBox();

    private static GuiComponent exampleRadioButtonGroup;

    private static final GuiComponent exampleComboBoxField = new ComboBox();

    private static final GuiComponent exampleTextField = new TextBox();
    
    public AttributePanelExampleObject() {
        this.setOid(new NmSimpleOid());
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getHealthBenefits() {
        return healthBenefits;
    }

    public String getDescription() {
        return description;
    }

    public String getDisplayType() {
        return displayType;
    }

    public String getDisplayIdentifier() {
        return displayIdentifier;
    }

    public GuiComponent getInputTextField() {
        ((TextBox) inputTextField).setId("testTextBoxID");
        ((TextBox) inputTextField).setName("testTextBoxName");
        return inputTextField;
    }

    public GuiComponent getInputTextAreaField() {
        ((TextArea) inputTextAreaField).setId("testTextAreaID");
        ((TextArea) inputTextAreaField).setName("testTextAreaName");
        ((TextArea) inputTextAreaField).setHeight(4);
        ((TextArea) inputTextAreaField).setWidth(30);
        return inputTextAreaField;
    }

    public GuiComponent getComboBoxField() {
        ArrayList<String> internalValues = new ArrayList<String>();
        internalValues.add("Option A");
        internalValues.add("Option B");
        internalValues.add("Option C");
        ((ComboBox) comboBoxField).setInternalValues(internalValues);
        ((ComboBox) comboBoxField).setName("Test Combo box");
        ((ComboBox) comboBoxField).setValues(internalValues);
        return comboBoxField;
    }

    public GuiComponent getCheckbox() {
        ((CheckBox) checkbox).setEnabled(true);
        ((CheckBox) checkbox).setName("Test Combobox");
        return checkbox;
    }

    public boolean getTestBooleanField() {
        return testBooleanField;
    }

    public Date getDateField() {
        return dateField;
    }

    public GUIComponentArray getGuiArray() {
        guiArray = new GUIComponentArray();
        guiArray.addGUIComponent(new Label("TestLabel"));
        TextDisplayComponent tdc = new TextDisplayComponent("textDisplay");
        tdc.setValue("Test text display component value.");
        guiArray.addGUIComponent(tdc);
        guiArray.setDelimiter(Delimiter.SPACE);
        return guiArray;
    }

    public GuiComponent getRadioButtonGroup() {
        radioButtonGroup = new RadioButtonGroup();
        RadioButton btn1 = new RadioButton();
        btn1.setLabel("Button One");
        btn1.setRenderLabel(true);
        btn1.setNameQualifier("Option_One");
        btn1.setColumnName("exampleRadioButtongroup");
        btn1.setId("id11");
        ((RadioButtonGroup) radioButtonGroup).addButton(btn1);

        RadioButton btn2 = new RadioButton();
        btn2.setLabel("Button Two");
        btn2.setRenderLabel(true);
        btn2.setNameQualifier("Option_Two");
        btn2.setColumnName("exampleRadioButtongroup");
        btn2.setId("id22");
        ((RadioButtonGroup) radioButtonGroup).addButton(btn2);

        RadioButton btn3 = new RadioButton();
        btn3.setLabel("Button Three");
        btn3.setRenderLabel(true);
        btn3.setNameQualifier("Option_Three");
        btn3.setColumnName("exampleRadioButtongroup");
        btn3.setId("id33");
        ((RadioButtonGroup) radioButtonGroup).addButton(btn3);

        return radioButtonGroup;
    }
    
    public GuiComponent getTextDisplayOne(){
    	textDisplayOne = new TextDisplayComponent("");
    	((TextDisplayComponent)textDisplayOne).setLabel("HiddenText");
    	((TextDisplayComponent)textDisplayOne).setValue("Test value");
    	((TextDisplayComponent)textDisplayOne).setComponentHidden(true);
    	return textDisplayOne;
    }
    
    public GuiComponent getTextDisplayTwo(){
    	textDisplayTwo = new TextDisplayComponent("");
    	((TextDisplayComponent)textDisplayTwo).setLabel("HiddenText Two");
    	((TextDisplayComponent)textDisplayTwo).setValue("Test value Two");
    	((TextDisplayComponent)textDisplayTwo).setComponentHidden(true);
    	return textDisplayTwo;
    }

    public String getExampleName() {
        return exampleName;
    }

    public String getExampleVersion() {
        return exampleVersion;
    }

    public TextArea getExampleTextArea() {
        ((TextArea) exampleTextArea).setId("exampleTestTextAreaID");
        ((TextArea) exampleTextArea).setName("exampleTestTextAreaName");
        ((TextArea) exampleTextArea).setHeight(4);
        ((TextArea) exampleTextArea).setWidth(30);
        return exampleTextArea;
    }

    public String getCultivationTechnique() {
        return cultivationTechnique;
    }

    public boolean isRenderOnTopExample() {
        return renderOnTopExample;
    }

    public GuiComponent getExampleCheckbox() {
        ((CheckBox) exampleCheckbox).setEnabled(true);
        ((CheckBox) exampleCheckbox).setName("Render on top checkbox");
        return exampleCheckbox;
    }

    public GuiComponent getExampleRadioButtonGroup() {
        exampleRadioButtonGroup = new RadioButtonGroup();
        RadioButton btn1 = new RadioButton();
        btn1.setLabel("Option One");
        btn1.setRenderLabel(true);
        btn1.setNameQualifier("Option_One");
        btn1.setColumnName("exampleRadioButtongroup");
        btn1.setId("id111");
        ((RadioButtonGroup) exampleRadioButtonGroup).addButton(btn1);

        RadioButton btn2 = new RadioButton();
        btn2.setLabel("Option Two");
        btn2.setRenderLabel(true);
        btn2.setNameQualifier("Option_Two");
        btn2.setColumnName("exampleRadioButtongroup");
        btn2.setId("id222");
        ((RadioButtonGroup) exampleRadioButtonGroup).addButton(btn2);

        RadioButton btn3 = new RadioButton();
        btn3.setLabel("Option Three");
        btn3.setRenderLabel(true);
        btn3.setNameQualifier("Option_Three");
        btn3.setColumnName("exampleRadioButtongroup");
        btn3.setId("id333");
        ((RadioButtonGroup) exampleRadioButtonGroup).addButton(btn3);

        return exampleRadioButtonGroup;
    }

    public Date getExampleDateField() {
        return exampleDateField;
    }
    
    public GuiComponent getExampleTextField() {
        ((TextBox) exampleTextField).setId("exampleTextBoxID");
        ((TextBox) exampleTextField).setName("exampleTextBoxName");
        return exampleTextField;
    }

    public GuiComponent getExampleComboBoxField() {
        ArrayList<String> internalValues = new ArrayList<String>();
        internalValues.add("Option A");
        internalValues.add("Option B"); 
        internalValues.add("Option C");
        ((ComboBox) exampleComboBoxField).setInternalValues(internalValues);
        ((ComboBox) exampleComboBoxField).setName("Render On Top Test Combo box");
        ((ComboBox) exampleComboBoxField).setValues(internalValues);
        return exampleComboBoxField;
    }

    public GuiComponent getExampleMultiValueArea() {
        StringInputComponent sic = new StringInputComponent("Example Multi Input", -1, true);
        sic.setName("multiTextAreaInput");
        sic.setId("multiTextAreaInputID");
        ArrayList<AttributeInputComponent>guiComponents = new ArrayList<AttributeInputComponent>();
        guiComponents.add(sic);
        
        exampleMultiValueArea = new MultiValuedInputComponent(guiComponents, sic);
        ((MultiValuedInputComponent)exampleMultiValueArea).setName("exampleMultiValueFieldName");
        ((MultiValuedInputComponent)exampleMultiValueArea).setColumnName("exampleMultiValueFieldColumnName");
        ((MultiValuedInputComponent)exampleMultiValueArea).setMultiValued(true);
        return exampleMultiValueArea;
    }
    
    public GuiComponent getExampleMultiValueField() {
        StringInputComponent sic = new StringInputComponent("Example Multi Input", -1, false);
        sic.setName("multiTextFieldInput");
        sic.setId("multiTextFieldInputID");
        sic.setInputWidth(10);
        ArrayList<AttributeInputComponent>guiComponents = new ArrayList<AttributeInputComponent>();
        guiComponents.add(sic);
        
        exampleMultiValueField = new MultiValuedInputComponent(guiComponents, sic);
        ((MultiValuedInputComponent)exampleMultiValueField).setName("exampleMultiValueFieldName");
        ((MultiValuedInputComponent)exampleMultiValueField).setColumnName("exampleMultiValueFieldColumnName");
        ((MultiValuedInputComponent)exampleMultiValueField).setMultiValued(true);
        return exampleMultiValueField;
        
    }
    
    public GuiComponent getExampleURLLink() {
        ((UrlInputComponent) exampleURLLink).setName("exampleURLLinkName");
        ((UrlInputComponent) exampleURLLink).setColumnName("exampleURLLinkColumnName");
        ((UrlInputComponent) exampleURLLink).setEditable(true);
        return exampleURLLink;
    }
    

    public GuiComponent getExampleMultiValueURL() {
        UrlInputComponent uic = new UrlInputComponent("Example Multi Url", -1, false);
        uic.setName("exampleMVUrlInput");
        uic.setColumnName("exampleMVUrlColumnName");
        ArrayList<AttributeInputComponent> guiComponents = new ArrayList<AttributeInputComponent>();
        guiComponents.add(uic);

        exampleMultiValueURL = new MultiValuedInputComponent(guiComponents, uic);
        ((MultiValuedInputComponent) exampleMultiValueURL).setName("exampleMVUrl");
        ((MultiValuedInputComponent) exampleMultiValueURL).setColumnName("exampleMVUrlCN");
        ((MultiValuedInputComponent) exampleMultiValueURL).setMultiValued(true);
        return exampleMultiValueURL;

    }
}
