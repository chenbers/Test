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
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.hos.adjusted.HOSAdjustedList;
import com.inthinc.hos.ddl.HosDailyDriverLog;
import com.inthinc.hos.ddl.HosDriverDailyLogGraph;
import com.inthinc.hos.ddl.Recap;
import com.inthinc.hos.ddl.RecapCanada;
import com.inthinc.hos.ddl.RecapCanada2007;
import com.inthinc.hos.ddl.RecapType;
import com.inthinc.hos.ddl.RecapUS;
import com.inthinc.hos.ddl.RemarkLog;
import com.inthinc.hos.ddl.VehicleInfo;
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
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.hos.HOSOccupantLog;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.model.hos.HOSVehicleDayData;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.jasper.ReportUtils;
import com.inthinc.pro.reports.util.DateTimeUtil;
import com.inthinc.pro.reports.util.MessageUtil;

public class HosDailyDriverLogReportCriteria {

    private static final Logger logger = Logger.getLogger(HosDailyDriverLogReportCriteria.class);
    
    private AccountDAO accountDAO;
    private AddressDAO addressDAO;
    private DriverDAO driverDAO;
    private GroupDAO groupDAO;
    private HOSDAO hosDAO;
    private UserDAO userDAO;
    private VehicleDAO vehicleDAO;
    
    private static final String BASE_LOG_GRAPH_IMAGE_PATH = "hos/hosLog";
    
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

    public HosDailyDriverLogReportCriteria(Locale locale, Boolean defaultUseMetric) 
    {
        this.locale = locale;
        this.defaultUseMetric = defaultUseMetric;
        dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy").withLocale(locale);
        setResourceBundle(ReportType.HOS_DAILY_DRIVER_LOG_REPORT.getResourceBundle(locale));
    }

