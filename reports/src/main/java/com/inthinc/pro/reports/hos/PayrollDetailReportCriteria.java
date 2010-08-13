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
            if (driver.getDot() == null || driver.getDot().equals(RuleSetType.NON_DOT))
                continue;
            DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
            DateTime queryStart = new DateTime(interval.getStart(), dateTimeZone).minusDays(1);
            DateTime queryEnd = new DateTime(interval.getEnd(), dateTimeZone).plusDays(1);
            driverHOSRecordMap.put(driver, hosDAO.getHOSRecords(driver.getDriverID(), new Interval(queryStart, queryEnd)));
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
