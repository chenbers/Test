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
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.model.GroupHierarchy;
import com.inthinc.pro.reports.hos.model.Violation;
import com.inthinc.pro.reports.hos.model.ViolationsDetailRaw;
import com.inthinc.pro.reports.hos.util.HOSUtil;

public abstract class ViolationsDetailReportCriteria extends ReportCriteria {

    
    protected GroupDAO groupDAO;
    protected DriverDAO driverDAO;
    protected HOSDAO hosDAO;

    private static final String   DISPLAY_DATE_FORMAT     = "MM/dd/yy HH:mm z";
    protected DateTimeFormatter displayDateTimeFormatter;
    protected DateTimeFormatter dateTimeFormatter;

    
    public ViolationsDetailReportCriteria(ReportType reportType, Locale locale) 
    {
        super(reportType, "", locale);
        displayDateTimeFormatter = DateTimeFormat.forPattern(DISPLAY_DATE_FORMAT).withLocale(locale);
        dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy").withLocale(locale);
    }

    public void init(Integer groupID, Interval interval)
    {
        Group topGroup = groupDAO.findByID(groupID);
        List<Group> groupList = groupDAO.getGroupHierarchy(topGroup.getAccountID(), topGroup.getGroupID());
        List<Driver> driverList = driverDAO.getDrivers(groupID);

        init(topGroup, groupList, driverList, interval);
    }
    
    public void init(List<Integer> driverIDList, Interval interval)
    {
        Group topGroup = null;
        List<Group> groupList = null;
        List<Driver> driverList = new ArrayList<Driver>();
        for (Integer driverID : driverIDList) {
            Driver driver = driverDAO.findByID(driverID);
            if (topGroup == null) {
                groupList = groupDAO.getGroupsByAcctID(driver.getPerson().getAcctID());
                for (Group group : groupList) {
                    if (group.getParentID() == null || group.getParentID() == -1) {
                        topGroup = group;
                        break;
                    }
                }
            }
            driverList.add(driver);
        }
        init(topGroup, groupList, driverList, interval);
    }
    
    private void init(Group topGroup, List<Group> groupList, List<Driver> driverList, Interval interval)
    {
        Map<Driver, List<HOSRecord>> driverHOSRecordMap = new HashMap<Driver, List<HOSRecord>> ();
        
        if (driverList != null) {
            for (Driver driver : driverList) {
                if (driver.getDot() == null)
                    continue;
                DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
                DateTime queryStart = new DateTime(interval.getStart(), dateTimeZone).minusDays(RuleSetFactory.getDaysBackForRuleSetType(driver.getDriverDOTType()));
                DateTime queryEnd = new DateTime(interval.getEnd(), dateTimeZone).minusDays(RuleSetFactory.getDaysForwardForRuleSetType(driver.getDriverDOTType()));
                driverHOSRecordMap.put(driver, hosDAO.getHOSRecords(driver.getDriverID(), new Interval(queryStart, queryEnd), getHOSStatusFilterList()));
            }
        }
        
        initDataSet(interval, topGroup, groupList, driverHOSRecordMap);

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
        return statusFilterList;
    }

    void initDataSet(Interval interval, Group topGroup,  List<Group> groupList, Map<Driver, List<HOSRecord>> driverHOSRecordMap)
    {
        GroupHierarchy groupHierarchy = new GroupHierarchy(topGroup, groupList);

        List<ViolationsDetailRaw> violationDetailList = new ArrayList<ViolationsDetailRaw>();
        
        DateTime currentTime = new DateTime(DateTimeZone.UTC);
        
        for (Entry<Driver, List<HOSRecord>> entry : driverHOSRecordMap.entrySet()) {
            Driver driver = entry.getKey();
            DateTimeZone driverTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
            RuleSetType driverDOTType = driver.getDriverDOTType();
            DateTime reportEndDate = new LocalDate(interval.getEnd()).toDateTimeAtStartOfDay(driverTimeZone).plusDays(1).minusSeconds(1);
            if (reportEndDate.isAfterNow())
                reportEndDate = currentTime;
            
            List<HOSRec> recListForViolationsCalc = HOSUtil.getRecListFromLogList(entry.getValue(), reportEndDate.toDate(), !(driverDOTType.equals(RuleSetType.NON_DOT)));

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

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    public HOSDAO getHosDAO() {
        return hosDAO;
    }


    public void setHosDAO(HOSDAO hosDAO) {
        this.hosDAO = hosDAO;
    }

}
