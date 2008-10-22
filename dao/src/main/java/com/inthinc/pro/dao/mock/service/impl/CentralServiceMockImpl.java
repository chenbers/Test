package com.inthinc.pro.dao.mock.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.mock.data.MockData;
import com.inthinc.pro.dao.service.CentralService;
import com.inthinc.pro.model.User;

public class CentralServiceMockImpl implements CentralService
{
    MockData mockData = new MockData();

    @Override
    public Map<String, Object> getUserByAccountID(Integer accountID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getUserIDByEmail(String email) throws ProDAOException
    {
//        Map<String, Object> returnMap =  mockData.allUsersByEmailMap.get(email);
        Map<String, Object> returnMap =  mockData.lookup(User.class, "email", email);
        
        if (returnMap == null)
        {
            throw new EmptyResultSetException("No user for email: " + email, "getUserIDByEmail", 0);
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> getUserIDByName(String username) throws ProDAOException
    {
//        Map<String, Object> returnMap =  mockData.allUsersByUsernameMap.get(username);
        Map<String, Object> returnMap =  mockData.lookup(User.class, "username", username);
        
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
 //       Map<String, Object> returnMap =  mockData.lookup(OverallScore.class, "userID", userID);
        Map<String, Object> returnMap =  new HashMap<String, Object>();
        returnMap.put("score", Integer.valueOf(50));
        if (returnMap == null)
        {
            throw new EmptyResultSetException("No overall score for: " + userID, "getOverallScore", 0);
        }
        return returnMap;
    }

}
