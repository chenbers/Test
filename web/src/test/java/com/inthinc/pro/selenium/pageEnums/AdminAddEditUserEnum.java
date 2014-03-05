package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum AdminAddEditUserEnum implements SeleniumEnums {
    DEFAULT_URL(appUrl + "/admin/editPerson"),

    TITLE("Admin - Add User", Xpath.start().span(Id.clazz("admin")).toString()),
    
    /* Drop downs and such */
    DROP_DOWNS(null, "edit-form:editPerson-***"),

    DRIVER_TEAM_DHX(null, "edit-form:editPerson-driver_groupID"),
    USER_GROUP_DHX(null, "edit-form:editPerson-user_groupID"),

    TEXT_FIELDS(null, "edit-form:editPerson-***"),

    //Text fields
    FIRST_NAME_FIELD("First Name:", "edit-form:editPerson-first"),
    MIDDLE_NAME_FIELD("Middle Name:", "edit-form:editPerson-middle"),
    LAST_NAME_FIELD("Last Name:", "edit-form:editPerson-last"),
    DOB_FIELD("DOB:", "edit-form:editPerson-dobInputDate"),
    DRIVER_LICENSE_NUMBER_FIELD("Driver License #:", "edit-form:editPerson-driver_license"),
    CLASS_FIELD("Class:", "edit-form:editPerson-driver_licenseClass"),
    EXPIRATION_FIELD("Expiration:", "edit-form:editPerson-driver_expirationInputDate"),
    CERTIFICATIONS_FIELD("Certifications:", "edit-form:editPerson-driver_certifications"),
    RFID_BAR_CODE_FIELD("RFID Bar Code", "edit-form:editPerson-driver_barcode"),
    ONE_WIRE_ID_FIELD("1-Wire ID:", "edit-form:editPerson-driver_fobID"),
    EMPLOYEE_ID_FIELD("Employee ID:", "edit-form:editPerson-empId"),
    REPORTS_TO_FIELD("Reports To:", "edit-form:editPerson-reportsTo"),
    TITLE_FIELD("Title:", "edit-form:editPerson-title"),
    USER_NAME_FIELD("User Name:", "edit-form:editPerson-user_username"),
    PASSWORD_FIELD("Password:", "edit-form:editPerson-user_password"),
    PASSWORD_AGAIN_FIELD("Password Again:", "edit-form:editPerson-confirmPassword"),
    EMAIL_ONE_FIELD("Email 1:", "edit-form:editPerson-priEmail"),
    EMAIL_TWO_FIELD("Email 2:", "edit-form:editPerson-secEmail"),
    TEXT_MESSAGE_ONE_FIELD("Text Message 1:", "edit-form:editPerson-priText"),
    TEXT_MESSAGE_TWO_FIELD("Text Message 2:", "edit-form:editPerson-secText"),
    PHONE_ONE_FIELD("Email 1:", "edit-form:editPerson-priPhone"),
    PHONE_TWO_FIELD("Email 2:", "edit-form:editPerson-secPhone"),
    //Buttons
    CANCEL("Cancel", "edit-form:editPersonCancel***"),
    SAVE(save, "edit-form:editPersonSave***"),
    //Drop downs
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
    INFORMATION("Information:", "edit-form:editPerson-info"),
    WARNING("Warning:", "edit-form:editPerson-warn"),
    CRITICAL("Critical:", "edit-form:editPerson-crit")
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
