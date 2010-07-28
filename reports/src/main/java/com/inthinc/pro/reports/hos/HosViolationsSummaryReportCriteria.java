package com.inthinc.pro.reports.hos;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.hos.model.HOSRec;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.hos.model.RuleViolationTypes;
import com.inthinc.hos.model.ViolationsData;
import com.inthinc.hos.rules.RuleSetFactory;
import com.inthinc.hos.violations.DailyViolations;
import com.inthinc.hos.violations.ShiftViolations;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.model.GroupHierarchy;
import com.inthinc.pro.reports.hos.model.HosGroupMileage;
import com.inthinc.pro.reports.hos.model.HosViolationsSummary;

public class HosViolationsSummaryReportCriteria extends ReportCriteria {

    private static final Logger logger = Logger.getLogger(HosViolationsSummaryReportCriteria.class);
    
    private GroupDAO groupDAO;
    private DriverDAO driverDAO;
    private DateTimeFormatter dateTimeFormatter;
    
    public HosViolationsSummaryReportCriteria(Locale locale) 
    {
        super(ReportType.HOS_VIOLATIONS_SUMMARY_REPORT, "", locale);
        dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy").withLocale(locale);
    }
    
    public void init(Integer groupID, Interval interval)
    {
        Group topGroup = groupDAO.findByID(groupID);
        List<Group> groupList = groupDAO.getGroupHierarchy(topGroup.getAccountID(), topGroup.getGroupID());
        List<Driver> driverList = driverDAO.getDrivers(groupID);
        Map<Driver, List<HOSRecord>> driverHOSRecordMap = new HashMap<Driver, List<HOSRecord>> ();
        
        if (driverList != null) {
            for (Driver driver : driverList) {
                if (driver.getDot() == null)
                    continue;
    //            Date hosLogQueryStartDate = DateUtil.getStartOfDayForDaysBack(parseDate(startDate), RuleSetFactory.getDaysBackForRuleSetType(driverDotRuleType), timeZone);
    //            Date hosLogQueryEndDate = DateUtil.getEndOfDayForDaysForward(parseDate(endDate), RuleSetFactory.getDaysForwardForRuleSetType(driverDotRuleType), timeZone);
                DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
                DateTime queryStart = new DateTime(interval.getStart(), dateTimeZone).minusDays(RuleSetFactory.getDaysBackForRuleSetType(driver.getDot()));
                DateTime queryEnd = new DateTime(interval.getEnd(), dateTimeZone).minusDays(RuleSetFactory.getDaysForwardForRuleSetType(driver.getDot()));
                // TODO:
    //            List<HOSRecord> hosRecordList = hosDAO.getHosRecords(driver.getDriverID(), new Interval(queryStart, queryEnd));
                List<HOSRecord> hosRecordList = new ArrayList<HOSRecord>();
                driverHOSRecordMap.put(driver, hosRecordList);
//                List<HOSRec> recListForViolationsCalc = getRecListFromLogList(hosRecordList, queryEnd.toDate(), !(driver.getDot().equals(RuleSetType.NON_DOT)));
                
//                driverHOSRecMap.put(driver, getRecListFromLogList(hosRecordList, queryEnd.toDate(), !(driver.getDot().equals(RuleSetType.NON_DOT))));
                
            }
        }
        
        // TODO:
        List<HosGroupMileage> groupMileageList = new ArrayList<HosGroupMileage>();
        List<HosGroupMileage> groupNoDriverMileageList = new ArrayList<HosGroupMileage>();

        initDataSet(interval, topGroup, groupList, driverHOSRecordMap, groupMileageList, groupNoDriverMileageList);

    }
    
    void initDataSet(Interval interval, Group topGroup,  List<Group> groupList, Map<Driver, List<HOSRecord>> driverHOSRecordMap,
            List<HosGroupMileage> groupMileageList, List<HosGroupMileage> groupNoDriverMileageList)
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
            RuleSetType driverDOTType = driver.getDot();
            DateTime reportEndDate = new LocalDate(interval.getEnd()).toDateTimeAtStartOfDay(driverTimeZone).plusDays(1).minusSeconds(1);
            List<HOSRec> recListForViolationsCalc = getRecListFromLogList(entry.getValue(), reportEndDate.toDate(), !(driverDOTType.equals(RuleSetType.NON_DOT)));

