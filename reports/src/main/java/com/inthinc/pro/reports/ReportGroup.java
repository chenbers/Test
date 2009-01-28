package com.inthinc.pro.reports;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ReportGroup
{
    FLEET_DASHBOARD("Fleet Dashboard",1,ReportType.OVERALL_SCORE,ReportType.TREND,ReportType.MPG_GROUP),
    DIVISION_DASHBOARD("Division Dashboard",2,ReportType.OVERALL_SCORE,ReportType.TREND,ReportType.MPG_GROUP);
    
    private ReportType[] reports;
    private Integer reportID;
    private String label;
    
    private ReportGroup(String label, Integer reportID,ReportType... reports){
        this.reports = reports;
        this.label = label;
        this.reportID = reportID;
    }
    
    private static final Map<String, ReportGroup> lookup = new HashMap<String, ReportGroup>();
    static
    {
        for (ReportGroup p : EnumSet.allOf(ReportGroup.class))
        {
            lookup.put(p.toString(), p);
        }
    }

    public static ReportGroup toReportGroup(String stringValue)
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
    
    public ReportType[] getReports(){
        return reports;
    }
}
