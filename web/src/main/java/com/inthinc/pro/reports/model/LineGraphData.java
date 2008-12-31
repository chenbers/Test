package com.inthinc.pro.reports.model;

public class LineGraphData
{
    private String seriesID;
   
    private Integer durationPoint;
    private Float score;
    private String label;
    
    public LineGraphData(){};
    
    public LineGraphData(String seriesID,Integer durationPoint,Float score,String label){
        this.seriesID = seriesID;
        this.label = label;
        this.durationPoint = durationPoint;
        this.score = score;
    }
    
    public String getSeriesID()
    {
        return seriesID;
    }
    public Integer getDurationPoint()
    {
        return durationPoint;
    }

    public void setDurationPoint(Integer durationPoint)
    {
        this.durationPoint = durationPoint;
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
    

}
