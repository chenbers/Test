package com.inthinc.pro.dao.hessian.proserver;

import java.util.List;
import java.util.Map;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.model.ScoreType;

public interface ReportService extends HessianService
{
  // ------------- Methods related to the Scores/Reporting
    
    Map<String, Object> getAverageScoreByType(Integer groupID, Integer startDate, Integer endDate, ScoreType st) throws ProDAOException;

    Map<String, Object> getAverageScoreByTypeAndMiles(Integer driverID, Integer milesBack, ScoreType st) throws ProDAOException;
    
    /**
     * getScores -- retrieves the scores for direct children of the specified group
     * 
     * @param groupID
     * @param startDate
     * @param endDate
     * @param scoreType
     *            SCORE_OVERALL = 1, SCORE_SPEEDING=2, SCORE_SEATBELT=3, SCORE_DRIVING_STYLE=4, SCORE_COACHING_EVENTS=5
     * @return
     * @throws ProDAOException
     */
    List<Map<String, Object>> getScores(Integer groupID, Integer startDate, Integer endDate, Integer scoreType) throws ProDAOException;

    /**
     * getTopFiveScores -- retrieves the top five overall scores for the drivers of the specified group over the last thirty days.
     * 
     * @param groupID
     * @return
     * @throws ProDAOException
     */
    List<Map<String, Object>> getTopFiveScores(Integer groupID);

    /**
     * getBottomFiveScores -- retrieves the bottom five overall scores for the drivers of the specified group over the last thirty days.
     * 
     * @param groupID
     * @return
     * @throws ProDAOException
     */
    List<Map<String, Object>> getBottomFiveScores(Integer groupID);

    /**
     * getScoreBreakdown -- retrieves the scores for the specified group, scoreType and dateRange broken down into 5 percentages (0-100)
     * 
     * 0 - the percentage of drivers in the group whose overall score is between 0 - 1.0 1 - the percentage of drivers in the group whose overall score is between 1.1 - 2.0 2 - the
     * percentage of drivers in the group whose overall score is between 2.1 - 3.0 3 - the percentage of drivers in the group whose overall score is between 3.1 - 4.0 4 - the
     * percentage of drivers in the group whose overall score is between 4.1 - 5.0
     * 
     * @param groupID
     * @param startDate
     * @param endDate
     * @return
     * @throws ProDAOException
     */
    List<Map<String, Object>> getScoreBreakdown(Integer groupID, Integer startDate, Integer endDate, Integer scoreType) throws ProDAOException;

    /**
     * getScoreHistory -- retrieves the scores available associated with mileage. 
     * 
     * @param driverID
     * @param milesBack
     * @param scoreType
     * @return
     * @throws ProDAOException
     */
    List<Map<String, Object>> getDriverScoreHistoryByMiles(Integer driverID, Integer milesBack, Integer scoreType) throws ProDAOException;
    
}
