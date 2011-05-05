package com.inthinc.pro.selenium.pageEnums;



import com.inthinc.pro.automation.enums.SeleniumEnums;

import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum TeamSpeedEnum implements SeleniumEnums {
    
    SPEED_SCORE(null,null,Xpath.start().div(Id.clazz("middle")).table().tbody().tr().toString(), null),
    SPEED_SCORE_LABEL(null,null,Xpath.start().div(Id.clazz("middle")).text().toString(), null),
    
    LIMIT_TABLE_SUBTITLE("Statistics reflect event count for 1 of 16 drivers with speed violations. ",null, Xpath.start().tr("1").td(Id.align("center")).text("2").toString(), null),
    LIMIT_TABLE_TITLE("Limit",null, Xpath.start().table(Id.id("summarySpeedStats")).thead().tr("1").th().toString(), null),
    
    LIMIT_1_30_HEADER(null,null,Xpath.start().table(Id.id("summarySpeedStats")).thead().tr("2").th("1").toString(), null),
    LIMIT_31_40_HEADER(null,null,Xpath.start().table(Id.id("summarySpeedStats")).thead().tr("2").th("2").toString(), null),
    LIMIT_41_54_HEADER(null,null,Xpath.start().table(Id.id("summarySpeedStats")).thead().tr("2").th("3").toString(), null),
    LIMIT_55_64_HEADER(null,null,Xpath.start().table(Id.id("summarySpeedStats")).thead().tr("2").th("4").toString(), null),
    LIMIT_65_80_HEADER(null,null,Xpath.start().table(Id.id("summarySpeedStats")).thead().tr("2").th("5").toString(), null),
    LIMIT_TOTAL_HEADER(null,null,Xpath.start().table(Id.id("summarySpeedStats")).thead().tr("2").th("6").toString(), null),
    
    LIMIT_1_30_NUMBER(null,null,Xpath.start().tbody(Id.id("summarySpeedStats:tb")).tr("1").td("1").toString(), null),
    LIMIT_31_40_NUMBER(null,null,Xpath.start().tbody(Id.id("summarySpeedStats:tb")).tr("1").td("2").toString(), null),
    LIMIT_41_54_NUMBER(null,null,Xpath.start().tbody(Id.id("summarySpeedStats:tb")).tr("1").td("3").toString(), null),
    LIMIT_55_64_NUMBER(null,null,Xpath.start().tbody(Id.id("summarySpeedStats:tb")).tr("1").td("4").toString(), null),
    LIMIT_65_80_NUMBER(null,null,Xpath.start().tbody(Id.id("summarySpeedStats:tb")).tr("1").td("5").toString(), null),
    LIMIT_TOTAL_NUMBER(null,null,Xpath.start().tbody(Id.id("summarySpeedStats:tb")).tr("1").td("6").toString(), null),
    
    LIMIT_1_30_PERCENT(null,null,Xpath.start().tbody(Id.id("summarySpeedStats:tb")).tr("2").td("1").toString(), null),
    LIMIT_31_40_PERCENT(null,null,Xpath.start().tbody(Id.id("summarySpeedStats:tb")).tr("2").td("2").toString(), null),
    LIMIT_41_54_PERCENT(null,null,Xpath.start().tbody(Id.id("summarySpeedStats:tb")).tr("2").td("3").toString(), null),
    LIMIT_55_64_PERCENT(null,null,Xpath.start().tbody(Id.id("summarySpeedStats:tb")).tr("2").td("4").toString(), null),
    LIMIT_65_80_PERCENT(null,null,Xpath.start().tbody(Id.id("summarySpeedStats:tb")).tr("2").td("5").toString(), null),
    LIMIT_TOTAL_PERCENT(null,null,Xpath.start().tbody(Id.id("summarySpeedStats:tb")).tr("2").td("6").toString(), null),
    

    ;

    private String text, ID, xpath, xpath_alt, url;

    private TeamSpeedEnum(String text, String ID, String xpath, String xpath_alt) {
        this.text = text;
        this.ID = ID;
        this.xpath = xpath;
        this.xpath_alt = xpath_alt;
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
