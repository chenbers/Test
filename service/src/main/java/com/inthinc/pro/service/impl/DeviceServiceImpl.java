package com.inthinc.pro.service.impl;

import java.util.List;

import com.inthinc.pro.model.Device;
import com.inthinc.pro.service.DeviceService;
import com.inthinc.pro.util.SecureDeviceDAO;

public class DeviceServiceImpl implements DeviceService {

    private SecureDeviceDAO deviceDAO;

    public List<Device> getAll() {
        return deviceDAO.getAll();
    }

    public Device get(Integer deviceID) {
        return deviceDAO.findByID(deviceID);
    }

    public Device findByIMEI(String imei) {
        return deviceDAO.findByIMEI(imei);
    }

    public Device findBySerialNum(String serialNum) {
        return deviceDAO.findBySerialNum(serialNum);
    }

    public void setDeviceDAO(SecureDeviceDAO deviceDAO) {
        this.deviceDAO = deviceDAO;
    }

    public SecureDeviceDAO getDeviceDAO() {
        return deviceDAO;
    }
}
