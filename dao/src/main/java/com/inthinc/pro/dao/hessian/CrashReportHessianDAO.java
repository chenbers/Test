package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.AddressDAO;
import com.inthinc.pro.dao.CrashReportDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.CrashReport;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.ReportSchedule;
import com.inthinc.pro.model.Vehicle;


public class CrashReportHessianDAO extends GenericHessianDAO<CrashReport, Integer> implements CrashReportDAO {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
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
        try
        {
            List<Map<String, Object>> crashReportList = getSiloService().getCrashReportsByGroupID(groupID);
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
