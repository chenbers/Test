package com.inthinc.pro.dao.mock.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.mock.data.SearchCriteria;
import com.inthinc.pro.dao.service.SiloService;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.User;

public class SiloServiceMockImpl extends MockImpl implements SiloService
{
    private static final Logger logger = Logger.getLogger(SiloServiceMockImpl.class);
    
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
        Map<String, Object> returnMap =  getMockDataContainer().lookup(User.class, "userID", userID);

        if (returnMap == null)
        {
            throw new EmptyResultSetException("No user for ID: " + userID, "getUserIDByEmail", 0);
        }
        return returnMap;

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
        searchCriteria.addKeyValueRange("date", startDate, endDate);
        
        Map<String, Object> returnMap =  getMockDataContainer().lookup(ScoreableEntity.class, searchCriteria);
        if (returnMap == null)
        {
            throw new EmptyResultSetException("No overall score for: " + groupID, "getOverallScore", 0);
        }
        return returnMap;
    }

    @Override
    public List<Map<String, Object>> getOverallScores(Integer groupID, Integer startDate, Integer endDate)
            throws ProDAOException
    {
        logger.debug("mock IMPL getOverallScores groupID = " + groupID);
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("parentID", groupID);

        // get list of groups that have the specified groupID as the parent
        List<Map<String, Object>> entityList =  getMockDataContainer().lookupList(Group.class, searchCriteria);
        
        List<Map<String, Object>> returnList =  new ArrayList<Map<String, Object>>();
        
        for (Map<String, Object> groupMap : entityList)
        {
            searchCriteria = new SearchCriteria();
            searchCriteria.addKeyValue("entityID", groupMap.get("groupID"));
            searchCriteria.addKeyValueRange("date", startDate, endDate);
            
            Map<String, Object> scoreMap = getMockDataContainer().lookup(ScoreableEntity.class, searchCriteria);
            
            if (scoreMap != null)
            {
                returnList.add(scoreMap);
            }
            else
            {
                logger.error("score missing for groupID " + groupMap.get("groupID"));
            }
        }
        return returnList;
    }

    @Override
    public List<Map<String, Object>> getVehiclesByAcctID(Integer acctID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }


    

}
