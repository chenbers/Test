/**
 * 
 */
package com.inthinc.pro.reports.ifta;

import java.util.List;
import java.util.Locale;

import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.StateMileageDAO;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;

/**
 * ReportCriteria parent class for DOT/IFTA reports.
 */
public abstract class DOTReportCriteria extends ReportCriteria {
    protected DateTimeFormatter dateTimeFormatter; 
    protected String units;
    protected GroupDAO groupDAO;
    protected StateMileageDAO stateMileageDAO;
    protected GroupHierarchy accountGroupHierarchy;
    /**
     * Default constructor.
     * @param reportType the report type
     * @param locale the user Locale
     */
    public DOTReportCriteria(ReportType reportType, Locale locale) {
        super(reportType, "", locale);
        dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy").withLocale(locale);
        setMeasurementType(MeasurementType.ENGLISH);
    }

    public GroupHierarchy getAccountGroupHierarchy() {
        return accountGroupHierarchy;
    }

    public void setAccountGroupHierarchy(GroupHierarchy accountGroupHierarchy) {
        this.accountGroupHierarchy = accountGroupHierarchy;
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.reports.ReportCriteria#setMeasurementType(com.inthinc.pro.model.MeasurementType)
     */
    @Override
    public void setMeasurementType(MeasurementType measurementType) {
        super.setMeasurementType(measurementType);
        if (measurementType != null)
            units = measurementType.name().toLowerCase();
    }

    /**
     * Setter for Group DAO. 
     * @param groupDAO
     */
    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    /**
     * Initiate the data set and the parameters for the report.
     * @param groupId the groupId chosen by the user
     * @param interval the date period
     * @param iftaOnly the flag to consider only IFTA 
     */
    public void init(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, boolean dotOnly) {
        addParameter(ReportCriteria.REPORT_START_DATE, dateTimeFormatter.print(interval.getStart()));
        addParameter(ReportCriteria.REPORT_END_DATE, dateTimeFormatter.print(interval.getEnd()));
        addParameter("units", units);
        this.accountGroupHierarchy = accountGroupHierarchy;
    }

    /**
     * The StateMileageDAO setter.
     * @param stateMileageDAO the DAO to set
     */
    public void setStateMileageDAO(StateMileageDAO stateMileageDAO) {
        this.stateMileageDAO = stateMileageDAO;
    }   
    
    public String getFullGroupName(Integer groupID) {
        if (accountGroupHierarchy == null)
            return "";
        String fullName = accountGroupHierarchy.getFullGroupName(groupID, GROUP_SEPARATOR);
        if (fullName.endsWith(GROUP_SEPARATOR)) {
            fullName = fullName.substring(0, fullName.length() - GROUP_SEPARATOR.length());
        }
        return fullName;

    }

}
