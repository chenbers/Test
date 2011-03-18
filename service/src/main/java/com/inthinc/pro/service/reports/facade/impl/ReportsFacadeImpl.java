package com.inthinc.pro.service.reports.facade.impl;

import java.util.List;
import java.util.Locale;

import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ifta.model.MileageByVehicle;
import com.inthinc.pro.reports.ifta.model.StateMileageByMonth;
import com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus;
import com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup;
import com.inthinc.pro.reports.ifta.model.StateMileageFuelByVehicle;
import com.inthinc.pro.reports.performance.model.DriverHours;
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
     * 
     * @see com.inthinc.pro.service.reports.facade.ReportsFacade#getTenHourViolations(java.lang.Integer, org.joda.time.Interval)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<TenHoursViolation> getTenHourViolations(Integer groupID, Interval interval, Locale locale) {
        ReportCriteria criteria = reportService.getTenHoursDayViolationsCriteria(
                getAccountGroupHierarchy(), groupID, interval, locale);
        return criteria.getMainDataset();
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.facade.ReportsFacade#getDriverHours(java.lang.Integer, org.joda.time.Interval)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DriverHours> getDriverHours(Integer groupID, Interval interval, Locale locale) {
        ReportCriteria criteria = reportService.getDriverHoursReportCriteria(
                getAccountGroupHierarchy(), groupID, interval, locale);
        return criteria.getMainDataset();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inthinc.pro.service.reports.facade.ReportsFacade#getStateMileageByVehicleRoadStatus(java.util.List, org.joda.time.Interval, boolean)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<StateMileageByVehicleRoadStatus> getStateMileageByVehicleRoadStatus(List<Integer> groupIDList, 
            Interval interval, boolean dotOnly, Locale locale, MeasurementType type) {
        ReportCriteria criteria = reportService.getStateMileageByVehicleRoadStatusReportCriteria(getAccountGroupHierarchy(), 
                groupIDList, interval, locale, type, dotOnly);
        return criteria.getMainDataset();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inthinc.pro.service.reports.facade.ReportsFacade#getMileageByVehicle(java.lang.Integer, org.joda.time.Interval, boolean, java.util.Locale,
     *      com.inthinc.pro.model.MeasurementType)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<MileageByVehicle> getMileageByVehicle(List<Integer> idList, Interval interval, boolean dotOnly, Locale locale, MeasurementType type) {
        ReportCriteria criteria = reportService.getMileageByVehicleReportCriteria(getAccountGroupHierarchy(), 
                idList, interval, locale, type, dotOnly);
        return criteria.getMainDataset();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inthinc.pro.service.reports.facade.ReportsFacade#getStateMileageByVehicleStateComparison(java.lang.Integer, org.joda.time.Interval, boolean, java.util.Locale,
     *      com.inthinc.pro.model.MeasurementType)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<StateMileageCompareByGroup> getStateMileageGroupComparison(List<Integer> groupIDList, 
            Interval interval, boolean dotOnly, Locale locale, MeasurementType measurementType) {
        ReportCriteria criteria = reportService.getStateMileageCompareByGroupReportCriteria(
                getAccountGroupHierarchy(), groupIDList, interval, locale, measurementType, dotOnly);
        return criteria.getMainDataset();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inthinc.pro.service.reports.facade.ReportsFacade#getStateMileageByVehicle(java.lang.Integer, org.joda.time.Interval, boolean)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<MileageByVehicle> getStateMileageByVehicle(List<Integer> groupIDList, 
            Interval interval, boolean dotOnly, Locale locale, MeasurementType measurementType) {
        ReportCriteria criteria = reportService.getStateMileageByVehicleReportCriteria(
                getAccountGroupHierarchy(), groupIDList, interval, locale, measurementType, dotOnly);
        return criteria.getMainDataset();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inthinc.pro.service.reports.facade.ReportsFacade#getStateMileageByVehicleByMonth(java.util.List, org.joda.time.Interval, boolean)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<StateMileageByMonth> getStateMileageByMonth(List<Integer> groupIDList, Interval interval, boolean dotOnly, Locale locale, MeasurementType type) {
        ReportCriteria criteria = reportService.getStateMileageByMonthReportCriteria(
                getAccountGroupHierarchy(), groupIDList, interval, locale, type, dotOnly);
        return criteria.getMainDataset();
    }

    /**
     * @see com.inthinc.pro.service.reports.facade.ReportsFacade#getStateMileageFuelByVehicle(java.util.List, org.joda.time.Interval, boolean)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<StateMileageFuelByVehicle> getStateMileageFuelByVehicle(List<Integer> groupIDList, Interval interval, boolean dotOnly, Locale locale, MeasurementType measurementType) {
        ReportCriteria criteria = reportService.getStateMileageFuelByVehicleReportCriteria(
                getAccountGroupHierarchy(), groupIDList, interval, locale, measurementType, dotOnly);
        return criteria.getMainDataset();
    }


}
