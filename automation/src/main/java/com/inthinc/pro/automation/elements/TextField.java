package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Typeable;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public class TextField extends ElementBase implements Typeable {
    
    public TextField(SeleniumEnums anEnum) {
        super(anEnum);
    }
    
    public TextField clear() {
        type(null);
        return this;
    }
    
    public TextField type(String inputText) {
        //selenium.type(myEnum, inputText);
        selenium.type(myEnum, inputText);
        return this;
    }

    //TODO: jwimmer: push the next two methods up into Text (the abstract? implementation of TextBased interface?)
    @Override
    public ElementInterface compareText(String expected) {
        String actual = getText();
        if (!expected.equals(actual)) {
            addError(this.myEnum.toString(), "Expected = " + expected + "\nActual = " + actual);
        }
        return this;
    }

    @Override
    public ElementInterface compareText() {
        return compareText(myEnum.getText());
    }

    @Override
    public String getText() {
        return selenium.getText(myEnum);
    }

}
