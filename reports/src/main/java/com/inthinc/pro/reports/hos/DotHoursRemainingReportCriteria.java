package com.inthinc.pro.reports.hos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
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
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.dao.util.HOSUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.GroupListReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.converter.Converter;
import com.inthinc.pro.reports.hos.model.DotHoursRemaining;
import com.inthinc.pro.reports.tabular.ColumnHeader;
import com.inthinc.pro.reports.tabular.Result;
import com.inthinc.pro.reports.tabular.Tabular;
import com.inthinc.pro.reports.util.DateTimeUtil;
import com.inthinc.pro.reports.util.MessageUtil;

public class DotHoursRemainingReportCriteria extends GroupListReportCriteria implements Tabular {

    private GroupDAO groupDAO;
    private HOSDAO hosDAO;

    
    protected DateTimeFormatter dayFormatter;    

    public static final int     DAYS_BACK=14;
    
    public DotHoursRemainingReportCriteria(Locale locale) {
        super(ReportType.DOT_HOURS_REMAINING, locale);
        dayFormatter = DateTimeFormat.forPattern("MM/dd/yy").withLocale(locale);
    }
    
    public void init(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList) {
        
        List<Group> reportGroupList = getReportGroupList(groupIDList, accountGroupHierarchy);
        List<Driver> driverList = getReportDriverList(reportGroupList);

        DateTime currentDate = new DateTime(); 
        
        Map<Driver, List<HOSRecord>> driverHOSRecordMap = new HashMap<Driver, List<HOSRecord>> ();
        for (Driver driver : driverList) {
            if (driver.getDot() == null || driver.getDot().equals(RuleSetType.NON_DOT))
                continue;
            Interval interval = DateTimeUtil.getDaysBackInterval(currentDate, DateTimeZone.forTimeZone(driver.getPerson().getTimeZone()), RuleSetFactory.getDaysBackForRuleSetType(driver.getDot()));
            if(includeDriver(getDriverDAO(), driver.getDriverID(), interval)){
                driverHOSRecordMap.put(driver, hosDAO.getHOSRecords(driver.getDriverID(), interval, true));
            }
        }
        
        initDataSet(accountGroupHierarchy, driverHOSRecordMap, currentDate);
        
    }

    void initDataSet(GroupHierarchy accountGroupHierarchy, Map<Driver, List<HOSRecord>> driverHOSRecordMap, DateTime currentDate)
    {
        List<DotHoursRemaining> dotHoursRemainingList = new ArrayList<DotHoursRemaining>();

        Interval interval = new Interval(new DateMidnight(currentDate.minusDays(DAYS_BACK)), new DateMidnight(currentDate));
        
        for (Entry<Driver, List<HOSRecord>> entry : driverHOSRecordMap.entrySet()) {
            Driver driver = entry.getKey();
            List<DateTime> dayList = DateTimeUtil.getDayList(interval, DateTimeZone.forTimeZone(driver.getPerson().getTimeZone()));
            List<HOSRecord> hosRecordList = entry.getValue();
            if (hosRecordList == null) {
                hosRecordList = new ArrayList<HOSRecord>();
            }
            Collections.sort(hosRecordList);

            fillInDotHoursRemainingData(dotHoursRemainingList, getFullGroupName(accountGroupHierarchy, driver.getGroupID()), driver, dayList, hosRecordList, currentDate);
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
//       System.out.println("driver: " + driver.getPerson().getFullName());
//       HOSStats minutesData = rules.getHOSStats(recListForHoursRemainingCalc, currentDate.toDate());
//       System.out.println("OffDutyMinutes " + minutesData.getOffDutyMinutes());
//       System.out.println("DrivingMinutes " + minutesData.getOnDutyDrivingMinutes());
//       System.out.println("OnDutyNotDrivingMinutes " + minutesData.getOnDutyNotDrivingMinutes());
//       System.out.println("OnDutyMinutes " + minutesData.getOnDutyMinutes());
//       System.out.println("Cumulative remaining " + minutesData.getCumulativeDOTMinutesRemaining());
//       System.out.println("Driving remaining " + minutesData.getDrivingDOTMinutesRemaining());

       
       long minutesRemaining = data.getAllDOTMinutesRemaining();
       long cumulativeMinutesRemaining = data.getCumulativeDOTMinutesRemaining();
       
       for (DateTime day : dayList)
       {
           List<HOSRecAdjusted> dayLogList = hosAdjustedList.getAdjustedListForDay(day.toDate(), currentDate.toDate(), true, driver.getPerson().getTimeZone());
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
                   minutesRemaining, cumulativeMinutesRemaining,
                   dayFormatter.print(day), day.toDate(), HOSStatus.DRIVING, drivingIncrements*15l));
           dataList.add(new DotHoursRemaining(groupName, 
                   driver.getDriverID(), driver.getPerson().getFullNameLastFirst(),  driver.getDot(),
                   minutesRemaining,cumulativeMinutesRemaining, 
                   dayFormatter.print(day), day.toDate(), HOSStatus.ON_DUTY, onDutyIncrements*15l));
       }
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

