package com.inthinc.pro.reports.hos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.hos.model.HOSOrigin;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.model.DriverDOTLog;
import com.inthinc.pro.reports.tabular.ColumnHeader;
import com.inthinc.pro.reports.tabular.Result;
import com.inthinc.pro.reports.tabular.Tabular;
import com.inthinc.pro.reports.util.DateTimeUtil;
import com.inthinc.pro.reports.util.MessageUtil;

public class HosDriverDOTLogReportCriteria  extends ReportCriteria implements Tabular {


    private HOSDAO hosDAO;
    private DriverDAO driverDAO;
    private static final String   DISPLAY_DATE_FORMAT     = "yyyy-MM-dd HH:mm:ss z";
    protected DateTimeFormatter displayDateTimeFormatter;
    protected DateTimeFormatter dateTimeFormatter;
    protected DateTimeFormatter addedTimeFormatter;    

    
    public HosDriverDOTLogReportCriteria(Locale locale) {
        super(ReportType.HOS_DRIVER_DOT_LOG_REPORT, "", locale);
        displayDateTimeFormatter = DateTimeFormat.forPattern(DISPLAY_DATE_FORMAT).withLocale(locale);
        dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy").withLocale(locale);
        addedTimeFormatter = DateTimeFormat.forPattern("M/d/yy h:mm a").withLocale(locale);
        this.setIncludeZeroMilesDrivers(ReportCriteria.DEFAULT_INCLUDE_ZERO_MILES_DRIVERS);
        this.setIncludeInactiveDrivers(ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS);
    }
    
    public void init(Integer driverID, Interval interval){
        List<Driver> driverList = new ArrayList<Driver>();

        driverList.add(driverDAO.findByID(driverID));

        init(driverList, interval);
    }
    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    public void init(List<Driver> driverList, Interval interval) {
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
        
        initDataSet(interval, driverHOSRecordMap);
    }
    
    void initDataSet(Interval interval, Map<Driver, List<HOSRecord>> driverHOSRecordMap)
    {
        List<DriverDOTLog> driverDOTLogList = new ArrayList<DriverDOTLog>();
        
        for (Entry<Driver, List<HOSRecord>> entry : driverHOSRecordMap.entrySet()) {
            Driver driver = entry.getKey();

            String driverName = driver.getPerson().getFullNameLastFirst();
            List<HOSRecord> hosRecordList = entry.getValue();
            Collections.sort(hosRecordList);

            for (HOSRecord hosRecord : hosRecordList) {
                if (!interval.contains(hosRecord.getLogTime().getTime()) || hosRecord.getStatus() == null || hosRecord.getStatus().isInternal()) {
                    continue;
                }
                DriverDOTLog log = new DriverDOTLog();
                log.setDriverName(driverName);
                log.setOrigin(hosRecord.getOrigin());
                log.setEditUserName(hosRecord.getEditUserName());
                log.setLocation(hosRecord.getLocation());
                log.setService(hosRecord.getServiceID());
                log.setStatus(hosRecord.getStatus());
                log.setTimeStr(displayDateTimeFormatter.print(new DateTime(hosRecord.getLogTime(), DateTimeZone.forTimeZone(hosRecord.getTimeZone()))));
                log.setTimeAddedStr(addedTimeFormatter.print(new DateTime(hosRecord.getAddedTime(), DateTimeZone.forTimeZone(hosRecord.getTimeZone()))));
                log.setTrailer(hosRecord.getTrailerID());
                log.setVehicleName(hosRecord.getVehicleName());
                log.setChangedCnt(hosRecord.getChangedCnt());
                log.setDeleted(hosRecord.getDeleted());
                log.setDate(hosRecord.getLogTime());
                driverDOTLogList.add(log);
            }
        }
        setMainDataset(driverDOTLogList);
        
        addParameter("REPORT_START_DATE", dateTimeFormatter.print(interval.getStart()));
        addParameter("REPORT_END_DATE", dateTimeFormatter.print(interval.getEnd()));
        addParameter("REPORT_TYPE", getReport().getName());
        
//        setUseMetric(true);
        

    }
    
    public HOSDAO getHosDAO() {
        return hosDAO;
    }

    public void setHosDAO(HOSDAO hosDAO) {
        this.hosDAO = hosDAO;
    }

    @Override
    public List<String> getColumnHeaders() {
        ResourceBundle resourceBundle = ReportType.HOS_DRIVER_DOT_LOG_REPORT.getResourceBundle(getLocale());
        
        List<String> columnHeaders = new ArrayList<String>();
        for (int i = 1; i <= 8; i++)
            columnHeaders.add(MessageUtil.getBundleString(resourceBundle, "column."+i+".tabular"));
        
        return columnHeaders;
    }

    @Override
    public List<List<Result>> getTableRows() {
        ResourceBundle resourceBundle = ReportType.HOS_DRIVER_DOT_LOG_REPORT.getResourceBundle(getLocale());
        List<DriverDOTLog> dataList = (List<DriverDOTLog>)getMainDataset();
        if (dataList == null)
            return null;
        
        List<List<Result>>records = new ArrayList<List<Result>>();
        
        for (DriverDOTLog detail : dataList) {
            List<Result> row = new ArrayList<Result>();
            row.add(new Result(detail.getTimeStr(), detail.getDate()));
            row.add(new Result(detail.getDriverName(), detail.getDriverName()));
            row.add(new Result(detail.getVehicleName(), detail.getVehicleName()));
            row.add(new Result(detail.getTrailer(), detail.getTrailer()));
            row.add(new Result(detail.getService(), detail.getService()));
            row.add(new Result(detail.getLocation(), detail.getLocation()));
            String statusStr = MessageUtil.getBundleString(resourceBundle, "status."+detail.getStatus().getCode()); 
            row.add(new Result(statusStr, statusStr));
            String addedBy = (detail.getOrigin() == HOSOrigin.PORTAL) ? detail.getEditUserName() : MessageUtil.getBundleString(resourceBundle, "origin."+detail.getOrigin().toString());
            row.add(new Result(addedBy, addedBy));
            records.add(row);
        }
        return records;
    }
    @Override
    public List<ColumnHeader> getColumnSummaryHeaders() {
        return null;
    }

}
