package com.inthinc.pro.selenium.pageEnums;

import org.apache.commons.lang.StringEscapeUtils;

import com.inthinc.pro.automation.enums.SeleniumEnums;

import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum TeamDriverStatsEnum implements SeleniumEnums {

    /* Edit Columns */
    EDIT_COLUMNS_BUTTON(null, "teamStatisticsForm:teamStatisticsEditColumns", null, null),

    EDIT_COLUMNS_HEADER("Edit Columns", "editColumnsHeader", null, null),
    EDIT_COLUMNS_X_BUTTON(null, null, Xpath.start().div(Id.id("editColumnsContentDiv")).div().img().toString(), null),
    EDIT_COLUMNS_SAVE(save, "editColumnsForm:teamStatistics-editColumnsPopupSave", null, null),
    EDIT_COLUMNS_CANCEL(cancel, "editColumnsForm:teamStatistics-editColumnsPopupCancel", null, null),
    EDIT_COLUMNS_SUB_TITLE("The selected columns will be displayed.", "popupsubtitle", null, null),

    EDIT_COLUMNS_CHECKBOX(null, "editColumnsForm:teamStatistics-editColumnsGrid:***:teamStatistics-col", null, null),
    EDIT_COLUMNS_TEXT(null, null, Xpath.start().td("editColumnsForm:teamStatistics-editColumnsGrid:***").label().text("2").toString(), null),

    /* Export Tool */
    EXPORT_TOOLS(null, null, Xpath.start().span(Id.id("teamStatisticsForm:teamStatistics_reportToolImageId")).span("2").toString(), null),

    EXPORT_PDF_TOOL(exportPDF, "teamStatisticsForm:teamStatistics-export_menu_item:anchor", null, null),
    EXPORT_EMAIL_TOOL(emailReport, "teamStatisticsForm:teamStatistics-emailMenuItem:anchor", null, null),
    EXPORT_EXCEL_TOOL(exportExcel, "teamStatisticsForm:teamStatistics-exportExcelMEnuItem:anchor", null, null),

    EMAIL_HEADER("Team - ***", "teamStatisticsForm:teamStatistics_reportEmailModalHeader", null, null),
    EMAIL_SUB_TITLE(email + " this report to the following " + email.toLowerCase() + " addresses.", null, Xpath.start().div(Id.clazz("popupsubtitle")).toString(), null),
    EMAIL_TITLE(email + " Address(es): (" + email.toLowerCase() + " addresses separated by a comma)", null, Xpath.start().form(Id.id("teamStatisticsForm:teamStatistics_reportEmailModal_form")).span()
            .table().tbody().tr("1").toString(), null),
    EMAIL_TEXT_AREA(null, "teamStatisticsForm:teamStatistics_reportEmailModal_form:teamStatistics_reportEmailModal_email", null, null),

    EMAIL_SUBMIT(email, "teamStatisticsForm:teamStatistics_reportEmailModal_form:emailReportPopupEmail3", null, null),
    EMAIL_CANCEL(cancel, "emailReportPopUpSubmit", null, null),
    EMAIL_X_BUTTON(null, null, Xpath.start().div(Id.clazz("rich-mpnl-text rich-mpnl-controls popupControls")).img().toString(), null),

    /* Team rows */
    TEAM_NAME_HEADER("Team", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsTeamNameheader:sortDiv", null, null),
    TEAM_SCORE_HEADER("Score", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsOverallScoreheader:sortDiv", null, null),
    TEAM_TRIPS_HEADER("Trips", "teamStatisticsForm:drivers:driverTotals:j_id235header:sortDiv", null, null),
    TEAM_STOPS_HEADER("Stops", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsTripStopsheader:sortDiv", null, null),
    TEAM_DISTANCE_HEADER("Distance Driven", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsMilesDrivenheader:sortDiv", null, null),
    TEAM_DURATION_HEADER("Duration", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsDriveTimeheader:sortDiv", null, null),
    TEAM_IDLE_HEADER("Idle Time", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsIdleTimeheader:sortDiv", null, null),
    TEAM_LOW_HEADER("Low Idle", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsLoIdleheader:sortDiv", null, null),
    TEAM_HIGH_HEADER("High Idle", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsHiIdleheader:sortDiv", null, null),
    TEAM_PERCENT_HEADER(StringEscapeUtils.unescapeHtml("Idle &#37;"), "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsIdlePercentageheader:sortDiv", null, null),
    TEAM_FUEL_HEADER("Fuel Eff.", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsMpgheader:sortDiv", null, null),
    TEAM_CRASHES_HEADER("Crashes", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsCrashesheader:sortDiv", null, null),
    TEAM_SAFETY_HEADER("Safety", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsSafetyheader:sortDiv", null, null),

    TEAM_NAME_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:newTeamStatsTab-totalsTeamName", null, null),
    TEAM_SCORE_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:newTeamStatsTab-totalsOverallScore", null, null),
    TEAM_TRIPS_VALUE(null, null, Xpath.start().tr(Id.clazz("rich-subtable-row rich-subtable-firstrow tableOdd")).td("4").toString(), null),
    TEAM_STOPS_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:newTeamStatsTab-totalsTripStops", null, null),
    TEAM_DISTANCE_VALUE(null, "teamStatisticsForm:drivers:0:newTeamStatsTab-milesDriven", null, null),
    TEAM_DURATION_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:newTeamStatsTab-totalsDriveTime", null, null),
    TEAM_IDLE_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:newTeamStatsTab-totalsIdleTime", null, null),
    TEAM_LOW_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:newTeamStatsTab-totalsLoIdle", null, null),
    TEAM_HIGH_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:newTeamStatsTab-totalsHiIdle", null, null),
    TEAM_PERCENT_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:idlePercentageNonZero", null, null),
    TEAM_FUEL_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:summaryMpg", null, null),
    TEAM_CRASHES_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:newTeamStatsTab-totalsCrashes", null, null),
    TEAM_SAFETY_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:safetyGroupLink", null, null),

    DRIVER_NAME_HEADER("Driver", "teamStatisticsForm:drivers:newTeamStatsTab-driverNameheader:sortDiv", null, null),
    DRIVER_SCORE_HEADER("Score", "teamStatisticsForm:drivers:newTeamStatsTab-overallScoreheader:sortDiv", null, null),
    DRIVER_VEHICLE_HEADER("Vehicle", "teamStatisticsForm:drivers:newTeamStatsTab-vehicleheader:sortDiv", null, null),
    DRIVER_TRIPS_HEADER("Trips", "teamStatisticsForm:drivers:newTeamStatsTab-tripsheader:sortDiv", null, null),
    DRIVER_STOPS_HEADER("Stops", "teamStatisticsForm:drivers:newTeamStatsTab-tripStopsheader:sortDiv", null, null),
    DRIVER_DISTANCE_HEADER("Distance Driven", "teamStatisticsForm:drivers:newTeamStatsTab-milesDrivenheader:sortDiv", null, null),
    DRIVER_DURATION_HEADER("Duration", "teamStatisticsForm:drivers:newTeamStatsTab-driveTimeheader:sortDiv", null, null),
    DRIVER_IDLE_HEADER("Idle Time", "teamStatisticsForm:drivers:newTeamStatsTab-idleTimeheader:sortDiv", null, null),
    DRIVER_LOW_HEADER("Low Idle", "teamStatisticsForm:drivers:newTeamStatsTab-loIdleheader:sortDiv", null, null),
    DRIVER_HIGH_HEADER("High Idle", "teamStatisticsForm:drivers:newTeamStatsTab-hiIdleheader:sortDiv", null, null),
    DRIVER_PERCENT_HEADER(StringEscapeUtils.unescapeHtml("Idle &#37;"), "teamStatisticsForm:drivers:newTeamStatsTab-idlePercentageheader:sortDiv", null, null),
    DRIVER_FUEL_HEADER("Fuel Eff.", "teamStatisticsForm:drivers:newTeamStatsTab-mpgheader:sortDiv", null, null),
    DRIVER_CRASHES_HEADER("Crashes", "teamStatisticsForm:drivers:newTeamStatsTab-crashesheader:sortDiv", null, null),
    DRIVER_SAFETY_HEADER("Safety", "teamStatisticsForm:drivers:newTeamStatsTab-safetyheader:sortDiv", null, null),

    DRIVER_NAME_ENTRY(null, "teamStatisticsForm:drivers:###:newTeamStatsTab-driverName", null, null),
    DRIVER_SCORE_ENTRY(null, "teamStatisticsForm:drivers:###:newTeamStatsTab-overallScore", null, null),
    DRIVER_VEHICLE_ENTRY(null, "teamStatisticsForm:drivers:###:newTeamStatsTab-vehicle", null, null),
    DRIVER_TRIPS_ENTRY(null, "teamStatisticsForm:drivers:###:newTeamStatsTab-trips", null, null),
    DRIVER_STOPS_ENTRY(null, "teamStatisticsForm:drivers:###:newTeamStatsTab-tripStops", null, null),
    DRIVER_DISTANCE_ENTRY(null, "teamStatisticsForm:drivers:###:newTeamStatsTab-milesDriven", null, null),
    DRIVER_DURATION_ENTRY(null, "teamStatisticsForm:drivers:###:newTeamStatsTab-driveTime", null, null),
    DRIVER_IDLE_ENTRY(null, "teamStatisticsForm:drivers:###:newTeamStatsTab-idleTime", null, null),
    DRIVER_LOW_ENTRY(null, "teamStatisticsForm:drivers:###:newTeamStatsTab-loIdle", null, null),
    DRIVER_HIGH_ENTRY(null, "teamStatisticsForm:drivers:###:newTeamStatsTab-hiIdle", null, null),
    DRIVER_PERCENT_ENTRY(null, "teamStatisticsForm:drivers:###:newTeamStatsTab-idlePercentage", null, null),
    DRIVER_FUEL_ENTRY(null, "teamStatisticsForm:drivers:###:newTeamStatsTab-mpg", null, null),
    DRIVER_CRASHES_ENTRY(null, "teamStatisticsForm:drivers:###:newTeamStatsTab-crashes", null, null),
    DRIVER_SAFETY_ENTRY(null, "teamStatisticsForm:drivers:###:newTeamStatsTab-safety", null, null),

    ;

    private String text, ID, xpath, xpath_alt, url;

    private TeamDriverStatsEnum(String text, String ID, String xpath, String xpath_alt) {
        this.text = text;
        this.ID = ID;
        this.xpath = xpath;
        this.xpath_alt = xpath_alt;
    }

    private TeamDriverStatsEnum(String url) {
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
