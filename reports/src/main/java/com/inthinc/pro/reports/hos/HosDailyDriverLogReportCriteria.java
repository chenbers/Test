package com.inthinc.pro.reports.hos;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
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
import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.hos.HOSOccupantLog;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.model.hos.HOSVehicleDayData;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.model.HosDailyDriverLog;
import com.inthinc.pro.reports.hos.model.Recap;
import com.inthinc.pro.reports.hos.model.RecapCanada;
import com.inthinc.pro.reports.hos.model.RecapCanada2007;
import com.inthinc.pro.reports.hos.model.RecapType;
import com.inthinc.pro.reports.hos.model.RecapUS;
import com.inthinc.pro.reports.hos.model.RemarkLog;
import com.inthinc.pro.reports.hos.model.VehicleInfo;
import com.inthinc.pro.reports.hos.util.HOSUtil;
import com.inthinc.pro.reports.jasper.ReportUtils;
import com.inthinc.pro.reports.util.MessageUtil;

public class HosDailyDriverLogReportCriteria {

    private static final Logger logger = Logger.getLogger(HosDailyDriverLogReportCriteria.class);
    
    private AccountDAO accountDAO;
    private DriverDAO driverDAO;
    private GroupDAO groupDAO;
    private HOSDAO hosDAO;
    
    private static final String BASE_LOG_GRAPH_IMAGE_PATH = "hos/hosLog.jpg";
    
    // each item in list is data for one day
    private List<ReportCriteria> criteriaList;
    private Locale locale;
    private Boolean defaultUseMetric;
    
    private DateTimeFormatter dateTimeFormatter; 

    public HosDailyDriverLogReportCriteria(Locale locale, Boolean defaultUseMetric) 
    {
        this.locale = locale;
        this.defaultUseMetric = defaultUseMetric;
        dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy").withLocale(locale);
    }
    public void init(Integer driverID, Interval interval)
    {
        Driver driver = driverDAO.findByID(driverID);
        Account account = accountDAO.findByID(driver.getPerson().getAcctID());
        Group group = groupDAO.findByID(driver.getGroupID());
        List<HOSRecord> hosRecordList = hosDAO.getHOSRecords(driverID, interval);
        List<HOSVehicleDayData> hosVehicleDayData = hosDAO.getHOSVehicleDataByDay(driverID, interval);
        List<HOSOccupantLog> hosOccupantLogList = hosDAO.getHOSOccupantLogs(driverID, interval);
        
        initCriteriaList(interval, hosRecordList, hosVehicleDayData, hosOccupantLogList, driver, account, group);
    }
    
    public List<ReportCriteria> getCriteriaList()
    {
    
        return criteriaList;
    }

    private void setReportDate(Date date, ReportCriteria reportCriteria){
        SimpleDateFormat sdf = new SimpleDateFormat(MessageUtil.getMessageString("report.hos.dateTimeFormat", locale));
        reportCriteria.addParameter("REPORT_DATE_TIME", sdf.format(date));
    }

