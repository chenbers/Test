package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum AdminRouteHazardsEnum implements SeleniumEnums {
    
    TITLE("Admin - Add Hazard", "//span[@class='editHazard']"),

    ADD_DESTINATION("Add Destination", "addDestination"),
    
    SORT_BY_DESCRIPTION("Description", "hazards-form:hazards-table:descriptionheader:sortDiv"),
    SORT_BY_TYPE("Type", "hazards-form:hazards-table:typeheader:sortDiv"),
    SORT_BY_EXP_TIME("Exp. Time", "hazards-form:hazards-table:endTimeheader:sortDiv"),
    
    ENTRY_DESCRIPTION(null, "hazards-form:hazards-table:###:description"),
    ENTRY_TYPE(null, "hazards-form:hazards-table:###:type"),
    ENTRY_EXP_TIME(null, "hazards-form:hazards-table:###:endTime"),
    
    ORIGIN_TEXTFIELD(null, "origin"),
    ADD_DESTINATION_TEXTFIELD(null, "waypoint###"),
    DESTINATION_TEXTFIELD(null, "destination"),
    
    FIND_ROUTE_BUTTON(null, "submit"),
    
    URL(appUrl + "/admin/editHazard"),
    
    ;
    private String text, url;
    private String[] IDs;

    private AdminRouteHazardsEnum(String url) {
        this.url = url;
    }

    private AdminRouteHazardsEnum(String text, String... IDs) {
        this.text = text;
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
