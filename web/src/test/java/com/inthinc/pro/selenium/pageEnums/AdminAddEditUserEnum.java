package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum AdminAddEditUserEnum implements SeleniumEnums {
    DEFAULT_URL(appUrl + "/admin/editPerson"),

    /* Drop downs and such */
    DROP_DOWNS(null, "edit-form:editPerson-***"),

    DRIVER_TEAM_DHX(null, "edit-form:editPerson-driver_groupID"),
    USER_GROUP_DHX(null, "edit-form:editPerson-user_groupID"),

    TEXT_FIELDS(null, "edit-form:editPerson-***"),
    MIDDLE_FIELD(null, "edit-form:middle"),

    CANCEL("Cancel", "edit-form:editPersonCancel***"),
    SAVE(save, "edit-form:editPersonSave***"),
    
    LOCALE("Locale:", "edit-form:editPerson-user_locale"),
    TIME_ZONE("Time Zone:", "edit-form:editPerson-timeZone"),
    MEASUREMENT("Measurement:", "edit-form:editPerson-user_person_measurementType"),
    FUEL_EFFICIENCY("Fuel Efficiency Ratio:", "edit-form:editPerson-user_person_fuelEfficiencyType"),
    DOT("DOT:", "edit-form:editPerson-driver_dot"),
    DRIVER_STATUS("Status:", "edit-form:editPerson-driver_status"),
    USER_STATUS("Status:", "edit-form:editPerson-user_status"),
    SUFFIX("Suffix:", "edit-form:editPerson-suffix"),
    GENDER("Gender:", "edit-form:editPerson-gender"), 
    STATE("State:", "edit-form:editPerson-driver_state"),
    
    ;

    private String text, url;
    private String[] IDs;

    private AdminAddEditUserEnum(String url) {
	this.url = url;
    }

    private AdminAddEditUserEnum(String text, String... IDs) {
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
