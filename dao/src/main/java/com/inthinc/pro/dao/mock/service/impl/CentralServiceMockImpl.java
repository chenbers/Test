package com.inthinc.pro.dao.mock.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.mock.data.MockData;
import com.inthinc.pro.dao.mock.data.MockDataContainer;
import com.inthinc.pro.dao.mock.data.SearchCriteria;
import com.inthinc.pro.dao.service.CentralService;
import com.inthinc.pro.model.OverallScore;
import com.inthinc.pro.model.User;

public class CentralServiceMockImpl extends MockImpl implements CentralService
{

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

}
