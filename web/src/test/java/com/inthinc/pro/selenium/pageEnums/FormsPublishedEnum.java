package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum FormsPublishedEnum implements SeleniumEnums {
    DEFAULT_URL(appUrl + "/forms/published"),
    TITLE("Forms", "//span[@class='admin']"),

    //No longer on page SEARCH_HEADER("Search:", "//div[@id='staging-forms-table_filter']"),
    //No longer on page FORMS_HEADER("Forms", "//label[@for='formSelection']"),

    RECORDS_DROPDOWN("records per page", "//select[@name='published-forms-table_length']"),
    SEARCH_TEXTFIELD("Search:", "//div[@id='published-forms-table_filter']/label/input"),
    
    //No longer on page SELECT_PUBLISHED_LINK("Select", "//th[@id='column-published-select']"),
    NAME_LINK("Name", "//th[@id='column-published-name']"),    
    BASE_FORM_ID_LINK("Base Form ID", "//th[@id='column-published-baseFormID']"),
    VERSION_LINK("Version", "//th[@id='column-published-version']"),
    DESCRIPTION_LINK("Description", "//th[@id='column-published-description']"),
    TRIGGER_LINK("Trigger", "//th[@id='column-published-trigger']"),

// NOT CURRENTLY ON PAGE:    
//    SELECT_ALL_CHECKBOX(null, "//th[@id='column-published-select']/input[@id='select-all']"),
//    CHECKBOX_ENTRY(null, "//table[@id='published-forms-table']/tbody/tr[###]/td[1]"),
    
    NAME_ENTRY("Name", "//table[@id='published-forms-table']/tbody/tr[###]/td[1]"),
    BASE_FORM_ID_ENTRY("Base Form ID", "//table[@id='published-forms-table']/tbody/tr[###]/td[2]"),
    VERSION_ENTRY("Version", "//table[@id='published-forms-table']/tbody/tr[###]/td[3]"),
    DESCRIPTION_ENTRY("Description", "//table[@id='published-forms-table']/tbody/tr[###]/td[4]"),
    TRIGGER_ENTRY("Trigger", "//table[@id='published-forms-table']/tbody/tr[###]/td[5]"),

    NO_RECORDS_FOUND_ERROR("No matching records found", "//table[@id='published-forms-table']/tbody/tr/td[@class='dataTables_empty']"),

    ENTRIES_TEXT("Showing ### to ### of ### entries", "//div[@id='published-forms-table_info']"),
    
    PREVIOUS("Previous", "//li[@class='prev']"), 
    PAGE_NUMBER(null, "//li[###]/a[@href='#']"),
    NEXT("Next", "//li[@class='next']"),
    
    ;

    private String text, url;
    private String[] IDs;
    
    private FormsPublishedEnum(String url){
    	this.url = url;
    }
    private FormsPublishedEnum(String text, String ...IDs){
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
