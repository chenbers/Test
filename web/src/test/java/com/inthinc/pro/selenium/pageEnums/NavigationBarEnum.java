package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.selenium.SeleniumEnums;

public enum NavigationBarEnum implements SeleniumEnums {

    HOME("Home", "tree_link", "//li[@class='tree_button current", null),
    
    REPORTS("Reports", "navigation:layout-navigationReports", "//li[2]/a[@class='nav_button_left']", null ),
    NOTIFICATIONS("Notifications", "navigation:layout-navigationNotifications", "//li[3]/a[@class='nav_button_left']", null),
    LIVE_FLEET("Live Fleet", "navigation:layout-navigationLiveFleet", "//li[4]/a[@class='nav_button_left']", null),
    HOS("HOS", "navigation:layout-navigationHos", "//li[5]/a[@class='nav_button_left']", null),
    ADMIN("Admin", "navigation:layout-navigationAdmin", "//li[6]/a[@class='nav_button_left']", null),
    SEARCH_BOX(null, "navigation:layout-redirectSearch", "//input[@class='text']", null),
    SEARCH_DROP_DOWN("  Drivers\n    Vehicles\n    Idling\n    Devices", "navigation:layout-navigationRedirectTo", "//select[@class='text']", null),
    SEARCH_BUTTON(null, "navigation:layout-navigation_search_button", "//input[@title='Search']", null),

    ;

    
    private String text, ID, xpath, xpathAlt, url=null;
    
    private NavigationBarEnum(String text, String ID, String xpath, String xpathAlt) {
        this.text = text;
        this.ID = ID;
        this.xpath = xpath;
        this.xpathAlt = xpathAlt;
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
        return xpathAlt;
    }

    public void setText(String text) {
        this.text = text;
    }

}
