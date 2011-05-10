package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum DriverPerformanceEnum implements SeleniumEnums {

    DRIVER_NAME_LINK(null, "breadcrumbForm:driverPerformancePerson"),
    BREADCRUMB_ITEM(null, "breadcrumbForm:breadcrumbitem:***:driverPerformance-dashboard"),

    /* Crash History Summary */
    CRASH_TITLE("Crash History Summary", Xpath.start().span(Id.clazz("crash")).toString()),
    CRASHES_PER_TEXT("Crashes per million miles", Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("1").td("1").toString()),
    CRASHES_PER_NUMBER(null, Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("2").td("1").toString()),
    CRASHES_PER_TIME_FRAME("(Last 12 months)", Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("3").td("1").toString()),
    TOTAL_CRASHES_TEXT("Total Crashes:", Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("1").td("2").toString()),
    TOTAL_CRASHES_NUMBER(null, Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("2").td("2").toString()),

    /* Overall Score */
    OVERALL_TIME_FRAME_SELECTOR(null, "dateLinksForm:driverPerformance***"),
    OVERALL_TITLE("Overall Score", Xpath.start().span(Id.clazz("overall")).toString()),
    OVERALL_SCORE_LABEL("Overall Score", Xpath.start().div(Id.id("overallScoreBox_body")).table().tbody().tr().td("1").h4().toString()),
    OVERALL_SCORE_NUMBER(null, Xpath.start().div(Id.id("overallScoreBox_body")).table().tbody().tr().td("2").table().tbody().tr().td().toString()),

    OVERALL_TOOLS(null, "dateLinksForm:toolsIcon"),
    OVERALL_EMAIL_TOOL(emailReport, "dateLinksForm:emailMenuItem:anchor"),
    OVERALL_PDF_TOOL(exportPDF, "dateLinksForm:export_menu_item:anchor"),

    EMAIL_HEADER("Driver - ***", "driverReportEmailModalHeader"),
    EMAIL_SUB_TITLE("E-mail this report to the following e-mail addresses.", "popupsubtitle"),
    EMAIL_TEXT("E-mail Address(es): (e-mail addresses separated by a comma)", Xpath.start().form(Id.id("driverReportEmailModal_form")).span().table().tbody().tr("1").toString()),
    EMAIL_TEXT_AREA(null, "driverReportEmailModal_form:driverReportEmailModal_email"),
    EMAIL_CANCEL_BUTTON(cancel, "emailReportPopUpSubmit"),
    EMAIL_SUBMIT_BUTTON(email, "driverReportEmailModal_form:emailReportPopupEmail3"),
    EMAIL_X_BUTTON(null, Xpath.start().div(Id.id("driverReportEmailModalContentDiv")).div().img().toString()),

    /* Speed */
    SPEED_TITLE("Speed", Xpath.start().span(Id.clazz("speed")).toString()),
    SPEED_TIME_FRAME(null, "speedForm:driverPerformanceSpeed-dateLinksToolsDetail***"),
    SPEED_SCORE_BOX(null, Xpath.start().div(Id.id("speedScoreBox_body")).table().tbody().tr().td().toString()),
    SPEED_DETAILS(null, "speedForm:driverPerformanceSpeed-dateLinksToolsDetailShow"),

    /* Driving Style */
    STYLE_TITLE("Driving Style", Xpath.start().span(Id.clazz("style")).toString()),
    STYLE_TIME_FRAME(null, "styleForm:driverPerformanceStyle-dateLinksToolsDetail***"),
    STYLE_SCORE_BOX(null, Xpath.start().div(Id.id("styleScoreBox_body")).table().tbody().tr().td().toString()),
    STYLE(null, "styleForm:driverPerformanceStyle-dateLinksToolsDetailShow"),

    /* SeatBelt */
    SEATBELT_TITLE("Seat Belt", Xpath.start().span(Id.clazz("seatbelt")).toString()),
    SEATBELT_TIME_FRAME(null, "seatBeltForm:driverPerformanceSeatBelt-dateLinksToolsDetail***"),
    SEATBELT_SCORE_BOX(null, Xpath.start().div(Id.id("seatBeltScoreBox_body")).table().tbody().tr().td().toString()),
    SEATBELT_DETAILS(null, "seatBeltForm:driverPerformanceSeatBelt-dateLinksToolsDetailShow"),

    /* Fuel Efficiency */
    MPG_TITLE("Fuel Efficiency", Xpath.start().span(Id.clazz("gas")).toString()),
    MPG_TIME_FRAME(null, "mpgForm:dateLinks***"),

    /* Fuel Efficiency */
    COACHING_TITLE("Coaching Events", Xpath.start().span(Id.clazz("coaching")).toString()),
    COACHING_TIME_FRAME(null, "coachingForm:dateLinks***"),

    VIEW_ALL_TRIPS("View all trips", "driverPerformanceDriverTrips");

    private String text, url;
    private String[] IDs;
    
    private DriverPerformanceEnum(String url){
    	this.url = url;
    }
    private DriverPerformanceEnum(String text, String ...IDs){
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
