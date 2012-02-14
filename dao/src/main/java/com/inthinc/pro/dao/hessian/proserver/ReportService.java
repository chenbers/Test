package com.inthinc.pro.dao.hessian.proserver;

import java.util.List;
import java.util.Map;

public interface ReportService extends HessianService
{
  // ------------- Methods related to the Scores/Reporting
    

    /**
     * retrieves the mpg values (light, medium, heavy) for direct children of the specified group
     * 
     * @param groupID
     * @param duration
     *          1 - 30 days, 2 - 3 months, 3 - 6 months, 4 - 12 months
     * @return
     * @throws ProDAOException
     */
//    List<Map<String, Object>> getMpgValues(Integer groupID, Integer duration) throws ProDAOException;
	
    /**
     * Return the average score for the specified group and time range.
     * 
     * @param groupID
     * @param duration
     *          1 - 30 days, 2 - 3 months, 3 - 6 months, 4 - 12 months
     * @param scoreType
     *            SCORE_OVERALL = 1, SCORE_SPEEDING=2, SCORE_SEATBELT=3, SCORE_DRIVING_STYLE=4, SCORE_COACHING_EVENTS=5
     * @return Map<String,Object> that maps to a ScoreableEntity
     * @throws ProDAOException
     */
//    Map<String, Object> getAverageScoreByType(Integer groupID, Integer duration, ScoreType scoreType) throws ProDAOException;


    /**
     * Return the average score for the specified driver and miles back.
     * 
     * @param driverID
     * @param milesBack
     *          500  1,000 10,000
     * @param scoreType
     *            SCORE_OVERALL = 1, SCORE_SPEEDING=2, SCORE_SEATBELT=3, SCORE_DRIVING_STYLE=4, SCORE_COACHING_EVENTS=5
     * @return Map<String,Object> that maps to a ScoreableEntity
     * @throws ProDAOException
     */
//    Map<String, Object> getAverageScoreByTypeAndMiles(Integer driverID, Integer milesBack, ScoreType scoreType) throws ProDAOException;
    
    /**
     * Return the average score for the specified driver and miles back.
     * 
     * @param driverID
     * @param milesBack
     *          500  1,000 10,000
     * @param scoreType
     *            SCORE_OVERALL = 1, SCORE_SPEEDING=2, SCORE_SEATBELT=3, SCORE_DRIVING_STYLE=4, SCORE_COACHING_EVENTS=5
     * @return Map<String,Object> that maps to a ScoreableEntity
     * @throws ProDAOException
     */
//    Map<String, Object> getVehicleAverageScoreByTypeAndMiles(Integer vehicleID, Integer milesBack, ScoreType scoreType) throws ProDAOException;
    
    /**
     * getScores -- retrieves the scores for direct children of the specified group
     * 
     * @param groupID
     * @param duration
     *          1 - 30 days, 2 - 3 months, 3 - 6 months, 4 - 12 months
     * @param scoreType
     *            SCORE_OVERALL = 1, SCORE_SPEEDING=2, SCORE_SEATBELT=3, SCORE_DRIVING_STYLE=4, SCORE_COACHING_EVENTS=5
     * @return List<Map<String,Object>> that maps to a List of ScoreableEntity
     * @throws ProDAOException
     */
//    List<Map<String, Object>> getScores(Integer groupID, Integer duration,Integer scoreType) throws ProDAOException;

    /**
     * getTopFiveScores -- retrieves the top five overall scores for the drivers of the specified group over the last thirty days.
     * 
     * @param groupID
     * @return List<Map<String,Object>> that maps to a List of ScoreableEntity
     * @throws ProDAOException
     */
//    List<Map<String, Object>> getTopFiveScores(Integer groupID);

    /**
     * getBottomFiveScores -- retrieves the bottom five overall scores for the drivers of the specified group over the last thirty days.
     * 
     * @param groupID
     * @return List<Map<String,Object>> that maps to a List of ScoreableEntity
     * @throws ProDAOException
     */
//    List<Map<String, Object>> getBottomFiveScores(Integer groupID);

    /**
     * getScoreBreakdown -- retrieves the scores for the specified group, scoreType and dateRange broken down into 5 percentages (0-100)
     * 
     * 0 - the percentage of drivers in the group whose overall score is between 0 - 1.0 
     * 1 - the percentage of drivers in the group whose overall score is between 1.1 - 2.0 
     * 2 - the percentage of drivers in the group whose overall score is between 2.1 - 3.0 
     * 3 - the percentage of drivers in the group whose overall score is between 3.1 - 4.0 
     * 4 - the percentage of drivers in the group whose overall score is between 4.1 - 5.0
     * 
     * @param groupID
     * @param startDate
     * @param endDate
     * @param scoreType
     *            SCORE_OVERALL = 1, SCORE_SPEEDING=2, SCORE_SEATBELT=3, SCORE_DRIVING_STYLE=4, SCORE_COACHING_EVENTS=5
     * @return List<Map<String,Object>> that maps to a List of ScoreableEntity
     * @throws ProDAOException
     */
//    List<Map<String, Object>> getScoreBreakdown(Integer groupID,  Integer duration, Integer scoreType) throws ProDAOException;

    /**
     * getDriverScoreHistoryByMiles -- retrieves a list of scores for the specified driverID and milesBack.   
     * 
     * @param driverID
     * @param milesBack
     *          500  1,000 5,000 10,000
     * @param scoreType
     *            SCORE_OVERALL = 1, SCORE_SPEEDING=2, SCORE_SEATBELT=3, SCORE_DRIVING_STYLE=4, SCORE_COACHING_EVENTS=5
     * @return List<Map<String,Object>> that maps to a List of ScoreableEntity
     * @throws ProDAOException
     */
//    List<Map<String, Object>> getDriverScoreHistoryByMiles(Integer driverID, Integer milesBack, Integer scoreType) throws ProDAOException;
    
