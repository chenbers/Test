package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.FindByKey;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.Vehicle;

public class VehicleHessianDAO extends GenericHessianDAO<Vehicle, Integer> implements VehicleDAO, FindByKey<Vehicle>
{

    private static final String CENTRAL_ID_KEY = "vin";

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

    @Override
    public Vehicle findByKey(String key)
    {
        return findByVIN(key);
    }

    @Override
    public Vehicle findByVIN(String vin)
    {
        // TODO: it can take up to 5 minutes from when a user record is added until
        // it can be accessed via getID().   Should this method account for that?
        try
        {
            Map<String, Object> returnMap = getSiloService().getID(CENTRAL_ID_KEY, vin);
            Integer deviceId = getCentralId(returnMap);
            return findByID(deviceId);
        }
        catch (EmptyResultSetException e)
        {
            return null;
        }
    }
}
