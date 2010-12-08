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
@Path("/group/{groupID}/report/ifta")
public interface IFTAService {
    String DATE_FORMAT = "yyyyMMdd";

    // ----------------------------------------------------------------------
    // State Mileage by Vehicle / Road status webservice 

    /**
     * Service for State mileage by vehicle / road status Report with an explicit Interval.
     * @param groupID the Group ID
     * @param startDate the start date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @param endDate the end date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @param iftaOnly the DOT indicator. If set to true, only DOT data will be returned. 
     *                                   Defaulted to false.
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageByVehicleRoadStatus found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageByVehicleRoadStatus found 
     */
    @GET
    @Path("/roadStatus/iftaOnly/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageByVehicleRoadStatusWithIftaAndDates(
            @PathParam("groupID") Integer groupID,
            @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate); 

    /**
     * Service for State mileage by vehicle / road status Report with an explicit Interval.
     * @param groupID the Group ID
     * @param iftaOnly the DOT indicator. If set to true, only DOT data will be returned. 
     *                                   Defaulted to false.
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageByVehicleRoadStatus found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageByVehicleRoadStatus found 
     */
    @GET
    @Path("/roadStatus/iftaOnly")
    @Produces("application/xml")
    Response getStateMileageByVehicleRoadStatusWithIfta(@PathParam("groupID") Integer groupID );

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
    Response getStateMileageByVehicleRoadStatusDefaults(@PathParam("groupID") Integer groupID); 

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
    Response getStateMileageByVehicleRoadStatusWithDates(
            @PathParam("groupID") Integer groupID,
            @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate); 

    // ----------------------------------------------------------------------
    // State Mileage by Vehicle / Group Comparison by State-Province

    /**
     * Service for State mileage by vehicle / Group Comparison by State-Province Report with an explicit Interval.
     * @param groupID the Group ID
     * @param startDate the start date 
     * @param endDate the end date
     * @param iftaOnly the DOT indicator. If set to true, only DOT data will be returned. 
     *                                   Defaulted to false.
     * @returnWrapped List<StateMileageCompareByGroup> the list of StateMileageCompareByGroup
     * @HTTP HTTP 200 - OK if any StateMileageCompareByGroup found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageCompareByGroup found 
     */
    @GET
    @Path("/stateComparaison/{startDate}/{endDate}/{iftaOnly}")
    @Produces("application/xml")
    Response getStateMileageByVehicleGroupComparison(
            @PathParam("groupID") Integer groupID,
            @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate,
            @PathParam("iftaOnly")  @DefaultValue("false") boolean iftaOnly ); 

    /**
     * Service for State mileage by vehicle / Group Comparison by State-Province Report with an explicit Interval.
     * @param groupID the Group ID
     * @param iftaOnly the DOT indicator. If set to true, only DOT data will be returned. 
     *                                   Defaulted to false.
     * @returnWrapped List<StateMileageByVehicleGroupComparaison> the list of StateMileageByVehicleGroupComparaison
     * @HTTP HTTP 200 - OK if any StateMileageByVehicleGroupComparaison found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageByVehicleGroupComparaison found 
     */
    @GET
    @Path("/stateComparaison/{iftaOnly}")
    @Produces("application/xml")
    Response getStateMileageByVehicleGroupComparisonIfta(
            @PathParam("groupID") Integer groupID,
            @PathParam("iftaOnly")  @DefaultValue("false") boolean iftaOnly );

    /**
     * Service for State mileage by vehicle /Group Comparison by State-Province Report with an explicit Interval.
     * @param groupID the Group ID
     * @returnWrapped List<StateMileageCompareByGroup> the list of StateMileageCompareByGroup
     * @HTTP HTTP 200 - OK if any StateMileageCompareByGroup found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageCompareByGroup found 
     */
    @GET
    @Path("/stateComparaison")
    @Produces("application/xml")
    Response getStateMileageByVehicleGroupComparisonGroup(@PathParam("groupID") Integer groupID); 

    /**
     * Service for State mileage by vehicle / Group Comparison by State-Province Report with an explicit Interval.
     * @param groupID the Group ID
     * @param startDate the start date 
     * @param endDate the end date
     * @returnWrapped List<StateMileageCompareByGroup> the list of StateMileageCompareByGroup
     * @HTTP HTTP 200 - OK if any StateMileageCompareByGroup found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageCompareByGroup found 
     */
    @GET
    @Path("/stateComparaison/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageByVehicleGroupComparaisonInterval(
            @PathParam("groupID") Integer groupID,
            @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate); 

    // ----------------------------------------------------------------------
    // Mileage By Vehicle 
    /**
     * Service for Mileage By Vehicle Report with an explicit Interval and iftaOnly flag.
     * @param groupID the Group ID
     * @param startDate the start date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @param endDate the end date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found 
     */
    @GET
    @Path("/mileage/iftaOnly/{startDate:\\d{8}}/{endDate:\\d{8}}")
    @Produces("application/xml")
    Response getMileageByVehicle(@PathParam("groupID") Integer groupID,
                                 @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
                                 @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate); 

    /**
     * Service for Mileage By Vehicle Report with an explicit Interval.
     * Service for Mileage By Vehicle Report without Interval but with IFTA flag.
     * @param groupID the Group ID
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found 
     */
    @GET
    @Path("/mileage/iftaOnly")
    @Produces("application/xml")
    Response getMileageByVehicleIfta(@PathParam("groupID") Integer groupID); 

    /**
     * Service for Mileage By Vehicle Report without any other params.
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found 
     */
    @GET
    @Path("/mileage")
    @Produces("application/xml")
    Response getMileageByVehicleGroup(@PathParam("groupID") Integer groupID); 

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
    @Path("/mileage/{startDate:\\d{8}}/{endDate:\\d{8}}")
    @Produces("application/xml")
    Response getMileageByVehicleInterval(@PathParam("groupID") Integer groupID,
                                 @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
                                 @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate); 

    
    // ----------------------------------------------------------------------
    // State Mileage By Vehicle
    
    /**
     * Service for State Mileage By Vehicle Report with an explicit Interval and IFTA flag.
     * 
     * @param groupID the Group ID
     * @param startDate the start date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @param endDate the end date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @param iftaOnly the string iftaOnly to consider true
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found 
     */
    @GET
    @Path("/stateMileage/iftaOnly/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageByVehicle(@PathParam("groupID") Integer groupID,
                                 @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
                                 @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate,
                                 @DefaultValue("true") Boolean iftaOnly); 

    /**
     * Service for State Mileage By Vehicle Report without Interval but with IFTA flag.
     * @param groupID the Group ID
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found 
     */
    @GET
    @Path("/stateMileage/iftaOnly")
    @Produces("application/xml")
    Response getStateMileageByVehicleIfta(@PathParam("groupID") Integer groupID); 

    /**
     * Service for State Mileage By Vehicle Report with only the group as a parameter.
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found 
     */
    @GET
    @Path("/stateMileage")
    @Produces("application/xml")
    Response getStateMileageByVehicleGroup(@PathParam("groupID") Integer groupID); 

    /**
     * Service for State Mileage By Vehicle Report with an explicit Interval only.
     * @param groupID the Group ID
     * @param startDate the start date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @param endDate the end date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found 
     */
    @GET
    @Path("/stateMileage/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageByVehicleInterval(@PathParam("groupID") Integer groupID,
                                 @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
                                 @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate);     
}
