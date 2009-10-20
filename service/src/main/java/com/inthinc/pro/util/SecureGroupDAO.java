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

            if (!getAccountID().equals(group.getAccountID()))
                throw new UnauthorizedException("accountID not found" + group.getAccountID());
            
            return true;

        }
        return false;
    }

    public boolean isAuthorized(Integer groupID) {
        return isAuthorized(findByID(groupID));
    }

    public Group findByID(Integer groupID) {
        Group group = groupDAO.findByID(groupID);
        if (group == null || !group.getAccountID().equals(getAccountID()))
            throw new NotFoundException("groupID not found: " + groupID);
        return group;
    }

    public List<Group> getAll()
    {
        return groupDAO.getGroupHierarchy(getAccountID(), getGroupID());

    }
    
    public Integer create(Group group) {
        if (isAuthorized(group))
            return groupDAO.create(getAccountID(), group);

        return null;
    }

    public Integer update(Group group) {
        if (isAuthorized(group))
            return groupDAO.update(group);

        return 0;
    }

    public Integer deleteByID(Integer groupID) {
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
