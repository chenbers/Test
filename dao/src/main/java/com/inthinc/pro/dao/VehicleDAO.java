package com.inthinc.pro.dao;

import java.util.Date;
import java.util.List;

import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleName;

public interface VehicleDAO extends GenericDAO<Vehicle, Integer> {
    List<Vehicle> getVehiclesInGroupHierarchy(Integer groupID);
    
    List<Vehicle> getVehiclesInGroup(Integer groupID);

    List<VehicleName> getVehicleNames(Integer groupID);
    
    void setVehicleDriver(Integer vehicleID, Integer driverID);

    // only use these from test code on dev to generate events in the past
    void setVehicleDriver(Integer vehicleID, Integer driverID, Date assignDate);
    void setVehicleDevice(Integer vehicleID, Integer deviceID, Date assignDate);


    void setVehicleDevice(Integer vehicleID, Integer deviceID);

    void clearVehicleDevice(Integer vehicleID, Integer deviceID);

    Vehicle findByVIN(String vin);

    Vehicle findByDriverID(Integer driverID);

    Vehicle findByDriverInGroup(Integer driverID, Integer groupID);

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
