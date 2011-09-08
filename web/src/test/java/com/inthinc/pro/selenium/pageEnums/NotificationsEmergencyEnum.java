package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum NotificationsEmergencyEnum implements SeleniumEnums {
    DEFAULT_URL("app/notifications/emergency"), 
    MAIN_TITLE("Emergency", "//span[@id='emergency_searchRegion:status']/../span[@class='emergency_title']"), 
    
    ;
    private String text, url;
    private String[] IDs;
    
    private NotificationsEmergencyEnum(String url){
        this.url = url;
    }
    private NotificationsEmergencyEnum(String text, String ...IDs){
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
