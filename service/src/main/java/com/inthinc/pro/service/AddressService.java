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

import com.inthinc.pro.model.Address;



@Path("/")
@Produces("application/xml")
public interface AddressService {


	@GET
	@Path("/addresses")
	public List<Address> getAll();

	@GET
	@Path("/address/{addressID}")
	public Address get(@PathParam("addressID")Integer addressID);

	@POST
	@Consumes("application/xml")
	@Path("/address")
	public Integer add(Address address);

	@PUT
	@Consumes("application/xml")
	@Path("/address")
	public Integer update(Address address);

	@DELETE
	@Path("/address/{id}")
	public Integer delete(@PathParam("id")Integer id);

	@POST
	@Consumes("application/xml")
	@Path("/addresses")
	public List<Integer> add(List<Address> addresses);

	@PUT
	@Consumes("application/xml")
	@Path("/addresses")
	public List<Integer> update(List<Address> addresses);
	
	@DELETE
	@Consumes("application/xml")
	@Path("/addresses")
	public List<Integer> delete(List<Integer> addressIDs);

}
