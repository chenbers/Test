package com.inthinc.pro.reports.hos;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

public class PayrollSignoffReportCriteria extends PayrollReportCriteria {

    
    public PayrollSignoffReportCriteria(Locale locale) 
    {
        super(ReportType.PAYROLL_SIGNOFF, locale);
    }

    public void init(Integer driverID, Interval interval)
    {
        Driver driver = driverDAO.findByID(driverID);
        Group topGroup = groupDAO.findByID(driver.getGroupID());
        Account account = accountDAO.findByID(topGroup.getAccountID());
        List<Group> groupList = groupDAO.getGroupHierarchy(topGroup.getAccountID(), topGroup.getGroupID());
        DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
        DateTime queryStart = new DateTime(interval.getStart(), dateTimeZone).minusDays(1);
        DateTime queryEnd = new DateTime(interval.getEnd(), dateTimeZone).plusDays(1);
        List<HOSRecord> driverHOSRecordList = hosDAO.getHOSRecords(driver.getDriverID(), new Interval(queryStart, queryEnd));
        initDataSet(interval, account, topGroup, groupList, driver, driverHOSRecordList);

    }
    
    void initDataSet(Interval interval, Account account, Group topGroup,  List<Group> groupList, Driver driver, List<HOSRecord> driverHOSRecordList)
    {
        super.initDataSet(interval, account);
        GroupHierarchy groupHierarchy = new GroupHierarchy(topGroup, groupList);

        Date currentTime = new Date();
        
        List<PayrollData> dataList = getDriverPayrollData(interval, groupHierarchy, currentTime, driver, driverHOSRecordList);
        
        Collections.sort(dataList);
        setMainDataset(dataList);

    }

    
}
