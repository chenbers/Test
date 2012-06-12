package com.inthinc.pro.reports.performance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventAggregationDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.aggregation.DriverForgivenEventTotal;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;

public class DriverExcludedViolationsCriteria extends ReportCriteria{
    
    private static Logger logger = Logger.getLogger(DriverExcludedViolationsCriteria.class);
    
    private DriverExcludedViolationsCriteria(Locale locale) {
        super(ReportType.DRIVER_EXCLUDED_VIOLATIONS, ReportType.DRIVER_EXCLUDED_VIOLATIONS.getLabel(), locale);
        logger.trace(String.format("Creating DriverExcludedViolationsCriteria with locale %s",locale));
    }
    
    /**
     * Builder used to create the DriverExcludedViolationsCriteria object
     * 
     * @author mstrong
     *
     */
    public static class Builder{
        
        private static final String GROUP_HIERARCHY = "GROUP_HIERARCHY";
        
        private static Logger logger = Logger.getLogger(Builder.class);
        
        private EventAggregationDAO eventAggregationDAO;
        
        private GroupHierarchy groupHierarchy;
        
        private GroupDAO groupDAO;
        
        private DriverDAO driverDAO;
        
        private Locale locale;
        
        private List<Integer> groupIDs;

        private Interval interval;
        
        private DateTimeZone dateTimeZone;
        
        public Builder(GroupHierarchy groupHierarchy,EventAggregationDAO eventAggregationDAO,GroupDAO groupDAO,DriverDAO driverDAO,List<Integer> groupIDs,Interval interval) {
           this.interval = interval;
           this.groupIDs = groupIDs;
           this.driverDAO = driverDAO;
           this.groupHierarchy = groupHierarchy;
           this.eventAggregationDAO = eventAggregationDAO;
           this.groupDAO = groupDAO;
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
            
            List<DriverForgivenEventTotal> driverForgivenEventTotals = eventAggregationDAO.findDriverForgivenEventTotalsByGroups(groupIDs, interval);
            logger.debug(String.format("Building DriverExcludedViolationsCriteria with groupIDs: %s", groupIDs));
            DriverExcludedViolationsCriteria driverExcludedViolationsCriteria = new DriverExcludedViolationsCriteria(locale);
            
            driverExcludedViolationsCriteria.addDateParameter(REPORT_START_DATE, interval.getStart().toDate(), this.dateTimeZone.toTimeZone());
            
            /* The interval returns for the end date the beginning of the next day. We minus a second to get the previous day */
            driverExcludedViolationsCriteria.addDateParameter(REPORT_END_DATE, interval.getEnd().minusSeconds(1).toDate(), this.dateTimeZone.toTimeZone());
            
            List<DriverForgivenEventTotalWrapper> driverForgivenEventTotalWrappers = new ArrayList<DriverExcludedViolationsCriteria.DriverForgivenEventTotalWrapper>();
            for(DriverForgivenEventTotal driverForgivenEventTotal:driverForgivenEventTotals){
                DriverForgivenEventTotalWrapper driverForgivenEventTotalWrapper = new DriverForgivenEventTotalWrapper(driverForgivenEventTotal, groupHierarchy.getFullGroupName(driverForgivenEventTotal.getGroupID()));
                driverForgivenEventTotalWrappers.add(driverForgivenEventTotalWrapper);
            }
            Collections.sort(driverForgivenEventTotalWrappers);
            driverExcludedViolationsCriteria.setMainDataset(driverForgivenEventTotalWrappers);
            
            return driverExcludedViolationsCriteria;
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
