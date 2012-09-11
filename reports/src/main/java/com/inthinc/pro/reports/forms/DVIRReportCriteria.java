package com.inthinc.pro.reports.forms;

import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.joda.time.DateTimeZone;

import com.inthinc.pro.dao.FormsDAO;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.form.SubmissionData;
import com.inthinc.pro.model.form.TriggerType;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;

public abstract class DVIRReportCriteria extends ReportCriteria {
    private static Logger logger = Logger.getLogger(DVIRReportCriteria.class);

    protected DVIRReportCriteria(ReportType reportType, Locale locale) {
        super(reportType, reportType.getLabel(), locale);
        this.dateTimeZone = DateTimeZone.UTC;
        logger.trace(String.format("Creating DVIRReportCriteria with locale %s and reportType %s",locale,reportType.getLabel()));
    }
    protected abstract  TriggerType getTriggerType();
    protected Locale locale;
    
//    protected DateTimeZone dateTimeZone;
//    
    protected Integer groupID;
    
    protected TimeFrame timeFrame;
    protected DateTimeZone dateTimeZone;
    
    protected FormsDAO formsDAO;
    private GroupHierarchy groupHierarchy;

    public DVIRReportCriteria build(GroupHierarchy groupHierarchy, FormsDAO formsDAO, Integer groupID, TimeFrame timeFrame) {
        logger.debug(String.format("Building DVIRReportCriteria with locale %s", locale));

        List<SubmissionData> submissions = formsDAO.getSubmissions(getTriggerType(), timeFrame.getInterval().getStart().toDate(), timeFrame.getInterval().getEnd().minusSeconds(1).toDate(), groupID);

        setMainDataset(submissions);
        addDateParameter(REPORT_START_DATE, timeFrame.getInterval().getStart().toDate(), this.dateTimeZone.toTimeZone());

        /* The interval returns for the end date the beginning of the next day. We minus a second to get the previous day */
        addDateParameter(REPORT_END_DATE, timeFrame.getInterval().getEnd().minusSeconds(1).toDate(), this.dateTimeZone.toTimeZone());
        setTimeFrame(timeFrame);
        setGroupID(groupID);
        return this;

    }

//        public Builder(GroupHierarchy groupHierarchy,FormsDAO formsDAO,Integer groupID,TimeFrame timeFrame) {
//            this.dateTimeZone = DateTimeZone.UTC;
//            this.locale = Locale.US;
//            this.groupID = groupID;
//            this.timeFrame = timeFrame;
//            this.groupHierarchy = groupHierarchy;
//            this.formsDAO = formsDAO;
//            
//        }
        
        public void setDateTimeZone(DateTimeZone dateTimeZone) {
            this.dateTimeZone = dateTimeZone;
        }
        
        public DateTimeZone getDateTimeZone() {
            return dateTimeZone;
        }
        
        public GroupHierarchy getGroupHierarchy() {
            return groupHierarchy;
        }
        
        public Integer getGroupID() {
            return groupID;
        }

        public TimeFrame getInterval() {
            return timeFrame;
        }
        public FormsDAO getFormsDAO() {
            return formsDAO;
        }


        public void setFormsDAO(FormsDAO formsDAO) {
            this.formsDAO = formsDAO;
        }
}
