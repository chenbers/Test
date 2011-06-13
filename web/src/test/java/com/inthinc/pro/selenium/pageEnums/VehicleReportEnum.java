package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum VehicleReportEnum implements SeleniumEnums {
	
	TITLE("Vehicle Report", "//span[@class='vehicle']"),
	
    DEFAULT_URL("/app/reports/vehiclesReport"),
    GROUP_SEARCH(null,"vehicles-form:vehicles:groupfsp"),
    DRIVER_SEARCH(null,"vehicles-form:vehicles:fullNamefsp"),
    VEHICLE_SEARCH(null,"vehicles-form:vehicles:namefsp"),
    YEAR_MAKE_MODEL_SEARCH(null,"vehicles-form:vehicles:makeModelYearfsp"),
    

    GROUP_VALUE(null,"vehicles-form:vehicles:###:vehiclesDashboard"),
    VEHICLE_VALUE(null,"vehicles-form:vehicles:###:vehiclesVehiclePerformance"),
    YEAR_MAKE_MODEL_VALUE(null, "vehicles-form:vehicles:###:makeModelYear"),
    DRIVER_VALUE(null,"vehicles-form:vehicles:###:vehiclesDriverPerformance"),
    DISTANCE_DRIVEN_VALUE(null, "vehicles-form:vehicles:###:distance"),
    ODOMETER_VALUE(null, "vehicles-form:vehicles:###:odometer"),
    OVERALL_SCORE_VALUE(null,"vehicles-form:drivers:###:overallScore"),   
    STYLE_SCORE_VALUE(null,"vehicles-form:drivers:###:styleScore"),              
    SPEED_SCORE_VALUE(null,"vehicles-form:drivers:###:speedScore"),
    
    
    GROUP_SORT(null,"vehicles-form:vehicles:vehiclesDashboardheader:sortDiv"),
    VEHICLE_SORT(null,"vehicles-form:vehicles:vehiclesVehiclePerformanceheader:sortDiv"),
    YEAR_MAKE_MODEL_SORT(null, "vehicles-form:vehicles:makeModelYearheader:sortDiv"),
    DRIVER_SORT(null,"vehicles-form:vehicles:vehiclesDriverPerformanceheader:sortDiv"),
    DISTANCE_DRIVEN_SORT(null, "vehicles-form:vehicles:distanceheader:sortDiv"),
    ODOMETER_SORT(null, "vehicles-form:vehicles:odometerheader:sortDiv"),
    OVERALL_SCORE_SORT(null,"vehicles-form:drivers:overallScoreheader:sortDiv"),          
    STYLE_SCORE_SORT(null,"vehicles-form:drivers:styleScoreheader:sortDiv"),              
    SPEED_SCORE_SORT(null,"vehicles-form:drivers:speedScoreheader:sortDiv"),
        
    ;
    
    private String text, url;
    private String[] IDs;
    
    private VehicleReportEnum(String url){
    	this.url = url;
    }
    private VehicleReportEnum(String text, String ...IDs){
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
