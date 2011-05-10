package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum IdlingReportEnum implements SeleniumEnums {
    TEAM_SEARCH(null, "idling-form:idling:groupfsp"),
    DRIVER_SEARCH(null, "idling-form:idling:fullNamefsp"),
    IDLING_FORM(null, "idling-form"),
    TEAM(null, "idling-form:idling:0:idlingsDashboard"),
    DRIVER(null, "idling-form:idling:0:idlingDriverPerformance"),
    TRIPS(null, "idling-form:idling:0:idlingDriverTrips"),
    START_DATE(null, "idling-form:startCalendarInputDate"),
    END_DATE(null, "idling-form:endCalendarInputDate"),
    REFRESH(null, "idling-form:idling_refresh");

    private String text, url;
    private String[] IDs;
    
    private IdlingReportEnum(String url){
    	this.url = url;
    }
    private IdlingReportEnum(String text, String ...IDs){
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
