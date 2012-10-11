package com.inthinc.pro.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DriversLicense
{
    Integer driverID;
    String licenseNumber;
    String licenseClass;
    State licenseState;
    Integer licenseExpiration;
    
    public Integer getDriverID()
    {
        return driverID;
    }
    public void setDriverID(Integer driverID)
    {
        this.driverID = driverID;
    }
    public String getLicenseNumber()
    {
        return licenseNumber;
    }
    public void setLicenseNumber(String licenseNumber)
    {
        this.licenseNumber = licenseNumber;
    }
    public String getLicenseClass()
    {
        return licenseClass;
    }
    public void setLicenseClass(String licenseClass)
    {
        this.licenseClass = licenseClass;
    }
    public State getLicenseState()
    {
        return licenseState;
    }
    public void setLicenseState(State licenseState)
    {
        this.licenseState = licenseState;
    }
    public Integer getLicenseExpiration()
    {
        return licenseExpiration;
    }
    public void setLicenseExpiration(Integer licenseExpiration)
    {
        this.licenseExpiration = licenseExpiration;
    }


}
