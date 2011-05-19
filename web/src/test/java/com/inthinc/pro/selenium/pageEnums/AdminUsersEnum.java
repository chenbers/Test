package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum AdminUsersEnum implements SeleniumEnums {
    DEFAULT_URL("/app/admin/people"),
    TITLE("Admin - Users", Xpath.start().span(Id.clazz("admin")).toString()),

    DELETE(delete, "admin-table-form:personTable-adminTableDelete"),
    BATCH_EDIT(batchEdit, "admin-table-form:personTable-adminTableEdit"),

    SEARCH_TEXT(search, Xpath.start().table(Id.id("grid_nav_search_box")).tbody().tr().td("1").toString()),
    SEARCH_TEXT_FIELD(null, "admin-table-form:personTable-filterTable"),
    SEARCH_BUTTON(search, "admin-table-form:personTable-adminTableSearch"),

    EDIT_COLUMNS_LINK(editColumns, "admin-table-form:personTable-adminTableEditColumns"),

    TABLE_HEADERS(null, "admin-table-form:personTable:***header:sortDiv"),
    TABLE_ENTRIES(null, "admin-table-form:personTable:###:***"),

    SELECT_ALL(null, "admin-table-form:personTable:selectAll"),
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
