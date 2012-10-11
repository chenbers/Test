package com.inthinc.pro.util;

import java.util.List;

import org.apache.commons.lang.NotImplementedException;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.model.Device;

public class SecureDeviceDAO extends SecureDAO<Device> {

    private DeviceDAO deviceDAO;

    @Override
    public boolean isAuthorized(Device device) {
        if (device != null) {
            if (isInthincUser() || getAccountID().equals(device.getAccountID()))
                return true;
        }
        return false;
    }

    public boolean isAuthorized(Integer deviceID) {
        return isAuthorized(findByID(deviceID));
    }

    @Override
    public Device findByID(Integer deviceID) {
        Device device = deviceDAO.findByID(deviceID);
        if (isAuthorized(device))
            return device;
        return null;
    }

    public Device findByIMEI(String imei) {
        Device device = deviceDAO.findByIMEI(imei);
        if (isAuthorized(device))
            return device;
        return null;
    }

    public Device findBySerialNum(String serialNum) {
        Device device = deviceDAO.findBySerialNum(serialNum);
        if (isAuthorized(device))
            return device;
        return null;
    }

    @Override
    public List<Device> getAll() {
        return deviceDAO.getDevicesByAcctID(getAccountID());
    }

    @Override
    public Integer create(Device device) {
        if (isAuthorized(device))
            return deviceDAO.create(getAccountID(), device);
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        throw new NotImplementedException();
    }

    @Override
    public Device update(Device object) {
        throw new NotImplementedException();
    }

    public DeviceDAO getDeviceDAO() {
        return deviceDAO;
    }

    public void setDeviceDAO(DeviceDAO deviceDAO) {
        this.deviceDAO = deviceDAO;
    }

}