    void initCriteriaList(Interval interval, List<HOSRecord> hosRecordList, List<HOSVehicleDayData> hosVehicleDayData, List<HOSOccupantLog> hosOccupantLogList, Driver driver, Account account, Group group) 
    {
        HOSAdjustedList adjustedList = HOSUtil.getAdjustedListFromLogList(hosRecordList);
        List<HOSRec> hosRecapList = getRecapList(adjustedList, interval.getEnd().toDate());
        adjustForOccupantTravelTime(hosRecordList, adjustedList, interval.getEnd().toDate());
        Collections.reverse(hosRecordList);

        Date currentTime = new Date();
        
        criteriaList = new ArrayList<ReportCriteria>();

        for (DateTime intervalDay = interval.getStart(); intervalDay.isBefore(interval.getEnd()); intervalDay = intervalDay.plusDays(1)) 
        {
            
            LocalDate localDate = new LocalDate(intervalDay);
            DateTimeZone dateTimeZone = getBestTimeZone(intervalDay.toDate(), hosRecordList);
            DateTime day = localDate.toDateTimeAtStartOfDay(dateTimeZone);
            List<HOSRecAdjusted> logListForDay = adjustedList.getAdjustedListForDay(day.toDate(), currentTime, true); 
            List<HOSOccupantLog> occupantLogListForDay = getOccupantLogsForDay(logListForDay, hosOccupantLogList);
            HOSRec firstHosRecForDay = getFirstRecordForDay(intervalDay.toDate(), hosRecapList);
            RuleSetType ruleSetType = getRuleSetTypeForDay(day, driver, firstHosRecForDay);
            DayTotals dayTotals = adjustedList.getAdjustedDayTotals(logListForDay);
            
            HosDailyDriverLog dayData= new HosDailyDriverLog();
            dayData.setDay(dateTimeFormatter.print(day));
            dayData.setRemarksList(getRemarksListForDay(day, hosRecordList));
            dayData.setCarrierName(account.getAcctName());
            dayData.setMainAddress(account.getAddress() == null ? "" : account.getAddress().getDisplayString());
            dayData.setTerminalAddress(group.getAddress() == null ? "" : group.getAddress().getDisplayString());
            dayData.setDriverName(driver.getPerson().getFullName());
            dayData.setEdited(isListEdited(logListForDay));
            dayData.setCodrivers(getCodrivers(logListForDay, occupantLogListForDay));
            dayData.setShipping(getShippingInfoForDay(logListForDay, occupantLogListForDay));
            dayData.setTrailers(getTrailerInfoForDay(logListForDay, occupantLogListForDay));
            dayData.setRuleSetType(ruleSetType);
            dayData.setVehicles(getVehicleInfoForDay(day, hosVehicleDayData));
            dayData.setMilesDriven(getMilesDrivenOnDay(dayData.getVehicles()));
            dayData.setCorrectedGraphList(logListForDay);
            dayData.setCorrectedGraph(createGraph(logListForDay, dayTotals));
            if (dayData.getEdited()) {
                List<HOSRecAdjusted> originalLogListForDay = adjustedList.getAdjustedListForDay(day.toDate(), currentTime, false); 
                dayData.setOriginalGraphList(originalLogListForDay);
                dayData.setOriginalGraph(createGraph(originalLogListForDay, adjustedList.getAdjustedDayTotals(logListForDay)));
            }
            dayData.setRecap(initRecap(ruleSetType, day, hosRecapList, dayTotals));
            dayData.setRecapType(getRecapType(dayData.getRecap()));

            List<HosDailyDriverLog> dataList = new ArrayList<HosDailyDriverLog>();
            dataList.add(dayData);
            
            ReportCriteria criteria = new ReportCriteria(ReportType.HOS_DAILY_DRIVER_LOG_REPORT, "", locale);
            criteria.setMainDataset(dataList);
            setReportDate(currentTime, criteria);
            criteria.setUseMetric(ruleSetType== null || ruleSetType == RuleSetType.NON_DOT ? defaultUseMetric : ruleSetType.isMetricUnits());
            criteriaList.add(criteria);
        }
    }
    private void adjustForOccupantTravelTime(List<HOSRecord> hosRecordList, HOSAdjustedList adjustedList, Date endDate) {
        
        RuleSetType occupantTravelTimeRuleType = null;
        for (HOSRecord logRec : hosRecordList) {
            if (logRec.getStatus() != null && logRec.getStatus().equals(HOSStatus.TRAVELTIME_OCCUPANT))
                occupantTravelTimeRuleType = logRec.getDriverDotType();
        }
        
        if (occupantTravelTimeRuleType != null) {
            
            List<HOSRec> ruleList = new ArrayList<HOSRec>();
            adjustedList.initAdjustedTimeAndMinutes(true, endDate);
            for (HOSRecAdjusted rec : adjustedList.getHosList()) {
                ruleList.add(0, new HOSRec(rec.getId(), rec.getStatus(), rec.getTotalRealMinutes(), rec.getLogTimeDate()));
            }

            HOSRules rule = RuleSetFactory.getRulesForRuleSetType(occupantTravelTimeRuleType);
            rule.convertOccupantTravelTime(ruleList);
            
            //We need to copy new status back to resultList record
            for (HOSRecAdjusted adjustedRec : adjustedList.getHosList()) {
                if (adjustedRec.getStatus().equals(HOSStatus.TRAVELTIME_OCCUPANT))
                {
                    for (HOSRec rec : ruleList)
                    {
                        if (rec.getId().equals(adjustedRec.getId()))
                        {
                            adjustedRec.setGraphStatus(rec.getStatus());
                            break;
                        }
                    }
                }
            }
        }
    }
    
