package com.inthinc.pro.selenium.pageEnums;

import org.apache.commons.lang.StringEscapeUtils;

import com.inthinc.pro.automation.enums.SeleniumEnums;

import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum TeamDriverStatsEnum implements SeleniumEnums {

    /* Edit Columns */
    EDIT_COLUMNS_BUTTON(null, "teamStatisticsForm:teamStatisticsEditColumns"),

    EDIT_COLUMNS_HEADER("Edit Columns", "editColumnsHeader"),
    EDIT_COLUMNS_X_BUTTON(null, Xpath.start().div(Id.id("editColumnsContentDiv")).div().img().toString()),
    EDIT_COLUMNS_SAVE(save, "editColumnsForm:teamStatistics-editColumnsPopupSave"),
    EDIT_COLUMNS_CANCEL(cancel, "editColumnsForm:teamStatistics-editColumnsPopupCancel"),
    EDIT_COLUMNS_SUB_TITLE("The selected columns will be displayed.", "popupsubtitle"),

    EDIT_COLUMNS_CHECKBOX(null, "editColumnsForm:teamStatistics-editColumnsGrid:***:teamStatistics-col"),
    EDIT_COLUMNS_TEXT(null, Xpath.start().td("editColumnsForm:teamStatistics-editColumnsGrid:***").label().text("2").toString()),

    /* Export Tool */
    EXPORT_TOOLS(null, Xpath.start().span(Id.id("teamStatisticsForm:teamStatistics_reportToolImageId")).span("2").toString()),

    EXPORT_PDF_TOOL(exportPDF, "teamStatisticsForm:teamStatistics-export_menu_item:anchor"),
    EXPORT_EMAIL_TOOL(emailReport, "teamStatisticsForm:teamStatistics-emailMenuItem:anchor"),
    EXPORT_EXCEL_TOOL(exportExcel, "teamStatisticsForm:teamStatistics-exportExcelMEnuItem:anchor"),

    EMAIL_HEADER("Team - ***", "teamStatisticsForm:teamStatistics_reportEmailModalHeader"),
    EMAIL_SUB_TITLE(email + " this report to the following " + email.toLowerCase() + " addresses.", Xpath.start().div(Id.clazz("popupsubtitle")).toString()),
    EMAIL_TITLE(email + " Address(es): (" + email.toLowerCase() + " addresses separated by a comma)", Xpath.start().form(Id.id("teamStatisticsForm:teamStatistics_reportEmailModal_form")).span()
            .table().tbody().tr("1").toString()),
    EMAIL_TEXT_AREA(null, "teamStatisticsForm:teamStatistics_reportEmailModal_form:teamStatistics_reportEmailModal_email"),

    EMAIL_SUBMIT(email, "teamStatisticsForm:teamStatistics_reportEmailModal_form:emailReportPopupEmail3"),
    EMAIL_CANCEL(cancel, "emailReportPopUpSubmit"),
    EMAIL_X_BUTTON(null, Xpath.start().div(Id.clazz("rich-mpnl-text rich-mpnl-controls popupControls")).img().toString()),

    /* Team rows */
    TEAM_NAME_HEADER("Team", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsTeamNameheader:sortDiv"),
    TEAM_SCORE_HEADER("Score", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsOverallScoreheader:sortDiv"),
    TEAM_TRIPS_HEADER("Trips", "teamStatisticsForm:drivers:driverTotals:j_id235header:sortDiv"),
    TEAM_STOPS_HEADER("Stops", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsTripStopsheader:sortDiv"),
    TEAM_DISTANCE_HEADER("Distance Driven", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsMilesDrivenheader:sortDiv"),
    TEAM_DURATION_HEADER("Duration", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsDriveTimeheader:sortDiv"),
    TEAM_IDLE_HEADER("Idle Time", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsIdleTimeheader:sortDiv"),
    TEAM_LOW_HEADER("Low Idle", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsLoIdleheader:sortDiv"),
    TEAM_HIGH_HEADER("High Idle", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsHiIdleheader:sortDiv"),
    TEAM_PERCENT_HEADER(StringEscapeUtils.unescapeHtml("Idle &#37;"), "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsIdlePercentageheader:sortDiv"),
    TEAM_FUEL_HEADER("Fuel Eff.", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsMpgheader:sortDiv"),
    TEAM_CRASHES_HEADER("Crashes", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsCrashesheader:sortDiv"),
    TEAM_SAFETY_HEADER("Safety", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsSafetyheader:sortDiv"),

    TEAM_NAME_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:newTeamStatsTab-totalsTeamName"),
    TEAM_SCORE_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:newTeamStatsTab-totalsOverallScore"),
    TEAM_TRIPS_VALUE(null, Xpath.start().tr(Id.clazz("rich-subtable-row rich-subtable-firstrow tableOdd")).td("4").toString()),
    TEAM_STOPS_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:newTeamStatsTab-totalsTripStops"),
    TEAM_DISTANCE_VALUE(null, "teamStatisticsForm:drivers:0:newTeamStatsTab-milesDriven"),
    TEAM_DURATION_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:newTeamStatsTab-totalsDriveTime"),
    TEAM_IDLE_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:newTeamStatsTab-totalsIdleTime"),
    TEAM_LOW_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:newTeamStatsTab-totalsLoIdle"),
    TEAM_HIGH_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:newTeamStatsTab-totalsHiIdle"),
    TEAM_PERCENT_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:idlePercentageNonZero"),
    TEAM_FUEL_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:summaryMpg"),
    TEAM_CRASHES_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:newTeamStatsTab-totalsCrashes"),
    TEAM_SAFETY_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:safetyGroupLink"),

    DRIVER_NAME_HEADER("Driver", "teamStatisticsForm:drivers:newTeamStatsTab-driverNameheader:sortDiv"),
    DRIVER_SCORE_HEADER("Score", "teamStatisticsForm:drivers:newTeamStatsTab-overallScoreheader:sortDiv"),
    DRIVER_VEHICLE_HEADER("Vehicle", "teamStatisticsForm:drivers:newTeamStatsTab-vehicleheader:sortDiv"),
    DRIVER_TRIPS_HEADER("Trips", "teamStatisticsForm:drivers:newTeamStatsTab-tripsheader:sortDiv"),
    DRIVER_STOPS_HEADER("Stops", "teamStatisticsForm:drivers:newTeamStatsTab-tripStopsheader:sortDiv"),
    DRIVER_DISTANCE_HEADER("Distance Driven", "teamStatisticsForm:drivers:newTeamStatsTab-milesDrivenheader:sortDiv"),
    DRIVER_DURATION_HEADER("Duration", "teamStatisticsForm:drivers:newTeamStatsTab-driveTimeheader:sortDiv"),
    DRIVER_IDLE_HEADER("Idle Time", "teamStatisticsForm:drivers:newTeamStatsTab-idleTimeheader:sortDiv"),
    DRIVER_LOW_HEADER("Low Idle", "teamStatisticsForm:drivers:newTeamStatsTab-loIdleheader:sortDiv"),
    DRIVER_HIGH_HEADER("High Idle", "teamStatisticsForm:drivers:newTeamStatsTab-hiIdleheader:sortDiv"),
    DRIVER_PERCENT_HEADER(StringEscapeUtils.unescapeHtml("Idle &#37;"), "teamStatisticsForm:drivers:newTeamStatsTab-idlePercentageheader:sortDiv"),
    DRIVER_FUEL_HEADER("Fuel Eff.", "teamStatisticsForm:drivers:newTeamStatsTab-mpgheader:sortDiv"),
    DRIVER_CRASHES_HEADER("Crashes", "teamStatisticsForm:drivers:newTeamStatsTab-crashesheader:sortDiv"),
    DRIVER_SAFETY_HEADER("Safety", "teamStatisticsForm:drivers:newTeamStatsTab-safetyheader:sortDiv"),

    DRIVER_NAME_ENTRY(null, "teamStatisticsForm:drivers:###:newTeamStatsTab-driverName"),
    DRIVER_SCORE_ENTRY(null, "teamStatisticsForm:drivers:###:newTeamStatsTab-overallScore"),
    DRIVER_VEHICLE_ENTRY(null, "teamStatisticsForm:drivers:###:newTeamStatsTab-vehicle"),
    DRIVER_TRIPS_ENTRY(null, "teamStatisticsForm:drivers:###:newTeamStatsTab-trips"),
    DRIVER_STOPS_ENTRY(null, "teamStatisticsForm:drivers:###:newTeamStatsTab-tripStops"),
    DRIVER_DISTANCE_ENTRY(null, "teamStatisticsForm:drivers:###:newTeamStatsTab-milesDriven"),
    DRIVER_DURATION_ENTRY(null, "teamStatisticsForm:drivers:###:newTeamStatsTab-driveTime"),
    DRIVER_IDLE_ENTRY(null, "teamStatisticsForm:drivers:###:newTeamStatsTab-idleTime"),
    DRIVER_LOW_ENTRY(null, "teamStatisticsForm:drivers:###:newTeamStatsTab-loIdle"),
    DRIVER_HIGH_ENTRY(null, "teamStatisticsForm:drivers:###:newTeamStatsTab-hiIdle"),
    DRIVER_PERCENT_ENTRY(null, "teamStatisticsForm:drivers:###:newTeamStatsTab-idlePercentage"),
    DRIVER_FUEL_ENTRY(null, "teamStatisticsForm:drivers:###:newTeamStatsTab-mpg"),
    DRIVER_CRASHES_ENTRY(null, "teamStatisticsForm:drivers:###:newTeamStatsTab-crashes"),
    DRIVER_SAFETY_ENTRY(null, "teamStatisticsForm:drivers:###:newTeamStatsTab-safety"),

    ;

    private String text, url;
    private String[] IDs;
    
    private TeamDriverStatsEnum(String url){
    	this.url = url;
    }
    private TeamDriverStatsEnum(String text, String ...IDs){
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
