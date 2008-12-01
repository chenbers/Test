package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.Vehicle;

public interface VehicleDAO extends GenericDAO<Vehicle, Integer>
{
//    List<Vehicle> getVehiclesByAcctID(Integer accountID);

    List<Vehicle> getVehiclesInGroupHierarchy(Integer groupID);

    Integer setVehicleDriver(Integer vehicleID, Integer driverID);

    Integer setVehicleDevice(Integer vehicleID, Integer deviceID);
}
