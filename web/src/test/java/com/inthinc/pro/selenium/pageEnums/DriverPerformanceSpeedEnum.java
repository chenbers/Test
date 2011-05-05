package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum DriverPerformanceSpeedEnum implements SeleniumEnums {
    /* Overall Score */
    OVERALL_TIME_FRAME_SELECTOR(null, "speedForm:driverSpeed-dateLinksToolsDetail***", null, null),
    OVERALL_TITLE("Speed: Overall", "titleSpan", null, null),
    OVERALL_SCORE_LABEL("Overall Score", null, Xpath.start().div(Id.id("overallScoreBox_body")).table().tbody().tr().td("1").h4().toString(), null),
    OVERALL_SCORE_NUMBER(null, null, Xpath.start().div(Id.id("overallScoreBox_body")).table().tbody().tr().td("2").table().tbody().tr().td().toString(), null),

    OVERALL_TOOLS(null, "dateLinksForm:toolsIcon", null, null),
    OVERALL_EMAIL_TOOL(emailReport, "dateLinksForm:emailMenuItem:anchor", null, null),
    OVERALL_PDF_TOOL(exportPDF, "dateLinksForm:export_menu_item:anchor", null, null),
    OVERALL_EXCEL_TOOL("Export to Excel", "speedForm:driverSpeed-exportExcelMenuItem:anchor", null, null),

    /* Email Report Pop-up */
    EMAIL_HEADER("Driver - ***", "speedForm_singleEmailHeader", null, null),
    EMAIL_SUB_TITLE("E-mail this report to the following e-mail addresses.", "popupsubtitle", null, null),
    EMAIL_TEXT("E-mail Address(es): (e-mail addresses separated by a comma)", null, Xpath.start().form(Id.id("speedForm_singleEmail_form")).span().table().tbody().tr("1").toString(), null),
    EMAIL_TEXT_AREA(null, "speedForm_singleEmail_form:speedForm_singleEmail_email", null, null),
    EMAIL_CANCEL_BUTTON(cancel, "emailReportPopUpSubmit", null, null),
    EMAIL_SUBMIT_BUTTON(email, "speedForm_singleEmail_form:emailReportPopupEmail3", null, null),
    EMAIL_X_BUTTON(null, null, Xpath.start().div(Id.id("speedForm_singleEmailContentDiv")).div().img().toString(), null),

    RETURN(null, "speedForm:driverSpeed-dateLinksToolsDetailShow", null, null),

    /* Breakdown by Speed Limit */
    BREAKDOWN_OVERALL_LINK("Overall"),
    BREAKDOWN_1_30_LINK("1-30 mph"),
    BREAKDOWN_31_40_LINK("31-40 mph"),
    BREAKDOWN_41_54_LINK("41-54 mph"),
    BREAKDOWN_55_64_LINK("55-64 mph"),
    BREAKDOWN_65_80_LINK("65-80 mph"),

    BREAKDOWN_OVERALL_SCORE(null, null, Xpath.start().td(Id.id("OVERALL")).table().tbody().tr().td("2").table().tbody().tr().toString(), null),
    BREAKDOWN_1_30_SCORE(null, null, Xpath.start().td(Id.id("TWENTYONE")).table().tbody().tr().td("2").table().tbody().tr().toString(), null),
    BREAKDOWN_31_40_SCORE(null, null, Xpath.start().td(Id.id("THIRTYONE")).table().tbody().tr().td("2").table().tbody().tr().toString(), null),
    BREAKDOWN_41_54_SCORE(null, null, Xpath.start().td(Id.id("FOURTYONE")).table().tbody().tr().td("2").table().tbody().tr().toString(), null),
    BREAKDOWN_55_64_SCORE(null, null, Xpath.start().td(Id.id("FIFTYFIVE")).table().tbody().tr().td("2").table().tbody().tr().toString(), null),
    BREAKDOWN_65_80_SCORE(null, null, Xpath.start().td(Id.id("SIXTYFIVE")).table().tbody().tr().td("2").table().tbody().tr().toString(), null),

    /* Details */
    DETAILS_TITLE("Details", null, Xpath.start().span(Id.clazz("details")).toString(), null),
    DETAILS_X_OF_Y("Showing XXX to YYY of ZZZ records", "headerEvents", null, null),

    LOCATION_HEADER("Location", "driverSpeedForm:notificationsTable:address_columnheader:sortDiv", null, null),
    DATE_TIME_HEADER("Date/Time", "driverSpeedForm:notificationsTable:timeheader:sortDiv", null, null),
    POSTED_HEADER("Posted Speed", "driverSpeedForm:notificationsTable:speedLimitheader:sortDiv", null, null),
    AVERAGE_HEADER("Avg Speed", "driverSpeedForm:notificationsTable:averageSpeedheader:sortDiv", null, null),
    TOP_HEADER("Top Speed", "driverSpeedForm:notificationsTable:topSpeedheader:sortDiv", null, null),
    DISTANCE_HEADER("Distance", "driverSpeedForm:notificationsTable:distanceheader:sortDiv", null, null),

    LOCATION_ENTRY(null, "driverSpeedForm:notificationsTable:***:eventAddress", null, null),
    DATE_TIME_ENTRY(null, "driverSpeedForm:notificationsTable:***:time", null, null),

    POSTED_ENTRY_SPEED(null, "driverSpeedForm:notificationsTable:***:speedLimitValue", null, null),
    POSTED_ENTRY_SPEED_UNITS(null, null, Xpath.start().td(Id.id("driverSpeedForm:notificationsTable:***:speedLimit")).strong().toString(), null),

    POSTED_ENTRY_SBS_BUTTON(null, "driverSpeedForm:notificationsTable:***:driverSpeedSlcr", null, null),

    AVERAGE_ENTRY_SPEED(null, "driverSpeedForm:notificationsTable:***:averageSpeedValue", null, null),
    AVERAGE_ENTRY_SPEED_UNITS(null, null, Xpath.start().td(Id.id("driverSpeedForm:notificationsTable:***:averageSpeed")).strong().toString(), null),
    AVERAGE_ENTRY_PLUSMINUS(null, null, Xpath.start().td(Id.id("driverSpeedForm:notificationsTable:***:averageSpeed")).span("1").toString(), null),
    AVERAGE_ENTRY_DIFF(null, "driverSpeedForm:notificationsTable:***:averageSpeedDifference", null, null),

    TOP_ENTRY_SPEED(null, "driverSpeedForm:notificationsTable:***:topSpeedValue", null, null),
    TOP_ENTRY_SPEED_UNITS(null, null, Xpath.start().td(Id.id("driverSpeedForm:notificationsTable:***:topSpeed")).strong().toString(), null),
    TOP_ENTRY_PLUSMINUS(null, null, Xpath.start().td(Id.id("driverSpeedForm:notificationsTable:***:topSpeed")).span("1").toString(), null),
    TOP_ENTRY_DIFF(null, "driverSpeedForm:notificationsTable:***:topSpeedDifference", null, null),

    DISTANCE_ENTRY(null, "driverSpeedForm:notificationsTable:***:distance", null, null),

    EXCLUDE("exclude", "driverSpeedForm:notificationsTable:***:driverSpeedInclude", null, null),

    ;

    private String text, ID, xpath, xpath_alt, url;

    private DriverPerformanceSpeedEnum(String text, String ID, String xpath, String xpath_alt) {
        this.text = text;
        this.ID = ID;
        this.xpath = xpath;
        this.xpath_alt = xpath_alt;
    }

    private DriverPerformanceSpeedEnum(String url) {
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
