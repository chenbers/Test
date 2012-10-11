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

public class PayrollDetailReportCriteria extends PayrollReportCriteria {

    
    public PayrollDetailReportCriteria(Locale locale) 
    {
        super(ReportType.PAYROLL_DETAIL, locale);
    }

    public void init(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval)
    {

        Account account = accountDAO.findByID(accountGroupHierarchy.getTopGroup().getAccountID());

        List<Group> reportGroupList = this.getReportGroupList(groupIDList, accountGroupHierarchy);
        List<Driver> driverList = this.getReportDriverList(reportGroupList);
        
        Map<Driver, List<HOSRecord>> driverHOSRecordMap = new HashMap<Driver, List<HOSRecord>> ();
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
    
    
    void initDataSet(Interval interval, Account account, GroupHierarchy accountGroupHierarchy, Map<Driver, List<HOSRecord>> driverHOSRecordMap)
    {
        super.initDataSet(interval, account);

        Date currentTime = new Date();
        
        List<PayrollData> dataList = new ArrayList<PayrollData>();
        
        for (Entry<Driver, List<HOSRecord>> entry : driverHOSRecordMap.entrySet()) {
            dataList.addAll(getDriverPayrollData(interval, accountGroupHierarchy, currentTime, entry.getKey(), entry.getValue()));

        }
        
        Collections.sort(dataList);
        setMainDataset(dataList);
        
    }

    
}
