package com.inthinc.pro.model;

public class VehicleSensitivity extends BaseEntity
{
    Integer vehicleId;
    Integer hardAccelerationLevel;
    Integer hardBrakeLevel;
    Integer hardTurnLevel;
    Integer hardVerticalLevel;

    public Integer getVehicleId()
    {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId)
    {
        this.vehicleId = vehicleId;
    }

    public Integer getHardAccelerationLevel()
    {
        return hardAccelerationLevel;
    }

    public void setHardAccelerationLevel(Integer hardAccelerationLevel)
    {
        this.hardAccelerationLevel = hardAccelerationLevel;
    }

    public Integer getHardBrakeLevel()
    {
        return hardBrakeLevel;
    }

    public void setHardBrakeLevel(Integer hardBrakeLevel)
    {
        this.hardBrakeLevel = hardBrakeLevel;
    }

    public Integer getHardTurnLevel()
    {
        return hardTurnLevel;
    }

    public void setHardTurnLevel(Integer hardTurnLevel)
    {
        this.hardTurnLevel = hardTurnLevel;
    }

    public Integer getHardVerticalLevel()
    {
        return hardVerticalLevel;
    }

    public void setHardVerticalLevel(Integer hardVerticalLevel)
    {
        this.hardVerticalLevel = hardVerticalLevel;
    }
}
