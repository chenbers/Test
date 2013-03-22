package com.inthinc.pro.reports.hos;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.hos.adjusted.HOSAdjustedList;
import com.inthinc.hos.ddl.DDLUtil;
import com.inthinc.hos.ddl.HOSOccupantLog;
import com.inthinc.hos.ddl.HosDailyDriverLog;
import com.inthinc.hos.ddl.HosDriverDailyLogGraph;
import com.inthinc.hos.ddl.RemarkLog;
import com.inthinc.hos.ddl.VehicleInfo;
import com.inthinc.hos.model.DayTotals;
import com.inthinc.hos.model.HOSOrigin;
import com.inthinc.hos.model.HOSRec;
import com.inthinc.hos.model.HOSRecAdjusted;
import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.AddressDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.util.HOSUtil;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.DOTOfficeType;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.model.hos.HOSVehicleDayData;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.jasper.ReportUtils;
import com.inthinc.pro.reports.util.DateTimeUtil;
import com.inthinc.pro.reports.util.MessageUtil;

public class HosDailyDriverLogReportCriteria extends ReportCriteria {

    private static final Logger logger = Logger.getLogger(HosDailyDriverLogReportCriteria.class);
    
    private AccountDAO accountDAO;
    private AddressDAO addressDAO;
    private DriverDAO driverDAO;
    private GroupDAO groupDAO;
    private HOSDAO hosDAO;
    private UserDAO userDAO;
    private VehicleDAO vehicleDAO;
    
    private static final String BASE_LOG_GRAPH_IMAGE_PATH = "hos/hosLog";
    private static final String DST_START_IMAGE= "_dst_start";
    private static final String DST_END_IMAGE= "_dst_end";
    
    // each item in list is data for one day
    private List<ReportCriteria> criteriaList;
    private Locale locale;
    private Boolean defaultUseMetric;
    private String mainOfficeDisplayAddress="";
    private String carrierName="";
    
    private DateTimeFormatter dateTimeFormatter;
    
    private static int MAX_RULESET_DAYSBACK = 24;

    private Map<Integer, Vehicle> vehicleMap = new HashMap<Integer, Vehicle>();

    private ResourceBundle resourceBundle;
    private DDLUtil ddlUtil;
    
    public HosDailyDriverLogReportCriteria(Locale locale, Boolean defaultUseMetric) {
        this.locale = locale;
        this.defaultUseMetric = defaultUseMetric;
        dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy").withLocale(locale);
        setResourceBundle(ReportType.HOS_DAILY_DRIVER_LOG_REPORT.getResourceBundle(locale));
        this.setIncludeZeroMilesDrivers(ReportCriteria.DEFAULT_INCLUDE_ZERO_MILES_DRIVERS);
        this.setIncludeInactiveDrivers(ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS);
        ddlUtil = new DDLUtil(); 
    }

