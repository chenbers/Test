package com.inthinc.pro.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.inthinc.pro.model.User;

@Path("/")
public interface UserService {

    @GET
    @Path("/users")
    @Produces("application/xml")
    public Response getAll();

    @GET
    @Path("/user/{userID:[0-9]+}")
    @Produces("application/xml")
    public Response get(@PathParam("userID") Integer userID);

    @GET
    @Path("/user/{userName:[A-Za-z]+}")
    @Produces("application/xml")
    public Response get(@PathParam("userName") String userName);

    @POST
    @Consumes("application/xml")
    @Path("/user")
    public Response create(User user, @Context UriInfo uriInfo);

    @PUT
    @Consumes("application/xml")
    @Path("/user")
    public Response update(User user);

    @DELETE
    @Path("/user/{id}")
    public Response delete(@PathParam("id") Integer id);

    @POST
    @Consumes("application/xml")
    @Path("/users")
    public Response create(List<User> users, @Context UriInfo uriInfo);

    @PUT
    @Consumes("application/xml")
    @Path("/users")
    public Response update(List<User> users);

    @DELETE
    @Consumes("application/xml")
    @Path("/users")
    public Response delete(List<Integer> userIDs);

}
