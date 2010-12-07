package com.inthinc.pro.service.reports;

import java.util.Date;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.inthinc.pro.service.annotations.DateFormat;

/**
 * Interface for IFTA/DOT Reports Services.
 */
@Produces("application/xml")
@Path("/group/{groupID}/report/IFTA")
public interface IFTAService {
    String DATE_FORMAT = "yyyyMMdd";

    /**
     * Service for State mileage by vehicle / road status Report with an explicit Interval.
     * @param groupID the Group ID
     * @param startDate the start date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @param endDate the end date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @param dotOnly the DOT indicator. If set to true, only DOT data will be returned. 
     *                                   Defaulted to false.
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageByVehicleRoadStatus found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageByVehicleRoadStatus found 
     */
    @GET
    @Path("/roadStatus/{startDate}/{endDate}/{dotOnly}")
    @Produces("application/xml")
    Response getStateMileageByVehicleRoadStatus(@PathParam("groupID") Integer groupID,
                                  @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
                                  @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate,
                                  @PathParam("dotOnly")  @DefaultValue("false") boolean dotOnly ); 

    /**
     * Service for State mileage by vehicle / road status Report with an explicit Interval.
     * @param groupID the Group ID
     * @param dotOnly the DOT indicator. If set to true, only DOT data will be returned. 
     *                                   Defaulted to false.
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageByVehicleRoadStatus found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageByVehicleRoadStatus found 
     */
    @GET
    @Path("/roadStatus/{dotOnly}")
    @Produces("application/xml")
    Response getStateMileageByVehicleRoadStatusOnlyStatus(@PathParam("groupID") Integer groupID,
                                                            @PathParam("dotOnly")  @DefaultValue("false") boolean dotOnly );
    
    /**
     * Service for State mileage by vehicle / road status Report with an explicit Interval.
     * @param groupID the Group ID
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageByVehicleRoadStatus found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageByVehicleRoadStatus found 
     */
    @GET
    @Path("/roadStatus")
    @Produces("application/xml")
    Response getStateMileageByVehicleRoadStatusOnlyGroup(@PathParam("groupID") Integer groupID); 
    
    /**
     * Service for State mileage by vehicle / road status Report with an explicit Interval.
     * @param groupID the Group ID
     * @param startDate the start date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @param endDate the end date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageByVehicleRoadStatus found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageByVehicleRoadStatus found 
     */
    @GET
    @Path("/roadStatus/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageByVehicleRoadStatusOnlyDates(@PathParam("groupID") Integer groupID,
                                  @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
                                  @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate); 

    // Mileage By Vehicle ----------------------------------------------------------------------
    /**
     * Service for Mileage By Vehicle Report with an explicit Interval and IFTA flag.
     * @param groupID the Group ID
     * @param startDate the start date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @param endDate the end date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @param iftaOnly the string iftaOnly to consider true
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found 
     */
    @GET
    @Path("/mileage/{startDate}/{endDate}/iftaOnly")
    @Produces("application/xml")
    Response getMileageByVehicle(@PathParam("groupID") Integer groupID,
                                 @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
                                 @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate,
                                 @DefaultValue("true") Boolean iftaOnly); 

    /**
     * Service for Mileage By Vehicle Report with an explicit Interval only.
     * @param groupID the Group ID
     * @param startDate the start date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @param endDate the end date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found 
     */
    @GET
    @Path("/mileage/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getMileageByVehicleWithInterval(@PathParam("groupID") Integer groupID,
                                 @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
                                 @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate); 

    /**
     * Service for Mileage By Vehicle Report without Interval but with IFTA flag.
     * @param groupID the Group ID
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found 
     */
    @GET
    @Path("/mileage/iftaOnly")
    @Produces("application/xml")
    Response getMileageByVehicleWithFlag(@PathParam("groupID") Integer groupID); 

    /**
     * Service for Mileage By Vehicle Report without any other params.
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found 
     */
    @GET
    @Path("/mileage")
    @Produces("application/xml")
    Response getMileageByVehicleWithoutParam(@PathParam("groupID") Integer groupID); 
}
