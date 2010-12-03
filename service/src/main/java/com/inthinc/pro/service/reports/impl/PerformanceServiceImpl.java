package com.inthinc.pro.service.reports.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.reports.performance.model.TenHoursViolation;
import com.inthinc.pro.service.reports.PerformanceService;
import com.inthinc.pro.service.reports.facade.ReportsFacade;

/**
 * PerformanceService implementation class.
 */
@Component
public class PerformanceServiceImpl implements PerformanceService {
    private static Logger logger = Logger.getLogger(PerformanceServiceImpl.class);
    @Autowired private ReportsFacade reportsFacade;
    
    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.PerformanceService#getTenHourViolations(java.lang.Integer)
     */
    @Override
    public Response getTenHourViolations(Integer groupID) {
        Calendar endDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.DAY_OF_MONTH, -ReportsFacade.DAYS_BACK);
        
        return this.getTenHourViolations(groupID, startDate.getTime(), endDate.getTime());
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.PerformanceService#getTenHourViolations(java.lang.Integer, java.util.Date, java.util.Date)
     */
    @Override
    public Response getTenHourViolations(Integer groupID, Date startDate, Date endDate) {
        String method = "10HourViolations Request ";
        Interval interval;

        logger.debug(method+"("+groupID+", "+startDate+", "+endDate+") started");
        // TODO check if we need this check
        if (endDate.before(startDate)) {
            interval = new Interval(endDate.getTime(), startDate.getTime());
        }
        interval = new Interval(startDate.getTime(), endDate.getTime());
        
        logger.debug(method+"calls ReportsFacade.getTenHourViolations()");
        List<TenHoursViolation> violations = null;
        try {
            violations = reportsFacade.getTenHourViolations(groupID, interval);
        } catch (Exception e) {
            logger.error(method+": "+e.toString(), e);
        }
        logger.debug(method+"done.");
        if (violations == null || violations.isEmpty()) {
            return Response.status(Status.NOT_FOUND).build();
        }
        
        return Response.ok(new GenericEntity<List<TenHoursViolation>>(violations) {}).build();
    }


    /**
     * The reportsFacade setter.
     * @param reportsFacade the reportsFacade to set
     */
    void setReportsFacade(ReportsFacade reportsFacade) {
        this.reportsFacade = reportsFacade;
    }

}
