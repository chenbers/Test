package com.inthinc.pro.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.inthinc.pro.model.Group;

@Path("/")
@Produces("application/xml")
public interface GroupService {

    @GET
    @Path("/groups")
    public Response getAll();

    @GET
    @Path("/group/{groupID}")
    public Response get(@PathParam("groupID") Integer groupID);

    @GET
    @Path("/group/{groupID}/scores/drivers/{numberOfDays}")
    public Response getDriverScores(@PathParam("groupID") Integer groupID, @PathParam("numberOfDays") @DefaultValue("30") Integer numberOfDays);

    // @GET
    // @Path("/group/{groupID}/score/driver/{numberOfDays}")
    // public Response getDriverScore(@PathParam("groupID") Integer groupID, @PathParam("numberOfDays") @DefaultValue("30") Integer numberOfdays);

    @GET
    @Path("/group/{groupID}/scores/vehicles/{numberOfDays}")
    public Response getVehicleScores(@PathParam("groupID") Integer groupID, @PathParam("numberOfDays") @DefaultValue("30") Integer numberOfDays);

    // @GET
    // @Path("/group/{groupID}/score/vehicle/{numberOfDays}")
    // public Response getVehicleScore(@PathParam("groupID") Integer groupID, @PathParam("numberOfDays") @DefaultValue("30") Integer numberOfDays);

    @GET
    @Path("/group/{groupID}/subgroups/trends/driver/{numberOfDays}")
    public Response getSubGroupsDriverTrends(@PathParam("groupID") Integer groupID, @PathParam("numberOfDays") @DefaultValue("30") Integer numberOfDays);

    @GET
    @Path("/group/{groupID}/subgroups/scores/driver/{numberOfDays}")
    public Response getSubGroupsDriverScores(@PathParam("groupID") Integer groupID, @PathParam("numberOfDays") @DefaultValue("30") Integer numberOfDays);
    
    /**
     * Returns the last known location of each driver in a group.
     * @param groupID the group ID
     * @HTTP HTTP 200 - OK if succeeds.
     */
    @GET
    @Path("/group/{groupID}/driverlocations")
    public Response getGroupDriverLocations(@PathParam("groupID") Integer groupID);
   
    @POST
    @Consumes("application/xml")
    @Path("/group")
    public Response create(Group group, @Context UriInfo uriInfo);

    @PUT
    @Consumes("application/xml")
    @Path("/group")
    public Response update(Group group);

    @DELETE
    @Path("/group/{id}")
    public Response delete(@PathParam("id") Integer id);
}
