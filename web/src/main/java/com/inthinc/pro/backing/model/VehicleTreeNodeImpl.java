package com.inthinc.pro.backing.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Vehicle;

public class VehicleTreeNodeImpl extends BaseTreeNodeImpl<Vehicle>
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -245093200058030847L;
    
    private transient DeviceDAO deviceDAO;
    private transient DriverDAO driverDAO;
    private transient VehicleDAO vehicleDAO;
    
    private Device device;
    private Driver driver;
  
    public VehicleTreeNodeImpl(Vehicle baseEntity, BaseTreeNodeImpl parentNode)
    {
        super(baseEntity, parentNode);
        setLabel(baseEntity.getName());
        StringBuilder sb = new StringBuilder(TreeNodeType.VEHICLE.getCode());
        sb.append(baseEntity.getVehicleID());
        setId(Integer.valueOf(sb.toString()));
    }
    
    @Override
    public BaseTreeNodeImpl getParent()
    {
        return parentNode;
    }
    
    @Override
    public TreeNodeType loadTreeNodeType()
    {
        return TreeNodeType.VEHICLE;
    }
    
    @Override
    protected List<BaseTreeNodeImpl> loadChildNodes()
    {
        Device device = deviceDAO.findByID(baseEntity.getDeviceID());
        if(device != null)
        {
            this.device = device;
            DeviceTreeNodeImpl treeNode = new DeviceTreeNodeImpl(device,this);
            treeNode.setVehicleDAO(vehicleDAO);
            treeNode.setDriverDAO(driverDAO);
            childNodes = new ArrayList<BaseTreeNodeImpl>();
            childNodes.add(treeNode);
            return childNodes;
        }
        else
        {
            return Collections.EMPTY_LIST;
        }
        
    }
    
    @Override
    public boolean getAllowsChildren()
    {
        return true;
    }
    
    public Vehicle getVehicle()
    {
        return baseEntity;
    }

    public void setDeviceDAO(DeviceDAO deviceDAO)
    {
        this.deviceDAO = deviceDAO;
    }

    public DeviceDAO getDeviceDAO()
    {
        return deviceDAO;
    }

    public void setDevice(Device device)
    {
        this.device = device;
    }

    public Device getDevice()
    {
        if(device == null)
            device = deviceDAO.findByID(getVehicle().getDeviceID());
        return device;
    }

    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }

    public Driver getDriver()
    {
        if(driver == null)
            driver = driverDAO.findByID(getVehicle().getDriverID());
        return driver;
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
}
