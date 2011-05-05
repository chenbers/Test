package com.inthinc.pro.selenium.pageEnums;



import com.inthinc.pro.automation.enums.SeleniumEnums;

import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum TeamBarEnum implements SeleniumEnums {
    TEAM_TITLE(null, null, Xpath.start().li(Id.clazz("l grid_title max-width")).toString(), null),
    TEAM_SCORE(null, null, Xpath.start().li(Id.clazz("l")).span().table().tbody().tr().td().toString(), null),
    
    CRASHES_PER_MILLION_NUMBER(null, null, Xpath.start().li("5", Id.clazz("l crash-item")).span("2").toString(), null),
    CRASHES_PER_MILLION_TEXT("Crashes per million miles",null, Xpath.start().li("5", Id.clazz("l crash-item")).text("2").toString(), null),
    
    DAYS_SINCE_NUMBER(null, null, Xpath.start().li("6", Id.clazz("l crash-item")).span().toString(), null),
    DAYS_SINCE_TEXT("Days since last crash", null, Xpath.start().li("6", Id.clazz("l crash-item")).text().toString(), null),
    
    MILES_SINCE_NUMBER(null, null, Xpath.start().li("7", Id.clazz("l crash-item")).span().toString(), null),
    MILES_SINCE_TEXT("Miles since last crash",null, Xpath.start().li("7", Id.clazz("l crash-item")).text().toString(), null),
    
    SUB_TITLE("What Happened...", null, Xpath.start().div(Id.clazz("panel_title")).text("1").toString(), null),
    
    DRIVER_STATISTICS("Driver Statistics", "teamStatistics_lbl", null, null),
    TRIPS("Trips", "teamTrips_lbl", null, null),
    STOPS("Stops", "teamStops_lbl", null, null),
    LIVE_TEAM("Live Team", "teamLive_lbl", null, null),
    OVERALL_SCORE("Overall Score", "teamOverall_lbl", null, null),
    DRIVING_STYLE("Driving Style", "teamStyle_lbl", null, null),
    SPEED("Speed", "teamSpeeding_lbl", null, null),  
    
    ;

    private String text, ID, xpath, xpath_alt, url;

    private TeamBarEnum(String text, String ID, String xpath, String xpath_alt) {
        this.text = text;
        this.ID = ID;
        this.xpath = xpath;
        this.xpath_alt = xpath_alt;
    }

    private TeamBarEnum(String url) {
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
