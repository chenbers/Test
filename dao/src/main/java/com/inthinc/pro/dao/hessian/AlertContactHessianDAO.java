package com.inthinc.pro.dao.hessian;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.AlertContactDAO;
import com.inthinc.pro.dao.FindByKey;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.AlertContact;

public class AlertContactHessianDAO extends GenericHessianDAO<AlertContact, Integer> implements AlertContactDAO, FindByKey<AlertContact>
{
    private static final Logger logger = Logger.getLogger(AlertContactHessianDAO.class);

    public AlertContact findByUserID(Integer userID)
    {
        try
        {
            return findByID(userID);
        }
        catch (EmptyResultSetException e)
        {
            return null;
        }
    }

    @Override
    public AlertContact findByKey(String key)
    {
    	Integer userID = Integer.parseInt(key);
        return findByUserID(userID);
    }
    


}
