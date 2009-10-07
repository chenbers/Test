package com.inthinc.pro.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.jboss.resteasy.spi.NotFoundException;
import org.jboss.resteasy.spi.UnauthorizedException;

import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.User;
import com.inthinc.pro.service.UserService;
import com.inthinc.pro.util.SecurityBean;

public class UserServiceImpl extends BaseService implements UserService {

    private UserDAO userDAO;

    public List<User> getAll() {
        return userDAO.getUsersInGroupHierarchy(getUser().getGroupID());
    }

    public User get(Integer userID) {

        User user = userDAO.findByID(userID);
        
        if(user != null && securityBean.isAuthorized(user))
            return user;
        else
            throw new NotFoundException("Attempt to find UserID: " + userID + " by User '" + getUser().getUsername() + "' did not return a result. Either the UserID does not exist or the User does not have authority." );


    }

    public Integer add(User user) {
        return userDAO.create(user.getPersonID(), user);
    }

    public Integer update(User user) {
        if (securityBean.isAuthorized(user))
            return userDAO.update(user);
        else
            throw new UnauthorizedException("Unauthorized attempt to update User: " + (user != null && user.getUsername() != null ? user.getUsername() : "Unknown User") + " by User: " + getUser().getUsername()   );

    }

    public Integer delete(Integer userID) {
        if (securityBean.isAuthorizedByUserID(userID))
            return userDAO.deleteByID(userID);
        else
            throw new UnauthorizedException("Unauthorized attempt to delete UserID: " + userID + " by User: " + getUser().getUsername()  );

    }

    public List<Integer> add(List<User> users) {
        List<Integer> results = new ArrayList<Integer>();
        for (User user : users)
            results.add(add(user));
        return results;
    }

    public List<Integer> update(List<User> users) {
        List<Integer> results = new ArrayList<Integer>();
        for (User user : users)
            results.add(update(user));
        return results;
    }

    public List<Integer> delete(List<Integer> userIDs) {
        List<Integer> results = new ArrayList<Integer>();
        for (Integer id : userIDs) {
            results.add(delete(id));
        }
        return results;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }
}
