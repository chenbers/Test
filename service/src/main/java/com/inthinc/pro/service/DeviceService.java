package com.inthinc.pro.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.User;

@Path("/deviceService")
public class DeviceService {
	
	private DeviceDAO deviceDAO;
	private UserDAO userDAO;
	private GroupDAO groupDAO;

	@GET
	@Path("/devices/{userName}")
	public String getDevices(@PathParam("userName")String userName) {
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
		
		StringBuilder sb = new StringBuilder();
		for(Device device:deviceList){
			sb.append(device.getName());
			sb.append(",");
			sb.append(device.getImei());
			sb.append(",");
			sb.append(device.getSim());
			if(device.getVehicleID() != null){
				sb.append(",");
				sb.append(device.getVehicleID());
			}
			if(deviceList.indexOf(device) < deviceList.size() -1)
				sb.append(";");
		}
		return sb.toString();
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
