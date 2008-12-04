package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.LastLocation;

public interface DriverDAO extends GenericDAO<Driver, Integer>
{
    List<Driver> getAllDrivers(Integer groupID);
    
    LastLocation getLastLocation(Integer driverID);
    
}
