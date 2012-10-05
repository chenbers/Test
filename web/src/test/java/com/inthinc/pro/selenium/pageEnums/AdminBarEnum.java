package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum AdminBarEnum implements SeleniumEnums {

    /* Admin Navigation Bar stuff */
    USERS_IMAGE(null, "//tr[1]/td/dl/dd/table/tbody/tr/td[1]"),
    USERS("Users", "link=Users", "//a[@id='side-nav-form:***-vlt-people']"),

    ADD_USER("Add User", "link=Add User", "side-nav-form:people-vlst-people"),

    VEHICLES_IMAGE(null, "//tr[2]/td/dl/dd/table/tbody/tr/td[1]"),
    VEHICLES("Vehicles", "link=Vehicles", "//a[@id='side-nav-form:***-vlt-vehicles']"),

    ADD_VEHICLE("Add Vehicle", "link=Add Vehicle", "side-nav-form:vehicles-vlst-vehicles"),

    DEVICES_IMAGE(null, "//tr[4]/td/dl/dd/table/tbody/tr/td[1]"),
    DEVICES("Devices", "link=Devices", "//a[@id='side-nav-form:***-vlt-devices']"),

    ZONES_IMAGE(null, "//tr[5]/td/dl/dd/table/tbody/tr/td[1]"),
    ZONES("Zones", "link=Zones", "//tr[5]/td/dl/dd/table/tbody/tr/td[2]", "side-nav-form:***-vlt-zones"),

    RED_FLAGS_IMAGE(null, "//tr[6]/td/dl/dd/table/tbody/tr/td[1]"),
    RED_FLAGS("Red Flags", "link=Red Flags", "//tr[6]/td/dl/dd/table/tbody/tr/td[2]", "side-nav-form:***-vlt-redFlags"),

    ADD_RED_FLAG("Add Red Flag", "link=Add Red Flag", "side-nav-form:redFlags-vlst-AddRedFlag"),

    REPORTS_IMAGE(null, "//tr[7]/td/dl/dd/table/tbody/tr/td[1]"),
    REPORTS("Reports", "//a[text()='Reports']", "//tr[7]/td/dl/dd/table/tbody/tr/td[2]", "side-nav-form:***-vlt-reportSchedules"),

    ADD_REPORT("Add Report", "link=Add Report", "side-nav-form:reportSchedules-vlst-addReportSchedule"),

    ORGANIZATION_IMAGE(null, "//tr[8]/td/dl/dd/table/tbody/tr/td[1]"),
    ORGANIZATION("Organization", "link=Organization", "//tr[8]/td/dl/dd/table/tbody/tr/td[2]", "side-nav-form:***-vlt-organization"),

    CUSTOM_ROLES_IMAGE(null, "//tr[9]/td/dl/dd/table/tbody/tr/td[1]"),
    CUSTOM_ROLES("Custom Roles", "link=Custom Roles", "//tr[9]/td/dl/dd/table/tbody/tr/td[2]", "side-nav-form:***-vlt-customRoles"),

    ADD_CUSTOM_ROLE("Add Custom Role", "link=Add Custom Role", "side-nav-form:customRoles-vlst-customRoles"),

    SPEED_BY_STREET_IMAGE(null, "//tr[10]/td/dl/dd/table/tbody/tr/td[1]"),
    SPEED_BY_STREET("Speed by Street", "link=Speed By Street", "//tr[10]/td/dl/dd/table/tbody/tr/td[2]", "side-nav-form:***-vlt-sbscr"),

    ACCOUNT_IMAGE(null, "//tr[11]/td/dl/dd/table/tbody/tr/td[1]"),
    ACCOUNT("Account", "link=Account", "//tr[11]/td/dl/dd/table/tbody/tr/td[2]", "side-nav-form:***-vlt-account"),

    
    /* Buttons and Strings */
    SEARCH_BUTTON("Search", "admin-table-form:***Table-adminTableSearch" ),
    SEARCH_LABEL("Search", "//table[@id='grid_nav_search_box']/tbody/tr/td[1]" ),
    SEARCH_TEXTFIELD(null, "admin-table-form:***Table-filterTable" ),
    DELETE("Delete", "admin-table-form:***Table-adminTableDelete"),
    BATCH_EDIT("Batch Edit", "admin-table-form:***Table-adminTableEdit" ),
    
    COUNTER("Showing XXX to YYY of ZZZ records","admin-table-form:recordCounts"),

    
    TITLE(null, Xpath.start().span(Id.clazz("admin")).toString()),

    
    EDIT_COLUMNS_LINK(editColumns, "admin-table-form:personTable-adminTableEditColumns"),

    TABLE_HEADERS(null, "admin-table-form:***Table:*column*header:sortDiv"),
    TABLE_ENTRIES(null, "admin-table-form:***Table:###:*column*"),
    

    SELECT_ALL(null, "admin-table-form:***Table:selectAll"),
    SELECT_ROW(null, "admin-table-form:***Table:###:select"),

    EDIT_ITEM("edit", "admin-table-form:***Table:###:edit"),
    
    
    /* Multi Selector */
    SELECTOR(null, "edit-form:edit***-*type*"),

    MOVE_RIGHT(null, "edit***-right"),
    MOVE_LEFT(null, "edit***-left"),
    MOVE_ALL_RIGHT(null, "edit***-allRight"),
    MOVE_ALL_LEFT(null, "edit***-allLeft"), 
    
    
    MASTER_ERROR(null, "//dt[@class='error']"),
    
    ADMIN_DETAILS_NAME(null, "//span[@class='admin']"),
    
	GO_BACK("< Back to Users", "display-form:***Cancel"),
	DETAILS_DELETE(delete, "display-form:***Delete"),
	EDIT("Edit", "display-form:***Edit"),
    
    ;

    private String text, url;
    private String[] IDs;
    
    private AdminBarEnum(String url){
    	this.url = url;
    }
    private AdminBarEnum(String text, String ...IDs){
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