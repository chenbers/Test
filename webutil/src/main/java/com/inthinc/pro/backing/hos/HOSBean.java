package com.inthinc.pro.backing.hos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.richfaces.model.Ordering;

import com.inthinc.hos.model.DebugInfo;
import com.inthinc.hos.model.HOSRec;
import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.hos.model.RuleViolationTypes;
import com.inthinc.hos.rules.HOSRules;
import com.inthinc.hos.rules.RuleSetFactory;
import com.inthinc.pro.backing.BaseBean;
import com.inthinc.pro.backing.dao.model.Result;
import com.inthinc.pro.backing.hos.ui.DateRange;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.dao.util.HOSUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.hos.HOSRecord;

public class HOSBean extends BaseBean {
    
    DriverDAO driverDAO;
    HOSDAO hosDAO;
    Integer driverID;
    DateRange dateRange = new DateRange(Locale.getDefault());
    String errorMessage;
    String driverName;
    DateTimeZone driverTimeZone;
    String selected;
    List<ViolationInfo> violationInfoList;
    
    List<String> columnHeaders;
    List<List<Result>> records;
    int recordCount = 0;
    Map<String, Object> sortOrder;
    
    List<String> logsColumnHeaders;
    List<List<Result>> logsRecords;
    Map<String, Object> logsSortOrder;
    
    private static DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("MM/dd/yy HH:mm:ss (z)");
    
    public void evaluateAction() {
        recordCount = 0;
        errorMessage = null;
        selected = null;
        if (dateRange.getBadDates() != null) {
            errorMessage = dateRange.getBadDates();
            return;
        }
        Driver driver = driverDAO.findByID(driverID);
        if (driver == null || driver.getPerson() == null) {
            errorMessage = "Unable to find a driver for ID.";
            return;
        }
        dateRange.setTimeZone(driver.getPerson().getTimeZone());
        Interval interval = dateRange.getInterval();
        driverName = driver.getPerson().getFullName();
        
        driverTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
        Interval queryInterval = getExpandedInterval(interval, driverTimeZone, RuleSetFactory.getDaysBackForRuleSetType(driver.getDot()), RuleSetFactory.getDaysForwardForRuleSetType(driver.getDot()));
        List<HOSRecord> hosRecordList = hosDAO.getHOSRecords(driver.getDriverID(), queryInterval, false);
        Collections.sort(hosRecordList);

        List<HOSRecord> filteredhosRecordList = getFilteredList(hosRecordList, getHOSStatusFilterList());

        RuleSetType driverDOTType = driver.getDot();
        if (driverDOTType == null)
            driverDOTType = RuleSetType.NON_DOT;
        DateTime reportEndDate = new LocalDate(interval.getEnd()).toDateTimeAtStartOfDay(driverTimeZone).plusDays(1).minusSeconds(1);
        if (reportEndDate.isAfterNow())
            reportEndDate = new DateTime();
        
        List<HOSRec> recListForViolationsCalc = HOSUtil.getRecListFromLogList(filteredhosRecordList, reportEndDate.toDate(), !(driverDOTType.equals(RuleSetType.NON_DOT)));

        
        violationInfoList = getViolationInfo(interval, driver, driverTimeZone, driverDOTType, recListForViolationsCalc);
        if (violationInfoList == null || violationInfoList.isEmpty()) {
            errorMessage = "No violations found in time frame for driver: " + driver.getPerson().getFullName() + " (ID: " + driverID + ")";
            return;
        }
        
        records = new ArrayList<List<Result>> ();
        for (ViolationInfo info : violationInfoList) {
            HOSRec rec = info.violationRec;
            DebugInfo debugInfo = info.debugInfo;
            
            List<Result> row = new ArrayList<Result>();
            DateTime dateTime = new DateTime(rec.getLogTimeDate(), driverTimeZone);
            row.add(new Result(dateFormatter.print(dateTime), rec.getLogTimeDate()));
            row.add(new Result(rec.getRuleType().getName(), rec.getRuleType().getName()));
            row.add(new Result(rec.getStatus().getName(), rec.getStatus().getName()));
            String violationsStr = info.getViolationsStr();
            row.add(new Result(violationsStr, violationsStr));
            String infoStr = getInfoStr(debugInfo, driverTimeZone, rec.getRuleType());
            row.add(new Result(infoStr, infoStr));
            row.add(new Result(rec.getId(), null));
            records.add(row);
            
        }
        recordCount = records.size();
    }

