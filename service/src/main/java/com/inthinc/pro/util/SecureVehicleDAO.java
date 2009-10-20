package com.inthinc.pro.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.inthinc.pro.dao.MpgDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.MpgEntity;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.Vehicle;

public class SecureVehicleDAO extends BaseSecureDAO {

    private VehicleDAO vehicleDAO;
    private SecureGroupDAO groupDAO;
    private MpgDAO mpgDAO;
    private SecureDeviceDAO deviceDAO;
    private SecureDriverDAO driverDAO;

    private boolean isAuthorized(Vehicle vehicle) {
        if (vehicle != null) {
            // TODO do we give user access to all groups, regardless of the users group????
            // TODO if so, we need a fast security check to verify a group intersects with user's groups
            if (groupDAO.isAuthorized(vehicle.getGroupID()))
                return true;

        }
        return false;
    }

    public Vehicle findByID(Integer vehicleID) {
        Vehicle vehicle = vehicleDAO.findByID(vehicleID);
        if (isAuthorized(vehicle))
            return vehicle;
        return null;
    }

    private boolean isAuthorized(Integer vehicleID) {
        return isAuthorized(findByID(vehicleID));
    }

    public List<Vehicle> getAll() {
        return vehicleDAO.getVehiclesInGroupHierarchy(getGroupID());
    }

    public Vehicle findByVIN(String vin) {
        Vehicle vehicle = vehicleDAO.findByVIN(vin);
        if (isAuthorized(vehicle))
            return vehicle;
        return null;
    }

    public Integer create(Vehicle vehicle) {
        if (isAuthorized(vehicle))
            return vehicleDAO.create(vehicle.getGroupID(), vehicle);

        return null;
    }

    public Integer update(Vehicle vehicle) {
        if (isAuthorized(vehicle))
            return vehicleDAO.update(vehicle);

        return 0;
    }

    public Integer deleteByID(Integer vehicleID) {
        if (isAuthorized(vehicleID))
            return vehicleDAO.deleteByID(vehicleID);

        return 0;
    }

    public LastLocation getLastLocation(Integer vehicleID) {
        if (isAuthorized(vehicleID))
            return vehicleDAO.getLastLocation(vehicleID);
        return null;
    }

    // TODO do we implement these?
    // vehicleDAO.getLastTrip(driverID);
    // vehicleDAO.getVehiclesNearLoc(groupID, numof, lat, lng);
    // vehicleDAO.getVehiclesInGroup(groupID);

    public List<Trip> getTrips(Integer vehicleID, String day) {
        if (!isAuthorized(vehicleID))
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
        if (!isAuthorized(vehicleID))
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
        if (!isAuthorized(id))
            return null;

        Duration duration = Duration.DAYS;

        return mpgDAO.getVehicleEntities(id, duration, 99);
    }

    public Vehicle assignDevice(Integer id, Integer deviceID) {
        if (!isAuthorized(id) || !deviceDAO.isAuthorized(deviceID))
            return null;

        vehicleDAO.setVehicleDevice(id, deviceID);
        return vehicleDAO.findByID(id);
    }

    public Vehicle assignDriver(Integer id, Integer driverID) {
        if (!isAuthorized(id) || !driverDAO.isAuthorized(driverID))
            return null;

        vehicleDAO.setVehicleDriver(id, driverID);
        return vehicleDAO.findByID(id);
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

    public MpgDAO getMpgDAO() {
        return mpgDAO;
    }

    public void setMpgDAO(MpgDAO mpgDAO) {
        this.mpgDAO = mpgDAO;
    }

    public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public SecureGroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(SecureGroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public SecureDeviceDAO getDeviceDAO() {
        return deviceDAO;
    }

    public void setDeviceDAO(SecureDeviceDAO deviceDAO) {
        this.deviceDAO = deviceDAO;
    }

    public SecureDriverDAO getDriverDAO() {
        return driverDAO;
    }

    public void setDriverDAO(SecureDriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

}
