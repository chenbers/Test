package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Checkable;
import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public class CheckBox extends ClickableObject implements Checkable, Clickable {

    public CheckBox(SeleniumEnums anEnum, String replaceWord) {
	super(anEnum, replaceWord);
    }

    public CheckBox(SeleniumEnums anEnum) {
	super(anEnum);
    }

    @Override
    public CheckBox check() {
	selenium.check(myEnum);
	return this;
    }

    @Override
    public CheckBox uncheck() {
	selenium.uncheck(myEnum);
	return this;
    }

    @Override
    public Boolean isChecked() {
	return selenium.isChecked(myEnum);
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
