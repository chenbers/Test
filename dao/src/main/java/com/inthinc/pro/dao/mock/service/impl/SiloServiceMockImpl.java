package com.inthinc.pro.dao.mock.service.impl;

import java.util.Map;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.mock.data.MockData;
import com.inthinc.pro.dao.mock.data.MockDataContainer;
import com.inthinc.pro.dao.service.SiloService;
import com.inthinc.pro.model.User;

public class SiloServiceMockImpl implements SiloService
{

    MockDataContainer mockDataContainer = new MockDataContainer();
    
    
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
        Map<String, Object> returnMap =  mockDataContainer.lookup(User.class, "userID", userID);

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

}
