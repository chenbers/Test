package com.inthinc.pro.reports.hos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.TreeMap;

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
import com.inthinc.pro.dao.util.HOSUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.hos.HOSGroupMileage;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.converter.Converter;
import com.inthinc.pro.reports.hos.model.HosViolationsSummary;
import com.inthinc.pro.reports.hos.model.ViolationsSummary;
import com.inthinc.pro.reports.tabular.ColumnHeader;
import com.inthinc.pro.reports.tabular.Result;
import com.inthinc.pro.reports.tabular.Tabular;
import com.inthinc.pro.reports.util.DateTimeUtil;
import com.inthinc.pro.reports.util.MessageUtil;

public class HosViolationsSummaryReportCriteria extends ViolationsSummaryReportCriteria implements Tabular {

    public HosViolationsSummaryReportCriteria(Locale locale) {
        super(ReportType.HOS_VIOLATIONS_SUMMARY_REPORT, locale);
        this.setIncludeInactiveDrivers(ReportCriteria.DEFAULT_INCLUDE_ZERO_MILES_DRIVERS);
        this.setIncludeZeroMilesDrivers(ReportCriteria.DEFAULT_INCLUDE_ZERO_MILES_DRIVERS);
    }
    public void init(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval) {
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
        
        List<HOSGroupMileage> groupMileageList = new ArrayList<HOSGroupMileage>();
        List<HOSGroupMileage> groupNoDriverMileageList = new ArrayList<HOSGroupMileage>();

        for (Group reportGroup : reportGroupList) {
            List<HOSGroupMileage> mileageList = hosDAO.getHOSMileage(reportGroup.getGroupID(), interval, false);
            for (HOSGroupMileage mileage : mileageList)
                if (!groupExists(groupMileageList, mileage.getGroupID()))
                    groupMileageList.add(mileage);
            List<HOSGroupMileage> zeroMileageList = hosDAO.getHOSMileage(reportGroup.getGroupID(), interval, true); 
            for (HOSGroupMileage mileage : zeroMileageList)
                if (!groupExists(groupNoDriverMileageList, mileage.getGroupID()))
                    groupNoDriverMileageList.add(mileage);
        }

        initDataSet(interval, accountGroupHierarchy, reportGroupList, driverHOSRecordMap, groupMileageList, groupNoDriverMileageList);
        
    }

