package com.inthinc.pro.service.reports.facade.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ifta.model.MileageByVehicle;
import com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus;
import com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup;
import com.inthinc.pro.reports.ifta.model.StateMileageFuelByVehicle;
import com.inthinc.pro.reports.performance.model.TenHoursViolation;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.service.reports.facade.ReportsFacade;
import com.inthinc.pro.service.security.TiwiproPrincipal;

/**
 * Facade to ReportCriteriaService.
 */
@Component
public class ReportsFacadeImpl implements ReportsFacade {

    @Autowired
    private ReportCriteriaService reportService;
    @Autowired
    private TiwiproPrincipal principal;

    /**
     * Returns the user group hierarchy.
     * 
     * @return
     */
    GroupHierarchy getAccountGroupHierarchy() {
        return principal.getAccountGroupHierarchy();
    }

    /**
     * Return the measurement type from the user preferences
     * 
     * @return Measurement Type
     */
    MeasurementType getMeasurementType() {
        return principal.getUser().getPerson().getMeasurementType();
    }

    /**
     * Returns the user Locale.
     * 
     * @return The user Locale
     */
    Locale getLocale() {
        return principal.getUser().getPerson().getLocale();
    }

    /**
     * The reportService setter.
     * 
     * @param reportService
     *            the reportService to set
     */
    void setReportService(ReportCriteriaService reportService) {
        this.reportService = reportService;
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.facade.ReportsFacade#getTenHourViolations(java.lang.Integer, org.joda.time.Interval)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<TenHoursViolation> getTenHourViolations(Integer groupID, Interval interval) {
        ReportCriteria criteria = reportService.getTenHoursDayViolationsCriteria(getAccountGroupHierarchy(), groupID, interval, getLocale());
        return criteria.getMainDataset();
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.facade.ReportsFacade#getStateMileageByVehicleRoadStatus(java.lang.Integer, org.joda.time.Interval, boolean)
     */
    @SuppressWarnings("unchecked")
    @Override
    @Deprecated
    public List<StateMileageByVehicleRoadStatus> getStateMileageByVehicleRoadStatus(Integer groupID, Interval interval, boolean dotOnly) {
        List<Integer> groupIDList = new ArrayList<Integer>();
        groupIDList.add(groupID);
        ReportCriteria criteria = reportService.getStateMileageByVehicleRoadStatusReportCriteria(getAccountGroupHierarchy(), groupIDList, interval, getLocale(), getMeasurementType(), dotOnly);
        return criteria.getMainDataset();
    }
    
    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.facade.ReportsFacade#getStateMileageByVehicleRoadStatus(java.util.List, org.joda.time.Interval, boolean)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<StateMileageByVehicleRoadStatus> getStateMileageByVehicleRoadStatus(List<Integer> groupIDList, Interval interval, boolean dotOnly) {
        ReportCriteria criteria = reportService.getStateMileageByVehicleRoadStatusReportCriteria(getAccountGroupHierarchy(), groupIDList, interval, getLocale(), getMeasurementType(), dotOnly);
        return criteria.getMainDataset();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inthinc.pro.service.reports.facade.ReportsFacade#getMileageByVehicle(java.lang.Integer, org.joda.time.Interval, boolean, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<MileageByVehicle> getMileageByVehicle(Integer groupID, Interval interval, boolean dotOnly, 
            Locale locale, MeasurementType type) {
        List<Integer> groupIDList = new ArrayList<Integer>();
        groupIDList.add(groupID);
        ReportCriteria criteria = reportService.getMileageByVehicleReportCriteria(getAccountGroupHierarchy(), 
                groupIDList, interval, locale, type, dotOnly);
        return criteria.getMainDataset();        
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.facade.ReportsFacade#getStateMileageByVehicleStateComparison(java.lang.Integer, org.joda.time.Interval, boolean, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<StateMileageCompareByGroup> getStateMileageGroupComparison(Integer groupID, Interval interval, 
            boolean dotOnly, Locale locale, MeasurementType measurementType) {
        List groupIDList = new ArrayList();
        groupIDList.add(groupID);
        ReportCriteria criteria = reportService.getStateMileageCompareByGroupReportCriteria(getAccountGroupHierarchy(), 
                groupIDList, interval, locale, measurementType, dotOnly);
        return criteria.getMainDataset();
    }
    
    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.facade.ReportsFacade#getStateMileageByVehicleStateComparison(java.lang.Integer, org.joda.time.Interval, boolean, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<StateMileageCompareByGroup> getStateMileageByVehicleStateComparison(List<Integer> groupIDList, Interval interval, 
            boolean dotOnly, Locale locale, MeasurementType measurementType) {
        ReportCriteria criteria = reportService.getStateMileageCompareByGroupReportCriteria(getAccountGroupHierarchy(), 
                groupIDList, interval, locale, measurementType, dotOnly);
        return criteria.getMainDataset();
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.facade.ReportsFacade#getStateMileageByVehicle(java.lang.Integer, org.joda.time.Interval, boolean)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<MileageByVehicle> getStateMileageByVehicle(Integer groupID, Interval interval, 
            boolean dotOnly, Locale locale, MeasurementType measurementType) {
        List<Integer> groupIDList = new ArrayList<Integer>();
        groupIDList.add(groupID);
        ReportCriteria criteria = reportService.getStateMileageByVehicleReportCriteria(getAccountGroupHierarchy(), 
                groupIDList, interval, locale, measurementType, dotOnly);
        return criteria.getMainDataset();
    }
    
    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.facade.ReportsFacade#getStateMileageByVehicleByMonth(java.lang.Integer, org.joda.time.Interval, boolean)
     */
    @SuppressWarnings("unchecked")
    @Override
    @Deprecated
    public List<MileageByVehicle> getStateMileageByVehicleByMonth(Integer groupID,
            Interval interval,
            boolean dotOnly) {
        List groupIDList = new ArrayList();
        groupIDList.add(groupID);
        ReportCriteria criteria = reportService.getStateMileageByMonthReportCriteria(getAccountGroupHierarchy(),
                groupIDList, 
                interval,
                getLocale(),
                getMeasurementType(), 
                dotOnly);
        return criteria.getMainDataset();
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.facade.ReportsFacade#getStateMileageByVehicleByMonth(java.util.List, org.joda.time.Interval, boolean)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<MileageByVehicle> getStateMileageByVehicleByMonth(List<Integer> groupIDList, Interval interval, boolean dotOnly) {
        ReportCriteria criteria = reportService.getStateMileageByMonthReportCriteria(getAccountGroupHierarchy(),
                groupIDList, 
                interval,
                getLocale(),
                getMeasurementType(), 
                dotOnly);
        return criteria.getMainDataset();
    }

    /**
     * @see com.inthinc.pro.service.reports.facade.ReportsFacade#getStateMileageFuelByVehicle(java.util.List, org.joda.time.Interval, boolean)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<StateMileageFuelByVehicle> getStateMileageFuelByVehicle(List<Integer> groupIDList, Interval interval, boolean dotOnly) {
        ReportCriteria criteria = reportService.getStateMileageFuelByVehicleReportCriteria(getAccountGroupHierarchy(), groupIDList, interval, getLocale(), getMeasurementType(), dotOnly);
        return criteria.getMainDataset();
    }
}
