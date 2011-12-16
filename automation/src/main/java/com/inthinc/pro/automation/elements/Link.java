package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public class Link extends ClickableObject implements Clickable {
    
    public Link(SeleniumEnums anEnum, Object ...objects) {
        super(anEnum, objects);
    }
}
