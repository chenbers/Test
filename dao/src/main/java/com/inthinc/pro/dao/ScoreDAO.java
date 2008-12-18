package com.inthinc.pro.dao;

import java.util.List;
import java.util.Map;

import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreTypeBreakdown;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.VehicleReportItem;

public interface ScoreDAO extends GenericDAO<ScoreableEntity, Integer>
{
    /**
     * Retrieve the overall score for the specified group and date range.
     * 
     * @param groupID
     * @param duration
     *        1 -   30 days
     *        2 -   3 months
     *        3 -   6 months
     *        4 -   12 months
     * @return
     */
    ScoreableEntity getAverageScoreByType(Integer groupID, Duration duration,  ScoreType st);
    
    /**
     * Retrieve the overall score by the specified driver and mileage.
     * 
     * @param driverID
     * @param milesBack
     * @param st
     * @return
     */
    ScoreableEntity getAverageScoreByTypeAndMiles(Integer driverID, Integer milesBack, ScoreType st);
    
    /**
     * Retrieve the overall score by the specified vehicle and mileage.
     * 
     * @param vehicleID
     * @param milesBack
     * @param st
     * @return
     */
    ScoreableEntity getVehicleAverageScoreByTypeAndMiles(Integer vehicleID, Integer milesBack, ScoreType st);
    /**
     * Retrieve the list of scores for the sub groups or drivers (one level down) under the specified group.
     * 
     * @param groupID
     * @param duration
     *        1 -   30 days
     *        2 -   3 months
     *        3 -   6 months
     *        4 -   12 months
     * @param scoreType
     * @return
     */
    List<ScoreableEntity> getScores(Integer groupID, Duration duration, ScoreType scoreType);
    
    Map<Integer,List<ScoreableEntity>> getTrendScores(Integer groupID, Duration duration);


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
     * @param duration
     *        1 -   30 days
     *        2 -   3 months
     *        3 -   6 months
     *        4 -   12 months
     * @return
     */
    List<ScoreableEntity> getScoreBreakdown(Integer groupID, Duration duration, ScoreType scoreType);

    
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
     *        1 -   30 days
     *        2 -   3 months
     *        3 -   6 months
     *        4 -   12 months
     * @return List of ScoreTypeBreakdown objects 
     */
    List<ScoreTypeBreakdown> getScoreBreakdownByType(Integer groupID, Duration duration, ScoreType scoreType);

    /**
     * Retrieve the driver scores and mileage.
     * 
     * @param driverID
     * @param milesBack
     * @param scoreType
     */
    List<ScoreableEntity> getDriverScoreHistoryByMiles(Integer driverID, Integer milesBack, ScoreType scoreType);
    
    /**
     * Retrieve the vehicle scores and mileage.
     * 
     * @param driverID
     * @param milesBack
     * @param scoreType
     */
    List<ScoreableEntity> getVehicleScoreHistoryByMiles(Integer vehicleID, Integer milesBack, ScoreType scoreType);


    /**
     * Retrieve the individual lines in the Vehicle Report.
     * 
     * @param groupID
     * @param duration
     */
    List<VehicleReportItem> getVehicleReportData(Integer groupID, Duration duration);

    /**
     * Retrieve the individual lines in the Driver Report.
     * 
     * @param groupID
     * @param duration
     */
    List<DriverReportItem> getDriverReportData(Integer groupID, Duration duration);

}
