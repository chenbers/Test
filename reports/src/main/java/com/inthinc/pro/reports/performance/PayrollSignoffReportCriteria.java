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
import com.inthinc.pro.reports.performance.model.PayrollData;
import com.inthinc.pro.reports.util.DateTimeUtil;

public class PayrollSignoffReportCriteria extends PayrollReportCriteria {

    
    public PayrollSignoffReportCriteria(Locale locale) 
    {
        super(ReportType.PAYROLL_SIGNOFF, locale);
    }
    public void init(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval)
    {
        Account account = accountDAO.findByID(accountGroupHierarchy.getTopGroup().getAccountID());

        
        List<Group> reportGroupList = getReportGroupList(groupIDList, accountGroupHierarchy);
        List<Driver> reportDriverList = getReportDriverList(reportGroupList);
        Map<Driver, List<HOSRecord>> driverHOSRecordMap = new HashMap<Driver, List<HOSRecord>> ();
        for (Driver driver : reportDriverList) {
            if(includeDriver(getDriverDAO(), driver.getDriverID(), interval)){
                DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
                Interval queryInterval = DateTimeUtil.getExpandedInterval(interval, dateTimeZone, 0, 1);
                List<HOSRecord> driverHOSRecordList = hosDAO.getHOSRecords(driver.getDriverID(), queryInterval, true);
                driverHOSRecordMap.put(driver, driverHOSRecordList);
            }
        }
        
        initDataSet(interval, account, accountGroupHierarchy, driverHOSRecordMap);

    }

    public void init(GroupHierarchy accountGroupHierarchy, Integer driverID, Interval interval)
    {
        Account account = accountDAO.findByID(accountGroupHierarchy.getTopGroup().getAccountID());
        Driver driver = getDriverDAO().findByID(driverID);
        DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
        Interval queryInterval = DateTimeUtil.getExpandedInterval(interval, dateTimeZone, 0, 1);
        List<HOSRecord> driverHOSRecordList = hosDAO.getHOSRecords(driver.getDriverID(), queryInterval, true);
        Map<Driver, List<HOSRecord>> driverHOSRecordMap = new HashMap<Driver, List<HOSRecord>> ();
        driverHOSRecordMap.put(driver, driverHOSRecordList);
        initDataSet(interval, account, accountGroupHierarchy, driverHOSRecordMap);

    }
    
    void initDataSet(Interval interval, Account account, GroupHierarchy accountGroupHierarchy, Map<Driver, List<HOSRecord>> driverHOSRecordMap)
    {
        super.initDataSet(interval, account);

        Date currentTime = new Date();
        List<PayrollData> dataList = new ArrayList<PayrollData>();
        for (Entry<Driver, List<HOSRecord>> entry : driverHOSRecordMap.entrySet()) {
            Driver driver = entry.getKey();
            if(includeDriver(getDriverDAO(), driver.getDriverID(), interval)){
                List<PayrollData> driverDataList = getDriverPayrollData(interval, accountGroupHierarchy, currentTime, entry.getKey(), entry.getValue());
                Collections.sort(driverDataList);
                dataList.addAll(driverDataList);
            }
        }
        setMainDataset(dataList);
    }
}
