package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum FormsManageEnum implements SeleniumEnums {
    DEFAULT_URL(appUrl + "/forms/forms"),
    TITLE("Forms", "//span[@class='admin']"),

    //No longer on page SEARCH_HEADER("Search:", "//div[@id='staging-forms-table_filter']"),
    //No longer on page FORMS_HEADER("Forms", "//label[@for='formSelection']"),
    
    NEW_FORM_BUTTON(null, "//i[@class='icon-plus icon-large']"),
    
    RECORDS_DROPDOWN("records per page", "//select[@name='staging-forms-table_length']"),
    SEARCH_TEXTFIELD("Search:", "//div[@id='staging-forms-table_filter']/label/input"),
    
    //No longer on page SELECT_MANAGE_LINK("Select", "//th[@id='column-working-select']"),
    NAME_LINK("Name", "//th[@id='column-staging-name']"),  
    BASE_FORM_ID_LINK("Base Form ID", "//th[@id='column-staging-baseFormID']"),
    VERSION_LINK("Version", "//th[@id='column-staging-version']"),
    DESCRIPTION_LINK("Description", "//th[@id='column-staging-description']"),
    STATUS_LINK("Status", "//th[@id='column-status']"),
    TRIGGER_LINK("Trigger", "//th[@id='column-staging-trigger']"),
    // IS CURRENTLY NO LONGER PRESENT
    //    PUBLISH_LINK("Publish", "//th[@id='column-staging-publish']"),
    EDIT_LINK("Edit", "//th[@id='column-staging-edit']"),

    SELECT_ALL_CHECKBOX(null, "//th[@id='column-staging-select']/input[@id='select-all']"),
    CHECKBOX_ENTRY(null, "//table[@id='staging-forms-table']/tbody/tr[###]/td[1]"),
    
    NAME_MANAGE_ENTRY("Name", "//table[@id='staging-forms-table']/tbody/tr[###]/td[1]"),
    BASE_FORM_ID_MANAGE_ENTRY("Base Form ID", "//table[@id='staging-forms-table']/tbody/tr[###]/td[2]"),
    VERSION_MANAGE_ENTRY("Version", "//table[@id='staging-forms-table']/tbody/tr[###]/td[3]"),
    DESCRIPTION_MANAGE_ENTRY("Description", "//table[@id='staging-forms-table']/tbody/tr[###]/td[4]"),
    STATUS_MANAGE_ENTRY("Status", "//table[@id='staging-forms-table']/tbody/tr[###]/td[5]"),
    TRIGGER_MANAGE_ENTRY("Trigger", "//table[@id='staging-forms-table']/tbody/tr[###]/td[6]"),
    GEAR(null, "//table[@id='staging-forms-table']/tbody/tr[###]/td[7]/div/button"),
    EDIT_ENTRY_LINK("Edit", "//table[@id='staging-forms-table']/tbody/tr[1]/td[7]/div/ul/li[###]"),
    PUBLISH_ENTRY_LINK("Publish", "//a[contains(@id,'publish_')]"),
    COPY_ENTRY_LINK("Copy", "//a[contains(@id,'copy_')]"),

    NO_RECORDS_FOUND_MANAGE_ERROR("No matching records found", "//table[@id='staging-forms-table']/tbody/tr/td[@class='dataTables_empty']"),
    
    ENTRIES_MANAGE_TEXT("Showing ### to ### of ### entries", "//div[@id='staging-forms-table_info']"),
    
    PREVIOUS_MANAGE("Previous", "//li[@class='prev']"), 
    PAGE_NUMBER_MANAGE(null, "//li[###]/a[@href='#']"),
    NEXT_MANAGE("Next", "//li[@class='next']"),
    
    ;

    private String text, url;
    private String[] IDs;
    
    private FormsManageEnum(String url){
    	this.url = url;
    }
    private FormsManageEnum(String text, String ...IDs){
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
