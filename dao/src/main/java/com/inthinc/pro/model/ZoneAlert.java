package com.inthinc.pro.model;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

public class ZoneAlert extends BaseAlert
{
    @Column(updateable = false)
    private static final long serialVersionUID = 3066238032590993441L;

    @ID
    private Integer           zoneAlertID;
    private Integer           zoneID;
    private Boolean           arrival;
    private Boolean           departure;
    private Boolean           driverIDViolation;
    private Boolean           ignitionOn;
    private Boolean           ignitionOff;
    private Boolean           position;
    private Boolean           seatbeltViolation;
    private Boolean           speedLimit;
    private Boolean           speedViolation;
    private Boolean           masterBuzzer;
    private Boolean           cautionArea;
    private Boolean           disableRF;
    private Boolean           monitorIdle;

    public Integer getZoneAlertID()
    {
        return zoneAlertID;
    }

    public void setZoneAlertID(Integer zoneAlertID)
    {
        this.zoneAlertID = zoneAlertID;
    }

    public Integer getZoneID()
    {
        return zoneID;
    }

    public void setZoneID(Integer zoneID)
    {
        this.zoneID = zoneID;
    }

    public Boolean getArrival()
    {
        return arrival;
    }

    public void setArrival(Boolean arrival)
    {
        this.arrival = arrival;
    }

    public Boolean getDeparture()
    {
        return departure;
    }

    public void setDeparture(Boolean departure)
    {
        this.departure = departure;
    }

    public Boolean getDriverIDViolation()
    {
        return driverIDViolation;
    }

    public void setDriverIDViolation(Boolean driverIDViolation)
    {
        this.driverIDViolation = driverIDViolation;
    }

    public Boolean getIgnitionOn()
    {
        return ignitionOn;
    }

    public void setIgnitionOn(Boolean ignitionOn)
    {
        this.ignitionOn = ignitionOn;
    }

    public Boolean getIgnitionOff()
    {
        return ignitionOff;
    }

    public void setIgnitionOff(Boolean ignitionOff)
    {
        this.ignitionOff = ignitionOff;
    }

    public Boolean getPosition()
    {
        return position;
    }

    public void setPosition(Boolean position)
    {
        this.position = position;
    }

    public Boolean getSeatbeltViolation()
    {
        return seatbeltViolation;
    }

    public void setSeatbeltViolation(Boolean seatbeltViolation)
    {
        this.seatbeltViolation = seatbeltViolation;
    }

    public Boolean getSpeedLimit()
    {
        return speedLimit;
    }

    public void setSpeedLimit(Boolean speedLimit)
    {
        this.speedLimit = speedLimit;
    }

    public Boolean getSpeedViolation()
    {
        return speedViolation;
    }

    public void setSpeedViolation(Boolean speedViolation)
    {
        this.speedViolation = speedViolation;
    }

    public Boolean getMasterBuzzer()
    {
        return masterBuzzer;
    }

    public void setMasterBuzzer(Boolean masterBuzzer)
    {
        this.masterBuzzer = masterBuzzer;
    }

    public Boolean getCautionArea()
    {
        return cautionArea;
    }

    public void setCautionArea(Boolean cautionArea)
    {
        this.cautionArea = cautionArea;
    }

    public Boolean getDisableRF()
    {
        return disableRF;
    }

    public void setDisableRF(Boolean disableRF)
    {
        this.disableRF = disableRF;
    }

    public Boolean getMonitorIdle()
    {
        return monitorIdle;
    }

    public void setMonitorIdle(Boolean monitorIdle)
    {
        this.monitorIdle = monitorIdle;
    }
}
