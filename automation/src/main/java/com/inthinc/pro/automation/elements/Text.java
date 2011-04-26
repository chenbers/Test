package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public class Text extends ElementBase implements TextBased {
    
    public Text(SeleniumEnums anEnum) {
        super(anEnum);
    }

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

    public String getText(){
        return selenium.getText(element);
    }
}
