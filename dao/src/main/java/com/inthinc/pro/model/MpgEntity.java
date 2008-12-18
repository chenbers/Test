package com.inthinc.pro.model;

import java.util.Date;

import com.inthinc.pro.dao.annotations.ID;
import com.inthinc.pro.dao.util.DateUtil;

public class MpgEntity extends BaseEntity {
	@ID
	private Integer entityID;
	private String entityName;
	private Integer groupID;
	private Integer lightValue;
	private Integer mediumValue;
	private Integer heavyValue;
    private Integer date;
    private Integer odometer;



    public MpgEntity()
    {
    }
	
	public MpgEntity(Integer entityID, String entityName, Integer groupID, Integer lightValue, Integer mediumValue, Integer heavyValue, Integer date)
    {
        super();
        this.entityID = entityID;
        this.entityName = entityName;
        this.groupID = groupID;
        this.lightValue = lightValue;
        this.mediumValue = mediumValue;
        this.heavyValue = heavyValue;
        this.date = date;
    }
    
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(entityID);
        sb.append(", date: " + date + " (s" + new Date(DateUtil.convertSecondsToMilliseconds(date)) + ")");
        return sb.toString();
    }

	public Integer getEntityID() {
		return entityID;
	}

	public void setEntityID(Integer entityID) {
		this.entityID = entityID;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public Integer getGroupID() {
		return groupID;
	}

	public void setGroupID(Integer groupID) {
		this.groupID = groupID;
	}

	public Integer getLightValue() {
		return lightValue;
	}

	public void setLightValue(Integer lightValue) {
		this.lightValue = lightValue;
	}

	public Integer getMediumValue() {
		return mediumValue;
	}

	public void setMediumValue(Integer mediumValue) {
		this.mediumValue = mediumValue;
	}

	public Integer getHeavyValue() {
		return heavyValue;
	}

	public void setHeavyValue(Integer heavyValue) {
		this.heavyValue = heavyValue;
	}

	public Integer getDate() {
		return date;
	}

	public void setDate(Integer date) {
		this.date = date;
	}
	
    public Integer getOdometer()
    {
        return odometer;
    }

    public void setOdometer(Integer odometer)
    {
        this.odometer = odometer;
    }

}
