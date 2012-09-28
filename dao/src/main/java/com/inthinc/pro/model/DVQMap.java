package com.inthinc.pro.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DVQMap extends BaseEntity
{
    private Driver driver;
    private Vehicle vehicle;
    private DriveQMap driveQ;
    
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
    public DriveQMap getDriveQ()
    {
        return driveQ;
    }
    public void setDriveQ(DriveQMap driveQ)
    {
        this.driveQ = driveQ;
    }
}
