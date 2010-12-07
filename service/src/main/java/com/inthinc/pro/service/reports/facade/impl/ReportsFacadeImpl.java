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
import com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus;
import com.inthinc.pro.reports.performance.model.TenHoursViolation;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.service.reports.facade.ReportsFacade;
import com.inthinc.pro.service.security.TiwiproPrincipal;

/**
 * Facade to ReportCriteriaService.
 */
@Component
public class ReportsFacadeImpl implements ReportsFacade {

    @Autowired private ReportCriteriaService reportService;    
    @Autowired private TiwiproPrincipal principal;

    @SuppressWarnings("unchecked")
    @Override
    public List<TenHoursViolation> getTenHourViolations(Integer groupID, Interval interval) {
        ReportCriteria criteria = reportService.getTenHoursDayViolationsCriteria(
                getAccountGroupHierarchy(), groupID, interval, getLocale());
        return criteria.getMainDataset();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<StateMileageByVehicleRoadStatus> getStateMileageByVehicleRoadStatus(Integer groupID, Interval interval, boolean dotOnly) {
        List groupIDList = new ArrayList();
        groupIDList.add(groupID);
        ReportCriteria criteria = reportService.getStateMileageByVehicleRoadStatusReportCriteria(getAccountGroupHierarchy(), groupIDList, interval, getLocale(), getMeasurementType(), dotOnly);
        return criteria.getMainDataset();
    }


    /**
     * Returns the user group hierarchy.
     * @return
     */
    GroupHierarchy getAccountGroupHierarchy() {
    	return principal.getAccountGroupHierarchy();
    }

    /** 
     * Return the measurement type from the user preferences
     * @return Measurement Type
     */
    MeasurementType getMeasurementType() {
        return principal.getUser().getPerson().getMeasurementType();
    }
    
    
    /**
     * Returns the user Locale.
     */
    Locale getLocale() {
        // FIXME temporarily fixed
        return Locale.US;
    }

    
    /**
     * The reportService setter.
     * @param reportService the reportService to set
     */
    void setReportService(ReportCriteriaService reportService) {
        this.reportService = reportService;
    }

}
 
