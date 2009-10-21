package com.inthinc.pro.service.impl;

import java.net.URI;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.inthinc.pro.service.GenericService;
import com.inthinc.pro.util.SecureDAO;

public abstract class GenericServiceImpl<T, DAO extends SecureDAO<T>> implements GenericService<T> {

    private DAO dao;

    @Override
    public Response create(T object, UriInfo uriInfo) {
        Integer id = dao.create(object);
        if (id != null) {
            UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
            URI uri = uriBuilder.path(id.toString()).build();
            return Response.created(uri).build();
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

    @Override
    public Response getAll() {
        List<T> list = dao.getAll();
        return Response.ok(new GenericEntity<List<T>>(list) {
        }).build();
    }

    @Override
    public Response update(T object) {
        if (dao.update(object).intValue() != 0) {
            return Response.ok().build();
        }
        return Response.status(Status.NOT_MODIFIED).build();
    }

    public DAO getDao() {
        return dao;
    }

    public void setDao(DAO dao) {
        this.dao = dao;
    }

}
