package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum AdminVehiclesEnum implements SeleniumEnums {
    DEFAULT_URL("/app/admin/vehicles"),
    TITLE("Admin - Vehicles", Xpath.start().span(Id.clazz("admin")).toString()),
    DELETE(delete, "admin-table-form:vehiclesTable-adminTableDelete"),
    BATCH_EDIT(batchEdit, "admin-table-form:vehiclesTable-adminTableEdit "),
    SEARCH_TEXT(search, Xpath.start().table(Id.id("grid_nav_search_box")).tbody().tr().td("1").toString()),
    SEARCH_TEXT_FIELD(null, "admin-table-form:vehiclesTable-filterTable"),
    SEARCH_BUTTON(search, "admin-table-form:vehiclesTable-adminTableSearch"),
    EDIT_COLUMNS_LINK(editColumns, "admin-table-form:vehiclesTable-adminTableEditColumns"),
    TABLE_HEADERS(null, "admin-table-form:vehiclesTable:***header:sortDiv"),
    TABLE_ENTRIES(null, "admin-table-form:vehiclesTable:###:***"),
    SELECT_ALL(null, "admin-table-form:vehiclesTable:selectAll"),
    SELECT_ROW(null, "admin-table-form:vehiclesTable:###:select"),
    EDIT_VEHICLE("edit", "admin-table-form:vehiclesTable:###:edit"),

    ;

    private String text, url;
    private String[] IDs;
    
    private AdminVehiclesEnum(String url){
    	this.url = url;
    }
    private AdminVehiclesEnum(String text, String ...IDs){
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
