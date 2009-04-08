package com.inthinc.pro.dao;

import java.util.Date;
import java.util.List;

import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.Vehicle;

public interface VehicleDAO extends GenericDAO<Vehicle, Integer>
{
    List<Vehicle> getVehiclesInGroupHierarchy(Integer groupID);
    
    List<Vehicle> getVehiclesInGroup(Integer groupID);

    void setVehicleDriver(Integer vehicleID, Integer driverID);

    void setVehicleDevice(Integer vehicleID, Integer deviceID);

    void clearVehicleDevice(Integer vehicleID, Integer deviceID);

    Vehicle findByVIN(String vin);
    
    Vehicle findByDriverInGroup(Integer driverID,Integer groupID);
    
    LastLocation getLastLocation(Integer vehicleID);
    
    List<Trip> getTrips(Integer vehicleID, Date startDate, Date endDate);
    
    Trip getLastTrip(Integer driverID);
    
    /**
     * Gets a list of DriverLocation's near the specified LatLng.
     * 
     * @param groupID
     * @param numof
     * @param lat
     * @param lng
     * @return
     */
    List<DriverLocation> getVehiclesNearLoc(Integer groupID, Integer numof, Double lat, Double lng);


}
