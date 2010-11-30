package com.inthinc.pro.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.model.aggregation.Trend;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.service.DriverService;
import com.inthinc.pro.service.adapters.DriverDAOAdapter;
import com.inthinc.pro.service.model.BatchResponse;

public class DriverServiceImpl extends AbstractService<Driver, DriverDAOAdapter> implements DriverService {

    @Override
    public Response getAll() {
        List<Driver> list = getDao().getAll();
        return Response.ok(new GenericEntity<List<Driver>>(list) {}).build();
    }

    @Override
    public Response getSpeedingEvents(Integer driverID) {
        List<Event> eventList = getDao().getSpeedingEvents(driverID);
        if (eventList != null && !eventList.isEmpty()) {
            return Response.ok(new GenericEntity<List<Event>>(eventList) {}).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response create(List<Driver> drivers, UriInfo uriInfo) {

        List<BatchResponse> responseList = new ArrayList<BatchResponse>();
        for (Driver driver : drivers) {
            BatchResponse batchResponse = new BatchResponse();
            UriBuilder uriBuilder = uriInfo.getBaseUriBuilder().path("driver");
            Integer id = getDao().create(driver);
            if (id == null) {
                batchResponse.setStatus(Status.INTERNAL_SERVER_ERROR.getStatusCode());
            } else {
                batchResponse.setStatus(Status.CREATED.getStatusCode());
                batchResponse.setUri(uriBuilder.path(id.toString()).build().toString());
            }
            responseList.add(batchResponse);
        }
        return Response.ok(new GenericEntity<List<BatchResponse>>(responseList) {}).build();
    }

    @Override
    public Response getScore(Integer driverID, Integer numberOfDays) {
        Duration duration = Duration.getDurationByDays(numberOfDays);
        if (duration != null) {
            Score score = getDao().getScore(driverID, duration);
            if (score != null)
                return Response.ok(score).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response getTrend(Integer driverID, Integer numberOfDays) {
        Duration duration = Duration.getDurationByDays(numberOfDays);
        if (duration != null) {
            List<Trend> list = getDao().getTrend(driverID, duration);
            if (!list.isEmpty())
                return Response.ok(new GenericEntity<List<Trend>>(list) {}).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

}
