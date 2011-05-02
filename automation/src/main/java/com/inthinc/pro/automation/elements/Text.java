package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public class Text extends TextObject implements TextBased {
    
    public Text(SeleniumEnums anEnum) {
        super(anEnum);
    }
    public Text(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
    }
    public Text(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }
    public Text(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
    }

}
