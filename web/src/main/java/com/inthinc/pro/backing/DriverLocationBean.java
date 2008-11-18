package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.LatLng;

public class DriverLocationBean extends BaseBean {

	private DriverDAO driverDAO;
    private NavigationBean navigation;
    private boolean pageChange = false;
    private LatLng center;
	private List<DriverBean> driverBeans;

	
	public DriverLocationBean() {
		super();

		center = new LatLng(37.4419, -122.1419);
	}

	public List<DriverBean> getDriverBeans() {

        List<Driver> drivers = driverDAO.getAllDrivers(this.navigation.getGroupID());
        // Do something to get driverBeans or last trips to get location
        driverBeans = new ArrayList<DriverBean>();
        for (Driver driver:drivers){

        	DriverBean db = new DriverBean();
        	db.setNavigation(navigation);
        	db.getNavigation().setDriver(driver);
        	db.setLastLocation(new LatLng(center.getLat()+Math.random()/10, center.getLng()+Math.random()/10));
        	driverBeans.add(db);
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
}
