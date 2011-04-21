package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

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
    RED_FLAGS("Red Flags", "link=Red Flags", "***-redFlags", "//li[@id='redflagtab']/a"),
    SAFETY("Safety", "link=Zones", "***-safety", "//li[@id='safetytab']/a"),
    DIAGNOSTICS("Diagnostics", "link=Diagnostics", "***-diagnostics", "//li[@id='diagnosticstab']/a"),
    ZONES("Zones", "link=Zones", "***-zoneEvents", "//li[@id='zoneEventstab']/a"),
    HOS_EXCEPTIONS("HOS Exceptions", "link=HOS Exceptions", "***-hosEvents", "//li[@id='hosEventstab']/a"),
    EMERGENCY("Emergency", "link=Emergency", "***-emergency", "//li[@id='emergencytab']/a"),
    CRASH_HISTORY("Crash History", "link=Crash History", "***-crashHistory", "//li[@id='crashhistorytab']/a"),

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
        return ID;
    }

    public String getXpath(String current) {
        return xpath.replace("***", current);
    }

    public String getXpath() {
        return xpath.replace("***", current);
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
