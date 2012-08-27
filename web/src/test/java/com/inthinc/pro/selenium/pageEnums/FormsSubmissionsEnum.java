package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum FormsSubmissionsEnum implements SeleniumEnums {
    DEFAULT_URL(appUrl + "/forms/submissions"),
    TITLE("List Submissions", Xpath.start().span(Id.clazz("submissions")).toString()),

    REFRESH("Refresh", "//button[@class='btn']"),
    
    DATE_SORT("Date/Time", "column-submission-date"),
    GROUP_SORT("Group", "column-submission-group"),
    DRIVER_SORT("Driver", "column-submission-driver"),
    VEHICLE_SORT("Vehicle", "column-submission-vehicle"),
    FORM_SORT("Form", "column-submission-form"),
    EDITED_SORT("Edited", "column-submission-edited"),
    APPROVED_SORT("Status", "column-submission-status"),
    
    FORM_DROPDOWN("Form:", "submissions-form-select"),
    DATE_DROPDOWN("Date:", "//input[@id='daterange']"),
    SPECIFIC_DATE_DROPDOWN(null, "//div[@class='range-start hasDatepicker']"),
    DATE_START_DROPDOWN(null, "//div[@class='range-start hasDatepicker']"),
    DATE_END_DROPDOWN(null, "//div[@class='range-end hasDatepicker']"),
    RECORDS_DROPDOWN(null, "//select[@name='submissions-table_length']"),
    EDITED_DROPDOWN(null, "column-filter-submission-edited"),
    APPROVED_DROPDOWN(null, "column-filter-submission-status"),

    GROUP_FIELD(null, "column-filter-submission-group"),
    DRIVER_FIELD(null, "column-filter-submission-driver"),
    VEHICLE_FIELD(null, "column-filter-submission-vehicle"),
    
    DATE_TIME_TEXT_ENTRY(null, "//tr[###]/td[@class='submissionDate']"),
    GROUP_LINK_ENTRY(null, "//tr[###]/td[@class='groupName']"),
    DRIVER_LINK_ENTRY(null, "//tr[###]/td[@class='driverName']"),
    VEHICLE_LINK_ENTRY(null, "//tr[###]/td[@class='vehicleName']"),
    FORM_TEXT_ENTRY(null, "//tr[###]/td[@class='formName']"),
    EDITED_TEXT_ENTRY(null, "//tr[###]/td[@class='edited']"),
    APPROVED_CHECKBOX_ENTRY(null, "//tr[###]/td[@class='status']"),
    
    //INLINE EDIT ELEMENTS
    TEXT_ENTRY(null, "//tr[###]/td[@class='editable ']"),//need Colleen to add unique ID, I suggest editable text
    NUMERIC_ENTRY(null, "//tr[###]/td[@class='editable ']"),//need Colleen to add unique ID, I suggest editable numeric
    DECIMAL_ENTRY(null, "//tr[###]/td[@class='editable ']"),//need Colleen to add unique ID, I suggest editable decimal
    DATE_ENTRY(null, "//tr[###]/td[@class='editable date']"),
    CHOOSEONE_ENTRY(null, "//tr[###]/td[@class='editable select1']"),
    CHOOSEMANY_ENTRY(null, "//tr[###]/td[@class='editable selectn']"),
    SAVE_BUTTON(null, "//a[@id='saveRow']"),
    CANCEL_BUTTON(null, "//a[@id='cancelRow']"),
    
    PREVIOUS("Previous", "//li[@class='prev']"),
    PAGE_NUMBER(null, "//li[###]/a[@href='#']"),
    NEXT("Next", "//li[@class='next']"),
    
    NO_RECORDS_ERROR("No matching records found", "//td[@class='dataTables_empty']"),
    
    INVALID_TEXT_ERROR("Invalid", "//td[8]/span[@class='label label-important invalid']"),
    INVALID_NUMERIC_ERROR("Invalid", "//td[9]/span[@class='label label-important invalid']"),
    INVALID_DECIMAL_ERROR("Invalid", "//td[10]/span[@class='label label-important invalid']"),
    INVALID_DATE_ERROR("Invalid", "//td[11]/span[@class='label label-important invalid']"),
    
    ENTRIES_TEXT("Showing #### to #### of #### entries", "//div[@id='submissions-table_info']"),
    ;

    private String text, url;
    private String[] IDs;
    
    private FormsSubmissionsEnum(String url){
    	this.url = url;
    }
    private FormsSubmissionsEnum(String text, String ...IDs){
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
