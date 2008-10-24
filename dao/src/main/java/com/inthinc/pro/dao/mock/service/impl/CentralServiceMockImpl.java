package com.inthinc.pro.dao.mock.service.impl;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.hessian.GraphicHessianDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.mock.data.MockData;
import com.inthinc.pro.dao.mock.data.MockDataContainer;
import com.inthinc.pro.dao.mock.data.SearchCriteria;
import com.inthinc.pro.dao.service.CentralService;
import com.inthinc.pro.model.OverallScore;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.User;

public class CentralServiceMockImpl extends MockImpl implements CentralService
{
	private static final Logger logger = Logger.getLogger(CentralServiceMockImpl.class);

    @Override
    public Map<String, Object> getUserByAccountID(Integer accountID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getUserIDByEmail(String email) throws ProDAOException
    {
        Map<String, Object> returnMap =  mockDataContainer.lookup(User.class, "email", email);

        if (returnMap == null)
        {
            throw new EmptyResultSetException("No user for email: " + email, "getUserIDByEmail", 0);
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> getUserIDByName(String username) throws ProDAOException
    {
        Map<String, Object> returnMap =  mockDataContainer.lookup(User.class, "username", username);
        
        if (returnMap == null)
        {
            throw new EmptyResultSetException("No user for username: " + username, "getUserIDByUsername", 0);
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> getOverallScore(Integer userID, Integer levelID, Integer startDate, Integer endDate)
            throws ProDAOException
    {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("userID", userID);
//        searchCriteria.addKeyValue("levelID", levelID);
        searchCriteria.addKeyValueRange("date", startDate, endDate);
        
        Map<String, Object> returnMap =  mockDataContainer.lookup(OverallScore.class, searchCriteria);
        if (returnMap == null)
        {
            throw new EmptyResultSetException("No overall score for: " + userID, "getOverallScore", 0);
        }
        return returnMap;
    }

    @Override
    public List<ScoreableEntity> getScores(Integer userID, Integer levelID, Integer startDate, Integer endDate)
            throws ProDAOException
    {
    	logger.debug("getting trend table");
       
        ArrayList <ScoreableEntity> scoreableEntities = new ArrayList<ScoreableEntity>();
        
        ScoreableEntity se = new ScoreableEntity();
		se.setEntityID(0);
		se.setIdentifier("New England");
		se.setScore("0.9");
		scoreableEntities.add(se);
		
		se = new ScoreableEntity();
		se.setEntityID(1);
		se.setIdentifier("South");
		se.setScore("1.8");	
		scoreableEntities.add(se);
        
		se = new ScoreableEntity();	
		se.setEntityID(2);
		se.setIdentifier("Lakes");
		se.setScore("2.9");
		scoreableEntities.add(se);
		
		se = new ScoreableEntity();	
		se.setEntityID(3);
		se.setIdentifier("Midwest");
		se.setScore("3.9");
		scoreableEntities.add(se);
		
		se = new ScoreableEntity();	
		se.setEntityID(4);
		se.setIdentifier("West Coast");
		se.setScore("4.6");
		scoreableEntities.add(se);		
		
		logger.debug("size of trendtable: " + scoreableEntities.size());		

        
        if (scoreableEntities == null)
        {
            throw new EmptyResultSetException("No table data for: " + userID, "getScores", 0);
        }
        return scoreableEntities;
    }
}
