package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.Vehicle;

public interface VehicleDAO extends GenericDAO<Vehicle, Integer>
{
    List<Vehicle> getVehiclesInGroupHierarchy(Integer groupID);

    void setVehicleDriver(Integer vehicleID, Integer driverID);

    void setVehicleDevice(Integer vehicleID, Integer deviceID);
}
