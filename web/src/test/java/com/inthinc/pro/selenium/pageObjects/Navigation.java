package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.automation.selenium.CoreMethodLib;
import com.inthinc.pro.automation.selenium.GlobalSelenium;
import com.inthinc.pro.automation.selenium.Page;
import com.inthinc.pro.selenium.pageEnums.NavigationEnum;

public class Navigation extends AbstractPage {
    
    protected static CoreMethodLib selenium;

    public Navigation(){
        selenium = GlobalSelenium.getSelenium();
    }  
    
    public void link_reportsMainMenu_click() {
        selenium.click(NavigationEnum.NAVIGATE_TO_DRIVER_REPORT_MAIN_MENU);        
    }
    
    public void link_driverSubMenu_click() {
        selenium.click(NavigationEnum.NAVIGATE_TO_DRIVER_REPORT);        
    }
    
    public void link_vehicleSubMenu_click() {
        selenium.click(NavigationEnum.NAVIGATE_TO_VEHICLE_REPORT);        
    }
    
    public void link_idlingSubMenu_click() {
        selenium.click(NavigationEnum.NAVIGATE_TO_IDLING_REPORT);        
    }
    
    public void link_devicesSubMenu_click() {
        selenium.click(NavigationEnum.NAVIGATE_TO_DEVICES_REPORT);        
    }
    
    public void link_waySmartSubMenu_click() {
        selenium.click(NavigationEnum.NAVIGATE_TO_WAYSMART_REPORT);        
    }    
    public void dropdown_search_select(String typeOfSearch) {
        selenium.select(NavigationEnum.SEARCH_DROPDOWN.getID(), "label=" + typeOfSearch );
    }
    
    public void textField_search_type(String search){
        selenium.type(NavigationEnum.SEARCH_TEXT_FIELD, search);
    }
    
    public void button_search_click() {
        selenium.click(NavigationEnum.SEARCH_BUTTON);
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

    @Override
    public String getExpectedPath() {
        // TODO Auto-generated method stub
        return null;
    }
}
