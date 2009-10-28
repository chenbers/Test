package com.inthinc.pro.util;

import java.util.List;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.Group;

public class SecureGroupDAO extends SecureDAO<Group> {

    private GroupDAO groupDAO;

    @Override
    public boolean isAuthorized(Group group) {
        if (group != null) {
            // TODO do we give user access to all groups, regardless of the users group????
            // TODO if so, we need a fast security check to verify a group intersects with user's groups
            if(getUser().getRole().equals(inthincRole))
                return true;
            if (!getAccountID().equals(group.getAccountID()))
                return false;
            return true;
        }
        return false;
    }

    public boolean isAuthorized(Integer groupID) {
        return isAuthorized(findByID(groupID));
    }

    @Override
    public Group findByID(Integer groupID) {
        Group group = groupDAO.findByID(groupID);
        if (isAuthorized(group))
            return group;
        return null;
    }

    @Override
    public List<Group> getAll() {
        return groupDAO.getGroupHierarchy(getAccountID(), getGroupID());

    }

    @Override
    public Integer create(Group group) {
        if (isAuthorized(group))
            return groupDAO.create(getAccountID(), group);

        return null;
    }

    @Override
    public Integer update(Group group) {
        if (isAuthorized(group))
            return groupDAO.update(group);

        return 0;
    }

    @Override
    public Integer delete(Integer groupID) {
        if (isAuthorized(groupID))
            return groupDAO.deleteByID(groupID);

        return 0;
    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }
}
