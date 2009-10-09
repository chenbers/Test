package com.inthinc.pro.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.service.DriverService;
import com.inthinc.pro.util.SecureDriverDAO;

public class DriverServiceImpl implements DriverService {

    private SecureDriverDAO driverDAO;

    public List<Driver> getAll() {
        return driverDAO.getAll();
    }

    public Driver get(Integer driverID) {
        return driverDAO.findByID(driverID);
    }

    public Integer create(Driver driver) {
        return driverDAO.create(driver);
    }

    public Integer update(Driver driver) {
        return driverDAO.update(driver);
    }

    public Integer delete(Integer driverID) {
        return driverDAO.deleteByID(driverID);
    }

    public List<Integer> create(List<Driver> drivers) {
        List<Integer> results = new ArrayList<Integer>();
        for (Driver driver : drivers)
            results.add(create(driver));
        return results;
    }

    public List<Integer> update(List<Driver> drivers) {
        List<Integer> results = new ArrayList<Integer>();
        for (Driver driver : drivers)
            results.add(update(driver));
        return results;
    }

    public List<Integer> delete(List<Integer> driverIDs) {
        List<Integer> results = new ArrayList<Integer>();
        for (Integer id : driverIDs) {
            results.add(delete(id));
        }
        return results;
    }

    public void setDriverDAO(SecureDriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    public SecureDriverDAO getDriverDAO() {
        return driverDAO;
    }

}
