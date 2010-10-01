package com.inthinc.pro.reports.hos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.LocalDate;

import com.inthinc.hos.model.HOSRec;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.hos.model.ViolationsData;
import com.inthinc.hos.rules.RuleSetFactory;
import com.inthinc.hos.violations.ShiftViolations;
import com.inthinc.pro.dao.util.HOSUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.model.DrivingTimeViolationsSummary;
import com.inthinc.pro.reports.hos.model.GroupHierarchy;
import com.inthinc.pro.reports.hos.model.ViolationsSummary;
import com.inthinc.pro.reports.util.DateTimeUtil;

public class DrivingTimeViolationsSummaryReportCriteria extends ViolationsSummaryReportCriteria {

    
    
    public DrivingTimeViolationsSummaryReportCriteria(Locale locale) 
    {
        super(ReportType.DRIVING_TIME_VIOLATIONS_SUMMARY_REPORT, locale);
    }
    
    @Override
    public void init(Integer groupID, Interval interval)
    {
        Group topGroup = groupDAO.findByID(groupID);
        List<Group> groupList = groupDAO.getGroupHierarchy(topGroup.getAccountID(), topGroup.getGroupID());
        List<Driver> driverList = getDriverDAO().getDrivers(groupID);
        Map<Driver, List<HOSRecord>> driverHOSRecordMap = new HashMap<Driver, List<HOSRecord>> ();
        for (Driver driver : driverList) {
            if (driver.getDot() == null)
                continue;
            DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
            Interval queryInterval = DateTimeUtil.getExpandedInterval(interval, dateTimeZone, RuleSetFactory.getDaysBackForRuleSetType(driver.getDriverDOTType()), RuleSetFactory.getDaysForwardForRuleSetType(driver.getDriverDOTType()));
            driverHOSRecordMap.put(driver, hosDAO.getHOSRecords(driver.getDriverID(), queryInterval, true));
        }

        initDataSet(interval, topGroup, groupList, driverHOSRecordMap);

    }
    
    void initDataSet(Interval interval, Group topGroup,  List<Group> groupList, Map<Driver, List<HOSRecord>> driverHOSRecordMap)
    {
        GroupHierarchy groupHierarchy = new GroupHierarchy(topGroup, groupList);
        List<Group> childGroupList = groupHierarchy.getChildren(topGroup);
        
        Map<Integer, DrivingTimeViolationsSummary> dataMap = new TreeMap<Integer, DrivingTimeViolationsSummary>();
        dataMap.put(topGroup.getGroupID(), new DrivingTimeViolationsSummary(topGroup.getName()));
        for (Group group : childGroupList) {
            dataMap.put(group.getGroupID(), new DrivingTimeViolationsSummary(groupHierarchy.getFullName(group)));
        }
        for (Entry<Driver, List<HOSRecord>> entry : driverHOSRecordMap.entrySet()) {
            Driver driver = entry.getKey();
            DateTimeZone driverTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
            RuleSetType driverDOTType = driver.getDriverDOTType();
            DateTime reportEndDate = new LocalDate(interval.getEnd()).toDateTimeAtStartOfDay(driverTimeZone).plusDays(1).minusSeconds(1);
            
            List<HOSRecord> hosRecordList = entry.getValue();
            Collections.sort(hosRecordList);

            List<HOSRec> recListForViolationsCalc = HOSUtil.getRecListFromLogList(hosRecordList, reportEndDate.toDate(), !(driverDOTType.equals(RuleSetType.NON_DOT)));

            ViolationsSummary summary = findSummary(groupHierarchy, dataMap, driver.getGroupID());
            if (summary == null) {
                continue;
            }
            
            // violations
            List<ViolationsData> shiftViolations = new ShiftViolations().getHosViolationsInTimeFrame(
                    interval, driverTimeZone.toTimeZone(),
                    driverDOTType, 
                    RuleSetType.SLB_INTERNAL,
                    recListForViolationsCalc);
            updateSummary(summary, shiftViolations);

            updateSummaryDriverCount(summary, driver);
        }
        
        List<DrivingTimeViolationsSummary> dataList = new ArrayList<DrivingTimeViolationsSummary>();
        for (DrivingTimeViolationsSummary summary : dataMap.values()) { 
            if (summary.getDriverCnt().intValue() != 0 )
                dataList.add(summary);
        }

        setMainDataset(dataList);
        
        addParameter("REPORT_START_DATE", dateTimeFormatter.print(interval.getStart()));
        addParameter("REPORT_END_DATE", dateTimeFormatter.print(interval.getEnd()));
        
//        setUseMetric(true);
        
    }


    @Override
    protected void updateSummaryDriverCount(ViolationsSummary summary, Driver driver) {
        summary.setDriverCnt(summary.getDriverCnt() + 1);
    }




}
