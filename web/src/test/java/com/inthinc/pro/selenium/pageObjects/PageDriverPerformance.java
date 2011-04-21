package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.selenium.pageEnums.DriverPerformanceEnum;

public class PageDriverPerformance extends NavigationBar {
    
    
    public void link_viewTrips_click() {
        selenium.click(DriverPerformanceEnum.VIEW_ALL_TRIPS);        
    }
}
