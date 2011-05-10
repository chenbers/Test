package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum NotificationsBarEnum implements SeleniumEnums {

    DEFAULT_URL("notifications/"),
    DEFAULT_CURRENT(""),
    RED_FLAGS_URL("redFlags"),
    SAFETY_URL("safety"),
    DIAGNOSTICS_URL("diagnostics"),
    ZONES_URL("zoneEvents", "zones", ""),
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
    private String text, url;
    private String[] IDs;
    
    private NotificationsBarEnum(String url){
    	this.url = url;
    }
    private NotificationsBarEnum(String text, String ...IDs){
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
