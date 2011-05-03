package com.inthinc.pro.selenium.pageEnums;

import java.util.List;

import com.inthinc.pro.automation.enums.SeleniumEnum;
import com.inthinc.pro.automation.enums.SeleniumEnum.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum TeamStopsEnum implements SeleniumEnums {

    /* Driver Selection */
    DRIVER_HEADER("Driver Name", Xpath.start().table(Id.id("stopsTableForm:stopsDrivers:4")).thead().tr().th("2")),

    DRIVER_RADIO_BUTTON(null, "stopsTableForm:stopsDrivers:###:stopsCheckDriver:***"),
    DRIVER_NAME(null, "stopsTableForm:stopsDrivers:###:teamStops-driverPerformance"),
    DRIVER_LETTER(Xpath.start().tr("***").td("3").div().div(Id.clazz("trips_label"))),

    /* Totals Section */
    TOTAL_SUPERHEADER("Total Time At Stop(s)", Xpath.start().table(Id.id("stopsTripsTableForm:stopsTripsSummary")).thead().tr("1").th("5")),
    TOTAL_TOTAL_HEADER("Total", Xpath.start().table(Id.id("stopsTripsTableForm:stopsTripsSummary")).thead().tr("2").th("1")),
    TOTAL_LOW_IDLE_HEADER("Low Idle", Xpath.start().table(Id.id("stopsTripsTableForm:stopsTripsSummary")).thead().tr("2").th("2")),
    TOTAL_HIGH_IDLE_HEADER("High Idle", Xpath.start().table(Id.id("stopsTripsTableForm:stopsTripsSummary")).thead().tr("2").th("3")),
    TOTAL_WAIT_HEADER("Wait", Xpath.start().table(Id.id("stopsTripsTableForm:stopsTripsSummary")).thead().tr("2").th("4")),
    TOTAL_DURATION_HEADER("Duration", Xpath.start().table(Id.id("stopsTripsTableForm:stopsTripsSummary")).thead().tr("1").th("6")),

    TOTAL_TOTAL_ENTRY(Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTripsSummary:tb")).tr().td("5")),
    TOTAL_LOW_IDLE_ENTRY(Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTripsSummary:tb")).tr().td("6")),
    TOTAL_HIGH_IDLE_ENTRY(Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTripsSummary:tb")).tr().td("7")),
    TOTAL_WAIT_ENTRY(Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTripsSummary:tb")).tr().td("8")),
    TOTAL_DURATION_ENTRY(Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTripsSummary:tb")).tr().td("9")),

    /* Detailed Section */
    DETAILED_SUPERHEADER("Time at Stop", Xpath.start().table(Id.id("stopsTripsTableForm:stopsTrips")).thead().tr("1").th("5")),

    ZONE_HEADER("Zone", Xpath.start().table(Id.id("stopsTripsTableForm:stopsTrips")).thead().tr("1").th("1")),
    LOCATION_HEADER("Stop Location", Xpath.start().table(Id.id("stopsTripsTableForm:stopsTrips")).thead().tr("1").th("2")),
    ARRIVE_HEADER("Arrive", Xpath.start().table(Id.id("stopsTripsTableForm:stopsTrips")).thead().tr("1").th("3")),
    DEPART_HEADER("Depart", Xpath.start().table(Id.id("stopsTripsTableForm:stopsTrips")).thead().tr("1").th("4")),
    TOTAL_HEADER("Total", Xpath.start().table(Id.id("stopsTripsTableForm:stopsTrips")).thead().tr("2").th("1")),
    LOW_IDLE_HEADER("Low Idle", Xpath.start().table(Id.id("stopsTripsTableForm:stopsTrips")).thead().tr("2").th("2")),
    HIGH_IDLE_HEADER("High Idle", Xpath.start().table(Id.id("stopsTripsTableForm:stopsTrips")).thead().tr("2").th("3")),
    WAIT_HEADER("Wait", Xpath.start().table(Id.id("stopsTripsTableForm:stopsTrips")).thead().tr("2").th("4")),
    DURATION_HEADER("Duration", Xpath.start().table(Id.id("stopsTripsTableForm:stopsTrips")).thead().tr("1").th("6")),
    
    ZONE_ENTRY(Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTrips:tb")).tr("###").td("1")),
    LOCATION_ENTRY(null, "stopsTripsTableForm:stopsTrips:###:address_column"),
    ARRIVE_ENTRY(Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTrips:tb")).tr("###").td("3")),
    DEPART_ENTRY(Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTrips:tb")).tr("###").td("4")),
    TOTAL_ENTRY(Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTrips:tb")).tr("###").td("5")),
    LOW_IDLE_ENTRY(Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTrips:tb")).tr("###").td("6")),
    HIGH_IDLE_ENTRY(Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTrips:tb")).tr("###").td("7")),
    WAIT_ENTRY(Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTrips:tb")).tr("###").td("8")),
    DURATION_ENTRY(Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTrips:tb")).tr("###").td("9")),

    ;

    private String text, ID, xpath, xpath_alt, url;

    private TeamStopsEnum(String text, String ID, String xpath, String xpath_alt) {
        this.text = text;
        this.ID = ID;
        this.xpath = xpath;
        this.xpath_alt = xpath_alt;
    }

    private TeamStopsEnum(String url) {
        this.url = url;
    }

    private TeamStopsEnum(String text, String ID) {
        this(text, ID, "", null);
    }

    private TeamStopsEnum(String text, String ID, String xpath) {
        this(text, ID, xpath, null);
    }

    private TeamStopsEnum(String text, String ID, Xpath xpath, Xpath xpath_alt) {
        this(text, ID, xpath.toString(), xpath_alt.toString());
    }

    private TeamStopsEnum(String text, String ID, Xpath xpath) {
        this(text, ID, xpath.toString(), null);
    }

    private TeamStopsEnum(String text, Xpath xpath) {
        this(text, null, xpath.toString(), null);
    }

    private TeamStopsEnum(Xpath xpath, Xpath xpath_alt) {
        this(null, null, xpath.toString(), xpath_alt.toString());
    }

    private TeamStopsEnum(Xpath xpath) {
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
        return SeleniumEnum.locators(this);
    }
    
    @Override
    public  TeamStopsEnum replaceNumber(String number) {
        ID = ID.replace("###", number);
        xpath = xpath.replace("###", number);
        xpath_alt = xpath_alt.replace("###", number);
        return this;
    }

    @Override
    public  TeamStopsEnum replaceWord(String word) {
        ID = ID.replace("***", word);
        xpath = xpath.replace("***", word);
        xpath_alt = xpath_alt.replace("***", word);
        return this;
    }
}
