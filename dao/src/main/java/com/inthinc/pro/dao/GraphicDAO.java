package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;

public interface GraphicDAO extends GenericDAO<ScoreableEntity, Integer>
{
    /**
     * Retrieve the overall score for the specified group and date range.
     * 
     * @param groupID
     * @param startDate
     * @param endDate
     * @return
     */
    ScoreableEntity getOverallScore(Integer groupID, Integer startDate, Integer endDate);
    
    
    /**
     * Retrieve the list of scores for the sub groups or drivers (one level down) under the specified group.
     * 
     * @param groupID
     * @param startDate
     * @param endDate
     * @return
     */
    List<ScoreableEntity> getScores(Integer groupID, Integer startDate, Integer endDate, ScoreType scoreType);

}
