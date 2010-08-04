package com.inthinc.pro.reports;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ReportType
{
    DRIVER_REPORT("Driver Report","DriverReport.jrxml","DriverReportRaw.jrxml"),
    VEHICLE_REPORT("Vehicle Report","VehicleReport.jrxml","VehicleReportRaw.jrxml"),
    IDLING_REPORT("Idling Report","IdlingReport.jrxml","IdlingReportRaw.jrxml"),
    DEVICES_REPORT("Devices Report","DevicesReport.jrxml","DevicesReportRaw.jrxml"),
    OVERALL_SCORE("Overal Score","ScorePieReport.jrxml"),
    TREND("Trend Report","TrendReport.jrxml"),
    MPG_GROUP("MPG Report","MPGGroupReport.jrxml"),
    DRIVER_SUMMARY_P1("Driver Summary Report P1", "DriverVehicleSummaryReport1.jrxml"),
    DRIVER_SUMMARY_P2("Driver Summary Report P2", "DriverVehicleSummaryReport2.jrxml"),
    DRIVER_SPEED("Driver Speed Report", "DriverVehicleSpeedReport.jrxml", "DVSpeedReportRaw.jrxml"),
    DRIVER_STYLE("Driver Style Report", "DriverVehicleStyleReport.jrxml", "DVStyleReportRaw.jrxml"),
    DRIVER_SEATBELT("Driver Seat Belt Report", "DriverVehicleSeatBeltReport.jrxml", "DVSeatBeltReportRaw.jrxml"),
    VEHICLE_SUMMARY_P1("Vehicle Summary Report P1", "DriverVehicleSummaryReport1.jrxml"),
    VEHICLE_SUMMARY_P2("Vehicle Summary Report P2", "DriverVehicleSummaryReport2.jrxml"),
    VEHICLE_SPEED("Vehicle Speed Report", "DriverVehicleSpeedReport.jrxml", "DVSpeedReportRaw.jrxml"),
    VEHICLE_STYLE("Vehicle Style Report", "DriverVehicleStyleReport.jrxml", "DVStyleReportRaw.jrxml"),
    VEHICLE_SEATBELT("Vehicle Seat Belt Report", "DriverVehicleSeatBeltReport.jrxml", "DVSeatBeltReportRaw.jrxml"),
    EVENT_REPORT("Event Report", "EventReport.jrxml","EventReportRaw.jrxml"),
    WARNING_REPORT("Warning Report", "EventReport.jrxml","EventReportRaw.jrxml"),
    EMERGENCY_REPORT("Emergency Report", "EventReport.jrxml","EventReportRaw.jrxml"),
    ZONE_ALERT_REPORT("Zone Alert Report", "EventReport.jrxml","EventReportRaw.jrxml"),
    RED_FLAG_REPORT("Red Flag Report", "RedFlagReport.jrxml","RedFlagReportRaw.jrxml"),
    CRASH_HISTORY_REPORT("Crash History Report", "CrashHistoryReport.jrxml", "CrashHistoryReportRaw.jrxml"),
    SPEED_PERCENTAGE("Speeding Percentage Report", "SpeedPercentageReport.jrxml"),
    IDLE_PERCENTAGE("Idling Percentage Report", "IdlePercentageReport.jrxml"),
    TEAM_STATISTICS_REPORT("Team Statistics Report","TeamStatisticsReport.jrxml","TeamStatisticsReportRaw.jrxml"),
    TEAM_STOPS_REPORT("Team Stops Report","TeamStopsReport.jrxml","TeamStopsReportRaw.jrxml"),
    
    // HOS
    HOS_DAILY_DRIVER_LOG_REPORT("HOS Daily Driver Log Report","HOSDailyDriverLog.jrxml"),
    HOS_VIOLATIONS_SUMMARY_REPORT("HOS Violations Summary Report","hos/hosViolations.jrxml", "hos/hosViolationsRaw.jrxml", "com.inthinc.pro.reports.jasper.hos.i18n.hosViolations"),
    NON_DOT_VIOLATIONS_SUMMARY_REPORT("NON-DOT Violations Summary Report","hos/nonDOTViolations.jrxml", "hos/nonDOTViolationsRaw.jrxml", "com.inthinc.pro.reports.jasper.hos.i18n.nonDOTViolations"),
    DRIVING_TIME_VIOLATIONS_SUMMARY_REPORT("Driving Time Violations Summary Report","hos/drivingTimeViolations.jrxml", "hos/drivingTimeViolationsRaw.jrxml", "com.inthinc.pro.reports.jasper.hos.i18n.drivingTimeViolations"),
    HOS_VIOLATIONS_DETAIL_REPORT("HOS Violations Detail Report","hos/violationsDetail.jrxml", "hos/violationsDetailRaw.jrxml", "com.inthinc.pro.reports.jasper.hos.i18n.violationsDetail", "HOS_VIOLATIONS_DETAIL"),
    NON_DOT_VIOLATIONS_DETAIL_REPORT("NON-DOT Violations Detail Report","hos/violationsDetail.jrxml", "hos/violationsDetailRaw.jrxml", "com.inthinc.pro.reports.jasper.hos.i18n.violationsDetail", "NON_DOT_VIOLATIONS_DETAIL"),
    DRIVING_TIME_VIOLATIONS_DETAIL_REPORT("Driving Time Violations Detail Report","hos/violationsDetail.jrxml", "hos/violationsDetailRaw.jrxml", "com.inthinc.pro.reports.jasper.hos.i18n.violationsDetail", "DRIVING_TIME_VIOLATIONS_DETAIL"),
    HOS_DRIVER_DOT_LOG_REPORT("HOS Driver DOT Log Report","hos/driverDotLog.jrxml", "hos/driverDotLogRaw.jrxml", "com.inthinc.pro.reports.jasper.hos.i18n.driverDotLog");

    private String prettyTemplate;
    private String rawTemplate;
    private String label;
    private String resourceBundle;
    private String name;
    
    private ReportType(String label,String prettyTemplate){
        this.label = label;
        this.prettyTemplate = prettyTemplate;
    }
    
    private ReportType(String label,String prettyTemplate, String rawTemplate){
        this.label = label;
        this.prettyTemplate = prettyTemplate;
        this.rawTemplate = rawTemplate;
    }

    private ReportType(String label,String prettyTemplate, String rawTemplate, String resourceBundle){
        this.label = label;
        this.prettyTemplate = prettyTemplate;
        this.rawTemplate = rawTemplate;
        this.resourceBundle = resourceBundle;
    }
    
    private ReportType(String label,String prettyTemplate, String rawTemplate, String resourceBundle, String name){
        this.label = label;
        this.prettyTemplate = prettyTemplate;
        this.rawTemplate = rawTemplate;
        this.resourceBundle = resourceBundle;
        this.name = name;
    }
    
    private static final Map<String, ReportType> lookup = new HashMap<String, ReportType>();
    static
    {
        for (ReportType p : EnumSet.allOf(ReportType.class))
        {
            lookup.put(p.toString(), p);
        }
    }

    public static ReportType toReport(String stringValue)
    {
        return lookup.get(stringValue);
    }
    
    public String getLabel(){
        return label;
    }

    public String getPrettyTemplate()
    {
        return prettyTemplate;
    }

    public String getRawTemplate()
    {
        return rawTemplate;
    }
    
    public String getPrettyJasper()
    {
        return prettyTemplate == null ? null : prettyTemplate.replace(".jrxml", ".jasper");
    }

    public String getRawJasper()
    {
        return rawTemplate == null ? null : rawTemplate.replace(".jrxml", ".jasper");
    }

    public String getResourceBundle() {
        return resourceBundle;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(".");
        sb.append(this.name());
        return sb.toString();
    }

    
    
    

  
}
