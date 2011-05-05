package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.utils.Xpath;

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
    }

    private NotificationsBarEnum(String url) {
        this.url = url;
    }

    private NotificationsBarEnum(String text, String ID) {
        this(text, ID, "", null);
    }

    private NotificationsBarEnum(String text, String ID, String xpath) {
        this(text, ID, xpath, null);
    }

    private NotificationsBarEnum(String text, String ID, Xpath xpath, Xpath xpath_alt) {
        this(text, ID, xpath.toString(), xpath_alt.toString());
    }

    private NotificationsBarEnum(String text, String ID, Xpath xpath) {
        this(text, ID, xpath.toString(), null);
    }

    private NotificationsBarEnum(String text, Xpath xpath) {
        this(text, null, xpath.toString(), null);
    }

    private NotificationsBarEnum(Xpath xpath, Xpath xpath_alt) {
        this(null, null, xpath.toString(), xpath_alt.toString());
    }

    private NotificationsBarEnum(Xpath xpath) {
        this(null, null, xpath.toString(), null);
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

    public String getLink() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

}
