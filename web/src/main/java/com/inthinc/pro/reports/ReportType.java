package com.inthinc.pro.reports;

public enum ReportType
{
    DRIVER_TABULAR("Driver Report","DriverTableReport"),
    OVERALL_SCORE("Overal Score","ScorePieReport"),
    TREND("Trend Report","TrendReport"),
    MPG_GROUP("Trend Report","MPGGroupReport");
    
  
    private ReportType[] reportSubtypes;
    private String filename;
    private String label;
    
    private ReportType(String label,String filename,ReportType... reportSubtypes){
        this.reportSubtypes = reportSubtypes;
        this.label = label;
        this.filename = filename;
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
