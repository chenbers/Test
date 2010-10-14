package com.inthinc.pro.model;

import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;
import com.inthinc.pro.model.configurator.TiwiproSpeedingConstants;

@XmlRootElement
public class RedFlagAlert extends BaseAlert {

    @Column(updateable = false)
    private static final long serialVersionUID = -1621262257747114161L;
    @Column(updateable = false)
    public static final Integer DEFAULT_LEVEL = 2;
    @Column(updateable = false)
    public static final Integer DEFAULT_BUMP_LEVEL = 3;
    @ID
    @Column(name = "alertID")
    private Integer redFlagAlertID;
    private Integer[] speedSettings;
    @Column(name = "accel")
    private Integer hardAcceleration;
    @Column(name = "brake")
    private Integer hardBrake;
    @Column(name = "turn")
    private Integer hardTurn;
    @Column(name = "vert")
    private Integer hardVertical;
    
    private RedFlagLevel severityLevel;
    private AlertMessageType type;
    
    public RedFlagAlert() {
    }

    public RedFlagAlert(AlertMessageType type, Integer accountID, Integer userID, String name, String description, Integer startTOD, Integer stopTOD, List<Boolean> dayOfWeek, List<Integer> groupIDs,
            List<Integer> driverIDs, List<Integer> vehicleIDs, List<VehicleType> vehicleTypes, List<Integer> notifyPersonIDs, List<String> emailTo, Integer[] speedSettings,
            Integer hardAcceleration, Integer hardBrake, Integer hardTurn, Integer hardVertical, RedFlagLevel severityLevel
            ) {
        super(accountID, userID, name, description, startTOD, stopTOD, dayOfWeek, groupIDs, driverIDs, vehicleIDs, vehicleTypes, notifyPersonIDs, emailTo);
        this.type = type;
        this.speedSettings = speedSettings;
        this.hardAcceleration = hardAcceleration;
        this.hardBrake = hardBrake;
        this.hardTurn = hardTurn;
        this.hardVertical = hardVertical;
        this.severityLevel = severityLevel;
    }

    public Integer getRedFlagAlertID() {
        return redFlagAlertID;
    }

    public void setRedFlagAlertID(Integer redFlagAlertID) {
        this.redFlagAlertID = redFlagAlertID;
    }

    public RedFlagLevel getSeverityLevel() {
        return severityLevel;
    }

    public void setSeverityLevel(RedFlagLevel severityLevel) {
        this.severityLevel = severityLevel;
    }

    public AlertMessageType getType() {
        return type;
    }

    public void setType(AlertMessageType type) {
        this.type = type;
    }
    
    public Integer[] getSpeedSettings() {
        return speedSettings;
    }

    public void setSpeedSettings(Integer[] speedSettings) {
        this.speedSettings = speedSettings;
        if ((speedSettings != null) && (speedSettings.length != TiwiproSpeedingConstants.INSTANCE.NUM_SPEEDS))
            throw new IllegalArgumentException("speedSettings.length must be " + TiwiproSpeedingConstants.INSTANCE.NUM_SPEEDS);
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

    @Override
    public String toString() {
        return "RedFlagAlert [type=" + type + ", severityLevel=" + severityLevel + ", hardAcceleration=" + hardAcceleration + ", hardBrake="
                + hardBrake + ", hardTurn=" + hardTurn + ", hardVertical=" + hardVertical
                + ", redFlagAlertID=" + redFlagAlertID +  ", speedLevels="
                + ", speedSettings=" + Arrays.toString(speedSettings) + "]";
    }

}
