package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public class SpinnerButton extends Button{

    public SpinnerButton(SeleniumEnums anEnum, Object... objects) {
        super(anEnum, objects);
    }
    
    @Override
    public Button click() {
        getSelenium().mouseDown(myEnum);
        getSelenium().mouseUp(myEnum);
        return this;
    }

}
