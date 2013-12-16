package com.inthinc.pro.dao.service.mapper;

import com.inthinc.pro.dao.service.dto.Device;

public class DeviceMapper implements GenericMapper <com.inthinc.pro.dao.service.dto.Device, com.inthinc.pro.model.Device> {

    @Override
    public Device mapToDTO(com.inthinc.pro.model.Device device) {
        Device dtoDevice = new Device();
        dtoDevice.setDeviceID(device.getDeviceID());
        dtoDevice.setProductVersion(device.getProductVer());
        return dtoDevice;
    }

    @Override
    public com.inthinc.pro.model.Device mapFromDTO(Device dtoDevice) {
        
        com.inthinc.pro.model.Device device = new com.inthinc.pro.model.Device();
        device.setDeviceID(dtoDevice.getDeviceID());
        device.setProductVer(dtoDevice.getProductVersion());
        return device;
    }

}