    private boolean groupExists(List<HOSGroupMileage> groupMileageList, Integer groupID) {
        for (HOSGroupMileage mileage : groupMileageList)
            if (mileage.getGroupID().equals(groupID))
                return true;
        return false;
    }
    void initDataSet(Interval interval, GroupHierarchy groupHierarchy,  List<Group> reportGroupList, Map<Driver, List<HOSRecord>> driverHOSRecordMap,
            List<HOSGroupMileage> groupMileageList, List<HOSGroupMileage> groupNoDriverMileageList)
    {
        
        Map<Integer, HosViolationsSummary> dataMap = new TreeMap<Integer, HosViolationsSummary>();
        for (Group group : reportGroupList) {
            dataMap.put(group.getGroupID(), new HosViolationsSummary(getFullGroupName(groupHierarchy, group.getGroupID())));
        }
        for (Entry<Driver, List<HOSRecord>> entry : driverHOSRecordMap.entrySet()) {
            Driver driver = entry.getKey();
            RuleSetType driverDOTType = driver.getDot();
            if (driverDOTType == null)
                driverDOTType = RuleSetType.NON_DOT;
            
            DateTimeZone driverTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
            DateTime reportEndDate = new LocalDate(interval.getEnd()).toDateTimeAtStartOfDay(driverTimeZone).plusDays(1).minusSeconds(1);
            
            List<HOSRecord> hosRecordList = entry.getValue();
            Collections.sort(hosRecordList);
            
            
            List<HOSRec> recListForViolationsCalc = HOSUtil.getRecListFromLogList(hosRecordList, reportEndDate.toDate(), !(driverDOTType.equals(RuleSetType.NON_DOT)));

            ViolationsSummary summary = findSummary(groupHierarchy, dataMap, driver.getGroupID());
            if (summary == null) {
                continue;
            }
            
            // violations
            List<ViolationsData> dailyViolations = new DailyViolations().getDailyHosViolationsForReport(interval,
                    driverTimeZone.toTimeZone(),
                    driverDOTType, 
                    recListForViolationsCalc);
//            dump(driver, dailyViolations);
            updateSummary(summary, dailyViolations);

            List<ViolationsData> shiftViolations = new ShiftViolations().getHosViolationsInTimeFrame(
                    interval, driverTimeZone.toTimeZone(),
                    driverDOTType, 
                    null, 
                    recListForViolationsCalc);
//            dump(driver, shiftViolations);
            updateSummary(summary, shiftViolations);

            updateSummaryDriverCount(summary, driver);
        }
        
        if (groupMileageList != null) {
            for (HOSGroupMileage groupMileage : groupMileageList) {
                HosViolationsSummary summary = (HosViolationsSummary)findSummary(groupHierarchy, dataMap, groupMileage.getGroupID());
                if (summary == null) {
                    continue;
                }
                summary.setTotalMiles(summary.getTotalMiles()+groupMileage.getDistance());
            }
        }
        
        if (groupNoDriverMileageList != null) {
            for (HOSGroupMileage groupMileage : groupNoDriverMileageList) {
                HosViolationsSummary summary = (HosViolationsSummary)findSummary(groupHierarchy, dataMap, groupMileage.getGroupID());
                if (summary == null) {
                    continue;
                }
                summary.setTotalMiles(summary.getTotalMiles()+groupMileage.getDistance());
                summary.setTotalMilesNoDriver(summary.getTotalMilesNoDriver()+groupMileage.getDistance());
            }
        }
         
        List<HosViolationsSummary> dataList = new ArrayList<HosViolationsSummary>();
        for (HosViolationsSummary summary : dataMap.values()) { 
            if (summary.getDriverCnt().intValue() != 0 || summary.getTotalMiles().intValue() != 0 || summary.getTotalMilesNoDriver().intValue() != 0)
                dataList.add(summary);
        }

        setMainDataset(dataList);
        
        addParameter("REPORT_START_DATE", dateTimeFormatter.print(interval.getStart()));
        addParameter("REPORT_END_DATE", dateTimeFormatter.print(interval.getEnd()));
        
        
    }

//    private void dump(Driver driver, List<ViolationsData> violations) {
//        System.out.println("Driver: " + driver.getDriverID() + " " + ((violations == null || violations.size() == 0) ? 0 : violations.size()) + " violations");
//        if (violations == null)
//            return;
//        for (ViolationsData data : violations) {
//            System.out.println(data.getHosViolationRec().getStartOfDay() + " " + data.getViolationMap().size());
//            for (Entry<RuleViolationTypes, Long> entry : data.getViolationMap().entrySet()) {
//                if (entry.getKey().isReportable()) {
//                    System.out.println(entry.getKey() + " " + entry.getValue());
//                }
//            }
//        }
//        
//    }
    @Override
    protected void updateSummaryDriverCount(ViolationsSummary summary, Driver driver) {
        if (driver.getDot() != null && driver.getDot() != RuleSetType.NON_DOT)
            summary.setDriverCnt(summary.getDriverCnt() + 1);
        
    }

    @Override
    public List<String> getColumnHeaders() {
        ResourceBundle resourceBundle = ReportType.HOS_VIOLATIONS_SUMMARY_REPORT.getResourceBundle(getLocale());
        
        List<String> columnHeaders = new ArrayList<String>();
        for (int i = 1; i <=16; i++) {
            if (i > 14 && getUseMetric())
                columnHeaders.add(MessageUtil.getBundleString(resourceBundle, "column."+i+".tabular.METRIC"));
            else columnHeaders.add(MessageUtil.getBundleString(resourceBundle, "column."+i+".tabular"));
        }
        
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
            row.add(new Result(Converter.convertRemarkDistance(summary.getTotalMiles(), getUseMetric(), getLocale()), summary.getTotalMiles()));
            row.add(new Result(Converter.convertRemarkDistance(summary.getTotalMilesNoDriver(), getUseMetric(), getLocale()), summary.getTotalMilesNoDriver()));
            
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
