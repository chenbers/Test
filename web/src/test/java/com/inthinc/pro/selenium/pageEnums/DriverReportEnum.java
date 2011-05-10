package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum DriverReportEnum implements SeleniumEnums {
    TEAM_SEARCH(null, "drivers-form:drivers:groupfsp"),
    DRIVER_SEARCH(null, "drivers-form:drivers:fullNamefsp"),
    VEHICLE_SEARCH(null, "drivers-form:drivers:vehiclenamefsp"),
    EMPLOYEE_SEARCH(null, "drivers-form:drivers:empidfsp"),

    OVERALL_SCORE_DHX(null, "drivers-form:drivers:overallScoreheader:sortDiv"),
    SPEED_SCORE_DHX(null, "drivers-form:drivers:speedScoreheader:sortDiv"),
    STYLE_SCORE_DHX(null, "drivers-form:drivers:styleScoreheader:sortDiv"),
    SEATBELT_SCORE_DHX(null, "drivers-form:drivers:seatbeltScoreheader:sortDiv"),

    OVERALL_SCORE_ARROW(null, "//div[@id='drivers-form:drivers:overallScoreheader:sortDiv']/span/span/span/div/img"),
    SPEED_SCORE_ARROW(null, "//div[@id='drivers-form:drivers:speedScoreheader:sortDiv']/span/span/span/div/img"),
    STYLE_SCORE_ARROW(null, "//div[@id='drivers-form:drivers:styleScoreheader:sortDiv']/span/span/span/div/img"),
    SEATBELT_SCORE_ARROW(null, "//div[@id='drivers-form:drivers:seatbeltScoreheader:sortDiv']/span/span/span/div/img"),
    
    DRIVER_FORM(null, "drivers-form"),

    TEAM(null, "drivers-form:drivers:###:driversDashboard"),
    DRIVER(null, "drivers-form:drivers:###:driversDriverPerformance"),
    VEHICLE(null, "drivers-form:drivers:###:driversVehiclePerformance"),

    OVERALL(null, "drivers-form:drivers:###:overallScore"), // not correct search term
    STYLE(null, "drivers-form:drivers:###:styleScore"), // not correct search term
    SEATBELT(null, "drivers-form:drivers:###:seatbeltScore") // not correct search term
    ;

    private String text, url;
    private String[] IDs;
    
    private DriverReportEnum(String url){
    	this.url = url;
    }
    private DriverReportEnum(String text, String ...IDs){
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
