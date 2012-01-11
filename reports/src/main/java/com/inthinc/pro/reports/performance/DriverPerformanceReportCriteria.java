package com.inthinc.pro.reports.performance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.pro.dao.report.DriverPerformanceDAO;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.aggregation.DriverPerformance;
import com.inthinc.pro.reports.GroupListReportCriteria;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;

public class DriverPerformanceReportCriteria extends GroupListReportCriteria {
    
    private DriverPerformanceDAO driverPerformanceDAO;
    protected DateTimeFormatter dateTimeFormatter;
    
    private List<ReportCriteria> criteriaList;


    
    public DriverPerformanceReportCriteria(ReportType reportType, Locale locale) {
        super(reportType, locale);
        
        dateTimeFormatter = DateTimeFormat.forPattern(ReportCriteria.DATE_FORMAT).withLocale(locale);

    }

    public void init(GroupHierarchy accountGroupHierarchy, Integer groupID, List<Integer> driverIDList, Interval interval, Boolean ryg) {
        
        String groupName = accountGroupHierarchy.getShortGroupName(groupID, "->");
        List<DriverPerformance> dataList = driverPerformanceDAO.getDriverPerformance(groupID, groupName, driverIDList, interval);

        criteriaList = new ArrayList<ReportCriteria>();
        for (DriverPerformance dp : dataList) {
            ReportCriteria reportCriteria = new DriverPerformanceReportCriteria(this.getReport(), this.getLocale());
            List<DriverPerformance> driverDataList = new ArrayList<DriverPerformance>();
            dp.setRyg(ryg);
            driverDataList.add(dp);
            reportCriteria.setMainDataset(driverDataList);
        
            reportCriteria.addParameter(REPORT_START_DATE, dateTimeFormatter.print(interval.getStart()));
            reportCriteria.addParameter(REPORT_END_DATE, dateTimeFormatter.print(interval.getEnd()));
            reportCriteria.addParameter("RYG", ryg);
            
            criteriaList.add(reportCriteria);
        }
    }

    public void init(GroupHierarchy accountGroupHierarchy, Integer groupID, Interval interval, Boolean ryg) {
        List<DriverPerformance> dataList = new ArrayList<DriverPerformance>();

        List<Integer> groupIDList = new ArrayList<Integer>();
        groupIDList.add(groupID);
        List<Group> reportGroupList = getReportGroupList(groupIDList, accountGroupHierarchy);

        for (Group group : reportGroupList) {
            if (group.getGroupID() != null && group.getType() == GroupType.TEAM) {
                String groupName = accountGroupHierarchy .getFullGroupName(group.getGroupID(), "->");
                List<DriverPerformance> groupList = driverPerformanceDAO.getDriverPerformanceListForGroup(group.getGroupID(), groupName, interval);
                for (DriverPerformance dp : groupList)
                    dp.setRyg(ryg);
                Collections.sort(groupList);
                dataList.addAll(groupList);
            }
        }

        
        setMainDataset(dataList);
        
        addParameter(REPORT_START_DATE, dateTimeFormatter.print(interval.getStart()));
        addParameter(REPORT_END_DATE, dateTimeFormatter.print(interval.getEnd()));
        addParameter("RYG", ryg);
        
    }

    public DriverPerformanceDAO getDriverPerformanceDAO() {
        return driverPerformanceDAO;
    }

    public void setDriverPerformanceDAO(DriverPerformanceDAO driverPerformanceDAO) {
        this.driverPerformanceDAO = driverPerformanceDAO;
    }

    public List<ReportCriteria> getCriteriaList() {
        return criteriaList;
    }

    public void setCriteriaList(List<ReportCriteria> criteriaList) {
        this.criteriaList = criteriaList;
    }


}
