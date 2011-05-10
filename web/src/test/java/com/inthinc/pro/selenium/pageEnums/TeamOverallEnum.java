package com.inthinc.pro.selenium.pageEnums;



import com.inthinc.pro.automation.enums.SeleniumEnums;

import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum TeamOverallEnum implements SeleniumEnums {

    OVERALL_SCORE(null, Xpath.start().div(Id.clazz("middle")).table().tbody().tr().toString()),
    OVERALL_SCORE_LABEL(null, Xpath.start().div(Id.clazz("middle")).text().toString()),
    
    SCORE_NA(null, Xpath.start().tbody(Id.id("summaryOverallStats:tb")).tr().td("1").toString()),    
    SCORE_0_1(null, Xpath.start().tbody(Id.id("summaryOverallStats:tb")).tr().td("2").toString()),
    SCORE_1_2(null, Xpath.start().tbody(Id.id("summaryOverallStats:tb")).tr().td("3").toString()),
    SCORE_2_3(null, Xpath.start().tbody(Id.id("summaryOverallStats:tb")).tr().td("4").toString()),
    SCORE_3_4(null, Xpath.start().tbody(Id.id("summaryOverallStats:tb")).tr().td("5").toString()),
    SCORE_4_5(null, Xpath.start().tbody(Id.id("summaryOverallStats:tb")).tr().td("6").toString()),
    SCORE_TOTAL(null, Xpath.start().tbody(Id.id("summaryOverallStats:tb")).tr().td("7").toString()),

    ;

    private String text, url;
    private String[] IDs;
    
    private TeamOverallEnum(String url){
    	this.url = url;
    }
    private TeamOverallEnum(String text, String ...IDs){
        this.text=text;
    	this.IDs = IDs;
    }

    @Override
    public String[] getIDs() {
        return IDs;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getURL() {
        return url;
    }
}
