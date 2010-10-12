package com.inthinc.pro.reports;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.reports.hos.model.GroupHierarchyForReports;

public class GroupListReportCriteria extends ReportCriteria {
    
    private DriverDAO driverDAO;
    private static final String GROUP_SEPARATOR="->";

    public GroupListReportCriteria(ReportType reportType, Locale locale) {
        super(reportType, "", locale);
    }

    public String getFullGroupName(GroupHierarchy groupHierarchy, Integer groupID) {
        String fullName = groupHierarchy.getFullGroupName(groupID, GROUP_SEPARATOR);
        if (fullName.endsWith(GROUP_SEPARATOR)) {
            fullName = fullName.substring(0, fullName.length() - GROUP_SEPARATOR.length());
        }
        return fullName;

    }
    protected List<Driver> getReportDriverList(List<Group> reportGroupList) {
        List<Driver> driverList = new ArrayList<Driver>();
        for (Group group : reportGroupList) {
            if (group.getGroupID() != null) {
                List<Driver> groupDriverList = driverDAO.getDrivers(group.getGroupID());
                if (groupDriverList != null && !groupDriverList.isEmpty())
                    driverList.addAll(groupDriverList);
            }
        }
        return driverList;
    }

    protected List<Group> getReportGroupList(List<Integer> groupIDList, GroupHierarchyForReports groupHierarchy) {
        List<Group> reportGroupList = new ArrayList<Group>();
        for (Integer groupID : groupIDList) {
            addGroupAndChildren(groupHierarchy, reportGroupList, groupID);
        }
        return reportGroupList;
    }

    private void addGroupAndChildren(GroupHierarchyForReports groupHierarchy, List<Group> reportGroupList, Integer groupID) {
        Group group = groupHierarchy.getGroup(groupID);
        if (group != null && !reportGroupList.contains(group))
            reportGroupList.add(group);
        List<Group> childGroupList = groupHierarchy.getChildren(group);
        for (Group childGroup : childGroupList) {
            addGroupAndChildren(groupHierarchy, reportGroupList, childGroup.getGroupID());
        }
    }
    protected List<Group> getReportGroupList(List<Integer> groupIDList, GroupHierarchy groupHierarchy) {
        List<Group> reportGroupList = new ArrayList<Group>();
        for (Integer groupID : groupIDList) {
            addGroupAndChildren(groupHierarchy, reportGroupList, groupID);
        }
        return reportGroupList;
    }

    private void addGroupAndChildren(GroupHierarchy groupHierarchy, List<Group> reportGroupList, Integer groupID) {
        Group group = groupHierarchy.getGroup(groupID);
        if (group != null && !reportGroupList.contains(group))
            reportGroupList.add(group);
        List<Group> childGroupList = groupHierarchy.getChildren(group);
        if (childGroupList != null) {
            for (Group childGroup : childGroupList) {
                addGroupAndChildren(groupHierarchy, reportGroupList, childGroup.getGroupID());
            }
        }
    }

    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }


}
