package com.inthinc.pro.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.service.DriverService;


public class DriverServiceImpl implements DriverService{
	
	private DriverDAO driverDAO;
	private UserDAO userDAO;
	private GroupDAO groupDAO;
	private String userName="speedracer";
	

	public List<Driver> getDrivers() {
		List<Driver> driverList = new ArrayList<Driver>();
		if(userName != null)
        {
            User user = userDAO.findByUserName(userName);
            if(user != null)
            {
            	driverList = driverDAO.getAllDrivers(user.getGroupID());
            }
        }
		
		return driverList;
	}
	
	public Driver getDriver(Integer driverID)
	{
		//TODO username for group security but get from logged in user
        User user = userDAO.findByUserName("speedracer");
        
        Driver driver = driverDAO.findByID(driverID);        
        //TODO we need a fast security check to verify a group intersects with user's groups
        Group group = groupDAO.findByID(driver.getGroupID());
        
		return driver;
	}

	public void setDriverDAO(DriverDAO driverDAO) {
		this.driverDAO = driverDAO;
	}

	public DriverDAO getDriverDAO() {
		return driverDAO;
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
