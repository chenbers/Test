package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum TeamDriverStatsEnum implements SeleniumEnums {

    DEFAULT_URL(appUrl + "/dashboard//tab/teamStatistics"),

    
    /* Edit Columns */
    EDIT_COLUMNS_BUTTON(null, "teamStatisticsForm:teamStatisticsEditColumns"),


    /* Export Tool */
    EXPORT_TOOLS(null, Xpath.start().span(Id.id("teamStatisticsForm:teamStatistics_reportToolImageId")).span("2").toString()),

    EXPORT_PDF_TOOL(exportPDF, "teamStatisticsForm:teamStatistics-export_menu_item:anchor"),
    EXPORT_EMAIL_TOOL(emailReport, "teamStatisticsForm:teamStatistics-emailMenuItem:anchor"),
    EXPORT_EXCEL_TOOL(exportExcel, "teamStatisticsForm:teamStatistics-exportExcelMEnuItem:anchor"),


    /* Team rows */
    TEAM_NAME_HEADER("Team", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsTeamNameheader:sortDiv"),
    TEAM_SCORE_HEADER("Score", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsOverallScoreheader:sortDiv"),
    TEAM_TRIPS_HEADER("Trips", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsTripsTripsheader:sortDiv"),
    TEAM_STOPS_HEADER("Stops", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsTripStopsheader:sortDiv"),
    TEAM_DISTANCE_HEADER("Distance Driven", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsMilesDrivenheader:sortDiv"),
    TEAM_DURATION_HEADER("Duration", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsDriveTimeheader:sortDiv"),
    TEAM_IDLE_HEADER("Idle Time", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsIdleTimeheader:sortDiv"),
    TEAM_LOW_HEADER("Low Idle", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsLoIdleheader:sortDiv"),
    TEAM_HIGH_HEADER("High Idle", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsHiIdleheader:sortDiv"),
    TEAM_PERCENT_HEADER("Idle &#37;", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsIdlePercentageheader:sortDiv"),
    TEAM_FUEL_HEADER("Fuel Eff.", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsMpgheader:sortDiv"),
    TEAM_CRASHES_HEADER("Crashes", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsCrashesheader:sortDiv"),
    TEAM_SAFETY_HEADER("Safety", "teamStatisticsForm:drivers:driverTotals:newTeamStatsTab-totalsSafetyheader:sortDiv"),

    TEAM_NAME_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:newTeamStatsTab-totalsTeamName"),
    TEAM_SCORE_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:newTeamStatsTab-totalsOverallScore"),
    TEAM_TRIPS_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:newTeamStatsTab-totalsTripsTrips"),
    TEAM_STOPS_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:newTeamStatsTab-totalsTripStops"),
    TEAM_DISTANCE_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:newTeamStatsTab-totalsMilesDriven"),
    TEAM_DURATION_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:newTeamStatsTab-totalsDriveTime"),
    TEAM_IDLE_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:newTeamStatsTab-totalsIdleTime"),
    TEAM_LOW_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:newTeamStatsTab-totalsLoIdle"),
    TEAM_HIGH_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:newTeamStatsTab-totalsHiIdle"),
    TEAM_PERCENT_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:newTeamStatsTab-totalsIdlePercentage"),
    TEAM_FUEL_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:newTeamStatsTab-totalsMpg"),
    TEAM_CRASHES_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:newTeamStatsTab-totalsCrashes"),
    TEAM_SAFETY_VALUE(null, "teamStatisticsForm:drivers:driverTotals:0:newTeamStatsTab-totalsSafety"),

    DRIVER_NAME_FILTER("Driver", "teamStatisticsForm:drivers:newTeamStatsTab-driverNameheader:sortDiv"),
    DRIVER_SCORE_FILTER("Score", "teamStatisticsForm:drivers:newTeamStatsTab-overallScoreheader:sortDiv"),
    DRIVER_VEHICLE_FILTER("Vehicle", "teamStatisticsForm:drivers:newTeamStatsTab-vehicleheader:sortDiv"),
    DRIVER_TRIPS_FILTER("Trips", "teamStatisticsForm:drivers:newTeamStatsTab-tripsheader:sortDiv"),
    DRIVER_STOPS_FILTER("Stops", "teamStatisticsForm:drivers:newTeamStatsTab-tripStopsheader:sortDiv"),
    DRIVER_DISTANCE_FILTER("Distance Driven", "teamStatisticsForm:drivers:newTeamStatsTab-milesDrivenheader:sortDiv"),
    DRIVER_DURATION_FILTER("Duration", "teamStatisticsForm:drivers:newTeamStatsTab-driveTimeheader:sortDiv"),
    DRIVER_IDLE_FILTER("Idle Time", "teamStatisticsForm:drivers:newTeamStatsTab-idleTimeheader:sortDiv"),
    DRIVER_LOW_FILTER("Low Idle", "teamStatisticsForm:drivers:newTeamStatsTab-loIdleheader:sortDiv"),
    DRIVER_HIGH_FILTER("High Idle", "teamStatisticsForm:drivers:newTeamStatsTab-hiIdleheader:sortDiv"),
    DRIVER_PERCENT_FILTER("Idle &#37;", "teamStatisticsForm:drivers:newTeamStatsTab-idlePercentageheader:sortDiv"),
    DRIVER_FUEL_FILTER("Fuel Eff.", "teamStatisticsForm:drivers:newTeamStatsTab-mpgheader:sortDiv"),
    DRIVER_CRASHES_FILTER("Crashes", "teamStatisticsForm:drivers:newTeamStatsTab-crashesheader:sortDiv"),
    DRIVER_SAFETY_FILTER("Safety", "teamStatisticsForm:drivers:newTeamStatsTab-safetyheader:sortDiv"),

    DRIVER_NAME_ENTRY(null, "teamStatisticsForm:drivers:###:newTeamStatsTab-driverName"),
    DRIVER_SCORE_ENTRY(null, "//div[@id='teamStatisticsForm:drivers:###:newTeamStatsTab-overallScore']/div/div/div/div/div/a"),
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
