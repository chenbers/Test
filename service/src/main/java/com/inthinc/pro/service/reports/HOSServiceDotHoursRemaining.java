package com.inthinc.pro.service.reports;

import java.util.Locale;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Produces("application/xml")
@Path("")
public interface HOSServiceDotHoursRemaining {
    
    @GET
    @Path("/group/{groupID}/report/hos/dotHoursRemaining")
    @Produces("application/xml")
    Response getDOTHoursRemaining(@PathParam("groupID") Integer groupID,
            @QueryParam("locale") Locale locale);
}
