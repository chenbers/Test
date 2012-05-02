package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum NotificationsHOSExceptionsEnum implements SeleniumEnums {
    TITLE("HOS Exceptions", "//span[@class='event']"), 
    MESSAGE("Hours of Service notifications from HOS enabled devices.", "//span[@class='panel_links']"),
    
    
    DEFAULT_URL(appUrl + "/notifications/hosEvents"),
    
    
    ;
    private String text, url;
    private String[] IDs;
    
    private NotificationsHOSExceptionsEnum(String url){
        this.url = url;
    }
    private NotificationsHOSExceptionsEnum(String text, String ...IDs){
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
