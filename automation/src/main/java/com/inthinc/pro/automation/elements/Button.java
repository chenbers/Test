package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.enums.SeleniumEnum.SeleniumEnums;


public class Button extends Link implements Clickable{
    public Button(SeleniumEnums anEnum) {
        super(anEnum);
    }
    public Button(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
    }
    public Button(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }
    public Button(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
    }
}
