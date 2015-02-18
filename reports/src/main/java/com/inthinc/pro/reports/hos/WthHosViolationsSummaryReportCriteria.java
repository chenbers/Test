package com.inthinc.pro.reports.hos;

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
import com.inthinc.pro.reports.hos.model.WthHosViolationsSummary;
import com.inthinc.pro.reports.hos.model.ViolationsSummary;
import com.inthinc.pro.reports.tabular.ColumnHeader;
import com.inthinc.pro.reports.tabular.Result;
import com.inthinc.pro.reports.tabular.Tabular;
import com.inthinc.pro.reports.util.DateTimeUtil;
import com.inthinc.pro.reports.util.MessageUtil;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class WthHosViolationsSummaryReportCriteria extends ViolationsSummaryReportCriteria implements Tabular {

    public WthHosViolationsSummaryReportCriteria(Locale locale) {
        super(ReportType.WEATHERFORD_HOS_VIOLATIONS_SUMMARY_REPORT, locale);
        this.setIncludeInactiveDrivers(ReportCriteria.DEFAULT_INCLUDE_ZERO_MILES_DRIVERS);
        this.setIncludeZeroMilesDrivers(ReportCriteria.DEFAULT_INCLUDE_ZERO_MILES_DRIVERS);
    }

    public void init(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval) {
        List<Group> reportGroupList = getReportGroupList(groupIDList, accountGroupHierarchy);
        List<Driver> driverList = getReportDriverList(reportGroupList);
        Map<Driver, List<HOSRecord>> driverHOSRecordMap = new HashMap<Driver, List<HOSRecord>>();
        for (Driver driver : driverList) {
            if (includeDriver(getDriverDAO(), driver.getDriverID(), interval)) {
                if (driver.getDot() == null)
                    continue;

                DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
                Interval queryInterval = DateTimeUtil.getExpandedInterval(interval, dateTimeZone, RuleSetFactory.getDaysBackForRuleSetType(driver.getDot()), RuleSetFactory.getDaysForwardForRuleSetType(driver.getDot()));
                driverHOSRecordMap.put(driver, hosDAO.getHOSRecords(driver.getDriverID(), queryInterval, false));
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

    void initDataSet(Interval interval, GroupHierarchy groupHierarchy, List<Group> reportGroupList, Map<Driver, List<HOSRecord>> driverHOSRecordMap,
                     List<HOSGroupMileage> groupMileageList, List<HOSGroupMileage> groupNoDriverMileageList) {

        Map<Integer, WthHosViolationsSummary> dataMap = new TreeMap<Integer, WthHosViolationsSummary>();
        for (Group group : reportGroupList) {
            dataMap.put(group.getGroupID(), new WthHosViolationsSummary(group.getName()));
        }
        DateTime currentTime = new DateTime(DateTimeZone.UTC);

        for (Entry<Driver, List<HOSRecord>> entry : driverHOSRecordMap.entrySet()) {
            Driver driver = entry.getKey();
            RuleSetType driverDOTType = driver.getDot();

            if (driverDOTType == null)
                driverDOTType = RuleSetType.NON_DOT;

            // filter other rulesets except US 8-day, US oil 8-day, US oil no wait 8-day, US waterwell 8-day
            if (!(driverDOTType.equals(RuleSetType.US_OIL_NO_WAIT_8DAY)
                    || driverDOTType.equals(RuleSetType.US_WATERWELL_8DAY)
                    || driverDOTType.equals(RuleSetType.US)
                    || driverDOTType.equals(RuleSetType.US_OIL)))
                continue;

            DateTimeZone driverTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
            DateTime reportEndDate = new LocalDate(interval.getEnd()).toDateTimeAtStartOfDay(driverTimeZone).plusDays(1).minusSeconds(1);
            if (reportEndDate.isAfterNow())
                reportEndDate = currentTime;

            List<HOSRecord> hosRecordList = entry.getValue();
            Collections.sort(hosRecordList);


            List<HOSRec> recListForViolationsCalc = HOSUtil.getRecListFromLogList(hosRecordList, reportEndDate.toDate(), !(driverDOTType.equals(RuleSetType.NON_DOT)));

            ViolationsSummary summary = findSummary(groupHierarchy, dataMap, driver.getGroupID());
            if (summary == null) {
                continue;
            }

            if (summary instanceof WthHosViolationsSummary){
                WthHosViolationsSummary wthHosViolationsSummary = (WthHosViolationsSummary) summary;
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

        if (groupMileageList != null) {
            for (HOSGroupMileage groupMileage : groupMileageList) {
                WthHosViolationsSummary summary = (WthHosViolationsSummary) findSummary(groupHierarchy, dataMap, groupMileage.getGroupID());
                if (summary == null) {
                    continue;
                }
                summary.setTotalMiles(summary.getTotalMiles() + groupMileage.getDistance());
            }
        }

        if (groupNoDriverMileageList != null) {
            for (HOSGroupMileage groupMileage : groupNoDriverMileageList) {
                WthHosViolationsSummary summary = (WthHosViolationsSummary) findSummary(groupHierarchy, dataMap, groupMileage.getGroupID());
                if (summary == null) {
                    continue;
                }
                summary.setTotalMiles(summary.getTotalMiles() + groupMileage.getDistance());
                summary.setTotalMilesNoDriver(summary.getTotalMilesNoDriver() + groupMileage.getDistance());
            }
        }

        List<WthHosViolationsSummary> dataList = new ArrayList<WthHosViolationsSummary>();
        for (WthHosViolationsSummary summary : dataMap.values()) {
            if (summary.getDriverCnt().intValue() != 0 || summary.getTotalMiles().intValue() != 0 || summary.getTotalMilesNoDriver().intValue() != 0)
                dataList.add(summary);
        }

        setMainDataset(dataList);

        addParameter("REPORT_START_DATE", dateTimeFormatter.print(interval.getStart()));
        addParameter("REPORT_END_DATE", dateTimeFormatter.print(interval.getEnd()));
    }

    @Override
    protected void updateSummaryDriverCount(ViolationsSummary summary, Driver driver) {
        if (driver.getDot() != null && driver.getDot() != RuleSetType.NON_DOT)
            summary.setDriverCnt(summary.getDriverCnt() + 1);

    }

    @Override
    public List<String> getColumnHeaders() {
        ResourceBundle resourceBundle = ReportType.WEATHERFORD_HOS_VIOLATIONS_SUMMARY_REPORT.getResourceBundle(getLocale());

        List<String> columnHeaders = new ArrayList<String>();
        for (int i = 1; i <= 9; i++) {
            if (i > 8 && getUseMetric())
                columnHeaders.add(MessageUtil.getBundleString(resourceBundle, "column." + i + ".tabular.METRIC"));
            else columnHeaders.add(MessageUtil.getBundleString(resourceBundle, "column." + i + ".tabular"));
        }

        return columnHeaders;
    }

    @Override
    public List<List<Result>> getTableRows() {

        List<WthHosViolationsSummary> dataList = (List<WthHosViolationsSummary>) getMainDataset();
        if (dataList == null)
            return null;

        List<List<Result>> records = new ArrayList<List<Result>>();

        for (WthHosViolationsSummary summary : dataList) {
            Integer total = summary.getHourDriving11()+summary.getOnDutyHours14()+summary.getOnDutyHours70()+summary.getThirtyMinuteBreak();
            List<Result> row = new ArrayList<Result>();
            row.add(new Result(summary.getGroupName(), summary.getGroupName()));
            row.add(new Result(summary.getHourDriving11().toString(), summary.getHourDriving11()));
            row.add(new Result(summary.getOnDutyHours14().toString(), summary.getOnDutyHours14()));
            row.add(new Result(summary.getOnDutyHours70().toString(), summary.getOnDutyHours70()));
            row.add(new Result(summary.getThirtyMinuteBreak().toString(), summary.getThirtyMinuteBreak()));
            row.add(new Result(total.toString(), total));
            row.add(new Result(summary.getDriverCnt().toString(), summary.getDriverCnt()));
            row.add(new Result(Converter.convertRemarkDistance(summary.getTotalMiles(), getUseMetric(), getLocale()), summary.getTotalMiles()));
            row.add(new Result(Converter.convertRemarkDistance(summary.getTotalMilesNoDriver(), getUseMetric(), getLocale()), summary.getTotalMilesNoDriver()));

            records.add(row);
        }

        return records;


    }

    @Override
    public List<ColumnHeader> getColumnSummaryHeaders() {
        ResourceBundle resourceBundle = ReportType.WEATHERFORD_HOS_VIOLATIONS_SUMMARY_REPORT.getResourceBundle(getLocale());

        List<ColumnHeader> columnHeaders = new ArrayList<ColumnHeader>();
        return columnHeaders;
    }
}
