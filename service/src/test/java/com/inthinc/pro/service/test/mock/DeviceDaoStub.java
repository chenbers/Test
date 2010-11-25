/**
 * 
 */
package com.inthinc.pro.service.test.mock;

import java.util.List;

import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.ForwardCommand;
import com.inthinc.pro.model.ForwardCommandStatus;

/**
 * @author dfreitas
 * 
 */
@Component
public class DeviceDaoStub implements DeviceDAO {
    private Device expectedDevice;

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.DeviceDAO#findByIMEI(java.lang.String)
     */
    @Override
    public Device findByIMEI(String imei) {
        return expectedDevice;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.DeviceDAO#findBySerialNum(java.lang.String)
     */
    @Override
    public Device findBySerialNum(String serialNum) {
        return expectedDevice;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.DeviceDAO#getDevicesByAcctID(java.lang.Integer)
     */
    @Override
    public List<Device> getDevicesByAcctID(Integer accountID) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.DeviceDAO#getForwardCommands(java.lang.Integer, com.inthinc.pro.model.ForwardCommandStatus)
     */
    @Override
    public List<ForwardCommand> getForwardCommands(Integer deviceID, ForwardCommandStatus status) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.DeviceDAO#queueForwardCommand(java.lang.Integer, com.inthinc.pro.model.ForwardCommand)
     */
    @Override
    public Integer queueForwardCommand(Integer deviceID, ForwardCommand forwardCommand) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.GenericDAO#create(java.lang.Object, java.lang.Object)
     */
    @Override
    public Integer create(Integer id, Device entity) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.GenericDAO#deleteByID(java.lang.Object)
     */
    @Override
    public Integer deleteByID(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.GenericDAO#findByID(java.lang.Object)
     */
    @Override
    public Device findByID(Integer id) {
        return expectedDevice;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.GenericDAO#update(java.lang.Object)
     */
    @Override
    public Integer update(Device entity) {
        // TODO Auto-generated method stub
        return null;
    }

    public Device getExpectedDevice() {
        return expectedDevice;
    }

    public void setExpectedDevice(Device expectedDevice) {
        this.expectedDevice = expectedDevice;
    }

}
