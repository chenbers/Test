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
    public ElementInterface compare(String expected) {
        String actual = getText();
        assertEquals(actual, expected);
        return this;
    }
    
    @Override
    public ElementInterface compare() {
        return compare(myEnum.getText());
    }

    public String getText(){
        return selenium.getText(myEnum);
    }
    
    public ElementInterface validateContains(String expectedPart) {
        String actual = getText();
        assertStringContains(actual, expectedPart);
        return this;
    }
    public ElementInterface validate(String expected) {
        return compare(expected);
    }
    
    public ElementInterface validate(TextEnum expected) {
        return compare(expected.getText());
    }
    public ElementInterface validate(TextEnum expected, String replaceOld, String withNew) {
        return compare(expected.getText().replace(replaceOld, withNew));
    }
    
    public ElementInterface validate(){
        return compare();
    }
    
    public ElementInterface assertEquals(String compareAgainst){
    	assertEquals(compareAgainst, myEnum);
    	return this;
    }
}
