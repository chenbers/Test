package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;

public class ClickableObject extends ElementBase implements Clickable {

    public ClickableObject(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
    }

    public ClickableObject(SeleniumEnums anEnum) {
        super(anEnum);
    }

    public ClickableObject(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
    }

    public ClickableObject(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }

    public ClickableObject(SeleniumEnums anEnum, TextEnum replaceWord) {
        super(anEnum, replaceWord);
    }

    public ClickableObject click() {
        selenium.click(myEnum);
        selenium.waitForPageToLoad();
        // setCurrentLocation(); //TODO: jwimmer: to dTanner: how do you feel about ONLY calling setCurrentLocation() if current.* is needed (like on TextLinkContextSense)
        return this;
    }

    @Override
    public Boolean isClickable() {
        return selenium.isClickable(myEnum);
    }

    @Override
    public Boolean validateClickable(Boolean clickable) {
        return validateEquals(clickable, isClickable());
    }

    @Override
    public Boolean assertClickable(Boolean clickable) {
        return assertEquals(clickable, isClickable());
    }
}
