package com.inthinc.pro.dao.mock.proserver;

import java.util.ArrayList;
import java.util.LinkedList;
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
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreValueType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;

public class SiloServiceMockImpl implements SiloService
{


    private static final Logger logger = Logger.getLogger(SiloServiceMockImpl.class);

    // helper method
    private Map<String, Object>doMockLookup(Class<?> clazz, String key, Object searchValue, String emptyResultSetMsg, String methodName)
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
    public Map<String, Object> deletePerson(Integer personID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Map<String, Object> createPerson(Integer acctID, Map<String, Object> personMap) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getPerson(Integer personID) throws ProDAOException
    {
        return doMockLookup(Person.class, "personID", personID, "No person for ID: " + personID, "getPerson");

    }

    @Override
    public Map<String, Object> updatePerson(Integer personID, Map<String, Object> personMap) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    
    

    @Override
    public List<Map<String, Object>> getPersonIDsInGroupHierarchy(Integer groupID)
    {
        final List<Map<String, Object>> personIDs = new LinkedList<Map<String,Object>>();
    
        final List<Map<String, Object>> hierarchy = new SiloServiceCreator().getService().getGroupHierarchy(groupID);
        for (final Map<String, Object> map : hierarchy)
        {
            final Integer id = (Integer) map.get("groupID");
            if (id != null)
            {
                final SearchCriteria criteria = new SearchCriteria();
                criteria.addKeyValue("groupID", id);
                final List<Map<String, Object>> matches = MockData.getInstance().lookupList(Person.class, criteria);
                if (matches != null)
                    personIDs.addAll(matches);
            }
        }
    
        return personIDs;
    }


    @Override
    public Map<String, Object> getOverallScore(Integer groupID, Integer startDate, Integer endDate)
            throws ProDAOException
    {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("entityID", groupID);
        searchCriteria.addKeyValue("scoreType", ScoreType.SCORE_OVERALL);
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
//                searchCriteria.addKeyValue("scoreValueType", ScoreValueType.SCORE_SCALE_0_50);
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
        
        Group topGroup= MockData.getInstance().lookupObject(Group.class, "groupID", groupID);

        List<Driver> allDriversInGroup = getAllDriversInGroup(topGroup);
        
        int totals[] = new int[5];
        for (Driver driver : allDriversInGroup)
        {
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.addKeyValue("entityID", driver.getDriverID());
            searchCriteria.addKeyValue("scoreType", ScoreType.getScoreType(scoreType));
            searchCriteria.addKeyValueRange("date", startDate, endDate);
            List<ScoreableEntity> allScores = MockData.getInstance().retrieveObjectList(ScoreableEntity.class, searchCriteria);
            Map<String, Object> scoreMap = getAverageScore(startDate, allScores);
            
            Integer score = (Integer)scoreMap.get("score");
            int idx = (score-1)/10;
            totals[idx]++;
        }
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        int totalDrivers = allDriversInGroup.size();
        int percentTotal = 0;
        for ( int i = 0; i < 5; i++ ) 
        {
                int percent = 0;
                if (i < 4)
                {
                    percent = Math.round((float)((float)totals[i]*100f)/(float)totalDrivers);
                    percentTotal += percent;
                }
                else
                {
                    percent = 100 - percentTotal;
                }
                returnList.add(MockData.createMapFromObject(new ScoreableEntity(groupID, 
                    EntityType.ENTITY_GROUP, 
                    (i+1) + "",     // name will be 1 to 5 for the 5 different score breakdowns 
                    new Integer(percent), 
                    startDate,
                    ScoreType.getScoreType(scoreType),
                    ScoreValueType.SCORE_PERCENTAGE)));
        }

        
        return returnList;
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
    public List<Map<String, Object>> getVehiclesInGroupHierarchy(Integer groupID)
    {
        final List<Map<String, Object>> vehicles = new LinkedList<Map<String,Object>>();

        final List<Map<String, Object>> hierarchy = new SiloServiceCreator().getService().getGroupHierarchy(groupID);
        for (final Map<String, Object> map : hierarchy)
        {
            final Integer id = (Integer) map.get("groupID");
            if (id != null)
            {
                final SearchCriteria criteria = new SearchCriteria();
                criteria.addKeyValue("groupID", id);
                final List<Map<String, Object>> matches = MockData.getInstance().lookupList(Vehicle.class, criteria);
                if (matches != null)
                    vehicles.addAll(matches);
            }
        }

        return vehicles;
    }

    @Override
    public Map<String, Object> deleteVehicle(Integer vehicleID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Map<String, Object> createVehicle(Integer acctID, Map<String, Object> vehicleMap) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getVehicle(Integer vehicleID) throws ProDAOException
    {
        return doMockLookup(Vehicle.class, "vehicleID", vehicleID, "No vehicle for ID: " + vehicleID, "getVehicle");

    }

    @Override
    public Map<String, Object> updateVehicle(Integer vehicleID, Map<String, Object> vehicleMap) throws ProDAOException
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
        if (topGroup == null)
            return new ArrayList<Map<String, Object>>();

        List<Group> hierarchyGroups = new ArrayList<Group>();
        hierarchyGroups.add(topGroup);
        
        // filter out just the ones in the hierarchy
        List<Group> allGroups = MockData.getInstance().lookupObjectList(Group.class, new Group());
        addChildren(hierarchyGroups, allGroups, groupID);
        
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        for (Group group : hierarchyGroups)
        {
            returnList.add(MockData.createMapFromObject(group));
        }
        
        return returnList;
    }

    
    //----------- HELPER METHODS ---------------------

    
    private Map<String, Object> getAverageScore(Integer startDate, List<ScoreableEntity> allScores)
    {
        int total = 0;
        ScoreableEntity firstEntity = allScores.get(0);
        ScoreableEntity returnEntity = new ScoreableEntity(firstEntity.getEntityID(), EntityType.ENTITY_GROUP, firstEntity.getIdentifier(), 0, startDate, firstEntity.getScoreType(), ScoreValueType.SCORE_SCALE_0_50);
        for (ScoreableEntity entity : allScores)
        {
            total += entity.getScore();
        }
        returnEntity.setScore(total/allScores.size());

        return MockData.createMapFromObject(returnEntity);
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

    // get all drivers that are under the specified group -- children, grandchildren, etc.
    private List<Driver> getAllDriversInGroup(Group topGroup)
    {
        // TODO Auto-generated method stub
        List<Group> hierarchyGroups = new ArrayList<Group>();
        hierarchyGroups.add(topGroup);
        
        // filter out just the ones in the hierarchy
        List<Group> allGroups = MockData.getInstance().lookupObjectList(Group.class, new Group());
        addChildren(hierarchyGroups, allGroups, topGroup.getGroupID());
        
        List<Driver> returnDriverList = new ArrayList<Driver>();
        for (Group group : hierarchyGroups )
        {
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.addKeyValue("groupID", group.getGroupID());
            
            List<Driver> driverList = MockData.getInstance().retrieveObjectList(Driver.class, searchCriteria);
            returnDriverList.addAll(driverList);
            
        }
        
        return returnDriverList;
    }
}
