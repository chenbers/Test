package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum VehiclePerformanceEnum implements SeleniumEnums {
    VIEW_ALL_TRIPS(null, "vehiclePerformanceTrips");

    private String text, url;
    private String[] IDs;
    
    private VehiclePerformanceEnum(String url){
    	this.url = url;
    }
    private VehiclePerformanceEnum(String text, String ...IDs){
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
