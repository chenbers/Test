package com.inthinc.pro.dao.mock.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
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
        Map<String, Object> returnMap =  getMockDataContainer().lookup(User.class, "email", email);

        if (returnMap == null)
        {
            throw new EmptyResultSetException("No user for email: " + email, "getUserIDByEmail", 0);
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> getUserIDByName(String username) throws ProDAOException
    {
        Map<String, Object> returnMap =  getMockDataContainer().lookup(User.class, "username", username);
        
        if (returnMap == null)
        {
            throw new EmptyResultSetException("No user for username: " + username, "getUserIDByUsername", 0);
        }
        return returnMap;
    }


}
