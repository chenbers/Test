package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum AdminReportsEnum implements SeleniumEnums {
    DEFAULT_URL("/app/admin/reports"),
    TITLE("Admin - Reports", Xpath.start().span(Id.clazz("admin")).toString()),

    DELETE(delete, "admin-table-form:reportScheduleTable-adminTableDelete"),
    BATCH_EDIT(batchEdit, "admin-table-form:reportScheduleTable-adminTableEdit"),

    SEARCH_TEXT(search, Xpath.start().table(Id.id("grid_nav_search_box")).tbody().tr().td("1").toString()),
    SEARCH_TEXT_FIELD(null, "admin-table-form:reportScheduleTable-filterTable"),
    SEARCH_BUTTON(search, "admin-table-form:reportScheduleTable-adminTableSearch"),

    EDIT_COLUMNS_LINK(editColumns, "admin-table-form:reportScheduleTable-adminTableEditColumns"),

    TABLE_HEADERS(null, "admin-table-form:reportScheduleTable:***header:sortDiv"),
    TABLE_ENTRIES(null, "admin-table-form:reportScheduleTable:###:***"),

    SELECT_ALL(null, "admin-table-form:reportScheduleTable:selectAll"),
    SELECT_ROW(null, "admin-table-form:reportScheduleTable:###:select"),

    EDIT_USER("edit", "admin-table-form:reportScheduleTable:###:edit"),

    ;

    private String text, url;
    private String[] IDs;
    
    private AdminReportsEnum(String url){
    	this.url = url;
    }
    private AdminReportsEnum(String text, String ...IDs){
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
