package com.inthinc.pro.dao.mock.proserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.proserver.ReportService;
import com.inthinc.pro.dao.mock.data.MockData;
import com.inthinc.pro.dao.mock.data.MockQuintileMap;
import com.inthinc.pro.dao.mock.data.SearchCriteria;
import com.inthinc.pro.dao.mock.data.TempConversionUtil;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.DVQMap;
import com.inthinc.pro.model.DriveQMap;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.GQMap;
import com.inthinc.pro.model.GQVMap;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.MpgEntity;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreTypeBreakdown;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.Vehicle;

public class ReportServiceMockImpl extends AbstractServiceMockImpl implements ReportService
{
    //Cutover to real issues:
    //
    //  * Backend expects integer indicator for duration,
    //      we currently use start/end date.
    //  * Use current backend interface and embed relevant
    //      Davids methods for scores in them.
    //  * Need easy way to pick out of driveq what we want,
    //      some utility "thing".
    //  * For performance, maybe session beans for caching
    //      previously snagged data, used down in the 
    //      data access methods. A quick check there and 
    //      then on to the data store.  The data is static.
    
    private static final Logger logger = Logger.getLogger(ReportServiceMockImpl.class);
    
    @Override
    public List<Map<String, Object>> getMpgValues(Integer groupID, Integer duration) throws ProDAOException
    {
        Integer endDate = getEndDate(duration);
        Integer startDate = getStartDate(duration);
//        logger.debug("mock IMPL getOverallScores groupID = " + groupID);
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("parentID", groupID);

        // get list of groups that have the specified groupID as the parent
        List<Map<String, Object>> entityList = MockData.getInstance().lookupList(Group.class, searchCriteria);

        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        if (entityList != null)
        {
            for (Map<String, Object> groupMap : entityList) // child groups
            {
                searchCriteria = new SearchCriteria();
                searchCriteria.addKeyValue("entityID", groupMap.get("groupID"));
                searchCriteria.addKeyValueRange("date", startDate, endDate);

                MpgEntity mpg = MockData.getInstance().retrieveObject(MpgEntity.class, searchCriteria);
                if (mpg != null)
                {
                    returnList.add(TempConversionUtil.createMapFromObject(mpg));
                }
                else
                {
                    logger.error("mpg missing for groupID " + groupMap.get("groupID"));
                }
            }
        }
        else
        {
            searchCriteria = new SearchCriteria();
            searchCriteria.addKeyValue("person:groupID", groupID);

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
                searchCriteria.addKeyValueRange("date", startDate, endDate);

                MpgEntity mpg = MockData.getInstance().retrieveObject(MpgEntity.class, searchCriteria);
                if (mpg != null)
                {
                    returnList.add(TempConversionUtil.createMapFromObject(mpg));
                }
                else
                {
                    logger.error("mpg missing for driverID " + driverMap.get("driverID"));
                }
            }

        }
        return returnList;
    }

    @Override
    public Map<String, Object> getAverageScoreByType(Integer groupID, Integer duration, ScoreType st) throws ProDAOException
    {
        Integer endDate = getEndDate(duration);
        Integer startDate = getStartDate(duration);
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
    public Map<String, Object> getVehicleAverageScoreByTypeAndMiles(Integer vehicleID, Integer milesBack, ScoreType scoreType) throws ProDAOException
    {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("entityID", vehicleID);
        searchCriteria.addKeyValue("scoreType", scoreType);

        // get all scores of the time period and average them
        List<ScoreableEntity> allScores = MockData.getInstance().retrieveObjectList(ScoreableEntity.class, searchCriteria);
        if (allScores.size() == 0)
        {
            throw new EmptyResultSetException("No overall score for: " + vehicleID, "getOverallScore", 0);
        }

        return getAverageScore(milesBack, allScores);
    }

    @Override
    public List<Map<String, Object>> getScores(Integer groupID, Integer duration, Integer scoreType) throws ProDAOException
    {
        Integer endDate = getEndDate(duration);
        Integer startDate = getStartDate(duration);
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
            searchCriteria.addKeyValue("person:groupID", groupID);

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
    public List<Map<String, Object>> getScoreBreakdown(Integer groupID, Integer duration, Integer scoreType) throws ProDAOException
    {

        Integer endDate = getEndDate(duration);
        Integer startDate = getStartDate(duration);
        Group topGroup = MockData.getInstance().lookupObject(Group.class, "groupID", groupID);

        List<Driver> allDriversInGroup = getAllDriversInGroup(topGroup);

        int totals[] = new int[5];
        totals = getScoreCategoryTotals(totals, startDate, endDate, ScoreType.valueOf(scoreType), allDriversInGroup);
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
        try
        {
            List<Map<String, Object>> returnList = getScores(groupID, Duration.DAYS.getCode(), ScoreType.SCORE_OVERALL.getCode());
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
        List<Map<String, Object>> returnList = getScores(groupID, Duration.DAYS.getCode(), ScoreType.SCORE_OVERALL.getCode());
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
        Integer currentOdometer = 49923;
        Integer numScoreRecords = 10;

        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();

        for (int i = numScoreRecords; i > 0; i--)
        {
            ScoreableEntity se = new ScoreableEntity();
        
        	
            Integer temp = milesBack > currentOdometer ? 0 : currentOdometer - (milesBack / i);
        	se.setIdentifier(temp.toString() + "mi");
      
            se.setScore((int) (Math.random() * ((50 - 0) + 1)) + 0);

            returnList.add(TempConversionUtil.createMapFromObject(se));
        }

        return returnList;
    }
    
    @Override
    public List<Map<String, Object>> getVehicleScoreHistoryByMiles(Integer vehicleID, Integer milesBack, Integer scoreType) throws ProDAOException
    {
        Integer currentOdometer = 49923;
        Integer numScoreRecords = 10;

        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();

        for (int i = numScoreRecords; i > 0; i--)
        {
            ScoreableEntity se = new ScoreableEntity();
        
            
            Integer temp = milesBack > currentOdometer ? 0 : currentOdometer - (milesBack / i);
            se.setIdentifier(temp.toString() + "mi");
      
            se.setScore((int) (Math.random() * ((50 - 0) + 1)) + 0);

            returnList.add(TempConversionUtil.createMapFromObject(se));
        }

        return returnList;
    }

    static Map<String, List<Map<String, Object>> >scoreBreakdownByTypeCache = new HashMap<String, List<Map<String, Object>> >();

    static Map<String, ScoreTypeBreakdown> scoreBreakdownCache = new HashMap<String, ScoreTypeBreakdown >();

    
    @Override
    public List<Map<String, Object>> getScoreBreakdownByType(Integer groupID, Integer duration, Integer scoreType) throws ProDAOException
    {
        String cacheKey = groupID + "_" + duration + "_" + scoreType;
        if (scoreBreakdownByTypeCache.get(cacheKey) != null)
        {
            System.out.println("found in cache");        
            
            return scoreBreakdownByTypeCache.get(cacheKey);
        }
        
        Integer endDate = getEndDate(duration);
        Integer startDate = getStartDate(duration);
        
        Group topGroup = MockData.getInstance().lookupObject(Group.class, "groupID", groupID);
        List<Driver> allDriversInGroup = getAllDriversInGroup(topGroup);
        
        List<ScoreType> scoreTypeList = ScoreType.valueOf(scoreType).getSubTypes();
        
        List<ScoreTypeBreakdown> scoreTypeBreakdownList = new ArrayList<ScoreTypeBreakdown>();
        
        for (ScoreType subType : scoreTypeList)
        {
            ScoreTypeBreakdown scoreTypeBreakdown = scoreBreakdownCache.get(cacheKey+ "_" + subType);
            if (scoreTypeBreakdown  == null)
                scoreTypeBreakdown = new ScoreTypeBreakdown();
            else
            {
                scoreTypeBreakdownList.add(scoreTypeBreakdown);
                continue;
            }
            scoreTypeBreakdown.setScoreType(subType);
            List<ScoreableEntity> scoreList = new ArrayList<ScoreableEntity>();
            scoreTypeBreakdown.setPercentageList(scoreList);
            scoreTypeBreakdownList.add(scoreTypeBreakdown);
            
            int totals[] = new int[5];
            totals = getScoreCategoryTotals(totals, startDate, endDate, subType, allDriversInGroup);
            int scoreCnt = 0;
            for (int i = 0; i < 5; i++)
                scoreCnt += totals[i];
            computePercentages(groupID, startDate, subType.getCode(), scoreList, totals, scoreCnt);
            scoreBreakdownCache.put(cacheKey+"_subType", scoreTypeBreakdown);
        }

        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        for (ScoreTypeBreakdown scoreTypeBreakdown : scoreTypeBreakdownList)
        {
            returnList.add(TempConversionUtil.createMapFromObject(scoreTypeBreakdown));
        }
        
        scoreBreakdownByTypeCache.put(cacheKey, returnList);
        return returnList;
    }
    
    private Integer getEndDate(Integer duration)
    {
        return MockData.getInstance().dateNow;
    }
    private Integer getStartDate(Integer duration)
    {
        Integer endDate = MockData.getInstance().dateNow;
        Integer startDate = DateUtil.getDaysBackDate(endDate, Duration.valueOf(duration).getNumberOfDays());
        return startDate;
    }
//Methods currently supported on REAL back end   
    
    @Override
    public Map<String, Object> getDScoreByDM(Integer driverID, Integer mileage)
    {       
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("driver:driverID", driverID);

        DVQMap dvq = MockData.getInstance().retrieveObject(DVQMap.class, searchCriteria);
        if (dvq == null)
            throw new EmptyResultSetException("No score for driver: " + driverID, "getDScoreByDM", 0);
        return TempConversionUtil.createMapFromObject(dvq.getDriveQ());
    } 
    
    @Override
    public Map<String, Object> getDScoreByDT(Integer driverID, Integer duration)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public List<Map<String, Object>> getDTrendByDTC(Integer driverID, Integer duration, Integer count)
    {
        // TODO Auto-generated method stub
        return null;
    } 
    
    @Override
    public List<Map<String, Object>> getDTrendByDMC(Integer driverID, Integer mileage, Integer count)
    {
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("driver:driverID", driverID);

        List<DVQMap> dvqList = MockData.getInstance().retrieveObjectList(DVQMap.class, searchCriteria);

        if (dvqList == null)
            throw new EmptyResultSetException("No score for driver: " + driverID, "getDTrendByDMC", 0);
        
        int start = dvqList.size() - count;
        int i = 0;
        for (DVQMap dvq : dvqList)
        {    
            if (i++ < start)
                continue;
            returnList.add(TempConversionUtil.createMapFromObject(dvq.getDriveQ()));
        }
        
        return returnList;
    }
    
    @Override
    public Map<String, Object> getVScoreByVM(Integer vehicleID, Integer mileage)
    {
        // TODO Auto-generated method stub
        return null;
    }  
    
    @Override
    public Map<String, Object> getVScoreByVT(Integer vehicleID, Integer duration)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public List<Map<String, Object>> getVTrendByVMC(Integer vehicleID, Integer mileage, Integer count)
    {
        // TODO Auto-generated method stub
        return null;
    } 
    
    @Override
    public List<Map<String, Object>> getVTrendByVTC(Integer vehicleID, Integer duration, Integer count)
    {
        // TODO Auto-generated method stub
        return null;
    }  
    
    @Override
    public Map<String, Object> getGDScoreByGT(Integer groupID, Integer duration)
    {
        List<DVQMap> entityList = new ArrayList<DVQMap>();
        
        
        Group topGroup = MockData.getInstance().lookupObject(Group.class, "groupID", groupID);
        List<Group> groupList = getGroupHierarchy(topGroup);
        
        for (Group group : groupList)
        {
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.addKeyValue("driver:person:groupID", group.getGroupID());
    
            // get list of drivers that are in the specified group
            List<DVQMap> groupDVQList = MockData.getInstance().retrieveObjectList(DVQMap.class, searchCriteria);
            if (groupDVQList == null)
            {
                continue;
            }
            for (DVQMap dvq : groupDVQList)
            {
                // TODO:
                // for now just return the scores for 1st driver in list, should actually average all scores 
                return TempConversionUtil.createMapFromObject(dvq.getDriveQ());
            }
        }
        throw new EmptyResultSetException("No scores for group: " + groupID, "getGDScoreByGT", 0);
    }  
    
    @Override
    public Map<String, Object> getGDTrendByGTC(Integer groupID, Integer duration)
    {
        // TODO Auto-generated method stub
        return null;
    } 
    
    public List<Map<String, Object>> getDVScoresByGT(Integer groupID, Integer duration)
    {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("person:groupID", groupID);

        // get list of drivers that are in the specified group
        List<Map<String, Object>> entityList = MockData.getInstance().lookupList(Driver.class, searchCriteria);

        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();

        if (entityList == null)
        {
            return returnList;
        }
        for (Map<String, Object> driverMap : entityList)
        {
            searchCriteria = new SearchCriteria();
            searchCriteria.addKeyValue("driver:driverID", driverMap.get("driverID"));
    
            DVQMap dvq = MockData.getInstance().retrieveObject(DVQMap.class, searchCriteria);
            returnList.add(TempConversionUtil.createMapFromObject(dvq));
        }
        return returnList;
    } 
    
    public List<Map<String, Object>> getVDScoresByGT(Integer groupID, Integer duration)
    {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("person:groupID", groupID);

        // get list of vehicles that are in the specified group
        List<Map<String, Object>> entityList = MockData.getInstance().lookupList(Vehicle.class, searchCriteria);

        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();

        if (entityList == null)
        {
            return returnList;
        }
        for (Map<String, Object> vehicleMap : entityList)
        {
            searchCriteria = new SearchCriteria();
            searchCriteria.addKeyValue("vehicle:vehicleID", vehicleMap.get("vehicleID"));
    
            DVQMap dvq = MockData.getInstance().retrieveObject(DVQMap.class, searchCriteria);
            returnList.add(TempConversionUtil.createMapFromObject(dvq));
        }
        return returnList;
    } 
        
    public List<Map<String, Object>>  getSDScoresByGT(Integer groupID, Integer duration)
    {
        Group topGroup = MockData.getInstance().lookupObject(Group.class, "groupID", groupID);
        List<Group> groupList = getGroupHierarchy(topGroup);
        
        List<Map<String, Object>> gqMapList = new ArrayList<Map<String, Object>>();
        
        for (Group group : groupList)
        {
            if (group.getParentID().equals(groupID))
            {
                SearchCriteria searchCriteria = new SearchCriteria();
                searchCriteria.addKeyValue("group:groupID", group.getGroupID());
        
                // get list of drivers that are in the specified group
                GQMap gqMap = MockData.getInstance().retrieveObject(GQMap.class, searchCriteria);
                if (gqMap == null)
                {
                    continue;
                }
                gqMapList.add(TempConversionUtil.createMapFromObject(gqMap));
            }
        }
        return gqMapList;
    } 
    
    public List<Map<String, Object>>  getSDTrendsByGTC(Integer groupID, Integer duration, Integer metric)
    {
        Group topGroup = MockData.getInstance().lookupObject(Group.class, "groupID", groupID);
        List<Group> groupList = getGroupHierarchy(topGroup);
        
        List<Map<String, Object>> gqMapList = new ArrayList<Map<String, Object>>();
        
        for (Group group : groupList)
        {
            if (group.getParentID().equals(groupID))
            {
                SearchCriteria searchCriteria = new SearchCriteria();
                searchCriteria.addKeyValue("group:groupID", group.getGroupID());
        
                // get list of drivers that are in the specified group
                GQMap gqMap = MockData.getInstance().retrieveObject(GQMap.class, searchCriteria);
                if (gqMap == null)
                {
                    continue;
                }
                GQVMap gqvMap = new GQVMap();
                gqvMap.setGroup(group);
                List<DriveQMap> driveQVList = new ArrayList<DriveQMap>();
                for (int i = 0; i < 3; i++)
                    driveQVList.add(gqMap.getDriveQ());
                gqvMap.setDriveQV(driveQVList);
                gqMapList.add(TempConversionUtil.createMapFromObject(gqvMap));
            }
        }
        return gqMapList;
    }  
    
//  Miscellaneous    
    public Map<String, Object> getDPctByGT(Integer groupID, Integer duration, Integer metric)
    {
        SearchCriteria searchCriteria;
        searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("groupID", groupID);
        searchCriteria.addKeyValue("driveQMetric", metric);
        searchCriteria.addKeyValue("duration", duration);

        MockQuintileMap mockQuintileMap = MockData.getInstance().retrieveObject(MockQuintileMap.class, searchCriteria);
        if (mockQuintileMap != null)
        {
            return TempConversionUtil.createMapFromObject(mockQuintileMap.getQuintileMap(), false);
        }
        throw new EmptyResultSetException("No scores for group: " + groupID, "getDPctByGT", 0);
    }




    
}
