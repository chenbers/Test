package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum DriverPerformanceStyleEnum implements SeleniumEnums {

    DEFAULT_URL(appUrl + "/driver/style/"),

    /* Overall */
    OVERALL_TIME_FRAME_SELECTOR(null, "styleForm:driverStyle-dateLinksToolsDetail***"),
    OVERALL_TITLE("Driving Style: Overall", "titleSpan"),
    OVERALL_SCORE_LABEL("Overall Score", Xpath.start().div(Id.id("overallScoreBox_body")).table().tbody().tr().td("1").h4().toString()),
    OVERALL_SCORE_NUMBER(null, "//div[@id='overallScoreBox_body']/table/tbody/tr/td[2]/table/tbody/tr/td"),

    OVERALL_TOOLS(null, "styleForm:toolsIcon"),
    OVERALL_EMAIL_TOOL(emailReport, "dateLinksForm:emailMenuItem:anchor"),
    OVERALL_PDF_TOOL(exportPDF, "dateLinksForm:export_menu_item:anchor"),
    OVERALL_EXCEL_TOOL("Export to Excel", "styleForm:driverStyle-exportExcelMenuItem:anchor"),


    RETURN(null, "styleForm:driverStyle-dateLinksToolsDetailShow"),

    /* Breakdown by Driving Style */
    BREAKDOWN_OVERALL_LINK("Overall", "driverStyleOverallForm:driverStyleOverall"),
    BREAKDOWN_HARD_BRAKE_LINK("Hard Brake", "driverStyleBrakeForm:driverStyleBrake"),
    BREAKDOWN_HARD_ACCEL_LINK("Hard Acceleration", "driverStyleAccelerateForm:driverStyleAccelerate"),
    BREAKDOWN_UNSAFE_TURN_LINK("Unsafe Turn", "driverStyleTurnForm:driverStyleTurn"),
    BREAKDOWN_HARD_BUMP_LINK("Hard Bump", "driverStyleBumpForm:driverStyleBump"),

    BREAKDOWN_OVERALL_SCORE(null, Xpath.start().td(Id.id("OVERALL")).table().tbody().tr().td("2").table().tbody().tr().toString()),
    BREAKDOWN_HARD_BRAKE_SCORE(null, Xpath.start().td(Id.id("BRAKE")).table().tbody().tr().td("2").table().tbody().tr().toString()),
    BREAKDOWN_HARD_ACCEL_SCORE(null, Xpath.start().td(Id.id("ACCELERATE")).table().tbody().tr().td("2").table().tbody().tr().toString()),
    BREAKDOWN_UNSAFE_TURN_SCORE(null, Xpath.start().td(Id.id("TURN")).table().tbody().tr().td("2").table().tbody().tr().toString()),
    BREAKDOWN_HARD_BUMP_SCORE(null, Xpath.start().td(Id.id("BUMP")).table().tbody().tr().td("2").table().tbody().tr().toString()),

    LOCATION_HEADER("Location", "driverStyleEventTable:notificationsTable:address_columnheader:sortDiv"),
    DATE_TIME_HEADER("Date/Time", "driverStyleEventTable:notificationsTable:timeheader:sortDiv"),
    EVENT_HEADER("Posted Speed", "driverStyleEventTable:notificationsTable:typeheader:sortDiv"),
    SPEED_HEADER("Avg Speed", "driverStyleEventTable:notificationsTable:speedheader:sortDiv"),
    SEVERITY_HEADER("Top Speed", "driverStyleEventTable:notificationsTable:severityheader:sortDiv"),

    LOCATION_ENTRY(null, "driverStyleEventTable:notificationsTable:###:driverStyleShow"),
    DATE_TIME_ENTRY(null, "driverStyleEventTable:notificationsTable:###:time"),
    EVENT_ENTRY(null, "driverStyleEventTable:notificationsTable:###:type"),

    SPEED_ENTRY(null, "driverStyleEventTable:notificationsTable:###:speed"),

    SEVERITY_ENTRY_SEVERITY(null, Xpath.start().td(Id.id("driverStyleEventTable:notificationsTable:###:severity")).tbody().tr("1").toString()),

    EXCLUDE("exclude", "driverStyleEventTable:notificationsTable:###:driverStyleInclude"),
    INCLUDE("include", "driverStyleEventTable:notificationsTable:###:driverStyle_excluded"), 

    ;

    private String text, url;
    private String[] IDs;
    
    private DriverPerformanceStyleEnum(String url){
    	this.url = url;
    }
    private DriverPerformanceStyleEnum(String text, String ...IDs){
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
