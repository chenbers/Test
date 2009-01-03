package com.inthinc.pro.reports.model;

public abstract class ChartData
{
    protected String label;
    protected String seriesID;
    protected Number value;
    
    public String getLabel()
    {
        return label;
    }
    public void setLabel(String label)
    {
        this.label = label;
    }
    public String getSeriesID()
    {
        return seriesID;
    }
    public void setSeriesID(String seriesID)
    {
        this.seriesID = seriesID;
    }
    public Number getValue()
    {
        return value;
    }
    public void setValue(Number value)
    {
        this.value = value;
    }
    
    
}