    public ResourceBundle getResourceBundle() {
        if (resourceBundle == null)
            resourceBundle = ReportType.HOS_DAILY_DRIVER_LOG_REPORT.getResourceBundle(locale);
        return resourceBundle;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public void init(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval) {
        List<Group> reportGroupList = getReportGroupList(groupIDList, accountGroupHierarchy);
        List<Driver> reportDriverList = getReportDriverList(reportGroupList);

        Account account = null;
        List<ReportCriteria> groupCriteriaList = new ArrayList<ReportCriteria>();

        Interval expandedInterval = DateTimeUtil.getExpandedInterval(interval, DateTimeZone.UTC, MAX_RULESET_DAYSBACK, 1); 
        
        for (Driver driver : reportDriverList) {
            if (account == null) {
                account = fetchAccount(driver.getPerson().getAcctID());
            }
            
            initMainOfficeInfo(accountGroupHierarchy, account, driver.getGroupID());
            Address terminalAddress = getTerminalAddress(accountGroupHierarchy, driver);
            Integer driverID = driver.getDriverID();
            initDriverCriteria(accountGroupHierarchy, driverID, interval, expandedInterval, driver, account, terminalAddress);
            groupCriteriaList.addAll(criteriaList);
        }
        
        criteriaList = groupCriteriaList;
    }


    public void init(GroupHierarchy accountGroupHierarchy, Integer driverID, Interval interval) {
        Driver driver = driverDAO.findByID(driverID);
        
        Account account = fetchAccount(driver.getPerson().getAcctID());
        initMainOfficeInfo(accountGroupHierarchy, account, driver.getGroupID());
        Address terminalAddress = getTerminalAddress(accountGroupHierarchy, driver);
        Interval expandedInterval = DateTimeUtil.getExpandedInterval(interval, DateTimeZone.UTC, MAX_RULESET_DAYSBACK, 1);
        
        initDriverCriteria(accountGroupHierarchy, driverID, interval, expandedInterval, driver, account, terminalAddress);
    }
    
    private void initMainOfficeInfo(GroupHierarchy accountGroupHierarchy, Account account, Integer groupID) {
        Address mainOfficeAddress = null;
        Group mainOfficeGroup = accountGroupHierarchy.getAddressGroup(DOTOfficeType.MAIN, groupID);
        if (mainOfficeGroup != null) {
            if (mainOfficeGroup.getAddress() == null && mainOfficeGroup.getAddressID() != null)
                mainOfficeGroup.setAddress(fetchAddress(mainOfficeGroup.getAddressID()));
            mainOfficeAddress = mainOfficeGroup.getAddress();
            carrierName = mainOfficeGroup.getName();
        }
        if (mainOfficeAddress == null) {
            mainOfficeAddress = account.getAddress();
            carrierName = account.getAcctName();
        }
        
        mainOfficeDisplayAddress = (mainOfficeAddress == null) ? "" : mainOfficeAddress.getDisplayString();
    }

    private Account fetchAccount(Integer accountID) {
        Account account = accountDAO.findByID(accountID);
        if (account.getAddress() == null && account.getAddressID() != null && account.getAddressID().intValue() != 0) {
            account.setAddress(fetchAddress(account.getAddressID()));
        }
        return account;
        
    }
    
    private Address fetchAddress(Integer addressID) {
        return addressDAO.findByID(addressID);
    }

    private Address getTerminalAddress(GroupHierarchy accountGroupHierarchy, Driver driver) {
        Group terminalOfficeGroup = accountGroupHierarchy.getAddressGroup(DOTOfficeType.TERMINAL, driver.getGroupID());
        if (terminalOfficeGroup != null) {
            if (terminalOfficeGroup.getAddress() == null && terminalOfficeGroup.getAddressID() != null)
                terminalOfficeGroup.setAddress(fetchAddress(terminalOfficeGroup.getAddressID()));
            return terminalOfficeGroup.getAddress();
        }
        return null;
    }
    private void initDriverCriteria(GroupHierarchy accountGroupHierarchy, Integer driverID, 
    		Interval interval, Interval expandedInterval, 
    		Driver driver, Account account,
    		Address terminalAddress) {
        List<HOSRecord> hosRecordList = hosDAO.getHOSRecords(driverID, expandedInterval, false);
        List<HOSOccupantLog> hosOccupantLogList = hosDAO.getHOSOccupantLogs(driverID, expandedInterval);
        initCriteriaList(interval, hosRecordList, null, hosOccupantLogList, driver, account, terminalAddress);
    }

    protected List<Driver> getReportDriverList(List<Group> reportGroupList){
        return getReportDriverList(reportGroupList, getIncludeInactiveDrivers());
    }
    
    protected List<Driver> getReportDriverList(List<Group> reportGroupList, boolean includeInactiveDrivers) {
        List<Driver> driverList = new ArrayList<Driver>();
        for (Group group : reportGroupList){
//            driverList.addAll(driverDAO.getDrivers(group.getGroupID()));
        	if (group.getGroupID() != null) {
                List<Driver> groupDriverList = driverDAO.getDrivers(group.getGroupID());
                if (groupDriverList != null && !groupDriverList.isEmpty()){
                    //driverList.addAll(groupDriverList);
                    for(Driver driver: groupDriverList){
                        if(Status.ACTIVE.equals(driver.getStatus()) || (includeInactiveDrivers)){
                            driverList.add(driver);
                        }
                    }
                }
            }
        }
        return driverList;
    }

    protected List<Group> getReportGroupList(List<Integer> groupIDList, GroupHierarchy groupHierarchy) {
        List<Group> reportGroupList = new ArrayList<Group>();
        for (Integer groupID : groupIDList) {
            addGroupAndChildren(groupHierarchy, reportGroupList, groupID);
        }
        return reportGroupList;
    }

    private void addGroupAndChildren(GroupHierarchy groupHierarchy, List<Group> reportGroupList, Integer groupID) {
        Group group = groupHierarchy.getGroup(groupID);
        if (group != null && !reportGroupList.contains(group))
            reportGroupList.add(group);
        List<Group> childGroupList = groupHierarchy.getChildren(group);
        if (childGroupList != null) {
            for (Group childGroup : childGroupList) {
                addGroupAndChildren(groupHierarchy, reportGroupList, childGroup.getGroupID());
            }
        }
    }

    public List<ReportCriteria> getCriteriaList(){
        return (criteriaList != null)? criteriaList:new ArrayList<ReportCriteria>();
    }

    private void setReportDate(Date date, ReportCriteria reportCriteria){
        SimpleDateFormat sdf = new SimpleDateFormat(MessageUtil.getMessageString("report.hos.dateTimeFormat", locale));
        reportCriteria.addParameter("REPORT_DATE_TIME", sdf.format(date));
    }

    void initCriteriaList(Interval interval, List<HOSRecord> hosRecordList, 
    		List<HOSVehicleDayData> hosVehicleDayData, 
    		List<HOSOccupantLog> hosOccupantLogList, 
    		Driver driver, Account account, 
    		Address terminalAddress) 
    {

        
        boolean initVehicleDayData = (hosVehicleDayData == null);
        
        Collections.sort(hosRecordList);

        Date endDate = interval.getEnd().toDate();
        HOSAdjustedList adjustedList = HOSUtil.getAdjustedListFromLogList(hosRecordList);
        HOSAdjustedList originalAdjustedList = HOSUtil.getOriginalAdjustedListFromLogList(hosRecordList);
        RuleSetType occupantTravelTimeRuleType = null;
        for (HOSRecord logRec : hosRecordList) {
            if (logRec.getStatus() != null && logRec.getStatus().equals(HOSStatus.TRAVELTIME_OCCUPANT))
                occupantTravelTimeRuleType = logRec.getDriverDotType();
        }
        
        if (occupantTravelTimeRuleType != null) {
            ddlUtil.adjustForOccupantTravelTime(occupantTravelTimeRuleType, adjustedList, endDate);
            ddlUtil.adjustForOccupantTravelTime(occupantTravelTimeRuleType, originalAdjustedList, endDate);
        }
        List<HOSRec> hosRecapList = ddlUtil.getRecapList(adjustedList, endDate);
        Collections.reverse(hosRecordList);

        Date currentTime = new Date();
        
        RuleSetType driverRuleSetType = (driver != null && driver.getDot() != null ? driver.getDot() : null);

        criteriaList = new ArrayList<ReportCriteria>();

        for (DateTime intervalDay = interval.getStart(); intervalDay.isBefore(interval.getEnd()); intervalDay = intervalDay.plusDays(1)) 
        {
            
            LocalDate localDate = new LocalDate(intervalDay);
            DateTime driverDay = localDate.toDateTimeAtStartOfDay(DateTimeZone.forTimeZone(driver.getPerson().getTimeZone()));
            DateTimeZone dateTimeZone = ddlUtil.getBestTimeZone(driverDay.toDate(), adjustedList.getHosList(), driver.getPerson().getTimeZone());

            DateTime day = localDate.toDateTimeAtStartOfDay(dateTimeZone);
            if (day.toDate().after(currentTime)) 
                break;

            List<HOSRecAdjusted> logListForDay = adjustedList.getAdjustedListForDay(day.toDate(), currentTime, true, dateTimeZone.toTimeZone());
            boolean isDSTStart = adjustedList.isDayDSTStart(day.toDate(), dateTimeZone.toTimeZone());
            boolean isDSTEnd = adjustedList.isDayDSTEnd(day.toDate(), dateTimeZone.toTimeZone());
            List<HOSOccupantLog> occupantLogListForDay = ddlUtil.getOccupantLogsForDay(logListForDay, hosOccupantLogList);
            HOSRec firstHosRecForDay = ddlUtil.getFirstRecordForDay(day.toDate(), hosRecapList, dateTimeZone);
            
            RuleSetType ruleSetType = ddlUtil.getRuleSetTypeForDay(day, driverRuleSetType, firstHosRecForDay);
            
            HosDailyDriverLog dayData= new HosDailyDriverLog();
            dayData.setCorrectedDayTotals(adjustedList.getAdjustedDayTotals(logListForDay));
            dayData.setDay(dateTimeFormatter.print(day));
            dayData.setRemarksList(getRemarksListForDay(day, hosRecordList));
            dayData.setCarrierName(carrierName);
            dayData.setMainAddress(mainOfficeDisplayAddress);
            dayData.setTerminalAddress(terminalAddress == null ? "" : terminalAddress.getDisplayString());
            dayData.setDriverName(driver.getPerson().getFullName());
            dayData.setEdited(ddlUtil.isListEdited(logListForDay));
            dayData.setCodrivers(ddlUtil.getCodrivers(logListForDay, occupantLogListForDay));
            dayData.setShipping(ddlUtil.getShippingInfoForDay(logListForDay, occupantLogListForDay));
            dayData.setTrailers(ddlUtil.getTrailerInfoForDay(logListForDay, occupantLogListForDay));
            dayData.setRuleSetType(ruleSetType);
            if (initVehicleDayData)
                dayData.setVehicles(initVehicleInfoForDay(day, driver.getDriverID(), logListForDay, hosRecordList));
            else dayData.setVehicles(getVehicleInfoForDay(day, hosVehicleDayData));
            dayData.setMilesDriven(getMilesDrivenOnDay(dayData.getVehicles()));
            dayData.setCorrectedGraphList(logListForDay);
            dayData.setCorrectedGraph(createGraph(logListForDay, dayData.getCorrectedDayTotals(), isDSTStart, isDSTEnd));
            if (dayData.getEdited()) {
                List<HOSRecAdjusted> originalLogListForDay = originalAdjustedList.getAdjustedListForDay(day.toDate(), currentTime, true, dateTimeZone.toTimeZone());
                dayData.setOriginalGraphList(originalLogListForDay);
                dayData.setOriginalDayTotals(originalAdjustedList.getAdjustedDayTotals(originalLogListForDay));
                dayData.setOriginalGraph(createGraph(originalLogListForDay, dayData.getOriginalDayTotals(), isDSTStart, isDSTEnd));
 
            }
            dayData.setRecap(ddlUtil.initRecap(ruleSetType, day, hosRecapList, dayData.getCorrectedDayTotals(), dateTimeZone));
            dayData.setRecapType(ddlUtil.getRecapType(dayData.getRecap()));

            List<HosDailyDriverLog> dataList = new ArrayList<HosDailyDriverLog>();
            dataList.add(dayData);
            
            ReportCriteria criteria = new ReportCriteria(ReportType.HOS_DAILY_DRIVER_LOG_REPORT, "", locale);
            criteria.setMainDataset(dataList);
            setReportDate(currentTime, criteria);
            criteria.setUseMetric(ruleSetType== null || ruleSetType == RuleSetType.NON_DOT ? defaultUseMetric : ruleSetType.isMetricUnits());
            criteriaList.add(criteria);
        }
    }
    
    List<VehicleInfo> initVehicleInfoForDay(DateTime day, Integer driverID, List<HOSRecAdjusted> logListForDay, List<HOSRecord> hosRecordList) {
        List<VehicleInfo> vehicleInfoList = new ArrayList<VehicleInfo>();
        for (HOSRecAdjusted rec  : logListForDay) {
            if (ddlUtil.isNoVehicleStatus(rec.getStatus())) 
                continue;
            if (isIDSet(rec.getVehicleID())) {
                boolean alreadyAdded = false;
                for (VehicleInfo vehicleInfo : vehicleInfoList) {
                    if (rec.getVehicleID().equals(vehicleInfo.getVehicleID())) {
                        alreadyAdded = true;
                        break;
                    }
                }
                if (!alreadyAdded) {
                    VehicleInfo vehicleInfo = new VehicleInfo();
                    Integer vehicleID = (Integer)(rec.getVehicleID());
                    vehicleInfo.setStartOdometer(getVehicleStartOdometer(day, rec, hosRecordList));
                    vehicleInfo.setName(getVehicleNameStr(vehicleID));
                    vehicleInfo.setVehicleID(rec.getVehicleID());
                    Map<Integer, Long> mileageMap = hosDAO.fetchMileageForDayVehicle(day, vehicleID);
                    vehicleInfo.setDriverMiles(getDriverMiles(mileageMap, logListForDay, driverID, vehicleID));
                    vehicleInfo.setVehicleMiles(getVehicleMiles(mileageMap));
                    vehicleInfoList.add(vehicleInfo);
                }
            }
        }
        return vehicleInfoList;
    }
    private Boolean isIDSet(Object id) {
        if (id == null)
            return false;
        
        String idString = id.toString().trim();
        if (idString.isEmpty() || idString.equals("0"))
            return false;
        
        
        return true;
    }
    private Number getDriverMiles(Map<Integer, Long> mileageMap, List<HOSRecAdjusted> logListForDay, Integer driverID, Integer vehicleID) {
        for (Entry<Integer, Long> entry : mileageMap.entrySet()) { 
            Integer vehicleDriverID = entry.getKey();
            if (vehicleDriverID.equals(driverID)) {
                    return entry.getValue();
            }
        }
        return 0l;
    }
    
    private Number getVehicleMiles(Map<Integer, Long> mileageMap) {
        Long vehicleMiles = 0l;
        for (Entry<Integer, Long> entry : mileageMap.entrySet()) { 
            vehicleMiles += entry.getValue();
        }
        return vehicleMiles;
    }

    private Number getVehicleStartOdometer(DateTime day, HOSRecAdjusted rec, List<HOSRecord> hosRecordList) {
        
        // find 1st record on day for the vehicle
        Interval dayInterval = new Interval(day, day.plusDays(1).minusSeconds(1));
        for (HOSRecord hosRecord :hosRecordList)
            if (hosRecord.getVehicleID() != null && hosRecord.getVehicleID().equals(rec.getVehicleID()) &&
                 dayInterval.contains(hosRecord.getLogTime().getTime())) {
                return hosRecord.getVehicleOdometer();
            }
        
        // can't find today look back and use most recent
        for (int i = hosRecordList.size()-1; i >= 0; i--) {
            HOSRecord hosRecord = hosRecordList.get(i);
            if (hosRecord.getVehicleID() != null && hosRecord.getVehicleID().equals(rec.getVehicleID()) &&
                    hosRecord.getLogTime().before(day.toDate()))
                  return hosRecord.getVehicleOdometer();
                            
            
        }
        
        return 0;
    }

    private String getVehicleNameStr(Integer vehicleID)
    {
        if (vehicleID == null || vehicleID.intValue() == 0) {
            return "";
        }
        Vehicle vehicle = vehicleMap.get(vehicleID);
        if (vehicle == null) {
            vehicle = vehicleDAO.findByID(vehicleID);
            vehicleMap.put(vehicleID, vehicle);
        }
        if (vehicle == null) {
            return "";
        }
        String returnStr = "";
        if (vehicle.getName() != null) {
            String formatString = MessageUtil.getBundleString(getResourceBundle(),"report.ddl.header.vehicleName");
            returnStr += MessageFormat.format(formatString, new Object[] {
                vehicle.getName() });
        }
        if (vehicle.getLicense() != null && !vehicle.getLicense().isEmpty()) {
            String formatString = MessageUtil.getBundleString(getResourceBundle(),"report.ddl.header.vehicleLicense");
            if (!returnStr.isEmpty())
                returnStr += " - ";
            returnStr += MessageFormat.format(formatString, new Object[] {
                vehicle.getLicense()});
        }
        return returnStr;
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
        if (remarkLogList.size() == 0 && hosRecordList.size() != 0 && !hosRecordList.get(0).getLogTime().after(dayEnd.toDate())) {
            
            remarkLogList.add(populateRemarkLog(hosRecordList.get(0)));
            if (hosRecordList.size() != 1)
                remarkLogList.add(populateRemarkLog(hosRecordList.get(hosRecordList.size() - 1)));
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

                remarkLog.setLocation(MessageUtil.getBundleString(getResourceBundle(),"report.ddl.webLogin"));
                remarkLog.setStatusDescription(MessageUtil.getBundleString(getResourceBundle(),"report.ddl.deferralDay2"));
                return remarkLog;
                
            }
        }
        return null;
    }
    
