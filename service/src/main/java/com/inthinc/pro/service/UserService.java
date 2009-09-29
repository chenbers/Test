package com.inthinc.pro.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import com.inthinc.pro.model.User;

import org.springframework.context.annotation.Scope;



@Produces("application/xml")
@Path("/users")
@Scope("request")
public interface UserService {


	@GET
	@Path("/")
	public List<User> getAll();

	@GET
	@Path("/id/{userID}")
	public User get(@PathParam("userID")Integer userID);

	@POST
	@Consumes("application/xml")
	@Path("/add")
	public Integer add(User user);

	@POST
	@Consumes("application/xml")
	@Path("/update")
	public Integer update(User user);

	@GET
	@Path("/delete/{id}")
	public Integer delete(@PathParam("id")Integer id);

	@POST
	@Consumes("application/xml")
	@Path("/add")
	public List<Integer> add(List<User> users);

	@POST
	@Consumes("application/xml")
	@Path("/update")
	public List<Integer> update(List<User> users);
	
	@POST
	@Consumes("application/xml")
	@Path("/delete")
	public List<Integer> delete(List<Integer> userIDs);

}
