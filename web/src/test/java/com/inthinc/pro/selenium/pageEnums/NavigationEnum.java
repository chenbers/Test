package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.selenium.SeleniumEnums;

public enum NavigationEnum implements SeleniumEnums {
    NAVIGATE_TO_DRIVER_REPORT_MAIN_MENU(null,"navigation:layout-navigationReports",null,null,null),
    NAVIGATE_TO_DRIVER_REPORT(null,null,"//a[@href='/tiwipro/app/reports/driversReport']",null,null),
    NAVIGATE_TO_VEHICLE_REPORT(null,null,"//a[@href='/tiwipro/app/reports/vehiclesReport'] ",null,null),
    NAVIGATE_TO_IDLING_REPORT(null,null,"//a[@href='/tiwipro/app/reports/idlingReport'] ",null,null),
    NAVIGATE_TO_DEVICES_REPORT(null,null,"//a[@href='/tiwipro/app/reports/devicesReport'] ",null,null),
    NAVIGATE_TO_WAYSMART_REPORT(null,null,"//a[@href='/tiwipro/app/reports/waysmartReport'] ",null,null),    
    SEARCH_DROPDOWN(null,"navigation:layout-navigationRedirectTo",null,null,null),
    SEARCH_TEXT_FIELD(null,"navigation:layout-redirectSearch",null,null,null),
    SEARCH_BUTTON(null,"navigation:layout-navigation_search_button",null,null,null)
    ;
    
    private String text, ID, xpath, xpath_alt, url;
    
    private NavigationEnum( String text, String ID, String xpath, String xpath_alt, String url) {
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
