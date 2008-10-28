package com.inthinc.pro.model;

public class VehicleCompanyInfo extends BaseEntity
{
    int    vehicleId;
    String vehicleIdentification; // name/id used by company
    int    costPerHour;          // in cents

    public int getVehicleId()
    {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId)
    {
        this.vehicleId = vehicleId;
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
