package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum NotificationsCrashHistoryEnum implements SeleniumEnums {

    DEFAULT_URL(appUrl + "/notifications/crashHistory"),
    
    TITLE("Crash History", "//span[@class='crash']"),
    

    COUNTER("SHowing XXX to YYY of ZZZ records","crashHistorySearch:header"),
    
    TEAM_DROP_DOWN("Team", "crashHistorySearch:crashHistory_groupID"),
    TIME_FRAME_DROP_DOWN("Time Frame", "crashHistorySearch:crashHistory_timeframe"),
    
    REFRESH_BUTTON(null, "crashHistorySearch:crashHistoryRefresh"),
    SEARCH_BOX("Search", "crashHistorySearch:searchText"),
    SEARCH_BUTTON(null, "crashHistorySearch:crashHistorySearch"),
    
    EDIT_COLUMNS_LINK("Edit Columns", "crashHistorySearch:crashHistoryEditColumns"),
    
    ADD_CRASH_REPORT_LINK("Add Crash Report", "crashHistorySearch:crashHistoryAdd"),
    
    TOOLS_BUTTON("Tools Menu", "crashHistorySearch:crashHistoryReportToolImageId"),
    EXPORT_PDF("Export To PDF", "crashHistorySearch:crashHistory-export_menu_item"),
    EXPORT_EXCEL("Export To Excel", "crashHistorySearch:crashHistory-exportExcelMEnuItem"),
    EXPORT_EMAIL("Email This Report", "crashHistorySearch:crashHistory-emailMenuItem"),
    
    DATE_TIME_TABLE("Date/Time", "crashHistory-form:crashHistory:###:date"),
    GROUP_TABLE("Group", "crashHistory-form:crashHistory:###:group"),
    DRIVER_TABLE("Driver", "crashHistory-form:crashHistory:###:driverName"),
    VEHICLE_TABLE("Vehicle", "crashHistory-form:crashHistory:###:vehicleName"),
    OCCUPANTS_TABLE("# Occupants", "crashHistory-form:crashHistory:###:occupants"),
    STATUS_TABLE("Status", "crashHistory-form:crashHistory:###:status"),
    WEATHER_TABLE("Weather", "crashHistory-form:crashHistory:###:weather"),
    DETAIL_TABLE("detail", "crashHistory-form:crashHistory:###:details"),
    
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
