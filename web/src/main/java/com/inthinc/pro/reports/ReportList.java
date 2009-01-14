package com.inthinc.pro.reports;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ReportList
{
    FLEET_DASHBOARD("Fleet Dashboard","home.faces",Report.OVERALL_SCORE,Report.TREND,Report.MPG_GROUP);
    
  
    private Report[] reports;
    private String pageName;
    private String label;
    
    private ReportList(String label,String pageName,Report... reports){
        this.reports = reports;
        this.label = label;
        this.pageName = pageName;
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
    
    public String getFilename(){
        return pageName;
    }
    
    public String getLabel(){
        return label;
    }
    
    public Report[] getReports(){
        return reports;
    }
}
