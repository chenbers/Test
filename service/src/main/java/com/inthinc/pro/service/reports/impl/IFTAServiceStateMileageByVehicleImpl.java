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

import com.inthinc.pro.reports.ifta.model.MileageByVehicle;
import com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicle;
import com.inthinc.pro.service.reports.facade.ReportsFacade;
import com.inthinc.pro.util.ReportsUtil;

@Component
public class IFTAServiceStateMileageByVehicleImpl extends BaseIFTAServiceImpl implements IFTAServiceStateMileageByVehicle {

    @Autowired
    public IFTAServiceStateMileageByVehicleImpl(ReportsFacade reportsFacade, ReportsUtil reportsUtil) {
        super(reportsFacade, reportsUtil);
    }

    /**
     * @see com.inthinc.pro.service.reports.IFTAService#getStateMileageByVehicleDefaults(java.lang.Integer)
     */
    @Override
    public Response getStateMileageByVehicleDefaults(Integer groupID) {
        Calendar today = reportsUtil.getMidnight();

        Calendar startDate = reportsUtil.getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);

        return getStateMileageByVehicleWithFullParameters(groupID, startDate.getTime(), today.getTime(), false);
    }

    /**
     * @see com.inthinc.pro.service.reports.IFTAService#getStateMileageByVehicleWithDates(java.lang.Integer, java.util.Date, java.util.Date)
     */
    @Override
    public Response getStateMileageByVehicleWithDates(Integer groupID, Date startDate, Date endDate) {
        return getStateMileageByVehicleWithFullParameters(groupID, startDate, endDate, false);
    }

    /**
     * @see com.inthinc.pro.service.reports.IFTAService#getStateMileageByVehicleWithIfta(java.lang.Integer)
     */
    @Override
    public Response getStateMileageByVehicleWithIfta(Integer groupID) {
        Calendar today = reportsUtil.getMidnight();

        Calendar startDate = reportsUtil.getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);

        return getStateMileageByVehicleWithFullParameters(groupID, startDate.getTime(), today.getTime(), true);
    }

    /**
     * @see com.inthinc.pro.service.reports.IFTAService#getStateMileageByVehicle(java.lang.Integer, java.util.Date, java.util.Date, java.lang.Boolean)
     */
    @Override
    public Response getStateMileageByVehicleWithIftaAndDates(Integer groupID, Date startDate, Date endDate) {
        return getStateMileageByVehicleWithFullParameters(groupID, startDate, endDate, true);
    }

    private Response getStateMileageByVehicleWithFullParameters(Integer groupID, Date startDate, Date endDate, boolean iftaOnly) {

        Response response = reportsUtil.checkParameters(groupID, startDate, endDate);

        // Some validation errors found
        if (response != null) {
            return response;
        }

        Interval interval = new Interval(startDate.getTime(), endDate.getTime());

        List<MileageByVehicle> list = null;
        try {
            list = reportsFacade.getStateMileageByVehicle(groupID, interval, iftaOnly);
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        if (list == null || list.isEmpty()) {
            return Response.status(Status.NOT_FOUND).build();
        }

        return Response.ok(new GenericEntity<List<MileageByVehicle>>(list) {}).build();
    }
}
