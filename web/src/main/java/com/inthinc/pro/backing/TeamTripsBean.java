package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.map.MapIcon;
import com.inthinc.pro.map.MapIconFactory;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Trip;

public class TeamTripsBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Driver> drivers;
	private List<Trip> selectedDriverTrips;
	private Driver selectedDriver;
    private Integer groupID;
    private Group group;
    private DriverDAO driverDAO;
    private List<MapIcon> icons;
	
	public List<Driver> getDrivers() {
		
		if (drivers == null){
			
			drivers = driverDAO.getDrivers(getGroupID());
			icons = MapIconFactory.IconType.TEAM_LEGEND.getIconList(15);
		}
		return drivers;
	}
	public void setDrivers(List<Driver> drivers) {
		this.drivers = drivers;
	}
	public List<Trip> getSelectedDriverTrips() {
		selectedDriverTrips = new ArrayList<Trip>();
		return selectedDriverTrips;
	}
	public void setSelectedDriverTrips(List<Trip> selectedDriverTrips) {
		this.selectedDriverTrips = selectedDriverTrips;
	}
	public Driver getSelectedDriver() {
		return selectedDriver;
	}
	public void setSelectedDriver(Driver selectedDriver) {
		this.selectedDriver = selectedDriver;
	}
	public Integer getGroupID() {
		return groupID;
	}
	public void setGroupID(Integer groupID) {

		if(groupID!= null){
			
		    group = getGroupHierarchy().getGroup(groupID);
		}
		this.groupID = groupID;
		    
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public DriverDAO getDriverDAO() {
		return driverDAO;
	}
	public void setDriverDAO(DriverDAO driverDAO) {
		this.driverDAO = driverDAO;
	}
	public List<MapIcon> getIcons() {
		return icons;
	}
	public void setIcons(List<MapIcon> icons) {
		this.icons = icons;
	}

}
