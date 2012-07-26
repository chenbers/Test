package com.inthinc.pro.dao.hessian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.FindByKey;
import com.inthinc.pro.dao.LocationDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleName;

@SuppressWarnings("serial")
public class VehicleHessianDAO extends GenericHessianDAO<Vehicle, Integer> implements VehicleDAO, FindByKey<Vehicle> {
    private static final Logger logger = Logger.getLogger(DriverHessianDAO.class);
    private static final String CENTRAL_ID_KEY = "vin";
    private static final Integer VEHICLE_TYPE = 2;
    private DeviceDAO deviceDAO;
    private LocationDAO locationDAO;
    
    @Override
    public List<Vehicle> getVehiclesInGroupHierarchy(Integer groupID) {
        try {
            return getMapper().convertToModelObject(getSiloService().getVehiclesByGroupIDDeep(groupID), Vehicle.class);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Vehicle> getVehiclesInGroup(Integer groupID) {
        List<Vehicle> vehicleList = new ArrayList<Vehicle>();
        try {
            List<Vehicle> completeSet = getMapper().convertToModelObject(getSiloService().getVehiclesByGroupID(groupID), Vehicle.class);
            for (Vehicle vehicle : completeSet) {
                if (groupID.equals(vehicle.getGroupID())) {
                    vehicleList.add(vehicle);
                }
            }
        } catch (EmptyResultSetException e) {
            vehicleList = Collections.emptyList();
        }
        return vehicleList;
    }

    @Override
    public void setVehicleDriver(Integer vehicleID, Integer driverID) {
        getSiloService().setVehicleDriver(vehicleID, driverID);
    }

    @Override
    public void setVehicleDriver(Integer vehicleID, Integer driverID, Date assignDate) {
        getSiloService().setVehicleDriver(vehicleID, driverID, DateUtil.convertDateToSeconds(assignDate));
    }

    @Override
    public void setVehicleDevice(Integer vehicleID, Integer deviceID) {
        Device device = null;
        if (deviceID != null)
            device = deviceDAO.findByID(deviceID);
        if (device != null && device.getDeviceID() != null && device.getVehicleID() != null) {
            clearVehicleDevice(device.getVehicleID(), device.getDeviceID());
        }
        getSiloService().setVehicleDevice(vehicleID, deviceID);
    }
    @Override
    public void setVehicleDevice(Integer vehicleID, Integer deviceID, Date assignDate) {
        getSiloService().setVehicleDevice(vehicleID, deviceID, DateUtil.convertDateToSeconds(assignDate));
    }

    @Override
    public void clearVehicleDevice(Integer vehicleID, Integer deviceID) {
        logger.debug("Cleared VehicleID: " + vehicleID + " DeviceID:" + deviceID);
        getSiloService().clrVehicleDevice(vehicleID, deviceID);
    }

    @Override
    public LastLocation getLastLocation(Integer vehicleID) {
    	return locationDAO.getLastLocationForVehicle(vehicleID);
    }

    @Override
    public Vehicle findByKey(String key) {
        return findByVIN(key);
    }

    @Override
    public Vehicle findByVIN(String vin) {
        // TODO: it can take up to 5 minutes from when a user record is added until
        // it can be accessed via getID(). Should this method account for that?
        try {
            Map<String, Object> returnMap = getSiloService().getID(CENTRAL_ID_KEY, vin);
            Integer deviceId = getCentralId(returnMap);
            return findByID(deviceId);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }

    @Override
    public Vehicle findByDriverID(Integer driverID) {
        try {
            return getMapper().convertToModelObject(getSiloService().getVehicleByDriverID(driverID), Vehicle.class);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }

    @Override
    public Vehicle findByDriverInGroup(Integer driverID, Integer groupID) {
        List<Vehicle> vehicleList = getVehiclesInGroupHierarchy(groupID);
        for (Vehicle vehicle : vehicleList) {
            if (vehicle.getDriverID() != null && vehicle.getDriverID().equals(driverID)) {
                return vehicle;
            }
        }
        return null;
    }

    @Override
    public Trip getLastTrip(Integer vehicleID) {
/*    	try {
            return getMapper().convertToModelObject(this.getSiloService().getLastTrip(vehicleID, VEHICLE_TYPE), Trip.class);
        } catch (EmptyResultSetException e) {
            return null;
        }
*/        
    	return locationDAO.getLastTripForVehicle(vehicleID);
    }

    @Override
    public List<Trip> getTrips(Integer vehicleID, Date startDate, Date endDate) {
        logger.debug("getTrips() vehicleID = " + vehicleID);
/*        try {
            List<Trip> tripList = getMapper().convertToModelObject(
                    this.getSiloService().getTrips(vehicleID, VEHICLE_TYPE, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(endDate)), Trip.class);
            return tripList;
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
*/
    	return locationDAO.getTripsForVehicle(vehicleID, startDate, endDate);

    }

    @Override
    public List<DriverLocation> getVehiclesNearLoc(Integer groupID, Integer numof, Double lat, Double lng) {
        try {
            return getMapper().convertToModelObject(this.getSiloService().getVehiclesNearLoc(groupID, numof, lat, lng), DriverLocation.class);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

    public void setDeviceDAO(DeviceDAO deviceDAO) {
        this.deviceDAO = deviceDAO;
    }

    public DeviceDAO getDeviceDAO() {
        return deviceDAO;
    }

    public LocationDAO getLocationDAO() {
		return locationDAO;
	}

	public void setLocationDAO(LocationDAO locationDAO) {
		this.locationDAO = locationDAO;
	}

	@Override
    public List<VehicleName> getVehicleNames(Integer groupID)
    {
        
        try
        {
            List<VehicleName> vehicleList = getMapper().convertToModelObject(this.getSiloService().getVehicleNamesByGroupIDDeep(groupID), VehicleName.class);
            return vehicleList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }

    }
}
