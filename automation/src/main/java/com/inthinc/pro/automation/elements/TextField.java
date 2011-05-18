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
        type(null);
        return this;
    }
    
    public TextField type(String inputText) {
        selenium.type(myEnum, inputText);
        return this;
    }


}
