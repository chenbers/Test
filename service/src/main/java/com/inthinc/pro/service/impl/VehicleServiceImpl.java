package com.inthinc.pro.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import com.inthinc.pro.dao.MpgDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.MpgEntity;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.service.VehicleService;

public class VehicleServiceImpl extends BaseService implements VehicleService {

    private VehicleDAO vehicleDAO;
    private MpgDAO mpgDAO;

    public List<Vehicle> getAll() {
        return vehicleDAO.getVehiclesInGroupHierarchy(securityBean.getGroupID());
    }

    public Vehicle get(Integer vehicleID) {
        Vehicle vehicle = securityBean.getVehicle(vehicleID);
        if (securityBean.isAuthorized(vehicle))
            return vehicle;

        return null;
    }

    public Vehicle findByVIN(String vin) {
        Vehicle vehicle = securityBean.getVehicleByVIN(vin);
        if (securityBean.isAuthorized(vehicle))
            return vehicle;

        return null;
    }

    public Integer add(Vehicle vehicle) {
        if (securityBean.isAuthorized(vehicle))
            return vehicleDAO.create(vehicle.getGroupID(), vehicle);

        return -1;
    }

    public Integer update(Vehicle vehicle) {
        if (securityBean.isAuthorized(vehicle))
            return vehicleDAO.update(vehicle);

        return -1;
    }

    public Integer delete(Integer vehicleID) {
        if (securityBean.isAuthorizedByVehicleID(vehicleID))
            return vehicleDAO.deleteByID(vehicleID);

        return -1;
    }

    public List<Integer> add(List<Vehicle> vehicles) {
        List<Integer> results = new ArrayList<Integer>();
        for (Vehicle vehicle : vehicles)
            results.add(add(vehicle));
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
        if (securityBean.isAuthorizedByVehicleID(vehicleID))

            return vehicleDAO.getLastLocation(vehicleID);
        return null;

    }

    // TODO do we implement these?
    // vehicleDAO.getLastTrip(driverID);
    // vehicleDAO.getVehiclesNearLoc(groupID, numof, lat, lng);
    // vehicleDAO.getVehiclesInGroup(groupID);

    public List<Trip> getTrips(Integer vehicleID, String day) {
        if (!securityBean.isAuthorizedByVehicleID(vehicleID))
            return null;

        Date startDate = parseDate(day);
        if (startDate == null)
            return null;

        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        // Add 1 day to the calendar
        cal.add(Calendar.DATE, 1);

        return vehicleDAO.getTrips(vehicleID, startDate, cal.getTime());

    }

    public List<Trip> getTrips(Integer vehicleID) {
        if (!securityBean.isAuthorizedByVehicleID(vehicleID))
            return null;

        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        // Add 1 day to the calendar
        cal.add(Calendar.DATE, -30);

        return vehicleDAO.getTrips(vehicleID, cal.getTime(), date);
    }

    // fuel consumption for vehicle (parameter:day)"
    public List<MpgEntity> getVehicleMPG(Integer id) {
        if (!securityBean.isAuthorizedByVehicleID(id))
            return null;

        Duration duration = Duration.DAYS;

        return mpgDAO.getVehicleEntities(id, duration, 99);
    }

    public Vehicle assignDevice(Integer id, Integer deviceID) {
        if (!securityBean.isAuthorizedByVehicleID(id) || !securityBean.isAuthorizedByDeviceID(deviceID))
            return null;

        vehicleDAO.setVehicleDevice(id, deviceID);
        return vehicleDAO.findByID(id);
    }

    public Vehicle assignDriver(Integer id, Integer driverID) {
        if (!securityBean.isAuthorizedByVehicleID(id) || !securityBean.isAuthorizedByDriverID(driverID))
            return null;

        vehicleDAO.setVehicleDriver(id, driverID);
        return vehicleDAO.findByID(id);
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }

    public MpgDAO getMpgDAO() {
        return mpgDAO;
    }

    public void setMpgDAO(MpgDAO mpgDAO) {
        this.mpgDAO = mpgDAO;
    }

    private Date parseDate(String datestring) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        Date date = null;

        try {
            date = formatter.parse(datestring);
        } catch (ParseException e) {
            return null;
        }
        return date;
    }
}
