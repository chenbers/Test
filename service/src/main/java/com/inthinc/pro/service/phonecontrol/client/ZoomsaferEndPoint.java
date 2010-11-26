package com.inthinc.pro.service.phonecontrol.client;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Client interface for Zoomsafer service endpoint.
 */
@Path("/api/command/exec")
public interface ZoomsaferEndPoint {

    /**
     * Sends a request to disable a phone.
     * 
     * @param cellPhoneNumber
     *            The cell phone number to be disabled.
     */
    @GET
    @Path("?type=startsafedriving&phone={PHONE-NUMBER}&time={TIMESTAMP}")
    Response disablePhone(@PathParam("PHONE-NUMBER") String cellPhoneNumber, @PathParam("TIMESTAMP")String timestamp);

    /**
     * Sends a request to enable a phone.
     * 
     * @param cellPhoneNumber
     *            The cell phone number to be enabled.
     */
    @GET
    @Path("?type=stopsafedriving&phone={PHONE-NUMBER}&time={TIMESTAMP}")
    Response enablePhone(@PathParam("PHONE-NUMBER") String cellPhoneNumber, @PathParam("TIMESTAMP")String timestamp);
}
