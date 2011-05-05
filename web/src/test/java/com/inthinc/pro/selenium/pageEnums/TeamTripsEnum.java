package com.inthinc.pro.selenium.pageEnums;



import com.inthinc.pro.automation.enums.SeleniumEnums;

import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum TeamTripsEnum implements SeleniumEnums {
    
    DRIVER_HEADER("Driver Name", null, Xpath.start().table(Id.id("tripsTableForm:driversTrips")).thead().tr().th("2").toString(), null),
    
    CHECK_BOX_ENTRY(null, "tripsTableForm:driversTrips:###:checkDriver"),
    DRIVER_NAME(null, "tripsTableForm:driversTrips:###:teamTrips-driverPerformance"),
    DRIVER_LETTER(null, null, Xpath.start().tr("###").td("3").div().div(Id.clazz("trips_label")).toString(), null),
    
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
}
