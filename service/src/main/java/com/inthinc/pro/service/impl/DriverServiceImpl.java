package com.inthinc.pro.service.impl;

import java.util.List;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.service.DriverService;
import com.inthinc.pro.util.SecurityBean;

public class DriverServiceImpl implements DriverService{
	
	private DriverDAO driverDAO;
	private SecurityBean securityBean;
	

	public List<Driver> getAll() {
        return driverDAO.getAllDrivers(securityBean.getGroupID());		
	}
	
	public Driver get(Integer driverID)
	{
        Driver driver = driverDAO.findByID(driverID);
		
        if (securityBean.isAuthorized(driver))
			return driver;
        
		return null;
	}

	public Integer add(Driver driver)
	{
		if (!securityBean.isAuthorized(driver))
			return driverDAO.create(driver.getPersonID(), driver);
	
		return -1;
	}

	public Integer update(Driver driver)
	{		
		if (securityBean.isAuthorized(driver))
			return driverDAO.update(driver);
		
		return -1;
	}

	public Integer delete(Integer driverID)
	{
		if(securityBean.isAuthorizedByDriverID(driverID))
			return driverDAO.deleteByID(driverID);
		
		return -1;
	}
	
	public void setDriverDAO(DriverDAO driverDAO) {
		this.driverDAO = driverDAO;
	}

	public DriverDAO getDriverDAO() {
		return driverDAO;
	}
	
	public SecurityBean getSecurityBean() {
		return securityBean;
	}

	public void setSecurityBean(SecurityBean securityBean) {
		this.securityBean = securityBean;
	}
}
