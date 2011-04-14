package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.selenium.SeleniumEnums;

public enum NotificationsRedFlags implements SeleniumEnums {
    /* Header */
    MAIN_TITLE("Red Flags", null, "//div[@class='panel_title']/span[1]", "//div[@class='panel_nw']/div/span[1]" ),
    MAIN_TITLE_COMMENT("Red Flags", null, "//div[@class='panel_title']/span[2]", "//div[@class='panel_nw']/div/span[2]" ),
    
    /* Selection bar */
    TEAM_LABEL("Team", null, "//table[@id='grid_nav_group_box']/tbody/tr/td[1]", null ),
    TEAM_SELECTION(null, null, "//table[@id='grid_nav_group_box']/tbody/tr/td[2]/span/div", null),
    TEAM_SELECTION_IMG(null, null, "//table[@id='grid_nav_group_box']/tbody/tr/td[2]/span/div/img", null),
    
    TIME_LABEL("Time Frame", null, "//table[@id='grid_nav_timeframe_box']/tbody/tr/td[1]", null ),
    TIME_SELECTION("Today", null, "//table[@id='grid_nav_timeframe_box']/tbody/tr/td[2]", null),
    TIME_SELECTION_IMG(null, null, "//table[@id='grid_nav_timeframe_box']/tbody/tr/td[1]/", null),
    
    
    ;

    private String text, ID, xpath, xpath_alt, url;
    
    private NotificationsRedFlags(String text, String ID, String xpath, String xpath_alt){
        this.text = text;
        this.ID = ID;
        this.xpath = xpath;
        this.xpath_alt = xpath_alt;
        this.url = null;
    }
    
    private NotificationsRedFlags(String url) {
        this.url = url;
        this.text = null;
        this.ID = null;
        this.xpath = null;
        this.xpath_alt = null;
    }

    public String getID() {
        return ID;
    }

    public String getText() {
        return text;
    }

    public String getURL() {
        return url;
    }

    public String getXpath() {
        return xpath;
    }

    public String getXpath_alt() {
        return xpath_alt;
    }

    public void setText(String text) {
        this.text = text;
    }

}
