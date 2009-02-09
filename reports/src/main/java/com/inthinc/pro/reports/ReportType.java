package com.inthinc.pro.reports;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ReportType
{
    DRIVER_REPORT("Driver Report","DriverReport.jrxml"), //Change to "DRIVERS" (plural)   ? 
    VEHICLE_REPORT("Vehicle Report","VehicleReport.jrxml"),
    IDLING_REPORT("Idling Report","IdlingReport.jrxml"),
    DEVICES_REPORT("Devices Report","DevicesReport.jrxml"),
    OVERALL_SCORE("Overal Score","ScorePieReport.jrxml"),
    TREND("Trend Report","TrendReport.jrxml"),
    MPG_GROUP("MPG Report","MPGGroupReport.jrxml"),
    DRIVER_SUMMARY_P1("Driver/Vehicle Summary Report P1", "DriverVehicleSummaryReport1.jrxml"),
    DRIVER_SUMMARY_P2("Driver/Vehicle Summary Report P2", "DriverVehicleSummaryReport2.jrxml"),
    DRIVER_SPEED("Speed Report", "DriverVehicleSpeedReport.jrxml"),
    DRIVER_STYLE("Style Report", "DriverVehicleStyleReport.jrxml");
  
    private String filename;
    private String label;
    
    private ReportType(String label,String filename){
        this.label = label;
        this.filename = filename;
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
    
    public String getFilename(){
        return filename;
    }
    
    public String getLabel(){
        return label;
    }
}
