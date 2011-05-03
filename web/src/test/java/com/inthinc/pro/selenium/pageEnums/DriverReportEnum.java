package com.inthinc.pro.selenium.pageEnums;

import java.util.List;

import com.inthinc.pro.automation.enums.SeleniumEnum;
import com.inthinc.pro.automation.enums.SeleniumEnum.SeleniumEnums;

public enum DriverReportEnum implements SeleniumEnums {
    TEAM_SEARCH(null,"drivers-form:drivers:groupfsp",null,null,null),
    DRIVER_SEARCH(null,"drivers-form:drivers:fullNamefsp",null,null,null),
    VEHICLE_SEARCH(null,"drivers-form:drivers:vehiclenamefsp",null,null,null),
    EMPLOYEE_SEARCH(null,"drivers-form:drivers:empidfsp",null,null,null),
    OVERALL_SCORE_FILTER(null,null,"//div[@id='drivers-form:drivers:overallScoreheader:sortDiv']/span/span/span/div/img",null,null),
    SPEED_SCORE_FILTER(null,null,"//div[@id='drivers-form:drivers:speedScoreheader:sortDiv']/span/span/span/div/img",null,null),
    STYLE_SCORE_FILTER(null,null,"//div[@id='drivers-form:drivers:styleScoreheader:sortDiv']/span/span/span/div/img",null,null),
    SEATBELT_SCORE_FILTER(null,null,"//div[@id='drivers-form:drivers:seatbeltScoreheader:sortDiv']/span/span/span/div/img",null,null),
    DRIVER_FORM(null,"drivers-form",null,null,null),
    TEAM(null,"drivers-form:drivers:###:driversDashboard",null,null,null),
    DRIVER(null,"drivers-form:drivers:###:driversDriverPerformance",null,null,null),
    VEHICLE(null,"drivers-form:drivers:###:driversVehiclePerformance",null,null,null),
    OVERALL(null,"drivers-form:drivers:###:overallScore",null,null,null),        // not correct search term
    STYLE(null,"drivers-form:drivers:###:styleScore",null,null,null),            // not correct search term
    SEATBELT(null,"drivers-form:drivers:###:seatbeltScore",null,null,null)       // not correct search term
    ;
    
    private String text, ID, xpath, xpath_alt, url;
    
    private DriverReportEnum( String text, String ID, String xpath, String xpath_alt, String url) {
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
        return SeleniumEnum.locators(this);
    }
    
    @Override
    public  DriverReportEnum replaceNumber(String number) {
        ID = ID.replace("###", number);
        xpath = xpath.replace("###", number);
        xpath_alt = xpath_alt.replace("###", number);
        return this;
    }

    @Override
    public  DriverReportEnum replaceWord(String word) {
        ID = ID.replace("***", word);
        xpath = xpath.replace("***", word);
        xpath_alt = xpath_alt.replace("***", word);
        return this;
    }
}
