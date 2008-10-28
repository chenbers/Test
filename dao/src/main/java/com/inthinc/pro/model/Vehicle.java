package com.inthinc.pro.model;

import java.util.List;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

public class Vehicle extends BaseEntity
{
    @ID
    private Integer    vehicleID;
    @Column(name = "acctID")
    private Integer    accountID;
    private Boolean    active;
    private String     timeZone;
    private Integer    startOdometer;
    @Column(updateable = false)
    private Integer    assignedDriverID;
    List<SafetyDevice> safetyDevices;
    VehicleCompanyInfo companyInfo;
    VehicleDescription description;
    VehicleLicense     license;
    VehicleSensitivity sensitivity;

    public Integer getVehicleID()
    {
        return vehicleID;
    }

    public void setVehicleID(Integer vehicleID)
    {
        this.vehicleID = vehicleID;
    }

    public Integer getAccountID()
    {
        return accountID;
    }

    public void setAccountID(Integer accountID)
    {
        this.accountID = accountID;
    }

    public Boolean getActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    public String getTimeZone()
    {
        return timeZone;
    }

    public void setTimeZone(String timeZone)
    {
        this.timeZone = timeZone;
    }

    public Integer getStartOdometer()
    {
        return startOdometer;
    }

    public void setStartOdometer(Integer startOdometer)
    {
        this.startOdometer = startOdometer;
    }

    public Integer getAssignedDriverID()
    {
        return assignedDriverID;
    }

    public void setAssignedDriverID(Integer driverID)
    {
        this.assignedDriverID = driverID;
    }

    public List<SafetyDevice> getSafetyDevices()
    {
        return safetyDevices;
    }

    public void setSafetyDevices(List<SafetyDevice> safetyDevices)
    {
        this.safetyDevices = safetyDevices;
    }

    public VehicleCompanyInfo getCompanyInfo()
    {
        return companyInfo;
    }

    public void setCompanyInfo(VehicleCompanyInfo companyInfo)
    {
        this.companyInfo = companyInfo;
    }

    public VehicleDescription getDescription()
    {
        return description;
    }

    public void setDescription(VehicleDescription description)
    {
        this.description = description;
    }

    public VehicleLicense getLicense()
    {
        return license;
    }

    public void setLicense(VehicleLicense license)
    {
        this.license = license;
    }

    public VehicleSensitivity getSensitivity()
    {
        return sensitivity;
    }

    public void setSensitivity(VehicleSensitivity sensitivity)
    {
        this.sensitivity = sensitivity;
    }
}
