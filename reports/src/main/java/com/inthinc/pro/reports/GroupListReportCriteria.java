package com.inthinc.pro.reports;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.reports.hos.model.GroupHierarchy;

public class GroupListReportCriteria extends ReportCriteria {
    
    private DriverDAO driverDAO;

    public GroupListReportCriteria(ReportType reportType, Locale locale) {
        super(reportType, "", locale);
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

    protected List<Group> getReportGroupList(List<Integer> groupIDList, GroupHierarchy groupHierarchy) {
        List<Group> reportGroupList = new ArrayList<Group>();
        for (Integer groupID : groupIDList) {
            Group group = groupHierarchy.getGroup(groupID);
            if (group != null && !reportGroupList.contains(group))
                reportGroupList.add(group);
            List<Group> childGroupList = groupHierarchy.getChildren(group);
            for (Group childGroup : childGroupList)
                if (!reportGroupList.contains(childGroup))
                    reportGroupList.add(childGroup);
        }
        return reportGroupList;
    }

    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }


}
