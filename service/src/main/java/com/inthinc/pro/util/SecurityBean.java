package com.inthinc.pro.util;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;

public class SecurityBean {

	private UserDAO userDAO;
	private GroupDAO groupDAO;
	private VehicleDAO vehicleDAO;
	private DeviceDAO deviceDAO;
	private DriverDAO driverDAO;
	private PersonDAO personDAO;

	private String getUserName()
	{
		//TODO get from logged in user
		return "speedracer";
	}
		
	private Group getUserGroup()
	{
		//TODO get Group from logged in user
        User user = userDAO.findByUserName(getUserName());
        Group usergroup = groupDAO.findByID(user.getGroupID());
        return usergroup;
	}
	
	public Integer getGroupID()
	{
		return getUserGroup().getGroupID();
	}
	
	public Integer getAccountID()
	{
		return getUserGroup().getAccountID();
	}
	
	public boolean isAuthorized(Vehicle vehicle)
	{
		if (vehicle!=null)
		{
			//TODO do we give user access to all groups, regardless of the users group????
	        //TODO if so, we need a fast security check to verify a group intersects with user's groups
			//TODO get Account from logged in user
	        Group vehiclegroup = groupDAO.findByID(vehicle.getGroupID());
	        
	        if (vehiclegroup!=null && getAccountID().equals(vehiclegroup.getAccountID()))
	        	return true;
	        			
		}
		return false;
	}
	public boolean isAuthorized(Device device)
	{
		if (device!=null)
		{
			//TODO do we give user access to all groups, regardless of the users group????
	        //TODO if so, we need a fast security check to verify a group intersects with user's groups
			//TODO get Account from logged in user
	        
	        if (getAccountID().equals(device.getAccountID()))
	        	return true;
	        			
		}
		return false;
	}
	public boolean isAuthorized(Driver driver)
	{
		if (driver!=null)
		{
			//TODO do we give user access to all groups, regardless of the users group????
	        //TODO if so, we need a fast security check to verify a group intersects with user's groups
			//TODO get Account from logged in user
			Person person = personDAO.findByID(driver.getPersonID());
			if (person==null)
				return false;
			
			if (!person.getAcctID().equals(getAccountID()))
				return false;
			
	        Group drivergroup = groupDAO.findByID(driver.getGroupID());
	        
	        if (drivergroup!=null && getAccountID().equals(drivergroup.getAccountID()))
	        	return true;
	        			
		}
		return false;
	}

	public boolean isAuthorized(User user)
	{
		if (user!=null)
		{
			//TODO do we give user access to all groups, regardless of the users group????
	        //TODO if so, we need a fast security check to verify a group intersects with user's groups
			//TODO get Account from logged in user
			Person person = personDAO.findByID(user.getPersonID());
			if (person==null)
				return false;
			
			if (!person.getAcctID().equals(getAccountID()))
				return false;
			
	        Group usergroup = groupDAO.findByID(user.getGroupID());
	        
	        if (usergroup!=null && getAccountID().equals(usergroup.getAccountID()))
	        	return true;
	        			
		}
		return false;
	}

	public boolean isAuthorized(Person person)
	{
		if (person!=null)
		{
			//TODO do we give user access to all groups, regardless of the users group????
	        //TODO if so, we need a fast security check to verify a group intersects with user's groups
			//TODO get Account from logged in user
	        if (getAccountID().equals(person.getAcctID()))
	        	return true;
	        			
		}
		return false;
	}

	public boolean isAuthorized(Group group)
	{
		if (group!=null)
		{
			//TODO do we give user access to all groups, regardless of the users group????
	        //TODO if so, we need a fast security check to verify a group intersects with user's groups
			//TODO get Account from logged in user
	        
	        if (getAccountID().equals(group.getAccountID()))
	        	return true;
	        			
		}
		return false;
	}

	//TODO this is dangerous because parameters are not strongly typed
	//     It would be easy to send in an id for the wrong entity type
	public boolean isAuthorizedByVehicleID(Integer vehicleID)
	{
		Vehicle vehicle = vehicleDAO.findByID(vehicleID);
		if (vehicle==null)
			return false;
		return isAuthorized(vehicle);
	}

	//TODO this is dangerous because parameters are not strongly typed
	//     It would be easy to send in an id for the wrong entity type
	public boolean isAuthorizedByDriverID(Integer driverID)
	{
		Driver driver = driverDAO.findByID(driverID);
		if (driver==null)
			return false;
		return isAuthorized(driver);
	}
	
	//TODO this is dangerous because parameters are not strongly typed
	//     It would be easy to send in an id for the wrong entity type
	public boolean isAuthorizedByUserID(Integer userID)
	{
		User user = userDAO.findByID(userID);
		if (user==null)
			return false;
		return isAuthorized(user);
	}
	
	//TODO this is dangerous because parameters are not strongly typed
	//     It would be easy to send in an id for the wrong entity type
	public boolean isAuthorizedByPersonID(Integer personID)
	{
		Person person = personDAO.findByID(personID);
		if (person==null)
			return false;
		return isAuthorized(person);
	}
	
	//TODO this is dangerous because parameters are not strongly typed
	//     It would be easy to send in an id for the wrong entity type
	public boolean isAuthorizedByDeviceID(Integer deviceID)
	{
		Device device = deviceDAO.findByID(deviceID);
		if (device==null)
			return false;
		return isAuthorized(device);
	}

	//TODO this is dangerous because parameters are not strongly typed
	//     It would be easy to send in an id for the wrong entity type
	public boolean isAuthorizedByGroupID(Integer groupID)
	{
		Group group = groupDAO.findByID(groupID);
		if (group==null)
			return false;
		return isAuthorized(group);
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
	public VehicleDAO getVehicleDAO() {
		return vehicleDAO;
	}

	public void setVehicleDAO(VehicleDAO vehicleDAO) {
		this.vehicleDAO = vehicleDAO;
	}

	public DeviceDAO getDeviceDAO() {
		return deviceDAO;
	}

	public void setDeviceDAO(DeviceDAO deviceDAO) {
		this.deviceDAO = deviceDAO;
	}

	public DriverDAO getDriverDAO() {
		return driverDAO;
	}

	public void setDriverDAO(DriverDAO driverDAO) {
		this.driverDAO = driverDAO;
	}

	public PersonDAO getPersonDAO() {
		return personDAO;
	}

	public void setPersonDAO(PersonDAO personDAO) {
		this.personDAO = personDAO;
	}

}
