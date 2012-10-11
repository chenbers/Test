package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum NavigationBarEnum implements SeleniumEnums {

    HOME("Home", "tree_link", "//li[@class='tree_button current"),
    

    LOGO(null, "headerForm:headerInitDashboard"),

    REPORTS("Reports", "navigation:layout-navigationReports", "//li[2]/a[@class='nav_button_left']"),
    NOTIFICATIONS("Notifications", "navigation:layout-navigationNotifications", "//li[3]/a[@class='nav_button_left']"),
    LIVE_FLEET("Live Fleet", "navigation:layout-navigationLiveFleet", "//li[4]/a[@class='nav_button_left']"),
    HOS("HOS", "navigation:layout-navigationHos", "//li[5]/a[@class='nav_button_left']"),
    ADMIN("Admin", "navigation:layout-navigationAdmin", "//li[6]/a[@class='nav_button_left']"),
    FORMS("Forms", "navigation:layout-navigationForms", "//li[7]/a[@class='nav_button_left']"),
    SEARCH_BOX(null, "navigation:layout-redirectSearch", "//input[@class='text']"),
    SEARCH_DROP_DOWN("  Drivers\n    Vehicles\n    Idling\n    Devices", "navigation:layout-navigationRedirectTo", "//select[@class='text']"),
    SEARCH_BUTTON(null, "navigation:layout-navigation_search_button", "//input[@title='Search']"),

    ;

    private String text, url;
    private String[] IDs;
    
    private NavigationBarEnum(String url){
    	this.url = url;
    }
    private NavigationBarEnum(String text, String ...IDs){
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
