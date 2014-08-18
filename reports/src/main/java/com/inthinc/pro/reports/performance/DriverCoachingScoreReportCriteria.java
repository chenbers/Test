package com.inthinc.pro.reports.performance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.LocalDate;

import com.inthinc.hos.model.HOSRec;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.hos.model.ViolationsData;
import com.inthinc.hos.rules.RuleSetFactory;
import com.inthinc.hos.violations.DailyViolations;
import com.inthinc.hos.violations.ShiftViolations;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.dao.report.DriverPerformanceDAO;
import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.dao.util.HOSUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverPerformance;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.util.DateTimeUtil;

/**
 * 
 * @author mstrong
 *
 */
public class DriverCoachingScoreReportCriteria extends ReportCriteria{
    
    private static Logger logger = Logger.getLogger(DriverCoachingScoreReportCriteria.class);
    
    private DriverCoachingScoreReportCriteria(Locale locale) {
        super(ReportType.DRIVER_COACHING_SCORE, ReportType.DRIVER_COACHING_SCORE.getLabel(), locale);
        logger.trace(String.format("Creating DriverCoachingScoreReportCriteria with locale %s",locale));
    }
    
    /**
     * Builder used to create the DriverCoachingScoreReportCriteria object
     * 
     * @author mstrong
     *
     */
    public static class Builder{
        
        private static Logger logger = Logger.getLogger(Builder.class);
        
        private GroupReportDAO groupReportDAO;
        
        private DriverPerformanceDAO driverPerformanceDAO;
        
        private DriverDAO driverDAO;
        
        private Locale locale;
        
        private Integer groupID;
        
        private Integer driverID;
        
        private Interval interval;
        
        private DateTimeZone dateTimeZone;
        
        private Map<Integer,Map<String, Integer>> driverTimeFrameScoreMap;
        
        private Boolean includeInactiveDrivers;

        private Boolean includeZeroMilesDrivers;

        private GroupHierarchy groupHierarchy;
        
        private Boolean accountHOSEnabled;
        
        private HOSDAO hosDAO;
        
        public Builder(GroupReportDAO groupReportDAO,DriverPerformanceDAO driverPerformanceDAO,Integer groupID,Interval interval) {
            this(groupReportDAO, driverPerformanceDAO, groupID, interval, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS, ReportCriteria.DEFAULT_EXCLUDE_ZERO_MILES_DRIVERS);
        }
        public Builder(GroupReportDAO groupReportDAO,DriverPerformanceDAO driverPerformanceDAO,Integer groupID,Interval interval, boolean includeInactiveDrivers, boolean includeZeroMilesDrivers) {
           this.groupReportDAO = groupReportDAO;
           this.driverPerformanceDAO = driverPerformanceDAO;
           this.interval = interval;
           this.groupID = groupID;
           this.driverTimeFrameScoreMap = new HashMap<Integer, Map<String,Integer>>();
           this.includeInactiveDrivers = includeInactiveDrivers;
           this.includeZeroMilesDrivers = includeZeroMilesDrivers;
        }
        
        public Builder(GroupReportDAO groupReportDAO,DriverPerformanceDAO driverPerformanceDAO, DriverDAO driverDAO,Integer driverID,Interval interval) {
            this(groupReportDAO, driverPerformanceDAO, driverDAO, driverID, interval, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS, ReportCriteria.DEFAULT_EXCLUDE_ZERO_MILES_DRIVERS);
        }
        public Builder(GroupReportDAO groupReportDAO,DriverPerformanceDAO driverPerformanceDAO, DriverDAO driverDAO,Integer driverID,Interval interval, boolean includeInactiveDrivers, boolean includeZeroMilesDrivers) {
            this.groupReportDAO = groupReportDAO;
            this.driverPerformanceDAO = driverPerformanceDAO;
            this.interval = interval;
            this.driverID = driverID;
            this.driverDAO = driverDAO;
            this.driverTimeFrameScoreMap = new HashMap<Integer, Map<String,Integer>>();
            this.includeInactiveDrivers = includeInactiveDrivers;
            this.includeZeroMilesDrivers = includeZeroMilesDrivers;
         }
        
