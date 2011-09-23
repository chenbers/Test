package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum AdminSpeedByStreetEnum implements SeleniumEnums {
    
    DEFAULT_URL("/app/admin/sbs"),
    
    DELETE(delete, "speedLimitChangeRequestTable:sbscrDelete"),
    CLEAR_ALL("Clear All", "speedLimitChangeRequestTable:sbscrDeleteAll"),
    SEARCH_BOX("Search", "speedLimitChangeRequestTable:address"),
    FIND_ADDRESS("Find Address", "speedLimitChangeRequestTable:sbscrSearch"),
    SUBMIT_REQUEST("Submit Request", "speedLimitChangeRequestTable:sbscrSubmit1"),
    TITLE("Admin - Speed By Street", "//span[@class='admin']"),
    MESSAGE("Type an address, or click on the map to identify street segments to update.", "speedLimitChangeRequestTable:items:caption"),
    
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
