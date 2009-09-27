package com.inthinc.pro.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.service.VehicleService;


public class VehicleServiceImpl implements VehicleService{
	
	private VehicleDAO vehicleDAO;
	private UserDAO userDAO;
	private GroupDAO groupDAO;
	private String userName="speedracer";
	

	public List<Vehicle> getVehicles() {
		List<Vehicle> vehicleList = new ArrayList<Vehicle>();
		if(userName != null)
        {
            User user = userDAO.findByUserName(userName);
            if(user != null)
            {
            	vehicleList = vehicleDAO.getVehiclesInGroupHierarchy(user.getGroupID());
            }
        }
		
		return vehicleList;
	}
	
	public Vehicle getVehicle(Integer vehicleID)
	{
		//TODO username for group security but get from logged in user
        User user = userDAO.findByUserName("speedracer");
        Group usergroup = groupDAO.findByID(user.getGroupID());
        
        Vehicle vehicle = vehicleDAO.findByID(vehicleID);        
        //TODO we need a fast security check to verify a group intersects with user's groups
        Group group = groupDAO.findByID(vehicle.getGroupID());
        
        if (!usergroup.getAccountID().equals(group.getAccountID()))
        	return null;
        
		return vehicle;
	}
	public Integer addVehicle(Vehicle vehicle)
	{
		return vehicleDAO.create(vehicle.getGroupID(), vehicle);
	}

	public void setVehicleDAO(VehicleDAO vehicleDAO) {
		this.vehicleDAO = vehicleDAO;
	}

	public VehicleDAO getVehicleDAO() {
		return vehicleDAO;
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
