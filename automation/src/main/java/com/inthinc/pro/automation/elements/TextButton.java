package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.selenium.CoreMethodLib;

public class TextButton extends Button implements TextBased {
    
    public TextButton(CoreMethodLib pageSelenium, SeleniumEnums anEnum) {
        super(pageSelenium, anEnum);
        myEnum = anEnum;
        mySelenium = pageSelenium;
    }

    //TODO: jwimmer: seems like there should be a way to get these to USE the Text.compareText(...) implementations
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
}
