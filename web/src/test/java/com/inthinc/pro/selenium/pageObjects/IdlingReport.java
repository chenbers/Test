package com.inthinc.pro.selenium.pageObjects;

import java.util.StringTokenizer;

import com.inthinc.pro.selenium.pageEnums.IdlingReportEnum;

public class IdlingReport extends NavigationBar {
    
    
    public void textField_startDate_type(String date) {
        selenium.type(IdlingReportEnum.START_DATE, date);
    }
    
    public void textField_endDate_type(String date) {
        selenium.type(IdlingReportEnum.END_DATE, date);
    }
    
    public void button_refresh_click() {
        selenium.click(IdlingReportEnum.REFRESH);
    }
    
    public void textField_driverSearch_type(String driver) {
        selenium.type(IdlingReportEnum.DRIVER_SEARCH, driver);        
    }
    
    public void textField_teamSearch_type(String team) {
        selenium.type(IdlingReportEnum.TEAM_SEARCH, team);        
    }    
    
    public void link_team_click(Integer row) {
        clickIt(IdlingReportEnum.TEAM.getID(), row);   
    }  
    
    public void link_driver_click(Integer row) {
        clickIt(IdlingReportEnum.DRIVER.getID(), row); 
    } 
    
    public void link_trips_click(Integer row) {
        clickIt(IdlingReportEnum.TRIPS.getID(), row); 
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
