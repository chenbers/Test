package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum FormsAdminEnum implements SeleniumEnums {
    DEFAULT_URL(appUrl + "/forms"),
    TITLE("Forms", Xpath.start().span(Id.clazz("forms")).toString()),

    SEARCH_HEADER("Search:", "//div[@id='working-forms-table_filter']"),
    FORMS_HEADER("Forms", "//span[@class='admin']"),
    
    CREATE_FORM_TOP_LINK("Create Form", "//a[@id='addForm-top']"),
    CREATE_FORM_BOTTOM_LINK("Create Form", "//a[@id='addForm-bottom']"),
    
    WORKING_TAB("Working", "//a[@href='#working']"),
    PUBLISHED_TAB("Published", "//a[@href='#published']"),
    
    RECORDS_WORKING_DROPDOWN("records per page", "//select[@name='working-forms-table_length']"),
    RECORDS_PUBLISHED_DROPDOWN("records per page", "//select[@name='published-forms-table_length']"),
    SEARCH_WORKING_TEXTFIELD("Search:", "//div[@id='working-forms-table_filter']/label/input"),
    SEARCH_PUBLISHED_TEXTFIELD("Search:", "//div[@id='published-forms-table_filter']/label/input"),
    
    SELECT_WORKING_LINK("Select", "//th[@id='column-working-select']"),
    SELECT_PUBLISHED_LINK("Select", "//th[@id='column-published-select']"),
    NAME_WORKING_LINK("Name", "//th[@id='column-working-name']"),
    NAME_PUBLISHED_LINK("Name", "//th[@id='column-published-name']"),    
    BASE_FORM_ID_WORKING_LINK("Base Form ID", "//th[@id='column-working-baseFormID']"),
    BASE_FORM_ID_PUBLISHED_LINK("Base Form ID", "//th[@id='column-published-baseFormID']"),
    VERSION_WORKING_LINK("Version", "//th[@id='column-working-version']"),
    VERSION_PUBLISHED_LINK("Version", "//th[@id='column-published-version']"),
    DESCRIPTION_WORKING_LINK("Description", "//th[@id='column-working-description']"),
    DESCRIPTION_PUBLISHED_LINK("Description", "//th[@id='column-published-description']"),
    TRIGGER_WORKING_LINK("Trigger", "//th[@id='column-working-trigger']"),
    TRIGGER_PUBLISHED_LINK("Trigger", "//th[@id='column-published-trigger']"),
    PUBLISH_LINK("Publish", "//th[@id='column-working-publish']"),
    EDIT_LINK("Edit", "//th[@id='column-working-edit']"),

    SELECT_ALL_WORKING_CHECKBOX(null, "//th[@id='column-working-select']/input[@id='select-all']"),
    SELECT_ALL_PUBLISHED_CHECKBOX(null, "//th[@id='column-published-select']/input[@id='select-all']"),
    WORKING_CHECKBOX_ENTRY(null, "//table[@id='working-forms-table']/tbody/tr[###]/td[1]"),
    PUBLISHED_CHECKBOX_ENTRY(null, "//table[@id='published-forms-table']/tbody/tr[###]/td[1]"),
    
    NAME_WORKING_ENTRY("Name", "//table[@id='working-forms-table']/tbody/tr[###]/td[2]"),
    NAME_PUBLISHED_ENTRY("Name", "//table[@id='published-forms-table']/tbody/tr[###]/td[2]"),
    BASE_FORM_ID_WORKING_ENTRY("Base Form ID", "//table[@id='working-forms-table']/tbody/tr[###]/td[3]"),
    BASE_FORM_ID_PUBLISHED_ENTRY("Base Form ID", "//table[@id='published-forms-table']/tbody/tr[###]/td[3]"),
    VERSION_WORKING_ENTRY("Version", "//table[@id='working-forms-table']/tbody/tr[###]/td[4]"),
    VERSION_PUBLISHED_ENTRY("Version", "//table[@id='published-forms-table']/tbody/tr[###]/td[4]"),
    DESCRIPTION_WORKING_ENTRY("Description", "//table[@id='working-forms-table']/tbody/tr[###]/td[5]"),
    DESCRIPTION_PUBLISHED_ENTRY("Description", "//table[@id='published-forms-table']/tbody/tr[###]/td[5]"),
    TRIGGER_WORKING_ENTRY("Trigger", "//table[@id='working-forms-table']/tbody/tr[###]/td[6]"),
    TRIGGER_PUBLISHED_ENTRY("Trigger", "//table[@id='published-forms-table']/tbody/tr[###]/td[6]"),
    PUBLISH_ENTRY_LINK("Publish", "//table[@id='working-forms-table']/tbody/tr[###]/td[7]"),
    EDIT_ENTRY_LINK("Edit", "//table[@id='working-forms-table']/tbody/tr[###]/td[8]"),
    
    NO_RECORDS_FOUND_WORKING_ERROR("No matching records found", "//table[@id='working-forms-table']/tbody/tr/td[@class='dataTables_empty']"),
    NO_RECORDS_FOUND_PUBLISHED_ERROR("No matching records found", "//table[@id='published-forms-table']/tbody/tr/td[@class='dataTables_empty']"),
    
    ENTRIES_WORKING_TEXT("Showing ### to ### of ### entries", "//div[@id='working-forms-table_info']"),
    ENTRIES_PUBLISHED_TEXT("Showing ### to ### of ### entries", "//div[@id='published-forms-table_info']"),
    
    //need unique id's for the two different tables on this page
    PREVIOUS_WORKING("Previous", "//li[@class='prev']"), 
    PREVIOUS_PUBLISHED("Previous", "//li[@class='prev']"), 
    PAGE_NUMBER_WORKING(null, "//li[###]/a[@href='#']"),
    PAGE_NUMBER_PUBLISHED(null, "//li[###]/a[@href='#']"),
    NEXT_WORKING("Next", "//li[@class='next']"),
    NEXT_PUBLISHED("Next", "//li[@class='next']"),
    
    ;

    private String text, url;
    private String[] IDs;
    
    private FormsAdminEnum(String url){
    	this.url = url;
    }
    private FormsAdminEnum(String text, String ...IDs){
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
