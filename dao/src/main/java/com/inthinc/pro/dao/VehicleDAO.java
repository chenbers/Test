package com.inthinc.pro.dao;

import java.util.Date;
import java.util.List;

import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.Vehicle;

public interface VehicleDAO extends GenericDAO<Vehicle, Integer>
{
    List<Vehicle> getVehiclesInGroupHierarchy(Integer groupID);
    
    List<Vehicle> getVehiclesInGroup(Integer groupID);

    void setVehicleDriver(Integer vehicleID, Integer driverID);

    void setVehicleDevice(Integer vehicleID, Integer deviceID);

    Vehicle findByVIN(String vin);
    
    LastLocation getLastLocation(Integer vehicleID);
    
    List<Trip> getTrips(Integer vehicleID, Date startDate, Date endDate);
    
    Trip getLastTrip(Integer driverID);


}
