package com.inthinc.pro.reports.performance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.hos.model.DayData;
import com.inthinc.hos.model.HOSRec;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.hos.rules.HOSRules;
import com.inthinc.hos.rules.RuleSetFactory;
import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.dao.util.HOSUtil;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.GroupListReportCriteria;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.performance.model.BreakDataSummary;
import com.inthinc.pro.reports.util.DateTimeUtil;

public class ThirtyMinuteBreaksReportCriteria extends GroupListReportCriteria {
    
    protected DateTimeFormatter dateTimeFormatter;
    
    protected AccountDAO accountDAO;
    
    protected HOSDAO hosDAO;

    public ThirtyMinuteBreaksReportCriteria (Locale locale) {
        super(ReportType.THIRTY_MINUTE_BREAKS, locale);
        dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy").withLocale(locale);
        this.setIncludeInactiveDrivers(ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS);
        this.setIncludeZeroMilesDrivers(ReportCriteria.DEFAULT_INCLUDE_ZERO_MILES_DRIVERS);
    }

    public void init(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval) {
        Account account = accountDAO.findByID(accountGroupHierarchy.getTopGroup().getAccountID());

        List<Group> reportGroupList = this.getReportGroupList(groupIDList, accountGroupHierarchy);
        List<Driver> driverList = this.getReportDriverList(reportGroupList);
        
        Map<Driver, List<HOSRec>> driverHOSRecMap = new HashMap<Driver, List<HOSRec>> ();
        
        for (Driver driver : driverList) {
            if(includeDriver(getDriverDAO(), driver.getDriverID(), interval)){
                RuleSetType driverDot = driver.getDot();
                if(driverDot == null) continue;
                DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
                Interval queryInterval = DateTimeUtil.getExpandedInterval(interval, dateTimeZone, 1, 1);
                List<HOSRecord> hosRecordList = hosDAO.getHOSRecords(driver.getDriverID(), queryInterval, false);
                List<HOSRec> hosRecList = HOSUtil.getRecListFromLogList(hosRecordList, interval.getEnd().toDate(), !(driverDot.equals(RuleSetType.NON_DOT)));
                driverHOSRecMap.put(driver, hosRecList);
            }
        }
        initDataSet(interval, account, accountGroupHierarchy, driverHOSRecMap);
    }

    void initDataSet(Interval interval, Account account, GroupHierarchy accountGroupHierarchy, Map<Driver, List<HOSRec>> driverHOSRecMap) {
        addParameter("REPORT_START_DATE", dateTimeFormatter.print(interval.getStart()));
        addParameter("REPORT_END_DATE", dateTimeFormatter.print(interval.getEnd()));
        addParameter("CUSTOMER", account);
        addParameter("CUSTOMER_NAME", account.getAcctName());
        addParameter("CUSTOMER_ADDRESS", (account.getAddress() == null) ? "" : account.getAddress().getDisplayString());
        
        List<BreakDataSummary> dataList = new ArrayList<BreakDataSummary>();
        
        for (Entry<Driver, List<HOSRec>> entry : driverHOSRecMap.entrySet()) {
            Driver driver = entry.getKey();
            List<HOSRec> hosRec = entry.getValue();
            HOSRules ruleSet = RuleSetFactory.getRulesForRuleSetType(driver.getDot());
            DateTimeZone timeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
            List<DayData> dayData = ruleSet.getDayData(hosRec, interval, timeZone, 30, 60);
            BreakDataSummary breakData = compileBreakData(interval, accountGroupHierarchy, driver, dayData);
            dataList.add(breakData);
        }
        
        Collections.sort(dataList);
        setMainDataset(dataList);
        
    }

    protected BreakDataSummary compileBreakData(Interval interval, GroupHierarchy groupHierarchy, Driver driver, List<DayData> dayData) {
        BreakDataSummary breakData = new BreakDataSummary(interval, groupHierarchy, driver, dayData);
        return breakData;
        
    }
    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
    
    public DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }

    public void setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    public HOSDAO getHosDAO() {
        return hosDAO;
    }

    public void setHosDAO(HOSDAO hosDAO) {
        this.hosDAO = hosDAO;
    }
    
}
