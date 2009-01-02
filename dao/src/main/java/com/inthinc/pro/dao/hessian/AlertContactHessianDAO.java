package com.inthinc.pro.dao.hessian;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.AlertContactDAO;
import com.inthinc.pro.dao.FindByKey;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.AlertCon;
import com.inthinc.pro.model.Silo;

public class AlertContactHessianDAO extends GenericHessianDAO<AlertCon, Integer> implements AlertContactDAO, FindByKey<AlertCon>
{
    private static final Logger logger = Logger.getLogger(AlertContactHessianDAO.class);

    @Override
    @SuppressWarnings("unchecked")
    public Integer create(AlertCon entity)
    {
        return super.create(entity.getUserID(), entity);
    }
    
    public AlertCon findByUserID(Integer userID)
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
    public AlertCon findByKey(String key)
    {
    	Integer userID = Integer.parseInt(key);
        return findByUserID(userID);
    }
    


}
