package com.inthinc.pro.dao.hessian;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.dao.annotations.ConvertColumnToField;
import com.inthinc.pro.model.User;

public class UserHessianDAO extends GenericHessianDAO<User, Integer> implements UserDAO
{
    private static final Logger logger = Logger.getLogger(UserHessianDAO.class);

    @Override
    public User findByEmail(String email)
    {
        Integer userId = getReturnKey(getSiloService().getUserIDByEmail(email));
        return findByID(userId);
    }

    @Override
    public User findByUserName(String username)
    {
        logger.debug("getting user by name: " + username);
        Integer userId = getReturnKey(getSiloService().getUserIDByName(username));
        return findByID(userId);
    }

    @ConvertColumnToField(columnName = "phone")
    public void phoneNumbersToModel(User user, Object value)
    {
        if (user == null || value == null)
            return;

        if (value instanceof String)
        {
            String[] phoneNumbers = ((String) value).split(";", 2);
            if (phoneNumbers.length > 0)
                user.getPerson().setWorkPhone(phoneNumbers[0]);
            if (phoneNumbers.length > 1)
                user.getPerson().setHomePhone(phoneNumbers[1]);
        }
    }
}
