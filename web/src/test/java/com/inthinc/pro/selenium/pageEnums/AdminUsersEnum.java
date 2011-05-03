package com.inthinc.pro.selenium.pageEnums;

import java.util.List;

import com.inthinc.pro.automation.enums.SeleniumEnum;
import com.inthinc.pro.automation.enums.SeleniumEnum.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum AdminUsersEnum implements SeleniumEnums {
    
    TITLE("Admin - Users", Xpath.start().span(Id.clazz("admin"))),
    
    DELETE(delete, "admin-table-form:personTable-adminTableDelete" ),
    BATCH_EDIT(batchEdit, "admin-table-form:personTable-adminTableEdit"),
    
    SEARCH_TEXT(search, Xpath.start().table(Id.id("grid_nav_search_box")).tbody().tr().td("1")),
    SEARCH_TEXT_FIELD(null, "admin-table-form:personTable-filterTable"),
    SEARCH_BUTTON(search, "admin-table-form:personTable-adminTableSearch"),
    
    EDIT_COLUMNS_LINK(editColumns, "admin-table-form:personTable-adminTableEditColumns"),
    
    TABLE_HEADERS(null, "admin-table-form:personTable:***header:sortDiv"),
    TABLE_ENTRIES(null, "admin-table-form:personTable:0:***"),
    
    SELECT_ALL(null, "admin-table-form:personTable:selectAll"),
    SELECT_ROW(null, "admin-table-form:personTable:0:select"),

    EDIT_USER("edit", "admin-table-form:personTable:0:edit"),
    
    
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

    private AdminUsersEnum(String text, String ID) {
        this(text, ID, "", null);
    }

    private AdminUsersEnum(String text, String ID, String xpath) {
        this(text, ID, xpath, null);
    }

    private AdminUsersEnum(String text, String ID, Xpath xpath, Xpath xpath_alt) {
        this(text, ID, xpath.toString(), xpath_alt.toString());
    }

    private AdminUsersEnum(String text, String ID, Xpath xpath) {
        this(text, ID, xpath.toString(), null);
    }

    private AdminUsersEnum(String text, Xpath xpath) {
        this(text, null, xpath.toString(), null);
    }

    private AdminUsersEnum(Xpath xpath, Xpath xpath_alt) {
        this(null, null, xpath.toString(), xpath_alt.toString());
    }

    private AdminUsersEnum(Xpath xpath) {
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
    @Override
    public List<String> getLocators() {        
        return SeleniumEnum.locators(this);
    }
    
    @Override
    public AdminUsersEnum replaceNumber(String number) {
        ID = ID.replace("###", number);
        xpath = xpath.replace("###", number);
        xpath_alt = xpath_alt.replace("###", number);
        return this;
    }

    @Override
    public AdminUsersEnum replaceWord(String word) {
        ID = ID.replace("***", word);
        xpath = xpath.replace("***", word);
        xpath_alt = xpath_alt.replace("***", word);
        return this;
    }
}
