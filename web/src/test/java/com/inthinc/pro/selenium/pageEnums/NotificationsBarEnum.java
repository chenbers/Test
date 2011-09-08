package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum NotificationsBarEnum implements SeleniumEnums {
    DEFAULT_URL("notifications/"),

    /* Navigation Bar for Notifications */
    RED_FLAGS("Red Flags", "link=Red Flags", "***-redFlags", "//li[@id='redflagtab']/a"),
    SAFETY("Safety", "link=Safety", "***-safety", "//li[@id='safetytab']/a"),
    DIAGNOSTICS("Diagnostics", "link=Diagnostics", "***-diagnostics", "//li[@id='diagnosticstab']/a"),
    ZONES("Zones", "link=Zones", "***-zoneEvents", "//li[@id='zoneEventstab']/a"),
    HOS_EXCEPTIONS("HOS Exceptions", "link=HOS Exceptions", "***-hosEvents", "//li[@id='hosEventstab']/a"),
    EMERGENCY("Emergency", "link=Emergency", "***-emergency", "//li[@id='emergencytab']/a"),
    CRASH_HISTORY("Crash History", "link=Crash History", "***-crashHistory", "//li[@id='crashhistorytab']/a"),
    

    STATUS_FILTER(null, "***-form:***:forgivenFilter"),
    CATEGORY_FILTER(null, "***-form:***:catFilter"),
    VEHICLE_FILTER(null, "***-form:***:vehiclefsp"),
    DRIVER_FILTER(null, "***-form:***:driverfsp"),
    GROUP_FILTER(null, "***-form:***:groupfsp"),
    
    

    LEVEL_FILTER_DHX(null, "***-form:***:levelFilter"),
    TIME_FRAME_DHX(null, "***_search:***_timeframe"),
    TEAM_SELECTION_DHX(null, "***_search:***_groupID"),
    
    REFRESH(null, "***_search:***_refresh"),
    
    EDIT_COLUMNS(null, "***_search:***_editColumns"),
    
    TOOLS(null, "***_search:***_reportToolImageId"),
    EMAIL_REPORT(null, "***_search:***-emailMenuItem:anchor"),
    EXPORT_TO_PDF(null, "***_search:***-export_menu_item:anchor"),
    EXPORT_TO_EXCEL(null, "***_search:***-exportExcelMEnuItem:anchor"),
    
    COUNTER(null, "***-form:header"),
    

    LOCATION(null, "***-form:***:###:mapColumn"),
    
    
    LEVEL_ENTRY(null, "***-form:***:###:level"),
    DETAILS_ENTRY(null, "***-form:***:###:alerts"),
    DATE_TIME_ENTRY(null, "***-form:***:###:date"),
    GROUP_ENTRY(null, "***-form:***:###:group"),
    DRIVER_ENTRY(null, "***-form:***:###:driver"),
    VEHICLE_ENTRY(null, "***-form:***:###:vehicle"),
    CATEGORY_ENTRY(null, "***-form:***:###:category"),
    DETAIL_ENTRY(null, "***-form:***:###:detail"),
    STATUS_ENTRY("exclude", "***-form:***:###:exclude"),
    

    HEADER_LEVEL("Level", "***-form:***:levelheader"),
    HEADER_DETAILS("Alert Details", "***-form:***:alertsheader"),
    SORT_DATE_TIME("Date/Time", "***-form:***:dateheader:sortDiv"),
    SORT_GROUP("Group", "***-form:***:groupheader:sortDiv"),
    SORT_DRIVER("Driver", "***-form:***:driverheader:sortDiv"),
    SORT_VEHICLE("Vehicle", "***-form:***:vehicleheader:sortDiv"),
    HEADER_CATEGORY("Category", "***-form:***:categoryheader"),
    HEADER_DETAIL("Detail", "***-form:***:detailheader"),
    HEADER_STATUS("Status", "***-form:***:excludeheader"),

    ;
    private String text, url;
    private String[] IDs;
    
    private NotificationsBarEnum(String url){
    	this.url = url;
    }
    private NotificationsBarEnum(String text, String ...IDs){
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
