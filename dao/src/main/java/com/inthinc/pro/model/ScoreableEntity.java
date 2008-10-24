package com.inthinc.pro.model;

import com.inthinc.pro.dao.annotations.ID;

public class ScoreableEntity extends BaseEntity {
	@ID
	private Integer entityID;
	  
	private String identifier;
	private String score;
	
	
    private Integer userID;
    private Integer date;

    public ScoreableEntity()
    {
        
    }
	
	public ScoreableEntity(Integer entityID, String identifier, String score, Integer userID, Integer date)
    {
        super();
        this.entityID = entityID;
        this.identifier = identifier;
        this.score = score;
        this.userID = userID;
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
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
    public Integer getUserID()
    {
        return userID;
    }
    public void setUserID(Integer userID)
    {
        this.userID = userID;
    }
    public Integer getDate()
    {
        return date;
    }
    public void setDate(Integer date)
    {
        this.date = date;
    }

}
