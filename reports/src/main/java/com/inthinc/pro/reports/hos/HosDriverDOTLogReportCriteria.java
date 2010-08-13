package com.inthinc.pro.reports.hos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.hos.model.RuleSetType;
import com.inthinc.hos.rules.RuleSetFactory;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.model.DriverDOTLog;

public class HosDriverDOTLogReportCriteria  extends ReportCriteria {

    private static final Logger logger = Logger.getLogger(HosDriverDOTLogReportCriteria.class);

    private HOSDAO hosDAO;
    private static final String   DISPLAY_DATE_FORMAT     = "yyyy-MM-dd HH:mm:ss z";
    protected DateTimeFormatter displayDateTimeFormatter;
    protected DateTimeFormatter dateTimeFormatter;
    protected DateTimeFormatter addedTimeFormatter;    

    
    public HosDriverDOTLogReportCriteria(Locale locale) {
        super(ReportType.HOS_DRIVER_DOT_LOG_REPORT, "", locale);
        displayDateTimeFormatter = DateTimeFormat.forPattern(DISPLAY_DATE_FORMAT).withLocale(locale);
        dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy").withLocale(locale);
        addedTimeFormatter = DateTimeFormat.forPattern("M/d/yy h:mm a").withLocale(locale);
    }
    
    public void init(List<Driver> driverList, Interval interval)
    {
        Map<Driver, List<HOSRecord>> driverHOSRecordMap = new HashMap<Driver, List<HOSRecord>> ();
        
        if (driverList != null) {
            for (Driver driver : driverList) {
                if (driver.getDot() == null || driver.getDot().equals(RuleSetType.NON_DOT))
                    continue;
                DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
                DateTime queryStart = new DateMidnight(interval.getStart(), dateTimeZone).toDateTime();
                DateTime queryEnd = new DateMidnight(interval.getEnd(), dateTimeZone).plusDays(1).toDateTime();
                driverHOSRecordMap.put(driver, hosDAO.getHOSRecords(driver.getDriverID(), new Interval(queryStart, queryEnd)));
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
            for (HOSRecord hosRecord : entry.getValue()) {
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

}
