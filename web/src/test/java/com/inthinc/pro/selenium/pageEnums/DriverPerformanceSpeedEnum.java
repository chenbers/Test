package com.inthinc.pro.selenium.pageEnums;



import com.inthinc.pro.automation.enums.SeleniumEnums;

import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum DriverPerformanceSpeedEnum implements SeleniumEnums {
    /* Overall Score */
    OVERALL_TIME_FRAME_SELECTOR(null, "speedForm:driverSpeed-dateLinksToolsDetail***"),
    OVERALL_TITLE("Speed: Overall", "titleSpan"),
    OVERALL_SCORE_LABEL("Overall Score", Xpath.start().div(Id.id("overallScoreBox_body")).table().tbody().tr().td("1").h4() ),
    OVERALL_SCORE_NUMBER(Xpath.start().div(Id.id("overallScoreBox_body")).table().tbody().tr().td("2").table().tbody().tr().td()),
    
    OVERALL_TOOLS(null, "dateLinksForm:toolsIcon"),
    OVERALL_EMAIL_TOOL(emailReport, "dateLinksForm:emailMenuItem:anchor"),
    OVERALL_PDF_TOOL(exportPDF, "dateLinksForm:export_menu_item:anchor"),
    OVERALL_EXCEL_TOOL("Export to Excel", "speedForm:driverSpeed-exportExcelMenuItem:anchor"),
    
    /* Email Report Pop-up */
    EMAIL_HEADER("Driver - ***", "speedForm_singleEmailHeader"),
    EMAIL_SUB_TITLE("E-mail this report to the following e-mail addresses.", "popupsubtitle"),
    EMAIL_TEXT("E-mail Address(es): (e-mail addresses separated by a comma)", Xpath.start().form(Id.id("speedForm_singleEmail_form")).span().table().tbody().tr("1")),
    EMAIL_TEXT_AREA(null, "speedForm_singleEmail_form:speedForm_singleEmail_email"),
    EMAIL_CANCEL_BUTTON(cancel, "emailReportPopUpSubmit"),
    EMAIL_SUBMIT_BUTTON(email, "speedForm_singleEmail_form:emailReportPopupEmail3"),
    EMAIL_X_BUTTON(Xpath.start().div(Id.id("speedForm_singleEmailContentDiv")).div().img()),
    
    RETURN(null, "speedForm:driverSpeed-dateLinksToolsDetailShow"),
    
    /* Breakdown by Speed Limit*/
    BREAKDOWN_OVERALL_LINK("Overall"),
    BREAKDOWN_1_30_LINK("1-30 mph"),
    BREAKDOWN_31_40_LINK("31-40 mph"),
    BREAKDOWN_41_54_LINK("41-54 mph"),
    BREAKDOWN_55_64_LINK("55-64 mph"),
    BREAKDOWN_65_80_LINK("65-80 mph"),
    
    BREAKDOWN_OVERALL_SCORE(Xpath.start().td(Id.id("OVERALL")).table().tbody().tr().td("2").table().tbody().tr()),
    BREAKDOWN_1_30_SCORE(Xpath.start().td(Id.id("TWENTYONE")).table().tbody().tr().td("2").table().tbody().tr()),
    BREAKDOWN_31_40_SCORE(Xpath.start().td(Id.id("THIRTYONE")).table().tbody().tr().td("2").table().tbody().tr()),
    BREAKDOWN_41_54_SCORE(Xpath.start().td(Id.id("FOURTYONE")).table().tbody().tr().td("2").table().tbody().tr()),
    BREAKDOWN_55_64_SCORE(Xpath.start().td(Id.id("FIFTYFIVE")).table().tbody().tr().td("2").table().tbody().tr()),
    BREAKDOWN_65_80_SCORE(Xpath.start().td(Id.id("SIXTYFIVE")).table().tbody().tr().td("2").table().tbody().tr()),
    
    /* Details */
    DETAILS_TITLE("Details", Xpath.start().span(Id.clazz("details"))),
    DETAILS_X_OF_Y("Showing XXX to YYY of ZZZ records", "headerEvents" ),
    
    LOCATION_HEADER("Location", "driverSpeedForm:notificationsTable:address_columnheader:sortDiv"),
    DATE_TIME_HEADER("Date/Time", "driverSpeedForm:notificationsTable:timeheader:sortDiv"),
    POSTED_HEADER("Posted Speed", "driverSpeedForm:notificationsTable:speedLimitheader:sortDiv"),
    AVERAGE_HEADER("Avg Speed", "driverSpeedForm:notificationsTable:averageSpeedheader:sortDiv"),
    TOP_HEADER("Top Speed", "driverSpeedForm:notificationsTable:topSpeedheader:sortDiv"),
    DISTANCE_HEADER("Distance", "driverSpeedForm:notificationsTable:distanceheader:sortDiv"),
    
    
    LOCATION_ENTRY(null, "driverSpeedForm:notificationsTable:***:eventAddress"),
    DATE_TIME_ENTRY(null, "driverSpeedForm:notificationsTable:***:time"),
    
    POSTED_ENTRY_SPEED(null, "driverSpeedForm:notificationsTable:***:speedLimitValue"),
    POSTED_ENTRY_SPEED_UNITS(Xpath.start().td(Id.id("driverSpeedForm:notificationsTable:***:speedLimit")).strong()),
    
    POSTED_ENTRY_SBS_BUTTON(null, "driverSpeedForm:notificationsTable:***:driverSpeedSlcr"),
    
    AVERAGE_ENTRY_SPEED(null, "driverSpeedForm:notificationsTable:***:averageSpeedValue"),
    AVERAGE_ENTRY_SPEED_UNITS(Xpath.start().td(Id.id("driverSpeedForm:notificationsTable:***:averageSpeed")).strong()),
    AVERAGE_ENTRY_PLUSMINUS(Xpath.start().td(Id.id("driverSpeedForm:notificationsTable:***:averageSpeed")).span("1")),
    AVERAGE_ENTRY_DIFF(null, "driverSpeedForm:notificationsTable:***:averageSpeedDifference"),
    
    TOP_ENTRY_SPEED(null, "driverSpeedForm:notificationsTable:***:topSpeedValue"),
    TOP_ENTRY_SPEED_UNITS(Xpath.start().td(Id.id("driverSpeedForm:notificationsTable:***:topSpeed")).strong()),
    TOP_ENTRY_PLUSMINUS(Xpath.start().td(Id.id("driverSpeedForm:notificationsTable:***:topSpeed")).span("1")),
    TOP_ENTRY_DIFF(null, "driverSpeedForm:notificationsTable:***:topSpeedDifference"),
    
    DISTANCE_ENTRY(null, "driverSpeedForm:notificationsTable:***:distance"),
    
    EXCLUDE("exclude", "driverSpeedForm:notificationsTable:***:driverSpeedInclude"),
    
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
    
    private DriverPerformanceSpeedEnum( String text, String ID) {
        this(text, ID, "", null);
    }
    private DriverPerformanceSpeedEnum( String text, String ID, String xpath) {
        this(text, ID, xpath, null);
    }
    
    private DriverPerformanceSpeedEnum(String text, String ID, Xpath xpath, Xpath xpath_alt){
        this(text, ID, xpath.toString(), xpath_alt.toString());
    }
    
    private DriverPerformanceSpeedEnum(String text, String ID, Xpath xpath){
        this(text, ID, xpath.toString(), null);
    }
    
    private DriverPerformanceSpeedEnum(String text, Xpath xpath){
        this(text, null, xpath.toString(), null);
    }
    
    private DriverPerformanceSpeedEnum( Xpath xpath, Xpath xpath_alt) {
        this(null, null, xpath.toString(), xpath_alt.toString());
    }
    
    private DriverPerformanceSpeedEnum( Xpath xpath) {
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
