package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum NotificationsDriverLoginsEnum implements SeleniumEnums {

    DEFAULT_URL(appUrl + "/notifications/driverLogins"),
    
    TITLE("Driver Logins", "//span[@class='event']"),
    MESSAGE("                       All driver login notifications.             ", "//span[@class='panel_links']"),
    
    ;

    private String text, url;
    private String[] IDs;
    
    private NotificationsDriverLoginsEnum(String url){
        this.url = url;
    }
    private NotificationsDriverLoginsEnum(String text, String ...IDs){
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
