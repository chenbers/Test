package com.inthinc.pro.util;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupTrendWrapper;

public class SecureGroupDAO extends SecureDAO<Group> {

    private GroupDAO groupDAO;
    private GroupReportDAO groupReportDAO;

    @Override
    public boolean isAuthorized(Group group) {
        if (group != null) {
            // TODO do we give user access to all groups, regardless of the users group????
            // TODO if so, we need a fast security check to verify a group intersects with user's groups
            if (isInthincUser() || getAccountID().equals(group.getAccountID()))
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

    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, Duration duration) {
        if (isAuthorized(groupID))
            return groupReportDAO.getDriverScores(groupID, duration, new GroupHierarchy(getAll()));
        return null;
    }

    public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, Duration duration) {
        if (isAuthorized(groupID))
            return groupReportDAO.getVehicleScores(groupID, duration, new GroupHierarchy(getAll()));
        return null;
    }

    public List<GroupTrendWrapper> getChildGroupsDriverTrends(Integer groupID, Duration duration) {
        if (isAuthorized(groupID))
            return groupReportDAO.getSubGroupsAggregateDriverTrends(groupID, duration, new GroupHierarchy(getAll()));
        return Collections.emptyList();
    }

    public List<GroupScoreWrapper> getChildGroupsDriverScores(Integer groupID, Duration duration) {
        if (isAuthorized(groupID))
            return groupReportDAO.getSubGroupsAggregateDriverScores(groupID, duration, new GroupHierarchy(getAll()));
        return Collections.emptyList();
    }

    @Override
    public Integer create(Group group) {
        if (isAuthorized(group))
            return groupDAO.create(getAccountID(), group);

        return null;
    }

    @Override
    public Group update(Group group) {
        if (isAuthorized(group) && groupDAO.update(group) != 0)
            return groupDAO.findByID(group.getGroupID());
        return null;
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

    public GroupReportDAO getGroupReportDAO() {
        return groupReportDAO;
    }

    public void setGroupReportDAO(GroupReportDAO groupReportDAO) {
        this.groupReportDAO = groupReportDAO;
    }
}