        public Builder setDateTimeZone(DateTimeZone dateTimeZone){
            this.dateTimeZone = dateTimeZone;
            return this;
        }
        
        public Builder setLocale(Locale locale){
            this.locale = locale;
            return this;
        }
        
        public Builder setGroupHierarchy(GroupHierarchy groupHierarchy){
            this.groupHierarchy = groupHierarchy;
            return this;
        }

        public Boolean isAccountHOSEnabled() {
            return accountHOSEnabled;
        }

        public void setAccountHOSEnabled(Boolean accountHOSEnabled) {
            this.accountHOSEnabled = accountHOSEnabled;
        }

        public void setHosDAO(HOSDAO hosDAO) {
            this.hosDAO = hosDAO;
        }
        
        public void setDriverDAO(DriverDAO driverDAO) {
            this.driverDAO = driverDAO;
        }

        /**
         * Gets a custom interval with the end date equal to the given interval and the start date based on the
         * given time frame's duration
         *
         * @param timeFrame time frame to get duration
         * @param interval interval to get end date
         * @param dateTimeZone time zone for new interval
         * @return the new interval
         */
        public Interval getCustomInterval(TimeFrame timeFrame, Interval interval, DateTimeZone dateTimeZone) {
            DateMidnight end =  interval.getEnd().toDateMidnight();
            long endMilis = end.getMillis();
            long startMilis = interval.getEnd().toDateMidnight().minus(timeFrame.getInterval().toDurationMillis()).getMillis();

            return TimeFrame.CUSTOM_RANGE.getInterval(startMilis, endMilis, dateTimeZone);
        }

        public List<ReportCriteria> build(){
            if(groupReportDAO == null){
                throw new IllegalArgumentException("groupReportDAO must not be null");
            }
            
            if(driverPerformanceDAO == null){
                throw new IllegalArgumentException("driverPerformanceDAO must not be null");
            }
            
            if(groupID == null && (driverID == null || driverDAO == null)){
                throw new IllegalArgumentException("driverID and driverDAO must not be null if groupID is null");
            }
            
            if(this.dateTimeZone == null){
                this.dateTimeZone = DateTimeZone.UTC;
            }



            //If we're requesting this for a driver
            if(groupID == null){
                Driver driver = driverDAO.findByID(driverID);
                groupID = driver.getGroupID();
            }
            
            loadDriverScoresIntoMap(TimeFrame.DAY, getCustomInterval(TimeFrame.DAY, interval, dateTimeZone));
            loadDriverScoresIntoMap(TimeFrame.PAST_SEVEN_DAYS, getCustomInterval(TimeFrame.PAST_SEVEN_DAYS, interval, dateTimeZone));
            loadDriverScoresIntoMap(TimeFrame.LAST_THIRTY_DAYS, getCustomInterval(TimeFrame.LAST_THIRTY_DAYS, interval, dateTimeZone));
            loadDriverScoresIntoMap(TimeFrame.THREE_MONTHS, getCustomInterval(TimeFrame.THREE_MONTHS, interval, dateTimeZone));
            
            List<ReportCriteria> driverCoachingScoreReportCriterias = new ArrayList<ReportCriteria>();
            
            /* 
             * Iterate over the driver performances and place the individual driver stats 
             * into a separate collection over which the report can iterate.
             */
            List<DriverPerformance> driverPerformances =  driverPerformanceDAO.getDriverPerformanceListForGroup(groupID, null, interval, includeInactiveDrivers, includeZeroMilesDrivers);
            for(DriverPerformance driverPerformance:driverPerformances){
                if(driverID != null && driverPerformance.getDriverID().equals(driverID)){
                    if (isAccountHOSEnabled()) {
                        populationHOSViolationCount(driverPerformance, interval);
                    }
                    DriverCoachingScoreReportCriteria driverCoachingScoreReportCriteria = convert(driverPerformance);
                    driverCoachingScoreReportCriterias.add(driverCoachingScoreReportCriteria);
                    break;
                }else if (driverID == null){
                    if (isAccountHOSEnabled()) {
                        populationHOSViolationCount(driverPerformance, interval);
                    }
                    DriverCoachingScoreReportCriteria driverCoachingScoreReportCriteria = convert(driverPerformance);
                    driverCoachingScoreReportCriterias.add(driverCoachingScoreReportCriteria);
                }
            }
            
            /*
             * If there were no drivers stats, we still need to pass a single empty
             * criteria so the report can return an error message instead of silently
             * failing and returning an empty report.
             */
            if(driverCoachingScoreReportCriterias.size() == 0) {
                DriverCoachingScoreReportCriteria emptyCriteria = new DriverCoachingScoreReportCriteria(locale);
                driverCoachingScoreReportCriterias.add(emptyCriteria);
            }
            
            return driverCoachingScoreReportCriterias;
        }
        
