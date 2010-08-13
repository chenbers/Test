package com.inthinc.pro.reports.hos;

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

import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.model.GroupHierarchy;
import com.inthinc.pro.reports.hos.model.PayrollData;
import com.inthinc.pro.reports.util.DateTimeUtil;

public class PayrollDetailReportCriteria extends PayrollReportCriteria {

    
    public PayrollDetailReportCriteria(Locale locale) 
    {
        super(ReportType.PAYROLL_DETAIL, locale);
    }

    public void init(Integer groupID, Interval interval)
    {

        Group topGroup = groupDAO.findByID(groupID);
        Account account = accountDAO.findByID(topGroup.getAccountID());
        List<Group> groupList = groupDAO.getGroupHierarchy(topGroup.getAccountID(), topGroup.getGroupID());
        List<Driver> driverList = driverDAO.getDrivers(groupID);
        Map<Driver, List<HOSRecord>> driverHOSRecordMap = new HashMap<Driver, List<HOSRecord>> ();
        for (Driver driver : driverList) {
            if (driver.getDot() == null)
                continue;
            DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
            Interval queryInterval = DateTimeUtil.getExpandedInterval(interval, dateTimeZone, 1, 1);
            driverHOSRecordMap.put(driver, hosDAO.getHOSRecords(driver.getDriverID(), queryInterval));
        }

        
        initDataSet(interval, account, topGroup, groupList, driverHOSRecordMap);
    }
    
    void initDataSet(Interval interval, Account account, Group topGroup,  List<Group> groupList, Map<Driver, List<HOSRecord>> driverHOSRecordMap)
    {
        super.initDataSet(interval, account);
        GroupHierarchy groupHierarchy = new GroupHierarchy(topGroup, groupList);

        Date currentTime = new Date();
        
        List<PayrollData> dataList = new ArrayList<PayrollData>();
        
        for (Entry<Driver, List<HOSRecord>> entry : driverHOSRecordMap.entrySet()) {
            dataList.addAll(getDriverPayrollData(interval, groupHierarchy, currentTime, entry.getKey(), entry.getValue()));

        }
        
        Collections.sort(dataList);
        setMainDataset(dataList);
        
    }

    
}
