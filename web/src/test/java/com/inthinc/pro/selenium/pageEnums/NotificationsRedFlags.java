package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum NotificationsRedFlags implements SeleniumEnums {
    DEFAULT_URL("/app/notifications/redflags"),
    /* Header */
    MAIN_TITLE("Red Flags", "//div[@class='panel_title']/span[1]", "//div[@class='panel_nw']/div/span[1]"),
    MAIN_TITLE_COMMENT("Red Flags", "//div[@class='panel_title']/span[2]", "//div[@class='panel_nw']/div/span[2]"),

    /* Selection bar */
    TEAM_LABEL("Team", "//table[@id='grid_nav_group_box']/tbody/tr/td[1]"),
    TEAM_SELECTION(null, "//table[@id='grid_nav_group_box']/tbody/tr/td[2]/span/div"),
    TEAM_SELECTION_IMG(null, "//table[@id='grid_nav_group_box']/tbody/tr/td[2]/span/div/img"),

    TIME_LABEL("Time Frame", "//table[@id='grid_nav_timeframe_box']/tbody/tr/td[1]"),
    TIME_SELECTION("Today", "//table[@id='grid_nav_timeframe_box']/tbody/tr/td[2]"),
    TIME_SELECTION_IMG(null, "//table[@id='grid_nav_timeframe_box']/tbody/tr/td[1]/"),

    ;

    private String text, url;
    private String[] IDs;
    
    private NotificationsRedFlags(String url){
    	this.url = url;
    }
    private NotificationsRedFlags(String text, String ...IDs){
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
