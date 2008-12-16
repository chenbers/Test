package com.inthinc.pro.model;

import java.util.List;

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
    private Integer           speedLimit;
    private Boolean           speedViolation;
    private Boolean           masterBuzzer;
    private Boolean           cautionArea;
    private Boolean           disableRF;
    private Boolean           monitorIdle;

    public ZoneAlert(Integer accountID, String name, String description, Integer startTOD, Integer stopTOD, List<Boolean> dayOfWeek, List<Integer> groupIDs,
            List<Integer> driverIDs, List<Integer> vehicleIDs, List<VehicleType> vehicleTypes, List<Integer> notifyPersonIDs, List<String> emailTo,
            Integer zoneAlertID, Integer zoneID, Boolean arrival, Boolean departure, Boolean driverIDViolation, Boolean ignitionOn, Boolean ignitionOff, Boolean position,
            Boolean seatbeltViolation, Integer speedLimit, Boolean speedViolation, Boolean masterBuzzer, Boolean cautionArea, Boolean disableRF, Boolean monitorIdle)
    {
        super(accountID, name, description, startTOD, stopTOD, dayOfWeek, groupIDs, driverIDs, vehicleIDs, vehicleTypes, notifyPersonIDs, emailTo);
        this.zoneAlertID = zoneAlertID;
        this.zoneID = zoneID;
        this.arrival = arrival;
        this.departure = departure;
        this.driverIDViolation = driverIDViolation;
        this.ignitionOn = ignitionOn;
        this.ignitionOff = ignitionOff;
        this.position = position;
        this.seatbeltViolation = seatbeltViolation;
        this.speedLimit = speedLimit;
        this.speedViolation = speedViolation;
        this.masterBuzzer = masterBuzzer;
        this.cautionArea = cautionArea;
        this.disableRF = disableRF;
        this.monitorIdle = monitorIdle;
    }

    public ZoneAlert(Integer zoneAlertID, Integer zoneID, Boolean arrival, Boolean departure, Boolean driverIDViolation, Boolean ignitionOn, Boolean ignitionOff, Boolean position,
            Boolean seatbeltViolation, Integer speedLimit, Boolean speedViolation, Boolean masterBuzzer, Boolean cautionArea, Boolean disableRF, Boolean monitorIdle)
    {
        super();
        this.zoneAlertID = zoneAlertID;
        this.zoneID = zoneID;
        this.arrival = arrival;
        this.departure = departure;
        this.driverIDViolation = driverIDViolation;
        this.ignitionOn = ignitionOn;
        this.ignitionOff = ignitionOff;
        this.position = position;
        this.seatbeltViolation = seatbeltViolation;
        this.speedLimit = speedLimit;
        this.speedViolation = speedViolation;
        this.masterBuzzer = masterBuzzer;
        this.cautionArea = cautionArea;
        this.disableRF = disableRF;
        this.monitorIdle = monitorIdle;
    }

    public ZoneAlert()
    {
        super();
    }
    
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

    public Integer getSpeedLimit()
    {
        return speedLimit;
    }

    public void setSpeedLimit(Integer speedLimit)
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
