package com.inthinc.pro.model;

import java.util.Date;

import com.inthinc.pro.dao.annotations.ID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MpgEntity extends BaseScore implements Comparable<MpgEntity> {
	@ID
	private Integer entityID;
	private String entityName;
	private Integer groupID;
	private Integer lightValue;
	private Integer mediumValue;
	private Integer heavyValue;
    private Number odometer;



    public MpgEntity()
    {
    }
	
	public MpgEntity(Integer entityID, String entityName, Integer groupID, Integer lightValue, Integer mediumValue, Integer heavyValue, Date date)
    {
        super(date);
        this.entityID = entityID;
        this.entityName = entityName;
        this.groupID = groupID;
        this.lightValue = lightValue;
        this.mediumValue = mediumValue;
        this.heavyValue = heavyValue;
    }
    
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(entityID);
        sb.append(", date: " + getDate() + " (s" + getDate() + ")");
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
    public Number getOdometer()
    {
        return odometer;
    }

    public void setOdometer(Number odometer)
    {
        this.odometer = odometer;
    }

    @Override
    public int compareTo(MpgEntity o)
    {
        return this.entityName.toUpperCase().compareTo(o.getEntityName().toUpperCase());
    }

}
