package com.inthinc.pro.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

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

    @POST
    @Consumes("application/xml")
    @Path("/group")
    public Response create(Group group);

    @PUT
    @Consumes("application/xml")
    @Path("/group")
    public Response update(Group group);

    @DELETE
    @Path("/group/{id}")
    public Response delete(@PathParam("id") Integer id);
}
