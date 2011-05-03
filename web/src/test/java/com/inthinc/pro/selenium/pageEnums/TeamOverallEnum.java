package com.inthinc.pro.selenium.pageEnums;

import java.util.List;

import com.inthinc.pro.automation.enums.SeleniumEnum;
import com.inthinc.pro.automation.enums.SeleniumEnum.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum TeamOverallEnum implements SeleniumEnums {

    OVERALL_SCORE(Xpath.start().div(Id.clazz("middle")).table().tbody().tr()),
    OVERALL_SCORE_LABEL(Xpath.start().div(Id.clazz("middle")).text()),
    
    SCORE_NA(Xpath.start().tbody(Id.id("summaryOverallStats:tb")).tr().td("1")),    
    SCORE_0_1(Xpath.start().tbody(Id.id("summaryOverallStats:tb")).tr().td("2")),
    SCORE_1_2(Xpath.start().tbody(Id.id("summaryOverallStats:tb")).tr().td("3")),
    SCORE_2_3(Xpath.start().tbody(Id.id("summaryOverallStats:tb")).tr().td("4")),
    SCORE_3_4(Xpath.start().tbody(Id.id("summaryOverallStats:tb")).tr().td("5")),
    SCORE_4_5(Xpath.start().tbody(Id.id("summaryOverallStats:tb")).tr().td("6")),
    SCORE_TOTAL(Xpath.start().tbody(Id.id("summaryOverallStats:tb")).tr().td("7")),

    ;

    private String text, ID, xpath, xpath_alt, url;

    private TeamOverallEnum(String text, String ID, String xpath, String xpath_alt) {
        this.text = text;
        this.ID = ID;
        this.xpath = xpath;
        this.xpath_alt = xpath_alt;
    }

    private TeamOverallEnum(String url) {
        this.url = url;
    }

    private TeamOverallEnum(String text, String ID) {
        this(text, ID, "", null);
    }

    private TeamOverallEnum(String text, String ID, String xpath) {
        this(text, ID, xpath, null);
    }

    private TeamOverallEnum(String text, String ID, Xpath xpath, Xpath xpath_alt) {
        this(text, ID, xpath.toString(), xpath_alt.toString());
    }

    private TeamOverallEnum(String text, String ID, Xpath xpath) {
        this(text, ID, xpath.toString(), null);
    }

    private TeamOverallEnum(String text, Xpath xpath) {
        this(text, null, xpath.toString(), null);
    }

    private TeamOverallEnum(Xpath xpath, Xpath xpath_alt) {
        this(null, null, xpath.toString(), xpath_alt.toString());
    }

    private TeamOverallEnum(Xpath xpath) {
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
    public  TeamOverallEnum replaceNumber(String number) {
        ID = ID.replace("###", number);
        xpath = xpath.replace("###", number);
        xpath_alt = xpath_alt.replace("###", number);
        return this;
    }

    @Override
    public  TeamOverallEnum replaceWord(String word) {
        ID = ID.replace("***", word);
        xpath = xpath.replace("***", word);
        xpath_alt = xpath_alt.replace("***", word);
        return this;
    }
}
