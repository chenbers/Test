package com.inthinc.pro.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.User;
import com.inthinc.pro.service.DeviceService;


public class DeviceServiceImpl implements DeviceService{
	
	private DeviceDAO deviceDAO;
	private UserDAO userDAO;
	private GroupDAO groupDAO;
	private String userName="speedracer";


	public List<Device> getDevices() {
		List<Device> deviceList = new ArrayList<Device>();
		if(userName != null)
        {
            User user = userDAO.findByUserName(userName);
            if(user != null)
            {
                Group group = groupDAO.findByID(user.getGroupID());
                deviceList = deviceDAO.getDevicesByAcctID(group.getAccountID());
            }
        }
		return deviceList;
	}
	
	public Device getDevice(Integer deviceID)
	{
		//TODO username for group security but get from logged in user
        User user = userDAO.findByUserName("speedracer");
        Group group = groupDAO.findByID(user.getGroupID());

        //TODO Security!!! Limit to users account? 
		//TODO Group is tough unless we hop to vehicle but that won't work for unassigned.
		Device device = deviceDAO.findByID(deviceID);
		if (device!=null && group.getAccountID().equals(device.getAccountID()))
		{
			return device;
		}
		return null;
	}

	public void setDeviceDAO(DeviceDAO deviceDAO) {
		this.deviceDAO = deviceDAO;
	}

	public DeviceDAO getDeviceDAO() {
		return deviceDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setGroupDAO(GroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}

	public GroupDAO getGroupDAO() {
		return groupDAO;
	}

}
