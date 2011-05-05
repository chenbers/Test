package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum AdminVehiclesEnum implements SeleniumEnums {
    
    TITLE("Admin - Vehicles", null, Xpath.start().span(Id.clazz("admin")).toString(), null),
    DELETE(delete, "admin-table-form:vehiclesTable-adminTableDelete", null, null),
    BATCH_EDIT(batchEdit, "admin-table-form:vehiclesTable-adminTableEdit ", null, null),
    SEARCH_TEXT(search, null, Xpath.start().table(Id.id("grid_nav_search_box")).tbody().tr().td("1").toString(), null),
    SEARCH_TEXT_FIELD(null, "admin-table-form:vehiclesTable-filterTable", null, null),
    SEARCH_BUTTON(search, "admin-table-form:vehiclesTable-adminTableSearch", null, null),
    EDIT_COLUMNS_LINK(editColumns, "admin-table-form:vehiclesTable-adminTableEditColumns", null, null),
    TABLE_HEADERS(null, "admin-table-form:vehiclesTable:***header:sortDiv", null, null),
    TABLE_ENTRIES(null, "admin-table-form:vehiclesTable:###:***", null, null),
    SELECT_ALL(null, "admin-table-form:vehiclesTable:selectAll", null, null),
    SELECT_ROW(null, "admin-table-form:vehiclesTable:###:select", null, null),
    EDIT_USER("edit", "admin-table-form:vehiclesTable:###:edit", null, null),

    ;

    private String text, ID, xpath, xpath_alt, url;

    private AdminVehiclesEnum(String text, String ID, String xpath, String xpath_alt) {
        this.text = text;
        this.ID = ID;
        this.xpath = xpath;
        this.xpath_alt = xpath_alt;
    }

    private AdminVehiclesEnum(String url) {
        this.url = url;
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
