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
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;

/**
 * ReportCriteria parent class for DOT/IFTA reports.
 */
public class DOTReportCriteria extends ReportCriteria {
    protected DateTimeFormatter dateTimeFormatter; 
    protected String units;
    protected GroupDAO groupDAO;
    protected StateMileageDAO stateMileageDAO;

    /**
     * Default constructor.
     * @param locale
     */
    public DOTReportCriteria(ReportType reportType, Locale locale) {
        super(reportType, "", locale);
        dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy").withLocale(locale);
        setMeasurementType(MeasurementType.ENGLISH);
    }

    /**
     * @{inherit-doc}
     * @see com.inthinc.pro.reports.ReportCriteria#setMeasurementType(com.inthinc.pro.model.MeasurementType)
     */
    @Override
    public void setMeasurementType(MeasurementType measurementType) {
        super.setMeasurementType(measurementType);
        if (measurementType != null)
            units = measurementType.name();
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
    public void init(List<Integer> groupIDList, Interval interval, boolean dotOnly) {
        addParameter(ReportCriteria.REPORT_START_DATE, dateTimeFormatter.print(interval.getStart()));
        addParameter(ReportCriteria.REPORT_END_DATE, dateTimeFormatter.print(interval.getEnd()));
        addParameter("units", units);
    }

    /**
     * The StateMileageDAO setter.
     * @param stateMileageDAO the DAO to set
     */
    public void setStateMileageDAO(StateMileageDAO stateMileageDAO) {
        this.stateMileageDAO = stateMileageDAO;
    }   
}
