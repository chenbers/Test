package com.inthinc.pro.backing.model;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.model.Vehicle;

public class VehicleTreeNodeImpl extends BaseTreeNodeImpl<Vehicle>
{
    
    
    
  
    public VehicleTreeNodeImpl(Vehicle baseEntity, BaseTreeNodeImpl parentNode)
    {
        super(baseEntity, parentNode);
        setLabel(baseEntity.getFullName());
        setId(baseEntity.getVehicleID());
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
        return Collections.EMPTY_LIST;
    }
    
    @Override
    public boolean getAllowsChildren()
    {
        // TODO Auto-generated method stub
        return false;
    }
    
    public Vehicle getVehicle()
    {
        return baseEntity;
    }
    
    
}