        private void populationHOSViolationCount(DriverPerformance driverPerformance, Interval interval) {
            Driver driver = driverDAO.findByID(driverPerformance.getDriverID());
            RuleSetType driverDOTType = driver.getDot();
            if (driverDOTType == null || driverDOTType == RuleSetType.NON_DOT) { 
                return;
            }
            
            DateTimeZone driverTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
            Interval queryInterval = DateTimeUtil.getExpandedInterval(interval, driverTimeZone, RuleSetFactory.getDaysBackForRuleSetType(driverDOTType), RuleSetFactory.getDaysForwardForRuleSetType(driverDOTType));
            List<HOSRecord> hosRecordList = hosDAO.getHOSRecords(driver.getDriverID(), queryInterval, false);
            Collections.sort(hosRecordList);

            DateTime reportEndDate = new LocalDate(interval.getEnd()).toDateTimeAtStartOfDay(driverTimeZone).plusDays(1).minusSeconds(1);
            if (reportEndDate.isAfterNow())
                reportEndDate = new DateTime();

            List<HOSRec> recListForViolationsCalc = HOSUtil.getRecListFromLogList(hosRecordList, reportEndDate.toDate(), true);
            List<ViolationsData> dailyViolations = new DailyViolations().getDailyHosViolationsForReport(interval, driverTimeZone.toTimeZone(), driverDOTType, recListForViolationsCalc);
            List<ViolationsData> shiftViolations = new ShiftViolations().getHosViolationsInTimeFrame(interval, driverTimeZone.toTimeZone(), driverDOTType, null, recListForViolationsCalc);
            
            driverPerformance.setHosViolationCount((dailyViolations == null ? 0 : dailyViolations.size()) + (shiftViolations == null ? 0 : shiftViolations.size()));
        }
        
        public ReportCriteria buildSingle(){
            List<ReportCriteria> reportCriterias = build();
            if(reportCriterias != null && reportCriterias.size() > 0){
                return reportCriterias.get(0);
            }else{
                return new DriverCoachingScoreReportCriteria(locale);
            }
        }
        
        /**
         * Converts a driver performance object into a DriverCoachingScoreReportCriteria object
         * 
         * @param driverPerformance
         * @return
         */
        private DriverCoachingScoreReportCriteria convert(DriverPerformance driverPerformance){
            logger.trace(String.format("Loading driver performance into DriverCoachingScoreReportViolationSummary List for driver %d", driverPerformance.getDriverID()));
            
            DriverCoachingScoreReportCriteria driverCoachingScoreReportCriteria = new DriverCoachingScoreReportCriteria(locale);
            driverCoachingScoreReportCriteria.setTimeZone(dateTimeZone.toTimeZone());
            List<DriverCoachingScoreReportViolationSummary> driverCoachingScoreReportViolationSummaries = toViolationSummaryList(driverPerformance);
            driverCoachingScoreReportCriteria.setMainDataset(driverCoachingScoreReportViolationSummaries);
            driverCoachingScoreReportCriteria.addParameter("DRIVER_NAME", driverPerformance.getDriverName());
            driverCoachingScoreReportCriteria.setReportDate(new Date(), this.dateTimeZone.toTimeZone());
            driverCoachingScoreReportCriteria.addParameter("DRIVER_SCORES_MAP", this.driverTimeFrameScoreMap.get(driverPerformance.getDriverID()));
            driverCoachingScoreReportCriteria.addReportStartEndDateParams(interval);

            if(logger.isTraceEnabled()){
                logger.trace(String.format("Driver violations loaded into %s", driverCoachingScoreReportViolationSummaries.toString()));
            }
            return driverCoachingScoreReportCriteria;
        }
        
