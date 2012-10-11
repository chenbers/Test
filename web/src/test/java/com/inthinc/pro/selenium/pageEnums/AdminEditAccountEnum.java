package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum AdminEditAccountEnum implements SeleniumEnums {
    
    DEFAULT_URL(appUrl + "/admin/account"),
    
    TITLE("Admin - Edit Account", "//span[@class='admin']"),
    
    UNKNOWN_DRIVER_HEADER("Unknown Driver", "//tr[1]/td[1]/div[@class='add_section_title']"),
    FIRST("First:","edit-form:editAccount-first"),
    LAST("Last:","edit-form:editAccount-last"),
    TIME_ZONE("Time Zone:","edit-form:editAccount-timeZone"),
    
    MISCELLANEOUS_HEADER("Miscellaneous", "//tr[2]/td[1]/div[@class='add_section_title']"),
    PHONE_ALERTS_ACTIVE("Phone Alerts Active:","edit-form:editAccount-phoneAlerts"),
    NO_REPLY_EMAIL("No Reply "+email,"edit-form:editAccount-noReplyEmail"),

    ADDRESS_HEADER("Address:", "//tr[3]/td[1]/div[@class='add_section_title']"),
    ADDRESS_1("Address 1:","edit-form:editAccount-addr1"),
    ADDRESS_2("Address 2:","edit-form:editAccount-addr2"),
    CITY("City:","edit-form:editAccount-city"),
    STATE("State:","edit-form:editAccount-state"),
    ZIP_CODE("Zip Code:","edit-form:editAccount-zip"),
    
    SUPPORT_CONTACT_INFORMATION_HEADER("Support Contact Information", "//tr[2]/td[3]/div[@class='add_section_title']"),
    CONTACT_1("Contact 1:", "edit-form:editAccount-supportContact0"),
    CONTACT_2("Contact 2:", "edit-form:editAccount-supportContact1"),
    CONTACT_3("Contact 3:", "edit-form:editAccount-supportContact2"),
    CONTACT_4("Contact 4:", "edit-form:editAccount-supportContact3"),
    CONTACT_5("Contact 5:", "edit-form:editAccount-supportContact4"),
    
    SAVE_TOP(save, "edit-form:editAccountSave1"),
    SAVE_BOTTOM(save, "edit-form:editAccountSave2"),
    
    CANCEL_TOP(cancel, "edit-form:editAccountCancel1"),
    CANCEL_BOTTOM(cancel, "edit-form:editAccountCancel2"), 
    EXAMPLE("Example", "edit-form:example"),
    
    
    ;
    private String text, url;
    private String[] IDs;

    private AdminEditAccountEnum(String url) {
        this.url = url;
    }

    private AdminEditAccountEnum(String text, String... IDs) {
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
