package com.inthinc.pro.selenium.pageEnums;

import org.apache.commons.lang.StringEscapeUtils;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum TeamVehicleStatsEnum implements SeleniumEnums {

    DEFAULT_URL(appUrl + "/dashboard//tab/teamVehicleStatistics"),

    
    /* Edit Columns */
    EDIT_COLUMNS_BUTTON(null, "teamStatisticsVehicleForm:teamVehicleStatisticsEditColumns"),


    /* Export Tool */
    EXPORT_TOOLS(null, Xpath.start().span(Id.id("teamStatisticsVehicleForm:teamVehicleStatistics_reportToolImageId")).span("2").toString()),

    EXPORT_PDF_TOOL(exportPDF, "teamStatisticsVehicleForm:teamVehicleStatistics-export_menu_item:anchor"),
    EXPORT_EMAIL_TOOL(emailReport, "teamStatisticsVehicleForm:teamVehicleStatistics-emailMenuItem:anchor"),
    EXPORT_EXCEL_TOOL(exportExcel, "teamStatisticsVehicleForm:teamVehicleStatistics-exportExcelMEnuItem"),


    /* Team rows */
    TEAM_NAME_HEADER("Team", "teamStatisticsVehicleForm:drivers:driverTotals:newTeamStatsTab-totalsTeamNameheader:sortDiv"),
    TEAM_SCORE_HEADER("Score", "teamStatisticsVehicleForm:drivers:driverTotals:newTeamStatsTab-totalsOverallScoreheader:sortDiv"),
    TEAM_TRIPS_HEADER("Trips", "teamStatisticsVehicleForm:drivers:driverTotals:j_id386header:sortDiv"),
    TEAM_STOPS_HEADER("Stops", "teamStatisticsVehicleForm:drivers:driverTotals:newTeamStatsTab-totalsTripStopsheader:sortDiv"),
    TEAM_DISTANCE_HEADER("Distance Driven", "teamStatisticsVehicleForm:drivers:driverTotals:newTeamStatsTab-totalsMilesDrivenheader:sortDiv"),
    TEAM_DURATION_HEADER("Duration", "teamStatisticsVehicleForm:drivers:driverTotals:newTeamStatsTab-totalsDriveTimeheader:sortDiv"),
    TEAM_IDLE_HEADER("Idle Time", "teamStatisticsVehicleForm:drivers:driverTotals:newTeamStatsTab-totalsIdleTimeheader:sortDiv"),
    TEAM_LOW_HEADER("Low Idle", "teamStatisticsVehicleForm:drivers:driverTotals:newTeamStatsTab-totalsLoIdleheader:sortDiv"),
    TEAM_HIGH_HEADER("High Idle", "teamStatisticsVehicleForm:drivers:driverTotals:newTeamStatsTab-totalsHiIdleheader:sortDiv"),
    TEAM_PERCENT_HEADER(StringEscapeUtils.unescapeHtml("Idle &#37;"), "teamStatisticsVehicleForm:drivers:driverTotals:newTeamStatsTab-totalsIdlePercentageheader:sortDiv"),
    TEAM_FUEL_HEADER("Fuel Eff.", "teamStatisticsVehicleForm:drivers:driverTotals:newTeamStatsTab-totalsMpgheader:sortDiv"),
    TEAM_CRASHES_HEADER("Crashes", "teamStatisticsVehicleForm:drivers:driverTotals:newTeamStatsTab-totalsCrashesheader:sortDiv"),
    TEAM_SAFETY_HEADER("Safety", "teamStatisticsVehicleForm:drivers:driverTotals:newTeamStatsTab-totalsSafetyheader:sortDiv"),

    TEAM_NAME_VALUE(null, "teamStatisticsVehicleForm:drivers:driverTotals:0:newTeamStatsTab-totalsTeamName"),
    TEAM_SCORE_VALUE(null, "teamStatisticsVehicleForm:drivers:driverTotals:0:newTeamStatsTab-totalsOverallScore"),
    TEAM_TRIPS_VALUE(null, Xpath.start().tr(Id.clazz("rich-subtable-row rich-subtable-firstrow tableOdd")).td("4").toString()),
    TEAM_STOPS_VALUE(null, "teamStatisticsVehicleForm:drivers:driverTotals:0:newTeamStatsTab-totalsTripStops"),
    TEAM_DISTANCE_VALUE(null, "teamStatisticsVehicleForm:drivers:driverTotals:0:newTeamStatsTab-totalsMilesDriven"),
    TEAM_DURATION_VALUE(null, "teamStatisticsVehicleForm:drivers:driverTotals:0:newTeamStatsTab-totalsDriveTime"),
    TEAM_IDLE_VALUE(null, "teamStatisticsVehicleForm:drivers:driverTotals:0:newTeamStatsTab-totalsIdleTime"),
    TEAM_LOW_VALUE(null, "teamStatisticsVehicleForm:drivers:driverTotals:0:newTeamStatsTab-totalsLoIdle"),
    TEAM_HIGH_VALUE(null, "teamStatisticsVehicleForm:drivers:driverTotals:0:newTeamStatsTab-totalsHiIdle"),
    TEAM_PERCENT_VALUE(null, "teamStatisticsVehicleForm:drivers:driverTotals:0:newTeamStatsTab-totalsIdlePercentage"),
    TEAM_FUEL_VALUE(null, "teamStatisticsVehicleForm:drivers:driverTotals:0:newTeamStatsTab-totalsMpg"),
    TEAM_CRASHES_VALUE(null, "teamStatisticsVehicleForm:drivers:driverTotals:0:newTeamStatsTab-totalsCrashes"),
    TEAM_SAFETY_VALUE(null, "teamStatisticsVehicleForm:drivers:driverTotals:0:newTeamStatsTab-totalsSafety"),

    DRIVER_NAME_HEADER("Driver", "teamStatisticsVehicleForm:drivers:newTeamStatsTab-driverNameheader:sortDiv"),
    DRIVER_SCORE_HEADER("Score", "teamStatisticsVehicleForm:drivers:newTeamStatsTab-overallScoreheader:sortDiv"),
    DRIVER_VEHICLE_HEADER("Vehicle", "teamStatisticsVehicleForm:drivers:newTeamStatsTab-vehicleheader:sortDiv"),
    DRIVER_TRIPS_HEADER("Trips", "teamStatisticsVehicleForm:drivers:newTeamStatsTab-tripsheader:sortDiv"),
    DRIVER_STOPS_HEADER("Stops", "teamStatisticsVehicleForm:drivers:newTeamStatsTab-tripStopsheader:sortDiv"),
    DRIVER_DISTANCE_HEADER("Distance Driven", "teamStatisticsVehicleForm:drivers:newTeamStatsTab-milesDrivenheader:sortDiv"),
    DRIVER_DURATION_HEADER("Duration", "teamStatisticsVehicleForm:drivers:newTeamStatsTab-driveTimeheader:sortDiv"),
    DRIVER_IDLE_HEADER("Idle Time", "teamStatisticsVehicleForm:drivers:newTeamStatsTab-idleTimeheader:sortDiv"),
    DRIVER_LOW_HEADER("Low Idle", "teamStatisticsVehicleForm:drivers:newTeamStatsTab-loIdleheader:sortDiv"),
    DRIVER_HIGH_HEADER("High Idle", "teamStatisticsVehicleForm:drivers:newTeamStatsTab-hiIdleheader:sortDiv"),
    DRIVER_PERCENT_HEADER(StringEscapeUtils.unescapeHtml("Idle &#37;"), "teamStatisticsVehicleForm:drivers:newTeamStatsTab-idlePercentageheader:sortDiv"),
    DRIVER_FUEL_HEADER("Fuel Eff.", "teamStatisticsVehicleForm:drivers:newTeamStatsTab-mpgheader:sortDiv"),
    DRIVER_CRASHES_HEADER("Crashes", "teamStatisticsVehicleForm:drivers:newTeamStatsTab-crashesheader:sortDiv"),
    DRIVER_SAFETY_HEADER("Safety", "teamStatisticsVehicleForm:drivers:newTeamStatsTab-safetyheader:sortDiv"),

    DRIVER_NAME_ENTRY(null, "teamStatisticsVehicleForm:drivers:###:newTeamStatsTab-driverName"),
    DRIVER_SCORE_ENTRY(null, "//div[@id='teamStatisticsVehicleForm:drivers:###:newTeamStatsTab-overallScore']/div/div/div/div/div/a"),
    DRIVER_VEHICLE_ENTRY(null, "teamStatisticsVehicleForm:drivers:###:newTeamStatsTab-vehicle"),
    DRIVER_TRIPS_ENTRY(null, "teamStatisticsVehicleForm:drivers:###:newTeamStatsTab-trips"),
    DRIVER_STOPS_ENTRY(null, "teamStatisticsVehicleForm:drivers:###:newTeamStatsTab-tripStops"),
    DRIVER_DISTANCE_ENTRY(null, "teamStatisticsVehicleForm:drivers:###:newTeamStatsTab-milesDriven"),
    DRIVER_DURATION_ENTRY(null, "teamStatisticsVehicleForm:drivers:###:newTeamStatsTab-driveTime"),
    DRIVER_IDLE_ENTRY(null, "teamStatisticsVehicleForm:drivers:###:newTeamStatsTab-idleTime"),
    DRIVER_LOW_ENTRY(null, "teamStatisticsVehicleForm:drivers:###:newTeamStatsTab-loIdle"),
    DRIVER_HIGH_ENTRY(null, "teamStatisticsVehicleForm:drivers:###:newTeamStatsTab-hiIdle"),
    DRIVER_PERCENT_ENTRY(null, "teamStatisticsVehicleForm:drivers:###:newTeamStatsTab-idlePercentage"),
    DRIVER_FUEL_ENTRY(null, "teamStatisticsVehicleForm:drivers:###:newTeamStatsTab-mpg"),
    DRIVER_CRASHES_ENTRY(null, "teamStatisticsVehicleForm:drivers:###:newTeamStatsTab-crashes"),
    DRIVER_SAFETY_ENTRY(null, "teamStatisticsVehicleForm:drivers:###:newTeamStatsTab-safety"),
    
    
    ;
    

    private String text, url;
    private String[] IDs;
    
    private TeamVehicleStatsEnum(String url){
    	this.url = url;
    }
    private TeamVehicleStatsEnum(String text, String ...IDs){
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
