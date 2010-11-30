package com.inthinc.pro.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupTrendWrapper;
import com.inthinc.pro.service.GroupService;
import com.inthinc.pro.service.adapters.GroupDAOAdapter;
import com.inthinc.pro.service.model.BatchResponse;

public class GroupServiceImpl extends AbstractService<Group, GroupDAOAdapter> implements GroupService {

    @Override
    public Response getAll() {
        List<Group> list = getDao().getAll();
        return Response.ok(new GenericEntity<List<Group>>(list) {}).build();
    }

    @Override
    public Response getDriverScores(Integer groupID, Integer numberOfDays) {
        Duration duration = Duration.getDurationByDays(numberOfDays);
        if (duration != null) {
            List<DriverVehicleScoreWrapper> list = getDao().getDriverScores(groupID, duration);
            if (!list.isEmpty())
                return Response.ok(new GenericEntity<List<DriverVehicleScoreWrapper>>(list) {}).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response getVehicleScores(Integer groupID, Integer numberOfDays) {
        Duration duration = Duration.getDurationByDays(numberOfDays);
        if (duration != null) {
            List<DriverVehicleScoreWrapper> list = getDao().getVehicleScores(groupID, duration);
            if (list != null && !list.isEmpty())
                return Response.ok(new GenericEntity<List<DriverVehicleScoreWrapper>>(list) {}).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response getSubGroupsDriverTrends(Integer groupID, Integer numberOfDays) {
        Duration duration = Duration.getDurationByDays(numberOfDays);
        if (duration != null) {
            List<GroupTrendWrapper> list = getDao().getChildGroupsDriverTrends(groupID, duration);
            if (list != null && !list.isEmpty())
                return Response.ok(new GenericEntity<List<GroupTrendWrapper>>(list) {}).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response getSubGroupsDriverScores(Integer groupID, Integer numberOfDays) {
        Duration duration = Duration.getDurationByDays(numberOfDays);
        if (duration != null) {
            List<GroupScoreWrapper> list = getDao().getChildGroupsDriverScores(groupID, duration);
            if (list != null && !list.isEmpty())
                return Response.ok(new GenericEntity<List<GroupScoreWrapper>>(list) {}).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response create(List<Group> list, UriInfo uriInfo) {
        List<BatchResponse> responseList = new ArrayList<BatchResponse>();
        for (Group group : list) {
            BatchResponse batchResponse = new BatchResponse();
            UriBuilder uriBuilder = uriInfo.getBaseUriBuilder().path("group");
            Integer id = getDao().create(group);
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
}
