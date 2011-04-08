package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.automation.selenium.CoreMethodLib;
import com.inthinc.pro.automation.selenium.GlobalSelenium;
import com.inthinc.pro.automation.selenium.Page;
import com.inthinc.pro.selenium.pageEnums.DashboardEnum;

public class Dashboard extends AbstractPage{
    
    protected static CoreMethodLib selenium;

    public Dashboard(){
        selenium = GlobalSelenium.getSelenium();
    } 
    
    public void link_dashboardHelp_click(String error) {
        selenium.click("css=a.actionButton[href=" + DashboardEnum.DASHBOARD_HELP.getXpath() + "]", error);
    }
    
    public void link_overallDuration_click(String durationQualifier, String error) {
        selenium.click(DashboardEnum.OVERALL_DURATION.getID() + durationQualifier, error);
    }

    public void link_trendDuration_click(String durationQualifier, String error) {
        selenium.click(DashboardEnum.TREND_DURATION.getID() + durationQualifier, error );        
    }

    public void link_fuelEfficiencyDuration_click(String durationQualifier, String error) {
        selenium.click(DashboardEnum.FUEL_EFFICIENCY_DURATION.getID() + durationQualifier, error );        
    }

    public void link_speedingDuration_click(String durationQualifier, String error) {
        selenium.click(DashboardEnum.SPEEDING_DURATION.getID() + durationQualifier, error );        
    }

    public void link_idlingDuration_click(String durationQualifier, String error) {
        selenium.click(DashboardEnum.IDLING_DURATION.getID() + durationQualifier, error );        
    }
    
    public void link_overallExpand_click() {
        selenium.click(DashboardEnum.OVERALL_EXPAND);
    }
    
    public void link_trendExpand_click() {
        selenium.click(DashboardEnum.TREND_EXPAND);
    }
    
    public void link_fuelEfficiencyExpand_click() {
        selenium.click(DashboardEnum.FUEL_EFFICIENCY_EXPAND);
    }
    
    public void link_liveFleetExpand_click() {
        selenium.click(DashboardEnum.LIVE_FLEET_EXPAND);
    }    
    
    public void link_dashboardOverallRestore_click() {
        selenium.click(DashboardEnum.DASHBOARD_OVERALL_RESTORE);
    }
    
    public void link_dashboardTrendRestore_click() {
        selenium.click(DashboardEnum.DASHBOARD_TREND_RESTORE);
    }
    
    public void link_dashboardFuelEfficiencyRestore_click() {
        selenium.click(DashboardEnum.DASHBOARD_FUEL_EFFICIENCY_RESTORE);
    }
    
    public void link_dashboardLiveFleetRestore_click() {
        selenium.click(DashboardEnum.DASHBOARD_LIVE_FLEET_RESTORE);
    }  
    
    public void link_liveFleetRefresh_click() {
        selenium.click(DashboardEnum.LIVE_FLEET_REFRESH);
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
