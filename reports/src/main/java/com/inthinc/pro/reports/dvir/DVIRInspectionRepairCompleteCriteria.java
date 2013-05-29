package com.inthinc.pro.reports.dvir;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTimeZone;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.FormsDAO;
import com.inthinc.pro.dao.report.DVIRInspectionRepairReportDAO;
import com.inthinc.pro.dao.report.DVIRViolationReportDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.dvir.DVIRInspectionRepairReport;
import com.inthinc.pro.model.form.SubmissionData;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;

public class DVIRInspectionRepairCompleteCriteria extends ReportCriteria {
    private static Logger logger = Logger.getLogger(DVIRInspectionRepairCompleteCriteria.class);
    
    private DVIRInspectionRepairCompleteCriteria(ReportType reportType, Locale locale){
        super(reportType, null, locale);
        logger.debug(String.format("Creating DVIRInspectionRepairCompleteCriteria with locale %s", locale.toString()));
    }
    
    public static class Builder {
        
        private Boolean isDetailed;
        
        private Locale locale;
        
        private DateTimeZone dateTimeZone;
        
        private List<Integer> groupIDs;
        
        private TimeFrame timeFrame;
        
        private GroupHierarchy groupHierarchy;
        
        private Integer groupID;
        
        private DVIRInspectionRepairReportDAO dVIRInspectionRepairReportDAO;
        
        private DriverDAO driverDAO;
        
        private FormsDAO formsDAO;
        
        public Builder(GroupHierarchy groupHierarchy, Integer groupID, DVIRInspectionRepairReportDAO dVIRInspectionRepairReportDAO, DriverDAO driverDAO, FormsDAO formsDAO, List<Integer> groupIDs, TimeFrame timeFrame, Boolean isDetailed){
            this.dateTimeZone = DateTimeZone.UTC;
            this.locale = Locale.US;
            this.groupHierarchy = groupHierarchy;
            this.groupID = groupID;
            this.dVIRInspectionRepairReportDAO = dVIRInspectionRepairReportDAO;
            this.groupIDs = groupIDs;
            this.timeFrame = timeFrame;
            this.driverDAO = driverDAO;
            this.formsDAO = formsDAO;
            this.isDetailed = isDetailed;
        }
        
        public Boolean getIsDetailed() {
            return isDetailed;
        }

        public void setIsDetailed(Boolean isDetailed) {
            this.isDetailed = isDetailed;
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

        public Integer getGroupID() {
            return groupID;
        }

        public void setGroupID(Integer groupID) {
            this.groupID = groupID;
        }

        public DVIRInspectionRepairReportDAO getdVIRInspectionRepairReportDAO() {
            return dVIRInspectionRepairReportDAO;
        }

        public void setdVIRInspectionRepairReportDAO(DVIRInspectionRepairReportDAO dVIRInspectionRepairReportDAO) {
            this.dVIRInspectionRepairReportDAO = dVIRInspectionRepairReportDAO;
        }
        
        public DriverDAO getDriverDAO() {
            return driverDAO;
        }

        public void setDriverDAO(DriverDAO driverDAO) {
            this.driverDAO = driverDAO;
        }

        public FormsDAO getFormsDAO() {
            return formsDAO;
        }

        public void setFormsDAO(FormsDAO formsDAO) {
            this.formsDAO = formsDAO;
        }

        public DVIRInspectionRepairCompleteCriteria build() {
            logger.debug(String.format("Building DVIRInspectionRepairCompleteCriteria with locale %s", locale));
            
            List<DVIRInspectionRepairReport> dvirInspectionList = (List<DVIRInspectionRepairReport>)dVIRInspectionRepairReportDAO.getDVIRInspectionRepairsForGroup(groupIDs, this.timeFrame.getInterval());
            
            // Logic that will pull the inspection form associated w/ the repair note ... if one exists! This will not be needed if the report is not including the details.
            if(isDetailed){
                for(DVIRInspectionRepairReport report : dvirInspectionList) {
                    Integer vehicleID = report.getVehicleID();
                    Date submissionDate = new Date(Long.valueOf(report.getAttr_formSubmissionTime()));
                    Integer formDefinitionID = Integer.valueOf(report.getAttr_formDefinitionID());
                    Integer groupID = report.getGroupID();
                    
                    SubmissionData submissionData = formsDAO.getSingleSubmission(vehicleID, formDefinitionID, submissionDate, groupID);
                    
                    if(submissionData != null){
                        report.setSubmissionData(submissionData);
                    }
                }
            }
            
            /*
             * Figure out some logic goodness that may / may not find a persons name based on the driverID that was entered as an Note Attribute by the user
             */
            replaceDriverIDWithPersonName(dvirInspectionList);
            
            ReportType reportType = isDetailed ? ReportType.DVIR_REPAIR_DETAIL : ReportType.DVIR_REPAIR;
            
            DVIRInspectionRepairCompleteCriteria criteria = new DVIRInspectionRepairCompleteCriteria(reportType, locale);
            
            criteria.setMainDataset(dvirInspectionList);
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

        private void replaceDriverIDWithPersonName(List<DVIRInspectionRepairReport> dvirInspectionList) {
            for (DVIRInspectionRepairReport report : dvirInspectionList) {
                if (StringUtils.isNumeric(report.getAttr_inspectorName())) {
                    Driver driver = driverDAO.findByID(Integer.valueOf(report.getAttr_inspectorName()));
                    Person person = driver.getPerson();
                    
                    String personFullName = person.getFullName();
                    
                    if (personFullName != null && personFullName.length() > 0) {
                        report.setAttr_inspectorName(personFullName);
                    }
                }
                if (StringUtils.isNumeric(report.getAttr_signOffName())) {
                    Driver driver = driverDAO.findByID(Integer.valueOf(report.getAttr_signOffName()));
                    Person person = driver.getPerson();
                    
                    String personFullName = person.getFullName();
                    
                    if (personFullName != null && personFullName.length() > 0) {
                        report.setAttr_signOffName(personFullName);
                    }
                }
                if (StringUtils.isNumeric(report.getAttr_mechanicName())) {
                    Driver driver = driverDAO.findByID(Integer.valueOf(report.getAttr_mechanicName()));
                    Person person = driver.getPerson();
                    
                    String personFullName = person.getFullName();
                    
                    if (personFullName != null && personFullName.length() > 0) {
                        report.setAttr_mechanicName(personFullName);
                    }
                }
            }
        }
    }
}
