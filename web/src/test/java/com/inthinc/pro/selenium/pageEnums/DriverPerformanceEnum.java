package com.inthinc.pro.selenium.pageEnums;

import java.util.List;

import com.inthinc.pro.automation.enums.SeleniumEnumUtil;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum DriverPerformanceEnum implements SeleniumEnums {
    
    DRIVER_NAME_LINK(null, "breadcrumbForm:driverPerformancePerson"),
    BREADCRUMB_ITEM(null, "breadcrumbForm:breadcrumbitem:***:driverPerformance-dashboard"),
    
    
    /* Crash History Summary */
    CRASH_TITLE("Crash History Summary", Xpath.start().span(Id.clazz("crash"))),
    CRASHES_PER_TEXT("Crashes per million miles", Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("1").td("1")),
    CRASHES_PER_NUMBER(Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("2").td("1") ),
    CRASHES_PER_TIME_FRAME("(Last 12 months)", Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("3").td("1") ),
    TOTAL_CRASHES_TEXT("Total Crashes:", Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("1").td("2")),
    TOTAL_CRASHES_NUMBER(Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("2").td("2")),
    
    /* Overall Score */
    OVERALL_TIME_FRAME_SELECTOR(null, "dateLinksForm:driverPerformance***"),
    OVERALL_TITLE("Overall Score", Xpath.start().span(Id.clazz("overall"))),
    OVERALL_SCORE_LABEL("Overall Score", Xpath.start().div(Id.id("overallScoreBox_body")).table().tbody().tr().td("1").h4() ),
    OVERALL_SCORE_NUMBER(Xpath.start().div(Id.id("overallScoreBox_body")).table().tbody().tr().td("2").table().tbody().tr().td()),
    
    OVERALL_TOOLS(null, "dateLinksForm:toolsIcon"),
    OVERALL_EMAIL_TOOL(emailReport, "dateLinksForm:emailMenuItem:anchor"),
    OVERALL_PDF_TOOL(exportPDF, "dateLinksForm:export_menu_item:anchor"),
    
    EMAIL_HEADER("Driver - ***", "driverReportEmailModalHeader"),
    EMAIL_SUB_TITLE("E-mail this report to the following e-mail addresses.", "popupsubtitle"),
    EMAIL_TEXT("E-mail Address(es): (e-mail addresses separated by a comma)", Xpath.start().form(Id.id("driverReportEmailModal_form")).span().table().tbody().tr("1")),
    EMAIL_TEXT_AREA(null, "driverReportEmailModal_form:driverReportEmailModal_email"),
    EMAIL_CANCEL_BUTTON(cancel, "emailReportPopUpSubmit"),
    EMAIL_SUBMIT_BUTTON(email, "driverReportEmailModal_form:emailReportPopupEmail3"),
    EMAIL_X_BUTTON(Xpath.start().div(Id.id("driverReportEmailModalContentDiv")).div().img()),
    
    
    /* Speed */
    SPEED_TITLE("Speed",  Xpath.start().span(Id.clazz("speed"))),
    SPEED_TIME_FRAME(null, "speedForm:driverPerformanceSpeed-dateLinksToolsDetail***"),
    SPEED_SCORE_BOX(Xpath.start().div(Id.id("speedScoreBox_body")).table().tbody().tr().td()),
    SPEED_DETAILS(null, "speedForm:driverPerformanceSpeed-dateLinksToolsDetailShow"),
    
    /* Driving Style */
    STYLE_TITLE("Driving Style",  Xpath.start().span(Id.clazz("style"))),
    STYLE_TIME_FRAME(null, "styleForm:driverPerformanceStyle-dateLinksToolsDetail***"),
    STYLE_SCORE_BOX(Xpath.start().div(Id.id("styleScoreBox_body")).table().tbody().tr().td()),
    STYLE(null, "styleForm:driverPerformanceStyle-dateLinksToolsDetailShow"),
    
    /* SeatBelt */
    SEATBELT_TITLE("Seat Belt",  Xpath.start().span(Id.clazz("seatbelt"))),
    SEATBELT_TIME_FRAME(null, "seatBeltForm:driverPerformanceSeatBelt-dateLinksToolsDetail***"),
    SEATBELT_SCORE_BOX(Xpath.start().div(Id.id("seatBeltScoreBox_body")).table().tbody().tr().td()),
    SEATBELT_DETAILS(null, "seatBeltForm:driverPerformanceSeatBelt-dateLinksToolsDetailShow"),
    
    /* Fuel Efficiency */
    MPG_TITLE("Fuel Efficiency",  Xpath.start().span(Id.clazz("gas"))),
    MPG_TIME_FRAME(null, "mpgForm:dateLinks***"),
    
    /* Fuel Efficiency */
    COACHING_TITLE("Coaching Events",  Xpath.start().span(Id.clazz("coaching"))),
    COACHING_TIME_FRAME(null, "coachingForm:dateLinks***"),
    
    
    VIEW_ALL_TRIPS("View all trips","driverPerformanceDriverTrips")
    ; 
    


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
    
    private DriverPerformanceEnum( String text, String ID) {
        this(text, ID, "", null);
    }
    private DriverPerformanceEnum( String text, String ID, String xpath) {
        this(text, ID, xpath, null);
    }
    
    private DriverPerformanceEnum(String text, String ID, Xpath xpath, Xpath xpath_alt){
        this(text, ID, xpath.toString(), xpath_alt.toString());
    }
    
    private DriverPerformanceEnum(String text, String ID, Xpath xpath){
        this(text, ID, xpath.toString(), null);
    }
    
    private DriverPerformanceEnum(String text, Xpath xpath){
        this(text, null, xpath.toString(), null);
    }
    
    private DriverPerformanceEnum( Xpath xpath, Xpath xpath_alt) {
        this(null, null, xpath.toString(), xpath_alt.toString());
    }
    
    private DriverPerformanceEnum( Xpath xpath) {
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
    @Override
    public List<String> getLocators() {        
        return SeleniumEnumUtil.getLocators(this);
    }
}
