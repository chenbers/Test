package com.inthinc.pro.selenium.pageEnums;

import java.util.List;

import com.inthinc.pro.automation.enums.SeleniumEnum;
import com.inthinc.pro.automation.enums.SeleniumEnum.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum AdminVehiclesEnum implements SeleniumEnums {
    
    TITLE("Admin - Vehicles", Xpath.start().span(Id.clazz("admin"))),                                   
    
    DELETE(delete, "admin-table-form:vehiclesTable-adminTableDelete" ),                             
                   
    BATCH_EDIT(batchEdit, "admin-table-form:vehiclesTable-adminTableEdit "),                            
                           
    SEARCH_TEXT(search, Xpath.start().table(Id.id("grid_nav_search_box")).tbody().tr().td("1")),      
    
    SEARCH_TEXT_FIELD(null, "admin-table-form:vehiclesTable-filterTable"),                         
                             
    SEARCH_BUTTON(search, "admin-table-form:vehiclesTable-adminTableSearch"),                       
                           
    EDIT_COLUMNS_LINK(editColumns, "admin-table-form:vehiclesTable-adminTableEditColumns"),         
                                    
    TABLE_HEADERS(null, "admin-table-form:vehiclesTable:***header:sortDiv"),                        
                         
    TABLE_ENTRIES(null, "admin-table-form:vehiclesTable:0:***"),                                    
    
    SELECT_ALL(null, "admin-table-form:vehiclesTable:selectAll"),                                  
    
    SELECT_ROW(null, "admin-table-form:vehiclesTable:0:select"),                                    

    EDIT_USER("edit", "admin-table-form:vehiclesTable:0:edit"),                                    
    
    
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

    private AdminVehiclesEnum(String text, String ID) {
        this(text, ID, "", null);
    }

    private AdminVehiclesEnum(String text, String ID, String xpath) {
        this(text, ID, xpath, null);
    }

    private AdminVehiclesEnum(String text, String ID, Xpath xpath, Xpath xpath_alt) {
        this(text, ID, xpath.toString(), xpath_alt.toString());
    }

    private AdminVehiclesEnum(String text, String ID, Xpath xpath) {
        this(text, ID, xpath.toString(), null);
    }

    private AdminVehiclesEnum(String text, Xpath xpath) {
        this(text, null, xpath.toString(), null);
    }

    private AdminVehiclesEnum(Xpath xpath, Xpath xpath_alt) {
        this(null, null, xpath.toString(), xpath_alt.toString());
    }

    private AdminVehiclesEnum(Xpath xpath) {
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
    public AdminVehiclesEnum replaceNumber(String number) {
        ID = ID.replace("###", number);
        xpath = xpath.replace("###", number);
        xpath_alt = xpath_alt.replace("###", number);
        return this;
    }

    @Override
    public AdminVehiclesEnum replaceWord(String word) {
        ID = ID.replace("***", word);
        xpath = xpath.replace("***", word);
        xpath_alt = xpath_alt.replace("***", word);
        return this;
    }
}
