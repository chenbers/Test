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
        Device device = deviceDAO.findByID(deviceID);

        if (securityBean.isAuthorized(device))
            return device;

        return null;
    }

    public Device findByIMEI(String imei) {
        Device device = deviceDAO.findByIMEI(imei);

        if (securityBean.isAuthorized(device))
            return device;

        return null;
    }

    public Device findBySerialNum(String serialNum) {
        Device device = deviceDAO.findBySerialNum(serialNum);

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
