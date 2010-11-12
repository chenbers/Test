/**
 * 
 */
package com.inthinc.pro.service.adapters;

import java.util.List;

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

}
