package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.proserver.CentralService;
import com.inthinc.pro.model.Vehicle;

public class VehicleHessianDAO extends GenericHessianDAO<Vehicle, Integer, CentralService> implements VehicleDAO
{

    @Override
    public List<Vehicle> getVehiclesByAcctID(Integer accountID)
    {
        try
        {
            return convertToModelObject(getSiloService().getVehiclesByAcctID(accountID));
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }

    }

}
