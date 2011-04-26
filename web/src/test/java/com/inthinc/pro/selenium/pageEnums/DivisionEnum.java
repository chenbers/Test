package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum DivisionEnum implements SeleniumEnums {
    HOME_PAGE(appUrl+"/dashboard"),
    
    TOOL_BUTTON(null, "executive-***_toolImage"),
    
    OVERVIEW_TOOL_BUTTON(null, "overview_header_form:overview_tools_icon"),
    
    OVERVIEW_SEND_BUTTON(email,"overview_emailPopup_form:emailReportPopupEmail1", 
            Xpath.start().button(Id.contains(Id.onClick(), "overall")).span(Id.clazz("email"))),
            
    OVERVIEW_EMAIL_BUTTON(emailReport, "overview_emailPopup_form:overview_emailPopup_email", 
            Xpath.start().form(Id.contains(Id.id(),"overallScore")).span().table().tr("2").td(Id.align("left"))),
            
    OVERVIEW_PDF_BUTTON(exportPDF, "overview_header_form:executive-export_menu_item:anchor"),
    
    EMAIL_CANCEL_BUTTON(cancel, "emailReportPopUpSubmit", Xpath.start().button(Id.contains(Id.onClick(), "***")).span(Id.clazz("cancel"))),
    
    EMAIL_SEND_BUTTON(email, "executive-***_singleEmail_form:emailReportPopupEmail", 
            Xpath.start().button(Id.contains(Id.onClick(), "overall")).span(Id.clazz("email"))),
            
    TOOL_EMAIL_BUTTON(emailReport, "executive-***_detail_form:executive-***_email:anchor"),
    
    TOOL_PDF_BUTTON(exportPDF, "executive-***_detail_form:executive-***-export:anchor"),
    
    EMAIL_TEXT_AREA(null,"executive-***_singleEmail_form:executive-***_singleEmail_email", 
            Xpath.start().form(Id.contains(Id.id(),"overallScore")).span().table().tr("2").td(Id.align("left"))),
    
       
    HELP_ICON(null, "overview_header_form:contextSensitiveHelpDB", Xpath.start().img(Id.title("Help "))),
    
    
    OVERALL_DURATION(null,"executive-overallScore_form:executive-overallScore-***"),
    
    TREND_DURATION(null,"executive-trend_form:executive-trend-***"),
    
    FUEL_EFFICIENCY_DURATION(null,"executive-mpgChart_form:executive-mpgChart-***"),
    
    SPEEDING_DURATION(null,"executive-speedPercentagePanel_form:executive-speedPercentagePanel-***"),
    
    IDLING_DURATION(null,"executive-idlingPercentagePanel_form:executive-idlingPercentagePanel-***"),  
    
    
    
    OVERALL_EXPAND(null,"executive-overallScore_details"),
    
    TREND_EXPAND(null,"executive-trend_details"),
    
    FUEL_EFFICIENCY_EXPAND(null,"executive-mpgChart_details"),
    
    LIVE_FLEET_EXPAND(null,"refresh:executive-liveFleetMapRestore"),
    
    
    LIVE_FLEET_REFRESH(null,"refresh:executive-liveFleetMapRefresh"),
  

    
    BREADCRUMB_ITEM(null, "overview_header_form:breadcrumbitem:***:executive-overviewHeader-dashboard", 
            Xpath.start().ul(Id.id("breadcrumb")).li("***")),
            
            
            
    OVERALL_TITLE("Overall Score", Xpath.start().span(Id.clazz("overall"))),
    
    CRASH_TITLE("Crash Stats", Xpath.start().span(Id.clazz("crash"))),
    
    
    CRASHES_PER_TEXT("Crashes per million miles", Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("1").td("1")),
    
    DAYS_SINCE_TEXT("Days since last crash", Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("1").td("2")),
    
    MILES_SINCE_TEXT("Miles since last crash", Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("1").td("3")),
    
    
    CRASHES_PER_NUMBER(Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("2").td("1")),
    
    DAYS_SINCE_NUMBER(Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("2").td("2")),
    
    MILES_SINCE_NUMBER(Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("2").td("3")),
    
    
    CRASHES_PER_TIME_FRAME("(Last 12 months)", Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("4").td("1")),
    
    SPEEDING_TITLE("Speeding % (mi) ", Xpath.start().div(Id.id("speedPercentageOutputPanel_body")).div().div().span(Id.clazz("line"))),
    
    TOTAL_DRIVEN_TEXT("Total Distance Driven:", Xpath.start().div(Id.id("speedPercentagePanel_body")).table().tbody().tr("1").td("1")),
    
    TOTAL_DRIVEN_NUMBER(null, "totalDistance", Xpath.start().div(Id.id("speedPercentagePanel_body")).table().tbody().tr("1").td("2").span()),
    
    TOTAL_SPEEDING_TEXT("Total Distance Speeding:", Xpath.start().div(Id.id("speedPercentagePanel_body")).table().tbody().tr("2").td("1")),
    
    TOTAL_SPEEDING_NUMBER(null, "totalSpeeding", Xpath.start().div(Id.id("speedPercentagePanel_body")).table().tbody().tr("2").td("2")),
    
    
    TREND_TITLE("Speeding % (mi) ", Xpath.start().div(Id.id("trendDurationPanel_body")).div().div().span(Id.clazz("line"))),
    
    TREND_SUPER_TITLE(Xpath.start().tbody(Id.id("trendTable:summaryitems:tb")).td("2")),
    
    TREND_GROUP_SORT("Division/Team", "trendTable:executive:***header", Xpath.start().th("2").div()),
    
    TREND_SCORE_SORT("Score", "trendTable:executive:***header", Xpath.start().th("3").div()),
    
    TREND_CRASH_SORT("Crash/Mil", "trendTable:executive:crashesheader", Xpath.start().th("4").div()),
    
    
    TREND_GROUP_LINK(null, "trendTable:executive:***:executive-trendGroup", 
            Xpath.start().tbody(Id.id("trendTable:executive:tb")).tr("***").td("2")),
            
    TREND_GROUP_SCORE(null, "trendTable:executive:***:executive-trendGroup", 
            Xpath.start().tbody(Id.id("trendTable:executive:tb")).tr("***").td("3")),
       
    TREND_GROUP_CRASH_NUMBER(null, "trendTable:executive:***:executive-trendGroup", 
            Xpath.start().tbody(Id.id("trendTable:executive:tb")).tr("***").td("4")),
    
    
    IDLING_TITLE("Speeding % (mi) ", Xpath.start().div(Id.id("idlePercentageOutputPanel_body")).div().div().span(Id.clazz("line"))),
    
    IDLING_STAT_TEXT(null, "idlingPercentageStatsMessage"),
    
    
    FUEL_EFFICIENCY_TITLE("Fuel Efficiency", Xpath.start().span(Id.clazz("gas"))),
    
    
    
    LIVE_FLEET_TITLE("Live Fleet", Xpath.start().span(Id.clazz("map"))),
    
    
    FLEET_LEGEND_TITLE("Fleet Legend", Xpath.start().span(Id.clazz("legend"))),
    
    FLEET_LEGEND_GROUP(null, "icos2:***", Xpath.start().tr(Id.clazz("rich-table-row carlegend"), "***").td("***")),
    
    ; 
  
    private String text, ID, xpath, xpath_alt, url=null;
    
    private DivisionEnum(String url){
        this.url = url;
    }
    
    private DivisionEnum( String text, String ID, String xpath, String xpath_alt) {
        this.text=text;
        this.ID=ID;
        if(!xpath.equals("")){
            this.xpath=xpath;
            this.xpath_alt=xpath_alt;
        }
    }
    
    private DivisionEnum( String text, String ID) {
        this(text, ID, "", null);
    }
    private DivisionEnum( String text, String ID, String xpath) {
        this(text, ID, xpath, null);
    }
    
    private DivisionEnum(String text, String ID, Xpath xpath, Xpath xpath_alt){
        this(text, ID, xpath.toString(), xpath_alt.toString());
    }
    
    private DivisionEnum(String text, String ID, Xpath xpath){
        this(text, ID, xpath.toString(), null);
    }
    
    private DivisionEnum(String text, Xpath xpath){
        this(text, null, xpath.toString(), null);
    }
    
    private DivisionEnum( Xpath xpath, Xpath xpath_alt) {
        this(null, null, xpath.toString(), xpath_alt.toString());
    }
    
    private DivisionEnum( Xpath xpath) {
        this(null, null, xpath.toString(), null);
    }

    public String getID() {
        return this.ID;
    }

    public String getText() {
        return this.text;
    }

    public String getXpath() {
        return this.xpath;
    }

    public String getXpath_alt() {
        return this.xpath_alt;
    }

    public void setText(String text) {
        this.text=text;
    }

    public String getURL() {
        return url;
    }

}