            HosViolationsSummary summary = findSummary(groupHierarchy, topGroup, dataMap, driver.getGroupID());
            if (summary == null) {
                continue;
            }
            
            // violations
            List<ViolationsData> dailyViolations = new DailyViolations().getDailyHosViolationsForReport(interval,
                    driverTimeZone.toTimeZone(),
                    driverDOTType, 
                    null, // RuleSetType overrideDOTType, TODO 
                    recListForViolationsCalc);
            updateSummary(summary, dailyViolations);

            List<ViolationsData> shiftViolations = new ShiftViolations().getHosViolationsInTimeFrame(
                    interval, driverTimeZone.toTimeZone(),
                    driverDOTType, 
                    null, // RuleSetType overrideDOTType, TODO 
                    recListForViolationsCalc);
            updateSummary(summary, shiftViolations);

            updateSummaryDriverCount(summary, driver);
        }
        
        for (HosGroupMileage groupMileage : groupMileageList) {
            HosViolationsSummary summary = findSummary(groupHierarchy, topGroup, dataMap, groupMileage.getGroupID());
            if (summary == null) {
                continue;
            }
            summary.setTotalMiles(summary.getTotalMiles()+groupMileage.getDistance());
            
        }
        for (HosGroupMileage groupMileage : groupNoDriverMileageList) {
            HosViolationsSummary summary = findSummary(groupHierarchy, topGroup, dataMap, groupMileage.getGroupID());
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

    private HosViolationsSummary findSummary(GroupHierarchy groupHierarchy, Group topGroup, Map<Integer, HosViolationsSummary> dataMap, Integer groupID) {
        Group driverGroup = groupHierarchy.getGroup(groupID);
        if (driverGroup == null) {
            logger.error("Group is null for groupID: " + groupID);
            return null;
        }
        Group topAncestor = (groupID.equals(topGroup.getGroupID())) ? topGroup : groupHierarchy.getTopAncestor(driverGroup, groupHierarchy.getChildren(topGroup));
        if (topAncestor == null) {
            logger.error("Group topAncestor is null for group: " + driverGroup.getGroupID());
            return null;
        }
        HosViolationsSummary summary = dataMap.get(topAncestor.getGroupID());
        if (summary == null) {
            logger.error("HosViolationsSummary  is null for group: " + groupID);
            return null;
        }
        return summary;
    }

    private void updateSummaryDriverCount(HosViolationsSummary summary, Driver driver) {
        if (driver.getDot() != null && driver.getDot() != RuleSetType.NON_DOT)
            summary.setDriverCnt(summary.getDriverCnt() + 1);
        
    }

    private void updateSummary(HosViolationsSummary summary, List<ViolationsData> violations) {
        
        if (violations == null)
            return;

        for (ViolationsData data : violations) {
            
            for (Entry<RuleViolationTypes, Long> entry : data.getViolationMap().entrySet()) {
                RuleViolationTypes violationType = entry.getKey();
                int minutes = entry.getValue() == null ? 0 : entry.getValue().intValue();
                
                summary.updateMinutes(violationType, minutes);
            }
        }
    }

    private List<HOSRec> getRecListFromLogList(List<HOSRecord> hosRecList, Date endDate, boolean isDriverDOT)
    {
        List<HOSRec> recList = new ArrayList<HOSRec>();
        for (HOSRecord hosRecord : hosRecList)
        {
            if (hosRecord.getStatus() == null || (hosRecord.getDeleted() != null && hosRecord.getDeleted()))
                continue;
            long totalRealMinutes = DateUtil.deltaMinutes(hosRecord.getLogTime(), endDate);
            endDate = hosRecord.getLogTime();
            HOSRec hosRec = new HOSRec(hosRecord.getHosLogID().toString(), 
                    hosRecord.getStatus(), 
                    totalRealMinutes,
                    hosRecord.getLogTime(), 
                    hosRecord.getTimeZone(),
                    hosRecord.getDriverDotType(),
                    hosRecord.getVehicleName(),
                    hosRecord.getSingleDriver(),
                    hosRecord.getVehicleIsDOT() && !isDriverDOT);
            
            recList.add(hosRec);

        }
        return recList;
    }



}
