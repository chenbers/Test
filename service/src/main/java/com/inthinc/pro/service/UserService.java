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

import com.inthinc.pro.model.User;

/**
 * This service is for maintaining the roles and credentials for users that can log into the system. 
 * Note, these records refer to Person objects.
 *  This service operates on XML payloads in the form: 
 * <pre>
 * {@code
 * <user> 
 * <modified>2009-08-20T19:03:42-07:00</modified> 
 * <groupID>1</groupID> 
 * <password>GX8gVUIqxDeAdB2QOmNmEaVuce8KjM6J6yOx8T9NiSU6HEjmAbVtP8mkp/+EJPbX</password> 
 * <person> 
 * <modified>2009-08-20T19:03:40-07:00</modified> 
 * <acctID>1</acctID> 
 * <addressID>1</addressID> 
 * <dept>drivers</dept> 
 * <dob>1989-07-10T17:00:00-07:00</dob> 
 * <empid>002</empid> 
 * <first>Speed</first> 
 * <gender>MALE</gender> 
 * <height>68</height> 
 *  <last>Racer</last> 
 *  <measurementType>ENGLISH</measurementType> 
 *  <personID>1</personID> 
 *  <priEmail>sracer@iwiglobal.com</priEmail> 
 *  <priPhone>8583859099</priPhone> 
 *  <secPhone>8583859099</secPhone> 
 *  <status>ACTIVE</status> 
 *  <timeZone> 
 *  <ID>US/Pacific</ID> 
 *  <rawOffset>-28800000</rawOffset> 
 *  </timeZone> 
 *  <title>#1 driver</title> 
 *  <weight>140</weight> 
 *  </person> 
 *  <personID>1</personID> 
 *  <role> 
 *  <name>superUser</name> 
 *  <roleID>5</roleID> 
 *  </role> 
 *  <status>ACTIVE</status> 
 *  <userID>1</userID> 
 *  <username>speedracer</username> 
 * </user> 
 * }
 * </pre>
 * @author vleduc
 *
 */
@Path("/")
@Produces("application/xml")
@Consumes("application/xml")
public interface UserService extends GenericService<User> {

    /**
     * Get All Users (bulk)
     * @return list of users in the form: 
     * <pre>
     *{@code
     * <collection>
     * <user>
     * <modified>2010-06-01T17:27:42-06:00</modified>
     * <groupID>53</groupID>
     * <password>
     * W1bf8nnwwKg8JMcbBNpOPe4drnJjWoPzT63p4X76gbvq/j0vTN1JeMXscx+80QPr
     * </password>
     * <personID>10041</personID>
     * <roles>24</roles>
     * <roles>23</roles>
     * <status>ACTIVE</status>
     * <userID>1090</userID>
     * <username>travelguard</username>
     * </user>
     * etc...
     * </collection>
     * }
 	 * </pre>
     */
    @GET
    @Path("/users")
    public Response getAll();

    /**
	 * Get User
	 * @param userID The user ID digit (:[0-9]+} to be get.
     * @returnWrapped User Returns a user found by id.
	 */
    @GET
    @Path("/user/{userID:[0-9]+}")
    public Response get(@PathParam("userID") Integer userID);

	/**
	 * Get User
	 * @param userName The userName of the user to be get.
     * @returnWrapped User Returns a user found by userName or 404 Not Found response status.
	 */
    @GET
    @Path("/user/{userName}")
    public Response get(@PathParam("userName") String userName);

	/**
	 * This method is specifically for Petrom. 
	 * They need a way to login the user through their software.
	 * @param userName login user name.
	 * @param password login password.
     * @returnWrapped User Returns the authorised user or 403 Forbidden response status.	 
     * */
    @GET
    @Path("/login/{userName}/{password}")
    public Response login(@PathParam("userName") String userName, @PathParam("password") String password);
    
	/**
	 * Create a new entity record from the included request body.
	 * @param user The request body contains XML for the user to be created. 
	 * @HTTP HTTP 201 - CREATED if the record was successfully created. 
     * @returnWrapped User Returns the created user.
	 */
    @POST
    @Path("/user")
    public Response create(User user, @Context UriInfo uriInfo);

	/**
	 * Update user.
	 * @param user The request body contains XML for the user entity to be updated. 
	 * @HTTP HTTP 200 - OK if the record was successfully updated.
	 */
    @PUT
    @Path("/user")
    public Response update(User user);

	/**
	 * Delete the user having the specified ID.
	 * @param id  The request body contains XML for the user id to be deleted.
	 * @HTTP HTTP 200 - OK if the record was successfully deleted.
	 */
    @DELETE
    @Path("/user/{id}")
    public Response delete(@PathParam("id") Integer id);

    /**
     * Create Users (bulk).
     * @param users The request body contains XML for a list of user entities to be created.
     * @return Response Returns a list of integers indicating whether each record was successfully created.
     */
    @POST
    @Path("/users")
    public Response create(List<User> users, @Context UriInfo uriInfo);

    /**
     * Update users (bulk)
     * @param users The request body contains XML for a list of user entities to be updated.
     * @return Returns a list of integers indicating whether each record was successfully updated.
     */
    @PUT
    @Path("/users")
    public Response update(List<User> users);

    /**
     * Delete users (bulk)
     * Delete the users specified by a list of IDs.
     * @param userIDs The request body contains XML for the user IDS to be deleted.
     * @return Returns a list of integers indicating whether each record was successfully deleted. 
     */
    @DELETE
    @Path("/users")
    public Response delete(List<Integer> userIDs);

}
