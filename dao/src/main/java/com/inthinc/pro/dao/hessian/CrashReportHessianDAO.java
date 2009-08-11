package com.inthinc.pro.dao.hessian;

import java.util.List;

import com.inthinc.pro.dao.AddressDAO;
import com.inthinc.pro.dao.CrashReportDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.CrashReport;


public class CrashReportHessianDAO extends GenericHessianDAO<CrashReport, Integer> implements CrashReportDAO {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private PersonDAO personDAO;
    private VehicleDAO vehicleDAO;
    private AddressDAO addressDAO;

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
        // TODO Auto-generated method stub
        return null;
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
}
