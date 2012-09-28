package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public class ClickableObject extends ElementBase implements Clickable {

    public ClickableObject(SeleniumEnums anEnum, Object ...objects) {
        super(anEnum, objects);
    }


    public ClickableObject click() {
        getSelenium().click(myEnum);
        getSelenium().waitForPageToLoad();
        return this;
    }

    @Override
    public Boolean isClickable() {
        return getSelenium().isClickable(myEnum);
    }

    @Override
    public Boolean validateClickable(Boolean clickable) {
        return validateEquals(clickable, isClickable());
    }

    @Override
    public Boolean assertClickable(Boolean clickable) {
        return assertEquals(clickable, isClickable());
    }


	@Override
	public Clickable doubleClick() {
        getSelenium().doubleClick(myEnum);
        getSelenium().waitForPageToLoad();
        return this;
	}
    
}
