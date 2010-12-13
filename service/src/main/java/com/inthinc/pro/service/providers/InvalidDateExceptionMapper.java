package com.inthinc.pro.service.providers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

/**
 * TODO Example provider to map exception {@link InvalidDateException} to HTTP error codes.
 */
@Provider
@Component
public class InvalidDateExceptionMapper implements ExceptionMapper<InvalidDateException> {

    /**
     * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
     */
    @Override
    public Response toResponse(InvalidDateException exception) {
        return Response.status(Status.BAD_REQUEST.getStatusCode()).entity(exception.getMessage()).build();
    }

}
