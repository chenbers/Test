package com.inthinc.pro.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement
public abstract class RedFlagOrZoneAlert extends BaseAlert implements Comparable<RedFlagOrZoneAlert>{


    public RedFlagOrZoneAlert() {
    }

    public RedFlagOrZoneAlert(Integer accountID, Integer userID, String name, String description, Integer startTOD, Integer stopTOD, List<Boolean> dayOfWeek, List<Integer> groupIDs,
            List<Integer> driverIDs, List<Integer> vehicleIDs, List<VehicleType> vehicleTypes, List<Integer> notifyPersonIDs, List<String> emailTo) {
        super(accountID, userID, name, description, startTOD, stopTOD, dayOfWeek, groupIDs, driverIDs, vehicleIDs, vehicleTypes, notifyPersonIDs, emailTo);
    }

    @Override
    public int compareTo(RedFlagOrZoneAlert o) {

        return this.getId().compareTo(o.getId());
    }

    public  AlertMessageType getType(){
        return null;
    }

    public void setType(AlertMessageType type){
        
    }
    public Integer[] getSpeedSettings(){
        return null;
    }

    public void setSpeedSettings(Integer[] speedSettings){}

    public Boolean isHardAccelerationNull(){
        return false;
    }
    
    public Integer getHardAcceleration(){
        
        return null;
    }

    public void setHardAcceleration(Integer hardAcceleration){}

    public Boolean isHardBrakeNull(){
        return false;
    }
    
    public Integer getHardBrake(){
        
        return null;
    }

    public void setHardBrake(Integer hardBrake){
        
    }

    public Boolean isHardTurnNull(){
        return false;
    }
    
    public Integer getHardTurn(){
        return null;
    }
    public void setHardTurn(Integer hardTurn){}
    public Boolean isHardVerticalNull(){
        return false;
    }
    public Integer getHardVertical(){
        return null;
    }

    public  void setHardVertical(Integer hardVertical){
        
    }
    public RedFlagLevel getSeverityLevel() {
        return null;
    }

    public void setSeverityLevel(RedFlagLevel severityLevel) {
        
    }

    public Integer getZoneID()
    {
        return null;
    }

    public void setZoneID(Integer zoneID)
    {
    }

    public Boolean getArrival()
    {
        return false;
    }

    public void setArrival(Boolean arrival)
    {
        
    }

    public Boolean getDeparture()
    {
        return false;
    }

    public void setDeparture(Boolean departure)
    {
       
    }
    
    public abstract Integer getId();
    public abstract void setId(Integer id);
}
