package com.inthinc.pro.service.client;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Client interface for Cellcontrol service endpoint.
 */
@Path("/API")
public interface CellcontrolEndpoint {

    /**
     * Sends a request to disable a phone.
     * 
     * @param cellPhoneNumber
     *            The cell phone number to be disabled.
     */
    @GET
    @Path("/{PHONE-NUMBER}/DISABLE")
    Response disablePhone(@PathParam("PHONE-NUMBER") String cellPhoneNumber);

    /**
     * Sends a request to enable a phone.
     * 
     * @param cellPhoneNumber
     *            The cell phone number to be enabled.
     */
    @GET
    @Path("/{PHONE-NUMBER}/ENABLE")
    Response enablePhone(@PathParam("PHONE-NUMBER") String cellPhoneNumber);
}
