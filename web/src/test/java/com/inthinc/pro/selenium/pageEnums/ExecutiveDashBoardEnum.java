package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum ExecutiveDashBoardEnum implements SeleniumEnums {
    HOME_PAGE(appUrl + "/dashboard"),

    TOOL_BUTTON(null, "***_toolImage"),

    OVERVIEW_TOOL_BUTTON(null, "overview_header_form:overview_tools_icon"),
    OVERVIEW_EMAIL_BUTTON(emailReport, "overview_header_form:executive-_email:anchor"),
    OVERVIEW_PDF_BUTTON(exportPDF, "overview_header_form:executive-export_menu_item:anchor"),


    TOOL_EMAIL_BUTTON(emailReport, "***_detail_form:***_email:anchor"),
    TOOL_PDF_BUTTON(exportPDF, "***_detail_form:***-export:anchor"),

    OVERALL_DURATION(null, "executive-overallScore_form:executive-overallScore-***"),
    TREND_DURATION(null, "executive-trend_form:executive-trend-***"),
    FUEL_EFFICIENCY_DURATION(null, "executive-mpgChart_form:executive-mpgChart-***"),
    SPEEDING_DURATION(null, "executive-speedPercentagePanel_form:executive-speedPercentagePanel-***"),
    IDLING_DURATION(null, "executive-idlingPercentagePanel_form:executive-idlingPercentagePanel-***"),
    
    EXPAND(null, "***_details"),
    EXPAND_LIVE_FLEET(null, "refresh:executive-liveFleetMapRestore"),

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

    TOTAL_DRIVEN_TEXT("Total Distance Driven:", "//span[@id='totalDistance']/../td[1]"),
    TOTAL_DRIVEN_NUMBER(null, "totalDistance"),
    TOTAL_SPEEDING_TEXT("Total Distance Speeding:", "//span[@id='totalSpeeding']/../td[1]"),
    TOTAL_SPEEDING_NUMBER(null, "totalSpeeding"),

    TREND_TITLE("Trend", Xpath.start().div(Id.id("trendDurationPanel_body")).div().div().span(Id.clazz("line")).toString()),
    TREND_SUPER_TITLE(null, Xpath.start().tbody(Id.id("trendTable:summaryitems:tb")).td("2").toString()),
    TREND_GROUP_SORT("Division/Team"/* , "trendTable:executive:***header:sortDiv" */, Xpath.start().th("2").div().toString()),
    TREND_SCORE_SORT("Score"/* , "trendTable:executive:***header:sortDiv" */, Xpath.start().th("3").div().toString()),
    TREND_CRASH_SORT("Crash/Mil", "trendTable:executive:crashesheader:sortDiv", Xpath.start().th("4").div().toString()),
    
    TREND_GROUP_LINK(null, "trendTable:executive:###:executive-trendGroup", Xpath.start().tbody(Id.id("trendTable:executive:tb")).tr("###").td("2").toString()),
    TREND_GROUP_SCORE(null, Xpath.start().tbody(Id.id("trendTable:executive:tb")).tr("###").td("3").toString()),
    TREND_GROUP_CRASH_NUMBER(null, Xpath.start().td(Id.id("trendTable:executive:###:crashes")).span("2").toString()),

    IDLING_TITLE("Idling Percentage", Xpath.start().div(Id.id("idlePercentageOutputPanel_body")).div().div().span(Id.clazz("line")).toString()),
    IDLING_STAT_TEXT(null, "idlingPercentageStatsMessage"),

    FUEL_EFFICIENCY_TITLE("Fuel Efficiency", Xpath.start().span(Id.clazz("gas")).toString()),
    LIVE_FLEET_TITLE("Live Fleet", Xpath.start().span(Id.clazz("map")).toString()),
    FLEET_LEGEND_TITLE("Fleet Legend", Xpath.start().span(Id.clazz("legend")).toString()),
    FLEET_LEGEND_GROUP(null, "icos2:###", Xpath.start().tr(Id.clazz("rich-table-row carlegend"), "###").td("###").toString()),
    
    OVERALL_SCORE(null, "//div[@class='overall_score_panel']/div/table/tbody/tr/td"),
    OVERALL_SCORE_LABEL("OVERALL SCORE", "//div[@class='overall_score_panel']/div/text()"),
    
    
    IDLING_STATS_NOTE("*Statistics reflect XXX out of YYY vehicles that are reporting idling statistics.", "idlingPercentageStatsMessage"),
    
    TOTAL_DURATION_TEXT("Total Duration:", "//span[@id='totalDriving']/../td[1]"),
    TOTAL_DURATION_VALUE(null, "totalDriving"),
    TOTAL_TIME_IDLING_TEXT("Total Time Idling:", "//span[@id='totalIdling']/../td[1]"),
    TOTAL_TIME_IDLING_VALUE(null, "totalIdling"),
    

    ;

    private String text, url;
    private String[] IDs;
    
    private ExecutiveDashBoardEnum(String url){
    	this.url = url;
    }
    private ExecutiveDashBoardEnum(String text, String ...IDs){
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
