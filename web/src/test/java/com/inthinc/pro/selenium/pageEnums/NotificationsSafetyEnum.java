package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum NotificationsSafetyEnum implements SeleniumEnums {
	
    MAIN_TITLE("Safety", "//span[@id='safety_searchRegion:status']/../span[@class='event']"), 
    MAIN_TITLE_COMMENT("              			All safety notifications from device including speeding, driving style, seat belt, etc.     		", "//span[@id='safety_searchRegion:status']/../span[@class='panel_links']"), 
    DEFAULT_URL(appUrl + "/notifications/safety"),
    
    ;

    private String text, url;
    private String[] IDs;

    private NotificationsSafetyEnum(String url) {
        this.url = url;
    }

    private NotificationsSafetyEnum(String text, String... IDs) {
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
