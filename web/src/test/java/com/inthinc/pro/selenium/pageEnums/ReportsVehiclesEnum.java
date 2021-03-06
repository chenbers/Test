package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum ReportsVehiclesEnum implements SeleniumEnums {

    DEFAULT_URL(appUrl + "/reports/vehiclesReport"),
	
	TITLE("Vehicle Report", "//span[@class='vehicle']"),
	
    GROUP_FILTER(null,"vehicles-form:vehicles:groupfsp"),
    DRIVER_FILTER(null,"vehicles-form:vehicles:fullNamefsp"),
    VEHICLE_FILTER(null,"vehicles-form:vehicles:namefsp"),
    YEAR_MAKE_MODEL_FILTER(null,"vehicles-form:vehicles:makeModelYearfsp"),
    

    GROUP_VALUE(null,"vehicles-form:vehicles:###:group"),
    VEHICLE_VALUE(null,"vehicles-form:vehicles:###:name"),
    YEAR_MAKE_MODEL_VALUE(null, "vehicles-form:vehicles:###:makeModelYear"),
    DRIVER_VALUE(null,"vehicles-form:vehicles:###:fullName"),
    DISTANCE_DRIVEN_VALUE(null, "vehicles-form:vehicles:###:distance"),
    ODOMETER_VALUE(null, "vehicles-form:vehicles:###:odometer"),
    OVERALL_SCORE_VALUE(null,"vehicles-form:vehicles:###:overallScore"),   
    STYLE_SCORE_VALUE(null,"vehicles-form:vehicles:###:styleScore"),              
    SPEED_SCORE_VALUE(null,"vehicles-form:vehicles:###:speedScore"),
    
    
    GROUP_SORT(null,"vehicles-form:vehicles:groupheader:sortDiv"),
    VEHICLE_SORT(null,"vehicles-form:vehicles:nameheader:sortDiv"),
    YEAR_MAKE_MODEL(null, "vehicles-form:vehicles:makeModelYearheader:sortDiv"),
    DRIVER_SORT(null,"vehicles-form:vehicles:fullNameheader:sortDiv"),
    DISTANCE_DRIVEN_SORT(null, "vehicles-form:vehicles:distanceheader:sortDiv"),
    ODOMETER_SORT(null, "vehicles-form:vehicles:odometerheader:sortDiv"),
    OVERALL_SCORE_SORT(null,"vehicles-form:vehicles:overallScoreheader:sortDiv"),          
    STYLE_SCORE_SORT(null,"vehicles-form:vehicles:styleScoreheader:sortDiv"),              
    SPEED_SCORE_SORT(null,"vehicles-form:vehicles:speedScoreheader:sortDiv"),
        
    ;
    
    private String text, url;
    private String[] IDs;
    
    private ReportsVehiclesEnum(String url){
    	this.url = url;
    }
    private ReportsVehiclesEnum(String text, String ...IDs){
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