package com.inthinc.pro.selenium.pageEnums;



import com.inthinc.pro.automation.enums.SeleniumEnums;

import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum TeamSpeedEnum implements SeleniumEnums {
    
    SPEED_SCORE(Xpath.start().div(Id.clazz("middle")).table().tbody().tr()),
    SPEED_SCORE_LABEL(Xpath.start().div(Id.clazz("middle")).text()),
    
    LIMIT_TABLE_SUBTITLE("Statistics reflect event count for 1 of 16 drivers with speed violations. ", Xpath.start().tr("1").td(Id.align("center")).text("2")),
    LIMIT_TABLE_TITLE("Limit", Xpath.start().table(Id.id("summarySpeedStats")).thead().tr("1").th()),
    
    LIMIT_1_30_HEADER(Xpath.start().table(Id.id("summarySpeedStats")).thead().tr("2").th("1")),
    LIMIT_31_40_HEADER(Xpath.start().table(Id.id("summarySpeedStats")).thead().tr("2").th("2")),
    LIMIT_41_54_HEADER(Xpath.start().table(Id.id("summarySpeedStats")).thead().tr("2").th("3")),
    LIMIT_55_64_HEADER(Xpath.start().table(Id.id("summarySpeedStats")).thead().tr("2").th("4")),
    LIMIT_65_80_HEADER(Xpath.start().table(Id.id("summarySpeedStats")).thead().tr("2").th("5")),
    LIMIT_TOTAL_HEADER(Xpath.start().table(Id.id("summarySpeedStats")).thead().tr("2").th("6")),
    
    LIMIT_1_30_NUMBER(Xpath.start().tbody(Id.id("summarySpeedStats:tb")).tr("1").td("1")),
    LIMIT_31_40_NUMBER(Xpath.start().tbody(Id.id("summarySpeedStats:tb")).tr("1").td("2")),
    LIMIT_41_54_NUMBER(Xpath.start().tbody(Id.id("summarySpeedStats:tb")).tr("1").td("3")),
    LIMIT_55_64_NUMBER(Xpath.start().tbody(Id.id("summarySpeedStats:tb")).tr("1").td("4")),
    LIMIT_65_80_NUMBER(Xpath.start().tbody(Id.id("summarySpeedStats:tb")).tr("1").td("5")),
    LIMIT_TOTAL_NUMBER(Xpath.start().tbody(Id.id("summarySpeedStats:tb")).tr("1").td("6")),
    
    LIMIT_1_30_PERCENT(Xpath.start().tbody(Id.id("summarySpeedStats:tb")).tr("2").td("1")),
    LIMIT_31_40_PERCENT(Xpath.start().tbody(Id.id("summarySpeedStats:tb")).tr("2").td("2")),
    LIMIT_41_54_PERCENT(Xpath.start().tbody(Id.id("summarySpeedStats:tb")).tr("2").td("3")),
    LIMIT_55_64_PERCENT(Xpath.start().tbody(Id.id("summarySpeedStats:tb")).tr("2").td("4")),
    LIMIT_65_80_PERCENT(Xpath.start().tbody(Id.id("summarySpeedStats:tb")).tr("2").td("5")),
    LIMIT_TOTAL_PERCENT(Xpath.start().tbody(Id.id("summarySpeedStats:tb")).tr("2").td("6")),
    

    ;

    private String text, ID, xpath, xpath_alt, url;

    private TeamSpeedEnum(String text, String ID, String xpath, String xpath_alt) {
        this.text = text;
        this.ID = ID;
        this.xpath = xpath;
        this.xpath_alt = xpath_alt;
    }

    private TeamSpeedEnum(String url) {
        this.url = url;
    }

    private TeamSpeedEnum(String text, String ID) {
        this(text, ID, "", null);
    }

    private TeamSpeedEnum(String text, String ID, String xpath) {
        this(text, ID, xpath, null);
    }

    private TeamSpeedEnum(String text, String ID, Xpath xpath, Xpath xpath_alt) {
        this(text, ID, xpath.toString(), xpath_alt.toString());
    }

    private TeamSpeedEnum(String text, String ID, Xpath xpath) {
        this(text, ID, xpath.toString(), null);
    }

    private TeamSpeedEnum(String text, Xpath xpath) {
        this(text, null, xpath.toString(), null);
    }

    private TeamSpeedEnum(Xpath xpath, Xpath xpath_alt) {
        this(null, null, xpath.toString(), xpath_alt.toString());
    }

    private TeamSpeedEnum(Xpath xpath) {
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
    
}
