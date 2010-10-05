package com.inthinc.pro.model.hos;

import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.model.BaseEntity;

public class HOSDriverLogin extends BaseEntity {
    
    private Integer driverID;
    private Integer deviceID;
    private Integer acctID;
    private RuleSetType driverDotType;
    private String timezoneID;
    private Integer currentVehicleID;
    private Integer currentDeviceID;
    private boolean currentOcupantFlag;
    private String currentAddress;
    private RuleSetType vehicleDotType;
    
    
    
    public HOSDriverLogin(Integer acctID, Integer deviceID, Integer driverID) {
        super();
        this.driverID = driverID;
        this.deviceID = deviceID;
        this.acctID = acctID;
    }

    public HOSDriverLogin() {
        super();
    }

    public Integer getDriverID() {
        return driverID;
    }

    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
    }

    public Integer getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Integer deviceID) {
        this.deviceID = deviceID;
    }

    public Integer getAcctID() {
        return acctID;
    }

    public void setAcctID(Integer acctID) {
        this.acctID = acctID;
    }

    public RuleSetType getDriverDotType() {
        return driverDotType;
    }

    public void setDriverDotType(RuleSetType driverDotType) {
        this.driverDotType = driverDotType;
    }
    
    public String getTimezoneID() {
        return timezoneID;
    }
    public void setTimezoneID(String timezoneID) {
        this.timezoneID = timezoneID;
    }

    public Integer getCurrentVehicleID() {
        return currentVehicleID;
    }

    public void setCurrentVehicleID(Integer currentVehicleID) {
        this.currentVehicleID = currentVehicleID;
    }

    public Integer getCurrentDeviceID() {
        return currentDeviceID;
    }

    public void setCurrentDeviceID(Integer currentDeviceID) {
        this.currentDeviceID = currentDeviceID;
    }

    public boolean isCurrentOcupantFlag() {
        return currentOcupantFlag;
    }

    public void setCurrentOcupantFlag(boolean currentOcupantFlag) {
        this.currentOcupantFlag = currentOcupantFlag;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public RuleSetType getVehicleDotType() {
        return vehicleDotType;
    }

    public void setVehicleDotType(RuleSetType vehicleDotType) {
        this.vehicleDotType = vehicleDotType;
    }
    
}
