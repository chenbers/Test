package com.inthinc.pro.reports.performance;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventAggregationDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.aggregation.DriverForgivenEventTotal;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import org.apache.log4j.Logger;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class DriverExcludedViolationsDetailCriteria extends ReportCriteria{

    private static Logger logger = Logger.getLogger(DriverExcludedViolationsDetailCriteria.class);

    private DriverExcludedViolationsDetailCriteria(Locale locale) {
        super(ReportType.DRIVER_EXCLUDED_VIOLATIONS_DETAIL, ReportType.DRIVER_EXCLUDED_VIOLATIONS_DETAIL.getLabel(), locale);
        logger.trace(String.format("Creating DriverExcludedViolationsDetailCriteria with locale %s",locale));
    }

    /**
     * Builder used to create the DriverExcludedViolationsCriteria object
     *
     * @author mstrong
     *
     */
    public static class Builder{

        private static Logger logger = Logger.getLogger(Builder.class);

        private EventAggregationDAO eventAggregationDAO;

        private GroupHierarchy groupHierarchy;

        private GroupDAO groupDAO;

        private DriverDAO driverDAO;

        private Locale locale;

        private List<Integer> groupIDs;

        private Interval interval;

        private DateTimeZone dateTimeZone;

        private Boolean includeInactiveDrivers;

        private Boolean includeZeroMilesDrivers;

        public Builder(GroupHierarchy groupHierarchy,EventAggregationDAO eventAggregationDAO,GroupDAO groupDAO,DriverDAO driverDAO,List<Integer> groupIDs,Interval interval) {
            this( groupHierarchy, eventAggregationDAO, groupDAO, driverDAO, groupIDs, interval,  ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS,  ReportCriteria.DEFAULT_EXCLUDE_ZERO_MILES_DRIVERS);
        }
        public Builder(GroupHierarchy groupHierarchy,EventAggregationDAO eventAggregationDAO,GroupDAO groupDAO,DriverDAO driverDAO,List<Integer> groupIDs,Interval interval, boolean includeInactiveDrivers, boolean includeZeroMilesDrivers) {
           this.interval = interval;
           this.groupIDs = groupIDs;
           this.driverDAO = driverDAO;
           this.groupHierarchy = groupHierarchy;
           this.eventAggregationDAO = eventAggregationDAO;
           this.groupDAO = groupDAO;
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

        public ReportCriteria build(){
            if(eventAggregationDAO == null){
                throw new IllegalArgumentException("eventAggregationDAO must not be null");
            }

            if(groupDAO == null){
                throw new IllegalArgumentException("groupDAO must not be null");
            }

            if(driverDAO == null){
                throw new IllegalArgumentException("driverDAO must not be null");
            }

            if(groupIDs == null || groupIDs.isEmpty()){
                throw new IllegalArgumentException("groupIDs must not be null and must not be empty");
            }

            if(this.dateTimeZone == null){
                this.dateTimeZone = DateTimeZone.UTC;
            }

            if(this.locale == null){
                this.locale = Locale.US;
            }

            List<DriverForgivenEventTotal> driverForgivenEventTotals = eventAggregationDAO.findDriverForgivenEventTotalsByGroups(groupIDs, interval, includeInactiveDrivers, includeZeroMilesDrivers);
            logger.debug(String.format("Building DriverExcludedViolationsCriteria with groupIDs: %s", groupIDs));
            DriverExcludedViolationsDetailCriteria driverExcludedViolationsCriteria = new DriverExcludedViolationsDetailCriteria(locale);

            driverExcludedViolationsCriteria.addReportStartEndDateParams(interval);

            List<DriverForgivenEventTotalWrapper> driverForgivenEventTotalWrappers = new ArrayList<DriverExcludedViolationsDetailCriteria.DriverForgivenEventTotalWrapper>();
            for(DriverForgivenEventTotal driverForgivenEventTotal:driverForgivenEventTotals){
                DriverForgivenEventTotalWrapper driverForgivenEventTotalWrapper = new DriverForgivenEventTotalWrapper(driverForgivenEventTotal, groupHierarchy.getFullGroupName(driverForgivenEventTotal.getGroupID()));
                driverForgivenEventTotalWrappers.add(driverForgivenEventTotalWrapper);
            }
            Collections.sort(driverForgivenEventTotalWrappers);
            driverExcludedViolationsCriteria.setMainDataset(driverForgivenEventTotalWrappers);
            
            return driverExcludedViolationsCriteria;
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
    
    public static class DriverForgivenEventTotalWrapper  implements Comparable<DriverForgivenEventTotalWrapper>{
        
        private String groupPath;
        
        private DriverForgivenEventTotal driverForgivenEventTotal;
        
        public DriverForgivenEventTotalWrapper(DriverForgivenEventTotal driverForgivenEventTotal,String groupPath) {
            this.driverForgivenEventTotal = driverForgivenEventTotal;
            this.groupPath = groupPath;
        }

        public String getGroupPath() {
            return groupPath;
        }

        public void setGroupPath(String groupPath) {
            this.groupPath = groupPath;
        }

        public DriverForgivenEventTotal getDriverForgivenEventTotal() {
            return driverForgivenEventTotal;
        }

        public void setDriverForgivenEventTotal(DriverForgivenEventTotal driverForgivenEventTotal) {
            this.driverForgivenEventTotal = driverForgivenEventTotal;
        }
        
        @Override
        public int compareTo(DriverForgivenEventTotalWrapper arg0) {
            return this.getGroupPath().compareTo(arg0.getGroupPath());
        }
    }
}
