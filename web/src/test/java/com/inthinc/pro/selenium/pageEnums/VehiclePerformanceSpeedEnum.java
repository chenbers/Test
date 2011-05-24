package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum VehiclePerformanceSpeedEnum implements SeleniumEnums {

	 /* Overall Score */
    TIME_FRAME_SELECTOR(null, "speedForm:notificationsTable-dateLinksToolsDetail***"),
    OVERALL_TITLE("Speed: Overall", "titleSpan"),
    OVERALL_SCORE_LABEL("Overall Score", Xpath.start().div(Id.id("overallScoreBox_body")).table().tbody().tr().td("1").h4().toString()),
    OVERALL_SCORE_NUMBER(null, Xpath.start().div(Id.id("overallScoreBox_body")).table().tbody().tr().td("2").table().tbody().tr().td().toString()),

    OVERALL_TOOLS(null, "speedForm:toolsIcon"),
    OVERALL_EMAIL_TOOL(emailReport, "dateLinksForm:emailMenuItem:anchor"),
    OVERALL_PDF_TOOL(exportPDF, "dateLinksForm:export_menu_item:anchor"),
    OVERALL_EXCEL_TOOL("Export to Excel", "speedForm:vehicleSpeed-exportExcelMenuItem:anchor"),

    RETURN(null, "speedForm:notificationsTable-dateLinksToolsDetailShow"),

    /* Breakdown by Speed Limit */
    BREAKDOWN_OVERALL_LINK("Overall", "vehicleChartOverallForm:vehicleSpeedOverall"),
    BREAKDOWN_1_30_LINK("1-30 mph", "vehicleChartTwentyOneForm:vehicleSpeedTwentyOne"),
    BREAKDOWN_31_40_LINK("31-40 mph", "vehicleChartThirtyOneForm:vehicleSpeedThirtyOne"),
    BREAKDOWN_41_54_LINK("41-54 mph", "vehicleChartFortyOneForm:vehicleSpeedFortyOne"),
    BREAKDOWN_55_64_LINK("55-64 mph", "vehicleChartFiftyFiveForm:vehicleSpeedFiftyFive"),
    BREAKDOWN_65_80_LINK("65-80 mph", "vehicleChartSixtyFiveForm:vehicleSpeedSixtyFive"),

    BREAKDOWN_OVERALL_SCORE(null, Xpath.start().td(Id.id("OVERALL")).table().tbody().tr().td("2").table().tbody().tr().toString()),
    BREAKDOWN_1_30_SCORE(null, Xpath.start().td(Id.id("TWENTYONE")).table().tbody().tr().td("2").table().tbody().tr().toString()),
    BREAKDOWN_31_40_SCORE(null, Xpath.start().td(Id.id("THIRTYONE")).table().tbody().tr().td("2").table().tbody().tr().toString()),
    BREAKDOWN_41_54_SCORE(null, Xpath.start().td(Id.id("FOURTYONE")).table().tbody().tr().td("2").table().tbody().tr().toString()),
    BREAKDOWN_55_64_SCORE(null, Xpath.start().td(Id.id("FIFTYFIVE")).table().tbody().tr().td("2").table().tbody().tr().toString()),
    BREAKDOWN_65_80_SCORE(null, Xpath.start().td(Id.id("SIXTYFIVE")).table().tbody().tr().td("2").table().tbody().tr().toString()),

    /* Details */
    DETAILS_TITLE("Details", Xpath.start().span(Id.clazz("details")).toString()),
    DETAILS_X_OF_Y("Showing XXX to YYY of ZZZ records", "headerEvents"),

    LOCATION_HEADER("Location", "notificationsTableForm:notificationsTable:address_columnheader:sortDiv"),
    DATE_TIME_HEADER("Date/Time", "notificationsTableForm:notificationsTable:timeheader:sortDiv"),
    POSTED_HEADER("Posted Speed", "notificationsTableForm:notificationsTable:speedLimitheader:sortDiv"),
    AVERAGE_HEADER("Avg Speed", "notificationsTableForm:notificationsTable:averageSpeedheader:sortDiv"),
    TOP_HEADER("Top Speed", "notificationsTableForm:notificationsTable:topSpeedheader:sortDiv"),
    DISTANCE_HEADER("Distance", "notificationsTableForm:notificationsTable:distanceheader:sortDiv"),

    LOCATION_ENTRY(null, "notificationsTableForm:notificationsTable:###:eventAddress"),
    DATE_TIME_ENTRY(null, "notificationsTableForm:notificationsTable:###:time"),

    POSTED_ENTRY_SPEED(null, "notificationsTableForm:notificationsTable:###:speedLimitValue"),
    POSTED_ENTRY_SPEED_UNITS(null, Xpath.start().td(Id.id("notificationsTableForm:notificationsTable:###:speedLimit")).strong().toString()),

    POSTED_ENTRY_SBS_BUTTON(null, "notificationsTableForm:notificationsTable:###:notificationsTableSlcr"),

    AVERAGE_ENTRY_SPEED(null, "notificationsTableForm:notificationsTable:###:averageSpeedValue"),
    AVERAGE_ENTRY_SPEED_UNITS(null, Xpath.start().td(Id.id("notificationsTableForm:notificationsTable:###:averageSpeed")).strong().toString()),
    AVERAGE_ENTRY_PLUSMINUS(null, Xpath.start().td(Id.id("notificationsTableForm:notificationsTable:###:averageSpeed")).span("1").toString()),
    AVERAGE_ENTRY_DIFF(null, "notificationsTableForm:notificationsTable:###:averageSpeedDifference"),

    TOP_ENTRY_SPEED(null, "notificationsTableForm:notificationsTable:###:topSpeedValue"),
    TOP_ENTRY_SPEED_UNITS(null, Xpath.start().td(Id.id("notificationsTableForm:notificationsTable:###:topSpeed")).strong().toString()),
    TOP_ENTRY_PLUSMINUS(null, Xpath.start().td(Id.id("notificationsTableForm:notificationsTable:###:topSpeed")).span("1").toString()),
    TOP_ENTRY_DIFF(null, "notificationsTableForm:notificationsTable:###:topSpeedDifference"),

    DISTANCE_ENTRY(null, "notificationsTableForm:notificationsTable:###:distance"),

    ;

    private String text, url;
    private String[] IDs;
    
    private VehiclePerformanceSpeedEnum(String url){
    	this.url = url;
    }
    private VehiclePerformanceSpeedEnum(String text, String ...IDs){
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
