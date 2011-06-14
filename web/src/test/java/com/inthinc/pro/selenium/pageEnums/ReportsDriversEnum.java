package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum ReportsDriversEnum implements SeleniumEnums {
	/* Permanent Items */
	TITLE("Driver Report", "//span[@class='driver']"),
	EDIT_COLUMNS("Edit Columns", "drivers-form:driversEditColumns"),
	TOOLS(null, "drivers-form:drivers_reportToolImageId"),
	
	
    DEFAULT_URL("/app/reports/driversReport"),
    GROUP_SEARCH(null, "drivers-form:drivers:groupfsp"),
    DRIVER_SEARCH(null, "drivers-form:drivers:fullNamefsp"),
    VEHICLE_SEARCH(null, "drivers-form:drivers:vehiclenamefsp"),
    EMPLOYEE_SEARCH(null, "drivers-form:drivers:empidfsp"),

    
    GROUP_VALUE(null, "drivers-form:drivers:###:driversDashboard"),
    DRIVER_VALUE(null, "drivers-form:drivers:###:driversDriverPerformance"),
    VEHICLE_VALUE(null, "drivers-form:drivers:###:driversVehiclePerformance"),
    EMPLOYEE_ID_VALUE(null, "drivers-form:drivers:###:empid"),
    DISTANCE_DRIVEN_VALUE(null, "drivers-form:drivers:###:distance"),
    OVERALL_SCORE_VALUE(null,  "//td[@id='drivers-form:drivers:###:overallScore']/span/div/div/div/div/div/a"),
    STYLE_SCORE_VALUE(null,    "//td[@id='drivers-form:drivers:###:styleScore']/span/div/div/div/div/div/a"),
    SPEED_SCORE_VALUE(null,    "//td[@id='drivers-form:drivers:###:speedScore']/span/div/div/div/div/div/a"), 
    SEATBELT_SCORE_VALUE(null, "//td[@id='drivers-form:drivers:###:seatbeltScore']/span/div/div/div/div/div/a"), 
        
    
    GROUP_SORT("Group:", "drivers-form:drivers:driversDashboardheader:sortDiv"),
    DRIVER_SORT("Driver:", "drivers-form:drivers:driversDriverPerformanceheader:sortDiv"),
    VEHICLE_SORT("Vehicle", "drivers-form:drivers:driversVehiclePerformanceheader:sortDiv"),
    EMPLOYEE_ID_SORT("Employee ID", "drivers-form:drivers:empidheader:sortDiv"),
    DISTANCE_DRIVEN_SORT("Distance Driven", "drivers-form:drivers:distanceheader:sortDiv"),
    OVERALL_SCORE_SORT("Overall", "drivers-form:drivers:overallScoreheader:sortDiv"),
    STYLE_SCORE_SORT("Style", "drivers-form:drivers:styleScoreheader:sortDiv"),
    SPEED_SCORE_SORT("Speed", "drivers-form:drivers:speadScoreheader:sortDiv"), 
    SEATBELT_SCORE_SORT("Seat Belt", "drivers-form:drivers:seatbeltScoreheader:sortDiv"), 
         
    ;

    private String text, url;
    private String[] IDs;
    
    private ReportsDriversEnum(String url){
    	this.url = url;
    }
    private ReportsDriversEnum(String text, String ...IDs){
        this.text=text;
    	this.IDs = IDs;
    }

    @Override
    public String[] getIDs() {
        return IDs;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getURL() {
        return url;
    }
}
