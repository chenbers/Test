package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;

public abstract class ClickableObject extends ElementBase implements Clickable {

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
    public ClickableObject(SeleniumEnums anEnum, TextEnum replaceWord){
    	super(anEnum, replaceWord);
    }

    public ClickableObject click() {
        selenium.click(myEnum);
        selenium.waitForPageToLoad();
        setCurrentLocation();
        return this;
    }
}
