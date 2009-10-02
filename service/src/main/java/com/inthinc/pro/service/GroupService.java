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

import com.inthinc.pro.model.Group;



@Path("/")
@Produces("application/xml")
public interface GroupService {


	@GET
	@Path("/groups")
	public List<Group> getAll();
	
	@GET
	@Path("/group/{groupID}")
	public Group get(@PathParam("groupID")Integer groupID);

	@POST
	@Consumes("application/xml")
	@Path("/group")
	public Integer add(Group group);

	@PUT
	@Consumes("application/xml")
	@Path("/group")
	public Integer update(Group group);

	@DELETE
	@Path("/group/{id}")
	public Integer delete(@PathParam("id")Integer id);
}
