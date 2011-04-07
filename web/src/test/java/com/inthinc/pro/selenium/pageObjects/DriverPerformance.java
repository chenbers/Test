package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.automation.selenium.CoreMethodLib;
import com.inthinc.pro.automation.selenium.GlobalSelenium;
import com.inthinc.pro.automation.selenium.Page;
import com.inthinc.pro.selenium.pageEnums.DriverPerformanceEnum;

public class DriverPerformance extends AbstractPage {
    
    protected static CoreMethodLib selenium;

    public DriverPerformance(){
        selenium = GlobalSelenium.getSelenium();
    }
    
    public void link_viewTrips_click(String error) {
        selenium.click(DriverPerformanceEnum.VIEW_ALL_TRIPS.getID(), error);        
    }

    @Override
    public Page load() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean validate() {
        // TODO Auto-generated method stub
        return false;
    }
}
