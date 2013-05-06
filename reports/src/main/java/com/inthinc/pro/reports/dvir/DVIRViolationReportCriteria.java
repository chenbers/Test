package com.inthinc.pro.reports.dvir;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.DateTimeZone;

import com.inthinc.pro.dao.report.DVIRViolationReportDAO;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.dvir.DVIRViolationReport;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;

public class DVIRViolationReportCriteria extends ReportCriteria {
    private static Logger logger = Logger.getLogger(DVIRViolationReportCriteria.class);
    
    private DVIRViolationReportCriteria(Locale locale) {
        super(ReportType.DVIR_VIOLATION, null, locale);
        logger.debug(String.format("Creating NonCommReportCriteria with locale %s", locale.toString()));
    }
    
    public static class Builder {
        
        private Locale locale;
        
        private DateTimeZone dateTimeZone;
        
        private List<Integer> groupIDs;
        
        private TimeFrame timeFrame;
        
        private GroupHierarchy groupHierarchy;
        
        private Integer groupID;
        
        private DVIRViolationReportDAO dvirViolationDao;
        
        public Builder(GroupHierarchy groupHierarchy, Integer groupID, DVIRViolationReportDAO dvirViolationDao, List<Integer> groupIDs, TimeFrame timeFrame) { // GroupHierarchy groupHierarchy
            this.dateTimeZone = DateTimeZone.UTC;
            this.locale = Locale.US;
            this.groupHierarchy = groupHierarchy;
            this.groupID = groupID;
            this.dvirViolationDao = dvirViolationDao;
            this.groupIDs = groupIDs;
            this.timeFrame = timeFrame;
        }
        
        public Integer getGroupID() {
            return groupID;
        }
        
        public void setGroupID(Integer groupID) {
            this.groupID = groupID;
        }
        
        public Locale getLocale() {
            return locale;
        }
        
        public void setLocale(Locale locale) {
            this.locale = locale;
        }
        
        public DateTimeZone getDateTimeZone() {
            return dateTimeZone;
        }
        
        public void setDateTimeZone(DateTimeZone dateTimeZone) {
            this.dateTimeZone = dateTimeZone;
        }
        
        public List<Integer> getGroupIDs() {
            return groupIDs;
        }
        
        public void setGroupIDs(List<Integer> groupIDs) {
            this.groupIDs = groupIDs;
        }
        
        public TimeFrame getTimeFrame() {
            return timeFrame;
        }
        
        public void setTimeFrame(TimeFrame timeFrame) {
            this.timeFrame = timeFrame;
        }
        
        public GroupHierarchy getGroupHierarchy() {
            return groupHierarchy;
        }
        
        public void setGroupHierarchy(GroupHierarchy groupHierarchy) {
            this.groupHierarchy = groupHierarchy;
        }
        
        public DVIRViolationReportDAO getDvirViolationDao() {
            return dvirViolationDao;
        }
        
        public void setDvirViolationDao(DVIRViolationReportDAO dvirViolationDao) {
            this.dvirViolationDao = dvirViolationDao;
        }
        
        public DVIRViolationReportCriteria build() {
            logger.debug(String.format("Building DVIRViolationReportCriteria with locale %s", locale));
            
            List<DVIRViolationReport> dvirViolationList = (List<DVIRViolationReport>) dvirViolationDao.getDVIRViolationsForGroup(this.groupIDs, this.timeFrame.getInterval());
            
            ViolationTypeListWharehouse violationTypeListWharehouse = new ViolationTypeListWharehouse(dvirViolationList);
            
            List<DVIRViolationReportCriteria.ViolationTypeListWharehouse> mainDataSetList = new ArrayList<DVIRViolationReportCriteria.ViolationTypeListWharehouse>();
            mainDataSetList.add(violationTypeListWharehouse);
            
            DVIRViolationReportCriteria criteria = new DVIRViolationReportCriteria(locale);
            
            criteria.setMainDataset(mainDataSetList);
            criteria.setRecordCountParam(dvirViolationList.size());
            criteria.addDateParameter(REPORT_START_DATE, timeFrame.getInterval().getStart().toDate(), this.dateTimeZone.toTimeZone());
            /* The interval returns for the end date the beginning of the next day. We minus a second to get the previous day */
            criteria.addDateParameter(REPORT_END_DATE, timeFrame.getInterval().getEnd().minusSeconds(1).toDate(), this.dateTimeZone.toTimeZone());
            criteria.setTimeFrame(this.timeFrame);
            
            Group group = null;
            if (this.groupHierarchy != null && this.groupHierarchy.getGroup(this.groupID) != null) {
                group = this.groupHierarchy.getGroup(this.groupID);
            }
            if (group.getGroupID() != null)
                criteria.addParameter(GROUP, String.valueOf(group.getGroupID()));
            else
                criteria.addParameter(GROUP, "");
            
            return criteria;
        }
        
    }
    
    public static class ViolationTypeListWharehouse {
        private List<DVIRViolationReport> unsafeViolationList;
        private List<DVIRViolationReport> noPreTripInspectionList;
        private List<DVIRViolationReport> noPostTripInspectionList;
        
        public ViolationTypeListWharehouse(List<DVIRViolationReport> dvirViolationList) {
            this.buildSeparateViolationTypeLists(dvirViolationList);
        }
        
        public List<DVIRViolationReport> getUnsafeViolationList() {
            return unsafeViolationList;
        }
        
        public List<DVIRViolationReport> getNoPreTripInspectionList() {
            return noPreTripInspectionList;
        }
        
        public List<DVIRViolationReport> getNoPostTripInspectionList() {
            return noPostTripInspectionList;
        }
        
        private void buildSeparateViolationTypeLists(List<DVIRViolationReport> dvirViolationList) {
            if (dvirViolationList == null || dvirViolationList.size() < 1) {
                return;
            }
            
            for (DVIRViolationReport report : dvirViolationList) {
                if (report.getNoteType().equals(NoteType.DVIR_DRIVEN_UNSAFE)) {
                    if (this.unsafeViolationList == null) {
                        this.unsafeViolationList = new ArrayList<DVIRViolationReport>();
                    }
                    
                    this.unsafeViolationList.add(report);
                }
                
                else if (report.getNoteType().equals(NoteType.DVIR_DRIVEN_NOPOSTINSPEC)) {
                    if (this.noPostTripInspectionList == null) {
                        this.noPostTripInspectionList = new ArrayList<DVIRViolationReport>();
                    }
                    
                    this.noPostTripInspectionList.add(report);
                }
                
                else if (report.getNoteType().equals(NoteType.DVIR_DRIVEN_NOPREINSPEC)) {
                    if (this.noPreTripInspectionList == null) {
                        this.noPreTripInspectionList = new ArrayList<DVIRViolationReport>();
                    }
                    
                    this.noPreTripInspectionList.add(report);
                } else {
                    continue;
                }
            }
        }
    }
    
}
