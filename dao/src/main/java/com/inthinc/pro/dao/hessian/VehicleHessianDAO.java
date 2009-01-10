package com.inthinc.pro.dao.hessian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.FindByKey;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.Vehicle;

public class VehicleHessianDAO extends GenericHessianDAO<Vehicle, Integer> implements VehicleDAO, FindByKey<Vehicle>
{
    private static final Logger logger = Logger.getLogger(DriverHessianDAO.class);
    private static final String CENTRAL_ID_KEY = "vin";
    private static final Integer VEHICLE_TYPE = 2;


    @Override
    public List<Vehicle> getVehiclesInGroupHierarchy(Integer groupID)
    {
        try
        {
            return getMapper().convertToModelObject(getSiloService().getVehiclesByGroupIDDeep(groupID), Vehicle.class);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }
    
    @Override
    public List<Vehicle> getVehiclesInGroup(Integer groupID)
    {
        List<Vehicle> vehicleList = new ArrayList<Vehicle>();
        try
        {
            List<Vehicle> completeSet = getMapper().convertToModelObject(getSiloService().getVehiclesByGroupID(groupID), Vehicle.class);
            for(Vehicle vehicle: completeSet)
            {
                if(groupID.equals(vehicle.getGroupID()))
                {
                    vehicleList.add(vehicle);
                }
            }
        }
        catch (EmptyResultSetException e)
        {
            vehicleList =  Collections.emptyList();
        }
        
        return vehicleList;
    }

    @Override
    public void setVehicleDriver(Integer vehicleID, Integer driverID)
    {
        getSiloService().setVehicleDriver(vehicleID, driverID);
    }

    @Override
    public void setVehicleDevice(Integer vehicleID, Integer deviceID)
    {
        getSiloService().setVehicleDevice(vehicleID, deviceID);
    }

    @Override
    public void clearVehicleDevice(Integer vehicleID, Integer deviceID)
    {
        getSiloService().clrVehicleDevice(vehicleID, deviceID);
    }

    @Override
    public LastLocation getLastLocation(Integer vehicleID)
    {
        try
        {
            
            return getMapper().convertToModelObject(this.getSiloService().getLastLoc(vehicleID, VEHICLE_TYPE), LastLocation.class);
        }
        catch (EmptyResultSetException e)
        {
            return null;
        }
    }

    @Override
    public Vehicle findByKey(String key)
    {
        return findByVIN(key);
    }

    @Override
    public Vehicle findByVIN(String vin)
    {
        // TODO: it can take up to 5 minutes from when a user record is added until
        // it can be accessed via getID().   Should this method account for that?
        try
        {
            Map<String, Object> returnMap = getSiloService().getID(CENTRAL_ID_KEY, vin);
            Integer deviceId = getCentralId(returnMap);
            return findByID(deviceId);
        }
        catch (EmptyResultSetException e)
        {
            return null;
        }
    }

    @Override
    public Trip getLastTrip(Integer vehicleID)
    {
        try
        {
            return getMapper().convertToModelObject(this.getSiloService().getLastTrip(vehicleID, VEHICLE_TYPE), Trip.class);
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
    public List<Trip> getTrips(Integer vehicleID, Date startDate, Date endDate)
    {
        logger.debug("getTrips() vehicleID = " + vehicleID);
        try
        {
            List<Trip> tripList = getMapper().convertToModelObject(this.getSiloService().getTrips(vehicleID, VEHICLE_TYPE, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(endDate)), Trip.class);
            return tripList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

    
   
}
