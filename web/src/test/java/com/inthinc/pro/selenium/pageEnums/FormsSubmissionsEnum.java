package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum FormsSubmissionsEnum implements SeleniumEnums {
    DEFAULT_URL("forms/submissions"),
//No longer on page    TITLE("List Submissions", "//span[@class='admin']"),

    DATE_HEADER("Search:", "//label[@for='dateSelection']"),
    FORM_HEADER("Forms", "//label[@for='formSelection']"),

    REFRESH("Refresh", "//button[@class='btn']"),
    
    DATE_SORT("Date/Time", "column-submission-date"),
    GROUP_SORT("Group", "column-submission-group"),
    DRIVER_SORT("Driver", "column-submission-driver"),
    VEHICLE_SORT("Vehicle", "column-submission-vehicle"),
    FORM_SORT("Form", "column-submission-form"),
    EDITED_SORT("Edited", "column-submission-edited"),
    APPROVED_SORT("Status", "column-submission-status"),
    
    FORM_DROPDOWN("Form:", "submissions-form-select"),
    DATE_DROPDOWN("Date:", "daterange"),
    RECORDS_DROPDOWN(null, "//select[@name='submissions-table_length']"),
    EDITED_DROPDOWN(null, "//th[@id='column-filter-submission-edited']/span/select"),  
    APPROVED_DROPDOWN(null, "//th[@id='column-filter-submission-status']/span/select"),

    GROUP_FIELD(null, "//th[3]/span/input"),
    DRIVER_FIELD(null, "//th[4]/span/input"),
    VEHICLE_FIELD(null, "//th[5]/span/input"),
        
    LOCATION_ENTRY(null, "//tr[###]/td[contains(@class,'location')]"),
    DATE_TIME_TEXT_ENTRY(null, "//tr[###]/td[contains(@class,'submissionDate sorting_1')]"),
    GROUP_LINK_ENTRY(null, "//tr[###]/td[contains(@class,'groupName')]"),
    DRIVER_LINK_ENTRY(null, "//tr[###]/td[contains(@class,'driverName')]"),
    VEHICLE_LINK_ENTRY(null, "//tr[###]/td[contains(@class,'vehicleName')]"),
    FORM_TEXT_ENTRY(null, "//tr[###]/td[contains(@class,'formName')]"),
    EDITED_TEXT_ENTRY(null, "//tr[###]/td[contains(@class,'edited')]"),
    APPROVED_CHECKBOX_ENTRY(null, "//tr[###]/td[contains(@class,'status')]/input[@type='checkbox']"),
    EDIT_ROW_BUTTON(null, "//tr[###]/td[contains(@class,'trigger')]"),

    //INLINE EDIT ELEMENTS
    TEXT_ENTRY(null, "//tr[###]/td[@class='editable string']"),
    TEXT_TEXTFIELD_ENTRY(null, "//td[@class='editable string']/form/input[@name='value']"),
    NUMERIC_ENTRY(null, "//tr[###]/td[@class='editable integer']"),
    NUMERIC_TEXTFIELD_ENTRY(null, "//td[@class='editable integer']/form/input[@name='value']"),
    DECIMAL_ENTRY(null, "//tr[###]/td[@class='editable decimal']"),
    DECIMAL_TEXTFIELD_ENTRY(null, "//td[@class='editable decimal']/form/input[@name='value']"),
    DATE_ENTRY(null, "//tr[###]/td[@class='editable date']"),
    DATE_DROPDOWN_ENTRY(null, "//td[@class='editable date']/form/input[@name='value']"),
    CHOOSEONE_ENTRY(null, "//tr[###]/td[@class='editable select1']"),
    CHOOSEONE_DROPDOWN_ENTRY(null, "//td[@class='editable select1']/form/select[@name='value']"),
    CHOOSEMANY_ENTRY(null, "//tr[###]/td[@class='editable selectn']"),
    CHOOSEMANY_CHECKBOX_ENTRY(null, "//td[@class='editable selectn']/form/div/input[###]"),
    SAVE_BUTTON(null, "//a[@id='saveRow']"),
    CANCEL_BUTTON(null, "//a[@id='cancelRow']"),
    
    PREVIOUS("Previous", "//li[@class='prev']"),
    PAGE_NUMBER(null, "//li[###]/a[@href='#']"),
    NEXT("Next", "//li[@class='next']"),
    
    NO_RECORDS_ERROR("No matching records found", "//td[@class='dataTables_empty']"),
    
    INVALID_TEXT_ERROR("Invalid", "//td[@class='editable string']/div[@class='label label-important']"),
    INVALID_NUMERIC_ERROR("Invalid", "//td[@class='editable integer']/div[@class='label label-important']"),
    INVALID_DECIMAL_ERROR("Invalid", "//td[@class='editable decimal']/div[@class='label label-important']"),
    INVALID_DATE_ERROR("Invalid", "//td[@class='editable date']/div[@class='label label-important']"),
    
    ENTRIES_TEXT("Showing #### to #### of #### entries", "//div[@id='submissions-table_info']"),
    
    YESTERDAY("Yesterday", "//li[@class='ui-daterangepicker-Yesterday ui-corner-all']")
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
