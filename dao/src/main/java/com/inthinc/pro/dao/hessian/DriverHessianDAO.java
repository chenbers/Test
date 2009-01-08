package com.inthinc.pro.dao.hessian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.Trip;

public class DriverHessianDAO extends GenericHessianDAO<Driver, Integer> implements DriverDAO
{
    private static final Logger logger = Logger.getLogger(DriverHessianDAO.class);
    private static final Integer DRIVER_TYPE = 1;

    @Override
    public List<Driver> getAllDrivers(Integer groupID)
    {
        
        try
        {
            List<Driver> driverList = getMapper().convertToModelObject(this.getSiloService().getDriversByGroupIDDeep(groupID), Driver.class);
            return driverList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }

    }
    
    @Override
    public List<Driver> getDrivers(Integer groupID)
    {
        List<Driver> driverList = new ArrayList<Driver>();
        try
        {
            List<Driver> completeSet = getMapper().convertToModelObject(this.getSiloService().getDriversByGroupID(groupID), Driver.class);
            for(Driver driver: completeSet)
            {
                if(driver.getGroupID()==groupID)
                {
                    driverList.add(driver);
                }
            }
        }
        catch (EmptyResultSetException e)
        {
            driverList = Collections.emptyList();
        }
        
        return driverList;
    }

    @Override
    public LastLocation getLastLocation(Integer driverID)
    {
        try
        {
            return getMapper().convertToModelObject(this.getSiloService().getLastLoc(driverID, DRIVER_TYPE), LastLocation.class);
        }
        catch (EmptyResultSetException e)
        {
            return null;
        }
    }

    @Override
    public List<DriverLocation> getDriversNearLoc(Integer groupID, Integer numof, Double lat, Double lng)
    {
        try
        {
            return getMapper().convertToModelObject(this.getSiloService().getDriversNearLoc(groupID, numof, lat, lng), DriverLocation.class);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
        // TODO: Remove when method is impl on back end
        catch (ProxyException ex)
        {
            if (ex.getErrorCode() == 422)
            {
                return Collections.emptyList();
            }
            throw ex;
        }
    }

    @Override
    public Trip getLastTrip(Integer driverID)
    {
        try
        {
            return getMapper().convertToModelObject(this.getSiloService().getLastTrip(driverID, DRIVER_TYPE), Trip.class);
        }
        catch (EmptyResultSetException e)
        {
            return null;
        }
        // TODO: Remove when method is impl on back end
        catch (ProxyException ex)
        {
            if (ex.getErrorCode() == 422)
            {
                return null;
            }
            throw ex;
        }
    }

    @Override
    public List<Trip> getTrips(Integer driverID, Date startDate, Date endDate)
    {
        logger.debug("getTrips() driverID = " + driverID);
        try
        {
            List<Trip> tripList = getMapper().convertToModelObject(this.getSiloService().getTrips(driverID, DRIVER_TYPE, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(endDate)), Trip.class);
            return tripList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

    @Override
    public Driver getDriverByPersonID(Integer personID)
    {
        try
        {
            return getMapper().convertToModelObject(this.getSiloService().getDriverByPersonID(personID), Driver.class);
        }
        catch (EmptyResultSetException e)
        {
            return null;
        }
    }


}
