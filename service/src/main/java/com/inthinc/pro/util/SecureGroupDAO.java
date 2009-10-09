package com.inthinc.pro.util;

import java.util.List;

import org.jboss.resteasy.spi.NotFoundException;
import org.jboss.resteasy.spi.UnauthorizedException;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.Group;

public class SecureGroupDAO extends BaseSecureDAO{

    private GroupDAO groupDAO;

    public boolean isAuthorized(Group group) {
        if (group != null) {
            // TODO do we give user access to all groups, regardless of the users group????
            // TODO if so, we need a fast security check to verify a group intersects with user's groups
            // TODO get Account from logged in user

            if (!getAccountID().equals(group.getAccountID()))
                throw new UnauthorizedException("accountID not found" + group.getAccountID());
            
            return true;

        }
        throw new UnauthorizedException("Group not found");
    }

    public List<Group> getAll()
    {
        return groupDAO.getGroupHierarchy(getAccountID(), getGroupID());

    }
    
    public Group findByID(Integer groupID) {
        Group group = groupDAO.findByID(groupID);
        if (group == null || !group.getAccountID().equals(getAccountID()))
            throw new NotFoundException("groupID not found: " + groupID);
        return group;
    }

    public Integer create(Group group) {
        if (isAuthorized(group))
            return groupDAO.create(getAccountID(), group);

        return -1;
    }

    public Integer update(Group group) {
        if (isAuthorized(group))
            return groupDAO.update(group);

        return -1;
    }

    public Integer deleteByID(Integer groupID) {
        if (isAuthorizedByGroupID(groupID))
            return groupDAO.deleteByID(groupID);

        return -1;
    }

    
    public boolean isAuthorizedByGroupID(Integer groupID) {
        return isAuthorized(findByID(groupID));
    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }
}
