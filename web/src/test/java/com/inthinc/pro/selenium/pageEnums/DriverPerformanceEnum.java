package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum DriverPerformanceEnum implements SeleniumEnums {

    DEFAULT_URL(appUrl + "/driver"),
    
    DRIVER_NAME(null, "breadcrumbForm:driverPerformancePerson"),
    BREADCRUMB_ITEM(null, "breadcrumbForm:breadcrumbitem:###:driverPerformance-dashboard"),

    VIEW_ALL_TRIPS("View all trips", "driverPerformanceDriverTrips"), 
    
    EXPANDED_DRIVER_NAME_LINK(null, "driver***ChartForm:driver***DriverPerformance"),
    EXPANDED_BREADCRUMB(null, "driver***ChartForm:breadcrumbitem:###:driver***-dashboard"),

    
    ;

    private String text, url;
    private String[] IDs;
    
    private DriverPerformanceEnum(String url){
    	this.url = url;
    }
    private DriverPerformanceEnum(String text, String ...IDs){
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
