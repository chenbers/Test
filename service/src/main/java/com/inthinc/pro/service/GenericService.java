package com.inthinc.pro.service;

import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public interface GenericService<T> {

    public Response get(Integer id);

    public Response getAll();

    public Response create(T object, UriInfo uriInfo);

    public Response update(T object);

    public Response delete(Integer id);

    public Response create(List<T> list, UriInfo uriInfo);

    public Response update(List<T> list);

    public Response delete(List<Integer> list);
}
