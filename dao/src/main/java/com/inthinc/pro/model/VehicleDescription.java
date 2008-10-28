package com.inthinc.pro.model;

public class VehicleDescription extends BaseEntity
{
    Integer vehicleId;
    String  make;
    String  model;
    String  year;
    String  color;
    Integer weight;     // pounds
    String  VIN;        // 17 chars
    String  description; // e.g. sedan, suv, etc

    public Integer getVehicleId()
    {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId)
    {
        this.vehicleId = vehicleId;
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

    public Integer getWeight()
    {
        return weight;
    }

    public void setWeight(Integer weight)
    {
        this.weight = weight;
    }

    public String getVIN()
    {
        return VIN;
    }

    public void setVIN(String vin)
    {
        VIN = vin;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
