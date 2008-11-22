package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.map.MapIcon;
import com.inthinc.pro.map.MapIconFactory;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.LatLng;

public class DriverLocationBean extends BaseBean {

	private DriverDAO driverDAO;
    private NavigationBean navigation;
    private boolean pageChange = false;
    private LatLng center;
	private List<DriverBean> driverBeans;
	private List<Group> childGroups;
	private Legend legend;
	
	public DriverLocationBean() {
		super();

		center = new LatLng(37.4419, -122.1419);
		legend = new Legend();
	}

	public List<DriverBean> getDriverBeans() {

        int index = 0;
        driverBeans = new ArrayList<DriverBean>();
        
        childGroups = getGroupHierarchy().getChildren(getGroupHierarchy().getGroup(this.navigation.getGroupID()));
        if (childGroups != null){
        	
         	MapIconFactory mif = new MapIconFactory();
         	List<MapIcon> mapIcons = mif.makeMapIcons(childGroups.size());
         	Iterator<MapIcon> mapIconIt = mapIcons.iterator();
         	
	        for (Group group:childGroups){
	        		         	
	         	legend.addIcon(group.getGroupID(), mapIconIt.next().getUrl());
		        List<Driver> drivers = driverDAO.getAllDrivers(group.getGroupID());
		        // Do something to get driverBeans or last trips to get location
		        for (Driver driver:drivers){
		
		        	DriverBean db = new DriverBean();
		        	db.setLastLocation(new LatLng(center.getLat()+Math.random()/10, center.getLng()+Math.random()/10));
		        	
		        	NavigationBean nb = new NavigationBean();
		        	nb.setDriver(driver);
		        	nb.setGroupID(group.getGroupID());
		        	db.setNavigation(nb);
		        	driverBeans.add(db);
		        }
		        index++;
	        }
        }
        else {
        	
	        List<Driver> drivers = driverDAO.getAllDrivers(this.navigation.getGroupID());

         	MapIconFactory mif = new MapIconFactory();
         	List<MapIcon> mapIcons = mif.makeMapIcons(1);
	        // Do something to get driverBeans or last trips to get location
         	legend.addIcon(navigation.getGroupID(), mapIcons.get(0).getUrl());
	        for (Driver driver:drivers){
	
	        	DriverBean db = new DriverBean();
	        	db.setLastLocation(new LatLng(center.getLat()+Math.random()/10, center.getLng()+Math.random()/10));
	        	
	        	NavigationBean nb = new NavigationBean();
	        	nb.setDriver(driver);
	        	nb.setGroupID(navigation.getGroupID());
	        	db.setNavigation(nb);
	        	driverBeans.add(db);
		        index++;
	        }
      	
        }
			return driverBeans;
	}

	public DriverDAO getDriverDAO() {
		return driverDAO;
	}

	public void setDriverDAO(DriverDAO driverDAO) {
		this.driverDAO = driverDAO;
	}

	public NavigationBean getNavigation() {
		return navigation;
	}

	public void setNavigation(NavigationBean navigation) {
		this.navigation = navigation;
	}

	public LatLng getCenter() {
		return center;
	}

	public void setCenter(LatLng center) {
		this.center = center;
	}

	public void setDriverBeans(List<DriverBean> driverBeans) {
		this.driverBeans = driverBeans;
	}

	public List<Group> getChildGroups() {
		return childGroups;
	}

	public void setChildGroups(List<Group> childGroups) {
		this.childGroups = childGroups;
	}

	public Legend getLegend() {
		return legend;
	}

	public void setLegend(Legend legend) {
		this.legend = legend;
	}
}
