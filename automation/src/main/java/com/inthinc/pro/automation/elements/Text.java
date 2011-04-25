package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.selenium.CoreMethodLib;

public class Text extends ElementBase implements TextBased {
    
    public Text(CoreMethodLib pageSelenium, SeleniumEnums anEnum) {
        super(pageSelenium, anEnum);
        myEnum = anEnum;
        mySelenium = pageSelenium;
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

}
