package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum AdminUsersEnum implements SeleniumEnums {
    DEFAULT_URL(appUrl + "/admin/people"),
    TITLE("Admin - Users", Xpath.start().span(Id.clazz("admin")).toString()),

    DELETE(delete, "admin-table-form:personTable-adminTableDelete"),
    BATCH_EDIT(batchEdit, "admin-table-form:personTable-adminTableEdit"),

    SEARCH_TEXT(search, Xpath.start().table(Id.id("grid_nav_search_box")).tbody().tr().td("1").toString()),
    SEARCH_TEXT_FIELD(null, "admin-table-form:personTable-filterTable"),
    SEARCH_BUTTON(search, "admin-table-form:personTable-adminTableSearch"),

    EDIT_COLUMNS_LINK(editColumns, "admin-table-form:personTable-adminTableEditColumns"),

    TABLE_HEADERS(null, "admin-table-form:personTable:***header:sortDiv"),
    TABLE_ENTRIES(null, "admin-table-form:personTable:###:***"),
    SELECT_ALL_CHECKBOX(null, "admin-table-form:personTable:selectAll"),
    NAME(null, "admin-table-form:personTable:nameheader"),
    USER_STATUS(null, "admin-table-form:personTable:statusheader"),
    USER_NAME(null, "admin-table-form:personTable:usernameheader"),
    USER_GROUP(null, "admin-table-form:personTable:groupNameheader"),
    ROLES(null, "admin-table-form:personTable:roleheader"),
    PHONE_1(null, "admin-table-form:personTable:priPhoneheader"),
    PHONE_2(null, "admin-table-form:personTable:secPhoneheader"),    
    EMAIL_1(null, "admin-table-form:personTable:priMailheader"),
    EMAIL_2(null, "admin-table-form:personTable:secMailheader"), 
    TEXT_MESSAGE_1(null, "admin-table-form:personTable:priTextheader"),
    TEXT_MESSAGE_2(null, "admin-table-form:personTable:secTextheader"),    
    INFORMATION_ALERTS(null, "admin-table-form:personTable:infoheader"),
    WARNING_ALERTS(null, "admin-table-form:personTable:warnheader"),
    CRITICAL_ALERTS(null, "admin-table-form:personTable:critheader"),
    TIME_ZONE(null, "admin-table-form:personTable:timeZoneheader"),
    EMPLOYEE_ID(null, "admin-table-form:personTable:empIdheader"),
    REPORTS_TO(null, "admin-table-form:personTable:reportsToheader"),
    JOB_TITLE(null, "admin-table-form:personTable:titleheader"),
    DOB(null, "admin-table-form:personTable:dobheader"),
    GENDER(null, "admin-table-form:personTable:genderheader"),
    BAR_CODE(null, "admin-table-form:personTable:barcodeheader"),
    RFID_1(null, "admin-table-form:personTable:rfid1header"),
    RFID_2(null, "admin-table-form:personTable:rfid2header"),
    LOCALE(null, "admin-table-form:personTable:displayNameheader"),
    MEASUREMENT_TYPE(null, "admin-table-form:personTable:measurementTypeheader"),
    FUEL_EFFICIENCY_RATIO(null, "admin-table-form:personTable:fuelEfficiencyTypeheader"),
    DRIVER_STATUS(null, "admin-table-form:personTable:descriptionheader"),
    DRIVER_LICENSE(null, "admin-table-form:personTable:licenseheader"),
    LICENSE_CLASS(null, "admin-table-form:personTable:licenseClassheader"),
    LICENSE_STATE(null, "admin-table-form:personTable:stateheader"),
    LICENSE_EXPIRATION(null, "admin-table-form:personTable:expirationheader"),
    CERTIFICATIONS(null, "admin-table-form:personTable:certificationheader"),
    DOT(null, "admin-table-form:personTable:dotheader"),
    DRIVER_TEAM(null, "admin-table-form:personTable:teamNameheader"),
    
    SELECT_ROW(null, "admin-table-form:personTable:###:select"),

    EDIT_USER("edit", "admin-table-form:personTable:###:edit"),

    ;

    private String text, url;
    private String[] IDs;
    
    private AdminUsersEnum(String url){
    	this.url = url;
    }
    private AdminUsersEnum(String text, String ...IDs){
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
