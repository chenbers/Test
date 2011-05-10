package com.inthinc.pro.selenium.pageObjects;

import java.util.StringTokenizer;

import com.inthinc.pro.automation.elements.DhxDropDown;
import com.inthinc.pro.automation.elements.TableTextLink;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.WebUtilEnum;
import com.inthinc.pro.selenium.pageEnums.VehicleReportEnum;

public class PageVehicleReport  extends NavigationBar {
    
    
	public class VehicleReportLinks{
		
	    public TableTextLink link_team_click(Integer row) {
	        return new TableTextLink(VehicleReportEnum.TEAM);   
	    }  
	    
	    public TableTextLink link_driver_click(Integer row) {
	        return new TableTextLink(VehicleReportEnum.DRIVER); 
	    } 
	    
	    public TableTextLink link_vehicle_click(Integer row) {
	        return new TableTextLink(VehicleReportEnum.VEHICLE);
	    }
	    
	    public TableTextLink link_overall_click(Integer row) {
	        return new TableTextLink(VehicleReportEnum.OVERALL);
	    }
	    
	    public TableTextLink link_style_click(Integer row) {  
	        return new TableTextLink(VehicleReportEnum.STYLE);
	    }
	    
	    public TableTextLink link_seatbelt_click(Integer row) {
	        return new TableTextLink(VehicleReportEnum.SEATBELT);        
	    }
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
    
    
    public class VehicleReportDropDowns{
    	
    	private SeleniumEnums[] enums = {VehicleReportEnum.SEATBELT_SCORE_DHX, 
    			VehicleReportEnum.STYLE_SCORE_DHX, VehicleReportEnum.SPEED_SCORE_DHX,
    			VehicleReportEnum.OVERALL_SCORE_DHX};
    	

        public DhxDropDown overallScoreFilter(WebUtilEnum selection) {
        	return new DhxDropDown(VehicleReportEnum.OVERALL_SCORE_DHX).tableOptions(enums);
        }
        
        public DhxDropDown speedScoreFilter(){
        	return new DhxDropDown(VehicleReportEnum.STYLE_SCORE_DHX).tableOptions(enums);
        }
        
        public DhxDropDown styleScoreFilter(){
        	return new DhxDropDown(VehicleReportEnum.STYLE_SCORE_DHX).tableOptions(enums);
        }
        
        public DhxDropDown seatBeltFilter(){
        	return new DhxDropDown(VehicleReportEnum.SEATBELT_SCORE_DHX).tableOptions(enums);
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
