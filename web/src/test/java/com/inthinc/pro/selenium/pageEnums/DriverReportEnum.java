package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

import com.inthinc.pro.automation.utils.Xpath;

public enum DriverReportEnum implements SeleniumEnums {
    TEAM_SEARCH(null, "drivers-form:drivers:groupfsp", null, null),
    DRIVER_SEARCH(null, "drivers-form:drivers:fullNamefsp", null, null),
    VEHICLE_SEARCH(null, "drivers-form:drivers:vehiclenamefsp", null, null),
    EMPLOYEE_SEARCH(null, "drivers-form:drivers:empidfsp", null, null),

    OVERALL_SCORE_FILTER(null, null, "//div[@id='drivers-form:drivers:overallScoreheader:sortDiv']/span/span/span/div/img", null),
    SPEED_SCORE_FILTER(null, null, "//div[@id='drivers-form:drivers:speedScoreheader:sortDiv']/span/span/span/div/img", null),
    STYLE_SCORE_FILTER(null, null, "//div[@id='drivers-form:drivers:styleScoreheader:sortDiv']/span/span/span/div/img", null),
    SEATBELT_SCORE_FILTER(null, null, "//div[@id='drivers-form:drivers:seatbeltScoreheader:sortDiv']/span/span/span/div/img", null),
    DRIVER_FORM(null, "drivers-form", null, null),

    TEAM(null, "drivers-form:drivers:###:driversDashboard", null, null),
    DRIVER(null, "drivers-form:drivers:###:driversDriverPerformance", null, null),
    VEHICLE(null, "drivers-form:drivers:###:driversVehiclePerformance", null, null),

    OVERALL(null, "drivers-form:drivers:###:overallScore", null, null), // not correct search term
    STYLE(null, "drivers-form:drivers:###:styleScore", null, null), // not correct search term
    SEATBELT(null, "drivers-form:drivers:###:seatbeltScore", null, null) // not correct search term
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

    @Override
    public String getID() {
        return this.ID;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public String getXpath() {
        return this.xpath;
    }

    @Override
    public String getXpath_alt() {
        return this.xpath_alt;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getURL() {
        return this.url;
    }

}