    RemarkLog populateRemarkLog(HOSRecord hosRecord) {
        RemarkLog remarkLog = new RemarkLog();
        remarkLog.setEdited(hosRecord.getEdited() || hosRecord.getOrigin().equals(HOSOrigin.PORTAL));
        remarkLog.setDeleted(hosRecord.getDeleted());
        remarkLog.setLogTimeDate(hosRecord.getLogTime());
        remarkLog.setLogTimeZone(hosRecord.getTimeZone());
        remarkLog.setLocation(hosRecord.getLocation());
        remarkLog.setOriginalLocation(hosRecord.getOriginalLocation());
        remarkLog.setStartOdometer(hosRecord.getVehicleOdometer()); 
        remarkLog.setStatusDescription(getStatusDescription(hosRecord));
        remarkLog.setStatus(hosRecord.getStatus());
        if (remarkLog.getEdited() && 
            ((remarkLog.getLocation() == null && remarkLog.getOriginalLocation() != null) ||
             (remarkLog.getOriginalLocation() == null && remarkLog.getLocation() != null) ||
             !remarkLog.getLocation().equals(remarkLog.getOriginalLocation())))
             remarkLog.setLocationEdited(true);
        else remarkLog.setLocationEdited(false);
        remarkLog.setEditor("");
        if (hosRecord.getOrigin() != null && hosRecord.getOrigin().equals(HOSOrigin.KIOSK)) 
            remarkLog.setEditor(MessageUtil.getBundleString(getResourceBundle(),"report.ddl.kiosk"));
        if (remarkLog.getEdited()) {
            if (hosRecord.getEditUserID() != null && hosRecord.getEditUserID() != 0)
                remarkLog.setEditor(getEditUserFullName(hosRecord.getEditUserID()));
        }
        return remarkLog;
    }
    

