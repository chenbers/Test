package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum AdminAccountDetailsEnum implements SeleniumEnums {

    DEFAULT_URL(appUrl + "/admin/account"),
    
    TITLE("Admin - Account Details", "//span[@class='admin']"),
    
    UNKNOWN_DRIVER_HEADER("Unknown Driver", "//tr[1]/td[1]/div[@class='add_section_title']"),
    FIRST("First:","//tr[1]/td[1]/div[@class='add_section_title']/../table/tbody/tr[1]/td[2]"),
    LAST("Last:","//tr[1]/td[1]/div[@class='add_section_title']/../table/tbody/tr[2]/td[2]"),
    TIME_ZONE("Time Zone:","//tr[1]/td[1]/div[@class='add_section_title']/../table/tbody/tr[3]/td[2]"),
    
    MISCELLANEOUS_HEADER("Miscellaneous", "//tr[2]/td[1]/div[@class='add_section_title']"),
    PHONE_ALERTS_ACTIVE("Phone Alerts Active:","display-form:editAccount-phoneAlerts"),
    NO_REPLY_EMAIL("No Reply "+email,"//tr[2]/td[1]/div[@class='add_section_title']/../table/tbody/tr[2]/td[2]"),

    ADDRESS_HEADER("Address:", "//tr[3]/td[1]/div[@class='add_section_title']"),
    ADDRESS_1("Address 1:","display-form:editAccount-addr1"),
    ADDRESS_2("Address 2:","display-form:editAccount-addr2"),
    CITY("City:","display-form:editAccount-city"),
    STATE("State:","display-form:editAccount-state"),
    ZIP_CODE("Zip Code:","display-form:editAccount-zip"),
    
    SUPPORT_CONTACT_INFORMATION_HEADER("Support Contact Information", "//tr[2]/td[3]/div[@class='add_section_title']"),
    CONTACT_1("Contact 1:", "//tr[2]/td[3]/div[@class='add_section_title']/../table/tbody/tr[1]/td[2]"),
    CONTACT_2("Contact 2:", "//tr[2]/td[3]/div[@class='add_section_title']/../table/tbody/tr[2]/td[2]"),
    CONTACT_3("Contact 3:", "//tr[2]/td[3]/div[@class='add_section_title']/../table/tbody/tr[3]/td[2]"),
    CONTACT_4("Contact 4:", "//tr[2]/td[3]/div[@class='add_section_title']/../table/tbody/tr[4]/td[2]"),
    CONTACT_5("Contact 5:", "//tr[2]/td[3]/div[@class='add_section_title']/../table/tbody/tr[5]/td[2]"),
    
    EDIT_BUTTON("Edit", "display-form:accountEdit"),

    ;

    private String text, url;
    private String[] IDs;

    private AdminAccountDetailsEnum(String url) {
        this.url = url;
    }

    private AdminAccountDetailsEnum(String text, String... IDs) {
        this.text = text;
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
