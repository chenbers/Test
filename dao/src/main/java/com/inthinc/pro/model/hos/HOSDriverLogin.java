package com.inthinc.pro.model.hos;

import com.inthinc.pro.model.BaseEntity;

public class HOSDriverLogin extends BaseEntity {
    
    Integer driverID;
    Integer deviceID;
    Integer acctID;

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

    
}
