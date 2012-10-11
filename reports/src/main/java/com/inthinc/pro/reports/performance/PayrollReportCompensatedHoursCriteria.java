package com.inthinc.pro.reports.performance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

import com.inthinc.hos.model.HOSStatus;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.converter.Converter;
import com.inthinc.pro.reports.performance.model.PayrollData;
import com.inthinc.pro.reports.tabular.ColumnHeader;
import com.inthinc.pro.reports.tabular.Result;
import com.inthinc.pro.reports.util.DateTimeUtil;

public class PayrollReportCompensatedHoursCriteria extends PayrollReportCriteria {

    public PayrollReportCompensatedHoursCriteria(Locale locale) {
        super(ReportType.PAYROLL_COMPENSATED_HOURS, locale);
    }

    protected List<PayrollData> getCompensatedRecords(List<PayrollData> records) {
        HashMap<String, PayrollData> compensatedRecords = new HashMap<String, PayrollData>();
        for (PayrollData rec : records) {
            if (!compensatedRecords.containsKey(rec.getDay()+"_"+rec.getDriverId())) {
                // add an onduty row with 0 minutes so that all driver/days show up on report
                compensatedRecords.put(rec.getDay()+"_"+rec.getDriverId(), new PayrollData(rec.getGroupName(), rec.getGroupAddress(), rec.getDriverId(), rec.getDriverName(), rec.getEmployeeID(), rec.getDay(),
                        HOSStatus.ON_DUTY, 0));
            }

            if (rec.getStatus() == HOSStatus.DRIVING                        // 
                    || rec.getStatus() == HOSStatus.ON_DUTY                 // 
                    || rec.getStatus() == HOSStatus.ON_DUTY_OCCUPANT        //
                    || rec.getStatus() == HOSStatus.SLEEPER 
                    || rec.getStatus() == HOSStatus.OFF_DUTY_AT_WELL
                    || rec.getStatus() == HOSStatus.TRAVELTIME_OCCUPANT) {
                compensatedRecords.get(rec.getDay()+"_"+rec.getDriverId()).addTotalAdjustedMinutes(rec.getTotalAdjustedMinutes());
            }
        }

        return new ArrayList<PayrollData>(compensatedRecords.values());
    }

    public void init(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval) {
        Account account = accountDAO.findByID(accountGroupHierarchy.getTopGroup().getAccountID());

        List<Group> reportGroupList = getReportGroupList(groupIDList, accountGroupHierarchy);
        List<Driver> driverList = getReportDriverList(reportGroupList);

        Map<Driver, List<HOSRecord>> driverHOSRecordMap = new HashMap<Driver, List<HOSRecord>>();
        for (Driver driver : driverList) {
            if(includeDriver(getDriverDAO(), driver.getDriverID(), interval)){
                if (driver.getDot() == null)
                    continue;
                DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
                Interval queryInterval = DateTimeUtil.getExpandedInterval(interval, dateTimeZone, 1, 1);
                driverHOSRecordMap.put(driver, hosDAO.getHOSRecords(driver.getDriverID(), queryInterval, true));
            }
        }

        initDataSet(interval, account, accountGroupHierarchy, driverHOSRecordMap);
    }

    public void initDataSet(Interval interval, Account account, GroupHierarchy accountGroupHierarchy, Map<Driver, List<HOSRecord>> driverHOSRecordMap) {
        super.initDataSet(interval, account);

        Date currentTime = new Date();

        List<PayrollData> dataList = new ArrayList<PayrollData>();
        for (Entry<Driver, List<HOSRecord>> entry : driverHOSRecordMap.entrySet()) {
            dataList.addAll(getCompensatedRecords(getDriverPayrollData(interval, accountGroupHierarchy, currentTime, entry.getKey(), entry.getValue() == null ? new ArrayList<HOSRecord>() : entry
                    .getValue())));
        }

        Collections.sort(dataList);
        setMainDataset(dataList);

    }

    private static final int TOTAL_COL = 5;

    @Override
    public List<String> getColumnHeaders() {
        return getColumnHeaders(ReportType.PAYROLL_COMPENSATED_HOURS, TOTAL_COL);
    }

    @Override
    public List<List<Result>> getTableRows() {
        List<PayrollData> dataList = (List<PayrollData>) getMainDataset();
        if (dataList == null || dataList.size() == 0)
            return null;

        List<List<Result>> records = new ArrayList<List<Result>>();

        // Map<String, Map<DateTime,Integer[]>> dataMap = new TreeMap<String, Map<DateTime,Integer[]>>();

        for (PayrollData data : dataList) {
            List<Result> row = new ArrayList<Result>();
            row.add(new Result(data.getGroupName(), data.getGroupName()));
            row.add(new Result(data.getEmployeeID(), data.getEmployeeID()));
            row.add(new Result(data.getDriverName(), data.getDriverName()));
            row.add(new Result(dateTimeFormatter.print(new DateTime(data.getDay())), data.getDay()));
            row.add(new Result(Converter.convertMinutes(new Long(data.getTotalAdjustedMinutes())), data.getTotalAdjustedMinutes()));
            records.add(row);
        }

        return records;
    }

    @Override
    public List<ColumnHeader> getColumnSummaryHeaders() {
        return null;
    }

}
