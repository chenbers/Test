package com.inthinc.pro.service.phonecontrol;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.inthinc.pro.model.phone.CellStatusType;
import com.iwi.teenserver.dao.hessian.mcm.IWINotificationType;

/**
 * Services for Cell phone Control.
 */
@Path("/")
@Produces("application/xml")
public interface CellPhoneService {

    /**
     * Send a request to the phone provider to enable or disable the driver's phone 
     * @param driverID The driver ID
     * @param event The event sent by the Note Server
     * @return
     */
    @GET
    @Consumes("application/xml")
    @Path("/eventService/notifyCellPhoneEvent/{driverID}/{event}")
    public Response createEvent(@PathParam("driverID") Integer driverID, @PathParam("event") IWINotificationType event  );
    
    
    /**
     * Service to update the driver's cell phone status.
     * @param phoneId The driver's phone ID
     * @param status The status to update
     * @return 200/OK if succeeded.
     */
    @PUT
    @Consumes("application/xml")
    @Path("/phone/{phoneID}/{status}") 
    public Response update(@PathParam("phoneID") String phoneId, @PathParam("status") CellStatusType status);
    
}