    private DateTimeZone getBestTimeZone(Date date, List<HOSRecord> hosRecordList) {
        
        HOSRecord priorRec = null; 
        for (HOSRecord logRec : hosRecordList)
        {
            if (logRec.getDeleted())
                continue;
            if (logRec.getLogTime().after(date))
            {
                break;
            }
            priorRec = logRec;
        }
        if (priorRec == null)
            return DateTimeZone.getDefault();
        
        return DateTimeZone.forTimeZone(priorRec.getTimeZone());
        
        
    }
    private List<Recap> initRecap(RuleSetType ruleSetType, DateTime day, List<HOSRec> hosRecList, DayTotals dayTotals) {
        List<Recap> recapList = new ArrayList<Recap>();
        if (ruleSetType == null ||
                ruleSetType == RuleSetType.NON_DOT ||
                ruleSetType == RuleSetType.CANADA_2007_60_DEGREES_OIL ||
                ruleSetType == RuleSetType.CANADA_2007_OIL ||
                ruleSetType == RuleSetType.CANADA_ALBERTA)
                return null;
            
        Recap recap = null;
        if (ruleSetType == RuleSetType.CANADA ||
                ruleSetType == RuleSetType.CANADA_60_DEGREES ||
                ruleSetType == RuleSetType.CANADA_HOME_OFFICE) {
            recap = new RecapCanada(ruleSetType, day, hosRecList, (dayTotals.getDriving()+dayTotals.getOnDuty()) * 15);
        }
        else if (ruleSetType == RuleSetType.CANADA_2007_CYCLE_1 ||
                    ruleSetType == RuleSetType.CANADA_2007_CYCLE_2 ||
                    ruleSetType == RuleSetType.CANADA_2007_60_DEGREES_CYCLE_1 ||
                    ruleSetType == RuleSetType.CANADA_2007_60_DEGREES_CYCLE_2) {
            recap = new RecapCanada2007(ruleSetType, day, hosRecList, (dayTotals.getDriving()+dayTotals.getOnDuty()) * 15);
        }
        else {
            recap = new RecapUS(ruleSetType, day, hosRecList, (dayTotals.getDriving()+dayTotals.getOnDuty()) * 15);
        }
    
        recapList.add(recap);
        return recapList;
    }
    
    private RecapType getRecapType(List<Recap> recapList) {
        if (recapList == null || recapList.size() == 0)
            return RecapType.NONE;
        
        return recapList.get(0).getRecapType();
    }
    

    private List<RemarkLog> getRemarksListForDay(DateTime day, List<HOSRecord> hosRecordList) {
        List<RemarkLog> remarkLogList = new ArrayList<RemarkLog>();
        
        DateTime dayEnd = day.plusDays(1);
        HOSRecord priorRecord = null;
        for (int idx = 0; idx < hosRecordList.size(); idx++) {
            HOSRecord hosRecord = hosRecordList.get(idx);
            DateTime hosRecordTime = new DateTime(hosRecord.getLogTime());
            
            if (hosRecordTime.isAfter(dayEnd)) {
                break;
            }
            DateTime hosRecordEndTime = hosRecordTime;
            if (idx+1 < hosRecordList.size())
                hosRecordEndTime = new DateTime(hosRecordList.get(idx+1).getLogTime());
            
            if (hosRecordEndTime.isBefore(day) ) {
                if (!hosRecord.getEdited() && 
                    !hosRecord.getDeleted() && 
                    !hosRecord.getOrigin().equals(HOSOrigin.PORTAL)) 
                    priorRecord = hosRecord;
            }
            else { // current day
                if (remarkLogList.isEmpty() && priorRecord != null) {
                    if (hosRecord.getEdited() || hosRecord.getOrigin().equals(HOSOrigin.PORTAL) )
                        remarkLogList.add(populateRemarkLog(priorRecord));
                }
                remarkLogList.add(populateRemarkLog(hosRecord));
            }
        }
        RemarkLog deferralDay2Record = getDeferralDay2Record(day.minusDays(1), hosRecordList);
        if (remarkLogList.isEmpty() && priorRecord != null) {
            remarkLogList.add(populateRemarkLog(priorRecord));
            if (deferralDay2Record != null) 
                remarkLogList.add(deferralDay2Record);
        }
        else {
            if (deferralDay2Record != null) 
                remarkLogList.add(0, deferralDay2Record);
        }

        return remarkLogList;
    }
    private RemarkLog getDeferralDay2Record(DateTime yesterday, List<HOSRecord> hosRecordList) {
        
        DateTime dayEnd = yesterday.plusDays(1);
        for (HOSRecord hosRecord : hosRecordList) {
            DateTime hosRecordTime = new DateTime(hosRecord.getLogTime());
            
            if (hosRecordTime.isAfter(dayEnd)) {
                return null;
            }
            
            if (hosRecordTime.isBefore(yesterday)) {
                continue;
            }
            
            if (hosRecord.getStatus() == HOSStatus.HOS_DERERRAL) {
                RemarkLog remarkLog = new RemarkLog();
                remarkLog.setEdited(true);
                remarkLog.setDeleted(false);
                remarkLog.setLogTimeDate(dayEnd.toDate());
                remarkLog.setLogTimeZone(hosRecord.getTimeZone());
                remarkLog.setLocation(MessageUtil.getMessageString("report.ddl.webLogin", locale));
                remarkLog.setStatusDescription(MessageUtil.getMessageString("report.ddl.deferralDay2", locale));
                return remarkLog;
                
            }
        }
        return null;
    }
    private RemarkLog populateRemarkLog(HOSRecord hosRecord) {
        RemarkLog remarkLog = new RemarkLog();
        remarkLog.setEdited(hosRecord.getEdited() || hosRecord.getOrigin().equals(HOSOrigin.PORTAL));
        remarkLog.setDeleted(hosRecord.getDeleted());
        remarkLog.setLogTimeDate(hosRecord.getLogTime());
        remarkLog.setLogTimeZone(hosRecord.getTimeZone());
        remarkLog.setLocation(hosRecord.getLocation());
        remarkLog.setOriginalLocation(hosRecord.getOriginalLocation());
        remarkLog.setStartOdometer(hosRecord.getVehicleOdometer()); 
        remarkLog.setStatusDescription((hosRecord.getNotificationData() != null) ? (" " + hosRecord.getNotificationData()) : "");
        return remarkLog;
    }

