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
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.model.GroupHierarchy;
import com.inthinc.pro.reports.performance.model.PayrollData;
import com.inthinc.pro.reports.util.DateTimeUtil;

public class PayrollSignoffReportCriteria extends PayrollReportCriteria {

    
    public PayrollSignoffReportCriteria(Locale locale) 
    {
        super(ReportType.PAYROLL_SIGNOFF, locale);
    }
    public void init(Integer userGroupID, List<Integer> groupIDList, Interval interval)
    {
        Group topGroup = groupDAO.findByID(userGroupID);
        List<Group> groupList = groupDAO.getGroupHierarchy(topGroup.getAccountID(), topGroup.getGroupID());
        Account account = accountDAO.findByID(topGroup.getAccountID());
        
        List<Group> reportGroupList = getReportGroupList(groupIDList, new GroupHierarchy(topGroup, groupList));
        List<Driver> reportDriverList = getReportDriverList(reportGroupList);
        Map<Driver, List<HOSRecord>> driverHOSRecordMap = new HashMap<Driver, List<HOSRecord>> ();
        for (Driver driver : reportDriverList) {
            DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
            Interval queryInterval = DateTimeUtil.getExpandedInterval(interval, dateTimeZone, 0, 1);
            List<HOSRecord> driverHOSRecordList = hosDAO.getHOSRecords(driver.getDriverID(), queryInterval, true);
            driverHOSRecordMap.put(driver, driverHOSRecordList);
        }
        
        initDataSet(interval, account, topGroup, groupList, driverHOSRecordMap);

    }

    public void init(Integer driverID, Interval interval)
    {
        Driver driver = getDriverDAO().findByID(driverID);
        Group topGroup = groupDAO.findByID(driver.getGroupID());
        Account account = accountDAO.findByID(topGroup.getAccountID());
        List<Group> groupList = groupDAO.getGroupHierarchy(topGroup.getAccountID(), topGroup.getGroupID());
        DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
        Interval queryInterval = DateTimeUtil.getExpandedInterval(interval, dateTimeZone, 0, 1);
        List<HOSRecord> driverHOSRecordList = hosDAO.getHOSRecords(driver.getDriverID(), queryInterval, true);
        Map<Driver, List<HOSRecord>> driverHOSRecordMap = new HashMap<Driver, List<HOSRecord>> ();
        driverHOSRecordMap.put(driver, driverHOSRecordList);
        initDataSet(interval, account, topGroup, groupList, driverHOSRecordMap);

    }
    
    void initDataSet(Interval interval, Account account, Group topGroup,  List<Group> groupList, Map<Driver, List<HOSRecord>> driverHOSRecordMap)
    {
        super.initDataSet(interval, account);
        GroupHierarchy groupHierarchy = new GroupHierarchy(topGroup, groupList);

        Date currentTime = new Date();
        List<PayrollData> dataList = new ArrayList<PayrollData>();
        for (Entry<Driver, List<HOSRecord>> entry : driverHOSRecordMap.entrySet()) {
        
            List<PayrollData> driverDataList = getDriverPayrollData(interval, groupHierarchy, currentTime, entry.getKey(), entry.getValue());
            Collections.sort(driverDataList);
            dataList.addAll(driverDataList);
        }
        setMainDataset(dataList);

    }

    
}
