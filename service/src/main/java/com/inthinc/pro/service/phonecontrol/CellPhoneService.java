package com.inthinc.pro.service.phonecontrol;

import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Services for Cell phone Control.
 */
@Path("/")
public interface CellPhoneService {

    /**
     * Process startMotion event received from the Note Server. 
     * A request for disabling to phone is sent to the phone provider
     * @param driverID The driver ID
     * @return
     */

    @POST
    @Path("/driver/{driverID}/startMotion")
    public Response processStartMotionEvent(@PathParam("driverID") Integer driverID);
    
    /**
     * Process stopMotion event received from the Note Server. 
     * A request for enabling to phone is sent to the phone provider
     * @param driverID The driver ID
     * @return
     */

    @POST
    @Path("/driver/{driverID}/stopMotion")
    public Response processStopMotionEvent(@PathParam("driverID") Integer driverID);
    
    
    /**
     * Service to update the driver's cell phone status ENABLED.
     * @param phoneId The driver's phone ID
     * @HTTP HTTP 200 - OK
     */
    @PUT
    @Path("/phone/{phoneID}/ENABLED") 
    public Response setStatusEnabled(@PathParam("phoneID") String phoneId);
    
    /**
     * Service to set the driver's cell phone status DISABLED.
     * @param phoneId The driver's phone ID
     * @HTTP HTTP 200 - OK
     */
    @PUT
    @Path("/phone/{phoneID}/DISABLED")
    public Response setStatusDisabled(@PathParam("phoneID") String phoneId);
}
