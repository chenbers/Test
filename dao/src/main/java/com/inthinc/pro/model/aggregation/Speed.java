package com.inthinc.pro.model.aggregation;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Speed {
    private Integer speedTime;
    private Integer maxSpeed;
    private Integer driverID;

    public Speed() {
    }

    public Speed(Integer speedTime, Integer maxSpeed) {
        this.speedTime = speedTime;
        this.maxSpeed = maxSpeed;
    }

    public Integer getSpeedTime() {
        return speedTime;
    }

    public void setSpeedTime(Integer speedTime) {
        this.speedTime = speedTime;
    }

    public Integer getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Integer maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Integer getDriverID() {
        return driverID;
    }

    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
    }
}
