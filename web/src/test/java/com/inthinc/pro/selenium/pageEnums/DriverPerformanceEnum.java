package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum DriverPerformanceEnum implements SeleniumEnums {

    DRIVER_NAME_LINK(null, "breadcrumbForm:driverPerformancePerson", null, null),
    BREADCRUMB_ITEM(null, "breadcrumbForm:breadcrumbitem:***:driverPerformance-dashboard", null, null),

    /* Crash History Summary */
    CRASH_TITLE("Crash History Summary", null, Xpath.start().span(Id.clazz("crash")).toString(), null),
    CRASHES_PER_TEXT("Crashes per million miles", null, Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("1").td("1").toString(), null),
    CRASHES_PER_NUMBER(null, null, Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("2").td("1").toString(), null),
    CRASHES_PER_TIME_FRAME("(Last 12 months)", null, Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("3").td("1").toString(), null),
    TOTAL_CRASHES_TEXT("Total Crashes:", null, Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("1").td("2").toString(), null),
    TOTAL_CRASHES_NUMBER(null, null, Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("2").td("2").toString(), null),

    /* Overall Score */
    OVERALL_TIME_FRAME_SELECTOR(null, "dateLinksForm:driverPerformance***", null, null),
    OVERALL_TITLE("Overall Score", null, Xpath.start().span(Id.clazz("overall")).toString(), null),
    OVERALL_SCORE_LABEL("Overall Score", null, Xpath.start().div(Id.id("overallScoreBox_body")).table().tbody().tr().td("1").h4().toString(), null),
    OVERALL_SCORE_NUMBER(null, null, Xpath.start().div(Id.id("overallScoreBox_body")).table().tbody().tr().td("2").table().tbody().tr().td().toString(), null),

    OVERALL_TOOLS(null, "dateLinksForm:toolsIcon", null, null),
    OVERALL_EMAIL_TOOL(emailReport, "dateLinksForm:emailMenuItem:anchor", null, null),
    OVERALL_PDF_TOOL(exportPDF, "dateLinksForm:export_menu_item:anchor", null, null),

    EMAIL_HEADER("Driver - ***", "driverReportEmailModalHeader", null, null),
    EMAIL_SUB_TITLE("E-mail this report to the following e-mail addresses.", "popupsubtitle", null, null),
    EMAIL_TEXT("E-mail Address(es): (e-mail addresses separated by a comma)", null, Xpath.start().form(Id.id("driverReportEmailModal_form")).span().table().tbody().tr("1").toString(), null),
    EMAIL_TEXT_AREA(null, "driverReportEmailModal_form:driverReportEmailModal_email", null, null),
    EMAIL_CANCEL_BUTTON(cancel, "emailReportPopUpSubmit", null, null),
    EMAIL_SUBMIT_BUTTON(email, "driverReportEmailModal_form:emailReportPopupEmail3", null, null),
    EMAIL_X_BUTTON(null, null, Xpath.start().div(Id.id("driverReportEmailModalContentDiv")).div().img().toString(), null),

    /* Speed */
    SPEED_TITLE("Speed", null, Xpath.start().span(Id.clazz("speed")).toString(), null),
    SPEED_TIME_FRAME(null, "speedForm:driverPerformanceSpeed-dateLinksToolsDetail***", null, null),
    SPEED_SCORE_BOX(null, null, Xpath.start().div(Id.id("speedScoreBox_body")).table().tbody().tr().td().toString(), null),
    SPEED_DETAILS(null, "speedForm:driverPerformanceSpeed-dateLinksToolsDetailShow", null, null),

    /* Driving Style */
    STYLE_TITLE("Driving Style", null, Xpath.start().span(Id.clazz("style")).toString(), null),
    STYLE_TIME_FRAME(null, "styleForm:driverPerformanceStyle-dateLinksToolsDetail***", null, null),
    STYLE_SCORE_BOX(null, null, Xpath.start().div(Id.id("styleScoreBox_body")).table().tbody().tr().td().toString(), null),
    STYLE(null, "styleForm:driverPerformanceStyle-dateLinksToolsDetailShow", null, null),

    /* SeatBelt */
    SEATBELT_TITLE("Seat Belt", null, Xpath.start().span(Id.clazz("seatbelt")).toString(), null),
    SEATBELT_TIME_FRAME(null, "seatBeltForm:driverPerformanceSeatBelt-dateLinksToolsDetail***", null, null),
    SEATBELT_SCORE_BOX(null, null, Xpath.start().div(Id.id("seatBeltScoreBox_body")).table().tbody().tr().td().toString(), null),
    SEATBELT_DETAILS(null, "seatBeltForm:driverPerformanceSeatBelt-dateLinksToolsDetailShow", null, null),

    /* Fuel Efficiency */
    MPG_TITLE("Fuel Efficiency", null, Xpath.start().span(Id.clazz("gas")).toString(), null),
    MPG_TIME_FRAME(null, "mpgForm:dateLinks***", null, null),

    /* Fuel Efficiency */
    COACHING_TITLE("Coaching Events", null, Xpath.start().span(Id.clazz("coaching")).toString(), null),
    COACHING_TIME_FRAME(null, "coachingForm:dateLinks***", null, null),

    VIEW_ALL_TRIPS("View all trips", "driverPerformanceDriverTrips", null, null);

    private String text, ID, xpath, xpath_alt, url;

    private DriverPerformanceEnum(String text, String ID, String xpath, String xpath_alt) {
        this.text = text;
        this.ID = ID;
        this.xpath = xpath;
        this.xpath_alt = xpath_alt;
    }

    private DriverPerformanceEnum(String url) {
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
