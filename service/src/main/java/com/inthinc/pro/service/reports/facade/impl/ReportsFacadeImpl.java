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

    @SuppressWarnings("unchecked")
    @Override
    public List<TenHoursViolation> getTenHourViolations(Integer groupID, Interval interval) {
        ReportCriteria criteria = reportService.getTenHoursDayViolationsCriteria(getAccountGroupHierarchy(), groupID, interval, getLocale());
        return criteria.getMainDataset();
    }

    @SuppressWarnings("unchecked")
    @Override
    @Deprecated
    public List<StateMileageByVehicleRoadStatus> getStateMileageByVehicleRoadStatus(Integer groupID, Interval interval, boolean dotOnly) {
        List<Integer> groupIDList = new ArrayList<Integer>();
        groupIDList.add(groupID);
        ReportCriteria criteria = reportService.getStateMileageByVehicleRoadStatusReportCriteria(getAccountGroupHierarchy(), groupIDList, interval, getLocale(), getMeasurementType(), dotOnly);
        return criteria.getMainDataset();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<StateMileageByVehicleRoadStatus> getStateMileageByVehicleRoadStatus(List<Integer> groupIDList, Interval interval, boolean dotOnly) {
        ReportCriteria criteria = reportService.getStateMileageByVehicleRoadStatusReportCriteria(getAccountGroupHierarchy(), groupIDList, interval, getLocale(), getMeasurementType(), dotOnly);
        return criteria.getMainDataset();
    }

    public List<MileageByVehicle> getMileageByVehicle(Integer groupID, Interval interval, boolean dotOnly) {
        return getMileageByVehicle(groupID, interval, dotOnly, getLocale(), getMeasurementType());
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


    @SuppressWarnings("unchecked")
    @Override
    public List<StateMileageCompareByGroup> getStateMileageByVehicleStateComparison(Integer groupID, Interval interval, boolean dotOnly) {
        List groupIDList = new ArrayList();
        groupIDList.add(groupID);
        ReportCriteria criteria = reportService.getStateMileageCompareByGroupReportCriteria(getAccountGroupHierarchy(), groupIDList, interval, getLocale(), getMeasurementType(), dotOnly);
        return criteria.getMainDataset();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MileageByVehicle> getStateMileageByVehicle(Integer groupID, Interval interval, boolean dotOnly) {
        List<Integer> groupIDList = new ArrayList<Integer>();
        groupIDList.add(groupID);
        ReportCriteria criteria = reportService.getStateMileageByVehicleReportCriteria(getAccountGroupHierarchy(), groupIDList, interval, getLocale(), getMeasurementType(), dotOnly);
        return criteria.getMainDataset();
    }
    
    @SuppressWarnings("unchecked")
    @Override
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
}
