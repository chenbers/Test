package com.inthinc.pro.model;

public class VehicleCompanyInfo extends BaseEntity
{
    int    vehicleID;
    String vehicleIdentification; // name/id used by company
    int    costPerHour;          // in cents

    public int getVehicleID()
    {
        return vehicleID;
    }

    public void setVehicleID(int vehicleId)
    {
        this.vehicleID = vehicleId;
    }

    public String getVehicleIdentification()
    {
        return vehicleIdentification;
    }

    public void setVehicleIdentification(String vehicleIdentification)
    {
        this.vehicleIdentification = vehicleIdentification;
    }

    public int getCostPerHour()
    {
        return costPerHour;
    }

    public void setCostPerHour(int costPerHour)
    {
        this.costPerHour = costPerHour;
    }
}
