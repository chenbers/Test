package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum AdminUsersEnum implements SeleniumEnums {

    TITLE("Admin - Users", null, Xpath.start().span(Id.clazz("admin")).toString(), null),

    DELETE(delete, "admin-table-form:personTable-adminTableDelete", null, null),
    BATCH_EDIT(batchEdit, "admin-table-form:personTable-adminTableEdit", null, null),

    SEARCH_TEXT(search, null, Xpath.start().table(Id.id("grid_nav_search_box")).tbody().tr().td("1").toString(), null),
    SEARCH_TEXT_FIELD(null, "admin-table-form:personTable-filterTable", null, null),
    SEARCH_BUTTON(search, "admin-table-form:personTable-adminTableSearch", null, null),

    EDIT_COLUMNS_LINK(editColumns, "admin-table-form:personTable-adminTableEditColumns", null, null),

    TABLE_HEADERS(null, "admin-table-form:personTable:***header:sortDiv", null, null),
    TABLE_ENTRIES(null, "admin-table-form:personTable:###:***", null, null),

    SELECT_ALL(null, "admin-table-form:personTable:selectAll", null, null),
    SELECT_ROW(null, "admin-table-form:personTable:###:select", null, null),

    EDIT_USER("edit", "admin-table-form:personTable:###:edit", null, null),

    ;

    private String text, ID, xpath, xpath_alt, url;

    private AdminUsersEnum(String text, String ID, String xpath, String xpath_alt) {
        this.text = text;
        this.ID = ID;
        this.xpath = xpath;
        this.xpath_alt = xpath_alt;
    }

    private AdminUsersEnum(String url) {
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