    @Override
    public List<String> getColumnHeaders() {
        ResourceBundle resourceBundle = ReportType.DOT_HOURS_REMAINING.getResourceBundle(getLocale());
        
        List<String> columnHeaders = new ArrayList<String>();
        for (int i = 1; i <= 5; i++)
            columnHeaders.add(MessageUtil.getBundleString(resourceBundle, "column."+i+".tabular"));
        
        int recordsPerDriver = (DAYS_BACK+1)*2;
        for (int i = 0; i < recordsPerDriver; i+=2) {
            columnHeaders.add(MessageUtil.getBundleString(resourceBundle, "column.6.tabular"));
            columnHeaders.add(MessageUtil.getBundleString(resourceBundle, "column.7.tabular"));
            
        }
        return columnHeaders;
    }

    @Override
    public List<List<Result>> getTableRows() {
        ResourceBundle resourceBundle = ReportType.DOT_HOURS_REMAINING.getResourceBundle(getLocale());
        
        List<DotHoursRemaining> dataList = (List<DotHoursRemaining>)getMainDataset();
        if (dataList == null)
            return null;
        
        int recordsPerDriver = (DAYS_BACK+1)*2;
        List<List<Result>>records = new ArrayList<List<Result>>();
        if (dataList == null || dataList.size() < recordsPerDriver)
            return null;
        
        List<Result> row = null;
        for (int i = 0; i < dataList.size(); i++) {

            DotHoursRemaining data = dataList.get(i);
            
            if (i % recordsPerDriver == 0) {
                if (row != null)
                    records.add(row);
                row = new ArrayList<Result>();
                row.add(new Result(data.getGroupName(), data.getGroupName()));
                row.add(new Result(data.getDriverName(), data.getDriverName()));
                String ruleSetTypeStr = MessageUtil.getBundleString(resourceBundle, "dot."+data.getDotType().getCode()); 
                row.add(new Result(ruleSetTypeStr,ruleSetTypeStr));
                row.add(new Result(Converter.convertMinutes(data.getMinutesRemaining()), data.getMinutesRemaining()));
                row.add(new Result(Converter.convertMinutes(data.getCumulativeMinutesRemaining()), data.getCumulativeMinutesRemaining()));
            }
            
            row.add(new Result(Converter.convertMinutes(data.getTotalAdjustedMinutes()), data.getTotalAdjustedMinutes()));
        }
        if (row != null)
            records.add(row);
        return records;
    }

    @Override
    public List<ColumnHeader> getColumnSummaryHeaders() {
        
        List<ColumnHeader> columnHeaders = new ArrayList<ColumnHeader>();
        columnHeaders.add(new ColumnHeader("", 5));
        
        List<DotHoursRemaining> dataList = (List<DotHoursRemaining>)getMainDataset();
        int recordsPerDriver = (DAYS_BACK+1)*2;
        if (dataList == null || dataList.size() < recordsPerDriver)
            return null;
        
        for (int i = 0; i < recordsPerDriver; i+=2) {
            columnHeaders.add(new ColumnHeader(dataList.get(i).getDay(), 2));
            
        }
        return columnHeaders;
    }

}

