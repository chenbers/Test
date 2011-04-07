package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.selenium.SeleniumEnums;

public enum DriverPerformanceEnum implements SeleniumEnums {
    VIEW_ALL_TRIPS(null,"driverPerformanceDriverTrips",null,null,null)
    ; 
    
    private String text, ID, xpath, xpath_alt, url;
    
    private DriverPerformanceEnum( String text, String ID, String xpath, String xpath_alt, String url) {
        this.text=text;
        this.ID=ID;
        this.xpath=xpath;
        this.xpath_alt=xpath_alt;
        this.url=url;
    }

    @Override
    public String getID() {
        // TODO Auto-generated method stub
        return this.ID;
    }

    @Override
    public String getText() {
        // TODO Auto-generated method stub
        return this.text;
    }

    @Override
    public String getXpath() {
        // TODO Auto-generated method stub
        return this.xpath;
    }

    @Override
    public String getXpath_alt() {
        // TODO Auto-generated method stub
        return this.xpath_alt;
    }

    @Override
    public void setText(String text) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getURL() {
        // TODO Auto-generated method stub
        return null;
    }

}
