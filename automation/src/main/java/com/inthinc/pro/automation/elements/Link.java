package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public class Link extends ClickableObject implements Clickable {
    
    public Link(SeleniumEnums anEnum) {
        super(anEnum);
    }
    public Link(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
    }
    public Link(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }
    public Link(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
    }
}
