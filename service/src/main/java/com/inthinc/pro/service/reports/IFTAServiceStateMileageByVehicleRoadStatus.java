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
public interface IFTAServiceStateMileageByVehicleRoadStatus {
    String DATE_FORMAT = "yyyyMMdd";

    /**
     * Service for State mileage by vehicle / road status Report with an explicit Interval.
     * 
     * @param groupID
     *            the Group ID
     * @param startDate
     *            the start date in format {@value com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleRoadStatus#DATE_FORMAT}
     * @param endDate
     *            the end date in format {@value com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleRoadStatus#DATE_FORMAT}
     * @param iftaOnly
     *            the DOT indicator. If set to true, only DOT data will be returned. Defaulted to false.
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageByVehicleRoadStatus found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageByVehicleRoadStatus found
     */
    @GET
    @Path("/group/{groupID}/report/ifta/roadStatus/iftaOnly/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageByVehicleRoadStatusWithIftaAndDates(@PathParam("groupID") Integer groupID, @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate);

    /**
     * Service for State mileage by vehicle / road status Report with an explicit Interval.
     * 
     * @param groupList
     *            the Group ID List
     * @param startDate
     *            the start date in format {@value com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleRoadStatus#DATE_FORMAT}
     * @param endDate
     *            the end date in format {@value com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleRoadStatus#DATE_FORMAT}
     * @param iftaOnly
     *            the DOT indicator. If set to true, only DOT data will be returned. Defaulted to false.
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageByVehicleRoadStatus found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageByVehicleRoadStatus found
     */
    @POST
    @Path("/groups/report/ifta/roadStatus/iftaOnly/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageByVehicleRoadStatusWithIftaAndDatesMultiGroup(GroupList groupList, @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate);
    
    /**
     * Service for State mileage by vehicle / road status Report with an explicit Interval.
     * 
     * @param groupID
     *            the Group ID
     * @param iftaOnly
     *            the DOT indicator. If set to true, only DOT data will be returned. Defaulted to false.
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageByVehicleRoadStatus found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageByVehicleRoadStatus found
     */
    @GET
    @Path("/group/{groupID}/report/ifta/roadStatus/iftaOnly")
    @Produces("application/xml")
    Response getStateMileageByVehicleRoadStatusWithIfta(@PathParam("groupID") Integer groupID);
    
    /**
     * Service for State mileage by vehicle / road status Report with an explicit Interval.
     * 
     * @param groupList
     *            the Group ID List
     * @param iftaOnly
     *            the DOT indicator. If set to true, only DOT data will be returned. Defaulted to false.
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageByVehicleRoadStatus found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageByVehicleRoadStatus found
     */
    @POST
    @Path("/groups/report/ifta/roadStatus/iftaOnly")
    @Produces("application/xml")
    Response getStateMileageByVehicleRoadStatusWithIftaMultiGroup(GroupList groupList);

    /**
     * Service for State mileage by vehicle / road status Report with an explicit Interval.
     * 
     * @param groupID
     *            the Group ID
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageByVehicleRoadStatus found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageByVehicleRoadStatus found
     */
    @GET
    @Path("/group/{groupID}/report/ifta/roadStatus")
    @Produces("application/xml")
    Response getStateMileageByVehicleRoadStatusDefaults(@PathParam("groupID") Integer groupID);
    
    

    /**
     * Service for State mileage by vehicle / road status Report with an explicit Interval.
     * 
     * @param groupList
     *            the Group ID List
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageByVehicleRoadStatus found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageByVehicleRoadStatus found
     */
    @POST
    @Path("/groups/report/ifta/roadStatus")
    @Produces("application/xml")
    @Consumes("application/xml")
    Response getStateMileageByVehicleRoadStatusDefaultsMultiGroup(GroupList groupList);

    /**
     * Service for State mileage by vehicle / road status Report with an explicit Interval.
     * 
     * @param groupID
     *            the Group ID
     * @param startDate
     *            the start date in format {@value com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleRoadStatus#DATE_FORMAT}
     * @param endDate
     *            the end date in format {@value com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleRoadStatus#DATE_FORMAT}
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageByVehicleRoadStatus found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageByVehicleRoadStatus found
     */
    @GET
    @Path("/group/{groupID}/report/ifta/roadStatus/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageByVehicleRoadStatusWithDates(@PathParam("groupID") Integer groupID, @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate);

    /**
     * Service for State mileage by vehicle / road status Report with an explicit Interval.
     * 
     * @param groupList
     *            the Group ID List
     * @param startDate
     *            the start date in format {@value com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleRoadStatus#DATE_FORMAT}
     * @param endDate
     *            the end date in format {@value com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleRoadStatus#DATE_FORMAT}
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageByVehicleRoadStatus found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageByVehicleRoadStatus found
     */
    @POST
    @Path("/groups/report/ifta/roadStatus/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageByVehicleRoadStatusWithDatesMultiGroup(GroupList groupList, @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate);
}
