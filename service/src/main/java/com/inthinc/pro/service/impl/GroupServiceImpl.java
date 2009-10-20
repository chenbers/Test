package com.inthinc.pro.service.impl;

import java.net.URI;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.User;
import com.inthinc.pro.service.GroupService;
import com.inthinc.pro.util.SecureGroupDAO;


public class GroupServiceImpl implements GroupService {

    private SecureGroupDAO groupDAO;

    @Override
    public Response getAll() {
        // TODO do we want group level security?
        // return groupDAO.getGroupsByAcctID(securityBean.getAccountID());

        List<Group> list = groupDAO.getAll();
        return Response.ok(new GenericEntity<List<Group>>(list) {
        }).build();
    }

    @Override
    public Response get(Integer groupID) {
        Group group = groupDAO.findByID(groupID);
        if (group != null)
            return Response.ok(group).build();
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response create(Group group, UriInfo uriInfo) {
        Integer id = groupDAO.create(group);
        if (id != null) {
            UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
            URI uri = uriBuilder.path(id.toString()).build();
            return Response.created(uri).build();
        }
        return Response.serverError().build();
    }

    @Override
    public Response update(Group group) {
        if (groupDAO.update(group).intValue() != 0) {
            return Response.ok().build();
        }
        return Response.status(Status.NOT_MODIFIED).build();
    }

    @Override
    public Response delete(Integer groupID) {
        if (groupDAO.deleteByID(groupID).intValue() != 0) {
            return Response.ok().build();
        }
        return Response.status(Status.NOT_MODIFIED).build();
    }

    public void setGroupDAO(SecureGroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public SecureGroupDAO getGroupDAO() {
        return groupDAO;
    }
}
