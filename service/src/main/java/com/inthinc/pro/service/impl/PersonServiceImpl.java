package com.inthinc.pro.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.inthinc.pro.model.Person;
import com.inthinc.pro.service.PersonService;
import com.inthinc.pro.service.model.BatchResponse;
import com.inthinc.pro.util.SecurePersonDAO;

public class PersonServiceImpl implements PersonService {

    private SecurePersonDAO personDAO;

    @Override
    public Response getAll() {
    	List<Person> list = personDAO.getAll();
        return Response.ok(new GenericEntity<List<Person>>(list) {}).build();
    }

    @Override
    public Response get(Integer personID) {
        Person person = personDAO.findByID(personID);
        if (person != null)
            return Response.ok(person).build();
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response create(Person person, UriInfo uriInfo) {
        Integer id = personDAO.create(person);
        if (id != null) {
            UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
            URI uri = uriBuilder.path(id.toString()).build();
            return Response.created(uri).build();
        }
        return Response.serverError().build();
    }

    @Override
    public Response update(Person person) {
        if (personDAO.update(person).intValue() != 0) {
            return Response.ok().build();
        }
        return Response.status(Status.NOT_MODIFIED).build();
    }

    @Override
    public Response delete(Integer personID) {
        if (personDAO.delete(personID).intValue() != 0) {
            return Response.ok().build();
        }
        return Response.status(Status.NOT_MODIFIED).build();
    }

    @Override
    public Response create(List<Person> persons, UriInfo uriInfo) {

        List<BatchResponse> responseList = new ArrayList<BatchResponse>();
        for (Person person : persons) {
            BatchResponse batchResponse = new BatchResponse();
            UriBuilder uriBuilder = uriInfo.getBaseUriBuilder().path("person");
            Integer id = personDAO.create(person);
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
    public Response update(List<Person> persons) {
        List<BatchResponse> responseList = new ArrayList<BatchResponse>();
        for (Person person : persons) {
            BatchResponse batchResponse = new BatchResponse();
            Integer changeCount = personDAO.update(person);
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
    public Response delete(List<Integer> personIDs) {
        List<BatchResponse> responseList = new ArrayList<BatchResponse>();
        for (Integer personID : personIDs) {
            BatchResponse batchResponse = new BatchResponse();
            Integer changeCount = personDAO.delete(personID);
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

    public void setPersonDAO(SecurePersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public SecurePersonDAO getPersonDAO() {
        return personDAO;
    }

}
