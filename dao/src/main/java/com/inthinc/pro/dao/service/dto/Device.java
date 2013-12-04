package com.inthinc.pro.dao.service.dto;

public class Device {
    
    private Integer deviceID;
    private Integer productVersion;

    public Device() {}

    public Integer getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Integer deviceID) {
        this.deviceID = deviceID;
    }
    
    public Integer getProductVersion() {
        return productVersion;
    }

    public void setProductVersion(Integer productVersion) {
        this.productVersion = productVersion;
    }
}
