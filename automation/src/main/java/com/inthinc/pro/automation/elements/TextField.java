package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Typeable;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;

public class TextField extends TextObject implements Typeable {
    
    public TextField(SeleniumEnums anEnum) {
        super(anEnum);
    }
    public TextField(SeleniumEnums anEnum, TextEnum replacement){
    	super(anEnum, replacement.getText());
    }
    public TextField(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
    }
    public TextField(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }
    public TextField(SeleniumEnums anEnum, String prefix, TextEnum replacement) {
        super(anEnum, prefix + replacement.getText());
    }
    public TextField(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
    }
    
    public TextField clear() {
        type("");
        return this;
    }
    
    public TextField type(String inputText) {
        selenium.type(myEnum, inputText);
        return this;
    }
    @Override
    public String getText(){
        logger.warn("myEnum.locators: "+myEnum.getLocatorsAsString());
        logger.warn("value : "+selenium.getValue(myEnum));
        return selenium.getValue(myEnum);
    }
    @Override
    public Boolean assertEquals() {
        return assertEquals(myEnum, getText(), myEnum);
    }

    @Override
    public Boolean assertEquals(String compareAgainst) {
        return assertEquals(compareAgainst, getText());
    }

    @Override
    public Boolean assertNotEquals(String compareAgainst) {
        return assertNotEquals(compareAgainst, getText());
    }
}