    public void logDetailsAction() {
        System.out.println("selected: " + selected);
        for (ViolationInfo info : violationInfoList) {
            if (info.violationRec.getId().equals(getSelected())) {
                logsRecords = info.getLogDisplayList();
                break;
            }
        }
    }

    private String getInfoStr(DebugInfo debugInfo, DateTimeZone driverTimeZone, RuleSetType ruleType) {
        StringBuffer infoStr = new StringBuffer();
        DateTime dateTime = new DateTime(debugInfo.getShiftStartTime(), driverTimeZone);
        DateTime cumulativeDateTime = new DateTime(debugInfo.getCumulativeStartTime(), driverTimeZone);
        if (debugInfo.getMinutesData() != null) {
            infoStr.append("<b>Shift:</b> <br/>");
            infoStr.append("  <i>Shift Start:</i> " + dateFormatter.print(dateTime) + "<br/>");
            infoStr.append("  <i>Driving Minutes:</i> " + formattedMinutes(debugInfo.getMinutesData().getOnDutyDrivingMinutes()) + "<br/>");
            infoStr.append("  <i>On Duty Not Driving Minutes:</i> " + formattedMinutes(debugInfo.getMinutesData().getOnDutyNotDrivingMinutes()) + "<br/>");
            infoStr.append("  <i>On Duty Minutes:</i> " + formattedMinutes(debugInfo.getMinutesData().getOnDutyMinutes()) + "<br/>");
            infoStr.append("<b>Shift Limits:</b> <br/>");
            infoStr.append("  <i>Driving Minutes:</i>" + formattedMinutes(debugInfo.getDrivingLimit()) + "<br/>");
            infoStr.append("  <i>On Duty Minutes:</i> " + formattedMinutes(debugInfo.getOnDutyLimit()) + "<br/>");
            infoStr.append("  <i>Off Duty Shift Reset Minutes:</i> " + formattedMinutes(debugInfo.getOffDutyReset()) + "<br/>");
            infoStr.append("<b>Cumulative:</b> <br/>");
            infoStr.append("  <i>Cumulative Start:</i> " + dateFormatter.print(cumulativeDateTime) + "<br/>");
            infoStr.append("  <i>Cumulative On Duty Minutes:</i> " + formattedMinutes(debugInfo.getCumulativeOnDutyMin()) + "<br/>");
            infoStr.append("<b>Cumulative Limits:</b> <br/>");
            infoStr.append("  <i>Cumulative Reset Minutes:</i> " + formattedMinutes(debugInfo.getCumulativeReset()) + "<br/>");
            infoStr.append("  <i>Cumulative Threshold:</i> " + formattedMinutes(debugInfo.getCumulativeThreshold()) + "<br/>");
        }
        
        if (ruleType.getDailyViolationSupport() && debugInfo.getDayMinutesData() != null) {
            infoStr.append("<b>Day:</b> <br/>");
            infoStr.append("  <i>Driving Minutes:</i> " + formattedMinutes(debugInfo.getDayMinutesData().getDayDrivingMinutes()) + "<br/>");
            infoStr.append("  <i>On Duty Minutes:</i> " + formattedMinutes(debugInfo.getDayMinutesData().getDayOnDutyMinutes()) + "<br/>");
            infoStr.append("<b>Day Limits:</b> <br/>");
            infoStr.append("  <i>Driving Minutes:</i> " + formattedMinutes(debugInfo.getDayDrivingLimit()) + "<br/>");
            infoStr.append("  <i>On Duty Minutes:</i> " + formattedMinutes(debugInfo.getDayOnDutyLimit()) + "<br/>");
            infoStr.append("  <i>Off Duty Threshold Minutes:</i> " + formattedMinutes(debugInfo.getDayOffDutyThreshold()) + "<br/>");
        }

        return infoStr.toString();
    }

    public List<String> getColumnHeaders() {
        if (columnHeaders == null) {
            columnHeaders = new ArrayList<String>();
            columnHeaders.add("Log Date");
            columnHeaders.add("Rule Set");
            columnHeaders.add("Status");
            columnHeaders.add("Violations");
            columnHeaders.add("Information");
            columnHeaders.add("Details");
        }
        return columnHeaders;
    }
    public List<String> getLogsColumnHeaders() {
        if (logsColumnHeaders == null) {
            logsColumnHeaders = new ArrayList<String>();
            logsColumnHeaders.add("Log Date");
            logsColumnHeaders.add("Rule Set");
            logsColumnHeaders.add("Status");
            logsColumnHeaders.add("Minutes");
            logsColumnHeaders.add("Comment");
        }
        return logsColumnHeaders;
    }

