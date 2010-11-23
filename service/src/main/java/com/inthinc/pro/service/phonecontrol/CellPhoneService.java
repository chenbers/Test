package com.inthinc.pro.service.phonecontrol;

import javax.ws.rs.Consumes;
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
