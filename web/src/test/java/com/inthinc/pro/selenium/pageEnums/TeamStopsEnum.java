package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum TeamStopsEnum implements SeleniumEnums {

    /* Export */
    EXPORT_TOOLS("", Xpath.start().span(Id.id("stopsReport:teamStops_reportToolImageId")).span("2").toString()),
    
    EXPORT_PDF_TOOL(exportPDF, "stopsReport:teamStops-export_menu_item:anchor"),
    EXPORT_EMAIL_TOOL(emailReport, "stopsReport:teamStops-emailMenuItem:anchor"),
    EXPORT_EXCEL_TOOL(exportExcel, "stopsReport:teamStops-exportExcelMEnuItem:anchor"),
    
    /* Driver Selection */
    DRIVER_HEADER("Driver Name", Xpath.start().table(Id.id("stopsTableForm:stopsDrivers:4")).thead().tr().th("2").toString()),

    DRIVER_RADIO_BUTTON(null, "stopsTableForm:stopsDrivers:###:stopsCheckDriver:***",null ),
    DRIVER_NAME(null, "stopsTableForm:stopsDrivers:###:teamStops-driverPerformance",null ),
    DRIVER_LETTER(null, Xpath.start().tr("***").td("3").div().div(Id.clazz("trips_label")).toString()),

    /* Totals Section */
    TOTAL_SUPERHEADER("Total Time At Stop(s)",null, Xpath.start().table(Id.id("stopsTripsTableForm:stopsTripsSummary")).thead().tr("1").th("5").toString()),
    TOTAL_TOTAL_HEADER("Total",null, Xpath.start().table(Id.id("stopsTripsTableForm:stopsTripsSummary")).thead().tr("2").th("1").toString()),
    TOTAL_LOW_IDLE_HEADER("Low Idle",null, Xpath.start().table(Id.id("stopsTripsTableForm:stopsTripsSummary")).thead().tr("2").th("2").toString()),
    TOTAL_HIGH_IDLE_HEADER("High Idle",null, Xpath.start().table(Id.id("stopsTripsTableForm:stopsTripsSummary")).thead().tr("2").th("3").toString()),
    TOTAL_WAIT_HEADER("Wait",null, Xpath.start().table(Id.id("stopsTripsTableForm:stopsTripsSummary")).thead().tr("2").th("4").toString()),
    TOTAL_DURATION_HEADER("Duration", Xpath.start().table(Id.id("stopsTripsTableForm:stopsTripsSummary")).thead().tr("1").th("6").toString()),

    TOTAL_TOTAL_ENTRY(null, Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTripsSummary:tb")).tr().td("5").toString()),
    TOTAL_LOW_IDLE_ENTRY(null, Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTripsSummary:tb")).tr().td("6").toString()),
    TOTAL_HIGH_IDLE_ENTRY(null, Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTripsSummary:tb")).tr().td("7").toString()),
    TOTAL_WAIT_ENTRY(null, Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTripsSummary:tb")).tr().td("8").toString()),
    TOTAL_DURATION_ENTRY(null, Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTripsSummary:tb")).tr().td("9").toString()),

    /* Detailed Section */
    DETAILED_SUPERHEADER("Time at Stop",null, Xpath.start().table(Id.id("stopsTripsTableForm:stopsTrips")).thead().tr("1").th("5").toString()),

    ZONE_HEADER("Zone",null, Xpath.start().table(Id.id("stopsTripsTableForm:stopsTrips")).thead().tr("1").th("1").toString()),
    LOCATION_HEADER("Stop Location",null, Xpath.start().table(Id.id("stopsTripsTableForm:stopsTrips")).thead().tr("1").th("2").toString()),
    ARRIVE_HEADER("Arrive",null, Xpath.start().table(Id.id("stopsTripsTableForm:stopsTrips")).thead().tr("1").th("3").toString()),
    DEPART_HEADER("Depart",null, Xpath.start().table(Id.id("stopsTripsTableForm:stopsTrips")).thead().tr("1").th("4").toString()),
    TOTAL_HEADER("Total",null, Xpath.start().table(Id.id("stopsTripsTableForm:stopsTrips")).thead().tr("2").th("1").toString()),
    LOW_IDLE_HEADER("Low Idle",null, Xpath.start().table(Id.id("stopsTripsTableForm:stopsTrips")).thead().tr("2").th("2").toString()),
    HIGH_IDLE_HEADER("High Idle",null, Xpath.start().table(Id.id("stopsTripsTableForm:stopsTrips")).thead().tr("2").th("3").toString()),
    WAIT_HEADER("Wait",null, Xpath.start().table(Id.id("stopsTripsTableForm:stopsTrips")).thead().tr("2").th("4").toString()),
    DURATION_HEADER("Duration",null, Xpath.start().table(Id.id("stopsTripsTableForm:stopsTrips")).thead().tr("1").th("6").toString()),
    
    ZONE_ENTRY(null, Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTrips:tb")).tr("###").td("1").toString()),
    LOCATION_ENTRY(null, "stopsTripsTableForm:stopsTrips:###:address_column"),
    ARRIVE_ENTRY(null, Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTrips:tb")).tr("###").td("3").toString()),
    DEPART_ENTRY(null, Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTrips:tb")).tr("###").td("4").toString()),
    TOTAL_ENTRY(null, Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTrips:tb")).tr("###").td("5").toString()),
    LOW_IDLE_ENTRY(null, Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTrips:tb")).tr("###").td("6").toString()),
    HIGH_IDLE_ENTRY(null, Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTrips:tb")).tr("###").td("7").toString()),
    WAIT_ENTRY(null, Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTrips:tb")).tr("###").td("8").toString()),
    DURATION_ENTRY(null, Xpath.start().tbody(Id.id("stopsTripsTableForm:stopsTrips:tb")).tr("###").td("9").toString()),
    
    VEHICLE_LINK(null, "stopsTripsTableForm:stopsTrips:###:j_id635")
    ;
    private String text, url;
    private String[] IDs;
    
    private TeamStopsEnum(String url){
    	this.url = url;
    }
    private TeamStopsEnum(String text, String ...IDs){
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
