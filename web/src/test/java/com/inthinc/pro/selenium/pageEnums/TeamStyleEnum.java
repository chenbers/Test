package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum TeamStyleEnum implements SeleniumEnums {

    STYLE_SCORE(null, null, Xpath.start().div(Id.clazz("middle")).table().tbody().tr().toString(), null),
    STYLE_SCORE_LABEL(null, null, Xpath.start().div(Id.clazz("middle")).text().toString(), null)

    ;

    private String text, ID, xpath, xpath_alt, url;

    private TeamStyleEnum(String text, String ID, String xpath, String xpath_alt) {
        this.text = text;
        this.ID = ID;
        this.xpath = xpath;
        this.xpath_alt = xpath_alt;
    }

    private TeamStyleEnum(String url) {
        this.url = url;
    }

    private TeamStyleEnum(String text, String ID) {
        this(text, ID, "", null);
    }

    private TeamStyleEnum(String text, String ID, String xpath) {
        this(text, ID, xpath, null);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getID() {
        return ID;
    }

    public String getXpath() {
        return xpath;
    }

    public String getXpath_alt() {
        return xpath_alt;
    }

    public String getURL() {
        return url;
    }
}
