package com.inthinc.pro.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.inthinc.pro.model.User;
import com.inthinc.pro.service.UserService;
import com.inthinc.pro.service.adapters.UserDAOAdapter;
import com.inthinc.pro.service.model.BatchResponse;

public class UserServiceImpl extends AbstractService<User, UserDAOAdapter> implements UserService {

    @Override
    public Response getAll() {
        List<User> list = getDao().getAll();
        return Response.ok(new GenericEntity<List<User>>(list) {
        }).build();
    }

    @Override
    public Response get(String userName) {
        User user = getDao().findByUserName(userName);
        if (user != null)
            return Response.ok(user).build();
        return Response.status(Status.NOT_FOUND).build();
    }


    @Override
    public Response login(String userName, String password) {
        User user = getDao().login(userName, password);
        if (user != null)
            return Response.ok(user).build();
        return Response.status(Status.FORBIDDEN).build();
    }

    @Override
    public Response create(List<User> users, UriInfo uriInfo) {

        List<BatchResponse> responseList = new ArrayList<BatchResponse>();
        for (User user : users) {
            BatchResponse batchResponse = new BatchResponse();
            UriBuilder uriBuilder = uriInfo.getBaseUriBuilder().path("user");
            Integer id = getDao().create(user);
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
