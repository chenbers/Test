package com.inthinc.pro.selenium.pageEnums;

import java.util.List;

import com.inthinc.pro.automation.enums.SeleniumEnum;
import com.inthinc.pro.automation.enums.SeleniumEnum.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum TeamLiveEnum implements SeleniumEnums {
    
    LEGEND_TITLE("Team Legend", Xpath.start().span(Id.clazz("legend"))),
    LEGEND_ENTRY(Xpath.start().td(Id.id("icos1:###")).div().span())
    
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

    private TeamLiveEnum(String text, String ID) {
        this(text, ID, "", null);
    }

    private TeamLiveEnum(String text, String ID, String xpath) {
        this(text, ID, xpath, null);
    }

    private TeamLiveEnum(String text, String ID, Xpath xpath, Xpath xpath_alt) {
        this(text, ID, xpath.toString(), xpath_alt.toString());
    }

    private TeamLiveEnum(String text, String ID, Xpath xpath) {
        this(text, ID, xpath.toString(), null);
    }

    private TeamLiveEnum(String text, Xpath xpath) {
        this(text, null, xpath.toString(), null);
    }

    private TeamLiveEnum(Xpath xpath, Xpath xpath_alt) {
        this(null, null, xpath.toString(), xpath_alt.toString());
    }

    private TeamLiveEnum(Xpath xpath) {
        this(null, null, xpath.toString(), null);
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
    @Override
    public List<String> getLocators() {        
        return SeleniumEnum.locators(this);
    }
    
    @Override
    public  TeamLiveEnum replaceNumber(String number) {
        ID = ID.replace("###", number);
        xpath = xpath.replace("###", number);
        xpath_alt = xpath_alt.replace("###", number);
        return this;
    }

    @Override
    public  TeamLiveEnum replaceWord(String word) {
        ID = ID.replace("***", word);
        xpath = xpath.replace("***", word);
        xpath_alt = xpath_alt.replace("***", word);
        return this;
    }
}
