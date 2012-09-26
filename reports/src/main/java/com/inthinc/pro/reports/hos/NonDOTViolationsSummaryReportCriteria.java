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
import com.inthinc.hos.violations.NonDOTShiftViolations;
import com.inthinc.pro.dao.util.HOSUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.model.NonDOTViolationsSummary;
import com.inthinc.pro.reports.hos.model.ViolationsSummary;
import com.inthinc.pro.reports.util.DateTimeUtil;

public class NonDOTViolationsSummaryReportCriteria extends ViolationsSummaryReportCriteria {

    
    public NonDOTViolationsSummaryReportCriteria(Locale locale) 
    {
        super(ReportType.NON_DOT_VIOLATIONS_SUMMARY_REPORT, locale);
    }
    
    public void init(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval)
    {
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

  
    void initDataSet(Interval interval, GroupHierarchy groupHierarchy,  List<Group> reportGroupList,  Map<Driver, List<HOSRecord>> driverHOSRecordMap)
    {
        
        Map<Integer, NonDOTViolationsSummary> dataMap = new TreeMap<Integer, NonDOTViolationsSummary>();
        for (Group group : reportGroupList) {
            dataMap.put(group.getGroupID(), new NonDOTViolationsSummary(getFullGroupName(groupHierarchy, group.getGroupID())));
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
            
            List<ViolationsData> shiftViolations = new NonDOTShiftViolations().getHosViolationsInTimeFrame(
                    interval, driverTimeZone.toTimeZone(),
                    driverDOTType, 
                    null,  
                    recListForViolationsCalc);
            updateSummary(summary, shiftViolations);

            updateSummaryDriverCount(summary, driver);
        }
        
         
        List<NonDOTViolationsSummary> dataList = new ArrayList<NonDOTViolationsSummary>();
        for (NonDOTViolationsSummary summary : dataMap.values()) { 
            if (summary.getDriverCnt().intValue() != 0)
                dataList.add(summary);
        }

        setMainDataset(dataList);
        
        addParameter("REPORT_START_DATE", dateTimeFormatter.print(interval.getStart()));
        addParameter("REPORT_END_DATE", dateTimeFormatter.print(interval.getEnd()));
        
        
    }

    @Override
    protected void updateSummaryDriverCount(ViolationsSummary summary, Driver driver) {
        summary.setDriverCnt(summary.getDriverCnt() + 1);
    }

}
