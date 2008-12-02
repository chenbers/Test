package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.Driver;

public class DriverHessianDAO extends GenericHessianDAO<Driver, Integer> implements DriverDAO
{
    private static final Logger logger = Logger.getLogger(DriverHessianDAO.class);

    @Override
    public List<Driver> getAllDrivers(Integer groupID)
    {
        
        try
        {
            List<Driver> driverList = getMapper().convertToModelObject(this.getSiloService().getDriversByGroupID(groupID), Driver.class);
            return driverList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }

    }

}
