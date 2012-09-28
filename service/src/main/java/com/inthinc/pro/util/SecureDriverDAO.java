package com.inthinc.pro.util;

import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.report.DriverReportDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.model.aggregation.Trend;
import com.inthinc.pro.model.event.Event;

public class SecureDriverDAO extends SecureDAO<Driver> {

    private DriverDAO driverDAO;
    private DriverReportDAO driverReportDAO;
    private EventDAO eventDAO;
    private SecureGroupDAO groupDAO;
    private SecurePersonDAO personDAO;

    @Override
    public boolean isAuthorized(Driver driver) {
        if (driver != null) {
            // TODO do we give user access to all groups, regardless of the users group????
            // TODO if so, we need a fast security check to verify a group intersects with user's groups
            if (isInthincUser() || (groupDAO.isAuthorized(driver.getGroupID()) && personDAO.isAuthorized(driver.getPersonID())))
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

    public List<Event> getSpeedingEvents(Integer driverID) {
        if (isAuthorized(driverID)) {
            DateTime dateTime = new DateTime();
            return eventDAO.getViolationEventsForDriver(driverID, dateTime.minusMonths(1).toDate(), dateTime.toDate(), 1);
        }
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
    
    public Score getScore(Integer driverID, Duration duration) {
        if (isAuthorized(driverID))
            return driverReportDAO.getScore(driverID, duration);
        return null;
    }

    public List<Trend> getTrend(Integer driverID, Duration duration) {
        if (isAuthorized(driverID))
            return driverReportDAO.getTrend(driverID, duration);
        return Collections.emptyList();
    }
    

    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    public EventDAO getEventDAO() {
        return eventDAO;
    }

    public void setEventDAO(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
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

    public DriverReportDAO getDriverReportDAO() {
        return driverReportDAO;
    }

    public void setDriverReportDAO(DriverReportDAO driverReportDAO) {
        this.driverReportDAO = driverReportDAO;
    }
}
