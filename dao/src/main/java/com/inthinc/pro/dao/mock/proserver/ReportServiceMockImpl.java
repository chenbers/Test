package com.inthinc.pro.dao.mock.proserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.proserver.ReportService;
import com.inthinc.pro.dao.mock.data.MockData;
import com.inthinc.pro.dao.mock.data.SearchCriteria;
import com.inthinc.pro.dao.mock.data.TempConversionUtil;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreTypeBreakdown;
import com.inthinc.pro.model.ScoreableEntity;

public class ReportServiceMockImpl extends AbstractServiceMockImpl implements ReportService
{
    private static final Logger logger = Logger.getLogger(ReportServiceMockImpl.class);

    @Override
    public Map<String, Object> getAverageScoreByType(Integer groupID, Integer startDate, Integer endDate, ScoreType st) throws ProDAOException
    {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("entityID", groupID);
        searchCriteria.addKeyValue("scoreType", st);
        searchCriteria.addKeyValueRange("date", startDate, endDate);

        // get all scores of the time period and average them
        List<ScoreableEntity> allScores = MockData.getInstance().retrieveObjectList(ScoreableEntity.class, searchCriteria);
        if (allScores.size() == 0)
        {
            throw new EmptyResultSetException("No overall score for: " + groupID, "getOverallScore", 0);
        }

        return getAverageScore(startDate, allScores);

    }

    @Override
    public Map<String, Object> getAverageScoreByTypeAndMiles(Integer driverID, Integer milesBack, ScoreType st) throws ProDAOException
    {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("entityID", driverID);
        searchCriteria.addKeyValue("scoreType", st);

        // get all scores of the time period and average them
        List<ScoreableEntity> allScores = MockData.getInstance().retrieveObjectList(ScoreableEntity.class, searchCriteria);
        if (allScores.size() == 0)
        {
            throw new EmptyResultSetException("No overall score for: " + driverID, "getOverallScore", 0);
        }

        return getAverageScore(milesBack, allScores);

    }

    @Override
    public List<Map<String, Object>> getScores(Integer groupID, Integer startDate, Integer endDate, Integer scoreType) throws ProDAOException
    {
//        logger.debug("mock IMPL getOverallScores groupID = " + groupID);
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("parentID", groupID);

        // get list of groups that have the specified groupID as the parent
        List<Map<String, Object>> entityList = MockData.getInstance().lookupList(Group.class, searchCriteria);

        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        if (entityList != null)
        {
            for (Map<String, Object> groupMap : entityList)
            {
                searchCriteria = new SearchCriteria();
                searchCriteria.addKeyValue("entityID", groupMap.get("groupID"));
                searchCriteria.addKeyValue("scoreType", ScoreType.valueOf(scoreType));
                // searchCriteria.addKeyValue("scoreValueType", ScoreValueType.SCORE_SCALE_0_50);
                searchCriteria.addKeyValueRange("date", startDate, endDate);

                List<ScoreableEntity> allScores = MockData.getInstance().retrieveObjectList(ScoreableEntity.class, searchCriteria);
                if (allScores.size() > 0)
                {
                    returnList.add(getAverageScore(startDate, allScores));
                }
                else
                {
                    logger.error("score missing for groupID " + groupMap.get("groupID"));
                }
            }
        }
        else
        {
            searchCriteria = new SearchCriteria();
            searchCriteria.addKeyValue("groupID", groupID);

            // get list of drivers that are in the specified group
            entityList = MockData.getInstance().lookupList(Driver.class, searchCriteria);
            if (entityList == null)
            {
                return returnList;
            }
            for (Map<String, Object> driverMap : entityList)
            {
                searchCriteria = new SearchCriteria();
                searchCriteria.addKeyValue("entityID", driverMap.get("driverID"));
                searchCriteria.addKeyValue("scoreType", ScoreType.valueOf(scoreType));
                searchCriteria.addKeyValueRange("date", startDate, endDate);

                List<ScoreableEntity> allScores = MockData.getInstance().retrieveObjectList(ScoreableEntity.class, searchCriteria);
                if (allScores.size() > 0)
                {
                    returnList.add(getAverageScore(startDate, allScores));
                }
                else
                {
                    logger.error("score missing for driverID " + driverMap.get("driverID"));
                }
            }

        }
        return returnList;
    }

    @Override
    public List<Map<String, Object>> getScoreBreakdown(Integer groupID, Integer startDate, Integer endDate, Integer scoreType) throws ProDAOException
    {

        Group topGroup = MockData.getInstance().lookupObject(Group.class, "groupID", groupID);

        List<Driver> allDriversInGroup = getAllDriversInGroup(topGroup);

        int[] totals = getScoreCatCntsForAllDrivers(startDate, endDate, scoreType, allDriversInGroup);
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        int totalDrivers = allDriversInGroup.size();
        int percentTotal = 0;
        for (int i = 0; i < 5; i++)
        {
            int percent = 0;
            if (i < 4)
            {
                percent = Math.round((float) ((float) totals[i] * 100f) / (float) totalDrivers);
                percentTotal += percent;
            }
            else
            {
                percent = 100 - percentTotal;
            }
            returnList.add(TempConversionUtil.createMapFromObject(new ScoreableEntity(groupID, EntityType.ENTITY_GROUP, (i + 1) + "", // name will be 1 to 5 for the 5 different
                    // score breakdowns
                    new Integer(percent), startDate, ScoreType.valueOf(scoreType))));
        }

        return returnList;
    }

