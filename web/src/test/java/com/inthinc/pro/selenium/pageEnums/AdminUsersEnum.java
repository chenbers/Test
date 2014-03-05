package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum AdminUsersEnum implements SeleniumEnums {
    DEFAULT_URL(appUrl + "/admin", appUrl + "/admin/people"),
    TITLE("Admin - Users", Xpath.start().span(Id.clazz("admin")).toString()),

    DELETE(delete, "personTable-form:personTable-adminTableDelete"),
    BATCH_EDIT(batchEdit, "personTable-form:personTable-adminTableEdit"),

    SEARCH_TEXT(search, Xpath.start().table(Id.id("grid_nav_search_box")).tbody().tr().td("1").toString()),

    NAME_TEXTFIELD("Name", "//input[@id='personTable-form:personTable:namefsp']"),
    USER_NAME_TEXTFIELD("User Name", "personTable-form:personTable:usernamefsp"),
    EMAILONE_TEXTFIELD("E-mail 1", "//input[@id='personTable-form:personTable:priEmailfsp']"),
    
    ENTRY_NAME(null, "personTable-form:personTable:###:personTableName"),
    ENTRY_USERNAME(null, "personTable-form:personTable:###:username"),
    
    EDIT_COLUMNS_LINK(editColumns, "personTable-form:personTable-adminTableEditColumns"),

//    TABLE_HEADERS(null, "personTable-form:personTable:***header:sortDiv"),
//    TABLE_ENTRIES(null, "personTable-form:personTable:###:***"),
    SELECT_ALL_CHECKBOX(null, "personTable-form:personTable:selectAll"),
    NAME(null, "personTable-form:personTable:nameheader:sortDiv"),
    USER_STATUS(null, "personTable-form:personTable:statusheader"),
    USER_NAME(null, "personTable-form:personTable:usernameheader"),
    USER_GROUP(null, "personTable-form:personTable:groupNameheader"),
    ROLES(null, "personTable-form:personTable:roleheader"),
    PHONE_1(null, "personTable-form:personTable:priPhoneheader"),
    PHONE_2(null, "personTable-form:personTable:secPhoneheader"),    
    EMAIL_1(null, "personTable-form:personTable:priEmailheader"),
    EMAIL_2(null, "personTable-form:personTable:secEmailheader"), 
    TEXT_MESSAGE_1(null, "personTable-form:personTable:priTextheader"),
    TEXT_MESSAGE_2(null, "personTable-form:personTable:secTextheader"),    
    INFORMATION_ALERTS(null, "personTable-form:personTable:infoheader"),
    WARNING_ALERTS(null, "personTable-form:personTable:warnheader"),
    CRITICAL_ALERTS(null, "personTable-form:personTable:critheader"),
    TIME_ZONE(null, "personTable-form:personTable:timeZoneheader"),
    EMPLOYEE_ID(null, "personTable-form:personTable:empIdheader"),
    REPORTS_TO(null, "personTable-form:personTable:reportsToheader"),
    JOB_TITLE(null, "personTable-form:personTable:titleheader"),
    DOB(null, "personTable-form:personTable:dobheader"),
    GENDER(null, "personTable-form:personTable:genderheader"),
    BAR_CODE(null, "personTable-form:personTable:barcodeheader"),
    RFID_1(null, "personTable-form:personTable:rfid1header"),
    RFID_2(null, "personTable-form:personTable:rfid2header"),
    ONE_WIRE(null, "personTable-form:personTable:fobIDheader"),
    LOCALE(null, "personTable-form:personTable:displayNameheader"),
    MEASUREMENT_TYPE(null, "personTable-form:personTable:measurementTypeheader"),
    FUEL_EFFICIENCY_RATIO(null, "personTable-form:personTable:fuelEfficiencyTypeheader"),
    DRIVER_STATUS(null, "personTable-form:personTable:descriptionheader"),
    DRIVER_LICENSE(null, "personTable-form:personTable:licenseheader"),
    LICENSE_CLASS(null, "personTable-form:personTable:licenseClassheader"),
    LICENSE_STATE(null, "personTable-form:personTable:stateheader"),
    LICENSE_EXPIRATION(null, "personTable-form:personTable:expirationheader"),
    CERTIFICATIONS(null, "personTable-form:personTable:certificationheader"),
    DOT(null, "personTable-form:personTable:dotheader"),
    DRIVER_TEAM(null, "personTable-form:personTable:teamNameheader"),
    
    SELECT_ROW(null, "personTable-form:personTable:###:select"),

    EDIT_USER("edit", "personTable-form:personTable:###:edit"),

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
