package com.inthinc.pro.model;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

public class Group extends BaseEntity {

	private static final long serialVersionUID = 6847106513956433939L;

	@ID
	private Integer groupID;

	@Column(name = "acctID")
	private Integer accountID;
	private String name;
	private Integer parentID;
	private GroupStatus status;
	private GroupLevel level;
	private Integer managerID; // Links to Person ID
	
	private String description;
	
	//Default map view settings
	private Integer mapZoom;
	private LatLng mapCenter;

	

	public Group() {
		super();
	}

	public Group(Integer groupID, Integer accountID, String name,
			Integer parentID) {
		super();
		this.groupID = groupID;
		this.accountID = accountID;
		this.name = name;
		this.parentID = parentID;
		this.status = GroupStatus.GROUP_ACTIVE;
	}
	
	public Group(Integer groupID, Integer accountID, String name,
			Integer parentID,GroupLevel level,Integer managerID,String description,
			Integer mapZoom,LatLng mapCenter) {
		super();
		this.groupID = groupID;
		this.accountID = accountID;
		this.name = name;
		this.parentID = parentID;
		this.status = GroupStatus.GROUP_ACTIVE;
		this.level = level;
		this.managerID = managerID;
		this.description = description;
		this.mapZoom = mapZoom;
		this.mapCenter = mapCenter;
	
	}

	public Integer getGroupID() {
		return groupID;
	}

	public void setGroupID(Integer groupID) {
		this.groupID = groupID;
	}

	public Integer getAccountID() {
		return accountID;
	}

	public void setAccountID(Integer accountID) {
		this.accountID = accountID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParentID() {
		return parentID;
	}

	public void setParentID(Integer parentID) {
		this.parentID = parentID;
	}

	public GroupStatus getStatus() {
		return status;
	}

	public void setStatus(GroupStatus status) {
		this.status = status;
	}

	public Integer getManagerID() {
		return managerID;
	}

	public void setManagerID(Integer managerID) {
		this.managerID = managerID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getMapZoom() {
		return mapZoom;
	}

	public void setMapZoom(Integer mapZoom) {
		this.mapZoom = mapZoom;
	}

	public LatLng getMapCenter() {
		return mapCenter;
	}

	public void setMapCenter(LatLng mapCenter) {
		this.mapCenter = mapCenter;
	}
	public GroupLevel getLevel() {
		return level;
	}

	public void setLevel(GroupLevel level) {
		this.level = level;
	}
}
