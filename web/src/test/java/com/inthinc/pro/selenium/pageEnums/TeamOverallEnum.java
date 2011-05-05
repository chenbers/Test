package com.inthinc.pro.selenium.pageEnums;



import com.inthinc.pro.automation.enums.SeleniumEnums;

import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum TeamOverallEnum implements SeleniumEnums {

    OVERALL_SCORE(null, null, Xpath.start().div(Id.clazz("middle")).table().tbody().tr().toString(), null),
    OVERALL_SCORE_LABEL(null, null, Xpath.start().div(Id.clazz("middle")).text().toString(), null),
    
    SCORE_NA(null, null, Xpath.start().tbody(Id.id("summaryOverallStats:tb")).tr().td("1").toString(), null),    
    SCORE_0_1(null, null, Xpath.start().tbody(Id.id("summaryOverallStats:tb")).tr().td("2").toString(), null),
    SCORE_1_2(null, null, Xpath.start().tbody(Id.id("summaryOverallStats:tb")).tr().td("3").toString(), null),
    SCORE_2_3(null, null, Xpath.start().tbody(Id.id("summaryOverallStats:tb")).tr().td("4").toString(), null),
    SCORE_3_4(null, null, Xpath.start().tbody(Id.id("summaryOverallStats:tb")).tr().td("5").toString(), null),
    SCORE_4_5(null, null, Xpath.start().tbody(Id.id("summaryOverallStats:tb")).tr().td("6").toString(), null),
    SCORE_TOTAL(null, null, Xpath.start().tbody(Id.id("summaryOverallStats:tb")).tr().td("7").toString(), null),

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
