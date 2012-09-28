package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Checkable;
import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public class CheckBox extends ClickableObject implements Checkable, Clickable {

    public CheckBox(SeleniumEnums anEnum, Object ...objects) {
        super(anEnum, objects);
    }
    

    @Override
    public CheckBox check() {
        if (!isChecked()) {
            click();
        }
        return this;
    }

    @Override
    public CheckBox uncheck() {
        if (isChecked()) {
            click();
        }
        return this;
    }

    @Override
    public Boolean isChecked() {
        return getSelenium().isChecked(myEnum);
    }

    @Override
    public Boolean validateChecked(Boolean checked) {
        return validateEquals(checked, isChecked());
    }

    @Override
    public Boolean assertChecked(Boolean checked) {
        return assertEquals(checked, isChecked());
    }
}
