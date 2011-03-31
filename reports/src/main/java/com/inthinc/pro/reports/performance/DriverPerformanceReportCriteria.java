package com.inthinc.pro.reports.performance;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.pro.dao.DriverPerformanceDAO;
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

    
    public DriverPerformanceReportCriteria(ReportType reportType, Locale locale) {
        super(reportType, locale);
        
        dateTimeFormatter = DateTimeFormat.forPattern(ReportCriteria.DATE_FORMAT).withLocale(locale);

    }

    public void init(Integer driverID, Interval interval) {
        List<DriverPerformance> dataList = new ArrayList<DriverPerformance>();
        dataList.add(driverPerformanceDAO.getDriverPerformance(driverID, interval));
        setMainDataset(dataList);
        
        addParameter(REPORT_START_DATE, dateTimeFormatter.print(interval.getStart()));
        addParameter(REPORT_END_DATE, dateTimeFormatter.print(interval.getEnd()));
    }

    public void init(GroupHierarchy accountGroupHierarchy, Integer groupID, Interval interval) {
        List<DriverPerformance> dataList = new ArrayList<DriverPerformance>();

        List<Integer> groupIDList = new ArrayList<Integer>();
        groupIDList.add(groupID);
        List<Group> reportGroupList = getReportGroupList(groupIDList, accountGroupHierarchy);

        for (Group group : reportGroupList) {
            if (group.getGroupID() != null && group.getType() == GroupType.TEAM) {
                dataList.addAll(driverPerformanceDAO.getDriverPerformanceListForGroup(group.getGroupID(), interval));
            }
        }

        
        setMainDataset(dataList);
        
        addParameter(REPORT_START_DATE, dateTimeFormatter.print(interval.getStart()));
        addParameter(REPORT_END_DATE, dateTimeFormatter.print(interval.getEnd()));
        
    }

    public DriverPerformanceDAO getDriverPerformanceDAO() {
        return driverPerformanceDAO;
    }

    public void setDriverPerformanceDAO(DriverPerformanceDAO driverPerformanceDAO) {
        this.driverPerformanceDAO = driverPerformanceDAO;
    }


}
