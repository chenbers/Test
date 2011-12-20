package com.inthinc.pro.model.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;
import com.inthinc.pro.model.BaseEntity;

@XmlRootElement
public class Role extends BaseEntity implements Comparable<Role>{
	
//	private static final long serialVersionUID = 1L;
	
	@ID
	private Integer roleID;
	private Integer acctID;
	private String name;
	private List<AccessPoint> accessPts;
	@Column(updateable = false)
	private Map<Integer,AccessPoint> accessPointsMap;
	
	public Role(Integer acctID, Integer roleID, String name, List<AccessPoint> accessPoints) {
		super();
		this.roleID = roleID;
		this.acctID = acctID;
		this.name = name;
		this.accessPts = accessPoints;
	}

	public Role() {
		super();
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

	public List<AccessPoint> getAccessPts() {
		return accessPts;
	}
	public boolean isAdmin(){
	    return "Admin".equalsIgnoreCase(name);
	}
	public boolean contains(Integer accessPointID){
	    
	     return accessPointsMap.containsKey(accessPointID);
	}
	public void addAccessPt(AccessPoint accessPoint){
	    this.accessPts.add(accessPoint);
	    accessPointsMap.put(accessPoint.getAccessPtID(), accessPoint);
	}

    public void addAccessPts(List<AccessPoint> accessPoints) {
        this.accessPts.addAll(accessPoints);
        if (accessPoints == null) {
            accessPoints = new ArrayList<AccessPoint>();
        }
        
        accessPointsMap = new HashMap<Integer, AccessPoint>();
        for (AccessPoint ap : accessPoints) {

            accessPointsMap.put(ap.getAccessPtID(), ap);
        }
    }
	public void setAccessPts(List<AccessPoint> accessPoints) {
		
		this.accessPts = accessPoints;
		if (accessPoints == null){
			
			accessPoints = new ArrayList<AccessPoint>();
		}
		accessPointsMap = new HashMap<Integer,AccessPoint>();
		
		for (AccessPoint ap: accessPoints){
			
			accessPointsMap.put(ap.getAccessPtID(),ap);
		}
	}
    public String toString()
    {
        return "ROLE_" + getName().toUpperCase();
    }
    
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
