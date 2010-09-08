package com.inthinc.pro.reports.hos;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.hos.adjusted.HOSAdjustedList;
import com.inthinc.hos.model.HOSRecAdjusted;
import com.inthinc.hos.model.HOSStatus;
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
import com.inthinc.pro.reports.hos.converter.Converter;
import com.inthinc.pro.reports.hos.model.GroupHierarchy;
import com.inthinc.pro.reports.hos.model.HosZeroMiles;
import com.inthinc.pro.reports.hos.model.PayrollData;
import com.inthinc.pro.reports.hos.util.HOSUtil;
import com.inthinc.pro.reports.tabular.Result;
import com.inthinc.pro.reports.tabular.Tabular;
import com.inthinc.pro.reports.util.DateTimeUtil;
import com.inthinc.pro.reports.util.MessageUtil;

public class PayrollReportCriteria extends ReportCriteria implements Tabular {

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
    
    @Override
    public List<String> getColumnHeaders() {
        ResourceBundle resourceBundle = null;
        String bundleName = ReportType.PAYROLL_DETAIL.getResourceBundle();
        if (bundleName != null)
            resourceBundle = MessageUtil.getBundle(getLocale(), bundleName);
        else resourceBundle = MessageUtil.getBundle(getLocale());
        
        List<String> columnHeaders = new ArrayList<String>();
        for (int i = 1; i <= 8; i++)
            columnHeaders.add(MessageUtil.getBundleString(resourceBundle, "column."+i+".tabular"));
        
        return columnHeaders;
    }


    @Override
    public List<List<Result>> getTableRows() {
        List<PayrollData> dataList = (List<PayrollData>)getMainDataset();
        if (dataList == null || dataList.size()==0)
            return null;
        
        Map<String, Map<Date,Integer[]>> dataMap = new TreeMap<String, Map<Date,Integer[]>>();
        
        for (PayrollData data : dataList) {
            Map<Date,Integer[]> driverData = dataMap.get(data.getDriverName());
            if (driverData == null) {
                driverData = new TreeMap<Date, Integer[]>();
                dataMap.put(data.getDriverName(), driverData);
            }
            Integer[] timeData = driverData.get(data.getDay());
            if (timeData == null) {
                timeData = new Integer[5];
                for (int i = 0; i < 5; i++)
                        timeData[i] = 0;
                
                driverData.put(data.getDay(),timeData);
            }
                
            if (data.getStatus()== HOSStatus.OFF_DUTY || data.getStatus() == HOSStatus.OFF_DUTY_OCCUPANT) {
                timeData[0] = timeData[0]+data.getTotalAdjustedMinutes();
            }
            else if (data.getStatus()== HOSStatus.OFF_DUTY_AT_WELL) {
                timeData[1] = timeData[1]+data.getTotalAdjustedMinutes();
            }
            else if (data.getStatus()== HOSStatus.SLEEPER) {
                timeData[2] = timeData[2]+data.getTotalAdjustedMinutes();
            }
            else if (data.getStatus()== HOSStatus.DRIVING) {
                timeData[3] = timeData[3]+data.getTotalAdjustedMinutes();
            }
            else if (data.getStatus()== HOSStatus.ON_DUTY || data.getStatus() == HOSStatus.ON_DUTY_OCCUPANT) {
                timeData[4] = timeData[4]+data.getTotalAdjustedMinutes();
            }
            
        }        
        
        List<List<Result>>records = new ArrayList<List<Result>>();
        for (Entry<String, Map<Date,Integer[]>> driverEntry : dataMap.entrySet()) {
            for (Entry<Date, Integer[]> dayEntry : driverEntry.getValue().entrySet()) {
                List<Result> row = new ArrayList<Result>();
                row.add(new Result(driverEntry.getKey(), driverEntry.getKey()));
                row.add(new Result(dateTimeFormatter.print(new DateTime(dayEntry.getKey())), dayEntry.getKey()));
                long sum = 0;
                for (int i = 0; i < 5; i++) {
                    row.add(new Result(Converter.convertMinutesRound15(dayEntry.getValue()[i]), dayEntry.getValue()[i]));
                    if (i != 0)
                        sum += dayEntry.getValue()[i];
                }
                row.add(new Result(Converter.convertMinutesRound15(sum), sum));
                records.add(row);
            }
        }

        return records;
        
        
    }


    
}
