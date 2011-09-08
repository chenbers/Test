package com.inthinc.pro.selenium.pageEnums;



import com.inthinc.pro.automation.interfaces.SeleniumEnums;

import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum TeamBarEnum implements SeleniumEnums {
    TEAM_TITLE(null, Xpath.start().li(Id.clazz("l grid_title max-width")).toString()),
    TEAM_SCORE(null, Xpath.start().li(Id.clazz("l")).span().table().tbody().tr().td().toString()),
    
    CRASHES_PER_MILLION_NUMBER(null, Xpath.start().li("5", Id.clazz("l crash-item")).span("2").toString()),
    CRASHES_PER_MILLION_TEXT("Crashes per million miles",null, Xpath.start().li("5", Id.clazz("l crash-item")).text("2").toString()),
    
    DAYS_SINCE_NUMBER(null, Xpath.start().li("6", Id.clazz("l crash-item")).span().toString()),
    DAYS_SINCE_TEXT("Days since last crash", Xpath.start().li("6", Id.clazz("l crash-item")).text().toString()),
    
    MILES_SINCE_NUMBER(null, Xpath.start().li("7", Id.clazz("l crash-item")).span().toString()),
    MILES_SINCE_TEXT("Miles since last crash",null, Xpath.start().li("7", Id.clazz("l crash-item")).text().toString()),
    
    SUB_TITLE("What Happened...", Xpath.start().div(Id.clazz("panel_title")).text("1").toString()),
    
    DRIVER_STATISTICS("Driver Statistics", "teamStatistics_lbl"),
    TRIPS("Trips", "teamTrips_lbl"),
    STOPS("Stops", "teamStops_lbl"),
    LIVE_TEAM("Live Team", "teamLive_lbl"),
    OVERALL_SCORE("Overall Score", "teamOverall_lbl"),
    DRIVING_STYLE("Driving Style", "teamStyle_lbl"),
    SPEED("Speed", "teamSpeeding_lbl"),  
    
    ;
    private String text, url;
    private String[] IDs;
    
    private TeamBarEnum(String url){
    	this.url = url;
    }
    private TeamBarEnum(String text, String ...IDs){
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
