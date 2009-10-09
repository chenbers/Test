package com.inthinc.pro.util;

import java.util.List;

import org.jboss.resteasy.spi.NotFoundException;
import org.jboss.resteasy.spi.UnauthorizedException;
import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.model.Device;

public class SecureDeviceDAO extends BaseSecureDAO{

    private DeviceDAO deviceDAO;

    private boolean isAuthorized(Device device) {
        if (device != null) {
            if (!getAccountID().equals(device.getAccountID()))
                throw new NotFoundException("accountID not found: " + device.getAccountID());
            return true;
        }
        throw new UnauthorizedException();
    }

    public boolean isAuthorized(Integer deviceID) {
        return isAuthorized(findByID(deviceID));
    }

    public Device findByID(Integer deviceID) {
        Device device = deviceDAO.findByID(deviceID);
        if (device == null || !device.getAccountID().equals(getAccountID()))
            throw new NotFoundException("deviceID not found: " + deviceID);
        return device;
    }

    public Device findByIMEI(String imei) {
        Device device = deviceDAO.findByIMEI(imei);
        if (device == null || !device.getAccountID().equals(getAccountID()))
            throw new NotFoundException("device IMEI not found: " + imei);
        return device;
    }

    public Device findBySerialNum(String serialNum) {
        Device device = deviceDAO.findBySerialNum(serialNum);
        if (device == null || !device.getAccountID().equals(getAccountID()))
            throw new NotFoundException("device serialNum not found: " + serialNum);
        return device;
    }
    
    public List<Device> getAll()
    {
    	return deviceDAO.getDevicesByAcctID(getAccountID());
    }

    public DeviceDAO getDeviceDAO() {
        return deviceDAO;
    }

    public void setDeviceDAO(DeviceDAO deviceDAO) {
        this.deviceDAO = deviceDAO;
    }

}
