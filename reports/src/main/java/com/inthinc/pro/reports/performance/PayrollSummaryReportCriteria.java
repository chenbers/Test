package com.inthinc.pro.reports.performance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

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

public class PayrollSummaryReportCriteria  extends PayrollReportCriteria {

    
    public PayrollSummaryReportCriteria(Locale locale) 
    {
        super(ReportType.PAYROLL_SUMMARY, locale);
    }


    public void init(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval)
    {
        Account account = accountDAO.findByID(accountGroupHierarchy.getTopGroup().getAccountID());
        
        List<Group> reportGroupList = getReportGroupList(groupIDList, accountGroupHierarchy);
        List<Driver> driverList = getReportDriverList(reportGroupList);

        Map<Driver, List<HOSRecord>> driverHOSRecordMap = new HashMap<Driver, List<HOSRecord>> ();
        for (Driver driver : driverList) {
            if (driver.getDot() == null)
                continue;
            DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
            Interval queryInterval = DateTimeUtil.getExpandedInterval(interval, dateTimeZone, 1, 1);
            driverHOSRecordMap.put(driver, hosDAO.getHOSRecords(driver.getDriverID(), queryInterval, true));
        }

        
        initDataSet(interval, account, accountGroupHierarchy, driverHOSRecordMap);
    }
    
    void initDataSet(Interval interval, Account account, GroupHierarchy accountGroupHierarchy, Map<Driver, List<HOSRecord>> driverHOSRecordMap)
    {
        super.initDataSet(interval, account);

        Date currentTime = new Date();
        
        List<PayrollData> dataList = new ArrayList<PayrollData>();
        
        for (Entry<Driver, List<HOSRecord>> entry : driverHOSRecordMap.entrySet()) {
            dataList.addAll(getDriverPayrollData(interval, accountGroupHierarchy, currentTime, entry.getKey(), entry.getValue() == null ? new ArrayList<HOSRecord>() : entry.getValue()));

        }
        
        Collections.sort(dataList);
        setMainDataset(dataList);
        
    }

    
    
    
    @Override
    public List<String> getColumnHeaders() {
        // summary report doesn't include the 'day' column
        List<String> columnHeaders = super.getColumnHeaders();
        columnHeaders.remove(1);
        return columnHeaders;
    }

    private static final int TOTAL_COL = 6;

    @Override
    public List<List<Result>> getTableRows() {
        List<List<Result>>records = new ArrayList<List<Result>>();

        List<List<Result>> driverRecords = super.getTableRows();
        if (driverRecords == null || driverRecords.size() == 0) 
            return records;

        List<Result> summaryRow = null;
        String driver = null;
        
        for (List<Result> row : driverRecords) {
            String nextDriver = row.get(0).getDisplay();
            if (driver == null || driver.compareTo(nextDriver) != 0) {
                if (summaryRow != null)
                    records.add(fixRowDisplay(summaryRow));
                summaryRow = new ArrayList<Result>();
                summaryRow.add(row.get(0));
                for (int i = 0; i < 5; i++) {
                    summaryRow.add(new Result("",0));
                }
                summaryRow.add(new Result("",0l));
                driver = nextDriver;
                
            }
            for (int i = 0; i < 5; i++) {
                summaryRow.get(i+1).setSort((Integer)(summaryRow.get(i+1).getSort()) + (Integer)(row.get(i+2).getSort()));
            }
            summaryRow.get(TOTAL_COL).setSort((Long)(summaryRow.get(TOTAL_COL).getSort()) + (Long)(row.get(TOTAL_COL+1).getSort()));
        }
        if (summaryRow != null)
            records.add(fixRowDisplay(summaryRow));

        return records;
        
        
    }

    private List<Result> fixRowDisplay(List<Result> summaryRow) {
        for (int i = 0; i < 5; i++) {
            summaryRow.get(i+1).setDisplay(Converter.convertMinutes((Integer)(summaryRow.get(i+1).getSort())));
        }
        summaryRow.get(TOTAL_COL).setDisplay(Converter.convertMinutes((Long)(summaryRow.get(TOTAL_COL).getSort())));
        return summaryRow;
    }

    @Override
    public List<ColumnHeader> getColumnSummaryHeaders() {
        // summary report doesn't include the 'day' column
        List<ColumnHeader> columnHeaders = super.getColumnSummaryHeaders();
        columnHeaders.get(0).setColspan(1);
        return columnHeaders;
    }


}


