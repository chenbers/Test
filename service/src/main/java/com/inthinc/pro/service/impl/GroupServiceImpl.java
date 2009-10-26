package com.inthinc.pro.service.impl;

import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.inthinc.pro.model.Group;
import com.inthinc.pro.service.GroupService;
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
        return Response.status(501).build();
    }
}
