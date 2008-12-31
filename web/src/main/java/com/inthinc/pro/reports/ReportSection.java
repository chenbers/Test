package com.inthinc.pro.reports;

public enum ReportSection
{
    //Keep this incase we figure out sub reports
    TEST("Test Report","TestReport"),
    DRIVER_TABLE("Driver Report","DriverTableReport"),
    VEHICLE_TABLE("Driver Report","driver_table"),
    DEVICE_TABLE("Driver Report","driver_table"),
    IDLE_TABLE("Driver Report","driver_table"),
    TREND_CHART("Driver Report","driver_table"),
    MILES_PER_GALLON("Driver Report","driver_table");
    
    private String filename;
    private String label;
    
    private ReportSection(String label,String filename){
        this.label = label;
        this.filename = filename;
    }
    
    public String getFilename()
    {
        return filename;
    }

    public String getLabel()
    {
        return label;
    }
}
