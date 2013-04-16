package com.inthinc.pro.reports.hos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.hos.model.RuleSetType;
import com.inthinc.hos.util.DateUtil;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.GroupListReportCriteria;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.model.HosEdit;
import com.inthinc.pro.reports.tabular.ColumnHeader;
import com.inthinc.pro.reports.tabular.Result;
import com.inthinc.pro.reports.tabular.Tabular;
import com.inthinc.pro.reports.util.DateTimeUtil;
import com.inthinc.pro.reports.util.MessageUtil;

public class HosEditsReportCriteria extends GroupListReportCriteria implements Tabular {


    private HOSDAO hosDAO;
    private GroupDAO groupDAO;
    private static final String   DISPLAY_DATE_FORMAT     = "yyyy-MM-dd HH:mm";
    protected DateTimeFormatter displayDateTimeFormatter;
    protected DateTimeFormatter dateTimeFormatter;
    protected DateTimeFormatter addedTimeFormatter;    

    
    public HosEditsReportCriteria(Locale locale) {
        super(ReportType.HOS_EDITS, locale);
        displayDateTimeFormatter = DateTimeFormat.forPattern(DISPLAY_DATE_FORMAT).withLocale(locale);
        dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy").withLocale(locale);
        addedTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS").withLocale(locale);
        this.setIncludeInactiveDrivers(ReportCriteria.DEFAULT_INCLUDE_INACTIVE_DRIVERS);
        this.setIncludeZeroMilesDrivers(ReportCriteria.DEFAULT_INCLUDE_ZERO_MILES_DRIVERS);
    }
    
    public void init(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval){
        
        List<Group> reportGroupList = getReportGroupList(groupIDList, accountGroupHierarchy);
                    
        List<Driver> driverList = getReportDriverList(reportGroupList);

        Map<Driver, List<HOSRecord>> driverHOSRecordMap = new HashMap<Driver, List<HOSRecord>> ();
        
        if (driverList != null) {
            for (Driver driver : driverList) {
                if(includeDriver(getDriverDAO(), driver.getDriverID(), interval)){
                    if (driver.getDot() == null || driver.getDot().equals(RuleSetType.NON_DOT))
                        continue;
                    DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
                    Interval queryInterval = DateTimeUtil.getExpandedInterval(interval, dateTimeZone, 0, 1);
                    driverHOSRecordMap.put(driver, hosDAO.getHOSRecords(driver.getDriverID(), queryInterval, false));
                }
            }
        }
        initDataSet(accountGroupHierarchy, interval, driverHOSRecordMap);

    }
    
    void initDataSet(GroupHierarchy accountGroupHierarchy, Interval interval, Map<Driver, List<HOSRecord>> driverHOSRecordMap)
    {
        List<HosEdit> hosEditList = new ArrayList<HosEdit>();
        
        
        for (Entry<Driver, List<HOSRecord>> entry : driverHOSRecordMap.entrySet()) {
            Driver driver = entry.getKey();
            String driverGroupName = getFullGroupName(accountGroupHierarchy, driver.getGroupID());
            String driverName = driver.getPerson().getFullNameLastFirst();
            List<HOSRecord> hosRecordList = entry.getValue();
            Collections.sort(hosRecordList);

            for (HOSRecord hosRecord : hosRecordList) {
                if (hosRecord.getEditUserName() == null || hosRecord.getEditUserName().isEmpty() || !interval.contains(hosRecord.getLogTime().getTime()) || hosRecord.getStatus() == null || hosRecord.getStatus().isInternal())
                    continue;
                HosEdit hosEdit = new HosEdit();
                hosEdit.setDriverName(driverName);
                hosEdit.setEmployeeID(driver.getPerson().getEmpid());
                hosEdit.setEditUserName(hosRecord.getEditUserName());
                hosEdit.setStatus(hosRecord.getStatus());
                hosEdit.setLogDate(hosRecord.getLogTime());
                hosEdit.setLogTime(displayDateTimeFormatter.print(new DateTime(hosRecord.getLogTime(), DateTimeZone.forTimeZone(hosRecord.getTimeZone()))));
                Date adjustedTime = calcAdjustedTime(hosRecord.getLogTime());
                hosEdit.setAdjustedTime(displayDateTimeFormatter.print(new DateTime(adjustedTime, DateTimeZone.forTimeZone(hosRecord.getTimeZone()))));
                hosEdit.setGroupName(driverGroupName);
                hosEdit.setDateAdded(addedTimeFormatter.print(new DateTime(hosRecord.getAddedTime(), DateTimeZone.UTC)));
                hosEdit.setDateLastUpdated(addedTimeFormatter.print(new DateTime(hosRecord.getDateLastUpdated(), DateTimeZone.UTC)));
                hosEditList.add(hosEdit);
            }
        }
        setMainDataset(hosEditList);
        
        addParameter("REPORT_START_DATE", dateTimeFormatter.print(interval.getStart()));
        addParameter("REPORT_END_DATE", dateTimeFormatter.print(interval.getEnd()));

    }
    private Date calcAdjustedTime(Date logTimeDate)
    {
        // time on 15 minute boundaries
        long ms = logTimeDate.getTime();
        
        long timeInMin = ms / DateUtil.MILLISECONDS_IN_MINUTE;
        
        if (timeInMin % 15 > 7)
        {
            ms = ((timeInMin+15l) / 15l) * 15l * DateUtil.MILLISECONDS_IN_MINUTE;
        }
        else 
        {
            ms = (timeInMin / 15l) * 15l * DateUtil.MILLISECONDS_IN_MINUTE;
        }
        
        return new Date(ms);
    }    
    public HOSDAO getHosDAO() {
        return hosDAO;
    }

    public void setHosDAO(HOSDAO hosDAO) {
        this.hosDAO = hosDAO;
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    @Override
    public List<String> getColumnHeaders() {
        List<String> columnHeaders = new ArrayList<String>();
        ResourceBundle resourceBundle = ReportType.HOS_EDITS.getResourceBundle(getLocale());
        
        for (int i = 1; i <= 7; i++)
            columnHeaders.add(MessageUtil.getBundleString(resourceBundle, "column."+i+".tabular"));
        return columnHeaders;
    }

    @Override
    public List<List<Result>> getTableRows() {
        List<List<Result>>records = new ArrayList<List<Result>>();
        ResourceBundle resourceBundle = ReportType.HOS_EDITS.getResourceBundle(getLocale());
        
        List<HosEdit> dataList = (List<HosEdit>)getMainDataset();
        if (dataList == null)
            return null;
        
        for (HosEdit hosEdit : dataList) {
            List<Result> row = new ArrayList<Result>();
            row.add(new Result(hosEdit.getGroupName(), hosEdit.getGroupName()));
            row.add(new Result(hosEdit.getDriverName(), hosEdit.getDriverName()));
            row.add(new Result(hosEdit.getEmployeeID(), hosEdit.getEmployeeID()));
            String statusStr = MessageUtil.getBundleString(resourceBundle, "status."+hosEdit.getStatus().getCode()); 
            row.add(new Result(statusStr, statusStr));
            row.add(new Result(hosEdit.getLogTime(), hosEdit.getLogDate()));
            row.add(new Result(hosEdit.getAdjustedTime(), hosEdit.getLogDate()));
            row.add(new Result(hosEdit.getEditUserName(), hosEdit.getEditUserName()));
            records.add(row);
        }
        return records;
    }
    @Override
    public List<ColumnHeader> getColumnSummaryHeaders() {
        return null;
    }

}