    private List<HOSOccupantLog> getOccupantLogsForDay(List<HOSRecAdjusted> logListForDay, List<HOSOccupantLog> hosOccupantLogList) {
        
        List<HOSOccupantLog> dayOccupantLogList = new ArrayList<HOSOccupantLog>(); 
        for (HOSRecAdjusted hosRecord: logListForDay) {
            if (hosRecord.getStatus().equals(HOSStatus.DRIVING) ||
                    hosRecord.getStatus().equals(HOSStatus.ON_DUTY) ||
                    hosRecord.getStatus().equals(HOSStatus.ON_DUTY_OCCUPANT) ||
                    hosRecord.getStatus().equals(HOSStatus.SLEEPER)) {
                for (HOSOccupantLog hosOccupantLog :  hosOccupantLogList) {
                    if (hosOccupantLog.getVehicleID().equals(hosRecord.getVehicleID()) && timesOverlap(hosRecord, hosOccupantLog)) 
                        dayOccupantLogList.add(hosOccupantLog);                
                }
            }
        }
        return dayOccupantLogList;
    }
    private boolean timesOverlap(HOSRecAdjusted hosRecord, HOSOccupantLog hosOccupantLog) {
        Interval driverInterval = new Interval(new DateTime(hosRecord.getLogTimeDate()), 
                                                new DateTime(hosRecord.getLogTimeDate()).plusMinutes((int)hosRecord.getTotalRealMinutes()));
        Interval occupantInterval = new Interval(new DateTime(hosOccupantLog.getLogTime()), new DateTime(hosOccupantLog.getEndTime()));
        return driverInterval.overlaps(occupantInterval);
    }

    private String getCodrivers(List<HOSRecAdjusted> logListForDay, List<HOSOccupantLog> occupantLogListForDay) {
        StringBuffer codrivers = new StringBuffer();
        for (HOSOccupantLog hosOccupantLog :  occupantLogListForDay) {
            if (!codrivers.toString().contains(hosOccupantLog.getDriverName())) {
                codrivers.append(codrivers.length() > 0 ? ", " : "");
                codrivers.append(hosOccupantLog.getDriverName());
            }
         }
        return codrivers.toString();
    }
    private String getTrailerInfoForDay(List<HOSRecAdjusted> logListForDay, List<HOSOccupantLog> occupantLogListForDay) {
        StringBuffer trailerInfo = new StringBuffer();
        for (HOSRecAdjusted hosRecord: logListForDay) {
            if (hosRecord.getTrailerID() != null && !trailerInfo.toString().contains(hosRecord.getTrailerID())) {
                trailerInfo.append(trailerInfo.length() > 0 ? ", " : "");
                trailerInfo.append(hosRecord.getTrailerID());
            }
        }
        for (HOSOccupantLog hosOccupantLog :  occupantLogListForDay) {
            if (hosOccupantLog.getTrailerID() == null)
                continue;
            if (!trailerInfo.toString().contains(hosOccupantLog.getTrailerID())) {
                trailerInfo.append(trailerInfo.length() > 0 ? ", " : "");
                trailerInfo.append(hosOccupantLog.getTrailerID());
            }
         }
        return trailerInfo.toString();
    }
    private String getShippingInfoForDay(List<HOSRecAdjusted> logListForDay, List<HOSOccupantLog> occupantLogListForDay) {
        StringBuffer shippingInfo = new StringBuffer();
        for (HOSRecAdjusted hosRecord: logListForDay) {
            if (hosRecord.getServiceID() != null && !shippingInfo.toString().contains(hosRecord.getServiceID())) {
                shippingInfo.append(shippingInfo.length() > 0 ? ", " : "");
                shippingInfo.append(hosRecord.getServiceID());
            }
        }
        for (HOSOccupantLog hosOccupantLog :  occupantLogListForDay) {
            if (hosOccupantLog.getServiceID() == null)
                continue;
            if (!shippingInfo.toString().contains(hosOccupantLog.getServiceID())) {
                shippingInfo.append(shippingInfo.length() > 0 ? ", " : "");
                shippingInfo.append(hosOccupantLog.getServiceID());
            }
         }
        return shippingInfo.toString();
    }

