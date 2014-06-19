package com.inthinc.pro.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Group extends BaseEntity implements HasAccountId
{

    @Column(updateable = false)
    private static final long serialVersionUID = 6847106513956433939L;

    @ID
    private Integer           groupID;

    @Column(name = "acctID")
    private Integer           accountID;
    private String            name;
    private Integer           parentID;
    private GroupStatus       status;
    @Column(name = "level")
    private GroupType         type;
    private Integer           managerID;                              // Links to Person ID

    @Column(name = "desc")
    private String            description;

    // Default map view settings (Default Value :USA Center)
    private Integer           mapZoom = 15;
    private Double            mapLat = 38.5482;
    private Double            mapLng = -95.8008;
    private Integer           zoneRev;

    @Column(name = "addrID")
    private Integer           addressID;
    
    @Column(updateable=false)
    private Address           address;

    private DOTOfficeType   dotOfficeType;
    private String   path;
    private String aggDate;

    @Column(name = "glcode")
    private String glCode;

	public Group()
    {
        super();
    }

    public Group(Integer groupID, Integer accountID, String name, Integer parentID)
    {
        super();
        this.groupID = groupID;
        this.accountID = accountID;
        this.name = name;
        this.parentID = parentID;
        this.status = GroupStatus.GROUP_ACTIVE;
        this.dotOfficeType = DOTOfficeType.NONE;
    }

    public Group(Integer groupID, Integer accountID, String name, Integer parentID, GroupType type, Integer managerID, String description, Integer mapZoom, LatLng mapCenter)
    {
        super();
        this.groupID = groupID;
        this.accountID = accountID;
        this.name = name;
        this.parentID = parentID;
        this.status = GroupStatus.GROUP_ACTIVE;
        this.type = type;
        this.managerID = managerID;
        this.description = description;
        this.mapZoom = mapZoom;
        if(mapCenter != null){
            this.mapLat = mapCenter.getLat();
            this.mapLng = mapCenter.getLng();
        }

    }

    public Integer getGroupID()
    {
        return groupID;
    }

    public void setGroupID(Integer groupID)
    {
        this.groupID = groupID;
    }

    public Integer getAccountID()
    {
        return accountID;
    }

    public void setAccountID(Integer accountID)
    {
        this.accountID = accountID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getParentID()
    {
        return parentID;
    }

    public void setParentID(Integer parentID)
    {
        this.parentID = parentID;
    }

    public GroupStatus getStatus()
    {
        return status;
    }

    public void setStatus(GroupStatus status)
    {
        this.status = status;
    }

    public Integer getManagerID()
    {
        return managerID;
    }

    public void setManagerID(Integer managerID)
    {
        this.managerID = managerID;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Integer getMapZoom()
    {
        return mapZoom;
    }

    public void setMapZoom(Integer mapZoom)
    {
        this.mapZoom = mapZoom;
    }

    public LatLng getMapCenter()
    {
        if (mapLat == null || mapLng == null)
            return null;
        return new LatLng(mapLat, mapLng);
    }

    public void setMapCenter(LatLng mapCenter)
    {
        if(mapCenter != null)
        {
            setMapLat(mapCenter.getLat());
            setMapLng(mapCenter.getLng());
        }
    }

    public GroupType getType()
    {
        return type;
    }

    public void setType(GroupType type)
    {
        this.type = type;
    }

    public Double getMapLat()
    {
        return mapLat;
    }

    public void setMapLat(Double mapLat)
    {
        this.mapLat = mapLat;
    }

    public Double getMapLng()
    {
        return mapLng;
    }

    public void setMapLng(Double mapLng)
    {
        this.mapLng = mapLng;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        boolean isEqual = false;
        if (obj instanceof Group)
        {
            EqualsBuilder eb = new EqualsBuilder().append(this.getGroupID(), ((Group)obj).getGroupID());
            isEqual = eb.isEquals();
        }
        return isEqual;
    }
    
    @Override
    public int hashCode()
    {
        HashCodeBuilder hcb = new HashCodeBuilder(5,9);
        hcb.append(this.getGroupID());
        return hcb.toHashCode();
    }
    
    public Integer getZoneRev()
    {
        return zoneRev;
    }

    public void setZoneRev(Integer zoneRev)
    {
        this.zoneRev = zoneRev;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Integer getAddressID() {
        return addressID;
    }

    public void setAddressID(Integer addressID) {
        this.addressID = addressID;
    }

    public DOTOfficeType getDotOfficeType() {
        return dotOfficeType;
    }

    public void setDotOfficeType(DOTOfficeType dotOfficeType) {
        this.dotOfficeType = dotOfficeType;
    }

    public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	
	public String getAggDate() {
		return aggDate;
	}

	public void setAggDate(String aggDate) {
		this.aggDate = aggDate;
	}

	@Override
    public String toString() {
        return "Group [accountID=" + accountID + ", description=" + description + ", groupID=" + groupID + ", managerID=" + managerID + ", mapLat=" + mapLat + ", mapLng=" + mapLng + ", mapZoom="
                + mapZoom + ", name=" + name + ", parentID=" + parentID + ", status=" + status + ", type=" + type + ", zoneRev=" + zoneRev + "dotOfficeType="+dotOfficeType+", addressID"+addressID+"]";
    }

	public String getGlCode() {
		return glCode;
	}

	public void setGlCode(String glCode) {
		this.glCode = glCode;
	}

    
}