    public Map<String, Object> getSortOrder() {
        if (sortOrder == null) {
            sortOrder = new HashMap<String, Object>();
            for (String col : getColumnHeaders())
                sortOrder.put(col, Ordering.UNSORTED);
        }
        return sortOrder;
    }
    public Map<String, Object> getLogsSortOrder() {
        if (logsSortOrder == null) {
            logsSortOrder = new HashMap<String, Object>();
            for (String col : getLogsColumnHeaders())
                logsSortOrder.put(col, Ordering.UNSORTED);
        }
        return sortOrder;
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

    public Interval getExpandedInterval(Interval interval, DateTimeZone dateTimeZone, int daysBack, int daysForward)
    {
        LocalDate localDate = new LocalDate(new DateMidnight(interval.getStart(), dateTimeZone));
        DateTime startDate = localDate.toDateTimeAtStartOfDay(dateTimeZone).minusDays(daysBack);
        localDate = new LocalDate(new DateMidnight(interval.getEnd(), dateTimeZone));
        DateTime endDate = localDate.toDateTimeAtStartOfDay(dateTimeZone).plusDays(1+daysForward).minusSeconds(1);

        return new Interval(startDate, endDate);

    }

    private List<ViolationInfo> getViolationInfo(Interval interval, Driver driver, DateTimeZone driverTimeZone, RuleSetType driverDOTType, List<HOSRec> hosLogList) 
    {
        if (hosLogList == null || hosLogList.size() == 0)
            return null;

//DateTimeZone timeZone = DateTimeZone.forTimeZone(determineTimeZone(driverTimeZone, hosLogList.get(0)));
        DateTimeZone timeZone = driverTimeZone;
        DateTime startDate = new LocalDate(interval.getStart()).toDateTimeAtStartOfDay(timeZone);
        DateTime endDate = new LocalDate(interval.getEnd()).toDateTimeAtStartOfDay(timeZone).plusDays(1).minusSeconds(1);

        int indexOfCurrent = getFirstRecordIndexInTimeFrame(hosLogList, startDate);
        if (indexOfCurrent < 0)
            return null;

        List<ViolationInfo> violationInfoList = new ArrayList<ViolationInfo>();
        for (int i = indexOfCurrent; i >= 0; i--)
        {
            HOSRec currentLogRec = hosLogList.get(i);

            if (currentLogRec.getStatus().equals(HOSStatus.DRIVING))
            {
                RuleSetType dotRuleType = currentLogRec.getRuleType();

                HOSRules rules = RuleSetFactory.getRulesForRuleSetType(dotRuleType);
                rules.setGatherDebugInfo(true);
                List<HOSRec> futureLogList = new ArrayList<HOSRec>(hosLogList.subList(0, i));
                List<HOSRec> previousLogList =  new ArrayList<HOSRec>(hosLogList.subList(i+1, hosLogList.size()));
                Map<RuleViolationTypes, Long> violations = rules.getViolations(currentLogRec, previousLogList, futureLogList);
                if (violations != null && !violations.isEmpty()) {
                    ViolationInfo violationInfo = new ViolationInfo();
                    violationInfo.debugInfo = rules.getDebugInfo();
                    violationInfo.violations = violations;
                    violationInfo.violationRec = currentLogRec;
                    violationInfo.hosRecList = previousLogList;
                    
                    violationInfoList.add(violationInfo);
                }
            }
        }
        
        
        for (DateTime intervalDay = interval.getStart(); intervalDay.isBefore(interval.getEnd().plusDays(1)); intervalDay = intervalDay.plusDays(1)) 
        {
            
            LocalDate localDate = new LocalDate(intervalDay);
            DateTime day = localDate.toDateTimeAtStartOfDay(driverTimeZone);

            HOSRec rec = getFirstRecordForDay(day, hosLogList);
            if (rec != null)
            {
                RuleSetType dotRuleType = rec.getRuleType();

                if (dotRuleType.getDailyViolationSupport())
                {
                    HOSRules rules = RuleSetFactory.getRulesForRuleSetType(dotRuleType);
                    rules.setGatherDebugInfo(true);
                    Map<RuleViolationTypes, Long> violations = rules.getDailyViolations(hosLogList, day.toDate());

                    if (violations != null && !violations.isEmpty()) {
                        ViolationInfo violationInfo = new ViolationInfo();
                        violationInfo.debugInfo = rules.getDebugInfo();
                        violationInfo.violations = violations;
                        violationInfo.violationRec = new HOSRec("daily", HOSStatus.DRIVING, day.toDate(), timeZone.toTimeZone(), dotRuleType);
                        violationInfo.hosRecList = null;
                        
                        violationInfoList.add(violationInfo);
                    }
                }
            }

        }
        
        return violationInfoList;
    }

    private HOSRec getFirstRecordForDay(DateTime day, List<HOSRec> hosList)
    {
        Date start = day.minusSeconds(1).toDate();
        Date end = day.plusDays(1).toDate();

        for (HOSRec hosRec : hosList) {
            if (hosRec.getEndTimeDate().after(start) && hosRec.getLogTimeDate().before(end)) {
                return hosRec;
            }
        }
        return null;
    }



    private int getFirstRecordIndexInTimeFrame(List<HOSRec> hosLogList, DateTime reportStartDate) {
        int idx = -1;
        for (int i = hosLogList.size()-1; i >= 0; i--) {
            HOSRec rec = hosLogList.get(i);
//if (rec.getLogTimeDate().before(reportStartDate.toDate())) {
            if (rec.getLogTimeDate().after(reportStartDate.toDate()) || rec.getLogTimeDate().equals(reportStartDate.toDate())) {
                idx = i;
                break;
            }
        }
        return idx;
    }


    public String formattedMinutes(long totalMinutes) 
    {
        int hours = 0;
        long minutes = 0;
        String strHours = "";
        String strMinutes = "";
        if (totalMinutes < 60)
            minutes = totalMinutes;
        else
        {
            hours = (int)Math.floor(totalMinutes/60);
            minutes = totalMinutes - (60 * hours);
        }
        strHours = String.valueOf(hours);
        strMinutes = String.valueOf(minutes);
        if (strMinutes.length() == 1 )
            strMinutes = "0" + strMinutes;
        return totalMinutes + " (" + strHours + ":" + strMinutes + ")";
    }
    
    public List<List<Result>> getRecords() {
        return records;
    }
    public void setRecords(List<List<Result>> records) {
        this.records = records;
    }
    public int getRecordCount() {
        return recordCount;
    }
    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public String getDriverName() {
        return driverName;
    }
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
    
    
    public List<List<Result>> getLogsRecords() {
        return logsRecords;
    }
    public void setLogsRecords(List<List<Result>> logsRecords) {
        this.logsRecords = logsRecords;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public DateRange getDateRange() {
        return dateRange;
    }
    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }
    
    public Integer getDriverID() {
        return driverID;
    }
    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
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
    
    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        System.out.println("setSelected: " + selected);
        this.selected = selected;
    }

    
    class ViolationInfo {
        public HOSRec violationRec;
        public Map<RuleViolationTypes, Long> violations;
        public DebugInfo debugInfo;
        public List<HOSRec> hosRecList;

    
        public String getViolationsStr() {
            StringBuffer violationStr = new StringBuffer();
            for (RuleViolationTypes violationType : violations.keySet()) {
                if (violationStr.length() > 0)
                    violationStr.append("<br/>");
                violationStr.append(violationType.getName() + " " + formattedMinutes(violations.get(violationType)));
            }
            return violationStr.toString();
        }


