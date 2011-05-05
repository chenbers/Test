package com.inthinc.pro.selenium.pageEnums;



import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.utils.Xpath;



public enum NavigationBarEnum implements SeleniumEnums {

    HOME("Home", "tree_link", "//li[@class='tree_button current"),
    
    REPORTS("Reports", "navigation:layout-navigationReports", "//li[2]/a[@class='nav_button_left']"),
    NOTIFICATIONS("Notifications", "navigation:layout-navigationNotifications", "//li[3]/a[@class='nav_button_left']"),
    LIVE_FLEET("Live Fleet", "navigation:layout-navigationLiveFleet", "//li[4]/a[@class='nav_button_left']"),
    HOS("HOS", "navigation:layout-navigationHos", "//li[5]/a[@class='nav_button_left']"),
    ADMIN("Admin", "navigation:layout-navigationAdmin", "//li[6]/a[@class='nav_button_left']"),
    SEARCH_BOX(null, "navigation:layout-redirectSearch", "//input[@class='text']"),
    SEARCH_DROP_DOWN("  Drivers\n    Vehicles\n    Idling\n    Devices", "navigation:layout-navigationRedirectTo", "//select[@class='text']"),
    SEARCH_BUTTON(null, "navigation:layout-navigation_search_button", "//input[@title='Search']"),
    
    ;

    
    private String text, ID, xpath, xpath_alt, url=null;
    
    private NavigationBarEnum(String text, String ID, String xpath, String xpath_alt) {
        this.text = text;
        this.ID = ID;
        this.xpath = xpath;
        this.xpath_alt = xpath_alt;
    }

    private NavigationBarEnum(String url) {
        this.url = url;
    }

    private NavigationBarEnum(String text, String ID) {
        this(text, ID, "", null);
    }

    private NavigationBarEnum(String text, String ID, String xpath) {
        this(text, ID, xpath, null);
    }

    private NavigationBarEnum(String text, String ID, Xpath xpath, Xpath xpath_alt) {
        this(text, ID, xpath.toString(), xpath_alt.toString());
    }

    private NavigationBarEnum(String text, String ID, Xpath xpath) {
        this(text, ID, xpath.toString(), null);
    }

    private NavigationBarEnum(String text, Xpath xpath) {
        this(text, null, xpath.toString(), null);
    }

    private NavigationBarEnum(Xpath xpath, Xpath xpath_alt) {
        this(null, null, xpath.toString(), xpath_alt.toString());
    }

    private NavigationBarEnum(Xpath xpath) {
        this(null, null, xpath.toString(), null);
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
