package com.inthinc.pro.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;
import com.inthinc.pro.dao.annotations.SimpleName;
import com.inthinc.pro.model.configurator.SpeedingConstants;

@SuppressWarnings("serial")
@XmlRootElement
@SimpleName(simpleName = "Alert")
public class RedFlagAlert extends BaseEntity implements Comparable<RedFlagAlert>
{

    /**
     * 
     */
    @ID
    @Column(name = "alertID")
    private Integer alertID;
    
    @Column(updateable = false)
    public static final int     MIN_TOD           = 0;
    @Column(updateable = false)
    public static final int     MAX_TOD           = 1439;
    @Column(updateable = false)
    public static final Integer DEFAULT_START_TOD = 480; // 8:00 am
    @Column(updateable = false)
    public static final Integer DEFAULT_STOP_TOD  = 1020; // 5:00 pm

    @Column(updateable = false)
    public static final Integer DEFAULT_LEVEL = 2;
    @Column(updateable = false)
    public static final Integer DEFAULT_BUMP_LEVEL = 3;
 
    @Column(name = "acctID")
    private Integer             accountID;
	private Integer             userID;
    private String              name;
    private String              description;
    private Integer             startTOD;
    private Integer             stopTOD;
    private List<Boolean>       dayOfWeek;
    private List<Integer>       groupIDs;
    private List<Integer>       driverIDs;
    private List<Integer>       vehicleIDs;
    private List<VehicleType>   vehicleTypes;
	private List<Integer>       notifyPersonIDs;
	private Boolean             notifyManagers;
	
//    private List<String>        emailTo;
    private Status              status;

    @Column(updateable = false)
    private String 				fullName;

    private List<AlertEscalationItem>  escalationList;
    @Column(name = "escalationTryLimit")
    private Integer             maxEscalationTries;
    @Column(name = "escalationTryTimeLimit")
    private Integer             maxEscalationTryTime;
    @Column(name = "escalationCallDelay")
    private Integer             escalationTimeBetweenRetries;

//    @Column(updateable = false)
//    private Integer             timeoutUnits; //calls or minutes
    
    private RedFlagLevel severityLevel;
    
    @Column(name = "alertTypeMask")
    private List<AlertMessageType> types;
    
    private Integer[] speedSettings;
    @Column(name = "accel")
    private Integer hardAcceleration;
    @Column(name = "brake")
    private Integer hardBrake;
    @Column(name = "turn")
    private Integer hardTurn;
    @Column(name = "vert")
    private Integer hardVertical;
    @Column(name = "satellite")
    private Boolean satellite;

    private Integer zoneID;
    
    private Integer idlingThreshold;
    
    
    @Column(updateable = false)
    private Boolean useMaxSpeed = Boolean.FALSE;
    
    
    private Integer maxSpeed;

    public RedFlagAlert()
    {
    }
    
    public RedFlagAlert(List<AlertMessageType> types, Integer accountID, Integer userID, String name, String description, Integer startTOD, Integer stopTOD, List<Boolean> dayOfWeek, List<Integer> groupIDs,
            List<Integer> driverIDs, List<Integer> vehicleIDs, List<VehicleType> vehicleTypes, List<Integer> notifyPersonIDs, List<String> emailTo,Integer[] speedSettings,
            Integer hardAcceleration, Integer hardBrake, Integer hardTurn, Integer hardVertical, RedFlagLevel severityLevel,Integer zoneID, Integer idlingThreshold,
            List<AlertEscalationItem> escalationList,Integer maxEscalationTries, Integer maxEscalationTryTime, Integer escalationTimeBetweenRetries
            /*,Integer timeoutUnits */)
    {
        super();
        this.types = types;
        this.accountID = accountID;
        this.userID = userID;
        this.name = name;
        this.description = description;
        this.startTOD = startTOD;
        this.stopTOD = stopTOD;
        this.dayOfWeek = dayOfWeek;
        this.groupIDs = groupIDs;
        this.driverIDs = driverIDs;
        this.vehicleIDs = vehicleIDs;
        this.vehicleTypes = vehicleTypes;
        this.notifyPersonIDs = notifyPersonIDs;
        this.status = Status.ACTIVE;
        this.speedSettings = speedSettings;
        this.hardAcceleration = hardAcceleration;
        this.hardBrake = hardBrake;
        this.hardTurn = hardTurn;
        this.hardVertical = hardVertical;
        this.severityLevel = severityLevel;
        this.zoneID = zoneID;
        this.idlingThreshold = idlingThreshold;
        this.escalationList = escalationList;
        this.maxEscalationTries = maxEscalationTries;
        this.maxEscalationTryTime = maxEscalationTryTime;
        this.escalationTimeBetweenRetries = escalationTimeBetweenRetries;
    }

