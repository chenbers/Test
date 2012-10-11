package com.inthinc.pro.model;

import java.util.List;

import com.inthinc.pro.dao.annotations.Column;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ScoreTypeBreakdown extends BaseEntity
{

    ScoreType scoreType;
    
    @Column(name="percentageList", type=com.inthinc.pro.model.ScoreableEntity.class, updateable=false)
    List<ScoreableEntity> percentageList;
    
    public ScoreTypeBreakdown()
    {
        
    }
    
    public ScoreType getScoreType()
    {
        return scoreType;
    }
    public void setScoreType(ScoreType scoreType)
    {
        this.scoreType = scoreType;
    }
    public List<ScoreableEntity> getPercentageList()
    {
        return percentageList;
    }
    public void setPercentageList(List<ScoreableEntity> percentageList)
    {
        this.percentageList = percentageList;
    }
    
    
    
}
