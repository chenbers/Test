package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum DriverPerformanceStyleEnum implements SeleniumEnums {
    
    /* Overall */
    
    OVERALL_TIME_FRAME_SELECTOR(null, "styleForm:driverStyle-dateLinksToolsDetail***"),
    OVERALL_TITLE("Driving Style: Overall", "titleSpan"),
    OVERALL_SCORE_LABEL("Overall Score", Xpath.start().div(Id.id("overallScoreBox_body")).table().tbody().tr().td("1").h4() ),
    OVERALL_SCORE_NUMBER(Xpath.start().div(Id.id("overallScoreBox_body")).table().tbody().tr().td("2").table().tbody().tr().td()),
    
    OVERALL_TOOLS(null, "styleForm:toolsIcon"),
    OVERALL_EMAIL_TOOL(emailReport, "dateLinksForm:emailMenuItem:anchor"),
    OVERALL_PDF_TOOL(exportPDF, "dateLinksForm:export_menu_item:anchor"),
    OVERALL_EXCEL_TOOL("Export to Excel", "styleForm:driverStyle-exportExcelMenuItem:anchor"),
    
    /* Email Report Pop-up */
    EMAIL_HEADER("Driver - ***", "styleForm_singleEmailHeader"),
    EMAIL_SUB_TITLE("E-mail this report to the following e-mail addresses.", "popupsubtitle"),
    EMAIL_TEXT("E-mail Address(es): (e-mail addresses separated by a comma)", Xpath.start().form(Id.id("styleForm_singleEmail_form")).span().table().tbody().tr("1")),
    EMAIL_TEXT_AREA(null, "styleForm_singleEmail_form:styleForm_singleEmail_email"),
    EMAIL_CANCEL_BUTTON(cancel, "emailReportPopUpSubmit"),
    EMAIL_SUBMIT_BUTTON(email, "styleForm_singleEmail_form:emailReportPopupEmail3"),
    EMAIL_X_BUTTON(Xpath.start().div(Id.id("styleForm_singleEmailContentDiv")).div().img()),
    
    RETURN(null, "styleForm:driverStyle-dateLinksToolsDetailShow"),
    
    /* Breakdown by Driving Style */
    
    BREAKDOWN_OVERALL_LINK("Overall"),
    BREAKDOWN_HARD_BRAKE_LINK("Hard Brake"),
    BREAKDOWN_HARD_ACCEL_LINK("Hard Acceleration"),
    BREAKDOWN_UNSAFE_TURN_LINK("Unsafe Turn"),
    BREAKDOWN_HARD_BUMP_LINK("Hard Bump"),
    
    BREAKDOWN_OVERALL_SCORE(Xpath.start().td(Id.id("OVERALL")).table().tbody().tr().td("2").table().tbody().tr()),
    BREAKDOWN_HARD_BRAKE_SCORE(Xpath.start().td(Id.id("BRAKE")).table().tbody().tr().td("2").table().tbody().tr()),
    BREAKDOWN_HARD_ACCEL_SCORE(Xpath.start().td(Id.id("ACCELERATE")).table().tbody().tr().td("2").table().tbody().tr()),
    BREAKDOWN_UNSAFE_TURN_SCORE(Xpath.start().td(Id.id("TURN")).table().tbody().tr().td("2").table().tbody().tr()),
    BREAKDOWN_HARD_BUMP_SCORE(Xpath.start().td(Id.id("BUMP")).table().tbody().tr().td("2").table().tbody().tr()),
    
    /* Details */
    DETAILS_TITLE("Details", Xpath.start().span(Id.clazz("details"))),
    DETAILS_X_OF_Y("Showing XXX to YYY of ZZZ records", "headerEvents" ),
    
    LOCATION_HEADER("Location", "driverStyleEventTable:notificationsTable:address_columnheader:sortDiv"),
    DATE_TIME_HEADER("Date/Time", "driverStyleEventTable:notificationsTable:timeheader:sortDiv"),
    EVENT_HEADER("Posted Speed", "driverStyleEventTable:notificationsTable:typeheader:sortDiv"),
    SPEED_HEADER("Avg Speed", "driverStyleEventTable:notificationsTable:speedheader:sortDiv"),
    SEVERITY_HEADER("Top Speed", "driverStyleEventTable:notificationsTable:severityheader:sortDiv"),
    
    LOCATION_ENTRY(null, "driverStyleEventTable:notificationsTable:***:driverStyleShow"),
    DATE_TIME_ENTRY(null, "driverStyleEventTable:notificationsTable:***:time"),
    EVENT_ENTRY(null, "driverStyleEventTable:notificationsTable:***:type"),
    
    SPEED_ENTRY(null, "driverStyleEventTable:notificationsTable:***:speed"),
    
    SEVERITY_ENTRY_SEVERITY(Xpath.start().td(Id.id("driverStyleEventTable:notificationsTable:***:severity")).tbody().tr("1")),
    
    EXCLUDE("exclude", "driverStyleEventTable:notificationsTable:***:driverStyleInclude"),
    
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
    
    private DriverPerformanceStyleEnum( String text, String ID) {
        this(text, ID, "", null);
    }
    private DriverPerformanceStyleEnum( String text, String ID, String xpath) {
        this(text, ID, xpath, null);
    }
    
    private DriverPerformanceStyleEnum(String text, String ID, Xpath xpath, Xpath xpath_alt){
        this(text, ID, xpath.toString(), xpath_alt.toString());
    }
    
    private DriverPerformanceStyleEnum(String text, String ID, Xpath xpath){
        this(text, ID, xpath.toString(), null);
    }
    
    private DriverPerformanceStyleEnum(String text, Xpath xpath){
        this(text, null, xpath.toString(), null);
    }
    
    private DriverPerformanceStyleEnum( Xpath xpath, Xpath xpath_alt) {
        this(null, null, xpath.toString(), xpath_alt.toString());
    }
    
    private DriverPerformanceStyleEnum( Xpath xpath) {
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
