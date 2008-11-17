package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.Vehicle;

public class VehicleHessianDAO extends GenericHessianDAO<Vehicle, Integer> implements VehicleDAO
{

    @Override
    public List<Vehicle> getVehiclesByAcctID(Integer accountID)
    {
        try
        {
            return getMapper().convertToModelObject(getSiloService().getVehiclesByAcctID(accountID), Vehicle.class);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }

    }

    @Override
    public List<Vehicle> getVehiclesInGroupHierarchy(Integer groupID)
    {
        try
        {
            return getMapper().convertToModelObject(getSiloService().getVehiclesInGroupHierarchy(groupID), Vehicle.class);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

    @Override
    public Integer setVehicleDriver(Integer vehicleID, Integer driverID)
    {
        return getChangedCount(getSiloService().setVehicleDriver(vehicleID, driverID));
    }

    @Override
    public Integer setVehicleDevice(Integer vehicleID, Integer deviceID)
    {
        return getChangedCount(getSiloService().setVehicleDevice(vehicleID, deviceID));
    }
}
