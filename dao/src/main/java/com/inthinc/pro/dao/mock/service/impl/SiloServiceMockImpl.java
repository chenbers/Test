package com.inthinc.pro.dao.mock.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.mock.data.MockData;
import com.inthinc.pro.dao.mock.data.SearchCriteria;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreValueType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.User;

public class SiloServiceMockImpl implements SiloService
{


    private static final Logger logger = Logger.getLogger(SiloServiceMockImpl.class);

    // helper method
    private Map<String, Object>doMockLookup(Class clazz, String key, Object searchValue, String emptyResultSetMsg, String methodName)
    {
        Map<String, Object> returnMap =  MockData.getInstance().lookup(clazz, key, searchValue);

        if (returnMap == null)
        {
            throw new EmptyResultSetException(emptyResultSetMsg, methodName, 0);
        }
        return returnMap;
        
    }

    
    @Override
    public Map<String, Object> deleteUser(Integer userID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Map<String, Object> createUser(Integer acctID, Map<String, Object> userMap) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getUser(Integer userID) throws ProDAOException
    {
        return doMockLookup(User.class, "userID", userID, "No user for ID: " + userID, "getUser");

    }

    @Override
    public Map<String, Object> updateUser(Integer userID, Map<String, Object> userMap) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    
    

    @Override
    public Map<String, Object> getOverallScore(Integer groupID, Integer startDate, Integer endDate)
            throws ProDAOException
    {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("entityID", groupID);
        searchCriteria.addKeyValue("scoreType", ScoreType.SCORE_OVERALL);
        searchCriteria.addKeyValue("scoreValueType", ScoreValueType.SCORE_SCALE_0_50);
        searchCriteria.addKeyValueRange("date", startDate, endDate);
        
        Map<String, Object> returnMap =  MockData.getInstance().lookup(ScoreableEntity.class, searchCriteria);
        if (returnMap == null)
        {
            throw new EmptyResultSetException("No overall score for: " + groupID, "getOverallScore", 0);
        }
        return returnMap;
    }

    @Override
    public List<Map<String, Object>> getScores(Integer groupID, Integer startDate, Integer endDate, Integer scoreType)
            throws ProDAOException
    {
        logger.debug("mock IMPL getOverallScores groupID = " + groupID);
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("parentID", groupID);

        // get list of groups that have the specified groupID as the parent
        List<Map<String, Object>> entityList =  MockData.getInstance().lookupList(Group.class, searchCriteria);
        
        List<Map<String, Object>> returnList =  new ArrayList<Map<String, Object>>();
        if (entityList != null)
        {
            for (Map<String, Object> groupMap : entityList)
            {
                searchCriteria = new SearchCriteria();
                searchCriteria.addKeyValue("entityID", groupMap.get("groupID"));
                searchCriteria.addKeyValue("scoreType", ScoreType.getScoreType(scoreType));
                searchCriteria.addKeyValue("scoreValueType", ScoreValueType.SCORE_SCALE_0_50);
                searchCriteria.addKeyValueRange("date", startDate, endDate);
                
                Map<String, Object> scoreMap = MockData.getInstance().lookup(ScoreableEntity.class, searchCriteria);
                
                if (scoreMap != null)
                {
                    returnList.add(scoreMap);
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
            entityList =  MockData.getInstance().lookupList(Driver.class, searchCriteria);
            if (entityList == null)
            {
                return returnList;
            }
            for (Map<String, Object> driverMap : entityList)
            {
                searchCriteria = new SearchCriteria();
                searchCriteria.addKeyValue("entityID", driverMap.get("driverID"));
                searchCriteria.addKeyValue("scoreType", ScoreType.getScoreType(scoreType));
                searchCriteria.addKeyValueRange("date", startDate, endDate);
                searchCriteria.addKeyValue("scoreValueType", ScoreValueType.SCORE_SCALE_0_50);
                
                Map<String, Object> scoreMap = MockData.getInstance().lookup(ScoreableEntity.class, searchCriteria);
                
                if (scoreMap != null)
                {
                    returnList.add(scoreMap);
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
        
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("entityID", groupID);
        searchCriteria.addKeyValue("scoreType", ScoreType.getScoreType(scoreType));
        searchCriteria.addKeyValueRange("date", startDate, endDate);
        searchCriteria.addKeyValue("scoreValueType", ScoreValueType.SCORE_PERCENTAGE);
        return MockData.getInstance().lookupList(ScoreableEntity.class, searchCriteria);
    }

    @Override
    public List<Map<String, Object>> getBottomFiveScores(Integer groupID)
    {
        logger.debug("mock IMPL getBottomFiveScores groupID = " + groupID);
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("parentID", groupID);

        // get list of drivers that have the specified groupID as the parent
        List<Map<String, Object>> entityList;
        
        List<Map<String, Object>> returnList =  new ArrayList<Map<String, Object>>();

        searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("groupID", groupID);

        // get list of drivers that are in the specified group
        entityList =  MockData.getInstance().lookupList(Driver.class, searchCriteria);
        if (entityList == null)
        {
            return returnList;
        }
        Integer endDate = DateUtil.getTodaysDate();
        Integer startDate = DateUtil.getDaysBackDate(endDate, 30);
        
        for (Map<String, Object> driverMap : entityList)
        {
            searchCriteria = new SearchCriteria();
            searchCriteria.addKeyValue("entityID", driverMap.get("driverID"));
            searchCriteria.addKeyValue("scoreType", ScoreType.SCORE_OVERALL);
            searchCriteria.addKeyValueRange("date", startDate, endDate);
            

            searchCriteria.addKeyValueRange("date", startDate, endDate);
            
            Map<String, Object> scoreMap = MockData.getInstance().lookup(ScoreableEntity.class, searchCriteria);
            
            if (scoreMap != null)
            {
                returnList.add(scoreMap);
            }
            else
            {
                logger.error("score missing for driverID " + driverMap.get("driverID"));
            }
        }
        //TODO sort in score order and return bottom 5 and refactor to eliminate duplicate code.   
        return returnList;
    }


    @Override
    public List<Map<String, Object>> getTopFiveScores(Integer groupID)
    {
        logger.debug("mock IMPL getBottomFiveScores groupID = " + groupID);
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("parentID", groupID);

        // get list of drivers that have the specified groupID as the parent
        List<Map<String, Object>> entityList;
        
        List<Map<String, Object>> returnList =  new ArrayList<Map<String, Object>>();

        searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("groupID", groupID);

        // get list of drivers that are in the specified group
        entityList =  MockData.getInstance().lookupList(Driver.class, searchCriteria);
        if (entityList == null)
        {
            return returnList;
        }
        Integer endDate = DateUtil.getTodaysDate();
        Integer startDate = DateUtil.getDaysBackDate(endDate, 30);
        
        for (Map<String, Object> driverMap : entityList)
        {
            searchCriteria = new SearchCriteria();
            searchCriteria.addKeyValue("entityID", driverMap.get("driverID"));
            searchCriteria.addKeyValue("scoreType", ScoreType.SCORE_OVERALL);
            searchCriteria.addKeyValueRange("date", startDate, endDate);
           

            searchCriteria.addKeyValueRange("date", startDate, endDate);
            
            Map<String, Object> scoreMap = MockData.getInstance().lookup(ScoreableEntity.class, searchCriteria);
            
            if (scoreMap != null)
            {
                returnList.add(scoreMap);
            }
            else
            {
                logger.error("score missing for driverID " + driverMap.get("driverID"));
            }
        }
        //TODO sort list in score order and return top 5  and refactor to eliminate duplicate code.   
        return returnList;
    }

    @Override
    public List<Map<String, Object>> getVehiclesByAcctID(Integer acctID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> createGroup(Integer acctID, Map<String, Object> groupMap) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> deleteGroup(Integer groupID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getGroup(Integer groupID) throws ProDAOException
    {
        return doMockLookup(Group.class, "groupID", groupID, "No group for ID: " + groupID, "getGroup");
    }

    @Override
    public Map<String, Object> updateGroup(Integer groupID, Map<String, Object> groupMap) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public List<Map<String, Object>> getGroupHierarchy(Integer groupID) throws ProDAOException
    {
        Group topGroup= MockData.getInstance().lookupObject(Group.class, "groupID", groupID);

        List<Group> hierarchyGroups = new ArrayList<Group>();
        hierarchyGroups.add(topGroup);
        
        // filter out just the ones in the hierarchy
        List<Group> allGroups = MockData.getInstance().lookupObjectList(Group.class, new Group());
        addChildren(hierarchyGroups, allGroups, groupID);
        
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        for (Group group : hierarchyGroups)
        {
            returnList.add(MockData.getInstance().createMapFromObject(group));
        }
        
        return returnList;
    }


    private void addChildren(List<Group> hierarchyGroups, List<Group> allGroups, Integer groupID)
    {
        for (Group group : allGroups)
        {
            if (group.getParentID().equals(groupID))
            { 
                hierarchyGroups.add(group);
                addChildren(hierarchyGroups, allGroups, group.getGroupID());
            }
        }
        
    }




    

}
