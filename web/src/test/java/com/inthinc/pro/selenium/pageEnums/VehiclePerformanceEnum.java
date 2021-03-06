package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum VehiclePerformanceEnum implements SeleniumEnums {

    DEFAULT_URL(appUrl + "/vehicle"), 
    
    VEHICLE_NAME("Vehicle Performance:", "vehiclePerformanceBreadCrumb:vehiclePerformance"),
    BREADCRUMB_ITEM(null, "vehiclePerformanceBreadCrumb:breadcrumbitem:###:vehiclePerformance-dashboard"), 

    VIEW_ALL_TRIPS("View all trips", "vehiclePerformanceTrips"), 
    
    EXPANDED_VEHICLE_NAME_LINK(null, "//a[contains(@id,'vehicle***Title')]"), 
    EXPANDED_BREADCRUMB(null, "//a[contains(@id,'breadcrumbitem:###:vehicle***-dashboard')]"),

    EXCLUDE("exclude", "eventTableForm:notificationsTable:###:vehicle***Included"),
    INCLUDE("include", "eventTableForm:notificationsTable:###:vehicle***_excluded"),
    
    ;

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
