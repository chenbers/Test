package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum AdminRedFlagAddEditEnum implements SeleniumEnums {
    
    TITLE("Red Flag Type", "//span[@class='admin']"),
    SAVE_TOP(save, "edit-form:editRedFlagSave1"),
    CANCEL_TOP(cancel, "edit-form:editRedFlagCancel1"),
    SAVE_BOTTOM(save, "edit-form:editRedFlagSave2"),
    CANCEL_BOTTOM(cancel, "edit-form:editRedFlagCancel2"),
    
    HEADER_TYPE("Red Flag Type", "//table[1]/tbody/tr/td[1]/div[@class='add_section_title']"),
    HEADER_NAME_DESCRIPTION_TYPE("Name, Description & Type", "//table[2]/tbody/tr/td[1]/div[@class='add_section_title']"),
    HEADER_ASSIGN_TO("Assign To *", "//table[2]/tbody/tr/td[3]/div[@class='add_section_title']"),
    HEADER_DRIVING_STYLE("Driving Style", "//span/div[@class='add_section_title']"),
    HEADER_NOTIFICATIONS_BY_NAME("Notifications By Name", "//table[4]/tbody/tr/td[1]/div[@class='add_section_title']"),
    HEADER_PHONE_CALL_ESCALATION("Phone Call Escalation", "//table[4]/tbody/tr/td[3]/div[@class='add_section_title']"),
    
    TYPE(null, "edit-form:alertType"),
    
    NAME("Name:", "edit-form:editRedFlag-name"),
    DESCRIPTION("Description:", "edit-form:editRedFlag-description"),
    NOTIFICATION("Notification:", "edit-form:editRedFlag-severityLevel"),
    STATUS("Status:", "edit-form:redFlagAlertsStatus"),
    WHICH_DAYS("Which Days:", "//table[@class='days']"),
    TIME_FRAME_ANYTIME("Anytime", "edit-form:editRedFlag-timeframe:0"),
    TIME_FRAME_BETWEEN("Between", "edit-form:editRedFlag-timeframe:1"),
    
    ASSIGN("Assign:", "edit-form:editRedFlag-assignType"),
    FILTER("Filter:", "edit-form:editAlert-filterKeyword"),
    ASSIGN_TO_LEFT(null, "edit-form:editRedFlag-from"),
    ASSIGN_TO_RIGHT(null, "edit-form:editRedFlag-picked"),
    ALERT_OWNER("Alert Owner:", "edit-form:editRedFlag-userID"),
    
    MOVE_RIGHT(null, "edit-form:editRedFlag-move_right"),
    MOVE_LEFT(null, "edit-form:editRedFlag-move_left"),
    MOVE_ALL_RIGHT(null, "edit-form:editRedFlag-move_all_right"),
    MOVE_ALL_LEFT(null, "edit-form:editRedFlag-move_all_left"),
    
    NOTIFICATIONS_BY_NAME_NOTE("Type the names of the employees you would like to notify. Names will appear as you type. Select names from the list.", "//table[4]/tbody/tr/td[1]/div[@class='instructions']"),
    NOTIFICATIONS_BY_NAME(null, "edit-form:editRedFlag-userID"),
    
    PHONE_CALL_NOTE("Type the names of the employees you would like to call. Names will appear as you type. Select names from list. Add one name per field in the order in which you would like them to be called. Finally add a person who will be emailed in the case of none of the calls being acknowledged.", "//td[3]/div[@class='instructions']"),
    PHONE_NUMBERS("Phone Numbers:", "edit-form:phNumbers:###:phNumInput"),
    CALL_DELAY("Call Delay:", "edit-form:redFlagAlertsPhoneDelay"),
    LIMIT_BY_TYPE("Limit By:", "edit-form:redFlagAlertsLimitType"),
    LIMIT_BY_VALUE(null, "edit-form:redFlagAlertsLimitValue"),
    ESCALATION_EMAIL("Escalation " + email, "edit-form:escEmailAddressInput"),
    ESCALATION_EMAIL_NOTE("Escalation Email will be sent when all the phone numbers have been called with no answer.", "//td[3]/div[@class='instructions'][2]"),
    
    
    
    ;
    private String text, url;
    private String[] IDs;

    private AdminRedFlagAddEditEnum(String url) {
        this.url = url;
    }

    private AdminRedFlagAddEditEnum(String text, String... IDs) {
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
