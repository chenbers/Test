package com.inthinc.pro.service.impl;

import java.util.List;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.service.GroupService;
import com.inthinc.pro.util.SecurityBean;

public class GroupServiceImpl implements GroupService{
	
	private GroupDAO groupDAO;
	private SecurityBean securityBean;

	public List<Group> getAll() {
		//TODO do we want group level security?
		//return groupDAO.getGroupsByAcctID(securityBean.getAccountID());
		
		return groupDAO.getGroupHierarchy(securityBean.getAccountID(), securityBean.getGroupID());
	}
	
	public Group get(Integer groupID)
	{
		Group group = groupDAO.findByID(groupID);
		if (securityBean.isAuthorized(group))
			return group;
		return null;
	}

	public Integer add(Group group)
	{
		if (!securityBean.isAuthorized(group))
			return groupDAO.create(group.getAccountID(), group);
	
		return -1;
	}

	public Integer update(Group group)
	{		
		if (securityBean.isAuthorized(group))
			return groupDAO.update(group);
		
		return -1;
	}

	public Integer delete(Integer groupID)
	{
		if(securityBean.isAuthorizedByGroupID(groupID))
			return groupDAO.deleteByID(groupID);
		
		return -1;
	}
	
	public void setGroupDAO(GroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}

	public GroupDAO getGroupDAO() {
		return groupDAO;
	}

	public SecurityBean getSecurityBean() {
		return securityBean;
	}

	public void setSecurityBean(SecurityBean securityBean) {
		this.securityBean = securityBean;
	}
}
