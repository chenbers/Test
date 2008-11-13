package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;

public interface ScoreDAO extends GenericDAO<ScoreableEntity, Integer>
{
    /**
     * Retrieve the overall score for the specified group and date range.
     * 
     * @param groupID
     * @param startDate
     * @param endDate
     * @return
     */
    ScoreableEntity getAverageScoreByType(Integer groupID, Integer startDate, Integer endDate, ScoreType st);
    
    
    /**
     * Retrieve the list of scores for the sub groups or drivers (one level down) under the specified group.
     * 
     * @param groupID
     * @param startDate
     * @param endDate
     * @param scoreType
     * @return
     */
    List<ScoreableEntity> getScores(Integer groupID, Integer startDate, Integer endDate, ScoreType scoreType);

    /**
     * Retrieve the list of overall scores for top five drivers for the last 30 days
     * 
     * @param groupID
     * @return
     */
    List<ScoreableEntity> getTopFiveScores(Integer groupID);
    /**
     * Retrieve the list of overall scores for bottom five drivers for the last 30 days
     * 
     * @param groupID
     * @return
     */
    List<ScoreableEntity> getBottomFiveScores(Integer groupID);
    
    /**
     * Retrieve the list of 5 percentage scores for the specified group.  The list contains the following:
     *          0 - the percentage of drivers in the group whose overall score is between 0 - 1.0
     *          1 - the percentage of drivers in the group whose overall score is between 1.1 - 2.0
     *          2 - the percentage of drivers in the group whose overall score is between 2.1 - 3.0
     *          3 - the percentage of drivers in the group whose overall score is between 3.1 - 4.0
     *          4 - the percentage of drivers in the group whose overall score is between 4.1 - 5.0
 
     * 
     * @param groupID
     * @param startDate
     * @param endDate
     * @return
     */
    List<ScoreableEntity> getScoreBreakdown(Integer groupID, Integer startDate, Integer endDate, ScoreType scoreType);
    
    /**
     * Retrieve the scores and mileage.
     */
    List<ScoreableEntity> getDriverScoreHistoryByMiles(Integer driverID, Integer milesBack, ScoreType scoreType);
    
}
