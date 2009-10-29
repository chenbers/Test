package com.inthinc.pro.util;

import java.util.List;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;

public class SecureDriverDAO extends SecureDAO<Driver> {

    private DriverDAO driverDAO;
    private SecureGroupDAO groupDAO;
    private SecurePersonDAO personDAO;

    @Override
    public boolean isAuthorized(Driver driver) {
        if (driver != null) {
            // TODO do we give user access to all groups, regardless of the users group????
            // TODO if so, we need a fast security check to verify a group intersects with user's groups

            if (!groupDAO.isAuthorized(driver.getGroupID()))
                return false;

            if (!personDAO.isAuthorized(driver.getPersonID()))
                return false;
            return true;
        }
        return false;
    }

    public boolean isAuthorized(Integer driverID) {
        return isAuthorized(findByID(driverID));
    }

    @Override
    public Driver findByID(Integer driverID) {

        Driver driver = driverDAO.findByID(driverID);
        if (isAuthorized(driver))
            return driver;
        return null;
    }

    @Override
    public Integer create(Driver driver) {
        if (isAuthorized(driver))
            return driverDAO.create(driver.getPersonID(), driver);
        return null;
    }

    @Override
    public Driver update(Driver driver) {
        if (isAuthorized(driver) && driverDAO.update(driver) != 0)
            return driverDAO.findByID(driver.getDriverID());
        return null;
    }

    @Override
    public Integer delete(Integer driverID) {
        if (isAuthorized(driverID))
            return driverDAO.deleteByID(driverID);
        return 0;
    }

    @Override
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
