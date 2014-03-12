package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum NotificationsCrashHistoryEnum implements SeleniumEnums {

    DEFAULT_URL(appUrl + "/notifications/crashHistory"),
    
    TITLE("Crash History", "//span[@class='crash']"),

    SEARCH_BOX("Search", "crashHistory_search:searchText"),
    SEARCH_BUTTON(null, "crashHistory_search:crashHistory_search"),
    ADD_CRASH_REPORT("Add Crash Report", "crashHistory_search:crashHistoryAdd"),
    
    SORT_OCCUPANTS("# Occupants", "crashHistory-form:crashHistory:occupantsheader:sortDiv"),
    SORT_STATUS("Status", "crashHistory-form:crashHistory:statusheader:sortDiv"),
    SORT_WEATHER("Weather", "crashHistory-form:crashHistory:weatherheader:sortDiv"),
    
    OCCUPANTS_ENTRY(null, "crashHistory-form:crashHistory:###:occupants"),
    STATUS_ENTRY(null, "crashHistory-form:crashHistory:###:status"),
    WEATHER_ENTRY(null, "crashHistory-form:crashHistory:###:weather"),
    DETAILS_ENTRY("details", "crashHistory-form:crashHistory:###:details"),
    
    ;
    
    private NotificationsCrashHistoryEnum(String url){
        this.url = url;
    }
    private NotificationsCrashHistoryEnum(String text, String ...IDs){
        this.text=text;
        this.IDs = IDs;
    }
        
    private String text, url;
    private String[] IDs;
    
    @Override
    public String getText() {
        return text;
    }

    @Override
    public String[] getIDs() {
        return IDs;
    }

    @Override
    public String getURL() {
        return url;
    }

}
