package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.selenium.SeleniumEnums;

public enum NotificationsBarEnum implements SeleniumEnums {

    DEFAULT_URL("notifications/"),
    DEFAULT_CURRENT(""),
    RED_FLAGS_URL("redFlags"),
    SAFETY_URL("safety"),
    DIAGNOSTICS_URL("diagnostics"),
    ZONES_URL("zoneEvents", "zones"),
    HOS_EXCEPTIONS_URL("hosEvents"),
    EMERGENCY_URL("emergency"),
    CRASH_HISTORY_URL("crashHistory"),

    /* Navigation Bar for Notifications */
    RED_FLAGS("Red Flags", "**-redFlags", "//div[@class='sub_nav-bg']/ul/li[1]/a", "//li[@id='redflagtab']/a"),
    SAFETY("Safety", "**-safety", "//div[@class='sub_nav-bg']/ul/li[2]/a", "//li[@id='safetytab']/a"),
    DIAGNOSTICS("Diagnostics", "**-diagnostics", "//div[@class='sub_nav-bg']/ul/li[3]/a", "//li[@id='diagnosticstab']/a"),
    ZONES("Zones", "**-zoneEvents", "//div[@class='sub_nav-bg']/ul/li[4]/a", "//li[@id='zoneEventstab']"),
    HOS_EXCEPTIONS("HOS Exceptions", "**-hosEvents", "//div[@class='sub_nav-bg']/ul/li[5]/a", "//li[@id='hosEventstab']"),
    EMERGENCY("Emergency", "**-emergency", "//div[@class='sub_nav-bg']/ul/li[6]/a", "//li[@id='emergencytab']"),
    CRASH_HISTORY("Crash History", "**-crashHistory", "//div[@class='sub_nav-bg']/ul/li[7]/a", "//li[@id='crashhistorytab']"),

    ;

    private String text, ID, xpath, xpath_alt, current = "redFlags", url;

    private NotificationsBarEnum(String text, String ID, String xpath, String xpath_alt) {
        this.text = text;
        this.ID = ID;
        this.xpath = xpath;
        this.xpath_alt = xpath_alt;
        this.url = null;
    }

    private NotificationsBarEnum(String url) {
        this.url = url;
        this.current = url;
        this.text = null;
        this.ID = null;
        this.xpath = null;
        this.xpath_alt = null;
    }

    private NotificationsBarEnum(String url, String current) {
        this.url = url;
        this.current = current;
        this.text = null;
        this.ID = null;
        this.xpath = null;
        this.xpath_alt = null;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getID() {
        return ID.replace("**", current);
    }

    public String getID(String current) {
        return ID.replace("**", current);
    }

    public String getXpath() {
        return xpath;
    }

    public String getXpath_alt() {
        return xpath_alt;
    }

    public String getURL() {
        return url;
    }
    
    public String getLink(){
        return current;
    }
    
    public void setCurrent(String current){
        this.current = current;
    }

}
