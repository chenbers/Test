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
    private Integer    groupID;
    private Boolean    active;
    private String     name;
    private String     make;
    private String     model;
    private String     year;
    private String     color;
    private String     unitType;     // e.g. sedan, suv, etc
    private String     VIN;          // 17 chars
    private Integer    weight;
    private String     license;
    private State      state;
    @Column(updateable = false)
    List<SafetyDevice> safetyDevices;
    VehicleSensitivity sensitivity;

    
    public Vehicle()
    {
        super();
    }
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

    public Integer getGroupID()
    {
        return groupID;
    }

    public void setGroupID(Integer groupID)
    {
        this.groupID = groupID;
    }

    public Boolean getActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getMake()
    {
        return make;
    }

    public void setMake(String make)
    {
        this.make = make;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public String getYear()
    {
        return year;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public String getUnitType()
    {
        return unitType;
    }

    public void setUnitType(String unitType)
    {
        this.unitType = unitType;
    }

    public String getVIN()
    {
        return VIN;
    }

    public void setVIN(String vin)
    {
        VIN = vin;
    }

    public Integer getWeight()
    {
        return weight;
    }

    public void setWeight(Integer weight)
    {
        this.weight = weight;
    }

    public String getLicense()
    {
        return license;
    }

    public void setLicense(String license)
    {
        this.license = license;
    }

    public State getState()
    {
        return state;
    }

    public void setState(State state)
    {
        this.state = state;
    }

    public List<SafetyDevice> getSafetyDevices()
    {
        return safetyDevices;
    }

    public void setSafetyDevices(List<SafetyDevice> safetyDevices)
    {
        this.safetyDevices = safetyDevices;
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
