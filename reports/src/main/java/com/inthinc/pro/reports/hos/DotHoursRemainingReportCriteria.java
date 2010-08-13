package com.inthinc.pro.reports.hos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.hos.adjusted.HOSAdjustedList;
import com.inthinc.hos.model.HOSRec;
import com.inthinc.hos.model.HOSRecAdjusted;
import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.MinutesRemainingData;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.hos.rules.HOSRules;
import com.inthinc.hos.rules.RuleSetFactory;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.model.DotHoursRemaining;
import com.inthinc.pro.reports.hos.model.GroupHierarchy;
import com.inthinc.pro.reports.hos.util.HOSUtil;
import com.inthinc.pro.reports.util.DateTimeUtil;

public class DotHoursRemainingReportCriteria extends ReportCriteria {

    private DriverDAO driverDAO;
    private GroupDAO groupDAO;
    private HOSDAO hosDAO;

    
    protected DateTimeFormatter dayFormatter;    

    public static final int     DAYS_BACK=14;
    
    public DotHoursRemainingReportCriteria(Locale locale) {
        super(ReportType.DOT_HOURS_REMAINING, "", locale);
        dayFormatter = DateTimeFormat.forPattern("MM/dd/yy").withLocale(locale);
    }
    
    public void init(Integer groupID)
    {
        Group topGroup = groupDAO.findByID(groupID);
        List<Group> groupList = groupDAO.getGroupHierarchy(topGroup.getAccountID(), topGroup.getGroupID());
        List<Driver> driverList = driverDAO.getAllDrivers(groupID);
        
        DateTime currentDate = new DateTime(); 
        
        Map<Driver, List<HOSRecord>> driverHOSRecordMap = new HashMap<Driver, List<HOSRecord>> ();
        for (Driver driver : driverList) {
            if (driver.getDot() == null || driver.getDot().equals(RuleSetType.NON_DOT))
                continue;
            Interval interval = DateTimeUtil.getInterval(currentDate, RuleSetFactory.getDaysBackForRuleSetType(driver.getDot()), 0); 
            driverHOSRecordMap.put(driver, hosDAO.getHOSRecords(driver.getDriverID(), interval));
        }
        
        initDataSet(topGroup, groupList, driverHOSRecordMap, currentDate);
    }

    void initDataSet(Group topGroup, List<Group> groupList, Map<Driver, List<HOSRecord>> driverHOSRecordMap, DateTime currentDate)
    {
        GroupHierarchy groupHierarchy = new GroupHierarchy(topGroup, groupList);

        List<DotHoursRemaining> dotHoursRemainingList = new ArrayList<DotHoursRemaining>();

        Interval interval = new Interval(new DateMidnight(currentDate.minusDays(14)), new DateMidnight(currentDate).plusDays(1));
        
        for (Entry<Driver, List<HOSRecord>> entry : driverHOSRecordMap.entrySet()) {
            Driver driver = entry.getKey();
            List<DateTime> dayList = DateTimeUtil.getDayList(interval, DateTimeZone.forTimeZone(driver.getPerson().getTimeZone()));
            fillInDotHoursRemainingData(dotHoursRemainingList, groupHierarchy.getFullName(driver.getGroupID()), driver, dayList, entry.getValue(), currentDate);
        }
    

        Collections.sort(dotHoursRemainingList);
        setMainDataset(dotHoursRemainingList);
        
        
    }
    
    private void fillInDotHoursRemainingData(List<DotHoursRemaining> dataList, String groupName, Driver driver, List<DateTime> dayList, List<HOSRecord> hosRecordList, DateTime currentDate )
    {

       HOSRules rules = RuleSetFactory.getRulesForRuleSetType(driver.getDot());
       HOSAdjustedList hosAdjustedList = HOSUtil.getAdjustedListFromLogList(hosRecordList);
       List<HOSRec> recListForHoursRemainingCalc = HOSUtil.getRecListFromLogList(hosRecordList, currentDate.toDate(), !(driver.getDot().equals(RuleSetType.NON_DOT)));
       MinutesRemainingData data = rules.getDOTMinutesRemaining(recListForHoursRemainingCalc, currentDate.toDate());
       if (data == null) {
           return;
       }
       long minutesRemaining = data.getAllDOTMinutesRemaining();
       
       for (DateTime day : dayList)
       {
           List<HOSRecAdjusted> dayLogList = hosAdjustedList.getAdjustedListForDay(day.toDate(), currentDate.toDate(), true);
           int drivingIncrements = 0;
           int onDutyIncrements = 0;
           for (HOSRecAdjusted log : dayLogList)
           {
               if (log.getStatus().equals(HOSStatus.DRIVING))
               {
                   drivingIncrements += log.getTotalIncrements();
               }
               else if (log.getStatus().equals(HOSStatus.ON_DUTY) || log.getStatus().equals(HOSStatus.ON_DUTY_OCCUPANT))
               {
                   onDutyIncrements += log.getTotalIncrements();
               }
           }
           dataList.add(new DotHoursRemaining(groupName, 
                   driver.getDriverID(), driver.getPerson().getFullNameLastFirst(),  driver.getDot(),
                   minutesRemaining, 
                   dayFormatter.print(day), day.toDate(), HOSStatus.DRIVING, drivingIncrements*15l));
           dataList.add(new DotHoursRemaining(groupName, 
                   driver.getDriverID(), driver.getPerson().getFullNameLastFirst(),  driver.getDot(),
                   minutesRemaining,dayFormatter.print(day), day.toDate(), HOSStatus.ON_DUTY, onDutyIncrements*15l));
       }
    }
    
    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
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

