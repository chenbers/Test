package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum AdminAddEditUserEnum implements SeleniumEnums {
    DEFAULT_URL("/app/admin/editPerson"),

    /* Drop downs and such */
    DROP_DOWNS(null, "edit-form:editPerson-***"),

    DRIVER_TEAM_DHX(null, "edit-form:editPerson-driver_groupID"),
    USER_GROUP_DHX(null, "edit-form:editPerson-user_groupID"),

    TEXT_FIELDS(null, "edit-form:editPerson-***"),
    MIDDLE_FIELD(null, "edit-form:middle"),

    CANCEL("Cancel", "edit-form:editPersonCancel***"),
    SAVE(save, "edit-form:editPersonSave***"),

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
