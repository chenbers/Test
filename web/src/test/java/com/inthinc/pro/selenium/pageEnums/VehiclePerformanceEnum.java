package com.inthinc.pro.selenium.pageEnums;

import java.util.List;

import com.inthinc.pro.automation.enums.SeleniumEnumUtil;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum VehiclePerformanceEnum implements SeleniumEnums {
    VIEW_ALL_TRIPS(null,"vehiclePerformanceTrips",null,null,null)
    ; 
    
    private String text, ID, xpath, xpath_alt, url;
    
    private VehiclePerformanceEnum( String text, String ID, String xpath, String xpath_alt, String url) {
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
    
    @Override
    public List<String> getLocators() {        
        return SeleniumEnumUtil.getLocators(this);
    }
}
