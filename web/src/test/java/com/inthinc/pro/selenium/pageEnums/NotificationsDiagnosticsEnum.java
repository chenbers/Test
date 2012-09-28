package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum NotificationsDiagnosticsEnum implements SeleniumEnums {
    URL(appUrl + "/notifications/diagnostics"),
    TITLE("Diagnostics", "//span[@id='diagnostics_searchRegion:status']/../span[@class='diagnostics_title']"),
    MESSAGE("                       All diagnostic related notifications from device including idling, low battery, tampering, etc.             ", "//span[@id='diagnostics_searchRegion:status']/../span[@class='panel_links']"),
    
    ;
    private String text, url;
    private String[] IDs;
    
    private NotificationsDiagnosticsEnum(String url){
        this.url = url;
    }
    private NotificationsDiagnosticsEnum(String text, String ...IDs){
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
