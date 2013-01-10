package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum NotificationsCrashHistoryEnum implements SeleniumEnums {

    DEFAULT_URL(appUrl + "/notifications/crashHistory"),
    
    TITLE("Crash History", "//span[@class='crash']"),
    
    TEAM_DROP_DOWN("Team", "crashHistorySearch:crashHistory_groupID"), //"crashHistorySearch:crashHistory_groupID"),//these two can be merged to NotificationsBarEnum
    TIME_FRAME_DROP_DOWN("Time Frame", "crashHistorySearch:crashHistory_timeframe"),// once the id has been fixed on the page
    
    REFRESH("Refresh", "//button[@id='crashHistorySearch:crashHistoryRefresh']"),
    
    SEARCH_BOX("Search", "crashHistorySearch:searchText"),
    SEARCH_BUTTON(null, "crashHistorySearch:crashHistorySearch"),
    ADD_CRASH_REPORT("Add Crash Report", "crashHistorySearch:crashHistoryAdd"),
    EDIT_COLUMNS("Edit Columns", "//a[@id='crashHistorySearch:crashHistoryEditColumns']"),
    
    TOOLS(null, "//span[@id='crashHistorySearch:crashHistoryReportToolImageId']"),
    EMAIL_REPORT(null, "crashHistorySearch:crashHistory-emailMenuItem:anchor"),
    EXPORT_TO_PDF(null, "crashHistorySearch:crashHistory-export_menu_item:anchor"),
    EXPORT_TO_EXCEL(null, "crashHistorySearch:crashHistory-exportExcelMEnuItem:anchor"),
    
    SORT_DATE_TIME("Date/Time", "crashHistory-form:crashHistory:dateheader:sortDiv"),
    SORT_GROUP("Group", "crashHistory-form:crashHistory:groupheader:sortDiv"),
    SORT_DRIVER("Driver", "crashHistory-form:crashHistory:driverNameheader:sortDiv"),
    SORT_VEHICLE("Vehicle", "crashHistory-form:crashHistory:vehiclenameheader:sortDiv"),
    SORT_OCCUPANTS("# Occupants", "crashHistory-form:crashHistory:occupantsheader:sortDiv"),
    SORT_STATUS("Status", "crashHistory-form:crashHistory:statusheader:sortDiv"),
    SORT_WEATHER("Weather", "crashHistory-form:crashHistory:weatherheader:sortDiv"),
    
    GROUP_ENTRY(null, "crashHistory-form:crashHistory:###:group"),
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
