package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public class TextLink extends Link implements TextBased {
    
    public TextLink(SeleniumEnums anEnum) {
        super(anEnum);
    }

    //TODO: jwimmer: seems like there should be a way to get these to USE the Text.compareText(...) implementations
    //TODO: dtanner: What is a TextLink vs a Link???
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
        return selenium.getText(element);
    }


}
