package com.inthinc.pro.dao;

import java.util.List;
import java.util.Map;

import com.inthinc.pro.model.CrashSummary;
import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.DriverScore;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.IdlePercentItem;
import com.inthinc.pro.model.ScoreItem;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreTypeBreakdown;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.SpeedPercentItem;
import com.inthinc.pro.model.TrendItem;
import com.inthinc.pro.model.VehicleReportItem;

/**
 * @author cjennings
 *
 */
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
    ScoreableEntity getAverageScoreByType(Integer groupID, Duration duration,  ScoreType st, GroupHierarchy gh);

    ScoreableEntity getSummaryScore(Integer groupID, Duration duration,  ScoreType st, GroupHierarchy gh);
    
    
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
    List<ScoreableEntity> getScores(Integer groupID, Duration duration, ScoreType scoreType, GroupHierarchy gh);
    
    ScoreableEntity getTrendSummaryScore(Integer groupID, Duration duration, ScoreType scoreType, GroupHierarchy gh);
    
    Map<Integer,List<ScoreableEntity>> getTrendScores(Integer groupID, Duration duration, GroupHierarchy gh);


    
    /**
     * Retrieve the list of overall scores for drivers in group based on duration.
     * 
     * @param groupID
     * @return
     */
    List<DriverScore> getSortedDriverScoreList(Integer groupID, Duration duration, GroupHierarchy gh);

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
    List<ScoreableEntity> getScoreBreakdown(Integer groupID, Duration duration, ScoreType scoreType, GroupHierarchy gh);

    
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
    List<ScoreTypeBreakdown> getScoreBreakdownByType(Integer groupID, Duration duration, ScoreType scoreType, GroupHierarchy gh);

    
    /**
     * Retrieve the individual lines in the Vehicle Report.
     * 
     * @param groupID
     * @param duration
     */
     List<VehicleReportItem> getVehicleReportData(Integer groupID, Duration duration, Map<Integer, Group> groupMap);

    /**
     * Retrieve the individual lines in the Driver Report.
     * 
     * @param groupID
     * @param duration
     */
     List<DriverReportItem> getDriverReportData(Integer groupID, Duration duration, Map<Integer, Group> groupMap);

    
    CrashSummary getGroupCrashSummaryData(Integer groupID, GroupHierarchy gh);
    CrashSummary getDriverCrashSummaryData(Integer driverID);
    CrashSummary getVehicleCrashSummaryData(Integer vehicleID);

    
    /**
     * @param groupID
     * @param duration
     * @return
     */
    List<SpeedPercentItem> getSpeedPercentItems(Integer groupID, Duration duration, GroupHierarchy gh);
    
    /**
     * @param groupID
     * @param duration
     * @return
     */
    List<IdlePercentItem> getIdlePercentItems(Integer groupID, Duration duration, GroupHierarchy gh);

    
    /**
     * @param id
     * 			driver or vehicle id
     * @param entityType
     * 			type of entity -- driver or vehicle
     * @param duration
     * 			duration (30 days, 3 m, 6 m, 12 m
     * @return
     */
     List<TrendItem> getTrendCumulative(Integer id, EntityType entityType, Duration duration);
    
    
    /**
     * @param id
     * 			driver or vehicle id
     * @param entityType
     * 			type of entity -- driver or vehicle
     * @param duration
     * 			duration (30 days, 3 m, 6 m, 12 m
     * @return
     */
    List<TrendItem> getTrendScores(Integer id, EntityType entityType, Duration duration);
    
    
    /**
     * @param id
     * 			driver or vehicle id
     * @param entityType
     * 			type of entity -- driver or vehicle
     * @param duration
     * 			duration (30 days, 3 m, 6 m, 12 m
     * @return
     */
    List<ScoreItem> getAverageScores(Integer id, EntityType entityType, Duration duration);

}
