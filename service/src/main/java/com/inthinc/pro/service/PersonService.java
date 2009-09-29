package com.inthinc.pro.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import com.inthinc.pro.model.Person;

import org.springframework.context.annotation.Scope;



@Produces("application/xml")
@Path("/persons")
@Scope("request")
public interface PersonService {


	@GET
	@Path("/")
	public List<Person> getAll();

	@GET
	@Path("/id/{personID}")
	public Person get(@PathParam("personID")Integer personID);

	@POST
	@Consumes("application/xml")
	@Path("/add")
	public Integer add(Person person);

	@POST
	@Consumes("application/xml")
	@Path("/update")
	public Integer update(Person person);

	@GET
	@Path("/delete/{id}")
	public Integer delete(@PathParam("id")Integer id);

	@POST
	@Consumes("application/xml")
	@Path("/add")
	public List<Integer> add(List<Person> persons);

	@POST
	@Consumes("application/xml")
	@Path("/update")
	public List<Integer> update(List<Person> persons);
	
	@POST
	@Consumes("application/xml")
	@Path("/delete")
	public List<Integer> delete(List<Integer> personIDs);

}
