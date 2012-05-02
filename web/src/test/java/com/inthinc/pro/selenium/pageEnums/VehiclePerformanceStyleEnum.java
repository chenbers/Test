package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum VehiclePerformanceStyleEnum implements SeleniumEnums {

    DEFAULT_URL(appUrl + "/vehicle/style/"),

    /* Overall */
    OVERALL_TIME_FRAME_SELECTOR(null, "styleForm:vehicleStyle-dateLinksToolsDetail***"),
    OVERALL_TITLE("Driving Style: Overall", "titleSpan"),
    OVERALL_SCORE_LABEL("Overall Score", Xpath.start().div(Id.id("overallScoreBox_body")).table().tbody().tr().td("1").h4().toString()),
    OVERALL_SCORE_NUMBER(null, Xpath.start().div(Id.id("overallScoreBox_body")).table().tbody().tr().td("2").table().tbody().tr().td().toString()),

    OVERALL_TOOLS(null, "styleForm:toolsIcon"),
    OVERALL_EMAIL_TOOL(emailReport, "styleForm:vehicleStyle-emailMenuItem:anchor"),
    OVERALL_PDF_TOOL(exportPDF, "styleForm:vehicleStyle-export_menu_item:anchor"),
    OVERALL_EXCEL_TOOL("Export to Excel", "styleForm:vehicleStyle-exportExcelMenuItem:anchor"),


    RETURN(null, "styleForm:vehicleStyle-dateLinksToolsDetailShow"),

    /* Breakdown by Driving Style */
    BREAKDOWN_OVERALL_LINK("Overall", "vehicleChartOverallStyleForm:vehicleStyleOverall"),
    BREAKDOWN_HARD_BRAKE_LINK("Hard Brake", "vehicleChartBrakeForm:vehicleStyleBrake"),
    BREAKDOWN_HARD_ACCEL_LINK("Hard Acceleration", "vehicleChartAccelerateForm:vehicleStyleAccelerate"),
    BREAKDOWN_UNSAFE_TURN_LINK("Unsafe Turn", "vehicleChartTurnForm:vehicleStyleTurn"),
    BREAKDOWN_HARD_BUMP_LINK("Hard Bump", "vehicleChartBumpForm:vehicleStyleBump"),

    BREAKDOWN_OVERALL_SCORE(null, Xpath.start().td(Id.id("OVERALL")).table().tbody().tr().td("2").table().tbody().tr().toString()),
    BREAKDOWN_HARD_BRAKE_SCORE(null, Xpath.start().td(Id.id("BRAKE")).table().tbody().tr().td("2").table().tbody().tr().toString()),
    BREAKDOWN_HARD_ACCEL_SCORE(null, Xpath.start().td(Id.id("ACCELERATE")).table().tbody().tr().td("2").table().tbody().tr().toString()),
    BREAKDOWN_UNSAFE_TURN_SCORE(null, Xpath.start().td(Id.id("TURN")).table().tbody().tr().td("2").table().tbody().tr().toString()),
    BREAKDOWN_HARD_BUMP_SCORE(null, Xpath.start().td(Id.id("BUMP")).table().tbody().tr().td("2").table().tbody().tr().toString()),

    LOCATION_HEADER("Location", "eventTable:notificationsTable:address_columnheader:sortDiv"),
    DATE_TIME_HEADER("Date/Time", "eventTable:notificationsTable:timeheader:sortDiv"),
    EVENT_HEADER("Posted Speed", "eventTable:notificationsTable:typeheader:sortDiv"),
    SPEED_HEADER("Avg Speed", "eventTable:notificationsTable:speedheader:sortDiv"),
    SEVERITY_HEADER("Top Speed", "eventTable:notificationsTable:severityheader:sortDiv"),

    LOCATION_ENTRY(null, "eventTable:notificationsTable:###:vehicleStyleShow"),
    DATE_TIME_ENTRY(null, "eventTable:notificationsTable:###:time"),
    EVENT_ENTRY(null, "eventTable:notificationsTable:###:type"),

    SPEED_ENTRY(null, "eventTable:notificationsTable:###:speed"),

    SEVERITY_ENTRY_SEVERITY(null, Xpath.start().td(Id.id("eventTable:notificationsTable:###:severity")).tbody().tr("1").toString()),
    

    ;

    private String text, url;
    private String[] IDs;
    
    private VehiclePerformanceStyleEnum(String url){
    	this.url = url;
    }
    private VehiclePerformanceStyleEnum(String text, String ...IDs){
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
