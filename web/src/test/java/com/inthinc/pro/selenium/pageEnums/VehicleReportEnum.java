package com.inthinc.pro.selenium.pageEnums;

import java.util.List;

import com.inthinc.pro.automation.enums.SeleniumEnumUtil;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum VehicleReportEnum implements SeleniumEnums {
    TEAM_SEARCH(null,"vehicles-form:vehicles:groupfsp",null,null,null),
    DRIVER_SEARCH(null,"vehicles-form:vehicles:fullNamefsp",null,null,null),
    VEHICLE_SEARCH(null,"vehicles-form:vehicles:namefsp",null,null,null),
    YEAR_MAKE_MODEL_SEARCH(null,"vehicles-form:vehicles:makeModelYearfsp",null,null,null),
    OVERALL_SCORE_FILTER(null,null,"//div[@id='vehicles-form:vehicles:overallScoreheader:sortDiv']/span/span/span/div/img",null,null),
    SPEED_SCORE_FILTER(null,null,"//div[@id='vehicles-form:vehicles:speedScoreheader:sortDiv']/span/span/span/div/img",null,null),
    STYLE_SCORE_FILTER(null,null,"//div[@id='vehicles-form:vehicles:styleScoreheader:sortDiv']/span/span/span/div/img",null,null),
    DRIVER_FORM(null,"vehicles-form",null,null,null),
    TEAM(null,"vehicles-form:vehicles:0:vehiclesDashboard",null,null,null),
    DRIVER(null,"vehicles-form:vehicles:2:vehiclesDriverPerformance",null,null,null),
    VEHICLE(null,"vehicles-form:vehicles:0:vehiclesVehiclePerformance",null,null,null),
    OVERALL(null,"vehicles-form:drivers:0:overallScore",null,null,null),          // not correct search term
    STYLE(null,"vehicles-form:drivers:0:styleScore",null,null,null),              // not correct search term
    SEATBELT(null,"vehicles-form:drivers:0:seatbeltScore",null,null,null)         // not correct search term
    ;
    
    private String text, ID, xpath, xpath_alt, url;
    
    private VehicleReportEnum( String text, String ID, String xpath, String xpath_alt, String url) {
        this.text=text;
        this.ID=ID;
        this.xpath=xpath;
        this.xpath_alt=xpath_alt;
        this.url=url;
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
    @Override
    public List<String> getLocators() {        
        return SeleniumEnumUtil.getLocators(this);
    }
}    
