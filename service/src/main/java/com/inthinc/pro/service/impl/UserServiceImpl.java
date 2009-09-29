package com.inthinc.pro.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.User;
import com.inthinc.pro.service.UserService;
import com.inthinc.pro.util.SecurityBean;

public class UserServiceImpl implements UserService{
	
	private UserDAO userDAO;
	private SecurityBean securityBean;
	

	public List<User> getAll() {
        return userDAO.getUsersInGroupHierarchy(securityBean.getGroupID());		
	}
	
	public User get(Integer userID)
	{
        User user = userDAO.findByID(userID);
		
        if (securityBean.isAuthorized(user))
			return user;
        
		return null;
	}

	public Integer add(User user)
	{
		if (!securityBean.isAuthorized(user))
			return userDAO.create(user.getPersonID(), user);
	
		return -1;
	}

	public Integer update(User user)
	{		
		if (securityBean.isAuthorized(user))
			return userDAO.update(user);
		
		return -1;
	}

	public Integer delete(Integer userID)
	{
		if(securityBean.isAuthorizedByUserID(userID))
			return userDAO.deleteByID(userID);
		
		return -1;
	}
	
	public List<Integer> add(List<User> users)
	{
		List<Integer> results = new ArrayList<Integer>();
		for(User user : users)
			results.add(add(user));
		return results;
	}

	public List<Integer> update(List<User> users)
	{
		List<Integer> results = new ArrayList<Integer>();
		for(User user : users)
			results.add(update(user));
		return results;
	}

	public List<Integer> delete(List<Integer> userIDs)
	{
		List<Integer> results = new ArrayList<Integer>();
		for(Integer id : userIDs)
		{
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
	
	public SecurityBean getSecurityBean() {
		return securityBean;
	}

	public void setSecurityBean(SecurityBean securityBean) {
		this.securityBean = securityBean;
	}
}
