package com.inthinc.pro.service.impl;

import java.util.List;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.service.DeviceService;

public class DeviceServiceImpl extends BaseService implements DeviceService {

    private DeviceDAO deviceDAO;

    public List<Device> getAll() {
        return deviceDAO.getDevicesByAcctID(securityBean.getAccountID());
    }

    public Device get(Integer deviceID) {
        Device device = securityBean.getDevice(deviceID);

        if (securityBean.isAuthorized(device))
            return device;

        return null;
    }

    public Device findByIMEI(String imei) {
        Device device = securityBean.getDeviceByIMEI(imei);

        if (securityBean.isAuthorized(device))
            return device;

        return null;
    }

    public Device findBySerialNum(String serialNum) {
        Device device = securityBean.getDeviceBySerialNum(serialNum);

        if (securityBean.isAuthorized(device))
            return device;

        return null;
    }

    public void setDeviceDAO(DeviceDAO deviceDAO) {
        this.deviceDAO = deviceDAO;
    }

    public DeviceDAO getDeviceDAO() {
        return deviceDAO;
    }

}
