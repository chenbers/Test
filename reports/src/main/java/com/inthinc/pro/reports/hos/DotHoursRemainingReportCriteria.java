package com.inthinc.pro.reports.hos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.hos.adjusted.HOSAdjustedList;
import com.inthinc.hos.model.HOSOrigin;
import com.inthinc.hos.model.HOSRec;
import com.inthinc.hos.model.HOSRecAdjusted;
import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.MinutesRemainingData;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.hos.rules.HOSRules;
import com.inthinc.hos.rules.RuleSetFactory;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.model.DotHoursRemaining;

public class DotHoursRemainingReportCriteria extends HosRecordReportCriteria {

    private static final Logger logger = Logger.getLogger(HosDriverDOTLogReportCriteria.class);
    
    protected DateTimeFormatter dayFormatter;    

    public static final int     DAYS_BACK=14;
    
    public DotHoursRemainingReportCriteria(Locale locale) {
        super(ReportType.DOT_HOURS_REMAINING, locale);
        dayFormatter = DateTimeFormat.forPattern("MM/dd/yy").withLocale(locale);
    }
    
    public void init(Integer groupID)
    {
/*        
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
                // TODO:
    //            List<HOSRecord> hosRecordList = hosDAO.getHosRecords(driver.getDriverID(), new Interval(queryStart, queryEnd));
                List<HOSRecord> hosRecordList = new ArrayList<HOSRecord>();
                driverHOSRecordMap.put(driver, hosRecordList);
//                List<HOSRec> recListForViolationsCalc = getRecListFromLogList(hosRecordList, queryEnd.toDate(), !(driver.getDot().equals(RuleSetType.NON_DOT)));
                
//                driverHOSRecMap.put(driver, getRecListFromLogList(hosRecordList, queryEnd.toDate(), !(driver.getDot().equals(RuleSetType.NON_DOT))));
                
            }
        }
        
//        initDataSet(interval, driverHOSRecordMap);
*/
    }
    
    // TODO: GROUP STUFF IS WRONG
    void initDataSet(Group group,  Map<Driver, List<HOSRecord>> driverHOSRecordMap, DateTime currentDate)
    {
        List<DotHoursRemaining> dotHoursRemainingList = new ArrayList<DotHoursRemaining>();

        Interval interval = new Interval(new DateMidnight(currentDate.minusDays(14)), new DateMidnight(currentDate).plusDays(1));
        
        for (Entry<Driver, List<HOSRecord>> entry : driverHOSRecordMap.entrySet()) {
            Driver driver = entry.getKey();
            List<DateTime> dayList = getDayList(interval, DateTimeZone.forTimeZone(driver.getPerson().getTimeZone()));
            fillInDotHoursRemainingData(dotHoursRemainingList, group, driver, dayList, entry.getValue(), currentDate);
        }
    

        Collections.sort(dotHoursRemainingList);
        setMainDataset(dotHoursRemainingList);
        
        
    }
    
    // TODO: move to utility class and unit test it
    public List<DateTime> getDayList(Interval interval, DateTimeZone dateTimeZone)
    {
        List<DateTime> dayList = new ArrayList<DateTime>();
        
        for (DateTime intervalDay = interval.getStart(); intervalDay.isBefore(interval.getEnd()); intervalDay = intervalDay.plusDays(1)) {
            LocalDate localDate = new LocalDate(intervalDay);
            DateTime day = localDate.toDateTimeAtStartOfDay(dateTimeZone);
            dayList.add(day);
        }
        return dayList;
    }
    private void fillInDotHoursRemainingData(List<DotHoursRemaining> dataList, Group group, Driver driver, List<DateTime> dayList, List<HOSRecord> hosRecordList, DateTime currentDate )
    {

       HOSRules rules = RuleSetFactory.getRulesForRuleSetType(driver.getDot());
       HOSAdjustedList hosAdjustedList = getAdjustedListFromLogList(hosRecordList);
       List<HOSRec> recListForHoursRemainingCalc = getRecListFromLogList(hosRecordList, currentDate.toDate(), !(driver.getDot().equals(RuleSetType.NON_DOT)));
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
//logger.info("     " + log.getStatus() + " " + log.getStartIncrement() + " " + log.getTotalIncrements() + " " + log.getAdjustedTime());           
               
               if (log.getStatus().equals(HOSStatus.DRIVING))
               {
                   drivingIncrements += log.getTotalIncrements();
               }
               else if (log.getStatus().equals(HOSStatus.ON_DUTY) || log.getStatus().equals(HOSStatus.ON_DUTY_OCCUPANT))
               {
                   onDutyIncrements += log.getTotalIncrements();
               }
           }
           dataList.add(new DotHoursRemaining(group.getName(), 
                   driver.getDriverID(), driver.getPerson().getFullNameLastFirst(),  driver.getDot(),
                   minutesRemaining, 
                   dayFormatter.print(day), day.toDate(), HOSStatus.DRIVING, drivingIncrements*15l));
           dataList.add(new DotHoursRemaining(group.getName(), 
                   driver.getDriverID(), driver.getPerson().getFullNameLastFirst(),  driver.getDot(),
                   minutesRemaining,dayFormatter.print(day), day.toDate(), HOSStatus.ON_DUTY, onDutyIncrements*15l));
       }
    }
    


    // TODO: this is also in ddl report criteria should move to common location
    private HOSAdjustedList getAdjustedListFromLogList(List<HOSRecord> hosRecList)
    {
        List<HOSRecAdjusted> adjustedList = new ArrayList<HOSRecAdjusted>();
        for (HOSRecord hosRec : hosRecList)
        {
            if (hosRec.getStatus() == null || !hosRec.getStatus().isGraphable() || hosRec.getDeleted())
                continue;
            HOSRecAdjusted hosDDLRec = new HOSRecAdjusted(hosRec.getHosLogID().toString(), 
                    hosRec.getStatus(), 
                    hosRec.getLogTime(), 
                    hosRec.getTimeZone());
            
            hosDDLRec.setEdited(hosRec.getEdited() || hosRec.getOrigin().equals(HOSOrigin.PORTAL));
            hosDDLRec.setServiceID(hosRec.getServiceID());
            hosDDLRec.setTrailerID(hosRec.getTrailerID());
            hosDDLRec.setRuleType(hosRec.getDriverDotType());

            adjustedList.add(hosDDLRec);

        }
        Collections.reverse(adjustedList);
        return new HOSAdjustedList(adjustedList);

    }

}

