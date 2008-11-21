package com.inthinc.pro.model;

import com.inthinc.pro.dao.annotations.Column;

public class VehicleSensitivity extends BaseEntity
{
    Integer vehicleId;
    @Column(name = "accel")
    Integer hardAcceleration;
    @Column(name = "brake")
    Integer hardBrake;
    @Column(name = "turn")
    Integer hardTurn;
    @Column(name = "vert")
    Integer hardVertical;

    public Integer getVehicleId()
    {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId)
    {
        this.vehicleId = vehicleId;
    }

    public Integer getHardAcceleration()
    {
        return hardAcceleration;
    }

    public void setHardAcceleration(Integer hardAccelerationLevel)
    {
        this.hardAcceleration = hardAccelerationLevel;
    }

    public Integer getHardBrake()
    {
        return hardBrake;
    }

    public void setHardBrake(Integer hardBrakeLevel)
    {
        this.hardBrake = hardBrakeLevel;
    }

    public Integer getHardTurn()
    {
        return hardTurn;
    }

    public void setHardTurn(Integer hardTurnLevel)
    {
        this.hardTurn = hardTurnLevel;
    }

    public Integer getHardVertical()
    {
        return hardVertical;
    }

    public void setHardVertical(Integer hardVerticalLevel)
    {
        this.hardVertical = hardVerticalLevel;
    }
}
