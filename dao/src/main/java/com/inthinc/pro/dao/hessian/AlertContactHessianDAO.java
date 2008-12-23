package com.inthinc.pro.dao.hessian;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.AlertContactDAO;
import com.inthinc.pro.dao.FindByKey;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.AlertContact;
import com.inthinc.pro.model.Silo;

public class AlertContactHessianDAO extends GenericHessianDAO<AlertContact, Integer> implements AlertContactDAO, FindByKey<AlertContact>
{
    private static final Logger logger = Logger.getLogger(AlertContactHessianDAO.class);

    @Override
    @SuppressWarnings("unchecked")
    public Integer create(AlertContact entity)
    {
        // if silo id is not provided -- get one from the back end
        Silo silo = getMapper().convertToModelObject(getSiloService().getNextSilo(), Silo.class);
        return super.create(silo.getSiloID(), entity);
    }
    
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
