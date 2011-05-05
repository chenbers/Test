package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum DivisionEnum implements SeleniumEnums {
    HOME_PAGE(appUrl + "/dashboard"),

    TOOL_BUTTON(null, "executive-***_toolImage", null, null),

    OVERVIEW_TOOL_BUTTON(null, "overview_header_form:overview_tools_icon", null, null),
    OVERVIEW_SEND_BUTTON(email, "overview_emailPopup_form:emailReportPopupEmail1", Xpath.start().button(Id.contains(Id.onClick(), "overall")).span(Id.clazz("email")).toString(), null),
    OVERVIEW_EMAIL_BUTTON(emailReport, "overview_emailPopup_form:overview_emailPopup_email", Xpath.start().form(Id.contains(Id.id(), "overallScore")).span().table().tr("2").td(Id.align("left")).toString(), null),
    OVERVIEW_PDF_BUTTON(exportPDF, "overview_header_form:executive-export_menu_item:anchor", null, null),

    EMAIL_CANCEL_BUTTON(cancel, "emailReportPopUpSubmit", Xpath.start().button(Id.contains(Id.onClick(), "***")).span(Id.clazz("cancel")).toString(), null),
    EMAIL_SEND_BUTTON(email, "executive-***_singleEmail_form:emailReportPopupEmail", Xpath.start().button(Id.contains(Id.onClick(), "overall")).span(Id.clazz("email")).toString(), null),

    TOOL_EMAIL_BUTTON(emailReport, "executive-***_detail_form:executive-***_email:anchor", null, null),
    TOOL_PDF_BUTTON(exportPDF, "executive-***_detail_form:executive-***-export:anchor", null, null),

    EMAIL_TEXT_AREA(null, "executive-***_singleEmail_form:executive-***_singleEmail_email", Xpath.start().form(Id.contains(Id.id(), "overallScore")).span().table().tr("2").td(Id.align("left")).toString(), null),
    HELP_ICON(null, "overview_header_form:contextSensitiveHelpDB", Xpath.start().img(Id.title("Help ")).toString(), null),

    OVERALL_DURATION(null, "executive-overallScore_form:executive-overallScore-***", null, null),
    TREND_DURATION(null, "executive-trend_form:executive-trend-***", null, null),
    FUEL_EFFICIENCY_DURATION(null, "executive-mpgChart_form:executive-mpgChart-***", null, null),
    SPEEDING_DURATION(null, "executive-speedPercentagePanel_form:executive-speedPercentagePanel-***", null, null),
    IDLING_DURATION(null, "executive-idlingPercentagePanel_form:executive-idlingPercentagePanel-***", null, null),
    OVERALL_EXPAND(null, "executive-overallScore_details", null, null),
    TREND_EXPAND(null, "executive-trend_details", null, null),

    FUEL_EFFICIENCY_EXPAND(null, "executive-mpgChart_details", null, null),
    LIVE_FLEET_EXPAND(null, "refresh:executive-liveFleetMapRestore", null, null),

    LIVE_FLEET_REFRESH(null, "refresh:executive-liveFleetMapRefresh", null, null),
    BREADCRUMB_ITEM(null, "overview_header_form:breadcrumbitem:***:executive-overviewHeader-dashboard", Xpath.start().ul(Id.id("breadcrumb")).li("***").toString(), null),
    OVERALL_TITLE("Overall Score",null, Xpath.start().span(Id.clazz("overall")).toString(), null),
    CRASH_TITLE("Crash Stats",null, Xpath.start().span(Id.clazz("crash")).toString(), null),

    CRASHES_PER_TEXT("Crashes per million miles",null, Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("1").td("1").toString(), null),
    DAYS_SINCE_TEXT("Days since last crash",null, Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("1").td("2").toString(), null),
    MILES_SINCE_TEXT("Miles since last crash",null, Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("1").td("3").toString(), null),

    CRASHES_PER_NUMBER(null, null, Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("2").td("1").toString(), null),
    DAYS_SINCE_NUMBER(null, null, Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("2").td("2").toString(), null),
    MILES_SINCE_NUMBER(null, null, Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("2").td("3").toString(), null),

    CRASHES_PER_TIME_FRAME("(Last 12 months)",null, Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("4").td("1").toString(), null),

    SPEEDING_TITLE("Speeding % (mi) ",null, Xpath.start().div(Id.id("speedPercentageOutputPanel_body")).div().div().span(Id.clazz("line")).toString(), null),

    TOTAL_DRIVEN_TEXT("Total Distance Driven:",null, Xpath.start().div(Id.id("speedPercentagePanel_body")).table().tbody().tr("1").td("1").toString(), null),
    TOTAL_DRIVEN_NUMBER(null, "totalDistance", Xpath.start().div(Id.id("speedPercentagePanel_body")).table().tbody().tr("1").td("2").span().toString(), null),
    TOTAL_SPEEDING_TEXT("Total Distance Speeding:",null, Xpath.start().div(Id.id("speedPercentagePanel_body")).table().tbody().tr("2").td("1").toString(), null),
    TOTAL_SPEEDING_NUMBER(null, "totalSpeeding", Xpath.start().div(Id.id("speedPercentagePanel_body")).table().tbody().tr("2").td("2").toString(), null),

    TREND_TITLE("Speeding % (mi) ",null, Xpath.start().div(Id.id("trendDurationPanel_body")).div().div().span(Id.clazz("line")).toString(), null),
    TREND_SUPER_TITLE(null, null, Xpath.start().tbody(Id.id("trendTable:summaryitems:tb")).td("2").toString(), null),
    TREND_GROUP_SORT("Division/Team"/* , "trendTable:executive:***header:sortDiv" */,null, Xpath.start().th("2").div().toString(), null),
    TREND_SCORE_SORT("Score"/* , "trendTable:executive:***header:sortDiv" */,null, Xpath.start().th("3").div().toString(), null),
    TREND_CRASH_SORT("Crash/Mil", "trendTable:executive:crashesheader:sortDiv", Xpath.start().th("4").div().toString(), null),
    TREND_GROUP_LINK(null, "trendTable:executive:###:executive-trendGroup", Xpath.start().tbody(Id.id("trendTable:executive:tb")).tr("###").td("2").toString(), null),
    TREND_GROUP_SCORE(null, "trendTable:executive:###:executive-trendGroup", Xpath.start().tbody(Id.id("trendTable:executive:tb")).tr("###").td("3").toString(), null),
    TREND_GROUP_CRASH_NUMBER(null, null, Xpath.start().td(Id.id("trendTable:executive:###:crashes")).span("2").toString(), null),

    IDLING_TITLE("Speeding % (mi) ",null, Xpath.start().div(Id.id("idlePercentageOutputPanel_body")).div().div().span(Id.clazz("line")).toString(), null),
    IDLING_STAT_TEXT(null, "idlingPercentageStatsMessage", null, null),

    FUEL_EFFICIENCY_TITLE("Fuel Efficiency",null, Xpath.start().span(Id.clazz("gas")).toString(), null),
    LIVE_FLEET_TITLE("Live Fleet", null, Xpath.start().span(Id.clazz("map")).toString(), null),
    FLEET_LEGEND_TITLE("Fleet Legend", null, Xpath.start().span(Id.clazz("legend")).toString(), null),
    FLEET_LEGEND_GROUP(null, "icos2:###", Xpath.start().tr(Id.clazz("rich-table-row carlegend"), "###").td("###").toString(), null),

    ;

    private String text, ID, xpath, xpath_alt, url = null;

    private DivisionEnum(String url) {
        this.url = url;
    }

    private DivisionEnum(String text, String ID, String xpath, String xpath_alt) {
        this.text = text;
        this.ID = ID;
        if (!xpath.equals("")) {
            this.xpath = xpath;
            this.xpath_alt = xpath_alt;
        }
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
        this.text = text;
    }

    public String getURL() {
        return url;
    }
}
