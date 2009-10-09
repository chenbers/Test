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
            // TODO do we give user access to all groups, regardless of the users group????
            // TODO if so, we need a fast security check to verify a group intersects with user's groups
            // TODO get Account from logged in user

            if (!getAccountID().equals(device.getAccountID()))
                throw new NotFoundException("accountID not found: " + device.getAccountID());
            return true;
        }
        throw new UnauthorizedException();
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

    public boolean isAuthorized(Integer deviceID) {
        return isAuthorized(findByID(deviceID));
    }

    public DeviceDAO getDeviceDAO() {
        return deviceDAO;
    }

    public void setDeviceDAO(DeviceDAO deviceDAO) {
        this.deviceDAO = deviceDAO;
    }

}
