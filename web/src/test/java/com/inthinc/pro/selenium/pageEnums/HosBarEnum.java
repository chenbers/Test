package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;


public enum HosBarEnum implements SeleniumEnums {
    
    /* Links */
    DRIVER_LOGS("Driver Logs", "//a[contains(@id,'hos-hosLogs')]"),
    REPORTS("Reports", "//a[contains(@id,'hos-hosReports')]"),
    FUEL_STOPS("Fuel Stops", "//a[contains(@id,'hos-fuelStops')]")
    
    ;
    
    private String text, url;
    private String[] IDs;
    
    private HosBarEnum(String url){
    	this.url = url;
    }
    private HosBarEnum(String text, String ...IDs){
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
