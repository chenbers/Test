package com.inthinc.pro.backing.model;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.model.User;

public class UserTreeNodeImpl extends BaseTreeNodeImpl<User>
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -787708377700653602L;

    @SuppressWarnings("unchecked")
    public UserTreeNodeImpl(User baseEntity, BaseTreeNodeImpl parentNode)
    {
        super(baseEntity, parentNode);
        setLabel(baseEntity.getPerson().getFullName());
        setId(baseEntity.getUserID());
        StringBuilder sb = new StringBuilder(TreeNodeType.USER.getCode());
        sb.append(baseEntity.getUserID());
        this.setId(Integer.valueOf(sb.toString()));
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
        return TreeNodeType.USER;
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
    
    public User getUser()
    {
        return baseEntity;
    }

}
