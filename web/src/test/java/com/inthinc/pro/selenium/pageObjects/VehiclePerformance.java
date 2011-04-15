package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.automation.selenium.CoreMethodLib;
import com.inthinc.pro.automation.selenium.GlobalSelenium;
import com.inthinc.pro.selenium.pageEnums.VehiclePerformanceEnum;

public class VehiclePerformance extends AbstractPage {
    
    protected static CoreMethodLib selenium;

    public VehiclePerformance(){
        selenium = GlobalSelenium.getSelenium();
    }
    
    public void link_viewTrips_click() {
        selenium.click(VehiclePerformanceEnum.VIEW_ALL_TRIPS);        
    }
}
