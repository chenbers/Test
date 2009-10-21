package com.inthinc.pro.util;

import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.jboss.resteasy.spi.NotFoundException;
import org.jboss.resteasy.spi.UnauthorizedException;
import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.model.Device;

public class SecureDeviceDAO extends SecureDAO<Device>{

    private DeviceDAO deviceDAO;

    @Override
    public boolean isAuthorized(Device device) {
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
    
    @Override
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
    
    @Override
    public List<Device> getAll()
    {
    	return deviceDAO.getDevicesByAcctID(getAccountID());
    }

    @Override
    public Integer create(Device object) {
        throw new NotImplementedException();
    }

    @Override
    public Integer delete(Integer id) {
        throw new NotImplementedException();
    }

    @Override
    public Integer update(Device object) {
        throw new NotImplementedException();
    }

    public DeviceDAO getDeviceDAO() {
        return deviceDAO;
    }

    public void setDeviceDAO(DeviceDAO deviceDAO) {
        this.deviceDAO = deviceDAO;
    }

}
