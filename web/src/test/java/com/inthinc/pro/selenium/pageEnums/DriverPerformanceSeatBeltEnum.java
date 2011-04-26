package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum DriverPerformanceSeatBeltEnum implements SeleniumEnums {
    
    /* Overall */
    
    OVERALL_TIME_FRAME_SELECTOR(null, "seatBeltForm:driversSeatBelt-dateLinksToolsDetail***"),
    OVERALL_TITLE("Seat Belt: Overall", "titleSpan"),
    OVERALL_SCORE_LABEL("Overall Score", Xpath.start().div(Id.id("overallScoreBox_body")).table().tbody().tr().td("1").h4() ),
    OVERALL_SCORE_NUMBER(Xpath.start().div(Id.id("overallScoreBox_body")).table().tbody().tr().td("2").table().tbody().tr().td()),
    
    OVERALL_TOOLS(null, "seatBeltForm:toolsIcon"),
    OVERALL_EMAIL_TOOL(emailReport, "dateLinksForm:emailMenuItem:anchor"),
    OVERALL_PDF_TOOL(exportPDF, "dateLinksForm:export_menu_item:anchor"),
    OVERALL_EXCEL_TOOL("Export to Excel", "seatBeltForm:driverSeatBelt-exportExcelMenuItem:anchor"),
    
    /* Email Report Pop-up */
    EMAIL_HEADER("Driver - ***", "seatBeltForm_singleEmailHeader"),
    EMAIL_SUB_TITLE("E-mail this report to the following e-mail addresses.", "popupsubtitle"),
    EMAIL_TEXT("E-mail Address(es): (e-mail addresses separated by a comma)", Xpath.start().form(Id.id("seatBeltForm_singleEmail_form")).span().table().tbody().tr("1")),
    EMAIL_TEXT_AREA(null, "seatBeltForm_singleEmail_form:seatBeltForm_singleEmail_email"),
    EMAIL_CANCEL_BUTTON(cancel, "emailReportPopUpSubmit"),
    EMAIL_SUBMIT_BUTTON(email, "seatBeltForm_singleEmail_form:emailReportPopupEmail3"),
    EMAIL_X_BUTTON(Xpath.start().div(Id.id("seatBeltForm_singleEmailContentDiv")).div().img()),
    
    RETURN(null, "seatBeltForm:driverSeatBelt-dateLinksToolsDetailShow"),
    
    /* Details */
    DETAILS_TITLE("Details", Xpath.start().span(Id.clazz("details"))),
    DETAILS_X_OF_Y("Showing XXX to YYY of ZZZ records", "headerEvents" ),
    
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

    private String text, ID, xpath, xpath_alt, url;

    private DriverPerformanceSeatBeltEnum(String text, String ID, String xpath, String xpath_alt) {
        this.text = text;
        this.ID = ID;
        this.xpath = xpath;
        this.xpath_alt = xpath_alt;
    }

    private DriverPerformanceSeatBeltEnum(String url) {
        this.url = url;
    }
    
    private DriverPerformanceSeatBeltEnum( String text, String ID) {
        this(text, ID, "", null);
    }
    private DriverPerformanceSeatBeltEnum( String text, String ID, String xpath) {
        this(text, ID, xpath, null);
    }
    
    private DriverPerformanceSeatBeltEnum(String text, String ID, Xpath xpath, Xpath xpath_alt){
        this(text, ID, xpath.toString(), xpath_alt.toString());
    }
    
    private DriverPerformanceSeatBeltEnum(String text, String ID, Xpath xpath){
        this(text, ID, xpath.toString(), null);
    }
    
    private DriverPerformanceSeatBeltEnum(String text, Xpath xpath){
        this(text, null, xpath.toString(), null);
    }
    
    private DriverPerformanceSeatBeltEnum( Xpath xpath, Xpath xpath_alt) {
        this(null, null, xpath.toString(), xpath_alt.toString());
    }
    
    private DriverPerformanceSeatBeltEnum( Xpath xpath) {
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

}
