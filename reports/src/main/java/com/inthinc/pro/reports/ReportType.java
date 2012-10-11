package com.inthinc.pro.reports;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import com.inthinc.pro.reports.util.MessageUtil;

public enum ReportType
{
    DRIVER_REPORT("Driver Report","DriverReport.jrxml","DriverReportRaw.jrxml"),
    VEHICLE_REPORT("Vehicle Report","VehicleReport.jrxml","VehicleReportRaw.jrxml"),
    IDLING_REPORT("Idling Report","IdlingReport.jrxml","IdlingReportRaw.jrxml"),
    IDLING_VEHICLE_REPORT("Idling Vehicle Report","IdlingVehicleReport.jrxml","IdlingVehicleReportRaw.jrxml"),
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
    SEATBELT_CLICKS_REPORT("Seatbelt Clicks Report","seatbeltClickReport.jrxml","seatbeltClickReportRaw.jrxml","performance","com.inthinc.pro.reports.jasper.performance.i18n.seatbeltClicksReport"),
    
    // DOT/IFTA
    MILEAGE_BY_VEHICLE("Mileage By Vehicle","mileageByVehicle.jrxml","mileageByVehicleRaw.jrxml", "ifta", "com.inthinc.pro.reports.jasper.ifta.i18n.stateMileage"),
    STATE_MILEAGE_BY_VEHICLE_ROAD_STATUS("State Mileage By Vehicle Road Status","stateMileageByVehicleRoadStatus.jrxml","stateMileageByVehicleRoadStatusRaw.jrxml", "ifta","com.inthinc.pro.reports.jasper.ifta.i18n.stateMileage", null, false),
    STATE_MILEAGE_BY_VEHICLE("State Mileage By Vehicle","stateMileageByVehicle.jrxml","stateMileageByVehicleRaw.jrxml", "ifta","com.inthinc.pro.reports.jasper.ifta.i18n.stateMileage", null, false),
    STATE_MILEAGE_FUEL_BY_VEHICLE("State Mileage Fuel By Vehicle","stateMileageFuelByVehicle.jrxml","stateMileageFuelByVehicleRaw.jrxml", "ifta","com.inthinc.pro.reports.jasper.ifta.i18n.stateMileage", null, false),
    STATE_MILEAGE_COMPARE_BY_GROUP("Group Comparison By State/Province","stateMileageCompareByGroup.jrxml","stateMileageCompareByGroupRaw.jrxml", "ifta","com.inthinc.pro.reports.jasper.ifta.i18n.stateMileage", null, false),
    STATE_MILEAGE_BY_MONTH("State Mileage By Month","stateMileageByMonth.jrxml","stateMileageByMonthRaw.jrxml", "ifta","com.inthinc.pro.reports.jasper.ifta.i18n.stateMileage", null, false),
    
    //MILEAGE
    STATE_MILEAGE_BY_VEHICLE_NON_IFTA("State Mileage By Vehicle","stateMileageByVehicle.jrxml","stateMileageByVehicleRaw.jrxml", "ifta","com.inthinc.pro.reports.jasper.ifta.i18n.stateMileage", null, false),
    
