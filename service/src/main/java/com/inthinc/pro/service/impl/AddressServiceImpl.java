package com.inthinc.pro.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.inthinc.pro.model.Address;
import com.inthinc.pro.service.AddressService;
import com.inthinc.pro.service.adapters.AddressDAOAdapter;
import com.inthinc.pro.service.model.BatchResponse;

public class AddressServiceImpl extends AbstractService<Address, AddressDAOAdapter> implements AddressService {
    
    @Override
    public Response getAll() {
        // Return 501 - Not Implemented
        return Response.status(501).build();
    }

    @Override
    public Response delete(Integer addressID) {
        // TODO: Backend delete method has not been implemented yet.
        // Return 501 - Not Implemented
        return Response.status(501).build();
    }

    @Override
    public Response create(List<Address> addresses, UriInfo uriInfo) {

        List<BatchResponse> responseList = new ArrayList<BatchResponse>();
        for (Address address : addresses) {
            BatchResponse batchResponse = new BatchResponse();
            UriBuilder uriBuilder = uriInfo.getBaseUriBuilder().path("address");
            Integer id = getDao().create(address);
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
    public Response delete(List<Integer> addressIDs) {
        // Return 501 - Not Implemented
        return Response.status(501).build();

    }

}
