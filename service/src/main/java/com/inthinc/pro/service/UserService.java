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
@Produces("application/xml")
@Consumes("application/xml")
public interface UserService extends GenericService<User> {

    @GET
    @Path("/users")
    public Response getAll();

    @GET
    @Path("/user/{userID:[0-9]+}")
    public Response get(@PathParam("userID") Integer userID);

    @GET
    @Path("/user/{userName}")
    public Response get(@PathParam("userName") String userName);

    //This method is specifically for Petrom. They need a way to login the user through their software.
    @GET
    @Path("/login/{userName}/{password}")
    public Response login(@PathParam("userName") String userName, @PathParam("password") String password);
    
    @POST
    @Path("/user")
    public Response create(User user, @Context UriInfo uriInfo);

    @PUT
    @Path("/user")
    public Response update(User user);

    @DELETE
    @Path("/user/{id}")
    public Response delete(@PathParam("id") Integer id);

    @POST
    @Path("/users")
    public Response create(List<User> users, @Context UriInfo uriInfo);

    @PUT
    @Path("/users")
    public Response update(List<User> users);

    @DELETE
    @Path("/users")
    public Response delete(List<Integer> userIDs);

}
