package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.User;

public class UserHessianDAO extends GenericHessianDAO<User, Integer> implements UserDAO
{
    private static final Logger logger = Logger.getLogger(UserHessianDAO.class);

    

    @Override
    public User findByUserName(String username)
    {
        logger.debug("getting user by name: " + username);
        
        
        Integer userId = getReturnKey(getSiloService().getID("username", username));
        return findByID(userId);
    }



    @Override
    public List<User> getUsersInGroupHierarchy(Integer groupID)
    {
        
        try
        {
            return getMapper().convertToModelObject(getSiloService().getUsersByGroupID(groupID), User.class);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }

    }
}
