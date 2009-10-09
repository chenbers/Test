package com.inthinc.pro.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.inthinc.pro.model.User;
import com.inthinc.pro.service.UserService;
import com.inthinc.pro.util.SecureUserDAO;

public class UserServiceImpl implements UserService {

    private SecureUserDAO userDAO;

    public List<User> getAll() {
        return userDAO.getAll();
    }

    public User get(Integer userID) {

        return userDAO.findByID(userID);
    }

    public Integer create(User user) {
       	return userDAO.create(user);
    }

    public Integer update(User user) {
        return userDAO.update(user);

    }

    public Integer delete(Integer userID) {
        return userDAO.deleteByID(userID);
    }

    public List<Integer> create(List<User> users) {
        List<Integer> results = new ArrayList<Integer>();
        for (User user : users)
            results.add(create(user));
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

    public void setUserDAO(SecureUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public SecureUserDAO getUserDAO() {
        return userDAO;
    }
}
