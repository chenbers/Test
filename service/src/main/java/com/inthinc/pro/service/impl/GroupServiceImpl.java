package com.inthinc.pro.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.User;
import com.inthinc.pro.service.GroupService;


public class GroupServiceImpl implements GroupService{
	
	private UserDAO userDAO;
	private GroupDAO groupDAO;
	private String userName="speedracer";


	public List<Group> getGroups() {
		List<Group> groupList = new ArrayList<Group>();
		if(userName != null)
        {
            User user = userDAO.findByUserName(userName);
            if(user != null)
            {
                Group group = groupDAO.findByID(user.getGroupID());
                groupList = groupDAO.getGroupsByAcctID(group.getAccountID());
            }
        }
		return groupList;
	}
	
	public Group getGroup(Integer groupID)
	{
		//TODO username for group security but get from logged in user
        User user = userDAO.findByUserName("speedracer");
        Group usergroup = groupDAO.findByID(user.getGroupID());

        //TODO Security!!! Limit to users account? 
		//TODO Group is tough unless we hop to vehicle but that won't work for unassigned.
		Group group = groupDAO.findByID(groupID);
		if (group!=null && usergroup.getAccountID().equals(group.getAccountID()))
		{
			return group;
		}
		return null;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setGroupDAO(GroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}

	public GroupDAO getGroupDAO() {
		return groupDAO;
	}

}
