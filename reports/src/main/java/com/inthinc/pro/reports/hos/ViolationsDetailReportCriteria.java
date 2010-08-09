package com.inthinc.pro.reports.hos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.model.GroupHierarchy;
import com.inthinc.pro.reports.hos.model.Violation;
import com.inthinc.pro.reports.hos.model.ViolationsDetail;
import com.inthinc.pro.reports.hos.model.ViolationsDetailRaw;

public abstract class ViolationsDetailReportCriteria extends HosRecordReportCriteria {

    private static final Logger logger = Logger.getLogger(ViolationsDetailReportCriteria.class);
    
    protected GroupDAO groupDAO;
    protected DriverDAO driverDAO;
    
    private static final String   DISPLAY_DATE_FORMAT     = "MM/dd/yy HH:mm z";
    protected DateTimeFormatter displayDateTimeFormatter;
    

    
    public ViolationsDetailReportCriteria(ReportType reportType, Locale locale) 
    {
        super(reportType, locale);
        displayDateTimeFormatter = DateTimeFormat.forPattern(DISPLAY_DATE_FORMAT).withLocale(locale);
    }

    
    public void init(List<Driver> driverList, Interval interval)
    {
//        Group topGroup = groupDAO.findByID(groupID);
//        List<Group> groupList = groupDAO.getGroupHierarchy(topGroup.getAccountID(), topGroup.getGroupID());
//        List<Driver> driverList = driverDAO.getDrivers(groupID);
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
        
//        initDataSet(interval, driverHOSRecordMap);

    }
    
    void initDataSet(Interval interval, Group topGroup,  List<Group> groupList, Map<Driver, List<HOSRecord>> driverHOSRecordMap)
    {
        GroupHierarchy groupHierarchy = new GroupHierarchy(topGroup, groupList);

        List<ViolationsDetailRaw> violationDetailList = new ArrayList<ViolationsDetailRaw>();
        
        for (Entry<Driver, List<HOSRecord>> entry : driverHOSRecordMap.entrySet()) {
            Driver driver = entry.getKey();
            DateTimeZone driverTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
            RuleSetType driverDOTType = driver.getDot();
            DateTime reportEndDate = new LocalDate(interval.getEnd()).toDateTimeAtStartOfDay(driverTimeZone).plusDays(1).minusSeconds(1);
            List<HOSRec> recListForViolationsCalc = getRecListFromLogList(entry.getValue(), reportEndDate.toDate(), !(driverDOTType.equals(RuleSetType.NON_DOT)));

            // violations
            addDriverViolations(interval, groupHierarchy, violationDetailList, driver, driverTimeZone, driverDOTType, recListForViolationsCalc);

        }
        Collections.sort(violationDetailList);
        setMainDataset(violationDetailList);
        
        addParameter("REPORT_START_DATE", dateTimeFormatter.print(interval.getStart()));
        addParameter("REPORT_END_DATE", dateTimeFormatter.print(interval.getEnd()));
        addParameter("REPORT_TYPE", getReport().getName());
        
//        setUseMetric(true);
        
    }


    protected abstract void addDriverViolations(Interval interval, GroupHierarchy groupHierarchy, List<ViolationsDetailRaw> violationDetailList, 
            Driver driver, DateTimeZone driverTimeZone,
            RuleSetType driverDOTType, List<HOSRec> recListForViolationsCalc);


    protected void addViolations(List<ViolationsDetailRaw> violationDetailList, Driver driver, List<ViolationsData> violations, GroupHierarchy groupHierarchy) {
        if (violations == null || violations.size() == 0)
            return;
        
        for (ViolationsData violationData : violations) {

            List<Violation> violationList = new ArrayList<Violation>();
            for (Entry<RuleViolationTypes, Long> violationEntry : violationData.getViolationMap().entrySet()) {
                if (includeViolation(violationEntry.getKey(), violationEntry.getValue()))
                        violationList.add(new Violation(violationEntry.getKey(), violationEntry.getValue()));
//System.out.println("adding violation type: " + violationEntry.getKey());                
                
            }
            if (violationList.isEmpty())
                continue;
            ViolationsDetailRaw hosViolationsDetail = new ViolationsDetailRaw();
            hosViolationsDetail.setDriverName(driver.getPerson().getFullNameLastFirst());
            hosViolationsDetail.setEmployeeId(driver.getPerson().getEmpid());
            hosViolationsDetail.setGroupName(groupHierarchy.getFullName(groupHierarchy.getGroup(driver.getGroupID())));
            hosViolationsDetail.setNotificationTime(violationData.getHosViolationRec().getLogTimeDate());
            hosViolationsDetail.setVehicleId(violationData.getHosViolationRec().getVehicleID());
            hosViolationsDetail.setRuleType(violationData.getHosViolationRec().getRuleType());
            hosViolationsDetail.setTimeStr(displayDateTimeFormatter.print(new DateTime(violationData.getHosViolationRec().getLogTimeDate(), DateTimeZone.forTimeZone(violationData.getHosViolationRec().getLogTimeZone()))));
            hosViolationsDetail.setViolationsList(violationList);
            violationDetailList.add(hosViolationsDetail);
            
        }
    }
    
    protected boolean includeViolation(RuleViolationTypes type, Long minutes)
    {
        return minutes > 0l && type.isReportable();
        
    }

}
