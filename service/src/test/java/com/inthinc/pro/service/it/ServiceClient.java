package com.inthinc.pro.service.it;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.ClientResponse;

import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.reports.ifta.model.MileageByVehicle;
import com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus;
import com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup;
import com.inthinc.pro.reports.ifta.model.StateMileageFuelByVehicle;
import com.inthinc.pro.reports.performance.model.TenHoursViolation;
import com.inthinc.pro.util.GroupList;

/**
 * Class used for Integration testing only. It helps marshaling the body response in order to inspect it during the assertions.
 */

@Path("/service/api")
@Consumes("application/xml")
public interface ServiceClient {
    String SIMPLE_DATE_FORMAT = "yyyyMMdd";

    // --------------------------------------------------------------------------------
    // Account services
    @GET
    @Path("/accounts")
    public ClientResponse<List<Account>> getAllAccounts();

    @POST
    @Path("/account")
    public ClientResponse<Account> create(Account account);

    @DELETE
    @Path("/account/{accountID}")
    public ClientResponse<Account> delete(@PathParam("accountID") Integer id);

    @GET
    @Path("/devices")
    public ClientResponse<List<Device>> getAllDevices();

    @GET
    @Path("/groups")
    public ClientResponse<List<Group>> getAllGroups();
    // --------------------------------------------------------------------------------
    // CellControl/ZoomSafer services
    @POST
    @Path("/driver/{driverID}/startMotion")
    public Response processStartMotionEvent(@PathParam("driverID") Integer driverID);

    @POST
    @Path("/driver/{driverID}/stopMotion")
    public Response processStopMotionEvent(@PathParam("driverID") Integer driverID);

    @PUT
    @Path("/phone/{phoneID}/ENABLED")
    public Response setStatusEnabled(@PathParam("phoneID") String phoneId);

    @PUT
    @Path("/phone/{phoneID}/DISABLED")
    public Response setStatusDisabled(@PathParam("phoneID") String phoneId);

    // ----------------------------------------------------------------------------------
    // Driver/Group web-services
    @GET
    @Path("/group/{groupID}/driverlocations")
    public ClientResponse<List<DriverLocation>> getGroupDriverLocations(@PathParam("groupID") Integer groupID);

    @GET
    @Path("/group/{groupID}/score")
    public ClientResponse<List<DriverVehicleScoreWrapper>> getDriverScores(@PathParam("groupID") Integer groupID, @PathParam("numberOfDays") Integer numberOfDays);

    @GET
    @Path("/driver/{driverID}/trip")
    public ClientResponse<Trip> getLastTrip(@PathParam("driverID") Integer driverID);

    @GET
    @Path("/driver/{driverID}/trips/{date}")
    public ClientResponse<List<Trip>> getLastTrips(@PathParam("driverID") Integer driverID, @PathParam("date") String date);

    @GET
    @Path("/driver/{driverID}/trips")
    public ClientResponse<List<Trip>> getLastTrips(@PathParam("driverID") Integer driverID);

    @GET
    @Path("/driver/{driverID}/location")
    public ClientResponse<LastLocation> getLastLocation(@PathParam("driverID") Integer driverID);

    // ----------------------------------------------------------------------------------
    // Vehicle/Group web-services

    @GET
    @Path("/vehicle/{vehicleID}/trips/{date}")
    public ClientResponse<List<Trip>> getLastVehicleTrips(@PathParam("vehicleID") Integer vehicleID, @PathParam("date") String date);

    @GET
    @Path("/vehicle/{vehicleID}/trips")
    public ClientResponse<List<Trip>> getLastVehicleTrips(@PathParam("vehicleID") Integer vehicleID);

    @GET
    @Path("/vehicle/{vehicleID}/location")
    public ClientResponse<LastLocation> getLastVehicleLocation(@PathParam("vehicleID") Integer vehicleID);
    // --------------------------------------------------------------------------------
    // Performance Reports web-services
    @GET
    @Path("/group/{groupID}/report/performance/10HourViolations")
    @Produces("application/xml")
    public ClientResponse<List<TenHoursViolation>> getTenHourViolations(@PathParam("groupID") Integer groupID);

    // --------------------------------------------------------------------------------
    // State Mileage by Vehicle Road Status
    @GET
    @Path("/group/{groupID}/report/ifta/roadStatus/{startDate}/{endDate}")
    @Produces("application/xml")
    public ClientResponse<List<StateMileageByVehicleRoadStatus>> getStateMileageByVehicleRoadStatusWithDates(@PathParam("groupID") Integer groupID, @PathParam("startDate") String startDate,
            @PathParam("endDate") String endDate);

    
    @POST
    @Path("/groups/report/ifta/roadStatus/{startDate}/{endDate}")
    @Produces("application/xml")
    @Consumes("application/xml")
    public ClientResponse<List<StateMileageByVehicleRoadStatus>> getStateMileageByVehicleRoadStatusWithDatesMultiGroup(GroupList groupList, @PathParam("startDate") String startDate,
            @PathParam("endDate") String endDate);
    
