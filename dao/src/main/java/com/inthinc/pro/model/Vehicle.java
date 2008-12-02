package com.inthinc.pro.model;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

public class Vehicle extends BaseEntity
{
    @Column(updateable = false)
    private static final long serialVersionUID = 6102998742224160619L;

    @ID
    private Integer           vehicleID;

    private Integer           groupID;
    // costPerHour?? not in db
    private Integer           costPerHour;                            // in cents
    private VehicleStatus   status;
    private String            name;
    private String            make;
    private String            model;
    private Integer            year;
    private String            color;
    private VehicleType       vtype;
    @Column(name = "vin")
    private String            VIN;                                    // 17 chars
    private Integer           weight;
    private String            license;
    @Column(name = "stateID")
    private State             state;
    @Column(updateable = false)
    private Integer           driverID;
    @Column(updateable = false)
    private Integer           deviceID;

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

    public Integer getYear()
    {
        return year;
    }

    public void setYear(Integer year)
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

    public Vehicle(Integer vehicleID, Integer groupID, Integer costPerHour, VehicleStatus status, String name, String make, String model, 
            Integer year, String color,
            VehicleType vtype, String vin, Integer weight, String license, State state)
    {
        super();
        this.vehicleID = vehicleID;
        this.groupID = groupID;
        this.costPerHour = costPerHour;
        this.status = status;
        this.name = name;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.vtype = vtype;
        VIN = vin;
        this.weight = weight;
        this.license = license;
        this.state = state;
    }

    public VehicleStatus getStatus()
    {
        return status;
    }

    public void setStatus(VehicleStatus status)
    {
        this.status = status;
    }

    public VehicleType getVtype()
    {
        return vtype;
    }

    public void setVtype(VehicleType vtype)
    {
        this.vtype = vtype;
    }
}
