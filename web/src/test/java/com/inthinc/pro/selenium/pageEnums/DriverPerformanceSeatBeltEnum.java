package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum DriverPerformanceSeatBeltEnum implements SeleniumEnums {
    
    DEFAULT_URL(appUrl + "/driver/seatbelt/"),

    /* Overall */
    OVERALL_TIME_FRAME_SELECTOR(null, "seatBeltForm:driversSeatBelt-dateLinksToolsDetail***"),
    OVERALL_TITLE("Seat Belt: Overall", "titleSpan"),
    OVERALL_SCORE_LABEL("Overall Score", Xpath.start().div(Id.id("overallScoreBox_body")).table().tbody().tr().td("1").h4().toString()),
    OVERALL_SCORE_NUMBER(null, Xpath.start().div(Id.id("overallScoreBox_body")).table().tbody().tr().td("2").table().tbody().tr().td().toString()),

    OVERALL_TOOLS(null, "seatBeltForm:toolsIcon"),
    OVERALL_EMAIL_TOOL(emailReport, "dateLinksForm:emailMenuItem:anchor"),
    OVERALL_PDF_TOOL(exportPDF, "dateLinksForm:export_menu_item:anchor"),
    OVERALL_EXCEL_TOOL("Export to Excel", "seatBeltForm:driverSeatBelt-exportExcelMenuItem:anchor"),

    RETURN(null, "seatBeltForm:driversSeatBelt-dateLinksToolsDetailShow"),

    /* Details */
    DETAILS_TITLE("Details", Xpath.start().span(Id.clazz("details")).toString()),

    LOCATION_HEADER("Location", "driverSeatBeltForm:notificationsTable:address_columnheader:sortDiv"),
    DATE_TIME_HEADER("Date/Time", "driverSeatBeltForm:notificationsTable:timeheader:sortDiv"),
    AVERAGE_SPEED_HEADER("Avg Speed", "driverSeatBeltForm:notificationsTable:averageSpeedheader:sortDiv"),
    TOP_SPEED_HEADER("Top Speed", "driverSeatBeltForm:notificationsTable:topSpeedheader:sortDiv"),
    DISTANCE_HEADER("Distance", "driverSeatBeltForm:notificationsTable:distanceheader:sortDiv"),

    LOCATION_ENTRY(null, "driverSpeedForm:notificationsTable:###:driverSeatBeltShow"),
    DATE_TIME_ENTRY(null, "driverSeatBeltForm:notificationsTable:###:time"),
    AVERAGE_SPEED_ENTRY(null, "driverSeatBeltForm:notificationsTable:###:averageSpeed"),
    TOP_SPEED_ENTRY(null, "driverSeatBeltForm:notificationsTable:###:topSpeed"),
    DISTANCE_ENTRY(null, "driverSeatBeltForm:notificationsTable:###:distance"),

    EXCLUDE("exclude", "driverSeatBeltForm:notificationsTable:###:driverSeatBelt_included"),
    INCLUDE("include", "driverSeatBeltForm:notificationsTable:###:driverSeatBelt_excluded"),
    
    DRIVER_NAME_LINK(null, "//a[contains(@id,'driverSeatBeltDriverPerformance')]"),
    BREADCRUMB(null, "//a[contains(@id,'breadcrumbitem:###:driversSeatBelt-dashboard')]"),

    ;

    private String text, url;
    private String[] IDs;
    
    private DriverPerformanceSeatBeltEnum(String url){
    	this.url = url;
    }
    private DriverPerformanceSeatBeltEnum(String text, String ...IDs){
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
