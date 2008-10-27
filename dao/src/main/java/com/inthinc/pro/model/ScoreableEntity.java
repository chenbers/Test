package com.inthinc.pro.model;

import com.inthinc.pro.dao.annotations.ID;

public class ScoreableEntity extends BaseEntity {
	@ID
	private Integer entityID;

	private EntityType entityType;
	private String identifier;
	private Integer score;
    private Integer date;

    public ScoreableEntity()
    {
        
    }
	
	public ScoreableEntity(Integer entityID, EntityType entityType, String identifier, Integer score, Integer date)
    {
        super();
        this.entityID = entityID;
        this.entityType = entityType;
        this.identifier = identifier;
        this.score = score;
        this.date = date;
    }
	
    public Integer getEntityID() {
		return entityID;
	}
	public void setEntityID(Integer entityID) {
		this.entityID = entityID;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
    public Integer getDate()
    {
        return date;
    }
    public void setDate(Integer date)
    {
        this.date = date;
    }
    public EntityType getEntityType()
    {
        return entityType;
    }

    public void setEntityType(EntityType entityType)
    {
        this.entityType = entityType;
    }

}
