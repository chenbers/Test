package com.inthinc.pro.service.adapters;

import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.model.Device;

/**
 * Adapter for the Device resources.
 * 
 * @author dcueva
 *
 */
@Component
public class DeviceDAOAdapter extends BaseDAOAdapter<Device> {

    @Autowired
    private DeviceDAO deviceDAO;	
	
	@Override
	public List<Device> getAll() {
		return deviceDAO.getDevicesByAcctID(getAccountID());
	}

	@Override
	protected GenericDAO<Device, Integer> getDAO() {
		return deviceDAO;
	}

	@Override
	protected Integer getResourceID(Device device) {
		return device.getDeviceID();
	}

	/**
	 * @see com.inthinc.pro.service.adapters.BaseDAOAdapter#update(java.lang.Object)
	 */
	@Override
	public Device update(Device device) {
        throw new NotImplementedException();
	}

	/**
	 * @see com.inthinc.pro.service.adapters.BaseDAOAdapter#delete(java.lang.Integer)
	 */
	@Override
	public Integer delete(Integer id) {
        throw new NotImplementedException();
	}

	// Specialized methods ----------------------------------------------------
	
    public Device findByIMEI(String imei) {
        return deviceDAO.findByIMEI(imei);
    }	

    public Device findBySerialNum(String serialNum) {
        return deviceDAO.findBySerialNum(serialNum);
    }

	// Getters and setters -------------------------------------------------
    
    /**
	 * @return the deviceDAO
	 */
	public DeviceDAO getDeviceDAO() {
		return deviceDAO;
	}

	/**
	 * @param deviceDAO the deviceDAO to set
	 */
	public void setDeviceDAO(DeviceDAO deviceDAO) {
		this.deviceDAO = deviceDAO;
	}  
    
    
}
