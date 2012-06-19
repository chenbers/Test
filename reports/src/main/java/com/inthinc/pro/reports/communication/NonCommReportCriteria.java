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
        
        public Builder(GroupHierarchy groupHierarchy,EventAggregationDAO eventAggregationDAO,List<Integer> groupIDs,TimeFrame timeFrame) {
            this.dateTimeZone = DateTimeZone.UTC;
            this.locale = Locale.US;
            this.groupIDs = groupIDs;
            this.timeFrame = timeFrame;
            this.groupHierarchy = groupHierarchy;
            this.eventAggregationDAO = eventAggregationDAO;
            
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
            List<LastReportedEvent> lastReportedEvents = eventAggregationDAO.findRecentEventByDevice(this.groupIDs, timeFrame.getInterval());
            
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
            criteria.addDateParameter(REPORT_START_DATE, timeFrame.getInterval().getStart().toDate(), this.dateTimeZone.toTimeZone());
            
            /* The interval returns for the end date the beginning of the next day. We minus a second to get the previous day */
            criteria.addDateParameter(REPORT_END_DATE, timeFrame.getInterval().getEnd().minusSeconds(1).toDate(), this.dateTimeZone.toTimeZone());
            return criteria;
            
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
