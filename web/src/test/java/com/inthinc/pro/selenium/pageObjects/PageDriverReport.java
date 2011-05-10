package com.inthinc.pro.selenium.pageObjects;

import java.util.StringTokenizer;

import com.inthinc.pro.automation.elements.DhxDropDown;
import com.inthinc.pro.automation.elements.TableTextLink;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.enums.AutomationEnum;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.DriverPerformanceEnum;
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
    	private SeleniumEnums[] enums = {DriverReportEnum.SEATBELT_SCORE_DHX, 
    			DriverReportEnum.STYLE_SCORE_DHX, DriverReportEnum.SPEED_SCORE_DHX,
    			DriverReportEnum.OVERALL_SCORE_DHX};
    	

        public DhxDropDown overallScoreFilter() {
        	return new DhxDropDown(DriverReportEnum.OVERALL_SCORE_DHX)
        	.tableOptions(enums)
        	.dropDownButton(DriverReportEnum.OVERALL_SCORE_ARROW);
        }
        
        public DhxDropDown speedScoreFilter(){
        	return new DhxDropDown(DriverReportEnum.STYLE_SCORE_DHX)
        	.tableOptions(enums)
        	.dropDownButton(DriverReportEnum.STYLE_SCORE_ARROW);
        }
        
        public DhxDropDown styleScoreFilter(){
        	return new DhxDropDown(DriverReportEnum.SPEED_SCORE_DHX)
        	.tableOptions(enums)
        	.dropDownButton(DriverReportEnum.SPEED_SCORE_ARROW);
        }
        
        public DhxDropDown seatBeltFilter(){
        	return new DhxDropDown(DriverReportEnum.SEATBELT_SCORE_DHX)
        	.tableOptions(enums)
        	.dropDownButton(DriverReportEnum.SEATBELT_SCORE_ARROW);
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
