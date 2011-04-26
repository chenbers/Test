package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public class Link extends ElementBase implements Clickable {
    
    public Link(SeleniumEnums anEnum) {
        super(anEnum);
    }

    @Override
    public ElementInterface click() {
        selenium.click(myEnum);
        return this;
    }

}
