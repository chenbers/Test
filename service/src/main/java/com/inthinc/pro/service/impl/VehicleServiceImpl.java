package com.inthinc.pro.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.service.VehicleService;


public class VehicleServiceImpl implements VehicleService{
	
	private VehicleDAO vehicleDAO;
	private UserDAO userDAO;
	private GroupDAO groupDAO;
	
	private String getUserName()
	{
		//TODO get from logged in user
		return "speedracer";
	}
		
	private Group getUserGroup()
	{
		//TODO get Group from logged in user
        User user = userDAO.findByUserName("speedracer");
        Group usergroup = groupDAO.findByID(user.getGroupID());
        return usergroup;
	}
	

	//TODO Assign Driver and Device by explicit call or OVERLOAD add/update??
	
	public List<Vehicle> getAll() {
       	return vehicleDAO.getVehiclesInGroupHierarchy(getUserGroup().getGroupID());
	}
	
	public Vehicle get(Integer vehicleID)
	{
        Vehicle vehicle = vehicleDAO.findByID(vehicleID);        
        if (!isAuthorized(vehicle))
        	return null;
		return vehicle;
	}

	public Vehicle findByVIN(String vin)
	{
        Vehicle vehicle = vehicleDAO.findByVIN(vin);        
        if (!isAuthorized(vehicle))
        	return null;
		return vehicle;
	}

	public Integer add(Vehicle vehicle)
	{
		if (!isAuthorized(vehicle))
			return -1;
		return vehicleDAO.create(vehicle.getGroupID(), vehicle);
	}

	public Integer update(Vehicle vehicle)
	{		
		if (!isAuthorized(vehicle))
			return -1;
		return vehicleDAO.update(vehicle);
	}

	public List<Integer> add(List<Vehicle> vehicles)
	{
		List<Integer> results = new ArrayList<Integer>();
		for(int i=0; i<vehicles.size(); i++)
			results.add(add(vehicles.get(i)));
		return results;
	}

	public List<Integer> update(List<Vehicle> vehicles)
	{
		List<Integer> results = new ArrayList<Integer>();
		for(int i=0; i<vehicles.size(); i++)
			results.add(update(vehicles.get(i)));
		return results;
	}

	public Integer delete(Integer vehicleID)
	{
		if(!isAuthorized(vehicleID))
			return -1;
		return vehicleDAO.deleteByID(vehicleID);
	}

	public List<Integer> delete(List<Integer> vehicleIDs)
	{
		List<Integer> results = new ArrayList<Integer>();
		for(int i=0; i<vehicleIDs.size(); i++)
		{
			results.add(delete(vehicleIDs.get(i)));
		}
		return results;
	}
	
	public LastLocation getLastLocation(Integer vehicleID)
	{
		if(!isAuthorized(vehicleID))
			return null;
		return vehicleDAO.getLastLocation(vehicleID);
	}
	
	//TODO do we implement these?
	public void getTrips(Integer vehicleID) 
	{
//		vehicleDAO.getTrips(vehicleID, startDate, endDate);
//		vehicleDAO.getLastTrip(driverID);
//		vehicleDAO.getVehiclesNearLoc(groupID, numof, lat, lng);
//		vehicleDAO.getVehiclesInGroup(groupID);
		
	}
	
	private boolean isAuthorized(Vehicle vehicle)
	{
		if (vehicle!=null)
		{
			//TODO do we give user access to all groups, regardless of the users group????
	        //TODO if so, we need a fast security check to verify a group intersects with user's groups
			//TODO get Account from logged in user
			Group usergroup = this.getUserGroup();
	        Group group = groupDAO.findByID(vehicle.getGroupID());
	        
	        if (group==null || !usergroup.getAccountID().equals(group.getAccountID()))
	        	return false;
	        			
		}
		return false;
	}
	
	private boolean isAuthorized(Integer vehicleID)
	{
		Vehicle vehicle = vehicleDAO.findByID(vehicleID);
		if (vehicle==null)
			return false;
		return isAuthorized(vehicle);
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
