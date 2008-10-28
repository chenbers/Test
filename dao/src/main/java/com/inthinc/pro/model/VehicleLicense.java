package com.inthinc.pro.model;

public class VehicleLicense extends BaseEntity
{
    Integer vehicleId;
    String  number;
    State   state;

    public Integer getVehicleId()
    {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId)
    {
        this.vehicleId = vehicleId;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public State getState()
    {
        return state;
    }

    public void setState(State state)
    {
        this.state = state;
    }
}
