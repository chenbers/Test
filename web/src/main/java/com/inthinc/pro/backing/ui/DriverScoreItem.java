package com.inthinc.pro.backing.ui;

import com.inthinc.pro.model.DriverScore;
import com.inthinc.pro.util.MessageUtil;

public class DriverScoreItem
{
    private DriverScore driverScore;
    private String driverName;
    private String vehicleID;
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
            
            if (driverScore.getVehicle() != null)
                vehicleID = driverScore.getVehicle().getName(); 
            else vehicleID = MessageUtil.getMessageString("teamTop_noVehicle");
        }
        else
        {
            driverName = "";
            vehicleID = MessageUtil.getMessageString("teamTop_noVehicle");
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
    public String getVehicleID()
    {
        return vehicleID;
    }
    public void setVehicleID(String vehicleID)
    {
        this.vehicleID = vehicleID;
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
