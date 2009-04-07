package com.inthinc.pro.backing.model;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.model.Driver;

public class DriverTreeNodeImpl extends BaseTreeNodeImpl<Driver>
{
    /**
     * 
     */
    private static final long serialVersionUID = 5055637433284792243L;

    @SuppressWarnings("unchecked")
    public DriverTreeNodeImpl(Driver baseEntity, BaseTreeNodeImpl parentNode)
    {
        super(baseEntity, parentNode);
        setLabel(baseEntity.getPerson().getFullName());
        setId(baseEntity.getDriverID());
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
}
