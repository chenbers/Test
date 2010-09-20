package com.inthinc.pro.reports.hos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
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
import com.inthinc.hos.violations.DailyViolations;
import com.inthinc.hos.violations.ShiftViolations;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.hos.HOSGroupMileage;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.model.GroupHierarchy;
import com.inthinc.pro.reports.hos.model.HosViolationsSummary;
import com.inthinc.pro.reports.hos.model.ViolationsSummary;
import com.inthinc.pro.reports.hos.util.HOSUtil;
import com.inthinc.pro.reports.tabular.ColumnHeader;
import com.inthinc.pro.reports.tabular.Result;
import com.inthinc.pro.reports.tabular.Tabular;
import com.inthinc.pro.reports.util.DateTimeUtil;
import com.inthinc.pro.reports.util.MessageUtil;

public class HosViolationsSummaryReportCriteria extends ViolationsSummaryReportCriteria implements Tabular {

    
    
    public HosViolationsSummaryReportCriteria(Locale locale) 
    {
        super(ReportType.HOS_VIOLATIONS_SUMMARY_REPORT, locale);
    }
    
    public void init(Integer groupID, Interval interval)
    {
        Group topGroup = groupDAO.findByID(groupID);
        List<Group> groupList = groupDAO.getGroupHierarchy(topGroup.getAccountID(), topGroup.getGroupID());
        List<Driver> driverList = driverDAO.getDrivers(groupID);
        Map<Driver, List<HOSRecord>> driverHOSRecordMap = new HashMap<Driver, List<HOSRecord>> ();
        for (Driver driver : driverList) {
            if (driver.getDot() == null)
                continue;
            
            DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
            Interval queryInterval = DateTimeUtil.getExpandedInterval(interval, dateTimeZone, RuleSetFactory.getDaysBackForRuleSetType(driver.getDriverDOTType()), RuleSetFactory.getDaysForwardForRuleSetType(driver.getDriverDOTType()));
            driverHOSRecordMap.put(driver, hosDAO.getHOSRecords(driver.getDriverID(), queryInterval, true));
            
        }
        
        List<HOSGroupMileage> groupMileageList = hosDAO.getHOSMileage(groupID, interval, false);
        List<HOSGroupMileage> groupNoDriverMileageList = hosDAO.getHOSMileage(groupID, interval, true);

        initDataSet(interval, topGroup, groupList, driverHOSRecordMap, groupMileageList, groupNoDriverMileageList);

    }
    
    void initDataSet(Interval interval, Group topGroup,  List<Group> groupList, Map<Driver, List<HOSRecord>> driverHOSRecordMap,
            List<HOSGroupMileage> groupMileageList, List<HOSGroupMileage> groupNoDriverMileageList)
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
            RuleSetType driverDOTType = driver.getDriverDOTType();
            DateTime reportEndDate = new LocalDate(interval.getEnd()).toDateTimeAtStartOfDay(driverTimeZone).plusDays(1).minusSeconds(1);
            List<HOSRec> recListForViolationsCalc = HOSUtil.getRecListFromLogList(entry.getValue(), reportEndDate.toDate(), !(driverDOTType.equals(RuleSetType.NON_DOT)));

            ViolationsSummary summary = findSummary(groupHierarchy, topGroup, dataMap, driver.getGroupID());
            if (summary == null) {
                continue;
            }
            
            // violations
            List<ViolationsData> dailyViolations = new DailyViolations().getDailyHosViolationsForReport(interval,
                    driverTimeZone.toTimeZone(),
                    driverDOTType, 
                    recListForViolationsCalc);
            updateSummary(summary, dailyViolations);

            List<ViolationsData> shiftViolations = new ShiftViolations().getHosViolationsInTimeFrame(
                    interval, driverTimeZone.toTimeZone(),
                    driverDOTType, 
                    null, 
                    recListForViolationsCalc);
            updateSummary(summary, shiftViolations);

