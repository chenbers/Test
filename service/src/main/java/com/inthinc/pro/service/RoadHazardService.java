package com.inthinc.pro.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.inthinc.pro.model.Hazard;

@Path("/")
@Produces({"application/xml","application/json"})
public interface RoadHazardService extends GenericService<Hazard> {

    /**
     * Returns all roadhazards data within 200 mile radius of location for this device's account.
     * @param mcmID the mcmID of the device (used to determine the account)
     * @param latitude
     * @param longitude
     * @return all roadhazards data within 200 mile radius of the location for this device's account.
     */
    @GET
    @Path("/roadhazard/getRH/{mcmID}/{lat}/{lng}")
    public Response getRH(
            @PathParam("mcmID")String mcmID,
            @PathParam("lat")Double latitude,
            @PathParam("lng")Double longitude);

    @GET
    @Path("/roadhazard/getRH/{mcmID}/{lat}/{lng}/{distanceInMeters}")
    public Response getRH(
            @PathParam("mcmID") String mcmID,
            @PathParam("lat") Double latitude,
            @PathParam("lng") Double longitude,
            @PathParam("distanceInMeters") Integer distanceInMeters);
    
    @GET
    @Path("/roadhazard/getRH/{acctID}/{sw_lat}/{sw_lng}/{ne_lat}/{ne_lng}")    
    public Response getRH(
    		@PathParam("acctID") Integer acctID,
    		@PathParam("sw_lat")Double sw_latitude,
            @PathParam("sw_lng")Double sw_longitude,
            @PathParam("ne_lat")Double ne_latitude,
            @PathParam("ne_lng")Double ne_longitude);

    /**
     * Returns Road Hazard Type defaults including values for:
     * type:
     * urgent:
     * radiusMeters:
     * group: 
     * details:
     * shelfLifeSeconds:
     * 
     * @param locale a 2 char String representing the Locale being requested
     * @return Road Hazard Type defaults
     */
    @GET
    @Path("/roadhazard/getTypes/{locale}")
    public Response types(@PathParam("locale")String locale);
}
