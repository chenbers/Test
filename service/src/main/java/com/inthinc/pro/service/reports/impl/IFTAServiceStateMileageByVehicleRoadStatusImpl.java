package com.inthinc.pro.service.reports.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus;
import com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleRoadStatus;
import com.inthinc.pro.service.reports.facade.ReportsFacade;
import com.inthinc.pro.util.ReportsUtil;

@Component
public class IFTAServiceStateMileageByVehicleRoadStatusImpl extends BaseIFTAServiceImpl implements IFTAServiceStateMileageByVehicleRoadStatus {

    @Autowired
    public IFTAServiceStateMileageByVehicleRoadStatusImpl(ReportsFacade reportsFacade, ReportsUtil reportsUtil) {
        super(reportsFacade, reportsUtil);
    }

    /**
     * Service to retrieve the StateMileageByVehicleRoadStatus report data beans from the backend via a facade.
     * 
     * @param gorupID
     *            The ID of the group
     * @param startDate
     *            The start date of the interval
     * @param endDate
     *            The end date of the interval
     * @param iftaOnly
     *            Indicator expressing if only the ifta data should be returned
     * @return javax.ws.rs.core.Response to return the client
     */
    Response getStateMileageByVehicleRoadStatusWithFullParameters(Integer groupID, Date startDate, Date endDate, boolean iftaOnly) {

        Response response = reportsUtil.checkParameters(groupID, startDate, endDate);

        // Some validation errors found
        if (response != null) {
            return response;
        }

        // No validation error found
        List<StateMileageByVehicleRoadStatus> list = null;

        Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        try {
            list = reportsFacade.getStateMileageByVehicleRoadStatus(groupID, interval, iftaOnly);
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        // Some data found
        if (list != null && !list.isEmpty()) {
            return Response.ok(new GenericEntity<List<StateMileageByVehicleRoadStatus>>(list) {}).build();
        }
        // No data found
        else {
            return Response.status(Status.NOT_FOUND).build();
        }

    }

    @Override
    public Response getStateMileageByVehicleRoadStatusWithIfta(Integer groupID) {
        Calendar today = reportsUtil.getMidnight();

        Calendar startDate = reportsUtil.getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);

        return getStateMileageByVehicleRoadStatusWithFullParameters(groupID, startDate.getTime(), today.getTime(), true);
    }

    @Override
    public Response getStateMileageByVehicleRoadStatusWithIftaAndDates(Integer groupID, Date startDate, Date endDate) {
        return getStateMileageByVehicleRoadStatusWithFullParameters(groupID, startDate, endDate, true);
    }

    @Override
    public Response getStateMileageByVehicleRoadStatusWithDates(Integer groupID, Date startDate, Date endDate) {
        return getStateMileageByVehicleRoadStatusWithFullParameters(groupID, startDate, endDate, false);
    }

    @Override
    public Response getStateMileageByVehicleRoadStatusDefaults(Integer groupID) {
        Calendar today = reportsUtil.getMidnight();

        Calendar startDate = reportsUtil.getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);

        return getStateMileageByVehicleRoadStatusWithFullParameters(groupID, startDate.getTime(), today.getTime(), false);
    }
}
