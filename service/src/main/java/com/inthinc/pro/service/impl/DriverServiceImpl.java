package com.inthinc.pro.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.service.DriverService;
import com.inthinc.pro.service.model.BatchResponse;
import com.inthinc.pro.util.SecureDriverDAO;

public class DriverServiceImpl implements DriverService {

    private SecureDriverDAO driverDAO;

    @Override
    public Response getAll() {
        List<Driver> list = driverDAO.getAll();
        return Response.ok(new GenericEntity<List<Driver>>(list) {
        }).build();
    }

    @Override
    public Response get(Integer driverID) {
        Driver driver = driverDAO.findByID(driverID);
        if (driver != null)
            return Response.ok(driver).build();
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response create(Driver driver, UriInfo uriInfo) {
        Integer id = driverDAO.create(driver);
        if (id != null) {
            UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
            URI uri = uriBuilder.path(id.toString()).build();
            return Response.created(uri).build();
        }
        return Response.serverError().build();
    }

    @Override
    public Response update(Driver driver) {
        if (driverDAO.update(driver).intValue() != 0) {
            return Response.ok().build();
        }
        return Response.status(Status.NOT_MODIFIED).build();
    }

    @Override
    public Response delete(Integer driverID) {
        if (driverDAO.deleteByID(driverID).intValue() != 0) {
            return Response.ok().build();
        }
        return Response.status(Status.NOT_MODIFIED).build();
    }

    @Override
    public Response create(List<Driver> drivers, UriInfo uriInfo) {

        List<BatchResponse> responseList = new ArrayList<BatchResponse>();
        for (Driver driver : drivers) {
            BatchResponse batchResponse = new BatchResponse();
            UriBuilder uriBuilder = uriInfo.getBaseUriBuilder().path("driver");
            Integer id = driverDAO.create(driver);
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
    public Response update(List<Driver> drivers) {
        List<BatchResponse> responseList = new ArrayList<BatchResponse>();
        for (Driver driver : drivers) {
            BatchResponse batchResponse = new BatchResponse();
            Integer changeCount = driverDAO.update(driver);
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
    public Response delete(List<Integer> driverIDs) {
        List<BatchResponse> responseList = new ArrayList<BatchResponse>();
        for (Integer driverID : driverIDs) {
            BatchResponse batchResponse = new BatchResponse();
            Integer changeCount = driverDAO.deleteByID(driverID);
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

    public void setDriverDAO(SecureDriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    public SecureDriverDAO getDriverDAO() {
        return driverDAO;
    }

}
