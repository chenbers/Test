/**
 * 
 */
package com.inthinc.pro.service.adapters;

import java.util.List;

import org.apache.commons.lang.NotImplementedException;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.model.Device;

/**
 * Adapter for the Device resources.
 * 
 * @author dcueva
 *
 */
public class DeviceDAOAdapter extends BaseDAOAdapter<Device> {

    private DeviceDAO deviceDAO;	
	
	@Override
	public List<Device> getAll() {
		// TODO Auto-generated method stub
		return null;
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

	/* (non-Javadoc)
	 * @see com.inthinc.pro.service.adapters.BaseDAOAdapter#delete(java.lang.Integer)
	 */
	@Override
	public Integer delete(Integer id) {
        throw new NotImplementedException();
	}

}
