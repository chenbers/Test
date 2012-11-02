package com.inthinc.pro.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.inthinc.pro.model.Hazard;

@Path("/")
@Produces({"application/xml","application/json"})
public interface RoadHazardService extends GenericService<Hazard> {

    @GET
    @Path("/roadhazard/getRH/{mcmID}/{lat}/{lng}")
    //@Produces(MediaType.APPLICATION_JSON)
    public Response getRH(
            @PathParam("mcmID")String mcmID,
            @PathParam("lat")Double latitude,
            @PathParam("lng")Double longitude);
}