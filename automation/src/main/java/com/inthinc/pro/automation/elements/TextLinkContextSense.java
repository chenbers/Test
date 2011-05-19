package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public class TextLinkContextSense extends TextLink implements TextBased, Clickable {
    public TextLinkContextSense(SeleniumEnums anEnum) {
        super(anEnum);
    }
    public TextLinkContextSense(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
    }
    public TextLinkContextSense(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }
    public TextLinkContextSense(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
    }
    
    @Override
    public TextLinkContextSense click() {
    	setCurrentLocation();
        String loc = current.get("page");
        if (!loc.equals("")){
            myEnum.replaceWord(loc);
        }
        super.click();
        return this;
    }
}
