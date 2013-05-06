/**
 * 
 */
package com.inthinc.pro.service.test.mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.joda.time.Interval;
import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleName;

/**
 * @author dfreitas
 *
 */
@Component
public class VehicleDaoStub implements VehicleDAO {

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.VehicleDAO#clearVehicleDevice(java.lang.Integer, java.lang.Integer)
     */
    @Override
    public void clearVehicleDevice(Integer vehicleID, Integer deviceID) {
    // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.VehicleDAO#findByDriverID(java.lang.Integer)
     */
    @Override
    public Vehicle findByDriverID(Integer driverID) {
        return new Vehicle();
    }

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.VehicleDAO#findByDriverInGroup(java.lang.Integer, java.lang.Integer)
     */
    @Override
    public Vehicle findByDriverInGroup(Integer driverID, Integer groupID) {
        return new Vehicle();
    }

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.VehicleDAO#findByVIN(java.lang.String)
     */
    @Override
    public Vehicle findByVIN(String vin) {
        return new Vehicle();
    }

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.VehicleDAO#getLastLocation(java.lang.Integer)
     */
    @Override
    public LastLocation getLastLocation(Integer vehicleID) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.VehicleDAO#getLastTrip(java.lang.Integer)
     */
    @Override
    public Trip getLastTrip(Integer driverID) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.VehicleDAO#getTrips(java.lang.Integer, java.util.Date, java.util.Date)
     */
    @Override
    public List<Trip> getTrips(Integer vehicleID, Date startDate, Date endDate) {
        return new ArrayList<Trip>();
    }
    
    @Override
    public List<LatLng> getLocationsForTrip(Integer driverID, Date startTime, Date endTime) {
        return Collections.emptyList();
    }

    @Override
    public List<LatLng> getLocationsForTrip(Integer driverID, Interval interval){
        return Collections.emptyList();
    }
    

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.VehicleDAO#getVehiclesInGroup(java.lang.Integer)
     */
    @Override
    public List<Vehicle> getVehiclesInGroup(Integer groupID) {
        return new ArrayList<Vehicle>();
    }

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.VehicleDAO#getVehiclesInGroupHierarchy(java.lang.Integer)
     */
    @Override
    public List<Vehicle> getVehiclesInGroupHierarchy(Integer groupID) {
        return new ArrayList<Vehicle>();
    }

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.VehicleDAO#getVehiclesNearLoc(java.lang.Integer, java.lang.Integer, java.lang.Double, java.lang.Double)
     */
    @Override
    public List<DriverLocation> getVehiclesNearLoc(Integer groupID, Integer numof, Double lat, Double lng) {
        return new ArrayList<DriverLocation>();
    }

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.VehicleDAO#setVehicleDevice(java.lang.Integer, java.lang.Integer, java.util.Date)
     */
    @Override
    public void setVehicleDevice(Integer vehicleID, Integer deviceID, Date assignDate) {
    // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.VehicleDAO#setVehicleDevice(java.lang.Integer, java.lang.Integer)
     */
    @Override
    public void setVehicleDevice(Integer vehicleID, Integer deviceID) {
    // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.VehicleDAO#setVehicleDriver(java.lang.Integer, java.lang.Integer)
     */
    @Override
    public void setVehicleDriver(Integer vehicleID, Integer driverID) {
    // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.VehicleDAO#setVehicleDriver(java.lang.Integer, java.lang.Integer, java.util.Date)
     */
    @Override
    public void setVehicleDriver(Integer vehicleID, Integer driverID, Date assignDate) {
    // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.GenericDAO#create(java.lang.Object, java.lang.Object)
     */
    @Override
    public Integer create(Integer id, Vehicle entity) {
        return 0;
    }

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.GenericDAO#deleteByID(java.lang.Object)
     */
    @Override
    public Integer deleteByID(Integer id) {
        return 0;
    }

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.GenericDAO#findByID(java.lang.Object)
     */
    @Override
    public Vehicle findByID(Integer id) {
        return new Vehicle();
    }

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.GenericDAO#update(java.lang.Object)
     */
    @Override
    public Integer update(Vehicle entity) {
        return 0;
    }

    @Override
    public List<VehicleName> getVehicleNames(Integer groupID) {
        // TODO Auto-generated method stub
        return null;
    }

}
