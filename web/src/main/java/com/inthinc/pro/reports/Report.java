package com.inthinc.pro.reports;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/*
 * If these enums chage, make sure to change the .xhtml files that reference them.
 */
public enum Report
{
    DRIVER_REPORT("Driver Report","DriverReport.jrxml"),
    VEHICLE_REPORT("Vehicle Report","VehicleReport.jrxml"),
    IDLING_REPORT("Idling Report","IdlingReport.jrxml"),
    DEVICES_REPORT("Devices Report","DevicesReport.jrxml"),
    OVERALL_SCORE("Overal Score","ScorePieReport.jrxml"),
    TREND("Trend Report","TrendReport.jrxml"),
    MPG_GROUP("MPG Report","MPGGroupReport.jrxml");
    
  
    private String filename;
    private String label;
    
    private Report(String label,String filename){
        this.label = label;
        this.filename = filename;
    }
    
    private static final Map<String, Report> lookup = new HashMap<String, Report>();
    static
    {
        for (Report p : EnumSet.allOf(Report.class))
        {
            lookup.put(p.toString(), p);
        }
    }

    public static Report toReport(String stringValue)
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
