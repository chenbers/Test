package com.inthinc.pro.service.reports.impl;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.reports.performance.model.DriverHours;
import com.inthinc.pro.reports.performance.model.TenHoursViolation;
import com.inthinc.pro.service.reports.PerformanceService;
import com.inthinc.pro.service.reports.facade.ReportsFacade;
import com.inthinc.pro.util.ReportsUtil;

/**
 * PerformanceService implementation class.
 */
@Component
public class PerformanceServiceImpl extends BaseReportServiceImpl implements PerformanceService {
    private static Logger logger = Logger.getLogger(PerformanceServiceImpl.class);

	/**
     * Default constructor.
     * @param reportsFacade
     * @param reportsUtil
     */
    @Autowired
    public PerformanceServiceImpl(ReportsFacade reportsFacade, ReportsUtil reportsUtil) {
        super(reportsFacade, reportsUtil);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.PerformanceService#getTenHourViolations(java.lang.Integer)
     */
    @Override
    public Response getTenHourViolations(Integer groupID, Locale locale) {
        return this.getTenHourViolations(groupID, null, null, locale);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.PerformanceService#getTenHourViolations(java.lang.Integer, java.util.Date, java.util.Date)
     */
    @Override
    public Response getTenHourViolations(Integer groupID, Date startDate, Date endDate, Locale locale) {
        String method = "Ten Hour Day Violations Request ";
        
        Interval interval = getInterval(startDate, endDate);

        logger.debug(method+"("+groupID+", "+startDate+", "+endDate+") started");
        
        logger.debug(method+"calls ReportsFacade.getTenHourViolations()");
        List<TenHoursViolation> violations = null;
        try {
            violations = reportsFacade.getTenHourViolations(groupID, interval, locale);
        } catch (Exception e) {
            logger.error(e.toString(), e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
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

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.PerformanceService#getDriverHours(java.lang.Integer)
     */
    @Override
    public Response getDriverHours(Integer groupID, Locale locale) {
        return getDriverHours(groupID, null, null, locale);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.PerformanceService#getDriverHours(java.lang.Integer, java.util.Date, java.util.Date)
     */
    @Override
    public Response getDriverHours(Integer groupID, Date startDate, Date endDate, Locale locale) {
        String method = "Driver Hours Request ";
        
        Interval interval = getInterval(startDate, endDate);

        logger.debug(method+"("+groupID+", "+startDate+", "+endDate+") started");
        
        logger.debug(method+"calls ReportsFacade.getDriverHours()");
        List<DriverHours> driverHoursList = null;
        try {
            driverHoursList = reportsFacade.getDriverHours(groupID, interval, locale);
        } catch (Exception e) {
            logger.error(e.toString(), e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
        logger.debug(method+"done.");
        if (driverHoursList == null || driverHoursList.isEmpty()) {
            return Response.status(Status.NOT_FOUND).build();
        }
        
        return Response.ok(new GenericEntity<List<DriverHours>>(driverHoursList) {}).build();
    }

}