    public ResourceBundle getResourceBundle() {
        if (resourceBundle == null)
            resourceBundle = ReportType.HOS_DAILY_DRIVER_LOG_REPORT.getResourceBundle(locale);
        return resourceBundle;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public void init(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval)
    {
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


    public void init(GroupHierarchy accountGroupHierarchy, Integer driverID, Interval interval)
    {
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

    protected List<Driver> getReportDriverList(List<Group> reportGroupList) {
        List<Driver> driverList = new ArrayList<Driver>();
        for (Group group : reportGroupList)
            driverList.addAll(driverDAO.getDrivers(group.getGroupID()));
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

    public List<ReportCriteria> getCriteriaList()
    {
    
        return criteriaList;
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
        adjustForOccupantTravelTime(hosRecordList, adjustedList, endDate);
        adjustForOccupantTravelTime(hosRecordList, originalAdjustedList, endDate);
        List<HOSRec> hosRecapList = getRecapList(adjustedList, endDate);
        Collections.reverse(hosRecordList);

        Date currentTime = new Date();
        
        criteriaList = new ArrayList<ReportCriteria>();

        for (DateTime intervalDay = interval.getStart(); intervalDay.isBefore(interval.getEnd()); intervalDay = intervalDay.plusDays(1)) 
        {
            
            LocalDate localDate = new LocalDate(intervalDay);
            DateTime driverDay = localDate.toDateTimeAtStartOfDay(DateTimeZone.forTimeZone(driver.getPerson().getTimeZone()));
            DateTimeZone dateTimeZone = getBestTimeZone(driverDay.toDate(), hosRecordList, driver.getPerson().getTimeZone());

            DateTime day = localDate.toDateTimeAtStartOfDay(dateTimeZone);
            if (day.toDate().after(currentTime)) 
                break;

            List<HOSRecAdjusted> logListForDay = adjustedList.getAdjustedListForDay(day.toDate(), currentTime, true, dateTimeZone.toTimeZone()); 
            List<HOSOccupantLog> occupantLogListForDay = getOccupantLogsForDay(logListForDay, hosOccupantLogList);
            HOSRec firstHosRecForDay = getFirstRecordForDay(day.toDate(), hosRecapList, dateTimeZone);
            RuleSetType ruleSetType = getRuleSetTypeForDay(day, driver, firstHosRecForDay);
            
            HosDailyDriverLog dayData= new HosDailyDriverLog();
            dayData.setCorrectedDayTotals(adjustedList.getAdjustedDayTotals(logListForDay));
            dayData.setDay(dateTimeFormatter.print(day));
            dayData.setRemarksList(getRemarksListForDay(day, hosRecordList));
            dayData.setCarrierName(carrierName);
            dayData.setMainAddress(mainOfficeDisplayAddress);
            dayData.setTerminalAddress(terminalAddress == null ? "" : terminalAddress.getDisplayString());
            dayData.setDriverName(driver.getPerson().getFullName());
            dayData.setEdited(isListEdited(logListForDay));
            dayData.setCodrivers(getCodrivers(logListForDay, occupantLogListForDay));
            dayData.setShipping(getShippingInfoForDay(logListForDay, occupantLogListForDay));
            dayData.setTrailers(getTrailerInfoForDay(logListForDay, occupantLogListForDay));
            dayData.setRuleSetType(ruleSetType);
            if (initVehicleDayData)
                dayData.setVehicles(initVehicleInfoForDay(day, driver.getDriverID(), logListForDay, hosRecordList));
            else dayData.setVehicles(getVehicleInfoForDay(day, hosVehicleDayData));
            dayData.setMilesDriven(getMilesDrivenOnDay(dayData.getVehicles()));
            dayData.setCorrectedGraphList(logListForDay);
            dayData.setCorrectedGraph(createGraph(logListForDay, dayData.getCorrectedDayTotals()));
            if (dayData.getEdited()) {
                List<HOSRecAdjusted> originalLogListForDay = originalAdjustedList.getAdjustedListForDay(day.toDate(), currentTime, true, dateTimeZone.toTimeZone());
                dayData.setOriginalGraphList(originalLogListForDay);
                dayData.setOriginalDayTotals(originalAdjustedList.getAdjustedDayTotals(originalLogListForDay));
                dayData.setOriginalGraph(createGraph(originalLogListForDay, dayData.getOriginalDayTotals()));
 
            }
            dayData.setRecap(initRecap(ruleSetType, day, hosRecapList, dayData.getCorrectedDayTotals(), dateTimeZone));
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
    
    List<VehicleInfo> initVehicleInfoForDay(DateTime day, Integer driverID, List<HOSRecAdjusted> logListForDay, List<HOSRecord> hosRecordList) {
        List<VehicleInfo> vehicleInfoList = new ArrayList<VehicleInfo>();
        for (HOSRecAdjusted rec  : logListForDay) {
            if (rec.getVehicleID() != null && rec.getVehicleID().intValue() != 0) {
                boolean alreadyAdded = false;
                for (VehicleInfo vehicleInfo : vehicleInfoList) {
                    if (rec.getVehicleID().equals(vehicleInfo.getVehicleID())) {
                        alreadyAdded = true;
                        break;
                    }
                }
                if (!alreadyAdded) {
                    VehicleInfo vehicleInfo = new VehicleInfo();
                    vehicleInfo.setStartOdometer(getVehicleStartOdometer(day, rec, hosRecordList));
                    vehicleInfo.setName(getVehicleNameStr(rec.getVehicleID()));
                    vehicleInfo.setVehicleID(rec.getVehicleID());
                    Map<Integer, Long> mileageMap = hosDAO.fetchMileageForDayVehicle(day, rec.getVehicleID());
                    vehicleInfo.setDriverMiles(getDriverMiles(mileageMap, logListForDay, driverID));
                    vehicleInfo.setVehicleMiles(getVehicleMiles(mileageMap));
                    vehicleInfoList.add(vehicleInfo);
                }
            }
        }
        return vehicleInfoList;
    }

    private Number getDriverMiles(Map<Integer, Long> mileageMap, List<HOSRecAdjusted> logListForDay, Integer driverID) {
        for (Entry<Integer, Long> entry : mileageMap.entrySet()) { 
            Integer vehicleDriverID = entry.getKey();
            for (HOSRecAdjusted dayRec  : logListForDay) {
                if (dayRec.getStatus()== HOSStatus.DRIVING && vehicleDriverID.equals(driverID)) {
                    return entry.getValue();
                }
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
//            for (HOSRecAdjusted adjustedRec : adjustedList.getHosList()) {
             for (int i = 0; i < adjustedList.getHosList().size(); i++) {
                HOSRecAdjusted adjustedRec = adjustedList.getHosList().get(i);
                if (adjustedRec.getStatus().equals(HOSStatus.TRAVELTIME_OCCUPANT) && !adjustedRec.getId().equalsIgnoreCase("generated"))
                {
                    for (HOSRec rec : ruleList)
                    {
                        if (rec.getId().equals(adjustedRec.getId()))
                        {
                            adjustedRec.setGraphStatus(rec.getStatus());
                            
                            // adjacent records
                            for (int j = i-1; j >= 0; j--) 
                                if (adjustedList.getHosList().get(j).getStatus() == HOSStatus.TRAVELTIME_OCCUPANT)
                                    adjustedList.getHosList().get(j).setGraphStatus(rec.getStatus());
                                else break;
                            
                            for (int j = i+1; j < adjustedList.getHosList().size(); j++) 
                                if (adjustedList.getHosList().get(j).getStatus() == HOSStatus.TRAVELTIME_OCCUPANT)
                                    adjustedList.getHosList().get(j).setGraphStatus(rec.getStatus());
                                else break;
                            
                            break;
                        }
                    }
                }
            }
        }
    }
    
    private DateTimeZone getBestTimeZone(Date date, List<HOSRecord> hosRecordList, TimeZone driverTimeZone) {
        
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
            return DateTimeZone.forTimeZone(driverTimeZone);
        
        return DateTimeZone.forTimeZone(priorRec.getTimeZone());
        
        
    }
    private List<Recap> initRecap(RuleSetType ruleSetType, DateTime day, List<HOSRec> hosRecList, DayTotals dayTotals, DateTimeZone dateTimeZone) {
        
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
            recap = new RecapCanada(ruleSetType, day, hosRecList, (dayTotals.getDriving()+dayTotals.getOnDuty()) * 15, dateTimeZone);
        }
        else if (ruleSetType == RuleSetType.CANADA_2007_CYCLE_1 ||
                    ruleSetType == RuleSetType.CANADA_2007_CYCLE_2 ||
                    ruleSetType == RuleSetType.CANADA_2007_60_DEGREES_CYCLE_1 ||
                    ruleSetType == RuleSetType.CANADA_2007_60_DEGREES_CYCLE_2) {
            recap = new RecapCanada2007(ruleSetType, day, hosRecList, (dayTotals.getDriving()+dayTotals.getOnDuty()) * 15, dateTimeZone);
        }
        else {
            if (ruleSetType == RuleSetType.TEXAS)
                recap = new RecapUS(ruleSetType, day, hosRecList, (dayTotals.getDriving()+dayTotals.getOnDuty()+dayTotals.getSleeperBerth()) * 15, dateTimeZone);
            else recap = new RecapUS(ruleSetType, day, hosRecList, (dayTotals.getDriving()+dayTotals.getOnDuty()) * 15, dateTimeZone);
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

    private List<HOSOccupantLog> getOccupantLogsForDay(List<HOSRecAdjusted> logListForDay, List<HOSOccupantLog> hosOccupantLogList) {
        
        List<HOSOccupantLog> dayOccupantLogList = new ArrayList<HOSOccupantLog>();
        if (hosOccupantLogList != null &&  hosOccupantLogList.size() > 0) {
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
        }
        return dayOccupantLogList;
    }
    private boolean timesOverlap(HOSRecAdjusted hosRecord, HOSOccupantLog hosOccupantLog) {
        // added this to prevent crashes with bad intervals from the database (end before start) 
        if (hosOccupantLog.getEndTime() == null || hosOccupantLog.getLogTime() == null ||
                hosRecord.getTotalRealMinutes() < 0 ||
                hosOccupantLog.getEndTime().before(hosOccupantLog.getLogTime())) {
            logger.error("Error in interval end time before start " + hosOccupantLog.getLogTime() + " " + hosOccupantLog.getEndTime() + " " + hosOccupantLog.getVehicleID());
            return false;
        }
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
    
    private HOSRec getFirstRecordForDay(Date day, List<HOSRec> hosList, DateTimeZone timeZone)
    {
        Date dayStart = DateUtil.getStartOfDay(day, timeZone.toTimeZone());
        Date dayEnd =  DateUtil.getEndOfDay(day, timeZone.toTimeZone());
        
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
            String imageFile = BASE_LOG_GRAPH_IMAGE_PATH + (!locale.getLanguage().equals("en") ? ("_" + locale.getLanguage() + ".jpg") : ".jpg");
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
        
        return hosDriverDailyLogGraph.drawHosLogGraph(img, graphList, dayTotals);
    }
    
    

    private List<HOSRec> getRecapList(HOSAdjustedList adjustedList, Date endDate) {
        List<HOSRec> hosRecapList = new ArrayList<HOSRec>();
        Date currentDate = new Date();
        adjustedList.initAdjustedTimeAndMinutes(true, endDate.after(currentDate) ? currentDate : endDate);

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