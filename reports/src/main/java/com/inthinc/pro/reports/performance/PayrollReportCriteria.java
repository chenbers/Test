package com.inthinc.pro.reports.performance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.hos.model.HOSStatus;
import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.GroupListReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.converter.Converter;
import com.inthinc.pro.reports.performance.model.PayrollData;
import com.inthinc.pro.reports.performance.model.PayrollHOSRec;
import com.inthinc.pro.reports.tabular.ColumnHeader;
import com.inthinc.pro.reports.tabular.Result;
import com.inthinc.pro.reports.tabular.Tabular;
import com.inthinc.pro.reports.util.DateTimeUtil;
import com.inthinc.pro.reports.util.MessageUtil;

public class PayrollReportCriteria extends GroupListReportCriteria implements Tabular {

    protected DateTimeFormatter dateTimeFormatter; 
    
    protected AccountDAO accountDAO;
    protected GroupDAO groupDAO;
    protected HOSDAO hosDAO;

    
    public PayrollReportCriteria(ReportType reportType, Locale locale) 
    {
        super(reportType, locale);
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

    
    protected List<PayrollHOSRec> getCompensatedRecordList(List<HOSRecord> hosRecList, Date startDate, Date endDate, Date currentDate)
    {
        List<PayrollHOSRec> compensatedRecList = new ArrayList<PayrollHOSRec>();
        
        for (HOSRecord rec : hosRecList)
        {
            HOSStatus status = null;
            if (rec.getStatus() == HOSStatus.DRIVING ||
                rec.getStatus() == HOSStatus.SLEEPER ||
                rec.getStatus() == HOSStatus.OFF_DUTY_AT_WELL)
                status = rec.getStatus();
            else if (rec.getStatus() == HOSStatus.ON_DUTY ||
                rec.getStatus() == HOSStatus.ON_DUTY_OCCUPANT ||
                rec.getStatus() == HOSStatus.TRAVELTIME_OCCUPANT) {
                status = HOSStatus.ON_DUTY;
            }
            else if (rec.getStatus() == HOSStatus.OFF_DUTY ||
                    rec.getStatus() == HOSStatus.OFF_DUTY_OCCUPANT ||
                    rec.getStatus() == HOSStatus.HOS_PERSONALTIME) {
                    status = HOSStatus.OFF_DUTY;
            }
            else {
                continue;
            }
            compensatedRecList.add(new PayrollHOSRec(status, rec.getLogTime(), 0l));
        }
        
        
        if (endDate.after(currentDate))
            endDate = currentDate;
        
        for (PayrollHOSRec rec : compensatedRecList) {
            
            rec.setTotalSeconds(deltaSeconds(endDate, rec.getLogTimeDate()));
            endDate = rec.getLogTimeDate();
        }
        
        if (endDate.after(startDate))
            compensatedRecList.add(new PayrollHOSRec(HOSStatus.OFF_DUTY, startDate, deltaSeconds(endDate, startDate)));
            
        return compensatedRecList;
    }
    
    public Long deltaSeconds(Date endTime, Date startTime) {
        Long delta = (endTime.getTime() - startTime.getTime()) / 1000l;
        if (delta < 0l) {
            return 0l;
        }
        return delta;
    }

    public static int secondsToMinutes(Long seconds) {
        
        long minutes = seconds / 60l;
        if (seconds % 60 >= 30)
            minutes++;
        
        return (int)minutes;
        
    }

    
    protected List<PayrollData> getDriverPayrollData(Interval interval, GroupHierarchy groupHierarchy, Date currentTime, Driver driver, List<HOSRecord> hosRecordList) {

        Interval driverInterval =DateTimeUtil.getStartEndIntervalInTimeZone(interval, DateTimeZone.forTimeZone(driver.getPerson().getTimeZone()));
        List<PayrollData> dataList = new ArrayList<PayrollData>();
        List<PayrollHOSRec> compensatedRecList = getCompensatedRecordList(hosRecordList, driverInterval.getStart().toDate(), driverInterval.getEnd().toDate(), currentTime);
        Collections.reverse(compensatedRecList);
        
        Group group = groupHierarchy.getGroup(driver.getGroupID());
        String groupName = getFullGroupName(groupHierarchy, group.getGroupID());
        String groupAddress = group.getAddress() == null ? "" : group.getAddress().getDisplayString();

        String driverName = driver.getPerson().getFullNameLastFirst();
        String employeeID = driver.getPerson().getEmpid();
        List<DateTime> dayList = DateTimeUtil.getDayList(interval, DateTimeZone.forTimeZone(driver.getPerson().getTimeZone()));

        for (DateTime day : dayList) {
            List<PayrollHOSRec> dayLogList = getListForDay(compensatedRecList, day.toDate(), day.plusDays(1).toDate());
            Map<HOSStatus, Long> dayMap = new HashMap<HOSStatus, Long>();
            Long dayCompTotal = 0l;
            for (PayrollHOSRec log : dayLogList)
            {
                if (log.getTotalSeconds() > 0)
                {
                    Long seconds = 0l;
                    if (dayMap.get(log.getStatus()) != null) {
                        seconds = dayMap.get(log.getStatus());
                    }
                    seconds += log.getTotalSeconds();
                    dayMap.put(log.getStatus(), seconds);
                    if (log.getStatus() != HOSStatus.OFF_DUTY)
                        dayCompTotal += log.getTotalSeconds();
                }
            }
            int totalCompMinutes = 0;
            for (HOSStatus status : dayMap.keySet()) {
                if (status != HOSStatus.OFF_DUTY) {
                    totalCompMinutes += secondsToMinutes(dayMap.get(status));
                }
            }
            int diffMinutes = secondsToMinutes(dayCompTotal) - totalCompMinutes;
            
            LinkedHashMap<HOSStatus,Long> sortedDayMap = sortMapByValue(dayMap, (diffMinutes < 0));
            
            int totalMinForDay = 0;
            for (HOSStatus status : sortedDayMap.keySet()) {
                int addMinute = 0;
                if (status != HOSStatus.OFF_DUTY) {
                    if (diffMinutes > 0) {
                        addMinute = 1;
                        diffMinutes -= 1;
                    }
                    else if (diffMinutes < 0) {
                        addMinute = -1;
                        diffMinutes += 1;
                    }
                }
                PayrollData item = new PayrollData(driver.getGroupID(), groupName, groupAddress, driver.getDriverID(), driverName, employeeID, day.toDate(), 
                        status, secondsToMinutes(sortedDayMap.get(status)) + addMinute, day);
                item.setDayStr(dateTimeFormatter.print(day));
                dataList.add(item);
                
                totalMinForDay = totalMinForDay + item.getTotalAdjustedMinutes();
            }
            
            long endMillis = new DateTime().getMillis();
            if (endMillis > day.plusDays(1).getMillis())
                endMillis = day.plusDays(1).getMillis();
            int minutesInDay = (int)((endMillis - day.getMillis()) / 60000l);
            if (totalMinForDay != minutesInDay) {
                
                int diff = minutesInDay - totalMinForDay;
                for (PayrollData item : dataList)
                    if (item.getDateTime().equals(day) && item.getStatus() == HOSStatus.OFF_DUTY) {
                        item.addTotalAdjustedMinutes(diff);
                        break;
                    }
            }

        }
        
        return dataList;
    }


    private LinkedHashMap<HOSStatus, Long> sortMapByValue(Map<HOSStatus, Long> dayMap, boolean isAscending) {
        Map<HOSStatus,Long> tempMap=new HashMap<HOSStatus,Long>(dayMap);
        LinkedHashMap<HOSStatus,Long> sortedDayMap=new LinkedHashMap<HOSStatus,Long>();

        for(int i=0;i<dayMap.size();i++){
            Map.Entry<HOSStatus,Long> sortEntry=null;
            Long sortValue=null;
            for(Map.Entry<HOSStatus,Long> entry:tempMap.entrySet()){
                Long entrySecs = entry.getValue() % 60;
                if(sortValue == null || 
                  (isAscending && entrySecs<sortValue) ||
                  (!isAscending && entrySecs>sortValue) )
                {
                    sortValue=entrySecs;
                    sortEntry=entry;
                }
            }
            tempMap.remove(sortEntry.getKey());
            sortedDayMap.put(sortEntry.getKey(),sortEntry.getValue());
        }
        return sortedDayMap;
    }

    public List<PayrollHOSRec> getListForDay(List<PayrollHOSRec> hosList, Date logDay, Date endOfDayCurrentDate)
    {
        final long      MILLISECONDS_IN_SECOND  = 1000;
        List<PayrollHOSRec> listForDay = new ArrayList<PayrollHOSRec>();
        
        if (hosList != null)
        {
            for (PayrollHOSRec log : hosList)
            {
                Date logTime = log.getLogTimeDate();
                Date logEndTime  = new Date(logTime.getTime() + (log.getTotalSeconds() * MILLISECONDS_IN_SECOND));
                Date startTime;
                Date endTime;
                long seconds;

                // log begin before or starts exactly on current day
                if ((logTime.before(logDay) || logTime.equals(logDay)) && logEndTime.after(logDay))
                {
                    startTime = logDay;
                    endTime = (logEndTime.after(endOfDayCurrentDate)) ? endOfDayCurrentDate : logEndTime;
                    seconds = (endTime.getTime() - startTime.getTime())/MILLISECONDS_IN_SECOND;
                    listForDay.add(new PayrollHOSRec(log.getStatus(), startTime, seconds));
                }
                
                
                // log starts within current day
                if (logTime.after(logDay) && logTime.before(endOfDayCurrentDate))
                {
                    startTime = logTime;
                    endTime = (logEndTime.after(endOfDayCurrentDate)) ? endOfDayCurrentDate : logEndTime;
                    seconds = (endTime.getTime() - startTime.getTime())/MILLISECONDS_IN_SECOND;

                    listForDay.add(new PayrollHOSRec(log.getStatus(), startTime, seconds));
                }
                // if log starts after end of current day, we are done
                else if (logTime.after(endOfDayCurrentDate))
                {
                    break;
                }
                
                
            }
        }
        return listForDay;
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

    public HOSDAO getHosDAO() {
        return hosDAO;
    }

    public void setHosDAO(HOSDAO hosDAO) {
        this.hosDAO = hosDAO;
    }
    
    @Override
    public List<String> getColumnHeaders() {
        return getColumnHeaders(ReportType.PAYROLL_DETAIL, 8);
    }
    
    public List<String> getColumnHeaders(ReportType reportType, int numberOfCols) {
        ResourceBundle resourceBundle = reportType.getResourceBundle(getLocale());
        
        List<String> columnHeaders = new ArrayList<String>();
        for (int i = 1; i <= numberOfCols; i++)
            columnHeaders.add(MessageUtil.getBundleString(resourceBundle, "column."+i+".tabular"));
        
        return columnHeaders;
    }


    @Override
    public List<List<Result>> getTableRows() {
        List<PayrollData> dataList = (List<PayrollData>)getMainDataset();
        if (dataList == null || dataList.size()==0)
            return null;
        
        Map<String, Map<DateTime,Integer[]>> dataMap = new TreeMap<String, Map<DateTime,Integer[]>>();
        
        for (PayrollData data : dataList) {
            Map<DateTime,Integer[]> driverData = dataMap.get(data.getDriverName());
            if (driverData == null) {
                driverData = new TreeMap<DateTime, Integer[]>();
                dataMap.put(data.getDriverName(), driverData);
            }
            Integer[] timeData = driverData.get(data.getDateTime());
            if (timeData == null) {
                timeData = new Integer[5];
                for (int i = 0; i < 5; i++)
                        timeData[i] = 0;
                
                driverData.put(data.getDateTime(),timeData);
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
        for (Entry<String, Map<DateTime,Integer[]>> driverEntry : dataMap.entrySet()) {
            for (Entry<DateTime, Integer[]> dayEntry : driverEntry.getValue().entrySet()) {
                List<Result> row = new ArrayList<Result>();
                row.add(new Result(driverEntry.getKey(), driverEntry.getKey()));
                row.add(new Result(dateTimeFormatter.print(new DateTime(dayEntry.getKey())), dayEntry.getKey()));
                long sum = 0;
                for (int i = 0; i < 5; i++) {
                    row.add(new Result(Converter.convertMinutes(new Long(dayEntry.getValue()[i])), dayEntry.getValue()[i]));
                    if (i != 0)
                        sum += dayEntry.getValue()[i];
                }
                row.add(new Result(Converter.convertMinutes(sum), sum));
                records.add(row);
            }
        }

        return records;
        
        
    }

    @Override
    public List<ColumnHeader> getColumnSummaryHeaders() {
        ResourceBundle resourceBundle = ReportType.PAYROLL_DETAIL.getResourceBundle(getLocale());

        List<ColumnHeader> columnHeaders = new ArrayList<ColumnHeader>();
        columnHeaders.add(new ColumnHeader("", 2));
        columnHeaders.add(new ColumnHeader(MessageUtil.getBundleString(resourceBundle, "column.tabularHours"), 6));
        
        return columnHeaders;
    }

    
}
