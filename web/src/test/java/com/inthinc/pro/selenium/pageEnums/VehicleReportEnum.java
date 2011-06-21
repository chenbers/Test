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
    OVERALL_SCORE_VALUE(null,"//td[@id='vehicles-form:vehicles:###:overallScore']/span/div/div/div/div/div/a"),   
    STYLE_SCORE_VALUE(null,"//td[@id='vehicles-form:vehicles:###:styleScore']/span/div/div/div/div/div/a"),              
    SPEED_SCORE_VALUE(null,"//td[@id='vehicles-form:vehicles:###:speedScore']/span/div/div/div/div/div/a"),
    
    
    GROUP_SORT(null,"vehicles-form:vehicles:groupheader:sortDiv"),
    VEHICLE_SORT(null,"vehicles-form:vehicles:nameheader:sortDiv"),
    YEAR_MAKE_MODEL_SORT(null, "vehicles-form:vehicles:makeModelYearheader:sortDiv"),
    DRIVER_SORT(null,"vehicles-vehicles-form:vehicles:fullNameheader:sortDiv"),
    DISTANCE_DRIVEN_SORT(null, "vehicles-form:vehicles:distanceheader:sortDiv"),
    ODOMETER_SORT(null, "vehicles-form:vehicles:odometerheader:sortDiv"),
    OVERALL_SCORE_SORT(null,"vehicles-form:vehicles:overallScoreheader:sortDiv"),          
    STYLE_SCORE_SORT(null,"vehicles-form:vehicles:styleScoreheader:sortDiv"),              
    SPEED_SCORE_SORT(null,"vehicles-form:vehicles:speedScoreheader:sortDiv"),
        
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
