package com.inthinc.pro.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import com.inthinc.pro.model.Group;

import org.springframework.context.annotation.Scope;



@Produces("application/xml")
@Path("/groupService")
@Scope("request")
public interface GroupService {


	@GET
	@Path("/groups/{userName}")
	public List<Group> getGroups(@PathParam("userName")String userName);
	
	@GET
	@Path("/group/{groupID}")
	public Group getGroup(@PathParam("groupID")Integer groupID);

}