    @Override
    public List<Map<String, Object>> getBottomFiveScores(Integer groupID)
    {
        Integer endDate = DateUtil.getTodaysDate();
        Integer startDate = DateUtil.getDaysBackDate(endDate, 30);
        try
        {
            List<Map<String, Object>> returnList = getScores(groupID, startDate, endDate, ScoreType.SCORE_OVERALL.getCode());
            // TODO return top 5.
            if (returnList.size() > 5)
            {
                for (int i = 0; returnList.size() > 5;)
                {

                    returnList.remove(i);
                }
            }
            return returnList;
        }
        catch (ProDAOException pdaoe)
        {

            return null;
        }
    }

    @Override
    public List<Map<String, Object>> getTopFiveScores(Integer groupID)
    {
        Integer endDate = DateUtil.getTodaysDate();
        Integer startDate = DateUtil.getDaysBackDate(endDate, 30);
        List<Map<String, Object>> returnList = getScores(groupID, startDate, endDate, ScoreType.SCORE_OVERALL.getCode());
        // TODO return top 5.
        if (returnList.size() > 5)
        {
            for (int i = 5; returnList.size() > 5;)
            {

                returnList.remove(i);
            }
        }
        return returnList;
    }

    @Override
    public List<Map<String, Object>> getDriverScoreHistoryByMiles(Integer driverID, Integer milesBack, Integer scoreType) throws ProDAOException
    {
        Integer currentOdometer = 9923;
        Integer numScoreRecords = 10;

        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < numScoreRecords; i++)
        {
            ScoreableEntity se = new ScoreableEntity();

            if (i == numScoreRecords) // No room for label on last data point. Place it next to last.
                se.setIdentifier(currentOdometer.toString() + "mi"); // Mileage at time the score was calculated.

            if (i == 0)
            {
                Integer temp = milesBack > currentOdometer ? 0 : currentOdometer - milesBack;
                se.setIdentifier(temp.toString() + "mi");
            }
            else
                se.setIdentifier("");

            se.setScore((int) (Math.random() * ((50 - 0) + 1)) + 0);

            returnList.add(TempConversionUtil.createMapFromObject(se));
        }

        return returnList;
    }

    private Map<String, Object> getAverageScore(Integer startDate, List<ScoreableEntity> allScores)
    {
        int total = 0;
        ScoreableEntity firstEntity = allScores.get(0);
        ScoreableEntity returnEntity = new ScoreableEntity(firstEntity.getEntityID(), EntityType.ENTITY_GROUP, firstEntity.getIdentifier(), 0, startDate, firstEntity
                .getScoreType());
        for (ScoreableEntity entity : allScores)
        {
            total += entity.getScore();
        }
        returnEntity.setScore(total / allScores.size());

        return TempConversionUtil.createMapFromObject(returnEntity);
    }

    @Override
    public List<Map<String, Object>> getScoreBreakdownByType(Integer groupID, Integer startDate, Integer endDate, Integer scoreType) throws ProDAOException
    {
        Group topGroup = MockData.getInstance().lookupObject(Group.class, "groupID", groupID);
        List<Driver> allDriversInGroup = getAllDriversInGroup(topGroup);
        
        List<ScoreType> scoreTypeList = ScoreType.valueOf(scoreType).getSubTypes();
        
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        for (ScoreType subType : scoreTypeList)
        {
            ScoreTypeBreakdown scoreTypeBreakdown = new ScoreTypeBreakdown();
            scoreTypeBreakdown.setScoreType(subType);
            List<ScoreableEntity> scoreList = new ArrayList<ScoreableEntity>();
            scoreTypeBreakdown.setPercentageList(scoreList);
            
            int[] totals = getScoreCatCntsForAllDrivers(startDate, endDate, scoreType, allDriversInGroup);
            int totalDrivers = allDriversInGroup.size();
            int percentTotal = 0;
            for (int i = 0; i < 5; i++)
            {
                int percent = 0;
                if (i < 4)
                {
                    percent = Math.round((float) ((float) totals[i] * 100f) / (float) totalDrivers);
                    percentTotal += percent;
                }
                else
                {
                    percent = 100 - percentTotal;
                }
                ScoreableEntity scoreableEntity = new ScoreableEntity(groupID, EntityType.ENTITY_GROUP, 
                            (i + 1) + "", // name will be 1 to 5 for the 5 different score breakdowns
                        new Integer(percent), startDate, ScoreType.valueOf(scoreType));
                scoreList.add(scoreableEntity);
            }
            
            returnList.add(TempConversionUtil.createMapFromObject(scoreTypeBreakdown));
            
        }

        return returnList;
    }

    private int[] getScoreCatCntsForAllDrivers(Integer startDate, Integer endDate, Integer scoreType, List<Driver> allDriversInGroup)
    {
        int totals[] = new int[5];
        for (Driver driver : allDriversInGroup)
        {
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.addKeyValue("entityID", driver.getDriverID());
            searchCriteria.addKeyValue("scoreType", ScoreType.valueOf(scoreType));
            searchCriteria.addKeyValueRange("date", startDate, endDate);
            List<ScoreableEntity> allScores = MockData.getInstance().retrieveObjectList(ScoreableEntity.class, searchCriteria);
            Map<String, Object> scoreMap = getAverageScore(startDate, allScores);

            Integer score = (Integer) scoreMap.get("score");
            int idx = (score - 1) / 10;
            totals[idx]++;
        }
        return totals;
    }


}
