package com.inthinc.pro.model;

import java.util.Arrays;
import java.util.List;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

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
    private RedFlagLevel[] speedLevels;
    @Column(name = "accel")
    private Integer hardAcceleration;
    @Column(name = "brake")
    private Integer hardBrake;
    @Column(name = "turn")
    private Integer hardTurn;
    @Column(name = "vert")
    private Integer hardVertical;
    private RedFlagLevel hardAccelerationLevel;
    private RedFlagLevel hardBrakeLevel;
    private RedFlagLevel hardTurnLevel;
    private RedFlagLevel hardVerticalLevel;
    private RedFlagLevel seatBeltLevel;
    private RedFlagLevel crashLevel;
    private RedFlagLevel tamperingLevel;
    private RedFlagLevel lowBatteryLevel;

    public RedFlagAlert() {
    }

    public RedFlagAlert(Integer accountID, String name, String description, Integer startTOD, Integer stopTOD, List<Boolean> dayOfWeek, List<Integer> groupIDs,
            List<Integer> driverIDs, List<Integer> vehicleIDs, List<VehicleType> vehicleTypes, List<Integer> notifyPersonIDs, List<String> emailTo, Integer[] speedSettings,
            RedFlagLevel[] speedLevels, Integer hardAcceleration, Integer hardBrake, Integer hardTurn, Integer hardVertical, RedFlagLevel hardAccelerationLevel,
            RedFlagLevel hardBrakeLevel, RedFlagLevel hardTurnLevel, RedFlagLevel hardVerticalLevel, RedFlagLevel seatBeltLevel, RedFlagLevel crashLevel,RedFlagLevel tamperingLevel,
            RedFlagLevel lowBatteryLevel) {
        super(accountID, name, description, startTOD, stopTOD, dayOfWeek, groupIDs, driverIDs, vehicleIDs, vehicleTypes, notifyPersonIDs, emailTo);
        this.speedSettings = speedSettings;
        this.speedLevels = speedLevels;
        this.hardAcceleration = hardAcceleration;
        this.hardBrake = hardBrake;
        this.hardTurn = hardTurn;
        this.hardVertical = hardVertical;
        this.hardAccelerationLevel = hardAccelerationLevel;
        this.hardBrakeLevel = hardBrakeLevel;
        this.hardTurnLevel = hardTurnLevel;
        this.hardVerticalLevel = hardVerticalLevel;
        this.seatBeltLevel = seatBeltLevel;
        this.crashLevel = crashLevel;
        this.lowBatteryLevel = lowBatteryLevel;
        this.tamperingLevel = tamperingLevel;
    }

    public Integer getRedFlagAlertID() {
        return redFlagAlertID;
    }

    public void setRedFlagAlertID(Integer redFlagAlertID) {
        this.redFlagAlertID = redFlagAlertID;
    }

    public Integer[] getSpeedSettings() {
        return speedSettings;
    }

    public void setSpeedSettings(Integer[] speedSettings) {
        this.speedSettings = speedSettings;
        if ((speedSettings != null) && (speedSettings.length != Device.NUM_SPEEDS))
            throw new IllegalArgumentException("speedSettings.length must be " + Device.NUM_SPEEDS);
    }

    public RedFlagLevel[] getSpeedLevels() {
        return speedLevels;
    }

    public void setSpeedLevels(RedFlagLevel[] speedLevels) {
        this.speedLevels = speedLevels;
    }

    public Integer getHardAcceleration() {
        if (hardAcceleration == null)
            return DEFAULT_LEVEL;
        return hardAcceleration;
    }

    public void setHardAcceleration(Integer hardAcceleration) {
        this.hardAcceleration = hardAcceleration;
    }

    public Integer getHardBrake() {
        if (hardBrake == null)
            return DEFAULT_LEVEL;
        return hardBrake;
    }

    public void setHardBrake(Integer hardBrake) {
        this.hardBrake = hardBrake;
    }

    public Integer getHardTurn() {
        if (hardTurn == null)
            return DEFAULT_LEVEL;
        return hardTurn;
    }

    public void setHardTurn(Integer hardTurn) {
        this.hardTurn = hardTurn;
    }

    public Integer getHardVertical() {
        if (hardVertical == null)
            return DEFAULT_BUMP_LEVEL;
        return hardVertical;
    }

    public void setHardVertical(Integer hardVertical) {
        this.hardVertical = hardVertical;
    }

    public RedFlagLevel getHardAccelerationLevel() {
        return hardAccelerationLevel;
    }

    public void setHardAccelerationLevel(RedFlagLevel hardAccelerationLevel) {
        this.hardAccelerationLevel = hardAccelerationLevel;
    }

    public RedFlagLevel getHardBrakeLevel() {
        return hardBrakeLevel;
    }

    public void setHardBrakeLevel(RedFlagLevel hardBrakeLevel) {
        this.hardBrakeLevel = hardBrakeLevel;
    }

    public RedFlagLevel getHardTurnLevel() {
        return hardTurnLevel;
    }

    public void setHardTurnLevel(RedFlagLevel hardTurnLevel) {
        this.hardTurnLevel = hardTurnLevel;
    }

    public RedFlagLevel getHardVerticalLevel() {
        return hardVerticalLevel;
    }

    public void setHardVerticalLevel(RedFlagLevel hardVerticalLevel) {
        this.hardVerticalLevel = hardVerticalLevel;
    }

    public RedFlagLevel getSeatBeltLevel() {
        return seatBeltLevel;
    }

    public void setSeatBeltLevel(RedFlagLevel seatBeltLevel) {
        this.seatBeltLevel = seatBeltLevel;
    }

    public RedFlagLevel getCrashLevel() {
        return crashLevel;
    }

    public void setCrashLevel(RedFlagLevel crashLevel) {
        this.crashLevel = crashLevel;
    }

    public void setTamperingLevel(RedFlagLevel tamperingLevel) {
        this.tamperingLevel = tamperingLevel;
    }

    public RedFlagLevel getTamperingLevel() {
        return tamperingLevel;
    }

    @Override
    public String toString() {
        return "RedFlagAlert [crashLevel=" + crashLevel + ", hardAcceleration=" + hardAcceleration + ", hardAccelerationLevel=" + hardAccelerationLevel + ", hardBrake="
                + hardBrake + ", hardBrakeLevel=" + hardBrakeLevel + ", hardTurn=" + hardTurn + ", hardTurnLevel=" + hardTurnLevel + ", hardVertical=" + hardVertical
                + ", hardVerticalLevel=" + hardVerticalLevel + ", redFlagAlertID=" + redFlagAlertID + ", seatBeltLevel=" + seatBeltLevel + ", speedLevels="
                + Arrays.toString(speedLevels) + ", speedSettings=" + Arrays.toString(speedSettings) + ", tamperingLevel=" + tamperingLevel + "]";
    }

    public void setLowBatteryLevel(RedFlagLevel lowBatteryLevel) {
        this.lowBatteryLevel = lowBatteryLevel;
    }

    public RedFlagLevel getLowBatteryLevel() {
        return lowBatteryLevel;
    }
}
