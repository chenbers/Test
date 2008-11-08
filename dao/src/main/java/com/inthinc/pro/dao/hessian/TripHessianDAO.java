package com.inthinc.pro.dao.hessian;

import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.TripDAO;
import com.inthinc.pro.dao.hessian.proserver.CentralService;
import com.inthinc.pro.model.Trip;

public class TripHessianDAO extends GenericHessianDAO<Trip, Integer, CentralService> implements TripDAO
{
    private static final Logger logger = Logger.getLogger(TripHessianDAO.class);
    
    @Override
    public List<Trip> getTrips(Integer driverID, Integer startDate, Integer endDate)
    {
        logger.debug("getTrips() driverID = " + driverID);
        List<Trip> tripList = convertToModelObject(this.getSiloService().getTrips(driverID, startDate, endDate));
        return tripList;
    }
}
