package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.enums.SeleniumEnums;

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
        if (!expected.equals(actual)) {
            addError(this.myEnum.toString(), "\t\tExpected = " + expected + "\n\t\tActual = " + actual);
        }
        return this;
    }
    
    @Override
    public ElementInterface compareText() {
        return compareText(myEnum.getText());
    }

    public String getText(){
        return selenium.getText(myEnum);
    }
    
    public ElementInterface validateText(String expected) {
        return compareText(expected);
    }
    
    public ElementInterface validateText(){
        return compareText();
    }
}
