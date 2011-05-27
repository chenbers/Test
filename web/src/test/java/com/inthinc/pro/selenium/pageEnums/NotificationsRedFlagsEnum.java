package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum NotificationsRedFlagsEnum implements SeleniumEnums {
    DEFAULT_URL("/app/notifications/redflags"),
    /* Header */
    MAIN_TITLE("Red Flags", "//div[@class='panel_title']/span[1]", "//div[@class='panel_nw']/div/span[1]"),
    MAIN_TITLE_COMMENT("Red Flags", "//div[@class='panel_title']/span[2]", "//div[@class='panel_nw']/div/span[2]"),

    /* Selection bar */
    TEAM_LABEL("Team", "//input[@name='redFlags_search:redFlags_groupID']/../../../../td[1]"),

    TIME_LABEL("Time Frame", "//table[@id='grid_nav_timeframe_box']/tbody/tr/td[1]"),

    STATUS_FILTER(null, "redFlags-form:redFlags:forgivenFilter"),
    CATEGORY_FILTER(null, "redFlags-form:redFlags:catFilter"),
    VEHICLE_FILTER(null, "redFlags-form:redFlags:vehiclefsp"),
    DRIVER_FILTER(null, "redFlags-form:redFlags:driverfsp"),
    GROUP_FILTER(null, "redFlags-form:redFlags:groupfsp"),
    
    GROUP_SORT("Group", "redFlags-form:redFlags:groupheader:sortDiv"),
    DRIVER_SORT("Driver", "redFlags-form:redFlags:driverheader:sortDiv"),
    VEHICLE_SORT("Vehicle", "redFlags-form:redFlags:vehicleheader:sortDiv"),
    
    ALERT_LEVEL_ENTRY(null, "redFlags-form:redFlags:###:level"),
    DETAILS_ENTRY(null, "redFlags-form:redFlags:###:alerts"),
    DATE_TIME_ENTRY(null, "redFlags-form:redFlags:###:time"),
    GROUP_ENTRY(null, "redFlags-form:redFlags:###:group"),
    DRIVER_ENTRY(null, "redFlags-form:redFlags:###:driver"),
    VEHICLE_ENTRY(null, "redFlags-form:redFlags:###:vehicle"),
    CATEGORY_ENTRY(null, "redFlags-form:redFlags:###:category"),
    DETAIL_ENTRY(null, "redFlags-form:redFlags:###:detail"),
    STATUS_ENTRY(null, "redFlags-form:redFlags:###:clear"),
    
    
    
    
    ;

    private String text, url;
    private String[] IDs;
    
    private NotificationsRedFlagsEnum(String url){
    	this.url = url;
    }
    private NotificationsRedFlagsEnum(String text, String ...IDs){
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
