package com.inthinc.pro.dao.hessian;

import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.hessian.proserver.CentralService;
import com.inthinc.pro.model.Driver;

public class DriverHessianDAO extends GenericHessianDAO<Driver, Integer> implements DriverDAO
{
    private static final Logger logger = Logger.getLogger(DriverHessianDAO.class);
    
    @Override
    public List<Driver> getAllDrivers(Integer groupID)
    {
        List<Driver> driverList = 
            convertToModelObject(this.getSiloService().getAllDrivers(groupID));
        return driverList;
    }    
    
    public Driver getDriverByID(Integer driverID)
    {
        return convertToModelObject(this.getSiloService().getDriverByID(driverID));
    }
}
