package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.Vehicle;

public class VehicleHessianDAO extends GenericHessianDAO<Vehicle, Integer> implements VehicleDAO
{


    @Override
    public List<Vehicle> getVehiclesInGroupHierarchy(Integer groupID)
    {
        try
        {
            return getMapper().convertToModelObject(getSiloService().getVehiclesByGroupID(groupID), Vehicle.class);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

    @Override
    public void setVehicleDriver(Integer vehicleID, Integer driverID)
    {
        getSiloService().setVehicleDriver(vehicleID, driverID);
    }

    @Override
    public void setVehicleDevice(Integer vehicleID, Integer deviceID)
    {
        getSiloService().setVehicleDevice(vehicleID, deviceID);
    }

    @Override
    public LastLocation getLastLocation(Integer vehicleID)
    {
        try
        {
            return getMapper().convertToModelObject(this.getSiloService().getLastLoc(LastLocation.VEHICLE_TYPE, vehicleID), LastLocation.class);
        }
        catch (EmptyResultSetException e)
        {
            return null;
        }
    }
}
