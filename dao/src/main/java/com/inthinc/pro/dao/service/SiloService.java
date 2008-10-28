package com.inthinc.pro.dao.service;

import java.util.List;
import java.util.Map;

import com.inthinc.pro.ProDAOException;

public interface SiloService extends DAOService
{

    // Methods related to the User type
    Map<String, Object> deleteUser(Integer userID) throws ProDAOException;

    Map<String, Object> getUser(Integer userID) throws ProDAOException;

    Map<String, Object> updateUser(Integer userID, Map<String, Object> userMap) throws ProDAOException;

    Map<String, Object> createUser(Integer acctID, Map<String, Object> userMap) throws ProDAOException;

    // Methods related to the Vehicle type
    List<Map<String, Object>> getVehiclesByAcctID(Integer acctID) throws ProDAOException;

    // ------------- Methods related to the Scores/Reporting
    Map<String, Object> getOverallScore(Integer groupID, Integer startDate, Integer endDate) throws ProDAOException;

    /**
     * getScores -- retrieves the scores for direct children of the specified group 
     * 
     * @param groupID
     * @param startDate
     * @param endDate
     * @param scoreType 
     *              SCORE_OVERALL = 1,
     *              SCORE_SPEEDING=2,
     *              SCORE_SEATBELT=3,
     *              SCORE_DRIVING_STYLE=4,
     *              SCORE_COACHING_EVENTS=5
     * @return
     * @throws ProDAOException
     */
    List<Map<String, Object>> getScores(Integer groupID, Integer startDate, Integer endDate, Integer scoreType) throws ProDAOException;

}
