package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum AdminRoadHazardsEnum implements SeleniumEnums {
    
    DEFAULT_URL(appUrl + "/admin/hazards"),

    TITLE("Admin - Zones", "//span[@class='zones']"),

    ADD_HAZARD_BUTTON("Add Hazard", "hazards-form:hazardsAdd"),
    
    SORT_BY_REGION_LINK("Region", "hazards-form:hazards-table:dateheader:sortDiv"),
    SORT_BY_LOCATION_LINK("Location", "hazards-form:hazards-table:locationheader:sortDiv"),
    SORT_BY_DESCRIPTION_LINK("Description", "hazards-form:hazards-table:descriptionheader:sortDiv"),
    SORT_BY_DRIVER_LINK("Driver", "hazards-form:hazards-table:driverNameheader:sortDiv"),
    SORT_BY_USER_LINK("User", "hazards-form:hazards-table:userheader:sortDiv"),
    SORT_BY_TYPE_LINK("Type", "hazards-form:hazards-table:typeheader:sortDiv"),
    SORT_BY_STATUS_LINK("Status", "hazards-form:hazards-table:statusheader:sortDiv"),
    SORT_BY_START_TIME_LINK("Start Time", "hazards-form:hazards-table:startTimeheader:sortDiv"),
    SORT_BY_EXP_TIME_LINK("Exp Time", "hazards-form:hazards-table:endTimeheader:sortDiv"),
    
    ENTRY_MAP_BUTTON(null, "hazards-form:hazards-table:###:mapIcon"),
    ENTRY_REGION_TEXT(null, "hazards-form:hazards-table:###:date"),
    ENTRY_LOCATION_TEXT(null, "hazards-form:hazards-table:###:location"),
    ENTRY_DESCRIPTION_TEXT(null, "hazards-form:hazards-table:###:description"),
    ENTRY_DRIVER_LINK(null, "hazards-form:hazards-table:###:driverName"),
    ENTRY_USER_TEXT(null, "hazards-form:hazards-table:###:user"),
    ENTRY_TYPE_TEXT(null, "hazards-form:hazards-table:###:type"),
    ENTRY_STATUS_TEXT(null, "hazards-form:hazards-table:###:status"),
    ENTRY_STARTING_TIME_TEXT(null, "hazards-form:hazards-table:###:startTime"),
    ENTRY_EXP_TIME_TEXT(null, "hazards-form:hazards-table:###:endTime"),
    ENTRY_EDIT_LINK("edit", "hazards-form:hazards-table:###:hazardsEdit")
    
    ;
    
    private String text, url;
    private String[] IDs;
    
    private AdminRoadHazardsEnum(String url){
        this.url = url;
    }
    private AdminRoadHazardsEnum(String text, String ...IDs){
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
