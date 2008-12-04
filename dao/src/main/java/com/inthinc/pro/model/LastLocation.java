package com.inthinc.pro.model;

import java.util.Date;

public class LastLocation
{
    private Integer driverID;
    private Integer vehicleID;
    private Date time;
    private Double lat;
    private Double lng;
    
    public static final Integer DRIVER_TYPE = 1;
    public static final Integer VEHICLE_TYPE = 2;
    
    public LastLocation()
    {
        
    }
    public Integer getDriverID()
    {
        return driverID;
    }
    public void setDriverID(Integer driverID)
    {
        this.driverID = driverID;
    }
    public Integer getVehicleID()
    {
        return vehicleID;
    }
    public void setVehicleID(Integer vehicleID)
    {
        this.vehicleID = vehicleID;
    }
    public Date getTime()
    {
        return time;
    }
    public void setTime(Date time)
    {
        this.time = time;
    }
    public Double getLat()
    {
        return lat;
    }
    public void setLat(Double lat)
    {
        this.lat = lat;
    }
    public Double getLng()
    {
        return lng;
    }
    public void setLng(Double lng)
    {
        this.lng = lng;
    }
}
