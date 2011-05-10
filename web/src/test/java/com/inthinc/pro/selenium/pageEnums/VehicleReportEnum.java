package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum VehicleReportEnum implements SeleniumEnums {
    TEAM_SEARCH(null,"vehicles-form:vehicles:groupfsp"),
    DRIVER_SEARCH(null,"vehicles-form:vehicles:fullNamefsp"),
    VEHICLE_SEARCH(null,"vehicles-form:vehicles:namefsp"),
    YEAR_MAKE_MODEL_SEARCH(null,"vehicles-form:vehicles:makeModelYearfsp"),
    
    OVERALL_SCORE_FILTER(null,"//div[@id='vehicles-form:vehicles:overallScoreheader:sortDiv']/span/span/span/div/img"),
    SPEED_SCORE_FILTER(null,"//div[@id='vehicles-form:vehicles:speedScoreheader:sortDiv']/span/span/span/div/img"),
    STYLE_SCORE_FILTER(null,"//div[@id='vehicles-form:vehicles:styleScoreheader:sortDiv']/span/span/span/div/img"),
    
    OVERALL_SCORE_DHX(null, "drivers-form:drivers:overallScoreheader:sortDiv"),
    SPEED_SCORE_DHX(null, "drivers-form:drivers:speedScoreheader:sortDiv"),
    STYLE_SCORE_DHX(null, "drivers-form:drivers:styleScoreheader:sortDiv"),
    SEATBELT_SCORE_DHX(null, "drivers-form:drivers:seatbeltScoreheader:sortDiv"),
    
    DRIVER_FORM(null,"vehicles-form"),
    TEAM(null,"vehicles-form:vehicles:0:vehiclesDashboard"),
    DRIVER(null,"vehicles-form:vehicles:2:vehiclesDriverPerformance"),
    VEHICLE(null,"vehicles-form:vehicles:0:vehiclesVehiclePerformance"),
    OVERALL(null,"vehicles-form:drivers:0:overallScore"),          // not correct search term
    STYLE(null,"vehicles-form:drivers:0:styleScore"),              // not correct search term
    SEATBELT(null,"vehicles-form:drivers:0:seatbeltScore"),         // not correct search term
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