        /**
        * Adds the list of driver scores to a map for a particular time frame
        * This loads the scores from the database and adds the scores by time frame to a map which is associated with a
        * driver id.
        * 
        * This is used to display all scores along the top of the report.
        * 
        * @param timeFrame
        * @param interval
        */
        private void loadDriverScoresIntoMap(TimeFrame timeFrame, Interval interval){
           List<DriverVehicleScoreWrapper> dayScoreList =  groupReportDAO.getDriverScores(groupID, interval, this.groupHierarchy);
           
           for(DriverVehicleScoreWrapper driverVehicleScoreWrapper:dayScoreList){
               /* If the driverID is present, then we're going to only allow the driver to be added */
               if(driverID != null && driverVehicleScoreWrapper.getDriver().getDriverID().equals(driverID)){
                   if(this.driverTimeFrameScoreMap.get(driverVehicleScoreWrapper.getDriver().getDriverID()) == null){
                       driverTimeFrameScoreMap.put(driverVehicleScoreWrapper.getDriver().getDriverID(), new HashMap<String, Integer>());
                   }
                   Score score = driverVehicleScoreWrapper.getScore();
                   driverTimeFrameScoreMap.get(driverVehicleScoreWrapper.getDriver().getDriverID()).put(timeFrame.name(), score.getOverall() == null?-1:score.getOverall().intValue());
                   break;
               }else if(this.driverID == null){
                   if(this.driverTimeFrameScoreMap.get(driverVehicleScoreWrapper.getDriver().getDriverID()) == null){
                       driverTimeFrameScoreMap.put(driverVehicleScoreWrapper.getDriver().getDriverID(), new HashMap<String, Integer>());
                   }
                   Score score = driverVehicleScoreWrapper.getScore();
                   driverTimeFrameScoreMap.get(driverVehicleScoreWrapper.getDriver().getDriverID()).put(timeFrame.name(), score.getOverall() == null?-1:score.getOverall().intValue());
               }
           }
        }
        
        private List<DriverCoachingScoreReportViolationSummary> toViolationSummaryList(DriverPerformance driverPerformance){
            List<DriverCoachingScoreReportViolationSummary> violationsSummaryList = new ArrayList<DriverCoachingScoreReportViolationSummary>();
            Integer speedTotal = driverPerformance.getSpeedCount0to7Over() + driverPerformance.getSpeedCount8to14Over() + driverPerformance.getSpeedCount15Over();
            violationsSummaryList.add(new DriverCoachingScoreReportViolationSummary(DriverCoachingScoreReportViolation.SPEED, speedTotal.toString()));
            violationsSummaryList.add(new DriverCoachingScoreReportViolationSummary(DriverCoachingScoreReportViolation.SEAT_BELT, driverPerformance.getSeatbeltCount() == null?"0":driverPerformance.getSeatbeltCount().toString()));
            
            /* 
             * Setup aggressive driving totals. Add aggressive child violations to the 
             * aggressive driving parent violation summary which is a total of all it's children 
             * */
            Integer aggresiveDrivingTotal = driverPerformance.getHardAccelCount() + driverPerformance.getHardBrakeCount() + driverPerformance.getHardTurnCount() + driverPerformance.getHardVerticalCount();
            DriverCoachingScoreReportViolationSummary aggressiveViolationSummary = new DriverCoachingScoreReportViolationSummary(DriverCoachingScoreReportViolation.AGGRESSIVE, aggresiveDrivingTotal.toString());
            violationsSummaryList.add(aggressiveViolationSummary);
            aggressiveViolationSummary.addChildViolation(new DriverCoachingScoreReportViolationSummary(DriverCoachingScoreReportViolation.HARD_ACCELARATE, driverPerformance.getHardAccelCount() == null?"0":driverPerformance.getHardAccelCount().toString()));
            aggressiveViolationSummary.addChildViolation(new DriverCoachingScoreReportViolationSummary(DriverCoachingScoreReportViolation.HARD_BRAKE, driverPerformance.getHardBrakeCount() == null?"0":driverPerformance.getHardBrakeCount().toString()));
            aggressiveViolationSummary.addChildViolation(new DriverCoachingScoreReportViolationSummary(DriverCoachingScoreReportViolation.HARD_TURN, driverPerformance.getHardTurnCount() == null?"0":driverPerformance.getHardTurnCount().toString()));
            aggressiveViolationSummary.addChildViolation(new DriverCoachingScoreReportViolationSummary(DriverCoachingScoreReportViolation.HARD_BUMP, driverPerformance.getHardVerticalCount() == null?"0":driverPerformance.getHardVerticalCount().toString()));
            violationsSummaryList.add(new DriverCoachingScoreReportViolationSummary(DriverCoachingScoreReportViolation.IDLE_DURATION, DateUtil.getDurationFromSeconds(driverPerformance.getIdleLo() == null ? 0 : driverPerformance.getIdleLo().intValue())));
            if (isAccountHOSEnabled()) {
                violationsSummaryList.add(new DriverCoachingScoreReportViolationSummary(DriverCoachingScoreReportViolation.HOS, driverPerformance.getHosViolationCount() == null?"0":driverPerformance.getHosViolationCount().toString()));
            }
            
            return violationsSummaryList;
        }

