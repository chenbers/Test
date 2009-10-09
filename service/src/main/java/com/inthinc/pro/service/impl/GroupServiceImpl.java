package com.inthinc.pro.service.impl;

import java.util.List;

import com.inthinc.pro.model.Group;
import com.inthinc.pro.service.GroupService;
import com.inthinc.pro.util.SecureGroupDAO;


public class GroupServiceImpl implements GroupService {

    private SecureGroupDAO groupDAO;

    public List<Group> getAll() {
        // TODO do we want group level security?
        // return groupDAO.getGroupsByAcctID(securityBean.getAccountID());
    	return groupDAO.getAll();
    }

    public Group get(Integer groupID) {
        return groupDAO.findByID(groupID);
    }

    public Integer create(Group group) {
        return groupDAO.create(group);
    }

    public Integer update(Group group) {
        return groupDAO.update(group);
    }

    public Integer delete(Integer groupID) {
        return groupDAO.deleteByID(groupID);
    }

    public void setGroupDAO(SecureGroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public SecureGroupDAO getGroupDAO() {
        return groupDAO;
    }
}
