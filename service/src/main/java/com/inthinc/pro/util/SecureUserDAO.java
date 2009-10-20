package com.inthinc.pro.util;

import java.util.List;

import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.User;

public class SecureUserDAO extends BaseSecureDAO {

    private UserDAO userDAO;
    private SecurePersonDAO personDAO;
    private SecureGroupDAO groupDAO;

    private boolean isAuthorized(User user) {
        if (user != null) {
            // TODO do we give user access to all groups, regardless of the users group????
            // TODO if so, we need a fast security check to verify a group intersects with user's groups

            if (!groupDAO.isAuthorized(user.getGroupID()))
                return false;

            if (!personDAO.isAuthorized(user.getPersonID()))
                return false;
            return true;
        }
        return false;
    }

    public boolean isAuthorized(Integer userID) {
        return isAuthorized(findByID(userID));
    }

    public User findByID(Integer userID) {
        User user = userDAO.findByID(userID);
        if (isAuthorized(user))
            return user;
        return null;
    }

    public User findByUserName(String userName) {
        User user = userDAO.findByUserName(userName);
        if (isAuthorized(user))
            return user;
        return null;
    }

    public List<User> getAll() {
        return userDAO.getUsersInGroupHierarchy(getUser().getGroupID());
    }

    public Integer create(User user) {
        if (isAuthorized(user))
            return userDAO.create(user.getPersonID(), user);
        return null;
    }

    public Integer update(User user) {
        if (isAuthorized(user))
            return userDAO.update(user);
        else
            return 0;
    }

    public Integer deleteByID(Integer userID) {
        if (isAuthorized(userID))
            return userDAO.deleteByID(userID);
        else
            return 0;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public SecurePersonDAO getPersonDAO() {
        return personDAO;
    }

    public void setPersonDAO(SecurePersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public SecureGroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(SecureGroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

}
