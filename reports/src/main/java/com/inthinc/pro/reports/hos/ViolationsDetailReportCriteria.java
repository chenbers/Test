package com.inthinc.pro.reports.hos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.hos.model.HOSRec;
import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.hos.model.RuleViolationTypes;
import com.inthinc.hos.model.ViolationsData;
import com.inthinc.hos.rules.RuleSetFactory;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.dao.util.HOSUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.GroupListReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.model.Violation;
import com.inthinc.pro.reports.hos.model.ViolationsDetailRaw;
import com.inthinc.pro.reports.util.DateTimeUtil;

public abstract class ViolationsDetailReportCriteria extends GroupListReportCriteria {

    
    protected GroupDAO groupDAO;
    protected HOSDAO hosDAO;

    private static final String   DISPLAY_DATE_FORMAT     = "MM/dd/yy HH:mm z";
    protected DateTimeFormatter displayDateTimeFormatter;
    protected DateTimeFormatter dateTimeFormatter;

    
    public ViolationsDetailReportCriteria(ReportType reportType, Locale locale) 
    {
        super(reportType, locale);
        displayDateTimeFormatter = DateTimeFormat.forPattern(DISPLAY_DATE_FORMAT).withLocale(locale);
        dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy").withLocale(locale);
    }

    public void init(GroupHierarchy accountGroupHierarchy, Integer driverID, Interval interval)
    {
        List<Driver> driverList = new ArrayList<Driver>();

        driverList.add(getDriverDAO().findByID(driverID));
        initDrivers(accountGroupHierarchy, driverList, interval);
    }
    public void init(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval)
    {
        List<Group> reportGroupList = getReportGroupList(groupIDList, accountGroupHierarchy);
        List<Driver> reportDriverList = getReportDriverList(reportGroupList);


        initDrivers(accountGroupHierarchy, reportDriverList, interval);
    }
    
    
    private void initDrivers(GroupHierarchy accountGroupHierarchy, List<Driver> driverList, Interval interval)
    {
        Map<Driver, List<HOSRecord>> driverHOSRecordMap = new HashMap<Driver, List<HOSRecord>> ();
        
        if (driverList != null) {
            for (Driver driver : driverList) {
                if (driver.getDot() == null)
                    continue;
                DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
                Interval queryInterval = DateTimeUtil.getExpandedInterval(interval, dateTimeZone, RuleSetFactory.getDaysBackForRuleSetType(driver.getDot()), RuleSetFactory.getDaysForwardForRuleSetType(driver.getDot()));
                List<HOSRecord> hosRecordList = hosDAO.getHOSRecords(driver.getDriverID(), queryInterval, false);
                Collections.sort(hosRecordList);


                driverHOSRecordMap.put(driver, getFilteredList(hosRecordList, getHOSStatusFilterList()));
            }
        }
        
        initDataSet(interval, accountGroupHierarchy, driverHOSRecordMap);

    }

    private List<HOSRecord> getFilteredList(List<HOSRecord> hosRecords, List<HOSStatus> hosStatusFilterList) {
        List<HOSRecord> filteredList = new ArrayList<HOSRecord>();
        for (HOSRecord hosRecord : hosRecords)
            if (hosStatusFilterList.contains(hosRecord.getStatus()))
                    filteredList.add(hosRecord);
        return filteredList;
    }

    protected List<HOSStatus> getHOSStatusFilterList() {
        List<HOSStatus> statusFilterList = new ArrayList<HOSStatus>();
        statusFilterList.add(HOSStatus.OFF_DUTY);
        statusFilterList.add(HOSStatus.SLEEPER); 
        statusFilterList.add(HOSStatus.DRIVING);
        statusFilterList.add(HOSStatus.ON_DUTY);
        statusFilterList.add(HOSStatus.OFF_DUTY_AT_WELL); 
        statusFilterList.add(HOSStatus.ON_DUTY_OCCUPANT); 
        statusFilterList.add(HOSStatus.OFF_DUTY_OCCUPANT);
        statusFilterList.add(HOSStatus.HOS_DERERRAL);
        statusFilterList.add(HOSStatus.HOS_PERSONALTIME);
        statusFilterList.add(HOSStatus.TRAVELTIME_OCCUPANT);
        return statusFilterList;
    }
    void initDataSet(Interval interval, GroupHierarchy accountGroupHierarchy, Map<Driver, List<HOSRecord>> driverHOSRecordMap)
    {
        List<ViolationsDetailRaw> violationDetailList = new ArrayList<ViolationsDetailRaw>();
        
        DateTime currentTime = new DateTime(DateTimeZone.UTC);
        
        for (Entry<Driver, List<HOSRecord>> entry : driverHOSRecordMap.entrySet()) {
            Driver driver = entry.getKey();
            DateTimeZone driverTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
            RuleSetType driverDOTType = driver.getDot();
            DateTime reportEndDate = new LocalDate(interval.getEnd()).toDateTimeAtStartOfDay(driverTimeZone).plusDays(1).minusSeconds(1);
            if (reportEndDate.isAfterNow())
                reportEndDate = currentTime;
            
            List<HOSRec> recListForViolationsCalc = HOSUtil.getRecListFromLogList(entry.getValue(), reportEndDate.toDate(), !(driverDOTType.equals(RuleSetType.NON_DOT)));

            // violations
            addDriverViolations(interval, accountGroupHierarchy, violationDetailList, driver, driverTimeZone, driverDOTType, recListForViolationsCalc);

        }
        Collections.sort(violationDetailList);
        setMainDataset(violationDetailList);
        
        addParameter("REPORT_START_DATE", dateTimeFormatter.print(interval.getStart()));
        addParameter("REPORT_END_DATE", dateTimeFormatter.print(interval.getEnd()));
        addParameter("REPORT_TYPE", getReport().getName());
        
//        setUseMetric(true);
        
    }


    protected abstract void addDriverViolations(Interval interval, GroupHierarchy accountGroupHierarchy, List<ViolationsDetailRaw> violationDetailList, 
            Driver driver, DateTimeZone driverTimeZone,
            RuleSetType driverDOTType, List<HOSRec> recListForViolationsCalc);


    protected void addViolations(List<ViolationsDetailRaw> violationDetailList, Driver driver, List<ViolationsData> violations, GroupHierarchy groupHierarchy) {
        if (violations == null || violations.size() == 0)
            return;
        
        for (ViolationsData violationData : violations) {

            List<Violation> violationList = new ArrayList<Violation>();
            for (Entry<RuleViolationTypes, Long> violationEntry : violationData.getViolationMap().entrySet()) {
                if (includeViolation(violationEntry.getKey(), violationEntry.getValue()))
                        violationList.add(new Violation(violationData.getHosViolationRec().getRuleType(), violationEntry.getKey(), violationEntry.getValue()));
            }
            if (violationList.isEmpty())
                continue;
            ViolationsDetailRaw hosViolationsDetail = new ViolationsDetailRaw();
            hosViolationsDetail.setDriverName(driver.getPerson().getFullNameLastFirst());
            hosViolationsDetail.setEmployeeId(driver.getPerson().getEmpid());
            hosViolationsDetail.setGroupName(getFullGroupName(groupHierarchy, driver.getGroupID()));
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

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }


    public HOSDAO getHosDAO() {
        return hosDAO;
    }


    public void setHosDAO(HOSDAO hosDAO) {
        this.hosDAO = hosDAO;
    }

}
