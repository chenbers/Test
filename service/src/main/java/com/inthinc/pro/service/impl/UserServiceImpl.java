package com.inthinc.pro.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.User;
import com.inthinc.pro.service.UserService;
import com.inthinc.pro.service.model.BatchResponse;
import com.inthinc.pro.util.SecureUserDAO;

public class UserServiceImpl implements UserService {

    private SecureUserDAO userDAO;

    @Override
    public Response getAll() {
        List<User> list = userDAO.getAll();
        return Response.ok(new GenericEntity<List<User>>(list) {
        }).build();
    }

    @Override
    public Response get(Integer userID) {
        User user = userDAO.findByID(userID);
        if (user != null)
            return Response.ok(user).build();
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response get(String userName) {
        User user = userDAO.findByUserName(userName);
        if (user != null)
            return Response.ok(user).build();
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response create(User user, UriInfo uriInfo) {
        Integer id = userDAO.create(user);
        if (id != null) {
            UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
            URI uri = uriBuilder.path(id.toString()).build();
            return Response.created(uri).build();
        }
        return Response.serverError().build();

    }

    @Override
    public Response update(User user) {
        if (userDAO.update(user).intValue() != 0) {
            return Response.ok().build();
        }
        return Response.status(Status.NOT_MODIFIED).build();
    }

    @Override
    public Response delete(Integer userID) {
        if (userDAO.deleteByID(userID).intValue() != 0) {
            return Response.ok().build();
        }
        return Response.status(Status.NOT_MODIFIED).build();
    }

    @Override
    public Response create(List<User> users, UriInfo uriInfo) {

        List<BatchResponse> responseList = new ArrayList<BatchResponse>();
        for (User user : users) {
            BatchResponse batchResponse = new BatchResponse();
            UriBuilder uriBuilder = uriInfo.getBaseUriBuilder().path("user");
            Integer id = userDAO.create(user);
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

    @Override
    public Response update(List<User> users) {

        List<BatchResponse> responseList = new ArrayList<BatchResponse>();
        for (User user : users) {
            BatchResponse batchResponse = new BatchResponse();
            Integer changeCount = userDAO.update(user);
            if (changeCount == 0) {
                batchResponse.setStatus(Status.NOT_MODIFIED.getStatusCode());
            } else {
                batchResponse.setStatus(Status.OK.getStatusCode());
            }
            responseList.add(batchResponse);
        }
        return Response.ok(new GenericEntity<List<BatchResponse>>(responseList) {
        }).build();
    }

    @Override
    public Response delete(List<Integer> userIDs) {
        List<BatchResponse> responseList = new ArrayList<BatchResponse>();
        for (Integer userID : userIDs) {
            BatchResponse batchResponse = new BatchResponse();
            Integer changeCount = userDAO.deleteByID(userID);
            if (changeCount == 0) {
                batchResponse.setStatus(Status.NOT_MODIFIED.getStatusCode());
            } else {
                batchResponse.setStatus(Status.OK.getStatusCode());
            }
            responseList.add(batchResponse);
        }
        return Response.ok(new GenericEntity<List<BatchResponse>>(responseList) {
        }).build();
    }

    public void setUserDAO(SecureUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public SecureUserDAO getUserDAO() {
        return userDAO;
    }
}
