package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

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

    ;

    private String ID, text, xpath, xpath_alt;

    private AdminBarEnum(String text, String ID, String xpath, String xpath_alt) {
        this.text = text;
        this.ID = ID;
        this.xpath = xpath;
        this.xpath_alt = xpath_alt;
    }

    public String getID() {
        return ID;
    }

    public String getText() {
        return text;
    }

    public String getURL() {
        return null;
    }

    public String getXpath() {
        return xpath;
    }

    public String getXpath_alt() {
        return xpath_alt;
    }

    public void setText(String text) {
        this.text = text;
    }

}
