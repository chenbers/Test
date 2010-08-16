package com.inthinc.pro.reports.hos;

import java.util.ArrayList;
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
import com.inthinc.hos.violations.DailyViolations;
import com.inthinc.hos.violations.ShiftViolations;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.hos.HOSGroupMileage;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.model.GroupHierarchy;
import com.inthinc.pro.reports.hos.model.HosViolationsSummary;
import com.inthinc.pro.reports.hos.model.ViolationsSummary;
import com.inthinc.pro.reports.hos.util.HOSUtil;
import com.inthinc.pro.reports.util.DateTimeUtil;

public class HosViolationsSummaryReportCriteria extends ViolationsSummaryReportCriteria {

    
    
    public HosViolationsSummaryReportCriteria(Locale locale) 
    {
        super(ReportType.HOS_VIOLATIONS_SUMMARY_REPORT, locale);
    }
    
    public void init(Integer groupID, Interval interval)
    {
        Group topGroup = groupDAO.findByID(groupID);
        List<Group> groupList = groupDAO.getGroupHierarchy(topGroup.getAccountID(), topGroup.getGroupID());
        List<Driver> driverList = driverDAO.getDrivers(groupID);
        Map<Driver, List<HOSRecord>> driverHOSRecordMap = new HashMap<Driver, List<HOSRecord>> ();
        for (Driver driver : driverList) {
            if (driver.getDot() == null)
                continue;
            
            DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
            Interval queryInterval = DateTimeUtil.getExpandedInterval(interval, dateTimeZone, RuleSetFactory.getDaysBackForRuleSetType(driver.getDriverDOTType()), RuleSetFactory.getDaysForwardForRuleSetType(driver.getDriverDOTType()));
            driverHOSRecordMap.put(driver, hosDAO.getHOSRecords(driver.getDriverID(), queryInterval));
            
        }
        
        List<HOSGroupMileage> groupMileageList = hosDAO.getHOSMileage(groupID, interval, false);
        List<HOSGroupMileage> groupNoDriverMileageList = hosDAO.getHOSMileage(groupID, interval, true);

        initDataSet(interval, topGroup, groupList, driverHOSRecordMap, groupMileageList, groupNoDriverMileageList);

    }
    
    void initDataSet(Interval interval, Group topGroup,  List<Group> groupList, Map<Driver, List<HOSRecord>> driverHOSRecordMap,
            List<HOSGroupMileage> groupMileageList, List<HOSGroupMileage> groupNoDriverMileageList)
    {
        GroupHierarchy groupHierarchy = new GroupHierarchy(topGroup, groupList);
        List<Group> childGroupList = groupHierarchy.getChildren(topGroup);
        
        Map<Integer, HosViolationsSummary> dataMap = new TreeMap<Integer, HosViolationsSummary>();
        dataMap.put(topGroup.getGroupID(), new HosViolationsSummary(topGroup.getName()));
        for (Group group : childGroupList) {
            dataMap.put(group.getGroupID(), new HosViolationsSummary(groupHierarchy.getFullName(group)));
        }
        for (Entry<Driver, List<HOSRecord>> entry : driverHOSRecordMap.entrySet()) {
            Driver driver = entry.getKey();
            DateTimeZone driverTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
            RuleSetType driverDOTType = driver.getDriverDOTType();
            DateTime reportEndDate = new LocalDate(interval.getEnd()).toDateTimeAtStartOfDay(driverTimeZone).plusDays(1).minusSeconds(1);
            List<HOSRec> recListForViolationsCalc = HOSUtil.getRecListFromLogList(entry.getValue(), reportEndDate.toDate(), !(driverDOTType.equals(RuleSetType.NON_DOT)));

            ViolationsSummary summary = findSummary(groupHierarchy, topGroup, dataMap, driver.getGroupID());
            if (summary == null) {
                continue;
            }
            
            // violations
            List<ViolationsData> dailyViolations = new DailyViolations().getDailyHosViolationsForReport(interval,
                    driverTimeZone.toTimeZone(),
                    driverDOTType, 
                    recListForViolationsCalc);
            updateSummary(summary, dailyViolations);

            List<ViolationsData> shiftViolations = new ShiftViolations().getHosViolationsInTimeFrame(
                    interval, driverTimeZone.toTimeZone(),
                    driverDOTType, 
                    null, 
                    recListForViolationsCalc);
            updateSummary(summary, shiftViolations);

            updateSummaryDriverCount(summary, driver);
        }
        
        for (HOSGroupMileage groupMileage : groupMileageList) {
            HosViolationsSummary summary = (HosViolationsSummary)findSummary(groupHierarchy, topGroup, dataMap, groupMileage.getGroupID());
            if (summary == null) {
                continue;
            }
            summary.setTotalMiles(summary.getTotalMiles()+groupMileage.getDistance());
            
        }
        for (HOSGroupMileage groupMileage : groupNoDriverMileageList) {
            HosViolationsSummary summary = (HosViolationsSummary)findSummary(groupHierarchy, topGroup, dataMap, groupMileage.getGroupID());
            if (summary == null) {
                continue;
            }
            summary.setTotalMiles(summary.getTotalMiles()+groupMileage.getDistance());
            summary.setTotalMilesNoDriver(summary.getTotalMilesNoDriver()+groupMileage.getDistance());
            
        }
         
        List<HosViolationsSummary> dataList = new ArrayList<HosViolationsSummary>();
        for (HosViolationsSummary summary : dataMap.values()) { 
            if (summary.getDriverCnt().intValue() != 0 || summary.getTotalMiles().intValue() != 0)
                dataList.add(summary);
        }

        setMainDataset(dataList);
        
        addParameter("REPORT_START_DATE", dateTimeFormatter.print(interval.getStart()));
        addParameter("REPORT_END_DATE", dateTimeFormatter.print(interval.getEnd()));
        
//        setUseMetric(true);
        
    }

    @Override
    protected void updateSummaryDriverCount(ViolationsSummary summary, Driver driver) {
        if (driver.getDot() != null && driver.getDriverDOTType() != RuleSetType.NON_DOT)
            summary.setDriverCnt(summary.getDriverCnt() + 1);
        
    }


}
