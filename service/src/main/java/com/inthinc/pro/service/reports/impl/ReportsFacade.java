package com.inthinc.pro.service.reports.impl;

import java.util.List;
import java.util.Locale;

import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.reports.ReportCriteria;
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
