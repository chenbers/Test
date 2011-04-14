package com.inthinc.pro.selenium.pageObjects;

import java.util.StringTokenizer;

import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.automation.selenium.CoreMethodLib;
import com.inthinc.pro.automation.selenium.GlobalSelenium;
import com.inthinc.pro.automation.selenium.Page;
import com.inthinc.pro.selenium.pageEnums.DriverReportEnum;
import com.inthinc.pro.selenium.pageEnums.UtilEnum;
import com.inthinc.pro.selenium.pageEnums.VehicleReportEnum;

public class VehicleReport  extends AbstractPage {
    
    protected static CoreMethodLib selenium;

    public VehicleReport(){
        selenium = GlobalSelenium.getSelenium();
    }
    
    public void link_team_click(Integer row) {
        clickIt(VehicleReportEnum.TEAM.getID(), row);   
    }  
    
    public void link_driver_click(Integer row) {
        clickIt(VehicleReportEnum.DRIVER.getID(), row); 
    } 
    
    public void link_vehicle_click(Integer row) {
        clickIt(VehicleReportEnum.VEHICLE.getID(), row);
    }
    
    public void link_overall_click(Integer row) {
        clickIt(VehicleReportEnum.OVERALL.getID(), row);
    }
    
    public void link_style_click(Integer row) {  
        clickIt(VehicleReportEnum.STYLE.getID(), row);
    }
    
    public void link_seatbelt_click(Integer row) {
        clickIt(VehicleReportEnum.SEATBELT.getID(), row);        
    }
    
    public void textField_driverSearch_type(String driver) {
        selenium.type(VehicleReportEnum.DRIVER_SEARCH, driver);        
    }
    
    public void textField_teamSearch_type(String driver) {
        selenium.type(VehicleReportEnum.TEAM_SEARCH, driver);        
    }
    
    public void textField_vehicleSearch_type(String driver) {
        selenium.type(VehicleReportEnum.VEHICLE_SEARCH, driver);        
    }
    
    public void textField_yearMakeModelSearch_type(String driver) {
        selenium.type(VehicleReportEnum.YEAR_MAKE_MODEL_SEARCH, driver);        
    }
    
    public void dropdown_overallScore_select(UtilEnum selection) {
        selenium.click(VehicleReportEnum.OVERALL_SCORE_FILTER.getXpath());
        selenium.click("//div[3]/div[" + selection.getID() + "]");  
    }
    
    public void dropdown_speedScore_select(UtilEnum selection) {
        selenium.click(VehicleReportEnum.SPEED_SCORE_FILTER.getXpath());
        selenium.click("//div[2]/div[" + selection.getID() + "]");  
    }
    
    public void dropdown_styleScore_select(UtilEnum selection) {
        selenium.click(VehicleReportEnum.STYLE_SCORE_FILTER.getXpath());
        selenium.click("//div[" + selection.getID() + "]");  
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
