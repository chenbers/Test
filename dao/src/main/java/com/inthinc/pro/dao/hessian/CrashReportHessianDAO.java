package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.inthinc.pro.dao.AddressDAO;
import com.inthinc.pro.dao.CrashReportDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.CrashReport;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.ForgivenType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.Vehicle;

public class CrashReportHessianDAO extends GenericHessianDAO<CrashReport, Integer> implements CrashReportDAO {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(CrashReportHessianDAO.class);
    private PersonDAO personDAO;
    private VehicleDAO vehicleDAO;
    private DriverDAO driverDAO;
    private AddressDAO addressDAO;
    private GroupDAO groupDAO;

    @Override
    public Integer create(Integer id, CrashReport entity) {
        return super.create(id, entity);
    }

    @Override
    public Integer deleteByID(Integer id) {
        return super.deleteByID(id);
    }

    @Override
    public CrashReport findByID(Integer id) {
        CrashReport report = super.findByID(id);
        if(report.getVehicleID() != null) {
            report.setVehicle(vehicleDAO.findByID(report.getVehicleID()));
        }
        if(report.getDriverID() != null) {
            report.setDriver(driverDAO.findByID(report.getDriverID()));
        }
        return report;
    }

    @Override
    public Integer update(CrashReport entity) {
        if(entity.getHasTrace() == 1)
            entity.setTrace(null);
        return super.update(entity);
    }

    @Override
    public List<CrashReport> findByGroupID(Integer groupID) {
        return findByGroupID(groupID, ForgivenType.INCLUDE);
    }

    @Override
    public List<CrashReport> findByGroupID(Integer groupID, ForgivenType forgivenType) {
        DateTime startTime = new DateTime(1960, 1, 1, 0, 0, 0, 0);
        DateTime stopTime = new DateTime();
        return findByGroupID(groupID, startTime.toDate(), stopTime.plusDays(3).toDate(), forgivenType);
    }

    @Override
    public List<CrashReport> findByGroupID(Integer groupID, Date startDate, Date stopDate, ForgivenType forgivenType) {
        try {
            List<Map<String, Object>> crashReportList = getSiloService().getCrashes(groupID, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(stopDate),
                    forgivenType.getCode());
            List<CrashReport> crashReports = getMapper().convertToModelObject(crashReportList, CrashReport.class);
            Map<Integer, Vehicle> vehicleMap = new HashMap<Integer, Vehicle>();
            Map<Integer, Driver> driverMap = new HashMap<Integer, Driver>();
            Map<Integer, Group> groupMap = new HashMap<Integer, Group>();
            for (CrashReport crashReport : crashReports) {
                if (!vehicleMap.containsKey(crashReport.getVehicleID()))
                    vehicleMap.put(crashReport.getVehicleID(), vehicleDAO.findByID(crashReport.getVehicleID()));
                if (!driverMap.containsKey(crashReport.getDriverID()))
                    driverMap.put(crashReport.getDriverID(), driverDAO.findByID(crashReport.getDriverID()));
                crashReport.setVehicle(vehicleMap.get(crashReport.getVehicleID()));
                crashReport.setDriver(driverMap.get(crashReport.getDriverID()));
            }
            return crashReports;
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Trip getTrip(CrashReport crashReport) {
        DateTime crashTime = new DateTime(crashReport.getDate());        
        List<Trip> tripList = null;
        //TODO: Don't lookup trips by driverID if the driver is the UNKNOWN driver
        if (crashReport.getDriverID() != null) {
            tripList = driverDAO.getTrips(crashReport.getDriverID(), crashTime.minusDays(1).toDate(), crashTime.plusDays(1).toDate());
        } else if (crashReport.getVehicleID() != null) {
            tripList = vehicleDAO.getTrips(crashReport.getVehicleID(), crashTime.minusDays(1).toDate(), crashTime.plusDays(1).toDate());
        }
        
        if(tripList != null && tripList.size() > 0) {
            for (Trip trip : tripList) {
                DateTime startTime = new DateTime(trip.getStartTime());
                DateTime endTime = new DateTime(trip.getEndTime());
                if(crashTime.isAfter(startTime.minusSeconds(1).getMillis()) && crashTime.isBefore(endTime.plusSeconds(1).getMillis())) {
                    //It seems that if the crash time occurred between a trips start and end time, that would indicate we have the trip that the crash is associated with.
                    //If for some reason it turns out that a driver can some how have trips that overlap, then we need to loop through the trips LatLngs and determine the correct trip that crash was a
                    //part of. The method below (locateTripInList) only looks for exact latlng matches. Trip event data contains LatLngs that are not as precise as the Crash LatLng.
//                    for (LatLng latlng : trip.getRoute()) {
//                        
//                    }
                    return trip;
                }
            }
        }
        
        return null;
    }

    public Trip locateTripInList(List<Trip> tripList, LatLng latLng) {
        for (Trip trip : tripList) {
            for (LatLng tempLatLnt : trip.getRoute()) {
                if (latLng.equals(tempLatLnt)) {
                    return trip;
                }
            }
        }
        return null;
    }

    @Override
    public Integer forgiveCrash(Integer groupID) {
        return getChangedCount(getSiloService().forgiveCrash(groupID));
    }

    @Override
    public Integer unforgiveCrash(Integer groupID) {
        return getChangedCount(getSiloService().unforgiveCrash(groupID));
    }

    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public PersonDAO getPersonDAO() {
        return personDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }

    public void setAddressDAO(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }

    public AddressDAO getAddressDAO() {
        return addressDAO;
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    public DriverDAO getDriverDAO() {
        return driverDAO;
    }
}
