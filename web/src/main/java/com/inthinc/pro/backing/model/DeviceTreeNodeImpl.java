package com.inthinc.pro.backing.model;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Vehicle;

public class DeviceTreeNodeImpl extends BaseTreeNodeImpl<Device>
{
    
    private DriverDAO driverDAO;
    private VehicleDAO vehicleDAO;
    
    private Driver driver;
    private Vehicle vehicle;
    
    public DeviceTreeNodeImpl(Device baseEntity, BaseTreeNodeImpl parentNode)
    {
        super(baseEntity, parentNode);
        this.setLabel(baseEntity.getName());
        StringBuilder sb = new StringBuilder(TreeNodeType.DEVICE.getCode());
        sb.append(baseEntity.getDeviceID());
        this.setId(Integer.valueOf(sb.toString()));
    }

    @Override
    public boolean getAllowsChildren()
    {
      
        return false;
    }
    
    @Override
    public BaseTreeNodeImpl getParent()
    {
        return parentNode;
    }
    
    @Override
    protected List<BaseTreeNodeImpl> loadChildNodes()
    {
        
        return Collections.EMPTY_LIST;
    }
    
    @Override
    public TreeNodeType loadTreeNodeType()
    {
        return TreeNodeType.DEVICE;
    }
    
    public Device getDevice()
    {
        return baseEntity;
    }

    public void setDriverDAO(DriverDAO driverDAO)
    {
        this.driverDAO = driverDAO;
    }

    public DriverDAO getDriverDAO()
    {
        return driverDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO)
    {
        this.vehicleDAO = vehicleDAO;
    }

    public VehicleDAO getVehicleDAO()
    {
        return vehicleDAO;
    }

    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }

    public Driver getDriver()
    {
        if(driver == null)
        {
            vehicle = vehicleDAO.findByID(getDevice().getVehicleID());
            driver = driverDAO.findByID(vehicle.getDriverID());
        }
        return driver;
    }

    public void setVehicle(Vehicle vehicle)
    {
        
        this.vehicle = vehicle;
    }

    public Vehicle getVehicle()
    {
        if(vehicle == null)
            vehicle = vehicleDAO.findByID(getDevice().getVehicleID());
        return vehicle;
    }
    
   

}
