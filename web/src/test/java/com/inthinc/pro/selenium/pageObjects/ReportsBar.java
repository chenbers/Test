package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.selenium.pageEnums.ReportsBarEnum;


public abstract class ReportsBar extends NavigationBar {
    
    public void link_drivers_click(){
        selenium.click(ReportsBarEnum.DRIVERS);
        selenium.waitForPageToLoad();
    }
    
    public void link_vehicles_click(){
        selenium.click(ReportsBarEnum.VEHICLES);
        selenium.waitForPageToLoad();
    }
    
    public void link_idling_click(){
        selenium.click(ReportsBarEnum.IDLING);
        selenium.waitForPageToLoad();
    }
    
    public void link_devices_click(){
        selenium.click(ReportsBarEnum.DEVICES);
        selenium.waitForPageToLoad();
    }
    
    public void link_waySmart_click(){
        selenium.click(ReportsBarEnum.WAYSMART);
        selenium.waitForPageToLoad();
    }

}
