package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum VehiclePerformanceSeatBBeltEnum implements SeleniumEnums {
	/* Overall */
    OVERALL_TIME_FRAME_SELECTOR(null, "seatBeltForm:vehicleSeatBelt-dateLinksToolsDetail***"),
    OVERALL_TITLE("Seat Belt: Overall", "titleSpan"),
    OVERALL_SCORE_LABEL("Overall Score", Xpath.start().div(Id.id("overallScoreBox_body")).table().tbody().tr().td("1").h4().toString()),
    OVERALL_SCORE_NUMBER(null, Xpath.start().div(Id.id("overallScoreBox_body")).table().tbody().tr().td("2").table().tbody().tr().td().toString()),

    OVERALL_TOOLS(null, "seatBeltForm:toolsIcon"),
    OVERALL_EMAIL_TOOL(emailReport, "seatBeltForm:vehicleSeatBelt-emailMenuItem:anchor"),
    OVERALL_PDF_TOOL(exportPDF, "seatBeltForm:vehicleSeatBelt-export_menu_item:anchor"),
    OVERALL_EXCEL_TOOL("Export to Excel", "seatBeltForm:vehicleSeatBelt-exportExcelMenuItem:anchor"),

    RETURN(null, "seatBeltForm:vehicleSeatBelt-dateLinksToolsDetailShow"),

    /* Details */
    DETAILS_TITLE("Details", Xpath.start().span(Id.clazz("details")).toString()),

    LOCATION_HEADER("Location", "eventTableForm:notificationsTable:address_columnheader:sortDiv"),
    DATE_TIME_HEADER("Date/Time", "eventTableForm:notificationsTable:timeheader:sortDiv"),
    AVERAGE_SPEED_HEADER("Avg Speed", "eventTableForm:notificationsTable:averageSpeedheader:sortDiv"),
    TOP_SPEED_HEADER("Top Speed", "eventTableForm:notificationsTable:topSpeedheader:sortDiv"),
    DISTANCE_HEADER("Distance", "eventTableForm:notificationsTable:distanceheader:sortDiv"),

    LOCATION_ENTRY(null, "eventTableForm:notificationsTable:###:address_column"),
    DATE_TIME_ENTRY(null, "eventTableForm:notificationsTable:###:time"),
    AVERAGE_SPEED_ENTRY(null, "eventTableForm:notificationsTable:###:averageSpeed"),
    TOP_SPEED_ENTRY(null, "eventTableForm:notificationsTable:###:topSpeed"),
    DISTANCE_ENTRY(null, "eventTableForm:notificationsTable:###:distance"),

    EXCLUDE("exclude", "eventTableForm:notificationsTable:###:vehicleSeatBeltIncluded"),
    INCLUDE("include", "eventTableForm:notificationsTable:###:vehicleSeatBelt_excluded"), 
    DRIVER_NAME_LINK(null, "//a[contains(@id,'vehicleSeatBeltTitle')]"), 
    
    BREADCRUMB(null, "//a[contains(@id,'breadcrumbitem:###:vehicleSeatBelt-dashboard')]"),
    
    
    

    ;

    private String text, url;
    private String[] IDs;
    
    private VehiclePerformanceSeatBBeltEnum(String url){
    	this.url = url;
    }
    private VehiclePerformanceSeatBBeltEnum(String text, String ...IDs){
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
