package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.LatLng;

public class LiveFleetBean extends BaseBean 
{
	private NavigationBean navigation;
	private GroupDAO groupDAO;
	private DriverDAO driverDAO;
	private static final Logger logger = Logger.getLogger(LiveFleetBean.class);
	
	private LatLng addressLatLng = new LatLng();
	private Double maxCount = 100.00D;
	private Double maxRadius = 300.00D;
	
	private List<DriverLastLocationBean> drivers = new ArrayList<DriverLastLocationBean>();

	public List<DriverLastLocationBean> getDrivers() {
		initDrivers();
		logger.debug("completed LastLoc init");
		return drivers;
	}

	public void setDrivers(List<DriverLastLocationBean> drivers) {
		this.drivers = drivers;
	}
	private GroupHierarchy organizationHierarchy;
	
	public void initDrivers()
	{
		organizationHierarchy = new GroupHierarchy(groupDAO.getGroupsByAcctID(getAccountID()));
		Group fleetGroup = organizationHierarchy.getTopGroup();
	    
		List<Driver> tempDrivers = driverDAO.getAllDrivers(fleetGroup.getGroupID()); // Replace with GetAllDriversNear(LatLng)
		
		for(Driver d: tempDrivers)
		{
		    
		    LastLocation last = driverDAO.getLastLocation(d.getDriverID());
		
			DriverLastLocationBean driverLast = new DriverLastLocationBean();
			
			logger.debug(last.getLat() + " " + d.getDriverID());
			
			driverLast.setLastLocation(new LatLng(last.getLat(), last.getLng()) );
			driverLast.setDriver(d);
			driverLast.setDriverName(d.getPerson().getFirst() + " " + d.getPerson().getLast());
			
			drivers.add(driverLast);
			
		}
	}
	
	public GroupDAO getGroupDAO() {
		return groupDAO;
	}
	public void setGroupDAO(GroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}

	public DriverDAO getDriverDAO() {
		return driverDAO;
	}
	public void setDriverDAO(DriverDAO driverDAO) {
		this.driverDAO = driverDAO;
	}

	public Double getMaxRadius() {
		return maxRadius;
	}
	public void setMaxRadius(Double maxRadius) {
		this.maxRadius = maxRadius;
	}
	
	//ADDRESS LATLNG PROPERTIES
	public LatLng getAddressLatLng() {
		return addressLatLng;
	}
	public void setAddressLatLng(LatLng addressLatLng) {
		this.addressLatLng = addressLatLng;
	}
	
	//NAVIGATION PROPERTIES
	public NavigationBean getNavigation() {
		return navigation;
	}
	public void setNavigation(NavigationBean navigation) {
		this.navigation = navigation;
	}
}
