package com.inthinc.pro.model;

import java.util.Date;

import com.inthinc.pro.dao.annotations.Column;

public class DriverLocation
{
    private Driver driver;
    private Vehicle vehicle;
    private LatLng loc;                  // last location of driver
    private Date time;
    private String addressStr;
    
    public LatLng getLoc()
    {
        return loc;
    }
    public Driver getDriver()
    {
        return driver;
    }
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }
    public Vehicle getVehicle()
    {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle)
    {
        this.vehicle = vehicle;
    }
    public void setLoc(LatLng loc)
    {
        this.loc = loc;
    }
    public Date getTime()
    {
        return time;
    }
    public void setTime(Date time)
    {
        this.time = time;
    }
    public String getAddressStr()
    {
        return addressStr;
    }
    public void setAddressStr(String addressStr)
    {
        this.addressStr = addressStr;
    }
 
}
