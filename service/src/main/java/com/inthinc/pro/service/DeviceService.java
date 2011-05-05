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

import com.inthinc.pro.model.Device;



@Path("/")
@Produces({"application/xml","application/json", "application/fastinfoset"})
public interface DeviceService extends GenericService<Device> {

	@GET
	@Path("/devices")
	public Response getAll();

	//TODO findBy group.......
	
	@GET
	@Path("/device/{deviceID}")
	public Response get(@PathParam("deviceID")Integer deviceID);

	@GET
	@Path("/device/imei/{imei}")
	public Response findByIMEI(@PathParam("imei")String imei);

	@GET
	@Path("/device/serialnum/{serialnum}")
	public Response findBySerialNum(@PathParam("serialnum")String serialnum);
	
    @POST
    @Consumes("application/xml")
    @Path("/device")
    public Response create(Device device, @Context UriInfo uriInfo);

    @PUT
    @Consumes("application/xml")
    @Path("/device")
    public Response update(Device device);

    @DELETE
    @Path("/device/{id}")
    public Response delete(@PathParam("id") Integer id);

    @POST
    @Consumes("application/xml")
    @Path("/devices")
    public Response create(List<Device> devices, @Context UriInfo uriInfo);

    @PUT
    @Consumes("application/xml")
    @Path("/devices")
    public Response update(List<Device> devices);

    @DELETE
    @Consumes("application/xml")
    @Path("/devices")
    public Response delete(List<Integer> deviceIDs);
	
//TODO do we disallow delete and add?
//TODO do we allow update on limited fields like ecall?
}
