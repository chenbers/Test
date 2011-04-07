package com.inthinc.pro.selenium.pageObjects;

import java.util.StringTokenizer;

import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.automation.selenium.CoreMethodLib;
import com.inthinc.pro.automation.selenium.GlobalSelenium;
import com.inthinc.pro.automation.selenium.Page;
import com.inthinc.pro.selenium.pageEnums.DashboardEnum;
import com.inthinc.pro.selenium.pageEnums.DriverReportEnum;
import com.inthinc.pro.selenium.pageEnums.LoginEnum;

public class DriverReport extends AbstractPage {
    
    protected static CoreMethodLib selenium;

    public DriverReport(){
        selenium = GlobalSelenium.getSelenium();
    }
    
    public void link_team_click(Integer row,String error) {
        clickIt(DriverReportEnum.TEAM.getID(), row, error);   
    }  
    
    public void link_driver_click(Integer row,String error) {
        clickIt(DriverReportEnum.DRIVER.getID(), row, error); 
    } 
    
    public void link_vehicle_click(Integer row,String error) {
        clickIt(DriverReportEnum.VEHICLE.getID(), row, error);
    }
    
    public void link_overall_click(Integer row,String error) {
        clickIt(DriverReportEnum.OVERALL.getID(), row, error);
    }
    
    public void link_style_click(Integer row,String error) {  
        clickIt(DriverReportEnum.STYLE.getID(), row, error);
    }
    
    public void link_seatbelt_click(Integer row,String error) {
        clickIt(DriverReportEnum.SEATBELT.getID(), row, error);        
    }
    
    public void textField_driverSearch_type(String driver) {
        selenium.type(DriverReportEnum.DRIVER_SEARCH.getID(), driver);        
    }
    
    public void textField_teamSearch_type(String driver) {
        selenium.type(DriverReportEnum.TEAM_SEARCH.getID(), driver);        
    }
    
    public void textField_vehicleSearch_type(String driver) {
        selenium.type(DriverReportEnum.VEHICLE_SEARCH.getID(), driver);        
    }
    
    public void form_driverSearch_submit() {
        selenium.submit(DriverReportEnum.DRIVER_FORM.getID());
    }
    
    private void clickIt(String rowQualifier, Integer row, String error) {
        if ( row != null ) {
            rowQualifier = insertRow(rowQualifier,row);
        }
        selenium.click(rowQualifier, error);
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
}
