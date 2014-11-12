package com.inthinc.pro.service;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.inthinc.pro.model.Person;

@Path("/")
@Produces({"application/xml","application/json", "application/fastinfoset"})
public interface PersonService extends GenericService<Person> {

    @GET
    @Path("/persons")
    public Response getAll();

    @GET
    @Path("/person/{personID}")
    public Response get(@PathParam("personID") Integer personID);

    /**
     * Gets person and scoring information for a given person ID.
     * @param personID person ID
     * @param numberOfDays number of days
     * @return a {@link com.inthinc.pro.model.PersonScoresView}
     */
    @GET
    @Path("/person/{personID}/score/{numberOfDays}")
    public Response getPersonAndScores(@PathParam("personID") Integer personID, @PathParam("numberOfDays") @DefaultValue("7") Integer numberOfDays);

    /**
     * Creates a person.
     *
     * @param person person object
     * @param uriInfo context information
     * @return response status
     */
    @POST
    @Path("/person")
    public Response create(Person person, @Context UriInfo uriInfo);
    
    @POST
    @Path("/person/{accountID}")
    public Response create(@PathParam("accountID") Integer id, Person person, @Context UriInfo uriInfo);

    @PUT
    @Path("/person")
    public Response update(Person person);

    @DELETE
    @Path("/person/{id}")
    public Response delete(@PathParam("id") Integer id);

    @POST
    @Path("/persons")
    public Response create(List<Person> persons, @Context UriInfo uriInfo);

    @PUT
    @Path("/persons")
    public Response update(List<Person> persons);

    @DELETE
    @Path("/persons")
    public Response delete(List<Integer> personIDs);

}
