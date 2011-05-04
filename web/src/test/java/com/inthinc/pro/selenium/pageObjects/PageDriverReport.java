package com.inthinc.pro.selenium.pageObjects;

import java.util.StringTokenizer;

import com.inthinc.pro.automation.elements.TableTextLink;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.selenium.pageEnums.DriverReportEnum;
import com.inthinc.pro.selenium.pageEnums.WebUtilEnum;

public class PageDriverReport extends NavigationBar {
    
    public DriverReportButtons _button(){
        return new DriverReportButtons();
    }
    
    public class DriverReportButtons{}
    
    public DriverReportDropDowns _dropDown(){
        return new DriverReportDropDowns();
    }
    
    public DriverReportLinks _link(){
        return new DriverReportLinks();
    }
    
    public DriverReportTextFields _textField(){
        return new DriverReportTextFields();
    }
    
    public DriverReportTexts _text(){
        return new DriverReportTexts();
    }
    
    public class DriverReportTexts{}
    
    public class DriverReportLinks extends NavigationBarLinks{
        
        public TableTextLink team() {
            return new TableTextLink(DriverReportEnum.TEAM);   
        }  
        
        public TableTextLink link_driver_click(Integer row) {
            return new TableTextLink(DriverReportEnum.DRIVER); 
        } 
        
        public TableTextLink link_vehicle_click(Integer row) {
            return new TableTextLink(DriverReportEnum.VEHICLE);
        }
        
        public TableTextLink link_overall_click(Integer row) {
            return new TableTextLink(DriverReportEnum.OVERALL);
        }
        
        public TableTextLink link_style_click(Integer row) {  
            return new TableTextLink(DriverReportEnum.STYLE);
        }
        
        public TableTextLink link_seatbelt_click(Integer row) {
            return new TableTextLink(DriverReportEnum.SEATBELT);        
        }
    }
    
    public class DriverReportTextFields extends NavigationBarTextFields{
        
        public TextField driverSearch() {
            return new TextField(DriverReportEnum.DRIVER_SEARCH);        
        }
        
        public TextField textField_teamSearch() {
            return new TextField(DriverReportEnum.TEAM_SEARCH);        
        }
        
        public TextField textField_vehicleSearch() {
            return new TextField(DriverReportEnum.VEHICLE_SEARCH);        
        }
    
        public TextField textField_employeeSearch() {
            return new TextField(DriverReportEnum.EMPLOYEE_SEARCH);        
        }
    }
    
    public class DriverReportDropDowns extends NavigationBarDropDowns{

        public void dropdown_overallScore_selectValue(WebUtilEnum selection) {
            selenium.click(DriverReportEnum.OVERALL_SCORE_FILTER.getXpath());
            selenium.click("//div[4]/div[" + selection.getID() + "]");  
        }
        
        public void dropdown_speedScore_selectValue(WebUtilEnum selection) {
            selenium.click(DriverReportEnum.SPEED_SCORE_FILTER.getXpath());
            selenium.click("//div[3]/div[" + selection.getID() + "]");  
        }
        
        public void dropdown_styleScore_selectValue(WebUtilEnum selection) {
            selenium.click(DriverReportEnum.STYLE_SCORE_FILTER.getXpath());
            selenium.click("//div[2]/div[" + selection.getID() + "]");  
        }
        
        public void dropdown_seatbeltScore_selectValue(WebUtilEnum selection) {
            selenium.click(DriverReportEnum.SEATBELT_SCORE_FILTER.getXpath());
            selenium.click("//div[" + selection.getID() + "]");  
        }
    }
    
    private void clickIt(String rowQualifier, Integer row) {
        if ( row != null ) {
            rowQualifier = insertRow(rowQualifier,row);
        }
        selenium.click(rowQualifier);
        
        // makes sure the next "thing" is there
        selenium.pause(10);        
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
