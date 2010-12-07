package com.inthinc.pro.service.it;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.ClientResponse;

import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.LastLocation;

import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus;
import com.inthinc.pro.reports.performance.model.TenHoursViolation;
import com.inthinc.pro.service.annotations.DateFormat;


/**
 * Class used for Integration testing only. It helps marshaling the body response
 * in order to inspect it during the assertions.
 */

@Path("/service/api")
@Consumes("application/xml")
public interface ServiceClient {
    String DATE_FORMAT = "yyyyMMdd";

    @GET
    @Path("/accounts")
    public ClientResponse<List<Account>> getAllAccounts();

    @POST
    @Path("/account")
    public ClientResponse<Account> create(Account account);

    @DELETE
    @Path("/account/{accountID}")
    public ClientResponse<Account> delete(@PathParam("accountID")Integer id);

    @GET
    @Path("/devices")
    public ClientResponse<List<Device>> getAllDevices();

    @POST
    @Path("/driver/{driverID}/startMotion")
    public Response processStartMotionEvent(@PathParam("driverID") Integer driverID);

    @POST
    @Path("/driver/{driverID}/stopMotion")
    public Response processStopMotionEvent(@PathParam("driverID") Integer driverID);

    @PUT
    @Path("/phone/{phoneID}/ENABLED") 
    public Response setStatusEnabled(@PathParam("phoneID") String phoneId);

    @GET
    @Path("/driver/{driverID}/trip")
    public ClientResponse<Trip> getLastTrip(@PathParam("driverID") Integer driverID);

    @PUT
    @Path("/phone/{phoneID}/DISABLED") 
    public Response setStatusDisabled(@PathParam("phoneID") String phoneId);

    @GET
    @Path("/group/{groupID}/driverlocations")
    public ClientResponse<List<DriverLocation>> getGroupDriverLocations(@PathParam("groupID") Integer groupID);

    @GET
    @Path("/driver/{driverID}/trips/{date}")
    public ClientResponse<List<Trip>> getLastTrips(
            @PathParam("driverID") Integer driverID, 
            @PathParam("date") String date );

    @GET
    @Path("/driver/{driverID}/trips")
    public ClientResponse<List<Trip>> getLastTrips(@PathParam("driverID") Integer driverID );

    @GET
    @Path("/driver/{driverID}/location")
    public ClientResponse<LastLocation> getLastLocation(@PathParam("driverID") Integer driverID);

    @GET
    @Path("/group/{groupID}/report/performance/10HourViolations")
    @Produces("application/xml")
    public ClientResponse<List<TenHoursViolation>> getTenHourViolations(@PathParam("groupID") Integer groupID); 

    @GET
    @Path("/group/{groupID}/report/IFTA/roadStatus/{startDate}/{endDate}/{dotOnly}")
    @Produces("application/xml")
    public ClientResponse<List<StateMileageByVehicleRoadStatus>> getStateMileageByVehicleRoadStatus(
            @PathParam("groupID") Integer groupID,
            @PathParam("startDate") String startDate,
            @PathParam("endDate") String endDate,
            @PathParam("dotOnly")  @DefaultValue("false") boolean dotOnly ); 

}
