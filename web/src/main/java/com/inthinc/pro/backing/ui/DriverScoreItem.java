package com.inthinc.pro.backing.ui;

import com.inthinc.pro.model.DriverScore;

public class DriverScoreItem
{
    private DriverScore driverScore;
    private String driverName;
    private String style;
    private Integer position;
    
    public DriverScoreItem(DriverScore driverScore)
    {
        this.driverScore = driverScore;
        if (driverScore != null)
        {
            if (driverScore.getDriver() != null && driverScore.getDriver().getPerson() != null)
                driverName = driverScore.getDriver().getPerson().getFullName();
            else driverName = "";
        }
    }
    public DriverScore getDriverScore()
    {
        return driverScore;
    }
    public void setDriverScore(DriverScore driverScore)
    {
        this.driverScore = driverScore;
    }
    public String getDriverName()
    {
        return driverName;
    }
    public void setDriverName(String driverName)
    {
        this.driverName = driverName;
    }
    public String getStyle()
    {
        return style;
    }
    public void setStyle(String style)
    {
        this.style = style;
    }
    public Integer getPosition()
    {
        return position;
    }
    public void setPosition(Integer position)
    {
        this.position = position;
    }
}
