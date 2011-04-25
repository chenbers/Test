package com.inthinc.pro.selenium.pageObjects;

import java.util.StringTokenizer;

import com.inthinc.pro.selenium.pageEnums.DashboardEnum;

public class PageDashboard extends NavigationBar{
    
    public static enum Duration{ //TODO: jwimmer: question for dtanner: these enum values seem a little out of place.  I'd rather see them someplace like the UtilEnum.java that pwehan put together (i.e. an enum for items that are not specific to a SINGLE page, but aren't necessarily part of an abstract pageObject like masthead)
        DAYS_30("durationPanelHeaderDays"),
        MONTHS_3("durationPanelHeaderThreeMonths"),
        MONTHS_6("durationPanelHeaderSixMonths"),
        MONTHS_12("durationPanelHeaderTwelveMonths"),
        ;
        
        private String duration;
        
        private Duration(String duration){
            this.duration = duration;
        }
        
        public String getDuration(){
            return duration;
        }
    }
    
    
    public void link_overallToolsEmail_click() {
        selenium.click(DashboardEnum.OVERALL_TOOLS);
        selenium.click(DashboardEnum.OVERALL_TOOLS_EMAIL);
    }
    
    public void link_overallToolsEmailCancel_click() {
        selenium.click(DashboardEnum.OVERALL_TOOLS_EMAIL_CANCEL);       
    }
    
    public void link_overallToolsEmailEmail_click() {
        selenium.click(DashboardEnum.OVERALL_TOOLS_EMAIL_EMAIL);       
    }
    
//    public void menu_overallPDF_click() {
//        selenium.click(DashboardEnum.OVERALL_TOOLS);
//        selenium.click(DashboardEnum.OVERALL_TOOLS_PDF);
//    }
    
    public void link_mpgChartToolsEmail_click() {
        selenium.click(DashboardEnum.MPG_CHART_TOOLS);
        selenium.click(DashboardEnum.MPG_CHART_TOOLS_EMAIL);
    }
    
    public void link_mpgChartToolsEmailCancel_click() {
        selenium.click(DashboardEnum.MPG_CHART_TOOLS_EMAIL_CANCEL);                
    }
    
    public void link_mpgChartToolsEmailEmail_click() {
        selenium.click(DashboardEnum.MPG_CHART_TOOLS_EMAIL_EMAIL);                
    }    
    
//    public void link_mpgPDF_click() {
//        selenium.click(DashboardEnum.OVERALL_TOOLS);
//        selenium.click(DashboardEnum.OVERALL_TOOLS_PDF);
//    }
    
    public void link_speedPercentageToolsEmail_click() {
        selenium.click(DashboardEnum.SPEED_PERCENTAGE_TOOLS);
        selenium.click(DashboardEnum.SPEED_PERCENTAGE_TOOLS_EMAIL);
    }
    
    public void link_speedPercentageToolsEmailCancel_click() {
        selenium.click(DashboardEnum.SPEED_PERCENTAGE_TOOLS_EMAIL_CANCEL);               
    }
    
    public void link_speedPercentageToolsEmailEmail_click() {
        selenium.click(DashboardEnum.SPEED_PERCENTAGE_TOOLS_EMAIL_EMAIL);               
    }
    
//    public void menu_speedPercentagePDF_click() {
//        selenium.click(DashboardEnum.OVERALL_TOOLS);
//        selenium.click(DashboardEnum.OVERALL_TOOLS_PDF);
//    }
    
    public void link_trendToolsEmail_click() {
        selenium.click(DashboardEnum.TREND_TOOLS);
        selenium.click(DashboardEnum.TREND_TOOLS_EMAIL);
    }
    
    public void link_trendToolsEmailCancel_click() {
        selenium.click(DashboardEnum.TREND_TOOLS_EMAIL_CANCEL);                
    }
    
    public void link_trendToolsEmailEmail_click() {
        selenium.click(DashboardEnum.TREND_TOOLS_EMAIL_EMAIL);                
    }
    
//    public void menu_trendPDF_click() {
//        selenium.click(DashboardEnum.TREND_TOOLS);
//        selenium.click(DashboardEnum.TREND_TOOLS_PDF);
//    }
    
    public void link_idlingPercentageToolsEmail_click() {
        selenium.click(DashboardEnum.IDLING_PERCENTAGE_TOOLS);
        selenium.click(DashboardEnum.IDLING_PERCENTAGE_TOOLS_EMAIL);
    }
    
    public void link_idlingPercentageToolsEmailCancel_click() {
        selenium.click(DashboardEnum.IDLING_PERCENTAGE_TOOLS_EMAIL_CANCEL);               
    }
    
    public void link_idlingPercentageToolsEmailEmail_click() {
        selenium.click(DashboardEnum.IDLING_PERCENTAGE_TOOLS_EMAIL_EMAIL);               
    }
    
//    public void menu_idlingPercentagePDF_click() {
//        selenium.click(DashboardEnum.IDLING_PERCENTAGE_TOOLS);
//        selenium.click(DashboardEnum.IDLING_PERCENTAGE_TOOLS_PDF);        
//    }
    
    public void link_dashboardHelp_click() {
        selenium.click(DashboardEnum.HELP_INVOKE);
    }
    
    public void link_overallDuration_click(Duration durationQualifier) {
        clickDuration(DashboardEnum.OVERALL_DURATION,  durationQualifier);
    }
    
    private void clickDuration(DashboardEnum locator, Duration duration){
        selenium.click(locator.getID() + duration.getDuration() );
        selenium.Pause(10);
    }

    public void link_trendDuration_click(Duration durationQualifier) {
        clickDuration(DashboardEnum.TREND_DURATION,  durationQualifier);
    }

    public void link_fuelEfficiencyDuration_click(Duration durationQualifier) {
        clickDuration(DashboardEnum.FUEL_EFFICIENCY_DURATION,  durationQualifier);
    }

    public void link_speedingDuration_click(Duration durationQualifier) {
        clickDuration(DashboardEnum.SPEEDING_DURATION,  durationQualifier);
    }

    public void link_idlingDuration_click(Duration durationQualifier) {
        clickDuration(DashboardEnum.IDLING_DURATION,  durationQualifier);
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
