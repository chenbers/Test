package com.inthinc.pro.dao.hessian;

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
import com.inthinc.pro.dao.hessian.mapper.Mapper;
import com.inthinc.pro.dao.hessian.mapper.SimpleMapper;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.DriverStops;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.Vehicle;

public class LocationHessianDAO   implements LocationDAO
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(LocationHessianDAO.class);
    private static final Integer DRIVER_TYPE = 1;
    private static final Integer VEHICLE_TYPE = 2;
    private SiloService siloService;
    private Mapper mapper;
    private VehicleDAO vehicleDAO;
    private DriverDAO driverDAO;
    
    
    public static void main(String[] args){
        SiloServiceCreator ssc = new SiloServiceCreator("tp-web10.tiwipro.com", 8099);
        LocationHessianDAO locationDAO = new LocationHessianDAO();
        locationDAO.setSiloService(ssc.getService());

        Date startDate = new Date((1344384000L-14400)*1000L);
        Date endDate = new Date((1344470400L-14400)*1000L);
        List<Trip> tripList = locationDAO.getTripsForVehicle(11290, startDate, endDate);
        for (Trip trip : tripList)
        {
            System.out.println("Vehicle Trip: " + trip);
        }
        
        tripList = locationDAO.getTripsForDriver(20187, startDate, endDate);
        for (Trip trip : tripList)
        {
            System.out.println("Driver Trip: " + trip);
        }
        
        
    }
    
    public LocationHessianDAO()
    {
        mapper = new SimpleMapper();
    }

    public SiloService getSiloService() {
        return siloService;
    }

    public void setSiloService(SiloService siloService) {
        this.siloService = siloService;
    }
    
	@Override
	public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }

	@Override
    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

	@Override
	public DriverDAO getDriverDAO() {
		return driverDAO;
	}

	@Override
	public void setDriverDAO(DriverDAO driverDAO) {
		this.driverDAO = driverDAO;
	}

	@Override
    public LastLocation getLastLocationForDriver(Integer driverID)
    {
        try
        {
            return mapper.convertToModelObject(this.getSiloService().getLastLoc(driverID, DRIVER_TYPE), LastLocation.class);
        }
        catch (EmptyResultSetException e)
        {
            return null;
        }
    }
    
    @Override
    public LastLocation getLastLocationForVehicle(Integer vehicleID) {
        try {
            return mapper.convertToModelObject(this.getSiloService().getLastLoc(vehicleID, VEHICLE_TYPE), LastLocation.class);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }



	@Override
	public List<DriverLocation> getDriverLocations(Integer groupID) {		
        try {
            return mapper.convertToModelObject(this.getSiloService().getDVLByGroupIDDeep(groupID), DriverLocation.class);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
	}
	
    @Override
    public List<Trip> getTripsForDriver(Integer driverID, Date startDate, Date endDate)
    {
        logger.debug("getTrips() driverID = " + driverID);
        try
        {
            List<Trip> tripList = mapper.convertToModelObject(this.getSiloService().getTrips(driverID, DRIVER_TYPE, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(endDate)), Trip.class);
            return tripList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Trip> getTripsForVehicle(Integer vehicleID, Date startDate, Date endDate)
    {
        logger.debug("getTrips() vehicleID = " + vehicleID);
        try
        {
            List<Trip> tripList = mapper.convertToModelObject(this.getSiloService().getTrips(vehicleID, VEHICLE_TYPE, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(endDate)), Trip.class);
            return tripList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }
    
    @Override
    public Trip getLastTripForDriver(Integer driverID)
    {
        try
        {
            return mapper.convertToModelObject(this.getSiloService().getLastTrip(driverID, DRIVER_TYPE), Trip.class);
        }
        catch (EmptyResultSetException e)
        {
            return null;
        }
    }

    @Override
    public Trip getLastTripForVehicle(Integer vehicleID)
    {
        try
        {
            return mapper.convertToModelObject(this.getSiloService().getLastTrip(vehicleID, VEHICLE_TYPE), Trip.class);
        }
        catch (EmptyResultSetException e)
        {
            return null;
        }
    }
    
	@Override
    public List<DriverStops> getStops(Integer driverID, String driverName, Interval interval) {
        try {
            Date start = interval.getStart().toDateTime().toDate();
            Date end   = interval.getEnd().toDateTime().toDate();
            List<DriverStops> driverStopsList = mapper.convertToModelObject(this.getSiloService().getStops(
                    driverID,
                    DateUtil.convertDateToSeconds(start),
                    DateUtil.convertDateToSeconds(end)), 
                    DriverStops.class);
            
            
            Map<Integer, String> vehicleMap = new HashMap<Integer, String>();
            for (DriverStops driverStop : driverStopsList) {
                driverStop.setDriverName(driverName);
                String vehicleName = vehicleMap.get(driverStop.getVehicleID());
                if (vehicleName == null) {
                    Vehicle vehicle = vehicleDAO.findByID(driverStop.getVehicleID());
                    vehicleName = vehicle == null ? "" : vehicle.getName();
                    vehicleMap.put(driverStop.getVehicleID(), vehicleName);
                }
                driverStop.setVehicleName(vehicleName);
            }
            
            
            return driverStopsList;
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }	    
	}

}
