package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum NotificationsRedFlagsEnum implements SeleniumEnums {
    DEFAULT_URL("/app/notifications/redflags"),
    /* Header */
    MAIN_TITLE("Red Flags", "//span[@id='redFlag_searchRegion:status']/../span[@class='redflag']"),
    MAIN_TITLE_COMMENT("Red Flags", "//span[@id='redFlag_searchRegion:status']/../span[@class='panel_links']"),

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
