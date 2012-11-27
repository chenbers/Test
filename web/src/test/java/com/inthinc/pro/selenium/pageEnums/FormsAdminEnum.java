package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum FormsAdminEnum implements SeleniumEnums {
    DEFAULT_URL(appUrl + "/forms"),
    TITLE("Forms", "//span[@class='admin']"),

    //No longer on page SEARCH_HEADER("Search:", "//div[@id='staging-forms-table_filter']"),
    //No longer on page FORMS_HEADER("Forms", "//label[@for='formSelection']"),
    
    NEW_FORM_LINK("Create Form", "//a[@class='btn btn-inthinc pull-left']"),
    
    MANAGE_TAB("Working", "//div[2]/div[1]/div/ul/li[1]/a"),
    PUBLISHED_TAB("Published", "//div[2]/div[1]/div/ul/li[2]/a"),
    SUBMISSIONS_TAB("Published", "//div[2]/div[1]/div/ul/li[3]/a"),
    CUSTOMERS_TAB("Published", "//div[2]/div[1]/div/ul/li[4]/a"),
    
    RECORDS_MANAGE_DROPDOWN("records per page", "//select[@name='staging-forms-table_length']"),
    RECORDS_PUBLISHED_DROPDOWN("records per page", "//select[@name='published-forms-table_length']"),
    SEARCH_MANAGE_TEXTFIELD("Search:", "//div[@id='staging-forms-table_filter']/label/input"),
    SEARCH_PUBLISHED_TEXTFIELD("Search:", "//div[@id='published-forms-table_filter']/label/input"),
    
    //No longer on page SELECT_MANAGE_LINK("Select", "//th[@id='column-working-select']"),
    //No longer on page SELECT_PUBLISHED_LINK("Select", "//th[@id='column-published-select']"),
    NAME_MANAGE_LINK("Name", "//th[@id='column-staging-name']"),
    NAME_PUBLISHED_LINK("Name", "//th[@id='column-published-name']"),    
    BASE_FORM_ID_MANAGE_LINK("Base Form ID", "//th[@id='column-staging-baseFormID']"),
    BASE_FORM_ID_PUBLISHED_LINK("Base Form ID", "//th[@id='column-published-baseFormID']"),
    VERSION_MANAGE_LINK("Version", "//th[@id='column-staging-version']"),
    VERSION_PUBLISHED_LINK("Version", "//th[@id='column-published-version']"),
    DESCRIPTION_MANAGE_LINK("Description", "//th[@id='column-staging-description']"),
    DESCRIPTION_PUBLISHED_LINK("Description", "//th[@id='column-published-description']"),
    STATUS_MANAGE_LINK("Status", "//th[@id='column-status']"),
    TRIGGER_MANAGE_LINK("Trigger", "//th[@id='column-staging-trigger']"),
    TRIGGER_PUBLISHED_LINK("Trigger", "//th[@id='column-published-trigger']"),
    PUBLISH_LINK("Publish", "//th[@id='column-staging-publish']"),
    EDIT_LINK("Edit", "//th[@id='column-staging-edit']"),

    SELECT_ALL_MANAGE_CHECKBOX(null, "//th[@id='column-staging-select']/input[@id='select-all']"),
    SELECT_ALL_PUBLISHED_CHECKBOX(null, "//th[@id='column-published-select']/input[@id='select-all']"),
    MANAGE_CHECKBOX_ENTRY(null, "//table[@id='staging-forms-table']/tbody/tr[###]/td[1]"),
    PUBLISHED_CHECKBOX_ENTRY(null, "//table[@id='published-forms-table']/tbody/tr[###]/td[1]"),
    
    NAME_MANAGE_ENTRY("Name", "//table[@id='staging-forms-table']/tbody/tr[###]/td[1]"),
    NAME_PUBLISHED_ENTRY("Name", "//table[@id='published-forms-table']/tbody/tr[###]/td[1]"),
    BASE_FORM_ID_MANAGE_ENTRY("Base Form ID", "//table[@id='staging-forms-table']/tbody/tr[###]/td[2]"),
    BASE_FORM_ID_PUBLISHED_ENTRY("Base Form ID", "//table[@id='published-forms-table']/tbody/tr[###]/td[2]"),
    VERSION_MANAGE_ENTRY("Version", "//table[@id='staging-forms-table']/tbody/tr[###]/td[3]"),
    VERSION_PUBLISHED_ENTRY("Version", "//table[@id='published-forms-table']/tbody/tr[###]/td[3]"),
    DESCRIPTION_MANAGE_ENTRY("Description", "//table[@id='staging-forms-table']/tbody/tr[###]/td[4]"),
    DESCRIPTION_PUBLISHED_ENTRY("Description", "//table[@id='published-forms-table']/tbody/tr[###]/td[4]"),
    STATUS_MANAGE_ENTRY("Status", "//table[@id='staging-forms-table']/tbody/tr[###]/td[5]"),
    TRIGGER_MANAGE_ENTRY("Trigger", "//table[@id='staging-forms-table']/tbody/tr[###]/td[6]"),
    TRIGGER_PUBLISHED_ENTRY("Trigger", "//table[@id='published-forms-table']/tbody/tr[###]/td[6]"),
    PUBLISH_ENTRY_LINK("Publish", "//table[@id='staging-forms-table']/tbody/tr[###]/td[7]"),
    EDIT_ENTRY_LINK("Edit", "//table[@id='staging-forms-table']/tbody/tr[###]/td[8]"),
    
    NO_RECORDS_FOUND_MANAGE_ERROR("No matching records found", "//table[@id='staging-forms-table']/tbody/tr/td[@class='dataTables_empty']"),
    NO_RECORDS_FOUND_PUBLISHED_ERROR("No matching records found", "//table[@id='published-forms-table']/tbody/tr/td[@class='dataTables_empty']"),
    
    ENTRIES_MANAGE_TEXT("Showing ### to ### of ### entries", "//div[@id='staging-forms-table_info']"),
    ENTRIES_PUBLISHED_TEXT("Showing ### to ### of ### entries", "//div[@id='published-forms-table_info']"),
    
    //need unique id's for the two different tables on this page
    PREVIOUS_MANAGE("Previous", "//li[@class='prev']"), 
    PREVIOUS_PUBLISHED("Previous", "//li[@class='prev']"), 
    PAGE_NUMBER_MANAGE(null, "//li[###]/a[@href='#']"),
    PAGE_NUMBER_PUBLISHED(null, "//li[###]/a[@href='#']"),
    NEXT_MANAGE("Next", "//li[@class='next']"),
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
