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

public class PayrollDetailReportCriteria extends ReportCriteria {

    private Locale locale;
    DateTimeFormatter dateTimeFormatter; 
    
    public PayrollDetailReportCriteria(Locale locale) 
    {
        super(ReportType.PAYROLL_DETAIL, "", locale);
        dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy").withLocale(locale);
    }

    public void init(Integer groupID, Interval interval)
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
    
    void initDataSet(Interval interval, Account account, Group topGroup,  List<Group> groupList, Map<Driver, List<HOSRecord>> driverHOSRecordMap)
    {
        GroupHierarchy groupHierarchy = new GroupHierarchy(topGroup, groupList);

        Date currentTime = new Date();
        
        List<PayrollData> dataList = new ArrayList<PayrollData>();
        
        for (Entry<Driver, List<HOSRecord>> entry : driverHOSRecordMap.entrySet()) {
            HOSAdjustedList adjustedList = getAdjustedListFromLogList(entry.getValue());
            Driver driver = entry.getKey();
            Group group = groupHierarchy.getGroup(driver.getGroupID());
            String groupName = groupHierarchy.getFullName(group);
            String groupAddress = group.getAddress() == null ? "" : group.getAddress().getDisplayString();
            
            String driverName = driver.getPerson().getFullNameLastFirst();
            String employeeID = driver.getPerson().getEmpid();
            
//            Interval interval = new Interval(new DateMidnight(currentDate.minusDays(14)), new DateMidnight(currentDate).plusDays(1));
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


        }
        
        Collections.sort(dataList);
        setMainDataset(dataList);
        
        addParameter("REPORT_START_DATE", dateTimeFormatter.print(interval.getStart()));
        addParameter("REPORT_END_DATE", dateTimeFormatter.print(interval.getEnd()));
        addParameter("CUSTOMER", account);
        addParameter("CUSTOMER_NAME", account.getAcctName());
        addParameter("CUSTOMER_ADDRESS", (account.getAddress() == null) ? "" : account.getAddress().getDisplayString());


    }

    
    private HOSAdjustedList getAdjustedListFromLogList(List<HOSRecord> hosRecList)
    {
        List<HOSRecAdjusted> adjustedList = new ArrayList<HOSRecAdjusted>();
        for (HOSRecord hosRec : hosRecList)
        {
            if (hosRec.getStatus() == null || !hosRec.getStatus().isGraphable() || hosRec.getDeleted())
                continue;
            HOSRecAdjusted hosDDLRec = new HOSRecAdjusted(hosRec.getHosLogID().toString(), 
                    hosRec.getStatus(), 
                    hosRec.getLogTime(), 
                    hosRec.getTimeZone());
            
            hosDDLRec.setEdited(hosRec.getEdited() || hosRec.getOrigin().equals(HOSOrigin.PORTAL));
            hosDDLRec.setServiceID(hosRec.getServiceID());
            hosDDLRec.setTrailerID(hosRec.getTrailerID());
            hosDDLRec.setRuleType(hosRec.getDriverDotType());

            adjustedList.add(hosDDLRec);

        }
        Collections.reverse(adjustedList);
        return new HOSAdjustedList(adjustedList);

    }
}
