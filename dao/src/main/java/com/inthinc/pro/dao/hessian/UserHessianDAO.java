package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.FindByKey;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.util.MiscUtil;
import com.inthinc.pro.model.User;

public class UserHessianDAO extends GenericHessianDAO<User, Integer> implements UserDAO, FindByKey<User> {
    private static final Logger logger = Logger.getLogger(UserHessianDAO.class);

    private static final String CENTRAL_ID_KEY = "username";

    @Override
    public User findByUserName(String username) {

        // TODO: it can take up to 5 minutes from when a user record is added until
        // it can be accessed via getID(). Should this method account for that?
        try {
            Map<String, Object> returnMap = getSiloService().getID(CENTRAL_ID_KEY, username);
            Integer userId = getCentralId(returnMap);
            return findByID(userId);
        } catch (EmptyResultSetException e) {
            return null;
        }

    }

    @Override
    public User findByKey(String key) {
        return findByUserName(key);
    }

    @Override
    public List<User> getUsersInGroupHierarchy(Integer groupID) {

        try {
            return MiscUtil.removeInThinc(getMapper().convertToModelObject(getSiloService().getUsersByGroupIDDeep(groupID), User.class));
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }

    }

    @Override
    public List<User> getUsersByGroupId(Integer groupID) {

        try {
            return MiscUtil.removeInThinc(getMapper().convertToModelObject(getSiloService().getUsersByGroupID(groupID), User.class));
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public User getUserByPersonID(Integer personID) {
        try {
            return getMapper().convertToModelObject(this.getSiloService().getUserByPersonID(personID), User.class);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }

}
