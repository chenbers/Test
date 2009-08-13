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
    CRASH_HISTORY_REPORT("Crash History Report", "CrashHistory.jrxml", "CrashHistoryReportRaw.jrxml");
  
    private String prettyTemplate;
    private String rawTemplate;
    private String label;
    
    private ReportType(String label,String prettyTemplate){
        this.label = label;
        this.prettyTemplate = prettyTemplate;
    }
    
    private ReportType(String label,String prettyTemplate, String rawTemplate){
        this.label = label;
        this.prettyTemplate = prettyTemplate;
        this.rawTemplate = rawTemplate;
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
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(".");
        sb.append(this.name());
        return sb.toString();
    }

    
    
    

  
}
