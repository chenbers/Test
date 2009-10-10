package com.inthinc.pro.util;

import java.util.List;

import org.jboss.resteasy.spi.NotFoundException;
import org.jboss.resteasy.spi.UnauthorizedException;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;

public class SecureDriverDAO extends BaseSecureDAO{

    private DriverDAO driverDAO;
	private SecureGroupDAO groupDAO;
    private SecurePersonDAO personDAO;

    private boolean isAuthorized(Driver driver) {
        if (driver != null) {
            // TODO do we give user access to all groups, regardless of the users group????
            // TODO if so, we need a fast security check to verify a group intersects with user's groups

        	if (!groupDAO.isAuthorized(driver.getGroupID()))
        		return false;

           	if (!personDAO.isAuthorized(driver.getPersonID()))
            		return false;
            return true;
        }
        throw new UnauthorizedException("Driver not found");
    }

    public boolean isAuthorized(Integer driverID) {
        return isAuthorized(findByID(driverID));
    }

    public Driver findByID(Integer driverID) {
    	
        Driver driver = driverDAO.findByID(driverID);
        if (driver == null)
            throw new NotFoundException("driverID not found: " + driverID);
        try 
        {
            if (isAuthorized(driver))
            	return driver;
        	
        }
        catch (Exception ex)
        {
            throw new NotFoundException("driverID not found: " + driverID);
        }
        return null;
    }
    
    public Integer create(Driver driver) {
    	if (isAuthorized(driver))
    		return driverDAO.create(driver.getPersonID(), driver);
    	return -1;
    }

    public Integer update(Driver driver) {
    	if (isAuthorized(driver))
    		return driverDAO.update(driver);
    	return -1;
    }

    public Integer deleteByID(Integer driverID) {
    	if (isAuthorized(driverID))
    		return driverDAO.deleteByID(driverID);
    	return -1;
    }


    public List<Driver> getAll() {
        return driverDAO.getAllDrivers(getGroupID());
    }

    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    public SecureGroupDAO getGroupDAO() {
		return groupDAO;
	}

	public void setGroupDAO(SecureGroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}

	public SecurePersonDAO getPersonDAO() {
		return personDAO;
	}

	public void setPersonDAO(SecurePersonDAO personDAO) {
		this.personDAO = personDAO;
	}
}
