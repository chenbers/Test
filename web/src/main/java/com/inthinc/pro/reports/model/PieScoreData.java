package com.inthinc.pro.reports.model;

public class PieScoreData
{
    private String key;
    private Integer value;
    private String label;
    
    public PieScoreData(String key,Integer value, String label)
    {
        this.key = key;
        this.value = value;
        this.label = label;
    }
    
    public String getKey()
    {
        return key;
    }
    public void setKey(String key)
    {
        this.key = key;
    }
    public Integer getValue()
    {
        return value;
    }
    public void setValue(Integer value)
    {
        this.value = value;
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
