package com.inthinc.pro.model;

import com.inthinc.pro.dao.annotations.ID;

public class ScoreableEntity extends BaseEntity implements Comparable<ScoreableEntity>
{

    @ID
	private Integer entityID;

	private EntityType entityType;
	private String identifier;
	private Integer score;
    private Integer date;
    private ScoreType scoreType;
    public ScoreableEntity()
    {
        
    }
	
	public ScoreableEntity(Integer entityID, EntityType entityType, String identifier, Integer score, Integer date, ScoreType scoreType)
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

	public Float getScoreAsFloat(){
	    Float fScore = null;
	    if(score != null){
	        fScore = new Float((this.getScore()==null) ? 0 : this.getScore() / 10.0);
	    }
	    
	    return fScore;
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

    public ScoreType getScoreType()
    {
        return scoreType;
    }

    public void setScoreType(ScoreType scoreType)
    {
        this.scoreType = scoreType;
    }

    @Override
    public int compareTo(ScoreableEntity o)
    {

        return score.compareTo(o.getScore());
    }


}