            updateSummaryDriverCount(summary, driver);
        }
        
        for (HOSGroupMileage groupMileage : groupMileageList) {
            HosViolationsSummary summary = (HosViolationsSummary)findSummary(groupHierarchy, topGroup, dataMap, groupMileage.getGroupID());
            if (summary == null) {
                continue;
            }
            summary.setTotalMiles(summary.getTotalMiles()+groupMileage.getDistance());
            
        }
        for (HOSGroupMileage groupMileage : groupNoDriverMileageList) {
            HosViolationsSummary summary = (HosViolationsSummary)findSummary(groupHierarchy, topGroup, dataMap, groupMileage.getGroupID());
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

    @Override
    protected void updateSummaryDriverCount(ViolationsSummary summary, Driver driver) {
        if (driver.getDot() != null && driver.getDriverDOTType() != RuleSetType.NON_DOT)
            summary.setDriverCnt(summary.getDriverCnt() + 1);
        
    }

    @Override
    public List<String> getColumnHeaders() {
        ResourceBundle resourceBundle = ReportType.HOS_VIOLATIONS_SUMMARY_REPORT.getResourceBundle(getLocale());
        
        List<String> columnHeaders = new ArrayList<String>();
        for (int i = 1; i <=16; i++)
            columnHeaders.add(MessageUtil.getBundleString(resourceBundle, "column."+i+".tabular"));
        
        return columnHeaders;
    }

    @Override
    public List<List<Result>> getTableRows() {
        
        List<HosViolationsSummary> dataList = (List<HosViolationsSummary>)getMainDataset();
        if (dataList == null)
            return null;
        
        List<List<Result>>records = new ArrayList<List<Result>>();
        
        for (HosViolationsSummary summary : dataList) {
            List<Result> row = new ArrayList<Result>();
            row.add(new Result(summary.getGroupName(), summary.getGroupName()));
            row.add(new Result(summary.getDriving_1().toString(), summary.getDriving_1()));
            row.add(new Result(summary.getDriving_2().toString(), summary.getDriving_2()));
            row.add(new Result(summary.getDriving_3().toString(), summary.getDriving_3()));
            row.add(new Result(summary.getOnDuty_1().toString(), summary.getOnDuty_1()));
            row.add(new Result(summary.getOnDuty_2().toString(), summary.getOnDuty_2()));
            row.add(new Result(summary.getOnDuty_3().toString(), summary.getOnDuty_3()));
            row.add(new Result(summary.getCumulative_1().toString(), summary.getCumulative_1()));
            row.add(new Result(summary.getCumulative_2().toString(), summary.getCumulative_2()));
            row.add(new Result(summary.getCumulative_3().toString(), summary.getCumulative_3()));
            row.add(new Result(summary.getOffDuty_1().toString(), summary.getOffDuty_1()));
            row.add(new Result(summary.getOffDuty_2().toString(), summary.getOffDuty_2()));
            row.add(new Result(summary.getOffDuty_3().toString(), summary.getOffDuty_3()));
            row.add(new Result(summary.getDriverCnt().toString(), summary.getDriverCnt()));
            row.add(new Result(summary.getTotalMiles().toString(), summary.getTotalMiles()));
            row.add(new Result(summary.getTotalMilesNoDriver().toString(), summary.getTotalMilesNoDriver()));
            
            records.add(row);
        }

        return records;
        
        
    }
    @Override
    public List<ColumnHeader> getColumnSummaryHeaders() {
        ResourceBundle resourceBundle = ReportType.HOS_VIOLATIONS_SUMMARY_REPORT.getResourceBundle(getLocale());
        
        List<ColumnHeader> columnHeaders = new ArrayList<ColumnHeader>();
        columnHeaders.add(new ColumnHeader(MessageUtil.getBundleString(resourceBundle, "column.1.tabularHeader"), 1));
        columnHeaders.add(new ColumnHeader(MessageUtil.getBundleString(resourceBundle, "column.2to4.tabularHeader"), 3));
        columnHeaders.add(new ColumnHeader(MessageUtil.getBundleString(resourceBundle, "column.5to7.tabularHeader"), 3));
        columnHeaders.add(new ColumnHeader(MessageUtil.getBundleString(resourceBundle, "column.8to10.tabularHeader"), 3));
        columnHeaders.add(new ColumnHeader(MessageUtil.getBundleString(resourceBundle, "column.11to13.tabularHeader"), 3));
        columnHeaders.add(new ColumnHeader(MessageUtil.getBundleString(resourceBundle, "column.14to16.tabularHeader"), 3));
        return columnHeaders;
    }


}
