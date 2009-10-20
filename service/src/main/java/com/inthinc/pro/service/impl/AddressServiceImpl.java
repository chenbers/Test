package com.inthinc.pro.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.NotImplementedException;

import com.inthinc.pro.model.Address;
import com.inthinc.pro.service.AddressService;
import com.inthinc.pro.service.model.BatchResponse;
import com.inthinc.pro.util.SecureAddressDAO;

public class AddressServiceImpl implements AddressService {

    private SecureAddressDAO addressDAO;

//    @Override
//    public Response getAll() {
//        throw new NotImplementedException();
//    }
    
    @Override
    public Response get(Integer addressID) {
        Address address = addressDAO.findByID(addressID);
        if (address != null)
            return Response.ok(address).build();
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response create(Address address, UriInfo uriInfo) {
        Integer id = addressDAO.create(address);
        if (id != null) {
            UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
            URI uri = uriBuilder.path(id.toString()).build();
            return Response.created(uri).build();
        }
        return Response.serverError().build();
    }

    @Override
    public Response update(Address address) {
        if (addressDAO.update(address).intValue() != 0) {
            return Response.ok().build();
        }
        return Response.status(Status.NOT_MODIFIED).build();
    }

//    @Override
//    public Response delete(Integer addressID) {
//
//        throw new NotImplementedException();
        
        //TODO: Backend delete method has not been implemented yet.
        
//        if (addressDAO.delete(addressID).intValue() != 0) {
//            return Response.ok().build();
//        }
//        return Response.status(Status.NOT_MODIFIED).build();
//    }

    @Override
    public Response create(List<Address> addresses, UriInfo uriInfo) {

        List<BatchResponse> responseList = new ArrayList<BatchResponse>();
        for (Address address : addresses) {
            BatchResponse batchResponse = new BatchResponse();
            UriBuilder uriBuilder = uriInfo.getBaseUriBuilder().path("address");
            Integer id = addressDAO.create(address);
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
    public Response update(List<Address> addresses) {

        List<BatchResponse> responseList = new ArrayList<BatchResponse>();
        for (Address address : addresses) {
            BatchResponse batchResponse = new BatchResponse();
            Integer changeCount = addressDAO.update(address);
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

//    @Override
//    public Response delete(List<Integer> addressIDs) {
//        
//        List<BatchResponse> responseList = new ArrayList<BatchResponse>();
//        for (Integer addressID : addressIDs) {
//            BatchResponse batchResponse = new BatchResponse();
//            Integer changeCount = addressDAO.delete(addressID);
//            if (changeCount == 0) {
//                batchResponse.setStatus(Status.NOT_MODIFIED.getStatusCode());
//            } else {
//                batchResponse.setStatus(Status.OK.getStatusCode());
//            }
//            responseList.add(batchResponse);
//        }
//        return Response.ok(new GenericEntity<List<BatchResponse>>(responseList) {}).build();
//    }

    public void setAddressDAO(SecureAddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }

    public SecureAddressDAO getAddressDAO() {
        return addressDAO;
    }


}