    private String getEditUserFullName(Integer editUserID) {
      
        if (editUserID == null || editUserID.intValue() == 0)
            return "";
        User user = userDAO.findByID(editUserID);
        if (user == null || user.getPerson() == null)
            return "";
        return user.getPerson().getFullName();
    }

    private String getStatusDescription(HOSRecord hosRecord) {
        
        
        String statusString = "";
        if (hosRecord.getStatus() != null)
            statusString = MessageUtil.getBundleString(getResourceBundle(),"status."+hosRecord.getStatus().getCode()); 

        if (hosRecord.getStatus() == HOSStatus.OFF_DUTY) {
            if (inspectionRequired(hosRecord)) {
                if (inspectionPerformed(hosRecord)) {
                    statusString += " - " + MessageUtil.getBundleString(getResourceBundle(),"status." + HOSStatus.HOS_POSTTRIP_INSPECTION.getCode());
                }
                else {
                    statusString += " - " + MessageUtil.getBundleString(getResourceBundle(),"status." + HOSStatus.HOS_POSTTRIP_INSPECTION_NOT_NEEDED.getCode());
                }
            }
        }
        else if (hosRecord.getStatus() == HOSStatus.ON_DUTY) {
            if (inspectionRequired(hosRecord)) {
                if (inspectionPerformed(hosRecord)) {
                    statusString += " - " + MessageUtil.getBundleString(getResourceBundle(),"status." + HOSStatus.HOS_PRETRIP_INSPECTION.getCode());
                }
                else {
                    statusString += " - " + MessageUtil.getBundleString(getResourceBundle(),"status." + HOSStatus.HOS_PRETRIP_INSPECTION_NOT_NEEDED.getCode());
                }
            }
        }
        else if (hosRecord.getStatus() == HOSStatus.FUEL_STOP) {
            if (defaultUseMetric) {
                String formatString = MessageUtil.getBundleString(getResourceBundle(),"report.ddl.fuelStopDescription.METRIC");
                statusString += " " + MessageFormat.format(formatString, new Object[] {
                        MeasurementConversionUtil.fromGallonsToLiters(hosRecord.getTruckGallons()),
                        MeasurementConversionUtil.fromGallonsToLiters(hosRecord.getTrailerGallons())});
            }
            else {
                String formatString = MessageUtil.getBundleString(getResourceBundle(),"report.ddl.fuelStopDescription");
                statusString += " " + MessageFormat.format(formatString, new Object[] {hosRecord.getTruckGallons(),hosRecord.getTrailerGallons()});
            }
        }
        
        return statusString.trim();

    }

