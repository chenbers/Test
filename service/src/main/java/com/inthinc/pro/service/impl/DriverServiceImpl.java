package com.inthinc.pro.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response.Status;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.service.DriverService;
import com.inthinc.pro.service.model.BatchResponse;
import com.inthinc.pro.util.SecureDriverDAO;

public class DriverServiceImpl extends AbstractService<Driver, SecureDriverDAO> implements DriverService {

    @Override
    public Response getAll() {
        List<Driver> list = getDao().getAll();
        return Response.ok(new GenericEntity<List<Driver>>(list){}).build();
    }

    @Override
    public Response create(List<Driver> drivers) {

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
        return Response.ok(new GenericEntity<List<BatchResponse>>(responseList) {
        }).build();
    }


}
