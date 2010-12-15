package com.inthinc.pro.service.reports;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.Form;

import com.inthinc.pro.service.annotations.DateFormat;
import com.inthinc.pro.service.params.IFTAReportsParamsBean;

/**
 * Interface for IFTA/DOT Reports Services.
 * 
 * @deprecated Use the report specific interfaces: {@link IFTAServiceStateMileageByVehicleMonth}, {@link IFTAServiceMileageByVehicle} etc...
 */
@Produces("application/xml")
@Path("/group/{groupID}/report/ifta")
@Deprecated
public interface IFTAService {
    String DATE_FORMAT = "yyyyMMdd";

    // ----------------------------------------------------------------------
    // State Mileage by Vehicle / Road status webservice

    /**
     * Service for State mileage by vehicle / road status Report with an explicit Interval.
     * 
     * @param groupID
     *            the Group ID
     * @param startDate
     *            the start date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @param endDate
     *            the end date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @param iftaOnly
     *            the DOT indicator. If set to true, only DOT data will be returned. Defaulted to false.
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageByVehicleRoadStatus found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageByVehicleRoadStatus found
     */
    @GET
    @Path("/roadStatus/iftaOnly/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageByVehicleRoadStatusWithIftaAndDates(@PathParam("groupID") Integer groupID, @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
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
    @Path("/roadStatus/iftaOnly")
    @Produces("application/xml")
    Response getStateMileageByVehicleRoadStatusWithIfta(@PathParam("groupID") Integer groupID);

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
    @Path("/roadStatus")
    @Produces("application/xml")
    Response getStateMileageByVehicleRoadStatusDefaults(@PathParam("groupID") Integer groupID);

    /**
     * Service for State mileage by vehicle / road status Report with an explicit Interval.
     * 
     * @param groupID
     *            the Group ID
     * @param startDate
     *            the start date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @param endDate
     *            the end date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageByVehicleRoadStatus found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageByVehicleRoadStatus found
     */
    @GET
    @Path("/roadStatus/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageByVehicleRoadStatusWithDates(@PathParam("groupID") Integer groupID, @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate);

    // ----------------------------------------------------------------------
    // State Mileage by Vehicle / Group Comparison by State-Province

    /**
     * Service for State mileage by vehicle / Group Comparison by State-Province Report with given group & IFTA only & given dates.
     * 
     * @param groupID
     *            the Group ID
     * @param startDate
     *            the start date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @param endDate
     *            the end date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageCompareByGroup found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageCompareByGroup found
     */
    @GET
    @Path("/stateComparison/iftaOnly/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageByVehicleStateComparisonWithIftaAndDates(@PathParam("groupID") Integer groupID, @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate);

    /**
     * Service for State mileage by vehicle / Group Comparison by State-Province Report with given group & IFTA only & default dates.
     * 
     * @param groupID
     *            the Group ID
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageCompareByGroup found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageCompareByGroup found
     */
    @GET
    @Path("/stateComparison/iftaOnly")
    @Produces("application/xml")
    Response getStateMileageByVehicleStateComparisonWithIfta(@PathParam("groupID") Integer groupID);

    /**
     * Service for State mileage by vehicle / Group Comparison by State-Province Report with given group & default ifta (false) & default dates
     * 
     * @param groupID
     *            the Group ID
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageCompareByGroup found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageCompareByGroup found
     */
    @GET
    @Path("/stateComparison")
    @Produces("application/xml")
    Response getStateMileageByVehicleStateComparisonDefaults(@PathParam("groupID") Integer groupID);

    /**
     * Service for State mileage by vehicle / Group Comparison by State-Province Report with given group & given dates & default ifta (false).
     * 
     * @param groupID
     *            the Group ID
     * @param startDate
     *            the start date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @param endDate
     *            the end date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageCompareByGroup found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageCompareByGroup found
     */
    @GET
    @Path("/stateComparison/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageByVehicleStateComparisonWithDates(@PathParam("groupID") Integer groupID, @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate);

    // ----------------------------------------------------------------------
    // Mileage By Vehicle
    /**
     * Service for Mileage By Vehicle Report with an explicit Interval and iftaOnly flag.
     * 
     * @param groupID
     *            the Group ID
     * @param startDate
     *            the start date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @param endDate
     *            the end date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     */
    @GET
    @Path("/mileage/iftaOnly/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getMileageByVehicleWithIftaAndDates(@PathParam("groupID") Integer groupID, @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate);

    /**
     * Service for Mileage By Vehicle Report with an explicit Interval. Service for Mileage By Vehicle Report without Interval but with IFTA flag.
     * 
     * @param groupID
     *            the Group ID
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     */
    @GET
    @Path("/mileage/iftaOnly")
    @Produces("application/xml")
    Response getMileageByVehicleWithIfta(@PathParam("groupID") Integer groupID);

    /**
     * Service for Mileage By Vehicle Report without any other params.
     * 
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     */
    @GET
    @Path("/mileage")
    @Produces("application/xml")
    Response getMileageByVehicleDefaults(@PathParam("groupID") Integer groupID);

    /**
     * Service for Mileage By Vehicle Report with an explicit Interval only.
     * 
     * @param groupID
     *            the Group ID
     * @param startDate
     *            the start date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @param endDate
     *            the end date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     */
    @GET
    @Path("/mileage/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getMileageByVehicleWithDates(@PathParam("groupID") Integer groupID, @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate);

    // ----------------------------------------------------------------------
    // State Mileage By Vehicle
    /**
     * Service for State Mileage By Vehicle Report with only the group as a parameter.
     * 
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     */
    @GET
    @Path("/stateMileage")
    @Produces("application/xml")
    Response getStateMileageByVehicleDefaults(@PathParam("groupID") Integer groupID);

    /**
     * Service for State Mileage By Vehicle Report with an explicit Interval only.
     * 
     * @param groupID
     *            the Group ID
     * @param startDate
     *            the start date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @param endDate
     *            the end date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     */
    @GET
    @Path("/stateMileage/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageByVehicleWithDates(@PathParam("groupID") Integer groupID, @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate);

    /**
     * Service for State Mileage By Vehicle Report without Interval but with IFTA flag.
     * 
     * @param groupID
     *            the Group ID
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     */
    @GET
    @Path("/stateMileage/iftaOnly")
    @Produces("application/xml")
    Response getStateMileageByVehicleWithIfta(@PathParam("groupID") Integer groupID);

    /**
     * Service for State Mileage By Vehicle Report with an explicit Interval and IFTA flag.
     * 
     * @param groupID
     *            the Group ID
     * @param startDate
     *            the start date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @param endDate
     *            the end date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     */
    @GET
    @Path("/stateMileage/iftaOnly/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageByVehicleWithIftaAndDates(@PathParam("groupID") Integer groupID, @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate);

    // ----------------------------------------------------------------------
    // State Mileage by Vehicle / Month Mileage webservice

    /**
     * Service for State mileage by vehicle / month Report with an explicit Interval.
     * 
     * @param groupID
     *            the Group ID
     * @param startDate
     *            the start date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @param endDate
     *            the end date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @param iftaOnly
     *            the DOT indicator. If set to true, only DOT data will be returned. Defaulted to false.
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of beans
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     */
    @GET
    @Path("/monthMileage/iftaOnly/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageByVehicleByMonthWithIftaAndDates(@PathParam("groupID") Integer groupID, @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate);

    /**
     * Service for State mileage by vehicle / month Report with an explicit Interval.
     * 
     * @param groupID
     *            the Group ID
     * @param iftaOnly
     *            the DOT indicator. If set to true, only DOT data will be returned. Defaulted to false.
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of beans
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     */
    @GET
    @Path("/monthMileage/iftaOnly")
    @Produces("application/xml")
    Response getStateMileageByVehicleByMonthWithIfta(@PathParam("groupID") Integer groupID);

    /**
     * Service for State mileage by vehicle / month Report with an explicit Interval.
     * 
     * @param groupID
     *            the Group ID
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of beans
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     */
    @GET
    @Path("/monthMileage")
    @Produces("application/xml")
    Response getStateMileageByVehicleByMonthDefaults(@PathParam("groupID") Integer groupID);

    /**
     * Service for State mileage by vehicle / month Report with an explicit Interval.
     * 
     * @param groupID
     *            the Group ID
     * @param startDate
     *            the start date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @param endDate
     *            the end date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of beans
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     */
    @GET
    @Path("/monthMileage/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageByVehicleByMonthWithDates(@PathParam("groupID") Integer groupID, @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate);

}
