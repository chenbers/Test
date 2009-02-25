package com.inthinc.pro.model;


import org.apache.commons.lang.builder.HashCodeBuilder;

import com.inthinc.pro.dao.annotations.ID;
import com.inthinc.pro.model.app.Roles;

public class Role extends BaseEntity implements ReferenceEntity, Comparable<Role>
{
    /**
     * 
     */
    private static final long serialVersionUID = -5621595749335872123L;

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
    public Integer retrieveID()
    {
        return roleID;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (obj != null && obj instanceof Role && retrieveID() != null && ((Role)obj).retrieveID() != null)
            return ((Role) obj).retrieveID().equals(retrieveID());
        else
            return false;
    }
    
    @Override
    public int hashCode()
    {
        HashCodeBuilder hcb = new HashCodeBuilder(5,9);
        hcb.append(this.retrieveID());
        return hcb.toHashCode();
    }
    
    @Override
    public int compareTo(Role o)
    {
        return this.name.compareTo(o.getName());
    }
}
