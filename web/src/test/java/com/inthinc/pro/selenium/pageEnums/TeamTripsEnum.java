package com.inthinc.pro.selenium.pageEnums;

import java.util.List;

import com.inthinc.pro.automation.enums.SeleniumEnumUtil;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum TeamTripsEnum implements SeleniumEnums {
    
    DRIVER_HEADER("Driver Name", Xpath.start().table(Id.id("tripsTableForm:driversTrips")).thead().tr().th("2")),
    
    CHECK_BOX_ENTRY(null, "tripsTableForm:driversTrips:###:checkDriver"),
    DRIVER_NAME(null, "tripsTableForm:driversTrips:###:teamTrips-driverPerformance"),
    DRIVER_LETTER(Xpath.start().tr("###").td("3").div().div(Id.clazz("trips_label"))),
    
    ;
    

    private String text, ID, xpath, xpath_alt, url;

    private TeamTripsEnum(String text, String ID, String xpath, String xpath_alt) {
        this.text = text;
        this.ID = ID;
        this.xpath = xpath;
        this.xpath_alt = xpath_alt;
    }

    private TeamTripsEnum(String url) {
        this.url = url;
    }
    
    private TeamTripsEnum( String text, String ID) {
        this(text, ID, "", null);
    }
    private TeamTripsEnum( String text, String ID, String xpath) {
        this(text, ID, xpath, null);
    }
    
    private TeamTripsEnum(String text, String ID, Xpath xpath, Xpath xpath_alt){
        this(text, ID, xpath.toString(), xpath_alt.toString());
    }
    
    private TeamTripsEnum(String text, String ID, Xpath xpath){
        this(text, ID, xpath.toString(), null);
    }
    
    private TeamTripsEnum(String text, Xpath xpath){
        this(text, null, xpath.toString(), null);
    }
    
    private TeamTripsEnum( Xpath xpath, Xpath xpath_alt) {
        this(null, null, xpath.toString(), xpath_alt.toString());
    }
    
    private TeamTripsEnum( Xpath xpath) {
        this(null, null, xpath.toString(), null);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getID() {
        return ID;
    }

    public String getXpath() {
        return xpath;
    }

    public String getXpath_alt() {
        return xpath_alt;
    }

    public String getURL() {
        return url;
    }
    @Override
    public List<String> getLocators() {        
        return SeleniumEnumUtil.getLocators(this);
    }
}
