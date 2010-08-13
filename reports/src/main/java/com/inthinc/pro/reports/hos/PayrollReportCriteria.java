package com.inthinc.pro.reports.hos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.hos.adjusted.HOSAdjustedList;
import com.inthinc.hos.model.HOSRecAdjusted;
import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.model.GroupHierarchy;
import com.inthinc.pro.reports.hos.model.PayrollData;
import com.inthinc.pro.reports.hos.util.HOSUtil;
import com.inthinc.pro.reports.util.DateTimeUtil;

public class PayrollReportCriteria extends ReportCriteria {

    protected DateTimeFormatter dateTimeFormatter; 
    
    protected AccountDAO accountDAO;
    protected GroupDAO groupDAO;
    protected DriverDAO driverDAO;
    protected HOSDAO hosDAO;

    
    public PayrollReportCriteria(ReportType reportType, Locale locale) 
    {
        super(reportType, "", locale);
        dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy").withLocale(locale);
    }

    void initDataSet(Interval interval, Account account)
    {
        addParameter("REPORT_START_DATE", dateTimeFormatter.print(interval.getStart()));
        addParameter("REPORT_END_DATE", dateTimeFormatter.print(interval.getEnd()));
        addParameter("CUSTOMER", account);
        addParameter("CUSTOMER_NAME", account.getAcctName());
        addParameter("CUSTOMER_ADDRESS", (account.getAddress() == null) ? "" : account.getAddress().getDisplayString());
    }

    protected List<PayrollData> getDriverPayrollData(Interval interval, GroupHierarchy groupHierarchy, Date currentTime, Driver driver, List<HOSRecord> hosRecordList) {
        
        List<PayrollData> dataList = new ArrayList<PayrollData>();
        
        HOSAdjustedList adjustedList = HOSUtil.getAdjustedListFromLogList(hosRecordList);
        Group group = groupHierarchy.getGroup(driver.getGroupID());
        String groupName = groupHierarchy.getFullName(group);
        String groupAddress = group.getAddress() == null ? "" : group.getAddress().getDisplayString();
        
        String driverName = driver.getPerson().getFullNameLastFirst();
        String employeeID = driver.getPerson().getEmpid();
        
        List<DateTime> dayList = DateTimeUtil.getDayList(interval, DateTimeZone.forTimeZone(driver.getPerson().getTimeZone()));
        
        for (DateTime day : dayList) {
            List<HOSRecAdjusted> dayLogList =adjustedList.getAdjustedListForDay(day.toDate(), currentTime, true);
            
            for (HOSRecAdjusted log : dayLogList)
            {
                if (log.getTotalIncrements() > 0)
                {
                    //String groupName, String groupAddress, Integer driverId, String driverName, TimeZone timeZone, String employeeID,
                    PayrollData item = new PayrollData(groupName, groupAddress, driver.getDriverID(), driverName, employeeID,
                            day.toDate(), log.getStatus(), log.getTotalIncrements()*15);
                    dataList.add(item);
                }
            }

        }
        
        return dataList;
    }

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    public HOSDAO getHosDAO() {
        return hosDAO;
    }

    public void setHosDAO(HOSDAO hosDAO) {
        this.hosDAO = hosDAO;
    }
    
}