    // HOS
    HOS_DAILY_DRIVER_LOG_REPORT("HOS Daily Driver Log Report","HOSDailyDriverLog.jrxml", null, "hos", "com.inthinc.pro.reports.jasper.hos.i18n.HOSDriverDailyLog"),
    HOS_VIOLATIONS_SUMMARY_REPORT("HOS Violations Summary Report","hosViolations.jrxml", "hosViolationsRaw.jrxml", "hos", "com.inthinc.pro.reports.jasper.hos.i18n.hosViolations", null, true),
    NON_DOT_VIOLATIONS_SUMMARY_REPORT("NON-DOT Violations Summary Report","nonDOTViolations.jrxml", "nonDOTViolationsRaw.jrxml", "hos", "com.inthinc.pro.reports.jasper.hos.i18n.nonDOTViolations"),
    DRIVING_TIME_VIOLATIONS_SUMMARY_REPORT("Driving Time Violations Summary Report","drivingTimeViolations.jrxml", "drivingTimeViolationsRaw.jrxml", "hos", "com.inthinc.pro.reports.jasper.hos.i18n.drivingTimeViolations"),
    HOS_VIOLATIONS_DETAIL_REPORT("HOS Violations Detail Report","hosViolationsDetail.jrxml", "hosViolationsDetailRaw.jrxml", "hos", "com.inthinc.pro.reports.jasper.hos.i18n.violationsDetail", "HOS_VIOLATIONS_DETAIL", true),
    NON_DOT_VIOLATIONS_DETAIL_REPORT("NON-DOT Violations Detail Report","violationsDetail.jrxml", "violationsDetailRaw.jrxml", "hos", "com.inthinc.pro.reports.jasper.hos.i18n.violationsDetail", "NON_DOT_VIOLATIONS_DETAIL"),
    DRIVING_TIME_VIOLATIONS_DETAIL_REPORT("Driving Time Violations Detail Report","violationsDetail.jrxml", "violationsDetailRaw.jrxml", "hos", "com.inthinc.pro.reports.jasper.hos.i18n.violationsDetail", "DRIVING_TIME_VIOLATIONS_DETAIL"),
    HOS_DRIVER_DOT_LOG_REPORT("HOS Driver DOT Log Report","driverDotLog.jrxml", "driverDotLogRaw.jrxml", "hos", "com.inthinc.pro.reports.jasper.hos.i18n.driverDotLog", null, true),
    DOT_HOURS_REMAINING("DOT Time Remaining","dotHoursRemaining.jrxml", "dotHoursRemainingRaw.jrxml", "hos", "com.inthinc.pro.reports.jasper.hos.i18n.dotHoursRemaining", null, true),
    HOS_ZERO_MILES("HOS Zero Miles","hosZeroMiles.jrxml", "hosZeroMilesRaw.jrxml", "hos", "com.inthinc.pro.reports.jasper.hos.i18n.hosZeroMiles", null, true),
    HOS_EDITS("HOS Edits","hosEdits.jrxml", "hosEditsRaw.jrxml", "hos", "com.inthinc.pro.reports.jasper.hos.i18n.hosEdits", null, true),

    // Performance
    PAYROLL_DETAIL("Driver Hours Report","payrollDetail.jrxml", "payrollRaw.jrxml", "performance", "com.inthinc.pro.reports.jasper.performance.i18n.payrollDetail", null, true),
    PAYROLL_SIGNOFF("Driver Hours Signoff","payrollSignOff.jrxml", "payrollRaw.jrxml", "performance", "com.inthinc.pro.reports.jasper.performance.i18n.payrollSignOff", null, true),
    PAYROLL_SUMMARY("Driver Hours Summary","payrollSummary.jrxml", "payrollRaw.jrxml", "performance", "com.inthinc.pro.reports.jasper.performance.i18n.payrollSummary", null, true),
    PAYROLL_COMPENSATED_HOURS("Payroll Compensated Hours","payrollCompensatedHoursReport.jrxml", "payrollCompensatedHoursRaw.jrxml", "performance", "com.inthinc.pro.reports.jasper.performance.i18n.payrollReportCompensatedHours", null, true),
    
    TEN_HOUR_DAY_VIOLATIONS("Ten Hour Day Violations","tenHourDayViolations.jrxml","tenHourDayViolationsRaw.jrxml", "performance","com.inthinc.pro.reports.jasper.performance.i18n.tenHourDayViolations", null, false),
    DRIVER_HOURS("Driver Hours","driverHoursReport.jrxml","driverHoursReportRaw.jrxml", "performance","com.inthinc.pro.reports.jasper.performance.i18n.driverHours", null, false), 
    VEHICLE_USAGE("Vehicle usage","vehicleUsageReport.jrxml","vehicleUsageReportRaw.jrxml", "performance","com.inthinc.pro.reports.jasper.performance.i18n.vehicleUsageReport", null, false),

    DRIVER_PERFORMANCE_KEY_METRICS("Driver Performance Key Metrics", null,"driverPerformanceKeyMetrics.jrxml", "performance","com.inthinc.pro.reports.jasper.performance.i18n.driverPerformanceKeyMetrics", null, false),
    DRIVER_PERFORMANCE_KEY_METRICS_TF_RYG("Driver Performance Key Metrics Time Frame RYG",null,"driverPerformanceKeyMetricsRYG.jrxml", "performance","com.inthinc.pro.reports.jasper.performance.i18n.driverPerformanceKeyMetrics", null, false),
    DRIVER_PERFORMANCE_TEAM("Driver Performance (Team)","driverPerformanceReport.jrxml","driverPerformanceRaw.jrxml", "performance","com.inthinc.pro.reports.jasper.performance.i18n.driverPerformanceReport", null, false), 
    DRIVER_PERFORMANCE_INDIVIDUAL("Driver  Performance (Individual)","driverPerformanceReport.jrxml","driverPerformanceRaw.jrxml", "performance","com.inthinc.pro.reports.jasper.performance.i18n.driverPerformanceReport", null, false), 
    DRIVER_PERFORMANCE_RYG_TEAM("Driver Performance RYG(Team)","driverPerformanceReport.jrxml","driverPerformanceRaw.jrxml", "performance","com.inthinc.pro.reports.jasper.performance.i18n.driverPerformanceReport", null, false), 
    DRIVER_PERFORMANCE_RYG_INDIVIDUAL("Driver  Performance RYG(Individual)","driverPerformanceReport.jrxml","driverPerformanceRaw.jrxml", "performance","com.inthinc.pro.reports.jasper.performance.i18n.driverPerformanceReport", null, false), 

