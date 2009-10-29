package com.inthinc.pro.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.inthinc.pro.model.Group;
import com.inthinc.pro.service.GroupService;
import com.inthinc.pro.service.model.BatchResponse;
import com.inthinc.pro.util.SecureGroupDAO;

public class GroupServiceImpl extends AbstractService<Group, SecureGroupDAO> implements GroupService {

    @Override
    public Response getAll() {
        List<Group> list = getDao().getAll();
        return Response.ok(new GenericEntity<List<Group>>(list) {
        }).build();
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
        return Response.ok(new GenericEntity<List<BatchResponse>>(responseList) {
        }).build();
    }
}
