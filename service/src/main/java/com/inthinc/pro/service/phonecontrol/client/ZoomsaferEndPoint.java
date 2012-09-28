package com.inthinc.pro.service.phonecontrol.client;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * Client interface for Zoomsafer service endpoint.
 */
@Path("/api/command/exec")
public interface ZoomsaferEndPoint {

    public static final String DISABLE_PHONE_EVENT_TYPE = "startsafedriving";
    public static final String ENABLE_PHONE_EVENT_TYPE = "stopsafedriving";
    
    /**
     * Sends a request to disable a phone.
     * 
     * @param type
     *            Has to be "startsafedriving".
     * 
     * @param cellPhoneNumber
     *            The cell phone number to be disabled.
     * 
     * @param timestamp
     *            The time the event was received, following the pattern "yyyy.MM.dd HH:mm:ss.SSS Z".
     */
    @GET
    Response disablePhone(@QueryParam("type") @DefaultValue("startsafedriving") String type, @QueryParam("phone") String cellPhoneNumber, @QueryParam("time") String timestamp);

    /**
     * Sends a request to enable a phone.
     * 
     * @param type
     *            Has to be "stopsafedriving".
     * 
     * @param cellPhoneNumber
     *            The cell phone number to be enabled.
     * 
     * @param timestamp
     *            The time the event was received, following the pattern "yyyy.MM.dd HH:mm:ss.SSS Z".
     */
    @GET
    Response enablePhone(@QueryParam("type") @DefaultValue("stopsafedriving") String type, @QueryParam("phone") String cellPhoneNumber, @QueryParam("time") String timestamp);
}
