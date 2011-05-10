package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum DivisionEnum implements SeleniumEnums {
    HOME_PAGE(appUrl + "/dashboard"),

    TOOL_BUTTON(null, "executive-***_toolImage"),

    OVERVIEW_TOOL_BUTTON(null, "overview_header_form:overview_tools_icon"),
    OVERVIEW_SEND_BUTTON(email, "overview_emailPopup_form:emailReportPopupEmail1", Xpath.start().button(Id.contains(Id.onClick(), "overall")).span(Id.clazz("email")).toString()),
    OVERVIEW_EMAIL_BUTTON(emailReport, "overview_emailPopup_form:overview_emailPopup_email", Xpath.start().form(Id.contains(Id.id(), "overallScore")).span().table().tr("2").td(Id.align("left")).toString()),
    OVERVIEW_PDF_BUTTON(exportPDF, "overview_header_form:executive-export_menu_item:anchor"),

    EMAIL_CANCEL_BUTTON(cancel, "emailReportPopUpSubmit", Xpath.start().button(Id.contains(Id.onClick(), "***")).span(Id.clazz("cancel")).toString()),
    EMAIL_SEND_BUTTON(email, "executive-***_singleEmail_form:emailReportPopupEmail", Xpath.start().button(Id.contains(Id.onClick(), "overall")).span(Id.clazz("email")).toString()),

    TOOL_EMAIL_BUTTON(emailReport, "executive-***_detail_form:executive-***_email:anchor"),
    TOOL_PDF_BUTTON(exportPDF, "executive-***_detail_form:executive-***-export:anchor"),

    EMAIL_TEXT_AREA(null, "executive-***_singleEmail_form:executive-***_singleEmail_email", Xpath.start().form(Id.contains(Id.id(), "overallScore")).span().table().tr("2").td(Id.align("left")).toString()),
    HELP_ICON(null, "overview_header_form:contextSensitiveHelpDB", Xpath.start().img(Id.title("Help ")).toString()),

    OVERALL_DURATION(null, "executive-overallScore_form:executive-overallScore-***"),
    TREND_DURATION(null, "executive-trend_form:executive-trend-***"),
    FUEL_EFFICIENCY_DURATION(null, "executive-mpgChart_form:executive-mpgChart-***"),
    SPEEDING_DURATION(null, "executive-speedPercentagePanel_form:executive-speedPercentagePanel-***"),
    IDLING_DURATION(null, "executive-idlingPercentagePanel_form:executive-idlingPercentagePanel-***"),
    OVERALL_EXPAND(null, "executive-overallScore_details"),
    TREND_EXPAND(null, "executive-trend_details"),

    FUEL_EFFICIENCY_EXPAND(null, "executive-mpgChart_details"),
    LIVE_FLEET_EXPAND(null, "refresh:executive-liveFleetMapRestore"),

    LIVE_FLEET_REFRESH(null, "refresh:executive-liveFleetMapRefresh"),
    BREADCRUMB_ITEM(null, "overview_header_form:breadcrumbitem:***:executive-overviewHeader-dashboard", Xpath.start().ul(Id.id("breadcrumb")).li("***").toString()),
    OVERALL_TITLE("Overall Score", Xpath.start().span(Id.clazz("overall")).toString()),
    CRASH_TITLE("Crash Stats", Xpath.start().span(Id.clazz("crash")).toString()),

    CRASHES_PER_TEXT("Crashes per million miles", Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("1").td("1").toString()),
    DAYS_SINCE_TEXT("Days since last crash", Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("1").td("2").toString()),
    MILES_SINCE_TEXT("Miles since last crash", Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("1").td("3").toString()),

    CRASHES_PER_NUMBER(null, Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("2").td("1").toString()),
    DAYS_SINCE_NUMBER(null, Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("2").td("2").toString()),
    MILES_SINCE_NUMBER(null, Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("2").td("3").toString()),

    CRASHES_PER_TIME_FRAME("(Last 12 months)", Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("4").td("1").toString()),

    SPEEDING_TITLE("Speeding % (mi) ", Xpath.start().div(Id.id("speedPercentageOutputPanel_body")).div().div().span(Id.clazz("line")).toString()),

    TOTAL_DRIVEN_TEXT("Total Distance Driven:", Xpath.start().div(Id.id("speedPercentagePanel_body")).table().tbody().tr("1").td("1").toString()),
    TOTAL_DRIVEN_NUMBER(null, "totalDistance", Xpath.start().div(Id.id("speedPercentagePanel_body")).table().tbody().tr("1").td("2").span().toString()),
    TOTAL_SPEEDING_TEXT("Total Distance Speeding:", Xpath.start().div(Id.id("speedPercentagePanel_body")).table().tbody().tr("2").td("1").toString()),
    TOTAL_SPEEDING_NUMBER(null, "totalSpeeding", Xpath.start().div(Id.id("speedPercentagePanel_body")).table().tbody().tr("2").td("2").toString()),

    TREND_TITLE("Speeding % (mi) ", Xpath.start().div(Id.id("trendDurationPanel_body")).div().div().span(Id.clazz("line")).toString()),
    TREND_SUPER_TITLE(null, Xpath.start().tbody(Id.id("trendTable:summaryitems:tb")).td("2").toString()),
    TREND_GROUP_SORT("Division/Team"/* , "trendTable:executive:***header:sortDiv" */, Xpath.start().th("2").div().toString()),
    TREND_SCORE_SORT("Score"/* , "trendTable:executive:***header:sortDiv" */, Xpath.start().th("3").div().toString()),
    TREND_CRASH_SORT("Crash/Mil", "trendTable:executive:crashesheader:sortDiv", Xpath.start().th("4").div().toString()),
    TREND_GROUP_LINK(null, "trendTable:executive:###:executive-trendGroup", Xpath.start().tbody(Id.id("trendTable:executive:tb")).tr("###").td("2").toString()),
    TREND_GROUP_SCORE(null, "trendTable:executive:###:executive-trendGroup", Xpath.start().tbody(Id.id("trendTable:executive:tb")).tr("###").td("3").toString()),
    TREND_GROUP_CRASH_NUMBER(null, Xpath.start().td(Id.id("trendTable:executive:###:crashes")).span("2").toString()),

    IDLING_TITLE("Speeding % (mi) ", Xpath.start().div(Id.id("idlePercentageOutputPanel_body")).div().div().span(Id.clazz("line")).toString()),
    IDLING_STAT_TEXT(null, "idlingPercentageStatsMessage"),

    FUEL_EFFICIENCY_TITLE("Fuel Efficiency", Xpath.start().span(Id.clazz("gas")).toString()),
    LIVE_FLEET_TITLE("Live Fleet", Xpath.start().span(Id.clazz("map")).toString()),
    FLEET_LEGEND_TITLE("Fleet Legend", Xpath.start().span(Id.clazz("legend")).toString()),
    FLEET_LEGEND_GROUP(null, "icos2:###", Xpath.start().tr(Id.clazz("rich-table-row carlegend"), "###").td("###").toString()),

    ;

    private String text, url;
    private String[] IDs;
    
    private DivisionEnum(String url){
    	this.url = url;
    }
    private DivisionEnum(String text, String ...IDs){
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