    public List<AlertMessageType> getTypes() {
        return types;
    }

    public void setTypes(List<AlertMessageType> types) {
        this.types = types;
    }

    public Integer getAlertID() {
        return alertID;
    }

    public void setAlertID(Integer alertID) {
        this.alertID = alertID;
    }

    public Integer getAccountID()
    {
        return accountID;
    }

    public void setAccountID(Integer accountID)
    {
        this.accountID = accountID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Integer getStartTOD()
    {
        return startTOD;
    }

    public void setStartTOD(Integer startTOD)
    {
        this.startTOD = startTOD;
    }

    public Integer getStopTOD()
    {
        return stopTOD;
    }

    public void setStopTOD(Integer stopTOD)
    {
        this.stopTOD = stopTOD;
    }

    public List<Boolean> getDayOfWeek()
    {
        return dayOfWeek;
    }

    public void setDayOfWeek(List<Boolean> dayOfWeek)
    {
        this.dayOfWeek = dayOfWeek;
    }

    public List<Integer> getGroupIDs()
    {
        if (groupIDs == null)
            return new ArrayList<Integer>();
        return groupIDs;
    }

    public void setGroupIDs(List<Integer> groupIDs)
    {
        this.groupIDs = groupIDs;
    }

    public List<Integer> getDriverIDs()
    {
        if (driverIDs == null)
            return new ArrayList<Integer>();
        return driverIDs;
    }

    public void setDriverIDs(List<Integer> driverIDs)
    {
        this.driverIDs = driverIDs;
    }

    public List<Integer> getVehicleIDs()
    {
        if (vehicleIDs == null)
            return new ArrayList<Integer>();
        return vehicleIDs;
    }

    public void setVehicleIDs(List<Integer> vehicleIDs)
    {
        this.vehicleIDs = vehicleIDs;
    }

    public List<VehicleType> getVehicleTypes()
    {
        if (vehicleTypes == null)
            return new ArrayList<VehicleType>();
        return vehicleTypes;
    }

    public void setVehicleTypes(List<VehicleType> vehicleTypes)
    {
        this.vehicleTypes = vehicleTypes;
    }

    public List<Integer> getNotifyPersonIDs()
    {
        if (notifyPersonIDs == null)
            return new ArrayList<Integer>();
        return notifyPersonIDs;
    }

    public void setNotifyPersonIDs(List<Integer> notifyPersonIDs)
    {
        this.notifyPersonIDs = notifyPersonIDs;
    }

    public Status getStatus()
    {
        if (status == null)
            status = Status.ACTIVE;
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

    public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
    public Integer getMaxEscalationTries() {
        return maxEscalationTries;
    }

    public void setMaxEscalationTries(Integer maxEscalationTries) {
        this.maxEscalationTries = maxEscalationTries;
    }

    public Integer getMaxEscalationTryTime() {
        return maxEscalationTryTime;
    }

    public void setMaxEscalationTryTime(Integer maxEscalationTryTime) {
        this.maxEscalationTryTime = maxEscalationTryTime;
    }

    public Integer getEscalationTimeBetweenRetries() {
        return escalationTimeBetweenRetries;
    }

    public void setEscalationTimeBetweenRetries(Integer escalationTimeBetweenRetries) {
        this.escalationTimeBetweenRetries = escalationTimeBetweenRetries;
    }

    public Integer getTimeoutUnits() {
        if (this.maxEscalationTryTime == null)
            return 1;
        else return 0;
    }
    
    public int compareTo(RedFlagAlert o) {

        return this.getAlertID().compareTo(o.getAlertID());
    }

    
    public Integer getZoneID() {
        return zoneID;
    }

    public void setZoneID(Integer zoneID) {
        this.zoneID = zoneID;
    }
    public Integer getIdlingThreshold() {
        return idlingThreshold;
    }

    public void setIdlingThreshold(Integer idlingThreshold) {
        this.idlingThreshold = idlingThreshold;
    }

    public RedFlagLevel getSeverityLevel() {
        return severityLevel;
    }

    public void setSeverityLevel(RedFlagLevel severityLevel) {
        this.severityLevel = severityLevel;
    }
    public Integer[] getSpeedSettings() {
        return speedSettings;
    }

    public void setSpeedSettings(Integer[] speedSettings) {
        this.speedSettings = speedSettings;
        if ((speedSettings != null) && (speedSettings.length != SpeedingConstants.INSTANCE.NUM_SPEEDS))
            throw new IllegalArgumentException("speedSettings.length must be " + SpeedingConstants.INSTANCE.NUM_SPEEDS);
    }

    public Boolean isHardAccelerationNull(){
        return hardAcceleration==null;
    }
    
    public Integer getHardAcceleration() {
        if (hardAcceleration == null)
            return DEFAULT_LEVEL;
        return hardAcceleration;
    }

    public void setHardAcceleration(Integer hardAcceleration) {
        this.hardAcceleration = hardAcceleration;
    }

    public Boolean isHardBrakeNull(){
        return hardBrake==null;
    }
    
    public Integer getHardBrake() {
        if (hardBrake == null)
            return DEFAULT_LEVEL;
        return hardBrake;
    }

    public void setHardBrake(Integer hardBrake) {
        this.hardBrake = hardBrake;
    }

    public Boolean isHardTurnNull(){
        return hardTurn==null;
    }
    
    public Integer getHardTurn() {
        if (hardTurn == null)
            return DEFAULT_LEVEL;
        return hardTurn;
    }

    public void setHardTurn(Integer hardTurn) {
        this.hardTurn = hardTurn;
    }

    public Boolean isHardVerticalNull(){
        return hardVertical==null;
    }
    
    public Integer getHardVertical() {
        if (hardVertical == null)
            return DEFAULT_BUMP_LEVEL;
        return hardVertical;
    }

    public void setHardVertical(Integer hardVertical) {
        this.hardVertical = hardVertical;
    }

    public Boolean getSatellite() {
        return satellite;
    }

    public void setSatellite(Boolean satellite) {
        this.satellite = satellite;
    }

    @Override
    public String toString() {
        return "RedFlagAlert [Types="+getTypes()+", severityLevel=" + severityLevel + ", hardAcceleration=" + hardAcceleration + ", hardBrake="
                + hardBrake + ", hardTurn=" + hardTurn + ", hardVertical=" + hardVertical
                + ", alertID=" + getAlertID() +  ", speedLevels="
                + ", speedSettings=" + Arrays.toString(speedSettings) + ", zoneID=" + zoneID /*+ ", emailTo="+getEmailTo()*/ +", fullName="+fullName+"]";
    }

    /**
     * Returns a non-null escalationList.  Empty if necessary, but never null. 
     * @return a non-null escalationList
     */
    public List<AlertEscalationItem> getEscalationList() {
        return (escalationList!=null)?escalationList: new ArrayList<AlertEscalationItem>();
    }

    public void setEscalationList(List<AlertEscalationItem> escalationList) {
        this.escalationList = escalationList;
    }
    /*
     * returns just the voice escalation items
     */
    public List<Integer>getVoiceEscalationPersonIDs(){
        
        List<Integer> voice = new ArrayList<Integer>();
        if(escalationList == null) return voice;
        for (AlertEscalationItem item : escalationList){
            if (!item.getEscalationOrder().equals(0)){
                voice.add(item.getPersonID());
            }
        }
        return voice;
    }
    /*
     * returns just the email escalation item
     */
    public Integer getEmailEscalationPersonID(){
        
        if(escalationList == null) return null;
        for (AlertEscalationItem item : escalationList){
            if (item.getEscalationOrder().equals(0)){
                return item.getPersonID();
            }
        }
        return null;
    }

    public void setEmailEscalationPersonID(Integer escEmailPersonID) {
        if(escEmailPersonID != null){
            boolean foundExistingEscEmailPersonID = false;
            if(escalationList == null){
                escalationList = new ArrayList<AlertEscalationItem>();          
            }
            if (!escalationList.isEmpty()) {                                    
                for (AlertEscalationItem item : escalationList) {
                    if (item.getEscalationOrder().equals(0)) {
                        item.setPersonID(escEmailPersonID);
                        foundExistingEscEmailPersonID = true;
                        break;
                    }
                }
            }
    
            if (!foundExistingEscEmailPersonID) {
                escalationList.add(new AlertEscalationItem(escEmailPersonID, 0));
            }
        }
    }
    public Integer getMaxSpeed() {
        return maxSpeed;
    }
    public void setMaxSpeed(Integer maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
    public Boolean getUseMaxSpeed() {
        return useMaxSpeed;
    }
    public void setUseMaxSpeed(Boolean useMaxSpeed) {
        this.useMaxSpeed = useMaxSpeed;
    }

    public Boolean getNotifyManagers() {
        return notifyManagers;
    }

    public void setNotifyManagers(Boolean notifyManagers) {
        this.notifyManagers = notifyManagers;
    }
}
