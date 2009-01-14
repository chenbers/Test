package com.inthinc.pro.reports;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/*
 * If these enums chage, make sure to change the .xhtml files that reference them.
 */
public enum ReportType
{
    DRIVER_REPORT("Driver Report","DriverReport.jrxml"),
    VEHICLE_REPORT("Vehicle Report","VehicleReport.jrxml"),
    IDLING_REPORT("Idling Report","IdlingReport.jrxml"),
    DEVICES_REPORT("Devices Report","DevicesReport.jrxml"),
    OVERALL_SCORE("Overal Score","ScorePieReport.jrxml"),
    TREND("Trend Report","TrendReport.jrxml"),
    MPG_GROUP("MPG Report","MPGGroupReport.jrxml");
    
  
    private ReportType[] reportSubtypes;
    private String filename;
    private String label;
    
    private ReportType(String label,String filename,ReportType... reportSubtypes){
        this.reportSubtypes = reportSubtypes;
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

    public static ReportType toReportType(String stringValue)
    {
        return lookup.get(stringValue);
    }
    
    public String getFilename(){
        return filename;
    }
    
    public String getLabel(){
        return label;
    }
    
    public ReportType[] getSubtypes(){
        return reportSubtypes;
    }
}