    private boolean inspectionPerformed(HOSRecord hosRecord) {
        return  (hosRecord.getTripInspectionFlag() != null && hosRecord.getTripInspectionFlag().booleanValue());
    }

    private boolean inspectionRequired(HOSRecord hosRecord) {
        return  (hosRecord.getTripReportFlag() != null && hosRecord.getTripReportFlag().booleanValue());
    }


    private Number getMilesDrivenOnDay(List<VehicleInfo> vehicles) {
        long total = 0l;
        for (VehicleInfo vehicleInfo : vehicles) {
            total += (vehicleInfo.getDriverMiles() != null) ? vehicleInfo.getDriverMiles().longValue() : 0l;
        }
        return total;
    }
    private List<VehicleInfo> getVehicleInfoForDay(DateTime day, List<HOSVehicleDayData> hosVehicleDayDataList) {
        List<VehicleInfo> vehicleInfoList = new ArrayList<VehicleInfo>();
        if (hosVehicleDayDataList != null) {
            for (HOSVehicleDayData hosVehicleDayData: hosVehicleDayDataList) {
                if (day.toDate().getTime() == hosVehicleDayData.getDay().getTime()) {
                    VehicleInfo vehicleInfo = new VehicleInfo();
                    vehicleInfo.setStartOdometer(hosVehicleDayData.getStartOdometer());
                    vehicleInfo.setName(hosVehicleDayData.getVehicleName());
                    vehicleInfo.setVehicleMiles(hosVehicleDayData.getVehicleMiles());
                    vehicleInfo.setDriverMiles(hosVehicleDayData.getDriverMiles());
                    vehicleInfoList.add(vehicleInfo);
                }
            }
        }
        return vehicleInfoList;
    }
    public Image createGraph(List<HOSRecAdjusted> graphList, DayTotals dayTotals, boolean isDSTStart, boolean isDSTEnd) 
    {
        
        BufferedImage img = null;
        try {
            String imageFile = BASE_LOG_GRAPH_IMAGE_PATH;
            if (isDSTStart)
                imageFile = imageFile + DST_START_IMAGE;
            if (isDSTEnd)
                imageFile = imageFile + DST_END_IMAGE;
            imageFile = imageFile + (!locale.getLanguage().equals("en") ? ("_" + locale.getLanguage() + ".jpg") : ".jpg");
            img = ImageIO.read(ReportUtils.loadFile(imageFile));
        } catch (IOException e) {
            logger.error(e);
            if (img == null)
                try {
                    img = ImageIO.read(ReportUtils.loadFile(BASE_LOG_GRAPH_IMAGE_PATH+".jpg"));
                } catch (IOException e1) {
                    logger.error(e1);
                }
        }
        
        
        HosDriverDailyLogGraph hosDriverDailyLogGraph = new HosDriverDailyLogGraph();
        
        return hosDriverDailyLogGraph.drawHosLogGraph(img, graphList, dayTotals, isDSTEnd);
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

    public HOSDAO getHosDAO() {
        return hosDAO;
    }
    public void setHosDAO(HOSDAO hosDAO) {
        this.hosDAO = hosDAO;
    }
    public AddressDAO getAddressDAO() {
        return addressDAO;
    }

    public void setAddressDAO(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public Map<Integer, Vehicle> getVehicleMap() {
        return vehicleMap;
    }


}