package com.inthinc.pro.selenium.pageObjects;

import java.util.StringTokenizer;

import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.automation.selenium.CoreMethodLib;
import com.inthinc.pro.automation.selenium.GlobalSelenium;
import com.inthinc.pro.automation.selenium.Page;
import com.inthinc.pro.selenium.pageEnums.DriverReportEnum;
import com.inthinc.pro.selenium.pageEnums.UtilEnum;

public class DriverReport extends AbstractPage {
    
    protected static CoreMethodLib selenium;

    public DriverReport(){
        selenium = GlobalSelenium.getSelenium();
    }
    
    public void link_team_click(Integer row) {
        clickIt(DriverReportEnum.TEAM.getID(), row);   
    }  
    
    public void link_driver_click(Integer row) {
        clickIt(DriverReportEnum.DRIVER.getID(), row); 
    } 
    
    public void link_vehicle_click(Integer row) {
        clickIt(DriverReportEnum.VEHICLE.getID(), row);
    }
    
    public void link_overall_click(Integer row) {
        clickIt(DriverReportEnum.OVERALL.getID(), row);
    }
    
    public void link_style_click(Integer row) {  
        clickIt(DriverReportEnum.STYLE.getID(), row);
    }
    
    public void link_seatbelt_click(Integer row) {
        clickIt(DriverReportEnum.SEATBELT.getID(), row);        
    }
    
    public void textField_driverSearch_type(String driver) {
        selenium.type(DriverReportEnum.DRIVER_SEARCH, driver);        
    }
    
    public void textField_teamSearch_type(String team) {
        selenium.type(DriverReportEnum.TEAM_SEARCH, team);        
    }
    
    public void textField_vehicleSearch_type(String vehicle) {
        selenium.type(DriverReportEnum.VEHICLE_SEARCH, vehicle);        
    }
    
    public void textField_employeeSearch_type(String vehicle) {
        selenium.type(DriverReportEnum.EMPLOYEE_SEARCH, vehicle);        
    }
    
    public void dropdown_overallScore_select(UtilEnum selection) {
        selenium.click(DriverReportEnum.OVERALL_SCORE_FILTER.getXpath());
        selenium.click("//div[4]/div[" + selection.getID() + "]");  
    }
    
    public void dropdown_speedScore_select(UtilEnum selection) {
        selenium.click(DriverReportEnum.SPEED_SCORE_FILTER.getXpath());
        selenium.click("//div[3]/div[" + selection.getID() + "]");  
    }
    
    public void dropdown_styleScore_select(UtilEnum selection) {
        selenium.click(DriverReportEnum.STYLE_SCORE_FILTER.getXpath());
        selenium.click("//div[2]/div[" + selection.getID() + "]");  
    }
    
    public void dropdown_seatbeltScore_select(UtilEnum selection) {
        selenium.click(DriverReportEnum.SEATBELT_SCORE_FILTER.getXpath());
        selenium.click("//div[" + selection.getID() + "]");  
    }
    
    public void form_driverSearch_submit() {
        selenium.submit(DriverReportEnum.DRIVER_FORM.getID());
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
