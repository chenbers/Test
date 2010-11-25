package com.inthinc.pro.service.phonecontrol;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.inthinc.pro.model.phone.CellStatusType;

/**
 * Services for Cell phone Control.
 */
@Path("/")
@Produces("application/xml")
public interface CellPhoneService {

    /**
     * Process startMotion event received from the Note Server. 
     * A request for disabling to phone is sent to the phone provider
     * @param driverID The driver ID
     * @return
     */

    @POST
    @Consumes("application/xml")
    @Path("/driver/{driverID}/startMotion")
    public Response processStartMotionEvent(@PathParam("driverID") Integer driverID);
    
    /**
     * Process stopMotion event received from the Note Server. 
     * A request for enabling to phone is sent to the phone provider
     * @param driverID The driver ID
     * @return
     */

    @POST
    @Consumes("application/xml")
    @Path("/driver/{driverID}/stopMotion")
    public Response processStopMotionEvent(@PathParam("driverID") Integer driverID);
    
    
    /**
     * Service to update the driver's cell phone status.
     * @param phoneId The driver's phone ID
     * @param status The status to update
     * @HTTP HTTP 200 - OK
     */
    @PUT
    @Consumes("application/xml")
    @Path("/phone/{phoneID}/{status}") 
    public Response updateStatus(@PathParam("phoneID") String phoneId, @PathParam("status") CellStatusType status);
    
}