        public Integer getDriverID() {
            return driverID;
        }

        public void setDriverID(Integer driverID) {
            this.driverID = driverID;
        }

        public Boolean getIncludeInactiveDrivers() {
            return includeInactiveDrivers;
        }

        public void setIncludeInactiveDrivers(Boolean includeInactiveDrivers) {
            this.includeInactiveDrivers = includeInactiveDrivers;
        }

        public Boolean getIncludeZeroMilesDrivers() {
            return includeZeroMilesDrivers;
        }

        public void setIncludeZeroMilesDrivers(Boolean includeZeroMilesDrivers) {
            this.includeZeroMilesDrivers = includeZeroMilesDrivers;
        }
    }
    
    public static class DriverCoachingScoreReportViolationSummary{
        
        private DriverCoachingScoreReportViolation driverCoachingScoreReportViolation;
        
        private String summary;
        
        private List<DriverCoachingScoreReportViolationSummary> childViolations;
        
        public DriverCoachingScoreReportViolationSummary(DriverCoachingScoreReportViolation driverCoachingScoreReportViolation, String summary) {
            super();
            this.driverCoachingScoreReportViolation = driverCoachingScoreReportViolation;
            this.summary = summary;
        }

        
        public String getSummary() {
            return summary;
        }
        
        public DriverCoachingScoreReportViolation getDriverCoachingScoreReportViolation() {
            return driverCoachingScoreReportViolation;
        }


        @Override
        public String toString() {
            return "DriverCoachingScoreReportViolationSummary [driverCoachingScoreReportViolation=" + driverCoachingScoreReportViolation + ", summary=" + summary + "]";
        }
        
        public List<DriverCoachingScoreReportViolationSummary> getChildViolations() {
            return childViolations;
        }
        
        public void setChildViolations(List<DriverCoachingScoreReportViolationSummary> childViolations) {
            this.childViolations = childViolations;
        }
        
        public void addChildViolation(DriverCoachingScoreReportViolationSummary childViolation){
            if(childViolations == null){
                childViolations = new  ArrayList<DriverCoachingScoreReportCriteria.DriverCoachingScoreReportViolationSummary>();
            }
            childViolations.add(childViolation);
        }
    }
    
    /**
     * 
     * @author mstrong
     * 
     * Enumeration to represent the violations which will be displayed in the driver coaching report.
     *
     */
    public static enum DriverCoachingScoreReportViolation{
        
        SPEED("violation.speed"),
        SEAT_BELT("violation.seatBelt"),
        AGGRESSIVE("violation.aggressive"),
        HARD_ACCELARATE("violation.hardAccelarate"),
        HARD_BRAKE("violation.hardBrake"),
        HARD_TURN("violation.hardTurn"),
        HARD_BUMP("violation.hardBump"),
        IDLE_DURATION("violation.idleDuration"),
        HOS("violation.hos");
        
        private String i18nCode;
        
        private DriverCoachingScoreReportViolation(String i18nCode){
            this.i18nCode = i18nCode;
        }
        
        public String getI18nCode() {
            return i18nCode;
        }
        
    }
    
}
