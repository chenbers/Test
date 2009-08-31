package com.inthinc.pro.dao.hessian;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;

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
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.ReportSchedule;
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
        return super.findByID(id);
    }
    
    @Override
    public Integer update(CrashReport entity) {
        return super.update(entity);
    }
    
    @Override
    public List<CrashReport> getCrashReportsByGroupID(Integer groupID) {
        
        // Grab everything by setting the following parameters. 
        GregorianCalendar gcStart       = new GregorianCalendar(1990,1,1);
        Date startDT                    = gcStart.getTime();
        GregorianCalendar gcStop        = new GregorianCalendar(2090,1,1);
        Date stopDT                     = gcStop.getTime();
        
        Integer incForgiven             = CrashReportDAO.INCLUDE_FORGIVEN;
        
        try
        {
            List<CrashReport> crashReports = getCrashes(groupID,startDT,stopDT,incForgiven);            
            return crashReports;
        }
        catch(EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }
        
    @Override
    public List<CrashReport> getCrashReportsByGroupIDAndForgiven(Integer groupID,Integer incForgiven) {
        
        GregorianCalendar gcStart       = new GregorianCalendar(1990,1,1);
        Date startDT                    = gcStart.getTime();
        GregorianCalendar gcStop        = new GregorianCalendar(2090,1,1);
        Date stopDT                     = gcStop.getTime();
        
        try
        {
            List<CrashReport> crashReports = getCrashes(groupID,startDT,stopDT,incForgiven);            
            return crashReports;
        }
        catch(EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }    
    
    @Override
    public List<CrashReport> getCrashes(Integer groupID, Date startDT, Date stopDT, Integer incForgiven) {
        try
        {
            List<Map<String, Object>> crashReportList = getSiloService().getCrashes(
                    groupID,
                    DateUtil.convertDateToSeconds(startDT),
                    DateUtil.convertDateToSeconds(stopDT),
                    incForgiven);
            List<CrashReport> crashReports = getMapper().convertToModelObject(crashReportList, CrashReport.class);
            Map<Integer, Vehicle> vehicleMap = new HashMap<Integer, Vehicle>();
            Map<Integer, Driver> driverMap = new HashMap<Integer, Driver>();
            Map<Integer, Group>   groupMap = new HashMap<Integer, Group>();
            
            for(CrashReport crashReport:crashReports){
                
                if(!vehicleMap.containsKey(crashReport.getVehicleID()))
                    vehicleMap.put(crashReport.getVehicleID(), vehicleDAO.findByID(crashReport.getVehicleID()));
                
                if(!driverMap.containsKey(crashReport.getDriverID()))
                    driverMap.put(crashReport.getDriverID(), driverDAO.findByID(crashReport.getDriverID()));
                
                crashReport.setVehicle(vehicleMap.get(crashReport.getVehicleID()));
                crashReport.setDriver(driverMap.get(crashReport.getDriverID()));
            }
            
            return crashReports;
        }catch(EmptyResultSetException e)
        {
            return Collections.emptyList();
        }        
    }
    
    @Override
    public Trip getTrip(CrashReport crashReport) {
        
        
        Calendar searchStartDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        Calendar searchEndDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        searchEndDate.roll(Calendar.DATE, 1);
        if(crashReport.getDate() != null){
            searchStartDate.setTime(crashReport.getDate());
            searchEndDate.setTime(crashReport.getDate());
            DateUtil.resetTime(searchEndDate);
            DateUtil.resetTime(searchStartDate);
            searchEndDate.roll(Calendar.DATE, 1);
        }
        
        logger.debug("Begin Date: " + searchStartDate.getTime());
        logger.debug("End Date: " + searchEndDate.getTime());
        Trip trip = null;
        
        if(crashReport.getDriverID() != null){
            List<Trip> tripList = driverDAO.getTrips(crashReport.getDriverID(), searchStartDate.getTime(), searchEndDate.getTime());
            trip = locateTripInList(tripList, crashReport.getLatLng());
        }
        
        if(crashReport.getVehicleID() != null && trip == null){
            List<Trip> tripList = vehicleDAO.getTrips(crashReport.getVehicleID(), searchStartDate.getTime(), searchEndDate.getTime());
            trip = locateTripInList(tripList, crashReport.getLatLng());
        }

        return trip;
    }
    
    public Trip locateTripInList(List<Trip> tripList,LatLng latLng){
        
        for(Trip trip:tripList){
            for(LatLng tempLatLnt:trip.getRoute()){
                if(latLng.equals(tempLatLnt)){
                    return trip;
                }
            }
        }
        return null;
    }
    
    @Override
    public Integer forgiveCrash(Integer groupID)
    {
        return getChangedCount(getSiloService().forgiveCrash(groupID));
    }

    @Override
    public Integer unforgiveCrash(Integer groupID)
    {
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
