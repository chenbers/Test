package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum DriverPerformanceSeatBeltEnum implements SeleniumEnums {

    /* Overall */
    OVERALL_TIME_FRAME_SELECTOR(null, "seatBeltForm:driversSeatBelt-dateLinksToolsDetail***", null, null),
    OVERALL_TITLE("Seat Belt: Overall", "titleSpan", null, null),
    OVERALL_SCORE_LABEL("Overall Score", null, Xpath.start().div(Id.id("overallScoreBox_body")).table().tbody().tr().td("1").h4().toString(), null),
    OVERALL_SCORE_NUMBER(null, null, Xpath.start().div(Id.id("overallScoreBox_body")).table().tbody().tr().td("2").table().tbody().tr().td().toString(), null),

    OVERALL_TOOLS(null, "seatBeltForm:toolsIcon", null, null),
    OVERALL_EMAIL_TOOL(emailReport, "dateLinksForm:emailMenuItem:anchor", null, null),
    OVERALL_PDF_TOOL(exportPDF, "dateLinksForm:export_menu_item:anchor", null, null),
    OVERALL_EXCEL_TOOL("Export to Excel", "seatBeltForm:driverSeatBelt-exportExcelMenuItem:anchor", null, null),

    /* Email Report Pop-up */
    EMAIL_HEADER("Driver - ***", "seatBeltForm_singleEmailHeader", null, null),
    EMAIL_SUB_TITLE("E-mail this report to the following e-mail addresses.", "popupsubtitle", null, null),
    EMAIL_TEXT("E-mail Address(es): (e-mail addresses separated by a comma)", null, Xpath.start().form(Id.id("seatBeltForm_singleEmail_form")).span().table().tbody().tr("1").toString(), null),
    EMAIL_TEXT_AREA(null, "seatBeltForm_singleEmail_form:seatBeltForm_singleEmail_email", null, null),
    EMAIL_CANCEL_BUTTON(cancel, "emailReportPopUpSubmit", null, null),
    EMAIL_SUBMIT_BUTTON(email, "seatBeltForm_singleEmail_form:emailReportPopupEmail3", null, null),
    EMAIL_X_BUTTON(null, null, Xpath.start().div(Id.id("seatBeltForm_singleEmailContentDiv")).div().img().toString(), null),

    RETURN(null, "seatBeltForm:driverSeatBelt-dateLinksToolsDetailShow", null, null),

    /* Details */
    DETAILS_TITLE("Details", null, Xpath.start().span(Id.clazz("details")).toString(), null),
    DETAILS_X_OF_Y("Showing XXX to YYY of ZZZ records", "headerEvents", null, null),

    LOCATION_HEADER("Location", "driverSeatBeltForm:events:address_columnheader:sortDiv", null, null),
    DATE_TIME_HEADER("Date/Time", "driverSeatBeltForm:events:timeheader:sortDiv", null, null),
    AVERAGE_SPEED_HEADER("Posted Speed", "driverSeatBeltForm:events:averageSpeedheader:sortDiv", null, null),
    TOP_SPEED_HEADER("Avg Speed", "driverSeatBeltForm:events:topSpeedheader:sortDiv", null, null),
    DISTANCE_HEADER("Top Speed", "driverSeatBeltForm:events:distanceheader:sortDiv", null, null),

    LOCATION_ENTRY(null, "driverSpeedForm:notificationsTable:***:driverSpeedShow", null, null),
    DATE_TIME_ENTRY(null, "driverSeatBeltForm:events:***:time", null, null),
    AVERAGE_SPEED_ENTRY(null, "driverSeatBeltForm:events:***:averageSpeed", null, null),
    TOP_SPEED_ENTRY(null, "driverSeatBeltForm:events:***:topSpeed", null, null),
    DISTANCE_ENTRY(null, "driverSeatBeltForm:events:***:distance", null, null),

    EXCLUDE("exclude", "driverSeatBeltForm:events:0:driverSeatBelt_included", null, null),

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
