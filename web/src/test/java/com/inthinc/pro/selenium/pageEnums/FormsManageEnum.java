package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum FormsManageEnum implements SeleniumEnums {
    DEFAULT_URL("forms/"),
    TITLE(null, "//span[@class='admin']"),

    //No longer on page SEARCH_HEADER("Search:", "//div[@id='staging-forms-table_filter']"),
    //No longer on page FORMS_HEADER("Forms", "//label[@for='formSelection']"),
    
    //NEW_FORM_BUTTON(null, "//i[@class='icon-plus icon-large']"),
    NEW_FORM_BUTTON(null, "//a[@href='/forms/build'[@class='btn btn-inthinc[@pull-left']"),
    RECORDS_DROPDOWN("records per page", "//select[@name='staging-forms-table_length']"),
    SEARCH_TEXTFIELD(null, "//div[@id='staging-forms-table_filter']/label/input"),
    
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

//    SELECT_ALL_CHECKBOX(null, "//th[@id='column-staging-select']/input[@id='select-all']"),
//    CHECKBOX_ENTRY(null, "//table[@id='staging-forms-table']/tbody/tr[###]/td[1]"),
    
    NAME_ENTRY("Name", "//table[@id='staging-forms-table']/tbody/tr[###]/td[1]"),
    BASE_FORM_ID_ENTRY("Base Form ID", "//table[@id='staging-forms-table']/tbody/tr[###]/td[2]"),
    VERSION_ENTRY("Version", "//table[@id='staging-forms-table']/tbody/tr[###]/td[3]"),
    DESCRIPTION_ENTRY("Description", "//table[@id='staging-forms-table']/tbody/tr[###]/td[4]"),
    STATUS_ENTRY("Status", "//table[@id='staging-forms-table']/tbody/tr[###]/td[5]"),
    TRIGGER_ENTRY("Trigger", "//table[@id='staging-forms-table']/tbody/tr[###]/td[6]"),
    GEAR(null, "//table[@id='staging-forms-table']/tbody/tr[###]/td[7]/div/button"),
    EDIT_ENTRY_LINK("Edit", "//table[@id='staging-forms-table']/tbody/tr[###]/td[7]/div/ul/li[1]"),
    PUBLISH_ENTRY_LINK(null,"//table[@id='staging-forms-table']/tbody/tr[###]/td[7]/div/ul/li[2]"),
    COPY_ENTRY_LINK("Copy", "//table[@id='staging-forms-table']/tbody/tr[###]/td[7]/div/ul/li[3]"),
    PUBLISH_DISABLED_LINK(null,"//table[@id='staging-forms-table']/tbody/tr[###]/td[7]/div/ul/li[@class='disabled']/a[@class='publish']"),

    NO_RECORDS_FOUND_ERROR("No matching records found", "//table[@id='staging-forms-table']/tbody/tr/td[@class='dataTables_empty']"),
    SUCCESS_ALERT(null,"//div[@id='#success-alert']"),
    
    ENTRIES_TEXT(null, "//div[@id='staging-forms-table_info']"),
    
    PREVIOUS("Previous", "//li[@class='prev']"), 
    PAGE_NUMBER(null, "//form/div/div[2]div[2]/div/ul/li[###]/a"),
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
