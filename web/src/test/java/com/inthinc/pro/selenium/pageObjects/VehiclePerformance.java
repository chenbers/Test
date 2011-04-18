package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.selenium.pageEnums.VehiclePerformanceEnum;

public class VehiclePerformance extends NavigationBar {
    
    
    public void link_viewTrips_click() {
        selenium.click(VehiclePerformanceEnum.VIEW_ALL_TRIPS);        
    }
}
