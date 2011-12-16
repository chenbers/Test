package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;


public class Button extends Link implements Clickable{
    public Button(SeleniumEnums anEnum, Object ...objects) {
        super(anEnum, objects);
    }
}