        public List<List<Result>> getLogDisplayList() {
            logsRecords = new ArrayList<List<Result>> ();
            logsRecords.add(getLogDisplayRow(violationRec, true));
            for (HOSRec rec : hosRecList) {
                logsRecords.add(getLogDisplayRow(rec, false));
            }
            return logsRecords;
        }


        private List<Result> getLogDisplayRow(HOSRec rec, boolean isViolation) {
            List<Result> row = new ArrayList<Result>();
            DateTime dateTime = new DateTime(rec.getLogTimeDate(), driverTimeZone);
            row.add(new Result(dateFormatter.print(dateTime), rec.getLogTimeDate()));
            row.add(new Result(rec.getRuleType().getName(), rec.getRuleType().getName()));
            row.add(new Result(rec.getStatus().getName(), rec.getStatus().getName()));
            row.add(new Result(formattedMinutes(rec.getTotalRealMinutes()), Long.valueOf(rec.getTotalRealMinutes())));

            StringBuffer comments = new StringBuffer();
            
            if (isViolation)
                comments.append("Violation");
            if (rec.getLogTimeDate().equals(debugInfo.getShiftStartTime())) {
                if (comments.length() > 0)
                    comments.append("<br/>");
                comments.append("Shift Reset");
            }
            if (rec.getLogTimeDate().equals(debugInfo.getCumulativeStartTime())) {
                if (comments.length() > 0)
                    comments.append("<br/>");
                comments.append("Cumulative Reset");
            }

            row.add(new Result(comments.toString(), comments.toString()));
            return row;
        }

    }
}
