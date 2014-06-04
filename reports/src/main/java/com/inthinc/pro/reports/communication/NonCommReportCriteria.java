package com.inthinc.pro.reports.communication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.joda.time.DateTimeZone;

import com.inthinc.pro.dao.EventAggregationDAO;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.event.LastReportedEvent;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.util.MessageUtil;
import org.joda.time.Interval;

public class NonCommReportCriteria extends ReportCriteria{
    
    private static Logger logger = Logger.getLogger(NonCommReportCriteria.class);
    
    private NonCommReportCriteria(Locale locale){
        super(ReportType.NON_COMM, null, locale);
        logger.debug(String.format("Creating NonCommReportCriteria with locale %s", locale.toString()));
    }
    
    public static class Builder{
        
        private Locale locale;
        
        private DateTimeZone dateTimeZone;
        
        private List<Integer> groupIDs;
        
        private TimeFrame timeFrame;
        
        private EventAggregationDAO eventAggregationDAO;
        
        private GroupHierarchy groupHierarchy;

        private Boolean dontIncludeUnassignedDevice;

        private Interval interval;

        public Builder(GroupHierarchy groupHierarchy,EventAggregationDAO eventAggregationDAO,List<Integer> groupIDs,TimeFrame timeFrame,Interval interval, boolean dontIncludeUnassignedDevice) {
            this.dateTimeZone = DateTimeZone.UTC;
            this.locale = Locale.US;
            this.groupIDs = groupIDs;
            this.timeFrame = timeFrame;
            this.groupHierarchy = groupHierarchy;
            this.eventAggregationDAO = eventAggregationDAO;
            this.dontIncludeUnassignedDevice = dontIncludeUnassignedDevice;
            this.interval=interval;
            
        }
        
        public void setDateTimeZone(DateTimeZone dateTimeZone) {
            this.dateTimeZone = dateTimeZone;
        }
        
        public DateTimeZone getDateTimeZone() {
            return dateTimeZone;
        }
        
        public void setLocale(Locale locale) {
            this.locale = locale;
        }
        
        public Locale getLocale() {
            return locale;
        }
        
        public List<Integer> getGroupIDs() {
            return groupIDs;
        }
        
        public EventAggregationDAO getEventAggregationDAO() {
            return eventAggregationDAO;
        }
        
        public GroupHierarchy getGroupHierarchy() {
            return groupHierarchy;
        }
        
        public TimeFrame getInterval() {
            return timeFrame;
        }
        
        public NonCommReportCriteria build(){
            logger.debug(String.format("Building NonCommReportCriteria with locale %s",locale));
            List<LastReportedEvent> lastReportedEvents=new ArrayList<LastReportedEvent>();
            if (timeFrame != null && !timeFrame.equals(TimeFrame.CUSTOM_RANGE)){
               lastReportedEvents = eventAggregationDAO.findLastEventForVehicles(this.groupIDs, timeFrame.getInterval(), this.dontIncludeUnassignedDevice);
            }
                else{
                lastReportedEvents = eventAggregationDAO.findLastEventForVehicles(this.groupIDs, interval, this.dontIncludeUnassignedDevice);
            }

            List<NonCommReportCriteria.LastReportedEventWrapper> lastReportedEventWrappers = new ArrayList<NonCommReportCriteria.LastReportedEventWrapper>();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(MessageUtil.formatMessageString("dateTimeFormat", locale), locale);
            for(LastReportedEvent lastReportedEvent:lastReportedEvents){
                LastReportedEventWrapper lastReportedEventWrapper = new LastReportedEventWrapper(lastReportedEvent, groupHierarchy.getFullGroupName(lastReportedEvent.getGroupID()));
                if(lastReportedEvent.getTime() != null){
                    lastReportedEventWrapper.setNoteDate(simpleDateFormat.format(lastReportedEvent.getTime()));
                }
                lastReportedEventWrappers.add(lastReportedEventWrapper);
            }
            Collections.sort(lastReportedEventWrappers);

            NonCommReportCriteria criteria = new NonCommReportCriteria(this.locale);
            criteria.setMainDataset(lastReportedEventWrappers);
            criteria.addDateParameter(REPORT_START_DATE, timeFrame.getInterval().getStart().toDate(), DateTimeZone.UTC.toTimeZone());
            if (timeFrame != null && !timeFrame.equals(TimeFrame.CUSTOM_RANGE)){
                criteria.addDateParameter(REPORT_START_DATE, timeFrame.getInterval().getStart().toDate(), DateTimeZone.UTC.toTimeZone());
            }
            else{
                criteria.addDateParameter(REPORT_START_DATE, interval.getStart().toDate(), DateTimeZone.UTC.toTimeZone());
                criteria.addDateParameter(REPORT_END_DATE, interval.getEnd().minusSeconds(1).toDate(), this.dateTimeZone.toTimeZone());
            }

            return criteria;
            
        }

        public Boolean getDontIncludeUnassignedDevice() {
            return dontIncludeUnassignedDevice;
        }

        public void setDontIncludeUnassignedDevice(Boolean dontIncludeUnassignedDevice) {
            this.dontIncludeUnassignedDevice = dontIncludeUnassignedDevice;
        }
    }
    
    public static class LastReportedEventWrapper  implements Comparable<LastReportedEventWrapper>{
        
        private String groupPath;
        
        private String noteDate;
        
        private LastReportedEvent lastReportedEvent;
        
        public LastReportedEventWrapper(LastReportedEvent lastReportedEvent,String groupPath) {
            this.lastReportedEvent = lastReportedEvent;
            this.groupPath = groupPath;
        }

        public String getGroupPath() {
            return groupPath;
        }

        public void setGroupPath(String groupPath) {
            this.groupPath = groupPath;
        }

        public LastReportedEvent getLastReportedEvent() {
            return lastReportedEvent;
        }
        
        public void setLastReportedEvent(LastReportedEvent lastReportedEvent) {
            this.lastReportedEvent = lastReportedEvent;
        }
        
        
        @Override
        public int compareTo(LastReportedEventWrapper arg0) {
            return this.getGroupPath().compareTo(arg0.getGroupPath());
        }

        public String getNoteDate() {
            return noteDate;
        }

        public void setNoteDate(String noteDate) {
            this.noteDate = noteDate;
        }

    }

}
