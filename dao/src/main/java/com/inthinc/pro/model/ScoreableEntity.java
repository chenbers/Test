package com.inthinc.pro.model;

import java.util.Date;

import com.inthinc.pro.dao.annotations.ID;

public class ScoreableEntity extends BaseEntity implements Comparable<ScoreableEntity>
{

    @ID
	private Integer entityID;

	private EntityType entityType;
	private String identifier;
	private Integer score;
    private Date date;
    private ScoreType scoreType;
    public ScoreableEntity()
    {
        
    }
	
	public ScoreableEntity(Integer entityID, EntityType entityType, String identifier, Integer score, Date date, ScoreType scoreType)
    {
        super();
        this.entityID = entityID;
        this.entityType = entityType;
        this.identifier = identifier;
        this.score = score;
        this.date = date;
        this.scoreType = scoreType;
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
    public Date getDate()
    {
        return date;
    }
    public void setDate(Date date)
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
    public ScoreType getScoreType()
    {
        return scoreType;
    }
    public void setScoreType(ScoreType scoreType)
    {
        this.scoreType = scoreType;
    }
    
    public Float getScoreAsFloat(){
        Float fScore = null;
        if(score != null){
            fScore = Float.valueOf((float)(this.getScore()==null ? 0 : this.getScore() / 10.0));
        }
        
        return fScore;
    }
    
    public String getScoreAsString(){
        String returnString = null;
        if(score < 0)
        {
            returnString = "N/A";
        }else{
            returnString = getScoreAsFloat().toString();
        }
        
        return returnString;
    }

    @Override
    public int compareTo(ScoreableEntity o)
    {
        return score.compareTo(o.getScore());
    }


}
