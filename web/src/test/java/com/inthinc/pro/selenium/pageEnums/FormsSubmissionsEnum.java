package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum FormsSubmissionsEnum implements SeleniumEnums {
    DEFAULT_URL(appUrl + "/forms/submissions"),
    TITLE("List Submissions", ""),

    SAVE("Save", ""),
    APPROVE("Approve", ""),

    DATE_SORT("Date/Time", "column-submission-date"),
    GROUP_SORT("Group", "column-submission-group"),
    DRIVER_SORT("Driver", "column-submission-driver"),
    VEHICLE_SORT("Vehicle", "column-submission-vehicle"),
    FORM_SORT("Form", "column-submission-form"),
    EDITED_SORT("Edited", "column-submission-edited"),
    STATUS_SORT("Status", "column-submission-status"),
    
    EDITED_DROPDOWN(null, "column-filter-submission-edited"),
    STATUS_DROPDOWN(null, "column-filter-submission-status"),

    DATE_START_FIELD(null, "tablerange_from_1"),
    DATE_END_FIELD(null, "tablerange_to_1"),
    GROUP_FIELD(null, "column-filter-submission-group"),
    DRIVER_FIELD(null, "column-filter-submission-driver"),
    VEHICLE_FIELD(null, "column-filter-submission-vehicle"),
    FORM_FIELD(null, "column-filter-submission-form"),
    //all these need class names
    DATE_ENTRY(null, "//tr[###]/td[@class='']"),
    GROUP_ENTRY(null, ""),
    DRIVER_ENTRY(null, ""),
    VEHICLE_ENTRY(null, ""),
    FORM_ENTRY(null, ""),
    EDITED_ENTRY(null, ""),
    STATUS_ENTRY(null, ""),
    
    PREVIOUS("Previous", "submissions-table_previous"),
    NEXT("Next", "submissions-table_next"),
    
    ENTRIES_TEXT("Showing ### to ### of ### entries", "submissions-table_info"),
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
