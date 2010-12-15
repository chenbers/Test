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

import com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup;
import com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleGroupComparison;
import com.inthinc.pro.service.reports.facade.ReportsFacade;
import com.inthinc.pro.util.ReportsUtil;

@Component
public class IFTAServiceStateMileageByVehicleGroupComparisonImpl extends BaseIFTAServiceImpl implements IFTAServiceStateMileageByVehicleGroupComparison {

    @Autowired
    public IFTAServiceStateMileageByVehicleGroupComparisonImpl(ReportsFacade reportsFacade, ReportsUtil reportsUtil) {
        super(reportsFacade, reportsUtil);
    }

    Response getStateMileageByVehicleStateComparisonWithFullParameters(Integer groupID, Date startDate, Date endDate, boolean iftaOnly) {

        Response response = reportsUtil.checkParameters(groupID, startDate, endDate);

        // Some validation errors found
        if (response != null) {
            return response;
        }

        List<StateMileageCompareByGroup> list = null;

        Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        try {
            list = reportsFacade.getStateMileageByVehicleStateComparison(groupID, interval, iftaOnly);
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        if (list != null && !list.isEmpty())
            return Response.ok(new GenericEntity<List<StateMileageCompareByGroup>>(list) {}).build();

        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response getStateMileageByVehicleStateComparisonWithIfta(Integer groupID) {
        Calendar today = reportsUtil.getMidnight();

        Calendar startDate = reportsUtil.getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);

        return getStateMileageByVehicleStateComparisonWithFullParameters(groupID, startDate.getTime(), today.getTime(), true);
    }

    @Override
    public Response getStateMileageByVehicleStateComparisonWithIftaAndDates(Integer groupID, Date startDate, Date endDate) {
        return getStateMileageByVehicleStateComparisonWithFullParameters(groupID, startDate, endDate, true);
    }

    @Override
    public Response getStateMileageByVehicleStateComparisonWithDates(Integer groupID, Date startDate, Date endDate) {
        return getStateMileageByVehicleStateComparisonWithFullParameters(groupID, startDate, endDate, false);
    }

    @Override
    public Response getStateMileageByVehicleStateComparisonDefaults(Integer groupID) {
        Calendar today = reportsUtil.getMidnight();

        Calendar startDate = reportsUtil.getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);

        return getStateMileageByVehicleStateComparisonWithFullParameters(groupID, startDate.getTime(), today.getTime(), false);
    }
}
