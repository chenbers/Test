package com.inthinc.pro.service;

import java.util.List;

import javax.ws.rs.GET;
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
	public List<Group> getGroups();
	
	@GET
	@Path("/id/{groupID}")
	public Group getGroup(@PathParam("groupID")Integer groupID);

}
