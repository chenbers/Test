package com.inthinc.pro.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import com.inthinc.pro.model.Group;

import org.springframework.context.annotation.Scope;



@Produces("application/xml")
@Path("/groups")
@Scope("request")
public interface GroupService {


	@GET
	@Path("/")
	public List<Group> getAll();
	
	@GET
	@Path("/id/{groupID}")
	public Group get(@PathParam("groupID")Integer groupID);

	@POST
	@Consumes("application/xml")
	@Path("/add")
	public Integer add(Group group);

	@POST
	@Consumes("application/xml")
	@Path("/update")
	public Integer update(Group group);

	@GET
	@Path("/delete/{id}")
	public Integer delete(@PathParam("id")Integer id);
}
