package it.com.inthinc.pro.dao.model;

import java.util.List;

import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;

public class GroupListData {

	public Integer driverType;
	public Group group;
	public User user;
	public List<Device> deviceList;
	public List<Vehicle> vehicleList;
	public List<Driver> driverList;
}
