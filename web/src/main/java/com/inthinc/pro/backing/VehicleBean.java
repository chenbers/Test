package com.inthinc.pro.backing;

import org.springframework.security.AccessDeniedException;

import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.util.MessageUtil;

public class VehicleBean extends BaseBean
{
    private Integer vehicleID;
    private Vehicle vehicle;
    private VehicleDAO vehicleDAO;

    public Integer getVehicleID()
    {
        return vehicleID;
    }

    public void setVehicleID(Integer vehicleID)
    {
        this.vehicle = vehicleDAO.findByID(vehicleID);
        if (vehicle == null || getGroupHierarchy().getGroup(vehicle.getGroupID()) == null)
            throw new AccessDeniedException(MessageUtil.getMessageString("exception_accessDenied", getUser().getLocale()));
        this.vehicleID = vehicleID;
    }

    public Vehicle getVehicle()
    {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle)
    {
        this.vehicle = vehicle;
    }

    public VehicleDAO getVehicleDAO()
    {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO)
    {
        this.vehicleDAO = vehicleDAO;
    }


}
