package com.inthinc.pro.reports.performance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.pro.dao.DriverPerformanceDAO;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverPerformance;
import com.inthinc.pro.model.aggregation.DriverPerformanceWeekly;
import com.inthinc.pro.reports.GroupListReportCriteria;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;

public class DriverPerformanceWeeklyReportCriteria extends GroupListReportCriteria {
    
    private DriverPerformanceDAO driverPerformanceDAO;
    protected DateTimeFormatter dateTimeFormatter;
    
    private List<ReportCriteria> criteriaList;


    
    public DriverPerformanceWeeklyReportCriteria(ReportType reportType, Locale locale) {
        super(reportType, locale);
        
        dateTimeFormatter = DateTimeFormat.forPattern(ReportCriteria.DATE_FORMAT).withLocale(locale);

    }

    public void init(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, TimeFrame timeFrame, MeasurementType measurementType) {
        List<DriverPerformanceWeekly> dataList = new ArrayList<DriverPerformanceWeekly>();

        List<Group> reportGroupList = getReportGroupList(groupIDList, accountGroupHierarchy);

        for (Group group : reportGroupList) {
            if (group.getGroupID() != null && group.getType() == GroupType.TEAM) {
                String divisionName = accountGroupHierarchy.getFullGroupName(group.getParentID(), "->");
                List<DriverPerformanceWeekly> groupList = driverPerformanceDAO.getDriverPerformanceWeeklyListForGroup(group.getGroupID(), divisionName, group.getName(), timeFrame);
                Collections.sort(groupList);
                dataList.addAll(groupList);
            }
        }
        
        setMainDataset(dataList);
        
        addParameter(REPORT_START_DATE, dateTimeFormatter.print(timeFrame.getInterval().getStart()));
        addParameter(REPORT_END_DATE, dateTimeFormatter.print(timeFrame.getInterval().getEnd()));
        setUseMetric(measurementType == MeasurementType.METRIC);
        
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
