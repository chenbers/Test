package com.inthinc.pro.dao.hessian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.Interval;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.LocationDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.DriverName;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.DriverStops;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.Vehicle;

public class DriverHessianDAO extends GenericHessianDAO<Driver, Integer> implements DriverDAO
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(DriverHessianDAO.class);
    private static final Integer DRIVER_TYPE = 1;

    private static final String BARCODE_KEY = "barcode";
    
    private VehicleDAO vehicleDAO;
    private LocationDAO locationDAO;
    

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
                if(groupID.equals(driver.getGroupID()))
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
    public List<DriverName> getDriverNames(Integer groupID)
    {
        
        try
        {
            List<DriverName> driverList = getMapper().convertToModelObject(this.getSiloService().getDriverNamesByGroupIDDeep(groupID), DriverName.class);
            return driverList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

    @Override
    public LastLocation getLastLocation(Integer driverID)
    {
    	return locationDAO.getLastLocationForDriver(driverID);
    }

    @Override
    public Trip getLastTrip(Integer driverID)
    {
/*    	
        try
        {
            return getMapper().convertToModelObject(this.getSiloService().getLastTrip(driverID, DRIVER_TYPE), Trip.class);
        }
        catch (EmptyResultSetException e)
        {
            return null;
        }
*/
    	return locationDAO.getLastTripForDriver(driverID);
    }

    @Override
    public List<Trip> getTrips(Integer driverID, Date startDate, Date endDate)
    {
        logger.debug("getTrips() driverID = " + driverID);
/*        try
        {
            List<Trip> tripList = getMapper().convertToModelObject(this.getSiloService().getTrips(driverID, DRIVER_TYPE, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(endDate)), Trip.class);
            return tripList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
*/
    	return locationDAO.getTripsForDriver(driverID, startDate, endDate);
        
    }

    @Override
    public List<Trip> getTrips(Integer driverID, Interval interval) {
        return getTrips(driverID, interval.getStart().toDateTime().toDate(), interval.getEnd().toDateTime().toDate());
    }

    @Override
    public Driver findByPersonID(Integer personID)
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

    @Override
	public List<Long> getRfidsByBarcode(String barcode) {

    	try
    	{
    		List<Long> rfids = getSiloService().getRfidsForBarcode(barcode);
    		return rfids;
    	}
    	catch(EmptyResultSetException e)
        {
            return null;
        }
    }

	@Override
    public Integer getDriverIDByBarcode(String barcode)
    {
        try
        {
            Map<String, Object> returnMap = getSiloService().getID(BARCODE_KEY, barcode);
            return getCentralId(returnMap);
        }
        catch (EmptyResultSetException e)
        {
            return null;
        }
    }

	@Override
	public List<DriverLocation> getDriverLocations(Integer groupID) {		
		return locationDAO.getDriverLocations(groupID);
	}
	
	@Override
    public List<DriverStops> getStops(Integer driverID, String driverName, Interval interval) {
		return locationDAO.getStops(driverID, driverName, interval);
	}

	public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

	public LocationDAO getLocationDAO() {
		return locationDAO;
	}

	public void setLocationDAO(LocationDAO locationDAO) {
		this.locationDAO = locationDAO;
	}
    
}
