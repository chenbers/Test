package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;

public abstract class TextObject extends ElementBase implements TextBased {

    public TextObject(SeleniumEnums anEnum) {
        super(anEnum);
    }
    public TextObject(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
    }
    public TextObject(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }
    public TextObject(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
    }

    @Override
    public ElementInterface compareText(String expected) {
        String actual = getText();
        assertEquals(actual, expected);
        return this;
    }
    
    @Override
    public ElementInterface compareText() {
        return compareText(myEnum.getText());
    }

    public String getText(){
        return selenium.getText(myEnum);
    }
    
    public ElementInterface validate(String expected) {
        return compareText(expected);
    }
    
    public ElementInterface validate(TextEnum expected) {
        return compareText(expected.getText());
    }
    public ElementInterface validate(TextEnum expected, String replaceOld, String withNew) {
        return compareText(expected.getText().replace(replaceOld, withNew));
    }
    
    public ElementInterface validate(){
        return compareText();
    }
}
