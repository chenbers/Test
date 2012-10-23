package com.inthinc.pro.reports.hos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

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
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.model.DrivingTimeViolationsSummary;
import com.inthinc.pro.reports.hos.model.ViolationsSummary;
import com.inthinc.pro.reports.util.DateTimeUtil;

public class DrivingTimeViolationsSummaryReportCriteria extends ViolationsSummaryReportCriteria {

    public DrivingTimeViolationsSummaryReportCriteria(Locale locale) {
        super(ReportType.DRIVING_TIME_VIOLATIONS_SUMMARY_REPORT, locale);
        this.setIncludeInactiveDrivers(ReportCriteria.DEFAULT_INCLUDE_ZERO_MILES_DRIVERS);
        this.setIncludeZeroMilesDrivers(ReportCriteria.DEFAULT_INCLUDE_ZERO_MILES_DRIVERS);
    }
    
    public void init(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval){
        List<Group> reportGroupList = getReportGroupList(groupIDList, accountGroupHierarchy);
        List<Driver> driverList = getReportDriverList(reportGroupList);
        Map<Driver, List<HOSRecord>> driverHOSRecordMap = new HashMap<Driver, List<HOSRecord>> ();
        for (Driver driver : driverList) {
            if(includeDriver(getDriverDAO(), driver.getDriverID(), interval)){
                if (driver.getDot() == null)
                    continue;
                
                DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
                Interval queryInterval = DateTimeUtil.getExpandedInterval(interval, dateTimeZone, RuleSetFactory.getDaysBackForRuleSetType(driver.getDot()), RuleSetFactory.getDaysForwardForRuleSetType(driver.getDot()));
                driverHOSRecordMap.put(driver, hosDAO.getHOSRecords(driver.getDriverID(), queryInterval, true));
            }
        }
        initDataSet(interval, accountGroupHierarchy, reportGroupList, driverHOSRecordMap);
    }
    
    void initDataSet(Interval interval, GroupHierarchy groupHierarchy,  List<Group> reportGroupList,   Map<Driver, List<HOSRecord>> driverHOSRecordMap)
    {
        
        Map<Integer, DrivingTimeViolationsSummary> dataMap = new TreeMap<Integer, DrivingTimeViolationsSummary>();
        for (Group group : reportGroupList) {
            dataMap.put(group.getGroupID(), new DrivingTimeViolationsSummary(getFullGroupName(groupHierarchy, group.getGroupID())));
        }
        for (Entry<Driver, List<HOSRecord>> entry : driverHOSRecordMap.entrySet()) {
            Driver driver = entry.getKey();
            DateTimeZone driverTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
            RuleSetType driverDOTType = driver.getDot();
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
