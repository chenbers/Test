package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum NotificationsMaintenanceEnum implements SeleniumEnums {

    DEFAULT_URL(appUrl + "/notifications/maintenance"),
    /* Header */
    MAIN_TITLE("Maintenance", "//span[@id='maintenance_searchRegion:status']/../span[@class='maintenance']"),
    MAIN_TITLE_COMMENT("Maintenance", "//span[@id='maintenance_searchRegion:status']/../span[@class='panel_links']"),

    ;

    private String text, url;
    private String[] IDs;

    private NotificationsMaintenanceEnum(String url){
        this.url = url;
    }
    private NotificationsMaintenanceEnum(String text, String ...IDs){
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
