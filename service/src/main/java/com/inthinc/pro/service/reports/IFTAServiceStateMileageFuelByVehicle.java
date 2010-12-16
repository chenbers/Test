package com.inthinc.pro.service.reports;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.inthinc.pro.service.annotations.DateFormat;
import com.inthinc.pro.util.GroupList;

/**
 * Interface for IFTA/DOT Reports Services.
 */
@Produces("application/xml")
@Path("")
public interface IFTAServiceStateMileageFuelByVehicle {
    String DATE_FORMAT = "yyyyMMdd";

    /**
     * Service for State mileage fuel by vehicle Report with an explicit Interval.
     * 
     * @param groupID
     *            the Group ID
     * @param startDate
     *            the start date in format {@value com.inthinc.pro.service.reports.IFTAServiceStateMileageFuelByVehicle#DATE_FORMAT}
     * @param endDate
     *            the end date in format {@value com.inthinc.pro.service.reports.IFTAServiceStateMileageFuelByVehicle#DATE_FORMAT}
     * @param iftaOnly
     *            the DOT indicator. If set to true, only DOT data will be returned. Defaulted to false.
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageFuelByVehicle> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageFuelByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageFuelByVehicle found
     */
    @GET
    @Path("/group/{groupID}/report/ifta/fuelConsumption/iftaOnly/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageFuelByVehicleWithIftaAndDates(@PathParam("groupID") Integer groupID, @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate);

    /**
     * Service for State mileage fuel by vehicle Report with an explicit Interval.
     * 
     * @param groupList
     *            the Group ID List
     * @param startDate
     *            the start date in format {@value com.inthinc.pro.service.reports.IFTAServiceStateMileageFuelByVehicle#DATE_FORMAT}
     * @param endDate
     *            the end date in format {@value com.inthinc.pro.service.reports.IFTAServiceStateMileageFuelByVehicle#DATE_FORMAT}
     * @param iftaOnly
     *            the DOT indicator. If set to true, only DOT data will be returned. Defaulted to false.
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageFuelByVehicle> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageFuelByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageFuelByVehicle found
     */
    @POST
    @Path("/groups/report/ifta/fuelConsumption/iftaOnly/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageFuelByVehicleWithIftaAndDatesMultiGroup(GroupList groupList, @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate);
    
    /**
     * Service for State mileage fuel by vehicle Report with an explicit Interval.
     * 
     * @param groupID
     *            the Group ID
     * @param iftaOnly
     *            the DOT indicator. If set to true, only DOT data will be returned. Defaulted to false.
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageFuelByVehicle> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageFuelByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageFuelByVehicle found
     */
    @GET
    @Path("/group/{groupID}/report/ifta/fuelConsumption/iftaOnly")
    @Produces("application/xml")
    Response getStateMileageFuelByVehicleWithIfta(@PathParam("groupID") Integer groupID);
    
    /**
     * Service for State mileage fuel by vehicle Report with an explicit Interval.
     * 
     * @param groupList
     *            the Group ID List
     * @param iftaOnly
     *            the DOT indicator. If set to true, only DOT data will be returned. Defaulted to false.
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageFuelByVehicle> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageFuelByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageFuelByVehicle found
     */
    @POST
    @Path("/groups/report/ifta/fuelConsumption/iftaOnly")
    @Produces("application/xml")
    Response getStateMileageFuelByVehicleWithIftaMultiGroup(GroupList groupList);

    /**
     * Service for State mileage fuel by vehicle Report with an explicit Interval.
     * 
     * @param groupID
     *            the Group ID
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageFuelByVehicle> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageFuelByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageFuelByVehicle found
     */
    @GET
    @Path("/group/{groupID}/report/ifta/fuelConsumption")
    @Produces("application/xml")
    Response getStateMileageFuelByVehicleDefaults(@PathParam("groupID") Integer groupID);
    
    

    /**
     * Service for State mileage fuel by vehicle Report with an explicit Interval.
     * 
     * @param groupList
     *            the Group ID List
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageFuelByVehicle> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageFuelByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageFuelByVehicle found
     */
    @POST
    @Path("/groups/report/ifta/fuelConsumption")
    @Produces("application/xml")
    @Consumes("application/xml")
    Response getStateMileageFuelByVehicleDefaultsMultiGroup(GroupList groupList);

    /**
     * Service for State mileage fuel by vehicle Report with an explicit Interval.
     * 
     * @param groupID
     *            the Group ID
     * @param startDate
     *            the start date in format {@value com.inthinc.pro.service.reports.IFTAServiceStateMileageFuelByVehicle#DATE_FORMAT}
     * @param endDate
     *            the end date in format {@value com.inthinc.pro.service.reports.IFTAServiceStateMileageFuelByVehicle#DATE_FORMAT}
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageFuelByVehicle> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageFuelByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageFuelByVehicle found
     */
    @GET
    @Path("/group/{groupID}/report/ifta/fuelConsumption/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageFuelByVehicleWithDates(@PathParam("groupID") Integer groupID, @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate);

    /**
     * Service for State mileage fuel by vehicle Report with an explicit Interval.
     * 
     * @param groupList
     *            the Group ID List
     * @param startDate
     *            the start date in format {@value com.inthinc.pro.service.reports.IFTAServiceStateMileageFuelByVehicle#DATE_FORMAT}
     * @param endDate
     *            the end date in format {@value com.inthinc.pro.service.reports.IFTAServiceStateMileageFuelByVehicle#DATE_FORMAT}
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageFuelByVehicle> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageFuelByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageFuelByVehicle found
     */
    @POST
    @Path("/groups/report/ifta/fuelConsumption/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageFuelByVehicleWithDatesMultiGroup(GroupList groupList, @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate);
}
