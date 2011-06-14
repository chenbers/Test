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
    public Boolean compare(String expected) {
        return compare(expected, getText());
    }
    
    @Override
    public Boolean compare() {
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
    public Boolean validate(String expected) {
        return compare(expected);
    }
    
    public Boolean validate(TextEnum expected) {
        return compare(expected.getText());
    }
    public Boolean validate(TextEnum expected, String replaceOld, String withNew) {
        return compare(expected.getText().replace(replaceOld, withNew));
    }
    

    public Boolean validate() {
        return validateEquals(myEnum.getText(), getText());
    }
    
    public Boolean assertEquals(String compareAgainst){
    	return assertEquals(compareAgainst, myEnum);
    }
}
