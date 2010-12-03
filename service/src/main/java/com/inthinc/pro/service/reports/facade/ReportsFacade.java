package com.inthinc.pro.service.reports.facade;

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

/**
 * Facade to ReportCriteriaService.
 */
@Component
public class ReportsFacade {

    @Autowired
    private ReportCriteriaService reportService;
    
    @SuppressWarnings("unchecked")
    public List<TenHoursViolation> getTenHourViolations(Integer groupID, Interval interval) {
        ReportCriteria criteria = reportService.getTenHoursDayViolationsCriteria(getGroupHierarchy(), groupID, interval, getLocale());
        return criteria.getMainDataset();
    }
    
    @SuppressWarnings("unchecked")
    public List<StateMileageByVehicleRoadStatus> getStateMileageByVehicleRoadStatus(Integer groupID, Interval interval, boolean dotOnly) {
        List groupIDList = new ArrayList();
        groupIDList.add(groupID);
        ReportCriteria criteria = reportService.getStateMileageByVehicleRoadStatusReportCriteria(getGroupHierarchy(), groupIDList, interval, getLocale(), getMeasurementType(), dotOnly);
        return criteria.getMainDataset();
    }

    private MeasurementType getMeasurementType() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Returns the user group hierarchy.
     * @return
     */
    private GroupHierarchy getGroupHierarchy() {
        // TODO Add method body
        return null;
    }

    /**
     * Returns the user Locale.
     * @return Locale
     */
    private Locale getLocale() {
        return Locale.US;
    }
}
