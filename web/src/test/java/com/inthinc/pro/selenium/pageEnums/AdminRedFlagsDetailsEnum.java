package com.inthinc.pro.selenium.pageEnums;

import org.apache.commons.lang.StringEscapeUtils;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum AdminRedFlagsDetailsEnum implements SeleniumEnums {
    
    TITLE("Admin - NAME_OF_ALERT Details", "//span[@class='admin']"),
    
    BACK_TO_RED_FLAGS(StringEscapeUtils.unescapeHtml("&lt; Back to Red Flags"), "redFlagForm:redFlagCancel"),
    
    DELETE(delete, "redFlagForm:redFlagDelete"),
    EDIT("Edit", "redFlagForm:redFlagEdit"),
    
    NAME_DESCRIPTION_TYPE_HEADER(StringEscapeUtils.unescapeHtml("Name, Description &amp; Type"), "//table[1]/tbody/tr/td[1]/div[@class='add_section_title']"),
    ASSIGN_TO_HEADER("Assign To", "//table[1]/tbody/tr/td[3]/div[@class='add_section_title']"),
    RED_FLAG_TYPE_HEADER("Red Flag Type", "//div/div[@class='add_section_title']"),
    NOTIFICATIONS_BY_NAME_HEADER("Notifications by Name", "//table[3]/tbody/tr/td[1]/div[@class='add_section_title']"),
    PHONE_CALL_ESCALATION_HEADER("Phone Call Escalation", "//table[3]/tbody/tr/td[3]/div[@class='add_section_title']"),
    
    NAME("Name:", "//table[1]/tbody/tr/td[1]/div[@class='add_section_title']/../table/tbody/tr[1]/td[2]"),
    DESCRIPTION("Description:", "//table[1]/tbody/tr/td[1]/div[@class='add_section_title']/../table/tbody/tr[2]/td[2]"),
    STATUS("Status:", "//table[1]/tbody/tr/td[1]/div[@class='add_section_title']/../table/tbody/tr[3]/td[2]"),
    WHICH_DAYS_DAYS(null, "//table[1]/tbody/tr/td[1]/div[@class='add_section_title']/../table/tbody/tr[4]/td[1]"),
    WHICH_DAYS_LABEL("Which Days:", "//table[1]/tbody/tr/td[1]/div[@class='add_section_title']/../table/tbody/tr[4]/td[2]/table/tbody/tr[1]/th[###]"),
    WHICH_DAYS_CHECK_BOX(null, "redFlagForm:redFlag-day###"),
    TIMEFRAME("Timeframe:", "//table[1]/tbody/tr/td[1]/div[@class='add_section_title']/../table/tbody/tr[5]/td[2]"),
    TYPE("Type:", "//table[1]/tbody/tr/td[1]/div[@class='add_section_title']/../table/tbody/tr[6]/td[2]"),
    NOTIFICATION("Notification:", "//table[1]/tbody/tr/td[1]/div[@class='add_section_title']/../table/tbody/tr[7]/td[2]"),
    
    ALERT_GROUPS(null, "//table[1]/tbody/tr/td[3]/div[@class='add_section_title']/../text()"),
    ALERT_OWNER("Alert Owner:", "//table[1]/tbody/tr/td[3]/div[@class='add_section_title']/../table/tbody/tr[2]/td[2]"),
    
    NOTIFICATIONS_BY_NAME_LIST(null, "//table[3]/tbody/tr/td[1]/div[@class='add_section_title']/../div[2]"),
    
    PHONE_CALL_ESCALATION_NUMBER_LIST(null, "phoneNumbers"),
    PHONE_CALL_ESCALATION_EMAIL(null, "//span[@class='emailInput'"),
    CALL_DELAY("Call Delay:", "//table[3]/tbody/tr/td[3]/div[@class='add_section_title']/../table/tbody/tr[1]/td[2]"),
    LIMIT_BY("Limit By:", "//table[3]/tbody/tr/td[3]/div[@class='add_section_title']/../table/tbody/tr[2]/td[2]"),
    
    ;
    private String text, url;
    private String[] IDs;
    
    private AdminRedFlagsDetailsEnum(String url){
        this.url = url;
    }
    private AdminRedFlagsDetailsEnum(String text, String ...IDs){
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
