package com.inthinc.pro.reports;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ReportGroup
{
    FLEET_DASHBOARD("Fleet Dashboard",1,Report.OVERALL_SCORE,Report.TREND,Report.MPG_GROUP),
    DIVISION_DASHBOARD("Division Dashboard",2,Report.OVERALL_SCORE,Report.TREND,Report.MPG_GROUP);
    
    private Report[] reports;
    private Integer reportID;
    private String label;
    
    private ReportGroup(String label, Integer reportID,Report... reports){
        this.reports = reports;
        this.label = label;
        this.reportID = reportID;
    }
    
    private static final Map<String, Report> lookup = new HashMap<String, Report>();
    static
    {
        for (Report p : EnumSet.allOf(Report.class))
        {
            lookup.put(p.toString(), p);
        }
    }

    public static Report toReportList(String stringValue)
    {
        return lookup.get(stringValue);
    }
    
    public Integer getReportID()
    {
        return this.reportID;
    }
    
    public String getLabel(){
        return label;
    }
    
    public Report[] getReports(){
        return reports;
    }
}
