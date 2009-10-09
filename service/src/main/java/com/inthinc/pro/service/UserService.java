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

import com.inthinc.pro.model.User;


@Path("/")
@Produces("application/xml")
public interface UserService {


	@GET
	@Path("/users")
	public List<User> getAll();

	@GET
	@Path("/user/{userID}")
	public User get(@PathParam("userID")Integer userID);

	@POST
	@Consumes("application/xml")
	@Path("/user")
	public Integer create(User user);

	@PUT
	@Consumes("application/xml")
	@Path("/user")
	public Integer update(User user);

	@DELETE
	@Path("/user/{id}")
	public Integer delete(@PathParam("id")Integer id);

	@POST
	@Consumes("application/xml")
	@Path("/users")
	public List<Integer> create(List<User> users);

	@PUT
	@Consumes("application/xml")
	@Path("/users")
	public List<Integer> update(List<User> users);
	
	@DELETE
	@Consumes("application/xml")
	@Path("/users")
	public List<Integer> delete(List<Integer> userIDs);

}
