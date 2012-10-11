package com.inthinc.pro.util;

import java.util.List;

import org.jasypt.util.password.StrongPasswordEncryptor;

import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.User;

public class SecureUserDAO extends SecureDAO<User> {

    private UserDAO userDAO;
    private SecurePersonDAO personDAO;
    private SecureGroupDAO groupDAO;

    @Override
    public boolean isAuthorized(User user) {
        if (user != null) {
            // TODO do we give user access to all groups, regardless of the users group????
            // TODO if so, we need a fast security check to verify a group intersects with user's groups
            if (isInthincUser() || (groupDAO.isAuthorized(user.getGroupID()) && personDAO.isAuthorized(user.getPersonID())))
                return true;
        }
        return false;
    }

    public boolean isAuthorized(Integer userID) {
        return isAuthorized(findByID(userID));
    }

    // TODO: Wondering if we should filter user with a status of DELETED
    @Override
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

    public User login(String userName, String password) {
        User user = userDAO.findByUserName(userName);
        if (isAuthorized(user)) {
            StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
            if(passwordEncryptor.checkPassword(password, user.getPassword())) 
                return user;
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        return userDAO.getUsersInGroupHierarchy(getUser().getGroupID());
    }

    @Override
    public Integer create(User user) {
        if (isAuthorized(user))
            return userDAO.create(user.getPersonID(), user);
        return null;
    }

    @Override
    public User update(User user) {
        if (isAuthorized(user) && userDAO.update(user) != 0) {
            return userDAO.findByID(user.getUserID());
        }
        return null;
    }

    @Override
    public Integer delete(Integer userID) {
        if (isAuthorized(userID))
            return userDAO.deleteByID(userID);
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
