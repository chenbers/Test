package com.inthinc.pro.service.reports;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Interface for HOS Reports Services.
 */
@Path("/group/{groupID}/report/hos")
public interface HOSService {

    @GET
    @Path("/10HourViolations/{rangeOfDatesEnum}")
    @Produces("application/xml")
    Response getTenHourViolations(@PathParam("groupID") Integer groupID );
}
