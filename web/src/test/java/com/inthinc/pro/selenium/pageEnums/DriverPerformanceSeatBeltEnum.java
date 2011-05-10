package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum DriverPerformanceSeatBeltEnum implements SeleniumEnums {

    /* Overall */
    OVERALL_TIME_FRAME_SELECTOR(null, "seatBeltForm:driversSeatBelt-dateLinksToolsDetail***"),
    OVERALL_TITLE("Seat Belt: Overall", "titleSpan"),
    OVERALL_SCORE_LABEL("Overall Score", Xpath.start().div(Id.id("overallScoreBox_body")).table().tbody().tr().td("1").h4().toString()),
    OVERALL_SCORE_NUMBER(null, Xpath.start().div(Id.id("overallScoreBox_body")).table().tbody().tr().td("2").table().tbody().tr().td().toString()),

    OVERALL_TOOLS(null, "seatBeltForm:toolsIcon"),
    OVERALL_EMAIL_TOOL(emailReport, "dateLinksForm:emailMenuItem:anchor"),
    OVERALL_PDF_TOOL(exportPDF, "dateLinksForm:export_menu_item:anchor"),
    OVERALL_EXCEL_TOOL("Export to Excel", "seatBeltForm:driverSeatBelt-exportExcelMenuItem:anchor"),

    /* Email Report Pop-up */
    EMAIL_HEADER("Driver - ***", "seatBeltForm_singleEmailHeader"),
    EMAIL_SUB_TITLE("E-mail this report to the following e-mail addresses.", "popupsubtitle"),
    EMAIL_TEXT("E-mail Address(es): (e-mail addresses separated by a comma)", Xpath.start().form(Id.id("seatBeltForm_singleEmail_form")).span().table().tbody().tr("1").toString()),
    EMAIL_TEXT_AREA(null, "seatBeltForm_singleEmail_form:seatBeltForm_singleEmail_email"),
    EMAIL_CANCEL_BUTTON(cancel, "emailReportPopUpSubmit"),
    EMAIL_SUBMIT_BUTTON(email, "seatBeltForm_singleEmail_form:emailReportPopupEmail3"),
    EMAIL_X_BUTTON(null, Xpath.start().div(Id.id("seatBeltForm_singleEmailContentDiv")).div().img().toString()),

    RETURN(null, "seatBeltForm:driverSeatBelt-dateLinksToolsDetailShow"),

    /* Details */
    DETAILS_TITLE("Details", Xpath.start().span(Id.clazz("details")).toString()),
    DETAILS_X_OF_Y("Showing XXX to YYY of ZZZ records", "headerEvents"),

    LOCATION_HEADER("Location", "driverSeatBeltForm:events:address_columnheader:sortDiv"),
    DATE_TIME_HEADER("Date/Time", "driverSeatBeltForm:events:timeheader:sortDiv"),
    AVERAGE_SPEED_HEADER("Posted Speed", "driverSeatBeltForm:events:averageSpeedheader:sortDiv"),
    TOP_SPEED_HEADER("Avg Speed", "driverSeatBeltForm:events:topSpeedheader:sortDiv"),
    DISTANCE_HEADER("Top Speed", "driverSeatBeltForm:events:distanceheader:sortDiv"),

    LOCATION_ENTRY(null, "driverSpeedForm:notificationsTable:***:driverSpeedShow"),
    DATE_TIME_ENTRY(null, "driverSeatBeltForm:events:***:time"),
    AVERAGE_SPEED_ENTRY(null, "driverSeatBeltForm:events:***:averageSpeed"),
    TOP_SPEED_ENTRY(null, "driverSeatBeltForm:events:***:topSpeed"),
    DISTANCE_ENTRY(null, "driverSeatBeltForm:events:***:distance"),

    EXCLUDE("exclude", "driverSeatBeltForm:events:0:driverSeatBelt_included"),

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
