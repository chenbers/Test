package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.selenium.CoreMethodLib;

public enum TeamBarEnum implements SeleniumEnums {
    TEAM_TITLE(null, null, "//li[@class='l grid_title max-width']", null),
    TEAM_SCORE(null, null, "//li[@class='l']/span/table/tbody/tr/td", null ),
    
    CRASHES_PER_MILLION_NUMBER(null, null, "//li[5][@class='l crash-item']/span[2]", null),
    CRASHES_PER_MILLION_TEXT("Crashes per million miles", null, "//li[5][@class='l crash-item']/text()[2]", null),
    
    DAYS_SINCE_NUMBER(null, null, "//li[6][@class='l crash-item']/span", null),
    DAYS_SINCE_TEXT("Days since last crash", null, "//li[6][@class='l crash-item']/text()", null),
    
    MILES_SINCE_NUMBER(null, null, "//li[7][@class='l crash-item']/span", null),
    MILES_SINCE_TEXT("Miles since last crash", null, "//li[7][@class='l crash-item']/text()", null),
    
    SUB_TITLE("What Happened...", null, "//div[@class='panel_title']/text()[1]", null),
    
    DRIVER_STATISTICS("Driver Statistics", "teamStatistics_lbl", null, null),
    TRIPS("Trips", "teamTrips_lbl", null, null),
    STOPS("Stops", "teamStops_lbl", null, null),
    LIVE_TEAM("Live Team", "teamLive_lbl", null, null),
    OVERALL_SCORE("Overall Score", "teamOverall_lbl", null, null),
    DRIVING_STYLE("Driving Style", "teamStyle_lbl", null, null),
    SPEED("Speed", "teamSpeeding_lbl", null, null),  
    
    ;
    
    private String ID, text, xpath, xpath_alt;

    private TeamBarEnum(String text, String ID, String xpath, String xpath_alt) {
        this.text = text;
        this.ID = ID;
        this.xpath = xpath;
        this.xpath_alt = xpath_alt;
    }

    public String getID() {
        return ID;
    }

    public String getText() {
        return text;
    }

    public String getURL() {
        return null;
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
