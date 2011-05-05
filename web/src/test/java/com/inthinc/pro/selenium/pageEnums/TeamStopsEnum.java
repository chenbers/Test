package com.inthinc.pro.selenium.pageEnums;



import org.ajax4jsf.xml.serializer.ToSAXHandler;

import com.inthinc.pro.automation.enums.SeleniumEnums;

import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum TeamStopsEnum implements SeleniumEnums {

    /* Driver Selection */
    DRIVER_HEADER("Driver Name", null, Xpath.start().table(Id.id("stopsTableForm:stopsDrivers:4")).thead().tr().th("2").toString(), null),

    DRIVER_RADIO_BUTTON(null, "stopsTableForm:stopsDrivers:###:stopsCheckDriver:***",null, null ),
    DRIVER_NAME(null, "stopsTableForm:stopsDrivers:###:teamStops-driverPerformance",null, null ),
    DRIVER_LETTER(null, null, Xpath.start().tr("***").td("3").div().div(Id.clazz("trips_label")).toString(), null),

    /* Totals Section */
    TOTAL_SUPERHEADER("Total Time At Stop(s)",null, Xpath.start().table(Id.id("stopsTripsTableForm:stopsTripsSummary")).thead().tr("1").th("5").toString(), null),
    TOTAL_TOTAL_HEADER("Total",null, Xpath.start().table(Id.id("stopsTripsTableForm:stopsTripsSummary")).thead().tr("2").th("1").toString(), null),
    TOTAL_LOW_IDLE_HEADER("Low Idle",null, Xpath.start().table(Id.id("stopsTripsTableForm:stopsTripsSummary")).thead().tr("2").th("2").toString(), null),
    TOTAL_HIGH_IDLE_HEADER("High Idle",null, Xpath.start().table(Id.id("stopsTripsTableForm:stopsTripsSummary")).thead().tr("2").th("3").toString(), null),
    TOTAL_WAIT_HEADER("Wait",null, Xpath.start().table(Id.id("stopsTripsTableForm:stopsTripsSummary")).thead().tr("2").th("4").toString(), null),
    TOTAL_DURATION_HEADER("Duration", null, Xpath.start().table(Id.id("stopsTripsTableForm:stopsTripsSummary")).thead().tr("1").th("6").toString(), null),

    TOTAL_TOTAL_ENTRY(null, null, Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTripsSummary:tb")).tr().td("5").toString(), null),
    TOTAL_LOW_IDLE_ENTRY(null, null, Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTripsSummary:tb")).tr().td("6").toString(), null),
    TOTAL_HIGH_IDLE_ENTRY(null, null, Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTripsSummary:tb")).tr().td("7").toString(), null),
    TOTAL_WAIT_ENTRY(null, null, Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTripsSummary:tb")).tr().td("8").toString(), null),
    TOTAL_DURATION_ENTRY(null, null, Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTripsSummary:tb")).tr().td("9").toString(), null),

    /* Detailed Section */
    DETAILED_SUPERHEADER("Time at Stop",null, Xpath.start().table(Id.id("stopsTripsTableForm:stopsTrips")).thead().tr("1").th("5").toString(), null),

    ZONE_HEADER("Zone",null, Xpath.start().table(Id.id("stopsTripsTableForm:stopsTrips")).thead().tr("1").th("1").toString(), null),
    LOCATION_HEADER("Stop Location",null, Xpath.start().table(Id.id("stopsTripsTableForm:stopsTrips")).thead().tr("1").th("2").toString(), null),
    ARRIVE_HEADER("Arrive",null, Xpath.start().table(Id.id("stopsTripsTableForm:stopsTrips")).thead().tr("1").th("3").toString(), null),
    DEPART_HEADER("Depart",null, Xpath.start().table(Id.id("stopsTripsTableForm:stopsTrips")).thead().tr("1").th("4").toString(), null),
    TOTAL_HEADER("Total",null, Xpath.start().table(Id.id("stopsTripsTableForm:stopsTrips")).thead().tr("2").th("1").toString(), null),
    LOW_IDLE_HEADER("Low Idle",null, Xpath.start().table(Id.id("stopsTripsTableForm:stopsTrips")).thead().tr("2").th("2").toString(), null),
    HIGH_IDLE_HEADER("High Idle",null, Xpath.start().table(Id.id("stopsTripsTableForm:stopsTrips")).thead().tr("2").th("3").toString(), null),
    WAIT_HEADER("Wait",null, Xpath.start().table(Id.id("stopsTripsTableForm:stopsTrips")).thead().tr("2").th("4").toString(), null),
    DURATION_HEADER("Duration",null, Xpath.start().table(Id.id("stopsTripsTableForm:stopsTrips")).thead().tr("1").th("6").toString(), null),
    
    ZONE_ENTRY(null, null, Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTrips:tb")).tr("###").td("1").toString(), null),
    LOCATION_ENTRY(null, "stopsTripsTableForm:stopsTrips:###:address_column", null, null),
    ARRIVE_ENTRY(null, null, Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTrips:tb")).tr("###").td("3").toString(), null),
    DEPART_ENTRY(null, null, Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTrips:tb")).tr("###").td("4").toString(), null),
    TOTAL_ENTRY(null, null, Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTrips:tb")).tr("###").td("5").toString(), null),
    LOW_IDLE_ENTRY(null, null, Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTrips:tb")).tr("###").td("6").toString(), null),
    HIGH_IDLE_ENTRY(null, null, Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTrips:tb")).tr("###").td("7").toString(), null),
    WAIT_ENTRY(null, null, Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTrips:tb")).tr("###").td("8").toString(), null),
    DURATION_ENTRY(null, null, Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTrips:tb")).tr("###").td("9").toString(), null),

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
