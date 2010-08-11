package com.inthinc.pro.reports.hos;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.hos.adjusted.HOSAdjustedList;
import com.inthinc.hos.model.DayTotals;
import com.inthinc.hos.model.HOSOrigin;
import com.inthinc.hos.model.HOSRec;
import com.inthinc.hos.model.HOSRecAdjusted;
import com.inthinc.hos.model.HOSRecBase;
import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.hos.rules.HOSRules;
import com.inthinc.hos.rules.RuleSetFactory;
import com.inthinc.hos.util.DateUtil;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.hos.HOSOccupantLog;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.model.hos.HOSVehicleDayData;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.model.GroupHierarchy;
import com.inthinc.pro.reports.hos.model.HosDailyDriverLog;
import com.inthinc.pro.reports.hos.model.PayrollData;
import com.inthinc.pro.reports.hos.model.Recap;
import com.inthinc.pro.reports.hos.model.RecapCanada;
import com.inthinc.pro.reports.hos.model.RecapCanada2007;
import com.inthinc.pro.reports.hos.model.RecapType;
import com.inthinc.pro.reports.hos.model.RecapUS;
import com.inthinc.pro.reports.hos.model.RemarkLog;
import com.inthinc.pro.reports.hos.model.VehicleInfo;
import com.inthinc.pro.reports.hos.model.ViolationsDetailRaw;
import com.inthinc.pro.reports.jasper.ReportUtils;
import com.inthinc.pro.reports.util.DateTimeUtil;
import com.inthinc.pro.reports.util.MessageUtil;

public class PayrollSignoffReportCriteria extends PayrollReportCriteria {

    
    public PayrollSignoffReportCriteria(Locale locale) 
    {
        super(ReportType.PAYROLL_SIGNOFF, locale);
    }

    public void init(Integer driverID, Interval interval)
    {
/*
 * TODO:
 *              
        List<HOSRecord> hosRecordList = hosDAO.getHosRecords(driverID, interval, hosStatuses);
        List<HOSVehicleDayData> hosVehicleDayData = hosDAO.getHosVehicleDataByDay(driverID, interval);
        List<HOSOccupantLog> hosOccupantLogList = hosDAO.getHosOccupantLogs(driverID, interval);
        Driver driver = driverDAO.findByID(driverID);
        Account account = accountDAO.findByID(driver.getPerson().getAcctID());
        
*/        
        Driver driver = null;
        Account account = null;
        Group group = null;
        Map<Driver, List<HOSRecord>> hosRecordMap = new HashMap<Driver, List<HOSRecord>>();
        
//        initDataSet(interval, hosRecordMap);
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
