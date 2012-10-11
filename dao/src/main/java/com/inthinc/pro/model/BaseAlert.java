package com.inthinc.pro.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.inthinc.pro.dao.annotations.Column;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class BaseAlert extends BaseEntity
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Column(updateable = false)
    public static final int     MIN_TOD           = 0;
    @Column(updateable = false)
    public static final int     MAX_TOD           = 1439;
    @Column(updateable = false)
    public static final Integer DEFAULT_START_TOD = 480; // 8:00 am
    @Column(updateable = false)
    public static final Integer DEFAULT_STOP_TOD  = 1020; // 5:00 pm

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
	
    private List<String>        emailTo;
    private Status              status;

    @Column(updateable = false)
    private String 				fullName;

    private List<Integer>       voiceEscalationPersonIDs;
    private Integer             emailEscalationPersonID;
    private Integer             maxEscalationTries;
    private Integer             maxEscalationTryTime;
    private Integer             escalationTimeBetweenRetries;

//    private Integer             timeoutUnits; //calls or minutes
    

    public BaseAlert()
    {
        
    }
    
    public BaseAlert(Integer accountID, Integer userID, String name, String description, Integer startTOD, Integer stopTOD, List<Boolean> dayOfWeek, List<Integer> groupIDs,
            List<Integer> driverIDs, List<Integer> vehicleIDs, List<VehicleType> vehicleTypes, List<Integer> notifyPersonIDs, List<String> emailTo)
    {
        super();
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
        this.emailTo = emailTo;
        this.status = Status.ACTIVE;
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

    public List<String> getEmailTo()
    {
        if (emailTo == null)
            return new ArrayList<String>();
        return emailTo;
    }

    public void setEmailTo(List<String> emailTo)
    {
        this.emailTo = emailTo;
    }

    public String getEmailToString()
    {
        final StringBuilder sb = new StringBuilder();
        if (getEmailTo() != null)
            for (final String email : getEmailTo())
            {
                if (sb.length() > 0)
                    sb.append(", ");
                sb.append(email);
            }
        return sb.toString();
    }

    public void setEmailToString(String emailToString)
    {
        if ((emailToString != null) && (emailToString.trim().length() > 0))
            setEmailTo(new ArrayList<String>(Arrays.asList(emailToString.split("[,; ]+"))));
        else
            setEmailTo(new ArrayList<String>());
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
	
    public List<Integer> getVoiceEscalationPersonIDs() {
        return voiceEscalationPersonIDs;
    }

    public void setVoiceEscalationPersonIDs(List<Integer> voiceEscalationPersonIDs) {
        this.voiceEscalationPersonIDs = voiceEscalationPersonIDs;
    }

    public Integer getEmailEscalationPersonID() {
        return emailEscalationPersonID;
    }

    public void setEmailEscalationPersonID(Integer emailEscalationPersonID) {
        this.emailEscalationPersonID = emailEscalationPersonID;
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
/*    

    public Integer getTimeoutUnits() {
        return timeoutUnits;
    }

    public void setTimeoutUnits(Integer timeoutUnits) {
        this.timeoutUnits = timeoutUnits;
    }
    
*/
}
