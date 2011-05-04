package com.inthinc.pro.selenium.pageEnums;



import com.inthinc.pro.automation.enums.SeleniumEnums;

import com.inthinc.pro.automation.utils.Xpath;

public enum DriverReportEnum implements SeleniumEnums {
    TEAM_SEARCH(null,"drivers-form:drivers:groupfsp"),
    DRIVER_SEARCH(null,"drivers-form:drivers:fullNamefsp"),
    VEHICLE_SEARCH(null,"drivers-form:drivers:vehiclenamefsp"),
    EMPLOYEE_SEARCH(null,"drivers-form:drivers:empidfsp"),
    
    OVERALL_SCORE_FILTER(null,null,"//div[@id='drivers-form:drivers:overallScoreheader:sortDiv']/span/span/span/div/img"),
    SPEED_SCORE_FILTER(null,null,"//div[@id='drivers-form:drivers:speedScoreheader:sortDiv']/span/span/span/div/img"),
    STYLE_SCORE_FILTER(null,null,"//div[@id='drivers-form:drivers:styleScoreheader:sortDiv']/span/span/span/div/img"),
    SEATBELT_SCORE_FILTER(null,null,"//div[@id='drivers-form:drivers:seatbeltScoreheader:sortDiv']/span/span/span/div/img"),
    DRIVER_FORM(null,"drivers-form"),
    
    TEAM(null,"drivers-form:drivers:###:driversDashboard"),
    DRIVER(null,"drivers-form:drivers:###:driversDriverPerformance"),
    VEHICLE(null,"drivers-form:drivers:###:driversVehiclePerformance"),
    
    OVERALL(null,"drivers-form:drivers:###:overallScore"),        // not correct search term
    STYLE(null,"drivers-form:drivers:###:styleScore"),            // not correct search term
    SEATBELT(null,"drivers-form:drivers:###:seatbeltScore")       // not correct search term
    ;
    
    private String text, ID, xpath, xpath_alt, url;
    
    private DriverReportEnum(String text, String ID, String xpath, String xpath_alt) {
        this.text = text;
        this.ID = ID;
        this.xpath = xpath;
        this.xpath_alt = xpath_alt;
    }

    private DriverReportEnum(String url) {
        this.url = url;
    }

    private DriverReportEnum(String text, String ID) {
        this(text, ID, "", null);
    }

    private DriverReportEnum(String text, String ID, String xpath) {
        this(text, ID, xpath, null);
    }

    private DriverReportEnum(String text, String ID, Xpath xpath, Xpath xpath_alt) {
        this(text, ID, xpath.toString(), xpath_alt.toString());
    }

    private DriverReportEnum(String text, String ID, Xpath xpath) {
        this(text, ID, xpath.toString(), null);
    }

    private DriverReportEnum(String text, Xpath xpath) {
        this(text, null, xpath.toString(), null);
    }

    private DriverReportEnum(Xpath xpath, Xpath xpath_alt) {
        this(null, null, xpath.toString(), xpath_alt.toString());
    }

    private DriverReportEnum(Xpath xpath) {
        this(null, null, xpath.toString(), null);
    }

    @Override
    public String getID() {
        // TODO Auto-generated method stub
        return this.ID;
    }

    @Override
    public String getText() {
        // TODO Auto-generated method stub
        return this.text;
    }

    @Override
    public String getXpath() {
        // TODO Auto-generated method stub
        return this.xpath;
    }

    @Override
    public String getXpath_alt() {
        // TODO Auto-generated method stub
        return this.xpath_alt;
    }

    @Override
    public void setText(String text) {
        // TODO Auto-generated method stub
        this.text=text;
    }

    @Override
    public String getURL() {
        // TODO Auto-generated method stub
        return this.url;
    }


}
