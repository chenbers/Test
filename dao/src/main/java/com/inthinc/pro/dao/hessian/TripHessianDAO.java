package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.TripDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Trip;

public class TripHessianDAO extends GenericHessianDAO<Trip, Integer> implements TripDAO
{
    private static final Logger logger = Logger.getLogger(TripHessianDAO.class);
    
    @Override
    public List<Trip> getTrips(Integer driverID, Date startDate, Date endDate)
    {
        logger.debug("getTrips() driverID = " + driverID);
        try
        {
            List<Trip> tripList = getMapper().convertToModelObject(this.getSiloService().getTrips(driverID, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(endDate)), Trip.class);
            return tripList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }
    
    @Override
    public Trip getLastTrip(Integer driverID)
    {
        try
        {
            return getMapper().convertToModelObject(this.getSiloService().getLastTrip(driverID), Trip.class);
        }
        catch (EmptyResultSetException e)
        {
            return null;
        }
    }
}
