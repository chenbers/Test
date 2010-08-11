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
import com.inthinc.pro.reports.jasper.ReportUtils;
import com.inthinc.pro.reports.util.MessageUtil;

public class HosDailyDriverLogReportCriteria {

    private static final Logger logger = Logger.getLogger(HosDailyDriverLogReportCriteria.class);

    private static final String BASE_LOG_GRAPH_IMAGE_PATH = "hos/hosLog.jpg";
    
    // each item in list is data for one day
    private List<ReportCriteria> criteriaList;
    private Locale locale;
    private Boolean defaultUseMetric;
    DateTimeFormatter dateTimeFormatter; 
    Integer hosStatuses[] = {
            HOSStatus.OFF_DUTY.getCode(),
            HOSStatus.SLEEPER.getCode(), 
            HOSStatus.DRIVING.getCode(),
            HOSStatus.ON_DUTY.getCode(),
            HOSStatus.OFF_DUTY_AT_WELL.getCode(), 
            HOSStatus.ON_DUTY_OCCUPANT.getCode(), 
            HOSStatus.OFF_DUTY_OCCUPANT.getCode(),
//            HOS_PRETRIP_INSPECTION(17, "Pre Trip Inspection", "HOS_PRETRIP_INSPECTION"),
//            HOS_PRETRIP_INSPECTION_NOT_NEEDED(18, "Pre Trip Inspection Not Needed", "HOS_PRETRIP_INSPECTION_NOT_NEEDED"),
//            HOS_POSTTRIP_INSPECTION(19, "Post Trip Inspection", "HOS_POSTTRIP_INSPECTION"),
//            HOS_POSTTRIP_INSPECTION_NOT_NEEDED(20, "Post Trip Inspection Not Needed", "HOS_POSTTRIP_INSPECTION_NOT_NEEDED"),
//            HOS_ADVERSE_DRIVING_CONDITIONS(21, "Adverse Driving Conditions", "HOS_ADVERSE_DRIVING_CONDITIONS"),
//            HOS_NEAREST_PLACE_OF_REST(22, "Nearest Place of Rest", "HOS_NEAREST_PLACE_OF_REST"),
//            HOS_EMERGENCY(23, "Emergency", "HOS_EMERGENCY"),
            HOSStatus.HOS_DERERRAL.getCode(),
//            DRIVING_NONDOT(25, "Driving Non-DOT", "DRIVING_NONDOT"),   
//            STOP_DRIVING_NONDOT(26, "Stop Driving Non-DOT", "STOP_DRIVING_NONDOT"),    
//            HOS_MIDTRIP_INSPECTION(28, "Mid Trip Inspection", "HOS_MIDTRIP_INSPECTION"),
//            TRAVELTIME_OCCUPANT(29, "Occupant Travel Time", "TRAVELTIME_OCCUPANT"),
//            HOS_PERSONALTIME(30, "Personal Time", "PERSONAL TIME");
      
    };
    public HosDailyDriverLogReportCriteria(Locale locale, Boolean defaultUseMetric) 
    {
        this.locale = locale;
        this.defaultUseMetric = defaultUseMetric;
        dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy").withLocale(locale);
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
        List<HOSRecord> hosRecordList = new ArrayList<HOSRecord>();
        List<HOSVehicleDayData> hosVehicleDayData = new ArrayList<HOSVehicleDayData>();
        List<HOSOccupantLog> hosOccupantLogList = new ArrayList<HOSOccupantLog>();
        
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
        HOSAdjustedList adjustedList = getAdjustedListFromLogList(hosRecordList);
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
            
            HosDailyDriverLog dayData= new HosDailyDriverLog();
            dayData.setDay(dateTimeFormatter.print(day));

            dayData.setRemarksList(getRemarksListForDay(day, hosRecordList));
            dayData.setCarrierName(account.getAcctName());
            dayData.setMainAddress(account.getAddress() == null ? "" : account.getAddress().getDisplayString());
            dayData.setTerminalAddress(group.getAddress() == null ? "" : group.getAddress().getDisplayString());
            dayData.setDriverName(driver.getPerson().getFullName());
            
            List<HOSRecAdjusted> logListForDay = adjustedList.getAdjustedListForDay(day.toDate(), currentTime, true); 
            dayData.setEdited(isListEdited(logListForDay));
            dayData.setCodrivers(getCodrivers(logListForDay, hosOccupantLogList));
            
            HOSRec firstHosRecForDay = getFirstRecordForDay(intervalDay.toDate(), hosRecapList);
            RuleSetType ruleSetType = getRuleSetTypeForDay(day, driver, firstHosRecForDay);
            dayData.setRuleSetType(ruleSetType);
            
            dayData.setVehicles(getVehicleInfoForDay(day, hosVehicleDayData));
            dayData.setMilesDriven(getMilesDrivenOnDay(dayData.getVehicles()));
            dayData.setShipping(getShippingInfoForDay(logListForDay));
            dayData.setTrailers(getTrailerInfoForDay(logListForDay));
            
            dayData.setCorrectedGraphList(logListForDay);
            DayTotals dayTotals = adjustedList.getAdjustedDayTotals(logListForDay);
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
        
        // TODO: refactor
        
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
    private String getCodrivers(List<HOSRecAdjusted> logListForDay, List<HOSOccupantLog> hosOccupantLogList) {
        StringBuffer codrivers = new StringBuffer();
        for (HOSRecAdjusted hosRecord: logListForDay) {
            if (hosRecord.getStatus().equals(HOSStatus.DRIVING)) {
                
                for (HOSOccupantLog hosOccupantLog :  hosOccupantLogList) {
                    if (timesOverlap(hosRecord, hosOccupantLog)) {
                        codrivers.append(codrivers.length() > 0 ? ", " : "");
                        codrivers.append(hosOccupantLog.getDriverName());
                    }
                }
            }
        }
        return codrivers.toString();
    }
    private boolean timesOverlap(HOSRecAdjusted hosRecord, HOSOccupantLog hosOccupantLog) {
        Interval driverInterval = new Interval(new DateTime(hosRecord.getLogTimeDate()), 
                                                new DateTime(hosRecord.getLogTimeDate()).plusMinutes((int)hosRecord.getTotalRealMinutes()));
        Interval occupantInterval = new Interval(new DateTime(hosOccupantLog.getTime()), new DateTime(hosOccupantLog.getEndTime()));
        return driverInterval.overlaps(occupantInterval);
    }
    private String getTrailerInfoForDay(List<HOSRecAdjusted> logListForDay) {
        StringBuffer trailerInfo = new StringBuffer();
        for (HOSRecAdjusted hosRecord: logListForDay) {
            if (hosRecord.getTrailerID() != null && !trailerInfo.toString().contains(hosRecord.getTrailerID())) {
                trailerInfo.append(trailerInfo.length() > 0 ? ", " : "");
                trailerInfo.append(hosRecord.getTrailerID());
            }
        }
        return trailerInfo.toString();
    }
    private String getShippingInfoForDay(List<HOSRecAdjusted> logListForDay) {
        StringBuffer shippingInfo = new StringBuffer();
        for (HOSRecAdjusted hosRecord: logListForDay) {
            if (hosRecord.getServiceID() != null && !shippingInfo.toString().contains(hosRecord.getServiceID())) {
                shippingInfo.append(shippingInfo.length() > 0 ? ", " : "");
                shippingInfo.append(hosRecord.getServiceID());
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
//System.out.println("day " + day.toDate().getTime());            
        for (HOSVehicleDayData hosVehicleDayData: hosVehicleDayDataList) {
//System.out.println("vehicle day " + hosVehicleDayData.getDay().getTime());            
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
        // TODO: LOCALIZED RULE SET TYPE?
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

}
