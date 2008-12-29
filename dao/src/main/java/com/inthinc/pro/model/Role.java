package com.inthinc.pro.model;


import com.inthinc.pro.dao.annotations.ID;
import com.inthinc.pro.model.app.Roles;

public class Role extends BaseEntity implements ReferenceEntity
{
    @ID
    Integer roleID;
    
    String name;
    
    public Role()
    {
        
    }
    public Role(Integer roleID, String name)
    {
        this.roleID = roleID;
        this.name = name;
        
    }

    public Integer getRoleID()
    {
        return roleID;
    }

    public void setRoleID(Integer roleID)
    {
        this.roleID = roleID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    
    public static Role valueOf(Integer ID)
    {
        return Roles.getRoleById(ID);
    }
    
    public String toString()
    {
        return "ROLE_" + getName().toUpperCase();
    }
    @Override
    public Integer getID()
    {
        return roleID;
    }
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Role)
            return ((Role) obj).getID().equals(getID());
        else
            return false;
    }
}
