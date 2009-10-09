package com.inthinc.pro.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.MpgEntity;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.service.VehicleService;
import com.inthinc.pro.util.SecureVehicleDAO;

public class VehicleServiceImpl implements VehicleService {

    private SecureVehicleDAO vehicleDAO;

    public List<Vehicle> getAll() {
        return vehicleDAO.getAll();
    }

    public Vehicle get(Integer vehicleID) {
        return vehicleDAO.findByID(vehicleID);
    }

    public Vehicle findByVIN(String vin) {
        return vehicleDAO.findByVIN(vin);
    }

    public Integer create(Vehicle vehicle) {
        return vehicleDAO.create(vehicle);
    }

    public Integer update(Vehicle vehicle) {
        return vehicleDAO.update(vehicle);
    }

    public Integer delete(Integer vehicleID) {
        return vehicleDAO.deleteByID(vehicleID);
    }

    public List<Integer> create(List<Vehicle> vehicles) {
        List<Integer> results = new ArrayList<Integer>();
        for (Vehicle vehicle : vehicles)
            results.add(create(vehicle));
        return results;
    }

    public List<Integer> update(List<Vehicle> vehicles) {
        List<Integer> results = new ArrayList<Integer>();
        for (Vehicle vehicle : vehicles)
            results.add(update(vehicle));
        return results;
    }

    public List<Integer> delete(List<Integer> vehicleIDs) {
        List<Integer> results = new ArrayList<Integer>();
        for (Integer id : vehicleIDs) {
            results.add(delete(id));
        }
        return results;
    }

    public LastLocation getLastLocation(Integer vehicleID) {
        return vehicleDAO.getLastLocation(vehicleID);
    }

    // TODO do we implement these?
    // vehicleDAO.getLastTrip(driverID);
    // vehicleDAO.getVehiclesNearLoc(groupID, numof, lat, lng);
    // vehicleDAO.getVehiclesInGroup(groupID);

    public List<Trip> getTrips(Integer vehicleID, String day) {

        return vehicleDAO.getTrips(vehicleID);

    }

    public List<Trip> getTrips(Integer vehicleID) {
        return vehicleDAO.getTrips(vehicleID);
    }

    // fuel consumption for vehicle (parameter:day)"
    public List<MpgEntity> getVehicleMPG(Integer id) {
        return vehicleDAO.getVehicleMPG(id);
    }

    public Vehicle assignDevice(Integer id, Integer deviceID) {
        return vehicleDAO.assignDevice(id, deviceID);
    }

    public Vehicle assignDriver(Integer id, Integer driverID) {
        return vehicleDAO.assignDriver(id, driverID);
    }

    public void setVehicleDAO(SecureVehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public SecureVehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }
}
