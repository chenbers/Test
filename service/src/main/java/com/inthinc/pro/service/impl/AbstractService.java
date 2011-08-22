package com.inthinc.pro.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.inthinc.pro.dao.util.IdExtractor;
import com.inthinc.pro.dao.util.ModelPropertyReplacer;
import com.inthinc.pro.service.GenericService;
import com.inthinc.pro.service.adapters.BaseDAOAdapter;
import com.inthinc.pro.service.model.BatchResponse;

public abstract class AbstractService<T, DAO extends BaseDAOAdapter<T>> implements GenericService<T> {

    private DAO dao;

    @Override
    public Response create(T object, UriInfo uriInfo) {
        Integer id = dao.create(object);
        if (id != null) {
            UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
            URI uri = uriBuilder.path(id.toString()).build();            
            T t = dao.findByID(id);         
            return Response.created(uri).entity(t).build();
        }
        return Response.serverError().build();
    }

    @Override
    public Response delete(Integer id) {
        if (dao.delete(id).intValue() != 0) {
            return Response.ok().build();
        }
        return Response.status(Status.NOT_MODIFIED).build();
    }

    @Override
    public Response get(Integer id) {
        T t = dao.findByID(id);
        if (t != null)
            return Response.ok(t).build();
        return Response.status(Status.NOT_FOUND).build();
    }

    // Not able to implement the getAll() method in this abstract class. When the JAX-B provider tries to marshal the data, it is unable to determine the List<T> generice type (
    // the
    // <T> parameter )
    // This issue is referenced in jboss's Jira https://jira.jboss.org/jira/browse/RESTEASY-167
    //
    // The developer Bill Burke states:

    // NOTE!
    //
    // There is a limit to generic type information knowledge at runtime. For instance, this use case will not work:
    //
    // pubilc class Parent<T>
    // {
    // @GET
    // @Produces("application/xml")
    // @Wrapped
    // List<T> get() { return new ArrayList<T>(); }
    // }
    //
    // @Path("/")
    // public class Child extends Parent<Customer> {}

    // @Override
    // public Response getAll() {
    // List<T> list = dao.getAll();
    // return Response.ok(new GenericEntity<List<T>>(list){}).build();
    // }

    @Override
    public Response update(T object) {
    	
    	//Get the original
    	Integer id = IdExtractor.getID(object);
    	if(id == null) {
            return Response.status(Status.NOT_FOUND).build();
    	}
        T original = dao.findByID(id);
    	//swap in the changed fields from the object
    	ModelPropertyReplacer.compareAndReplace(original, object);
    	//update all the values
        T t = dao.update(original);
        if (t != null) { 
            return Response.ok(t).build();
        }
        return Response.status(Status.NOT_MODIFIED).build();
    }

//    @Override
//    public Response create(List<T> list) {
//
//        List<BatchResponse> responseList = new ArrayList<BatchResponse>();
//        for (T t : list) {
//            BatchResponse batchResponse = new BatchResponse();
//            UriBuilder uriBuilder = uriInfo.getBaseUriBuilder().path("address");
//            Integer id = dao.create(t);
//            if (id == null) {
//                batchResponse.setStatus(Status.INTERNAL_SERVER_ERROR.getStatusCode());
//            } else {
//                batchResponse.setStatus(Status.CREATED.getStatusCode());
//                batchResponse.setUri(uriBuilder.path(id.toString()).build().toString());
//            }
//            responseList.add(batchResponse);
//        }
//        return Response.ok(new GenericEntity<List<BatchResponse>>(responseList) {
//        }).build();
//    }

    @Override
    public Response update(List<T> list) {

        List<BatchResponse> responseList = new ArrayList<BatchResponse>();
        for (T t : list) {
            BatchResponse batchResponse = new BatchResponse();
        	//Get the original
        	Integer id = IdExtractor.getID(t);
        	if(id == null) {
                return Response.status(Status.NOT_FOUND).build();
        	}
            T original = dao.findByID(id);
        	ModelPropertyReplacer.compareAndReplace(original, t);
            T tUpdate = dao.update(original);
            if (tUpdate == null) {
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
    public Response delete(List<Integer> list) {
        List<BatchResponse> responseList = new ArrayList<BatchResponse>();
        for (Integer userID : list) {
            BatchResponse batchResponse = new BatchResponse();
            Integer changeCount = dao.delete(userID);
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

    public DAO getDao() {
        return dao;
    }

    public void setDao(DAO dao) {
        this.dao = dao;
    }

}
