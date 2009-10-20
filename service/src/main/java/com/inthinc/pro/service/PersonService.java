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

import com.inthinc.pro.model.Person;



@Path("/")
@Produces("application/xml")
public interface PersonService {


	@GET
	@Path("/persons")
	public Response getAll();

	@GET
	@Path("/person/{personID}")
	public Response get(@PathParam("personID")Integer personID);

	@POST
	@Consumes("application/xml")
	@Path("/person")
	public Response create(Person person, @Context UriInfo uriInfo);

	@PUT
	@Consumes("application/xml")
	@Path("/person")
	public Response update(Person person);

	@DELETE
	@Path("/person/{id}")
	public Response delete(@PathParam("id")Integer id);

	@POST
	@Consumes("application/xml")
	@Path("/persons")
	public Response create(List<Person> persons, @Context UriInfo uriInfo);

	@PUT
	@Consumes("application/xml")
	@Path("/persons")
	public Response update(List<Person> persons);
	
	@DELETE
	@Consumes("application/xml")
	@Path("/persons")
	public Response delete(List<Integer> personIDs);

}
