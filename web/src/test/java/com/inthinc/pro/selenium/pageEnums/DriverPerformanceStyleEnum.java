package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum DriverPerformanceStyleEnum implements SeleniumEnums {

    /* Overall */
    OVERALL_TIME_FRAME_SELECTOR(null, "styleForm:driverStyle-dateLinksToolsDetail***", null, null),
    OVERALL_TITLE("Driving Style: Overall", "titleSpan", null, null),
    OVERALL_SCORE_LABEL("Overall Score", null, Xpath.start().div(Id.id("overallScoreBox_body")).table().tbody().tr().td("1").h4().toString(), null),
    OVERALL_SCORE_NUMBER(null, null, Xpath.start().div(Id.id("overallScoreBox_body")).table().tbody().tr().td("2").table().tbody().tr().td().toString(), null),

    OVERALL_TOOLS(null, "styleForm:toolsIcon", null, null),
    OVERALL_EMAIL_TOOL(emailReport, "dateLinksForm:emailMenuItem:anchor", null, null),
    OVERALL_PDF_TOOL(exportPDF, "dateLinksForm:export_menu_item:anchor", null, null),
    OVERALL_EXCEL_TOOL("Export to Excel", "styleForm:driverStyle-exportExcelMenuItem:anchor", null, null),

    /* Email Report Pop-up */
    EMAIL_HEADER("Driver - ***", "styleForm_singleEmailHeader", null, null),
    EMAIL_SUB_TITLE("E-mail this report to the following e-mail addresses.", "popupsubtitle", null, null),
    EMAIL_TEXT("E-mail Address(es): (e-mail addresses separated by a comma)", null, Xpath.start().form(Id.id("styleForm_singleEmail_form")).span().table().tbody().tr("1").toString(), null),
    EMAIL_TEXT_AREA(null, "styleForm_singleEmail_form:styleForm_singleEmail_email", null, null),
    EMAIL_CANCEL_BUTTON(cancel, "emailReportPopUpSubmit", null, null),
    EMAIL_SUBMIT_BUTTON(email, "styleForm_singleEmail_form:emailReportPopupEmail3", null, null),
    EMAIL_X_BUTTON(null, null, Xpath.start().div(Id.id("styleForm_singleEmailContentDiv")).div().img().toString(), null),

    RETURN(null, "styleForm:driverStyle-dateLinksToolsDetailShow", null, null),

    /* Breakdown by Driving Style */
    BREAKDOWN_OVERALL_LINK("Overall"),
    BREAKDOWN_HARD_BRAKE_LINK("Hard Brake"),
    BREAKDOWN_HARD_ACCEL_LINK("Hard Acceleration"),
    BREAKDOWN_UNSAFE_TURN_LINK("Unsafe Turn"),
    BREAKDOWN_HARD_BUMP_LINK("Hard Bump"),

    BREAKDOWN_OVERALL_SCORE(null, null, Xpath.start().td(Id.id("OVERALL")).table().tbody().tr().td("2").table().tbody().tr().toString(), null),
    BREAKDOWN_HARD_BRAKE_SCORE(null, null, Xpath.start().td(Id.id("BRAKE")).table().tbody().tr().td("2").table().tbody().tr().toString(), null),
    BREAKDOWN_HARD_ACCEL_SCORE(null, null, Xpath.start().td(Id.id("ACCELERATE")).table().tbody().tr().td("2").table().tbody().tr().toString(), null),
    BREAKDOWN_UNSAFE_TURN_SCORE(null, null, Xpath.start().td(Id.id("TURN")).table().tbody().tr().td("2").table().tbody().tr().toString(), null),
    BREAKDOWN_HARD_BUMP_SCORE(null, null, Xpath.start().td(Id.id("BUMP")).table().tbody().tr().td("2").table().tbody().tr().toString(), null),

    /* Details */
    DETAILS_TITLE("Details", null, Xpath.start().span(Id.clazz("details")).toString(), null),
    DETAILS_X_OF_Y("Showing XXX to YYY of ZZZ records", "headerEvents", null, null),

    LOCATION_HEADER("Location", "driverStyleEventTable:notificationsTable:address_columnheader:sortDiv", null, null),
    DATE_TIME_HEADER("Date/Time", "driverStyleEventTable:notificationsTable:timeheader:sortDiv", null, null),
    EVENT_HEADER("Posted Speed", "driverStyleEventTable:notificationsTable:typeheader:sortDiv", null, null),
    SPEED_HEADER("Avg Speed", "driverStyleEventTable:notificationsTable:speedheader:sortDiv", null, null),
    SEVERITY_HEADER("Top Speed", "driverStyleEventTable:notificationsTable:severityheader:sortDiv", null, null),

    LOCATION_ENTRY(null, "driverStyleEventTable:notificationsTable:***:driverStyleShow", null, null),
    DATE_TIME_ENTRY(null, "driverStyleEventTable:notificationsTable:***:time", null, null),
    EVENT_ENTRY(null, "driverStyleEventTable:notificationsTable:***:type", null, null),

    SPEED_ENTRY(null, "driverStyleEventTable:notificationsTable:***:speed", null, null),

    SEVERITY_ENTRY_SEVERITY(null, null, Xpath.start().td(Id.id("driverStyleEventTable:notificationsTable:***:severity")).tbody().tr("1").toString(), null),

    EXCLUDE("exclude", "driverStyleEventTable:notificationsTable:***:driverStyleInclude", null, null),

    ;

    private String text, ID, xpath, xpath_alt, url;

    private DriverPerformanceStyleEnum(String text, String ID, String xpath, String xpath_alt) {
        this.text = text;
        this.ID = ID;
        this.xpath = xpath;
        this.xpath_alt = xpath_alt;
    }

    private DriverPerformanceStyleEnum(String url) {
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
