package com.inthinc.pro.selenium.pageEnums;



import com.inthinc.pro.automation.enums.SeleniumEnums;

import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum TeamLiveEnum implements SeleniumEnums {
    
    LEGEND_TITLE("Team Legend",null, Xpath.start().span(Id.clazz("legend")).toString(), null),
    LEGEND_ENTRY(null, null,Xpath.start().td(Id.id("icos1:###")).div().span().toString(), null)
    
    ;

    private String text, ID, xpath, xpath_alt, url;

    private TeamLiveEnum(String text, String ID, String xpath, String xpath_alt) {
        this.text = text;
        this.ID = ID;
        this.xpath = xpath;
        this.xpath_alt = xpath_alt;
    }

    private TeamLiveEnum(String url) {
        this.url = url;
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
