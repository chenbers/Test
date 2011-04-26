package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum AdminBarEnum implements SeleniumEnums {

    /* Admin Navigation Bar stuff */
    USERS_IMAGE(null, null, "//tr[1]/td/dl/dd/table/tbody/tr/td[1]", null),
    USERS("Users", "link=Users", "//tr[1]/td/dl/dd/table/tbody/tr/td[2]", "side-nav-form:***-vlt-people"),

    ADD_USER("Add User", "link=Add User", null, "side-nav-form:people-vlst-people"),

    VEHICLES_IMAGE(null, null, "//tr[2]/td/dl/dd/table/tbody/tr/td[1]", null),
    VEHICLES("Vehicles", "link=Vehicles", "//tr[2]/td/dl/dd/table/tbody/tr/td[2]", "side-nav-form:***-vlt-vehicles"),

    ADD_VEHICLE("Add Vehicle", "link=Add Vehicle", null, "side-nav-form:vehicles-vlst-vehicles"),

    DEVICES_IMAGE(null, null, "//tr[4]/td/dl/dd/table/tbody/tr/td[1]", null),
    DEVICES("Devices", "link=Devices", "//tr[4]/td/dl/dd/table/tbody/tr/td[2]", "side-nav-form:***-vlt-devices"),

    ZONES_IMAGE(null, null, "//tr[5]/td/dl/dd/table/tbody/tr/td[1]", null),
    ZONES("Zones", "link=Zones", "//tr[5]/td/dl/dd/table/tbody/tr/td[2]", "side-nav-form:***-vlt-zones"),

    RED_FLAGS_IMAGE(null, null, "//tr[6]/td/dl/dd/table/tbody/tr/td[1]", null),
    RED_FLAGS("Red Flags", "link=Red Flags", "//tr[6]/td/dl/dd/table/tbody/tr/td[2]", "side-nav-form:***-vlt-redFlags"),

    ADD_RED_FLAG("Add Red Flag", "link=Add Red Flag", null, "side-nav-form:redFlags-vlst-AddRedFlag"),

    REPORTS_IMAGE(null, null, "//tr[7]/td/dl/dd/table/tbody/tr/td[1]", null),
    REPORTS("Reports", "//a[text()='Reports']", "//tr[7]/td/dl/dd/table/tbody/tr/td[2]", "side-nav-form:***-vlt-reportSchedules"),

    ADD_REPORT("Add Report", "link=Add Report", null, "side-nav-form:reportSchedules-vlst-addReportSchedule"),

    ORGANIZATION_IMAGE(null, null, "//tr[8]/td/dl/dd/table/tbody/tr/td[1]", null),
    ORGANIZATION("Organization", "link=Organization", "//tr[8]/td/dl/dd/table/tbody/tr/td[2]", "side-nav-form:***-vlt-organization"),

    CUSTOM_ROLES_IMAGE(null, null, "//tr[9]/td/dl/dd/table/tbody/tr/td[1]", null),
    CUSTOM_ROLES("Custom Roles", "link=Custom Roles", "//tr[9]/td/dl/dd/table/tbody/tr/td[2]", "side-nav-form:***-vlt-customRoles"),

    ADD_CUSTOM_ROLE("Add Custom Role", "link=Add Custom Role", null, "side-nav-form:customRoles-vlst-customRoles"),

    SPEED_BY_STREET_IMAGE(null, null, "//tr[10]/td/dl/dd/table/tbody/tr/td[1]", null),
    SPEED_BY_STREET("Speed by Street", "link=Speed By Street", "//tr[10]/td/dl/dd/table/tbody/tr/td[2]", "side-nav-form:***-vlt-sbscr"),

    ACCOUNT_IMAGE(null, null, "//tr[11]/td/dl/dd/table/tbody/tr/td[1]", null),
    ACCOUNT("Account", "link=Account", "//tr[11]/td/dl/dd/table/tbody/tr/td[2]", "side-nav-form:***-vlt-account"),

    
    EDIT_COLUMNS_HEADER(editColumns, "editColumnsHeader"),
    EDIT_COLUMNS_CANCEL(cancel, "editColumnsForm:***Table-editColumnsPopupCancel"),
    EDIT_COLUMNS_SAVE(save, "editColumnsForm:***Table-editColumnsPopupSave"),
    EDIT_COLUMNS_TITLE("The selected columns will be displayed.", Xpath.start().div(Id.clazz("popupsubtitle"))),
    
    EDIT_COLUMNS_CHECKBOX(null, "editColumnsForm:***Table-editColumnsGrid:###:***Table-col"),
    EDIT_COLUMNS_LABEL(Xpath.start().td(Id.id("editColumnsForm:***Table-editColumnsGrid:###")).label().text())
    
    ;

    private String text, ID, xpath, xpath_alt, url;

    private AdminBarEnum(String text, String ID, String xpath, String xpath_alt) {
        this.text = text;
        this.ID = ID;
        this.xpath = xpath;
        this.xpath_alt = xpath_alt;
    }

    private AdminBarEnum(String url) {
        this.url = url;
    }

    private AdminBarEnum(String text, String ID) {
        this(text, ID, "", null);
    }

    private AdminBarEnum(String text, String ID, String xpath) {
        this(text, ID, xpath, null);
    }

    private AdminBarEnum(String text, String ID, Xpath xpath, Xpath xpath_alt) {
        this(text, ID, xpath.toString(), xpath_alt.toString());
    }

    private AdminBarEnum(String text, String ID, Xpath xpath) {
        this(text, ID, xpath.toString(), null);
    }

    private AdminBarEnum(String text, Xpath xpath) {
        this(text, null, xpath.toString(), null);
    }

    private AdminBarEnum(Xpath xpath, Xpath xpath_alt) {
        this(null, null, xpath.toString(), xpath_alt.toString());
    }

    private AdminBarEnum(Xpath xpath) {
        this(null, null, xpath.toString(), null);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getID() {
        return ID;
    }

    public String getXpath() {
        return xpath;
    }

    public String getXpath_alt() {
        return xpath_alt;
    }

    public String getURL() {
        return url;
    }
}
