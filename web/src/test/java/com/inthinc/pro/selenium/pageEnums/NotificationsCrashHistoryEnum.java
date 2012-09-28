package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum NotificationsCrashHistoryEnum implements SeleniumEnums {

    DEFAULT_URL(appUrl + "/notifications/crashHistory"),
    
    TITLE("Crash History", "//span[@class='crash']"),
    
    TEAM_DROP_DOWN("Team", "crashHistorySearch:crashHistory_groupID"),//these two can be merged to NotificationsBarEnum
    TIME_FRAME_DROP_DOWN("Time Frame", "crashHistorySearch:crashHistory_timeframe"),// once the id has been fixed on the page
    
    SEARCH_BOX("Search", "crashHistorySearch:searchText"),
    SEARCH_BUTTON(null, "crashHistorySearch:crashHistorySearch"),
    ADD_CRASH_REPORT("Add Crash Report", "crashHistorySearch:crashHistoryAdd"),
    
    SORT_OCCUPANTS("# Occupants", "***-form:***:occupantsheader:sortDiv"),
    SORT_STATUS("Status", "***-form:***:statusheader:sortDiv"),
    SORT_WEATHER("Weather", "***-form:***:weatherheader:sortDiv"),
    
    DRIVER_ENTRY(null, "crashHistory-form:crashHistory:###:driverName"),   //these can be integrated into the Notifications Events bar if they are renamed
    VEHICLE_ENTRY(null, "crashHistory-form:crashHistory:###:vehiclename"), //these can be integrated into the Notifications Events bar if they are renamed
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
