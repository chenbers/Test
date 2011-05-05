package com.inthinc.pro.selenium.pageEnums;



import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.utils.Xpath;



public enum VehicleReportEnum implements SeleniumEnums {
    TEAM_SEARCH(null,"vehicles-form:vehicles:groupfsp"),
    DRIVER_SEARCH(null,"vehicles-form:vehicles:fullNamefsp"),
    VEHICLE_SEARCH(null,"vehicles-form:vehicles:namefsp"),
    YEAR_MAKE_MODEL_SEARCH(null,"vehicles-form:vehicles:makeModelYearfsp"),
    OVERALL_SCORE_FILTER(null,null,"//div[@id='vehicles-form:vehicles:overallScoreheader:sortDiv']/span/span/span/div/img"),
    SPEED_SCORE_FILTER(null,null,"//div[@id='vehicles-form:vehicles:speedScoreheader:sortDiv']/span/span/span/div/img"),
    STYLE_SCORE_FILTER(null,null,"//div[@id='vehicles-form:vehicles:styleScoreheader:sortDiv']/span/span/span/div/img"),
    DRIVER_FORM(null,"vehicles-form"),
    TEAM(null,"vehicles-form:vehicles:0:vehiclesDashboard"),
    DRIVER(null,"vehicles-form:vehicles:2:vehiclesDriverPerformance"),
    VEHICLE(null,"vehicles-form:vehicles:0:vehiclesVehiclePerformance"),
    OVERALL(null,"vehicles-form:drivers:0:overallScore"),          // not correct search term
    STYLE(null,"vehicles-form:drivers:0:styleScore"),              // not correct search term
    SEATBELT(null,"vehicles-form:drivers:0:seatbeltScore"),         // not correct search term
    ;
    
    private String text, ID, xpath, xpath_alt, url;
    
    private VehicleReportEnum(String text, String ID, String xpath, String xpath_alt) {
        this.text = text;
        this.ID = ID;
        this.xpath = xpath;
        this.xpath_alt = xpath_alt;
    }

    private VehicleReportEnum(String url) {
        this.url = url;
    }

    private VehicleReportEnum(String text, String ID) {
        this(text, ID, "", null);
    }

    private VehicleReportEnum(String text, String ID, String xpath) {
        this(text, ID, xpath, null);
    }

    private VehicleReportEnum(String text, String ID, Xpath xpath, Xpath xpath_alt) {
        this(text, ID, xpath.toString(), xpath_alt.toString());
    }

    private VehicleReportEnum(String text, String ID, Xpath xpath) {
        this(text, ID, xpath.toString(), null);
    }

    private VehicleReportEnum(String text, Xpath xpath) {
        this(text, null, xpath.toString(), null);
    }

    private VehicleReportEnum(Xpath xpath, Xpath xpath_alt) {
        this(null, null, xpath.toString(), xpath_alt.toString());
    }

    private VehicleReportEnum(Xpath xpath) {
        this(null, null, xpath.toString(), null);
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
        this.text=text;
    }

    @Override
    public String getURL() {
        return this.url;
    }
}    
