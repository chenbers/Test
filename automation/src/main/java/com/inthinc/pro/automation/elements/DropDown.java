package com.inthinc.pro.automation.elements;


import com.inthinc.pro.automation.elements.ElementInterface.Selectable;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public class DropDown extends SelectableObject implements Selectable {
    
    public DropDown(SeleniumEnums anEnum, Object ...objects) {
        super(anEnum, objects);
    }
    
}
