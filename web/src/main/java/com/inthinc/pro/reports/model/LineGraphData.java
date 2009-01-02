package com.inthinc.pro.reports.model;

public class LineGraphData
{
    private String seriesID;
   
    private String category;
   
    private Float score;
    private String label;
    
    public LineGraphData(){};
    
    public LineGraphData(String seriesID,String category,Float score,String label){
        this.seriesID = seriesID;
        this.label = label;
        this.category = category;
        this.score = score;
    }
    
    public String getSeriesID()
    {
        return seriesID;
    }
     

    public Float getScore()
    {
        return score;
    }

    public void setScore(Float score)
    {
        this.score = score;
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