    // ASSET
    WARRANTY_LIST("Warranty List","warrantyListReport.jrxml","warrantyListReportRaw.jrxml", "performance","com.inthinc.pro.reports.jasper.asset.i18n.warrantyList", null, false),
    
    //COMMUNICATION
    NON_COMM("Non Comm Report","nonCommReport.jrxml","nonCommReportRaw.jrxml","communication","com.inthinc.pro.reports.jasper.communication.i18n.nonCommReport"),

    //DEVON ENERGY CORP
    DRIVER_EXCLUDED_VIOLATIONS("Driver Excluded Violations","driverExcludedViolationsReport.jrxml","driverExcludedViolationsReportRaw.jrxml","performance","com.inthinc.pro.reports.jasper.performance.i18n.driverExcludedViolationsReport"),
    DRIVER_COACHING("Driver Coaching Report","driverCoachingReport.jrxml","driverCoachingReport.jrxml", "performance","com.inthinc.pro.reports.jasper.performance.i18n.driverCoachingReport", null, false),
    //FORMS
    DVIR_PRETRIP("Driver Vehicle Inspection Reports - PreTrip","driverVehiclePreTripInspectionReport.jrxml","driverVehiclePreTripInspectionReport.jrxml","forms"),
    DVIR_POSTTRIP("Driver Vehicle Inspection Reports - PostTrip","driverVehiclePostTripInspectionReport.jrxml","driverVehiclePostTripInspectionReport.jrxml","forms");
    
    private String prettyTemplate;
    private String rawTemplate;
    private String label;
    private String subDirectory;
    private String resourceBundleName;
    private String name;
    private boolean tabularSupport;
    
    private ReportType(String label,String prettyTemplate){
        this.label = label;
        this.prettyTemplate = prettyTemplate;
    }
    
    private ReportType(String label,String prettyTemplate, String rawTemplate){
        this(label, prettyTemplate);
        this.rawTemplate = rawTemplate;
    }
    
    private ReportType(String label,String prettyTemplate, String rawTemplate, String subDirectory){
        this(label, prettyTemplate, rawTemplate);
        this.subDirectory = subDirectory;
    }

    private ReportType(String label,String prettyTemplate, String rawTemplate, String subDirectory, String resourceBundleName){
        this(label, prettyTemplate, rawTemplate, subDirectory);
        this.resourceBundleName = resourceBundleName;
    }
    
    private ReportType(String label,String prettyTemplate, String rawTemplate, String subDirectory, String resourceBundle, String name){
        this(label, prettyTemplate, rawTemplate, subDirectory, resourceBundle);
        this.name = name;
    }
    private ReportType(String label,String prettyTemplate, String rawTemplate, String subDirectory, String resourceBundle, String name, boolean tabularSupport){
        this(label, prettyTemplate, rawTemplate, subDirectory, resourceBundle, name);
        this.tabularSupport = tabularSupport;
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
        String subDir = (subDirectory == null) ? "" : (subDirectory + "/");
        return prettyTemplate == null ? null : subDir + prettyTemplate.replace(".jrxml", ".jasper");
    }
    
    public String getPrettyJRXML()
    {
        String subDir = (subDirectory == null) ? "" : (subDirectory + "/");
        return prettyTemplate == null ? null : subDir + prettyTemplate;
    }

    public String getRawJasper()
    {
        String subDir = (subDirectory == null) ? "" : (subDirectory + "/");
        return rawTemplate == null ? null : subDir + rawTemplate.replace(".jrxml", ".jasper");
    }
    public String getSubDirectory() {
        return subDirectory;
    }

    public String getResourceBundleName() {
        return resourceBundleName;
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

    public boolean isTabularSupport() {
        return tabularSupport;
    }

    public void setTabularSupport(boolean tabularSupport) {
        this.tabularSupport = tabularSupport;
    }
    
    public ResourceBundle getResourceBundle(Locale locale) {
        if (resourceBundleName != null)
            return MessageUtil.getBundle(locale, resourceBundleName);
        else return MessageUtil.getBundle(locale);
    }

}
