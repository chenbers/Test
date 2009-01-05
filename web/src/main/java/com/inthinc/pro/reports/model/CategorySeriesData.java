package com.inthinc.pro.reports.model;


public class CategorySeriesData extends ChartData
{
    private String seriesID;
    private String category;
    
    public CategorySeriesData(){};
    
    public CategorySeriesData(String seriesID,String category,Number value,String label){
        this.seriesID = seriesID;
        this.label = label;
        this.category = category;
        this.setValue(value);
    }
    
    public String getSeriesID()
    {
        return seriesID;
    }

    public void setSeriesID(String seriesID)
    {
        this.seriesID = seriesID;
    }
    
    public String getLabel()
    {
        return label;
    }
    public void setLabel(String label)
    {
        this.label = label;
    }
    
    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }
}
