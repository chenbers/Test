package com.inthinc.pro.model;

import java.util.List;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

public class Vehicle extends BaseEntity
{
    @ID
    private Integer     vehicleID;
    @Column(name = "acctID")
    private Integer     accountID;
    private Integer     groupID;
    private Integer     costPerHour;  // in cents
    private Boolean     active;
    private String      name;
    private String      make;
    private String      model;
    private String      year;
    private String      color;
    @Column(name = "vtype")
    private VehicleType type;
    @Column(name = "vin")
    private String      VIN;          // 17 chars
    private Integer     weight;
    private String      license;
    @Column(name = "stateID")
    private State       state;
    @Column(updateable = false)
    List<SafetyDevice>  safetyDevices;
    VehicleSensitivity  sensitivity;
    @Column(updateable = false)
    private Integer     driverID;
    @Column(updateable = false)
    private Integer     deviceID;

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

    public Integer getCostPerHour()
    {
        return costPerHour;
    }

    public void setCostPerHour(Integer costPerHour)
    {
        this.costPerHour = costPerHour;
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

    public VehicleType getType()
    {
        return type;
    }

    public void setType(VehicleType type)
    {
        this.type = type;
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

    public Integer getDriverID()
    {
        return driverID;
    }

    public void setDriverID(Integer driverID)
    {
        this.driverID = driverID;
    }

    public Integer getDeviceID()
    {
        return deviceID;
    }

    public void setDeviceID(Integer deviceID)
    {
        this.deviceID = deviceID;
    }
}
