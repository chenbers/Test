package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum ReportsBarEnum implements SeleniumEnums {

    DRIVERS("Drivers", "subNavForm:drivers-driversReport"),
    VEHICLES("Vehicles", "subNavForm:drivers-vehiclesReport"),
    IDLING_DRIVERS("Idling Drivers", "subNavForm:drivers-idlingReport"),
    IDLING_VEHICLES("Idling Vehicles","subNavForm:drivers-idlingVehicleReport"),
    DEVICES("Devices", "subNavForm:drivers-devicesReport"),
    PERFORMANCE("Performance","subNavForm:drivers-performanceReport"),
    WAYSMART("waySmart", "subNavForm:drivers-waysmartReport"),

    OVERALL_SCORE_DHX(null, "//input[@name='***-form:***:overallScoreFilter']"),
    SPEED_SCORE_DHX(null, "//input[@name='***-form:***:speedScoreFilter']"),
    STYLE_SCORE_DHX(null, "//input[@name='***-form:***:styleScoreFilter']"),
    SEATBELT_SCORE_DHX(null, "//input[@name='***-form:***:seatbeltScoreFilter']"),

    OVERALL_SCORE_SORT(null, "***-form:***:overallScoreheader:sortDiv"),
    SPEED_SCORE_SORT(null, "***-form:***:speedScoreheader:sortDiv"),
    STYLE_SCORE_SORT(null, "***-form:***:styleScoreheader:sortDiv"),
    SEATBELT_SCORE_SORT(null, "***-form:***:seatbeltScoreheader:sortDiv"),
    
    TOOL_EMAIL("E-mail This Report", "***-form:***-emailMenuItem"),
    TOOL_PDF("Export To PDF", "***-form:***-export_menu_item:anchor"),
    TOOL_EXCEL("Export To Excel", "***-form:***-exportExcelMEnuItem:icon"),
    
    COUNTER("Showing XXX to YYY of ZZZ records", "***-form:header"), 

    ;

    private String text, url;
    private String[] IDs;
    
    private ReportsBarEnum(String url){
    	this.url = url;
    }
    private ReportsBarEnum(String text, String ...IDs){
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