    // -----------------------------------------------------------------------------------------------------------------
    // State Mileage by Vehicle State Comparison
    @GET
    @Path("/group/{groupID}/report/ifta/stateComparison/{startDate}/{endDate}")
    @Produces("application/xml")
    public ClientResponse<List<StateMileageCompareByGroup>> getStateMileageByVehicleStateComparisonWithDates(@PathParam("groupID") Integer groupID, @PathParam("startDate") String startDate,
            @PathParam("endDate") String endDate);

    
    // --------------------------------------------------------------------------------
    // State Mileage by Vehicle
    @GET
    @Path("/group/{groupID}/report/ifta/stateMileage")
    @Produces("application/xml")
    public ClientResponse<List<MileageByVehicle>> getStateMileageByVehicleDefaults(@PathParam("groupID") Integer groupID);

    @GET
    @Path("/group/{groupID}/report/ifta/stateMileage/{startDate}/{endDate}")
    @Produces("application/xml")
    public ClientResponse<List<MileageByVehicle>> getStateMileageByVehicleWithDates(@PathParam("groupID") Integer groupID, @PathParam("startDate") String startDate,
            @PathParam("endDate") String endDate);

    @GET
    @Path("/group/{groupID}/report/ifta/stateMileage/iftaOnly")
    @Produces("application/xml")
    public ClientResponse<List<MileageByVehicle>> getStateMileageByVehicleWithIfta(@PathParam("groupID") Integer groupID);

    @GET
    @Path("/group/{groupID}/report/ifta/stateMileage/iftaOnly/{startDate}/{endDate}")
    @Produces("application/xml")
    public ClientResponse<List<MileageByVehicle>> getStateMileageByVehicleWithIftaAndDates(@PathParam("groupID") Integer groupID, @PathParam("startDate") String startDate,
            @PathParam("endDate") String endDate);

    // --------------------------------------------------------------------------------
    // State Mileage by Month
    @GET
    @Path("/group/{groupID}/report/ifta/monthMileage/iftaOnly/{startDate}/{endDate}")
    public ClientResponse<List<MileageByVehicle>> getStateMileageByMonthWithIftaAndDates(@PathParam("groupID") Integer groupID, 
            @PathParam("startDate") String startDate, @PathParam("endDate") String endDate,
            @QueryParam("locale") String locale, @QueryParam("measurementType") String measurementType);
    
    @GET
    @Path("/group/{groupID}/report/ifta/monthMileage/iftaOnly")
    public ClientResponse<List<MileageByVehicle>> getStateMileageByMonthWithIfta(@PathParam("groupID") Integer groupID,
            @QueryParam("locale") String locale, @QueryParam("measurementType") String measurementType);
    
    @GET
    @Path("/group/{groupID}/report/ifta/monthMileage")
    public ClientResponse<List<MileageByVehicle>> getStateMileageByMonthDefaults(@PathParam("groupID") Integer groupID,
            @QueryParam("locale") String locale, @QueryParam("measurementType") String measurementType);
    
    @GET
    @Path("/group/{groupID}/report/ifta/monthMileage/{startDate}/{endDate}")
    public ClientResponse<List<MileageByVehicle>> getStateMileageByMonthWithDates(@PathParam("groupID") Integer groupID, 
            @PathParam("startDate") String startDate, @PathParam("endDate") String endDate,
            @QueryParam("locale") String locale, @QueryParam("measurementType") String measurementType);
    
    @POST
    @Path("/groups/report/ifta/monthMileage/iftaOnly/{startDate}/{endDate}")
    @Consumes("application/xml")
    public ClientResponse<List<MileageByVehicle>> getStateMileageByMonthWithIftaAndDatesMultiGroup(GroupList groupList, 
            @PathParam("startDate") String startDate, @PathParam("endDate") String endDate,
            @QueryParam("locale") String locale, @QueryParam("measurementType") String measurementType);

    @POST
    @Path("/groups/report/ifta/monthMileage/iftaOnly")
    @Consumes("application/xml")
    public ClientResponse<List<MileageByVehicle>> getStateMileageByMonthWithIftaMultiGroup(GroupList groupList,
            @QueryParam("locale") String locale, @QueryParam("measurementType") String measurementType);

    @POST
    @Path("/groups/report/ifta/monthMileage")
    @Consumes("application/xml")
    public ClientResponse<List<MileageByVehicle>> getStateMileageByMonthDefaultsMultiGroup(GroupList groupList,
            @QueryParam("locale") String locale, @QueryParam("measurementType") String measurementType);

    @POST
    @Path("/groups/report/ifta/monthMileage/{startDate}/{endDate}")
    @Consumes("application/xml")
    public ClientResponse<List<MileageByVehicle>> getStateMileageByMonthWithDatesMultiGroup(GroupList groupList, 
            @PathParam("startDate") String startDate, @PathParam("endDate") String endDate,
            @QueryParam("locale") String locale, @QueryParam("measurementType") String measurementType);