    /**
     * getVehicleScoreHistoryByMiles -- retrieves a list of scores for the specified vehicleID and milesBack.   
     * 
     * @param driverID
     * @param milesBack
     *          500  1,000 5,000 10,000
     * @param scoreType
     *            SCORE_OVERALL = 1, SCORE_SPEEDING=2, SCORE_SEATBELT=3, SCORE_DRIVING_STYLE=4, SCORE_COACHING_EVENTS=5
     * @return List<Map<String,Object>> that maps to a List of ScoreableEntity
     * @throws ProDAOException
     */
//    List<Map<String, Object>> getVehicleScoreHistoryByMiles(Integer vehicleID, Integer milesBack, Integer scoreType) throws ProDAOException;
    
    /**
     * Retrieve the List of Score Break Downs for a specific score type.  
     * 
     * A Score Break Down object consists of a score type and 5 percentage scores 
     * for the specified type.  The list contains the following:
     *          0 - the percentage of drivers in the group whose score is between 0 - 1.0
     *          1 - the percentage of drivers in the group whose score is between 1.1 - 2.0
     *          2 - the percentage of drivers in the group whose score is between 2.1 - 3.0
     *          3 - the percentage of drivers in the group whose score is between 3.1 - 4.0
     *          4 - the percentage of drivers in the group whose score is between 4.1 - 5.0
     *          
     *  The following list shows the expected break down for each score type that can be passed in:
     *  ScoreType = OVERALL (1)
     *      Breakdown list:
     *          OVERALL (1) 
     *          SPEEDING (2)
     *          SEAT BELT (3)
     *          DRIVING STYLE (4)
     *  ScoreType = SPEEDING (2)
     *      Breakdown list:
     *          SCORE_SPEEDING_21_30 (7)
     *          SCORE_SPEEDING_31_40 (8)
     *          SCORE_SPEEDING_41_54 (9)
     *          SCORE_SPEEDING_55_64 (10)
     *          SCORE_SPEEDING_65_80 (11)
     *  ScoreType = SEAT BELT (3)
     *      Breakdown list:
     *          SCORE_SEATBELT_30_DAYS(12)
     *          SCORE_SEATBELT_3_MONTHS(13)
     *          SCORE_SEATBELT_6_MONTHS(14)
     *          SCORE_SEATBELT_12_MONTHS(15)
     *  ScoreType = DRIVING STYLE (4)
     *      Breakdown list:
     *          SCORE_DRIVING_STYLE_HARD_BRAKE(16)
     *          SCORE_DRIVING_STYLE_HARD_ACCEL(17)
     *          SCORE_DRIVING_STYLE_HARD_LTURN(18)
     *          SCORE_DRIVING_STYLE_HARD_RTURN(19)
     *          SCORE_DRIVING_STYLE_HARD_BUMP(20)
     * 
     * @param groupID
     * @param duration
     *          1 - 30 days, 2 - 3 months, 3 - 6 months, 4 - 12 months
     * @param scoreType
     *            SCORE_OVERALL = 1, SCORE_SPEEDING=2, SCORE_SEATBELT=3, SCORE_DRIVING_STYLE=4
     * @return List<Map<String, Object>>  that maps to List of ScoreTypeBreakdown objects 
     */
//    List<Map<String, Object>> getScoreBreakdownByType(Integer groupID, Integer duration, Integer scoreType) throws ProDAOException;

  //Methods currently supported on REAL back end  
    
//  DriveQMap    
    

    Map<String, Object> getDScoreByDT(Integer driverID, Integer duration);
    
    List<Map<String, Object>> getDTrendByDTC(Integer driverID, Integer duration, Integer count);
    
//    List<Map<String, Object>> getDTrendByDMC(Integer driverID, Integer mileage, Integer count);
    
//    Map<String, Object> getVScoreByVM(Integer vehicleID, Integer mileage);
    
    Map<String, Object> getVScoreByVT(Integer vehicleID, Integer duration);
    
//    List<Map<String, Object>> getVTrendByVMC(Integer vehicleID, Integer mileage, Integer count);
    
    List<Map<String, Object>> getVTrendByVTC(Integer vehicleID, Integer duration, Integer count);
    
    Map<String, Object> getGDScoreByGT(Integer groupID, Integer duration);
    
    List<Map<String, Object>> getGDTrendByGTC(Integer groupID, Integer duration, Integer metric);
    
    Map<String, Object> getGDScoreByGSE(Integer groupID, Long start, Long end);
  
// NOT USED    
//    Map<String, Object> getGVScoreByGSE(Integer groupID, Long start, Long end);
    
//  DVQMap  
    List<Map<String, Object>> getDVScoresByGT(Integer groupID, Integer duration);
    
    List<Map<String, Object>> getVDScoresByGT(Integer groupID, Integer duration);
 
    Map<String, Object> getDPctByGT(Integer groupID, Integer duration, Integer metric);
    
    List<Map<String, Object>> getDVScoresByGSE(Integer groupID, Long start, Long end);
    
    List<Map<String, Object>> getVDScoresByGSE(Integer groupID, Long start, Long end);
    
//  GQMap 
    List<Map<String, Object>> getSDScoresByGT(Integer groupID, Integer duration);    
    
    List<Map<String, Object>> getSDTrendsByGTC(Integer groupID, Integer duration, Integer metric);
    
}
