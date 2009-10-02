package com.inthinc.pro.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import com.inthinc.pro.model.Person;

import org.springframework.context.annotation.Scope;



@Produces("application/xml")
@Scope("request")
public interface PersonService {


	@GET
	@Path("/persons")
	public List<Person> getAll();

	@GET
	@Path("/person/{personID}")
	public Person get(@PathParam("personID")Integer personID);

	@POST
	@Consumes("application/xml")
	@Path("/person")
	public Integer add(Person person);

	@PUT
	@Consumes("application/xml")
	@Path("/person")
	public Integer update(Person person);

	@DELETE
	@Path("/person/{id}")
	public Integer delete(@PathParam("id")Integer id);

	@POST
	@Consumes("application/xml")
	@Path("//persons")
	public List<Integer> add(List<Person> persons);

	@PUT
	@Consumes("application/xml")
	@Path("/persons")
	public List<Integer> update(List<Person> persons);
	
	@DELETE
	@Consumes("application/xml")
	@Path("/persons")
	public List<Integer> delete(List<Integer> personIDs);

}
