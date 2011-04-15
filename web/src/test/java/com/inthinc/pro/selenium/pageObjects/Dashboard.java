package com.inthinc.pro.selenium.pageObjects;

import java.util.StringTokenizer;

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
    
    public void menu_overallEmail_click() {
        selenium.click(DashboardEnum.OVERALL_TOOLS);
        selenium.click(DashboardEnum.OVERALL_TOOLS_EMAIL);
        selenium.click(DashboardEnum.OVERALL_TOOLS_EMAIL_CANCEL);
    }
    
    public void menu_overallPDF_click() {
        selenium.click(DashboardEnum.OVERALL_TOOLS);
        selenium.click(DashboardEnum.OVERALL_TOOLS_PDF);
    }
    
    public void menu_mpgChartEmail_click() {
        selenium.click(DashboardEnum.MPG_CHART_TOOLS);
        selenium.click(DashboardEnum.MPG_CHART_TOOLS_EMAIL);
        selenium.click(DashboardEnum.MPG_CHART_TOOLS_EMAIL_CANCEL);        
    }
    
    public void menu_mpgPDF_click() {
        selenium.click(DashboardEnum.OVERALL_TOOLS);
        selenium.click(DashboardEnum.OVERALL_TOOLS_PDF);
    }
    
    public void menu_speedPercentageEmail_click() {
        selenium.click(DashboardEnum.SPEED_PERCENTAGE_TOOLS);
        selenium.click(DashboardEnum.SPEED_PERCENTAGE_TOOLS_EMAIL);
        selenium.click(DashboardEnum.SPEED_PERCENTAGE_TOOLS_EMAIL_CANCEL);        
    }
    
    public void menu_speedPercentagePDF_click() {
        selenium.click(DashboardEnum.OVERALL_TOOLS);
        selenium.click(DashboardEnum.OVERALL_TOOLS_PDF);
    }
    
    public void menu_trendEmail_click() {
        selenium.click(DashboardEnum.TREND_TOOLS);
        selenium.click(DashboardEnum.TREND_TOOLS_EMAIL);
        selenium.click(DashboardEnum.TREND_TOOLS_EMAIL_CANCEL);        
    }
    
    public void menu_trendPDF_click() {
        selenium.click(DashboardEnum.TREND_TOOLS);
        selenium.click(DashboardEnum.TREND_TOOLS_PDF);
    }
    
    public void menu_idlingPercentageEmail_click() {
        selenium.click(DashboardEnum.IDLING_PERCENTAGE_TOOLS);
        selenium.click(DashboardEnum.IDLING_PERCENTAGE_TOOLS_EMAIL);
        selenium.click(DashboardEnum.IDLING_PERCENTAGE_TOOLS_EMAIL_CANCEL);        
    }
    
    public void menu_idlingPercentagePDF_click() {
        selenium.click(DashboardEnum.IDLING_PERCENTAGE_TOOLS);
        selenium.click(DashboardEnum.IDLING_PERCENTAGE_TOOLS_PDF);        
    }
    
    public void link_dashboardHelp_click() {
        selenium.click(DashboardEnum.HELP_INVOKE);
    }
    
    public void link_overallDuration_click(String durationQualifier, String error) {
        selenium.click(DashboardEnum.OVERALL_DURATION.getID() + durationQualifier);
        
        // makes sure the next "thing" is there
        selenium.Pause(10);
    }

    public void link_trendDuration_click(String durationQualifier, String error) {
        selenium.click(DashboardEnum.TREND_DURATION.getID() + durationQualifier ); 
        
        // makes sure the next "thing" is there
        selenium.Pause(10);
    }

    public void link_fuelEfficiencyDuration_click(String durationQualifier, String error) {
        selenium.click(DashboardEnum.FUEL_EFFICIENCY_DURATION.getID() + durationQualifier ); 
        
        // makes sure the next "thing" is there
        selenium.Pause(10);
    }

    public void link_speedingDuration_click(String durationQualifier, String error) {
        selenium.click(DashboardEnum.SPEEDING_DURATION.getID() + durationQualifier );  
        
        // makes sure the next "thing" is there
        selenium.Pause(10);
    }

    public void link_idlingDuration_click(String durationQualifier, String error) {
        selenium.click(DashboardEnum.IDLING_DURATION.getID() + durationQualifier );  
        
        // makes sure the next "thing" is there
        selenium.Pause(10);     
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
        selenium.click(DashboardEnum.OVERALL_RESTORE);
    }
    
    public void link_dashboardTrendRestore_click() {
        selenium.click(DashboardEnum.TREND_RESTORE);
    }
    
    public void link_dashboardFuelEfficiencyRestore_click() {
        selenium.click(DashboardEnum.FUEL_EFFICIENCY_RESTORE);
    }
    
    public void link_dashboardLiveFleetRestore_click() {
        selenium.click(DashboardEnum.LIVE_FLEET_RESTORE);
    }  
    
    public void link_liveFleetRefresh_click() {
        selenium.click(DashboardEnum.LIVE_FLEET_REFRESH);
    }
    
    public void link_trendTable_click(Integer row) {
        clickIt(DashboardEnum.TREND_TABLE_LINE.getID(), row);        
    }
    
    private void clickIt(String rowQualifier, Integer row) {
        if ( row != null ) {
            rowQualifier = insertRow(rowQualifier,row);
        }
        selenium.click(rowQualifier);
        
        // makes sure the next "thing" is there
        selenium.Pause(10);
    }
    
    private String insertRow(String rowQualifier,Integer row) {
        StringTokenizer st = new StringTokenizer(rowQualifier,":");
        
        StringBuffer sb = new StringBuffer();
        sb.append(st.nextToken());
        sb.append(":");
        sb.append(st.nextToken());
        sb.append(":");
        sb.append(Integer.toString(row));
        sb.append(":");
        st.nextToken();
        sb.append(st.nextToken());
        
        return sb.toString();
    }    
  
}
