package com.inthinc.pro.service.reports;

import java.util.Date;
import java.util.Locale;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.inthinc.pro.service.annotations.DateFormat;

/**
 * Interface for HOS Reports Services.
 */
@Path("/group/{groupID}/report/performance")
@Produces("application/xml")
public interface PerformanceService {
    String DATE_FORMAT = "yyyyMMdd";

    /**
     * Service for 10 Hours Violations Report with default Interval.
     * @param groupID the Group ID
     * @returnWrapped java.util.List<com.inthinc.pro.reports.performance.model.TenHoursViolation> the list of violations
     * @HTTP HTTP 200 - OK if any violations found
     * @HTTP HTTP 404 - NOT FOUND if no violations found
     */
    @GET
    @Path("/10HourViolations")
    Response getTenHourViolations(@PathParam("groupID") Integer groupID, 
            @QueryParam("locale") Locale locale);

    /**
     * Service for 10 Hours Violations Report with an explicite Interval.
     * @param groupID the Group ID
     * @param startDate the start date in format {@value com.inthinc.pro.service.reports.PerformanceService#DATE_FORMAT}
     * @param endDate the end date in format {@value com.inthinc.pro.service.reports.PerformanceService#DATE_FORMAT}
     * @returnWrapped java.util.List<com.inthinc.pro.reports.performance.model.TenHoursViolation> the list of violations
     * @HTTP HTTP 200 - OK if any violations found
     * @HTTP HTTP 404 - NOT FOUND if no violations found
     */
    @GET
    @Path("/10HourViolations/{startDate}/{endDate}")
    Response getTenHourViolations(@PathParam("groupID") Integer groupID,
            @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate, 
            @QueryParam("locale") Locale locale);
    
    /*------------------------------------------------------------------------------------------------------*/
    /**
     * Service for Driver Hours Report with default Interval.
     * @param groupID the Group ID
     * @returnWrapped java.util.List<com.inthinc.pro.reports.performance.model.> the list of driver's hours
     * @HTTP HTTP 200 - OK if any record found
     * @HTTP HTTP 404 - NOT FOUND if no record found
     */
    @GET
    @Path("/driverHours")
    Response getDriverHours(@PathParam("groupID") Integer groupID, 
            @QueryParam("locale") Locale locale);

    /**
     * Service for Driver Hours Report with an explicite Interval.
     * @param groupID the Group ID
     * @param startDate the start date in format "YYYYMMDD"
     * @param endDate the end date in format "YYYYMMDD"
     * @returnWrapped java.util.List<com.inthinc.pro.reports.performance.model.DriverHours> the list of violations
     * @HTTP HTTP 200 - OK if any record found
     * @HTTP HTTP 404 - NOT FOUND if no record found
     */
    @GET
    @Path("/driverHours/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getDriverHours(@PathParam("groupID") Integer groupID,
            @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate, 
            @QueryParam("locale") Locale locale);
}
