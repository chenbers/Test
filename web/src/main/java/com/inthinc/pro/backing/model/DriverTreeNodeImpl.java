package com.inthinc.pro.backing.model;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.Vehicle;

public class DriverTreeNodeImpl extends BaseTreeNodeImpl<Driver>
{
    /**
     * 
     */
    private static final long serialVersionUID = 5055637433284792243L;
    
    private DeviceDAO  deviceDAO;
    private VehicleDAO vehicleDAO;
    
    private Device  device;
    private Vehicle vehicle;
    
    private GroupHierarchy groupHeHierarchy;

    @SuppressWarnings("unchecked")
    public DriverTreeNodeImpl(Driver baseEntity, BaseTreeNodeImpl parentNode)
    {
        super(baseEntity, parentNode);
        setLabel(baseEntity.getPerson().getFullName());
        StringBuilder sb = new StringBuilder(TreeNodeType.DRIVER.getCode());
        sb.append(baseEntity.getDriverID());
        setId(Integer.valueOf(sb.toString()));
    }

    @SuppressWarnings("unchecked")
    @Override
    public BaseTreeNodeImpl getParent()
    {
        return parentNode;
    }
    
    @Override
    public TreeNodeType loadTreeNodeType()
    {
        return TreeNodeType.DRIVER;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected List<BaseTreeNodeImpl> loadChildNodes()
    {
        return Collections.EMPTY_LIST;
    }
    
    @Override
    public boolean getAllowsChildren()
    {
        // TODO Auto-generated method stub
        return false;
    }
    
    public Driver getDriver()
    {
        return baseEntity;
    }

    public void setDevice(Device device)
    {
        this.device = device;
    }

    public Device getDevice()
    {
       if(device == null && getVehicle() != null)
           device = deviceDAO.findByID(getVehicle().getDeviceID());
        return device;
    }

    public void setVehicle(Vehicle vehicle)
    {
        this.vehicle = vehicle;
    }

    public Vehicle getVehicle()
    {
        if(vehicle == null)
            vehicle = vehicleDAO.findByDriverInGroup(getDriver().getDriverID(), groupHeHierarchy.getTopGroup().getGroupID());
        return vehicle;
    }

    public void setDeviceDAO(DeviceDAO deviceDAO)
    {
        this.deviceDAO = deviceDAO;
    }

    public DeviceDAO getDeviceDAO()
    {
        return deviceDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO)
    {
        this.vehicleDAO = vehicleDAO;
    }

    public VehicleDAO getVehicleDAO()
    {
        return vehicleDAO;
    }

    public void setGroupHeHierarchy(GroupHierarchy groupHeHierarchy)
    {
        this.groupHeHierarchy = groupHeHierarchy;
    }

    public GroupHierarchy getGroupHeHierarchy()
    {
        return groupHeHierarchy;
    }
}
