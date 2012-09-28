package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum AdminSpeedByStreetEnum implements SeleniumEnums {
    
    DEFAULT_URL(appUrl + "/admin/sbs"),
    
    DELETE(delete, "speedLimitChangeRequestTable:sbscrDelete"),
    CLEAR_ALL("Clear All", "speedLimitChangeRequestTable:sbscrDeleteAll"),
    SEARCH_BOX("Search", "speedLimitChangeRequestTable:address"),
    FIND_ADDRESS("Find Address", "speedLimitChangeRequestTable:sbscrSearch"),
    SUBMIT_REQUEST("Submit Request", "speedLimitChangeRequestTable:sbscrSubmit1"),
    TITLE("Admin - Speed By Street", "//span[@class='admin']"),
    MESSAGE("Type an address, or click on the map to identify street segments to update.", "speedLimitChangeRequestTable:items:caption"),
    HEADING_ADDRESS("Address", "speedLimitChangeRequestTable:items:headingAddress"),
    HEADING_SPEED_LIMIT("Speed Limit", "speedLimitChangeRequestTable:items:headingSpeedLimit"),
    HEADING_CURRENT("Current", "speedLimitChangeRequestTable:items:headingCurrent"),
    HEADING_NEW("New", "speedLimitChangeRequestTable:items:headingNew"), 
    HEADING_COMMENT("Comment", "speedLimitChangeRequestTable:items:headingComment"),
    CHECKBOX(null, "speedLimitChangeRequestTable:items:###:c"),
    ADDRESS(null, "speedLimitChangeRequestTable:items:###:valueAddress"),
    SPEED_LIMIT_CURRENT(null, "speedLimitChangeRequestTable:items:###:valueSpeedLimit"),
    SPEED_LIMIT_NEW("0", "speedLimitChangeRequestTable:items:###:s"),
    COMMENT("", "speedLimitChangeRequestTable:items:###:t"),
    MAP(null, "speedLimitChangeRequestTable:map"),
    
    ;
    private String text, url;
    private String[] IDs;
    
    private AdminSpeedByStreetEnum(String url){
        this.url = url;
    }
    private AdminSpeedByStreetEnum(String text, String ...IDs){
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
