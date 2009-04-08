package com.inthinc.pro.backing.model;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.model.Device;

public class UnassignedDevicesTreeNodeImpl extends BaseTreeNodeImpl
{
    private transient DeviceDAO deviceDAO;
    private Integer accountID;
    
    public UnassignedDevicesTreeNodeImpl(Integer accountID)
    {
        super(null);
        setLabel("UNASSIGNED DEVICES");
        setId(-1);
        this.accountID = accountID;
    }

    @Override
    public boolean getAllowsChildren()
    {
        return true;
    }
    
    @Override
    public BaseTreeNodeImpl getParent()
    {
        UnassignedDevicesTreeNodeImpl devicesTreeNodeImpl = new UnassignedDevicesTreeNodeImpl(accountID);
        devicesTreeNodeImpl.setDeviceDAO(deviceDAO);
        devicesTreeNodeImpl.addChildNode(this);
        return devicesTreeNodeImpl;
    }
    
    @Override
    protected List<BaseTreeNodeImpl> loadChildNodes()
    {
        if(childNodes == null)
        {
            childNodes = new ArrayList<BaseTreeNodeImpl>();
            List<Device> devices =  deviceDAO.getDevicesByAcctID(accountID);
            for(Device device:devices)
            {
                if(device.getVehicleID() == null){
                    DeviceTreeNodeImpl treeNode = new DeviceTreeNodeImpl(device,this);
                    childNodes.add(treeNode);
                }
            }
        }
        return childNodes;
    }
    
    @Override
    public TreeNodeType loadTreeNodeType()
    {
        return TreeNodeType.DEVICE;
    }

    public void setDeviceDAO(DeviceDAO deviceDAO)
    {
        this.deviceDAO = deviceDAO;
    }

    public DeviceDAO getDeviceDAO()
    {
        return deviceDAO;
    }

}
