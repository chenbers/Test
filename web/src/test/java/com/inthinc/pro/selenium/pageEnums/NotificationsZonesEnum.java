package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum NotificationsZonesEnum implements SeleniumEnums {

    TITLE("Zones", "//span[@id='zone_searchRegion:status']/../span[@class='event']"),
    NOTE("                          All zone enter and departure notifications from devices.            ", "//span[@id='zone_searchRegion:status']/../span[@class='panel_links']"),
    URL(appUrl + "/notifications/zoneEvents"),
    
    ;

    private String text, url;
    private String[] IDs;

    private NotificationsZonesEnum(String url) {
        this.url = url;
    }

    private NotificationsZonesEnum(String text, String... IDs) {
        this.text = text;
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
