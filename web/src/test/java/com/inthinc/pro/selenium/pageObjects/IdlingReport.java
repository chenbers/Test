package com.inthinc.pro.selenium.pageObjects;

import java.util.StringTokenizer;

import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.automation.selenium.CoreMethodLib;
import com.inthinc.pro.automation.selenium.GlobalSelenium;
import com.inthinc.pro.automation.selenium.Page;
import com.inthinc.pro.selenium.pageEnums.DriverReportEnum;
import com.inthinc.pro.selenium.pageEnums.IdlingReportEnum;
import com.inthinc.pro.selenium.pageEnums.LoginEnum;

public class IdlingReport extends AbstractPage {
    
    protected static CoreMethodLib selenium;

    public IdlingReport(){
        selenium = GlobalSelenium.getSelenium();
    }
    
    public void textField_startDate_type(String date) {
        selenium.type(IdlingReportEnum.START_DATE.getID(), date);
    }
    
    public void textField_endDate_type(String date) {
        selenium.type(IdlingReportEnum.END_DATE.getID(), date);
    }
    
    public void button_refresh_click() {
        selenium.click(IdlingReportEnum.REFRESH.getID());
    }
    
    public void textField_driverSearch_type(String driver) {
        selenium.type(IdlingReportEnum.DRIVER_SEARCH.getID(), driver);        
    }
    
    public void textField_teamSearch_type(String driver) {
        selenium.type(IdlingReportEnum.TEAM_SEARCH.getID(), driver);        
    }    
    
    public void link_team_click(Integer row,String error) {
        clickIt(IdlingReportEnum.TEAM.getID(), row, error);   
    }  
    
    public void link_driver_click(Integer row,String error) {
        clickIt(IdlingReportEnum.DRIVER.getID(), row, error); 
    } 
    
    public void link_trips_click(Integer row,String error) {
        clickIt(IdlingReportEnum.TRIPS.getID(), row, error); 
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