    // --------------------------------------------------------------------------------
    // Mileage by Vehicle
    @GET
    @Path("/group/{groupID}/report/ifta/mileage/iftaOnly/{startDate}/{endDate}")
    @Produces("application/xml")
    public ClientResponse<List<MileageByVehicle>> getMileageByVehicleWithIftaAndDates(@PathParam("groupID") Integer groupID, @PathParam("startDate") String startDate,
            @PathParam("endDate") String endDate);

    @GET
    @Path("/group/{groupID}/report/ifta/mileage/iftaOnly")
    @Produces("application/xml")
    public ClientResponse<List<MileageByVehicle>> getMileageByVehicleWithIfta(@PathParam("groupID") Integer groupID);

    @GET
    @Path("/group/{groupID}/report/ifta/mileage/{startDate}/{endDate}")
    @Produces("application/xml")
    public ClientResponse<List<MileageByVehicle>> getMileageByVehicleWithDates(@PathParam("groupID") Integer groupID, @PathParam("startDate") String startDate, @PathParam("endDate") String endDate);

    @GET
    @Path("/group/{groupID}/report/ifta/mileage")
    @Produces("application/xml")
    public ClientResponse<List<MileageByVehicle>> getMileageByVehicleDefaults(@PathParam("groupID") Integer groupID);

    @POST
    @Path("/groups/report/ifta/mileage/iftaOnly/{startDate}/{endDate}")
    @Produces("application/xml")
    public ClientResponse<List<MileageByVehicle>> getMileageByVehicleWithIftaAndDatesMultiGroup(GroupList groupsList, @PathParam("startDate") String startDate,
            @PathParam("endDate") String endDate);
    
    @POST
    @Path("/groups/report/ifta/mileage/iftaOnly")
    @Produces("application/xml")
    public ClientResponse<List<MileageByVehicle>> getMileageByVehicleWithIftaMultiGroup(GroupList groupList);
    
    @POST
    @Path("/groups/report/ifta/mileage/{startDate}/{endDate}")
    @Produces("application/xml")
    public ClientResponse<List<MileageByVehicle>> getMileageByVehicleWithDatesMultiGroup(GroupList groupList, @PathParam("startDate") String startDate, @PathParam("endDate") String endDate);
    
    @POST
    @Path("/groups/report/ifta/mileage")
    @Produces("application/xml")
    public ClientResponse<List<MileageByVehicle>> getMileageByVehicleDefaultsMultiGroup(GroupList groupList);
    
    // --------------------------------------------------------------------------------
    
      // --------------------------------------------------------------------------------
    // State Mileage Fuel by Vehicle
    @GET
    @Path("/group/{groupID}/report/ifta/fuelConsumption/iftaOnly/{startDate}/{endDate}")
    @Produces("application/xml")
    public ClientResponse<List<StateMileageFuelByVehicle>> getStateMileageFuelByVehicleWithIftaAndDates(@PathParam("groupID") Integer groupID, @PathParam("startDate") String startDate,
            @PathParam("endDate") String endDate);

    @GET
    @Path("/group/{groupID}/report/ifta/fuelConsumption/iftaOnly")
    @Produces("application/xml")
    public ClientResponse<List<StateMileageFuelByVehicle>> getStateMileageFuelByVehicleWithIfta(@PathParam("groupID") Integer groupID);

    @GET
    @Path("/group/{groupID}/report/ifta/fuelConsumption/{startDate}/{endDate}")
    @Produces("application/xml")
    public ClientResponse<List<StateMileageFuelByVehicle>> getStateMileageFuelByVehicleWithDates(@PathParam("groupID") Integer groupID, @PathParam("startDate") String startDate,
            @PathParam("endDate") String endDate);

    @GET
    @Path("/group/{groupID}/report/ifta/fuelConsumption")
    @Produces("application/xml")
    public ClientResponse<List<StateMileageFuelByVehicle>> getStateMileageFuelByVehicleDefaults(@PathParam("groupID") Integer groupID);

    @POST
    @Path("/groups/report/ifta/fuelConsumption/iftaOnly/{startDate}/{endDate}")
    @Produces("application/xml")
    public ClientResponse<List<StateMileageFuelByVehicle>> getStateMileageFuelByVehicleWithIftaAndDatesMultiGroup(GroupList groupList, @PathParam("startDate") String startDate,
            @PathParam("endDate") String endDate);

    @POST
    @Path("/groups/report/ifta/fuelConsumption/iftaOnly")
    @Produces("application/xml")
    public ClientResponse<List<StateMileageFuelByVehicle>> getStateMileageFuelByVehicleWithIftaMultiGroup(GroupList groupList);

    @POST
    @Path("/groups/report/ifta/fuelConsumption/{startDate}/{endDate}")
    @Produces("application/xml")
    public ClientResponse<List<StateMileageFuelByVehicle>> getStateMileageFuelByVehicleWithDatesMultiGroup(GroupList groupList, @PathParam("startDate") String startDate,
            @PathParam("endDate") String endDate);

    @POST
    @Path("/groups/report/ifta/fuelConsumption")
    @Produces("application/xml")
    public ClientResponse<List<StateMileageFuelByVehicle>> getStateMileageFuelByVehicleDefaultsMultiGroup(GroupList groupList);

    // --------------------------------------------------------------------------------
}
