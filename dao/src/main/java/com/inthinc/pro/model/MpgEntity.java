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
	private Double lightValue;
	private Double mediumValue;
	private Double heavyValue;
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
        this.lightValue = lightValue.doubleValue()/100;
        this.mediumValue = mediumValue.doubleValue()/100;
        this.heavyValue = heavyValue.doubleValue()/100;
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

	public Double getLightValue() {
		return lightValue;
	}

	public void setLightValue(Double lightValue) {
		this.lightValue = lightValue;
	}

	public Double getMediumValue() {
		return mediumValue;
	}

	public void setMediumValue(Double mediumValue) {
		this.mediumValue = mediumValue;
	}

	public Double getHeavyValue() {
		return heavyValue;
	}

	public void setHeavyValue(Double heavyValue) {
		this.heavyValue = heavyValue;
	}

}
