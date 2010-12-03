package com.inthinc.pro.service.reports;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.inthinc.pro.service.annotations.DateFormat;

/**
 * Interface for HOS Reports Services.
 */
@Path("/group/{groupID}/report/performance")
public interface PerformanceService {
    String DATE_FORMAT = "yyyyMMdd";

    /**
     * Service for 10 Hours Violations Report with default Interval.
     * @param groupID the Group ID
     * @returnWrapped List<TenHoursViolation> the list of violations
     * @HTTP HTTP 200 - OK if any violations found
     * @HTTP HTTP 404 - NOT FOUND if no violations found 
     */
    @GET
    @Path("/10HourViolations")
    @Produces("application/xml")
    Response getTenHourViolations(@PathParam("groupID") Integer groupID); 

    /**
     * Service for 10 Hours Violations Report with an explicite Interval.
     * @param groupID the Group ID
     * @param startDate the start date 
     * @param endDate the end date
     * @returnWrapped List<TenHoursViolation> the list of violations
     * @HTTP HTTP 200 - OK if any violations found
     * @HTTP HTTP 404 - NOT FOUND if no violations found 
     */
    @GET
    @Path("/10HourViolations/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getTenHourViolations(@PathParam("groupID") Integer groupID, 
            @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate, 
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate);
}
