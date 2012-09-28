package com.inthinc.pro.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.annotations.ID;

@XmlRootElement
public class ScoreableEntity extends BaseScore implements Comparable<ScoreableEntity>
{

    @ID
	private Integer entityID;

	private EntityType entityType;
	private String identifier;
	private Number identifierNum;
	private Integer score;
    private ScoreType scoreType;
    public ScoreableEntity()
    {
        
    }
	
	public ScoreableEntity(Integer entityID, EntityType entityType, String identifier, Integer score, Date date, ScoreType scoreType)
    {
        super(date);
        this.entityID = entityID;
        this.entityType = entityType;
        this.identifier = identifier;
        this.score = score;
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
	public Number getIdentifierNum() {
        return identifierNum;
    }
    public void setIdentifierNum(Number identifierNum){
        this.identifierNum = identifierNum;
    }
    public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
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
    public Double getScoreAsDouble(){
        Double dScore = null;
        if(score != null){
        	dScore = Double.valueOf((Double)(this.getScore()==null ? 0 : this.getScore() / 10.0));
        }
        
        return dScore;
    }
    
    public String getScoreAsString(){
    	
//        String returnString = "N/A";
//        if(score != null && score >= 0)
//        {
//            returnString =  NumberFormat.getInstance(getLocale()).format(new BigDecimal(score).movePointLeft(1));
//        }
//        
//        return returnString;

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

    public String toString()
    {
    	return "ScoreableEntity id: "  + entityID +  " date: " + getDate() + " identifier: "  + identifier + " score: " + score;
    }

}
