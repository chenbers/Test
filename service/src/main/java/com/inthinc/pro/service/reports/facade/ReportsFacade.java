package com.inthinc.pro.service.reports.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus;
import com.inthinc.pro.reports.performance.model.TenHoursViolation;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.service.adapters.GroupDAOAdapter;

/**
 * Facade to ReportCriteriaService.
 */
@Component
public class ReportsFacade {
    private GroupHierarchy groupHierarchy;

    @Autowired private ReportCriteriaService reportService;    
    @Autowired private GroupDAOAdapter groupAdapter;
    
    @SuppressWarnings("unchecked")
    public List<TenHoursViolation> getTenHourViolations(Integer groupID, Interval interval) {
        ReportCriteria criteria = reportService.getTenHoursDayViolationsCriteria(
                getGroupHierarchy(), groupID, interval, getLocale());
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
    GroupHierarchy getGroupHierarchy() {
        if (groupHierarchy == null) {
            List<Group> accountGroupList = groupAdapter.getAll();
            groupHierarchy = new GroupHierarchy(accountGroupList);
        }
        return groupHierarchy;
    }

    /**
     * Returns the user Locale.
     * @return Locale
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

    /**
     * The groupAdapter setter.
     * @param groupAdapter the groupAdapter to set
     */
    void setGroupAdapter(GroupDAOAdapter groupAdapter) {
        this.groupAdapter = groupAdapter;
    }
}