    private Number getMilesDrivenOnDay(List<VehicleInfo> vehicles) {
        long total = 0l;
        for (VehicleInfo vehicleInfo : vehicles) {
            total += vehicleInfo.getMilesDriven().longValue();
        }
        return total;
    }
    private List<VehicleInfo> getVehicleInfoForDay(DateTime day, List<HOSVehicleDayData> hosVehicleDayDataList) {
        List<VehicleInfo> vehicleInfoList = new ArrayList<VehicleInfo>();
        for (HOSVehicleDayData hosVehicleDayData: hosVehicleDayDataList) {
            if (day.toDate().getTime() == hosVehicleDayData.getDay().getTime()) {
                VehicleInfo vehicleInfo = new VehicleInfo();
                vehicleInfo.setStartOdometer(hosVehicleDayData.getStartOdometer());
                vehicleInfo.setName(hosVehicleDayData.getVehicleName());
                vehicleInfo.setMilesDriven(hosVehicleDayData.getMilesDriven());
                vehicleInfoList.add(vehicleInfo);
            }
        }
        return vehicleInfoList;
    }
    private boolean isListEdited(List<HOSRecAdjusted> correctedListForDay) {
        for (HOSRecAdjusted rec : correctedListForDay)
            if (rec.isEdited())
                return true;
        return false;
    }
    private RuleSetType getRuleSetTypeForDay(DateTime day, Driver driver, HOSRecBase hosRec)
    {
        RuleSetType ruleSetType = (driver != null && driver.getDot() != null ? driver.getDot() : null);
        if (hosRec != null) {
            ruleSetType =  hosRec.getRuleType();
        }
        return ruleSetType;
    }
    
    private HOSRec getFirstRecordForDay(Date day, List<HOSRec> hosList)
    {
        Date dayStart = DateUtil.getStartOfDay(day);
        Date dayEnd =  DateUtil.getEndOfDay(day);
        
        for (HOSRec logRec : hosList)
        {
            if (logRec.getEndTimeDate().compareTo(dayStart) >= 0 && 
                logRec.getLogTimeDate().compareTo(dayEnd) < 0)
            {
                return logRec;
            }
        }
        return null;
    }

    public Image createGraph(List<HOSRecAdjusted> graphList, DayTotals dayTotals) 
    {
        
        BufferedImage img = null;
        try {
            img = ImageIO.read(ReportUtils.loadFile(BASE_LOG_GRAPH_IMAGE_PATH));
        } catch (IOException e) {
            logger.error(e);
        }
        
        
        HosDriverDailyLogGraph hosDriverDailyLogGraph = new HosDriverDailyLogGraph();
        
        return hosDriverDailyLogGraph.drawHosLogGraph(img, graphList, dayTotals);
    }
    
    

    private List<HOSRec> getRecapList(HOSAdjustedList adjustedList, Date endDate) {
        List<HOSRec> hosRecapList = new ArrayList<HOSRec>();
        
        adjustedList.initAdjustedTimeAndMinutes(true, endDate);
        List<HOSRecAdjusted> adjustedRecapList = new ArrayList<HOSRecAdjusted>(adjustedList.getHosList());
        Collections.reverse(adjustedRecapList);
        
        for (HOSRecAdjusted rec : adjustedRecapList) {
            hosRecapList.add(new HOSRec(rec.getId(), rec.getStatus(), rec.getTotalAdjustedMinutes(), rec.getAdjustedTime(), rec.getLogTimeZone(), rec.getRuleType(), null, true, false));
        }

        return hosRecapList;
    }

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }
    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
    public DriverDAO getDriverDAO() {
        return driverDAO;
    }
    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }
    public GroupDAO getGroupDAO() {
        return groupDAO;
    }
    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

}
