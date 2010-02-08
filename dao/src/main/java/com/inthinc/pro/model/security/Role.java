package com.inthinc.pro.model.security;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.inthinc.pro.dao.annotations.ID;
import com.inthinc.pro.model.BaseEntity;

@XmlRootElement
public class Role extends BaseEntity implements Comparable<Role>{
	
	private static final long serialVersionUID = 1L;
	
	@ID
	private Integer roleID;
	private Integer acctID;
	private String name;
	private List<AccessPoint> accessPoints;
	
	public Role(Integer acctID, Integer roleID, String name) {
		super();
		this.roleID = roleID;
		this.acctID = acctID;
		this.name = name;
	}

	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getRoleID() {
		return roleID;
	}

	public void setRoleID(Integer roleID) {
		this.roleID = roleID;
	}

	public Integer getAcctID() {
		return acctID;
	}

	public void setAcctID(Integer acctID) {
		this.acctID = acctID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<AccessPoint> getAccessPoints() {
		return accessPoints;
	}

	public void setAccessPoints(List<AccessPoint> accessPoints) {
		this.accessPoints = accessPoints;
	}
    public String toString()
    {
        return "ROLE_" + getName().toUpperCase();
    }
    
//    public static Role valueOf(Integer ID)
//    {
//        return Roles.getRoleById(ID);
//    }

//	@Override
//	public Integer retrieveID() {
//		// TODO Auto-generated method stub
//		return roleID;
//	}
    @Override
    public boolean equals(Object obj)
    {
        if (obj != null && obj instanceof Role && getRoleID() != null && ((Role)obj).getRoleID() != null)
            return ((Role) obj).getRoleID().equals(getRoleID());
        else
            return false;
    }
    
    @Override
    public int hashCode()
    {
        HashCodeBuilder hcb = new HashCodeBuilder(5,9);
        hcb.append(this.getRoleID());
        return hcb.toHashCode();
    }
    
    @Override
    public int compareTo(Role o)
    {
        return this.name.compareTo(o.getName());
    }

}
