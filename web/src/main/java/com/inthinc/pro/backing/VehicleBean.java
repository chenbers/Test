package com.inthinc.pro.backing;

import com.inthinc.pro.backing.model.GroupTreeNodeImpl;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Vehicle;

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
