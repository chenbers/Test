package com.inthinc.pro.service.exceptionMappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.inthinc.pro.service.exceptions.BadDateRangeException;

public class BadDateRangeExceptionMapper extends BaseExceptionMapper<BadDateRangeException> {

    public static Response getResponse(BadDateRangeException exception) {
        return Response.status(Status.BAD_REQUEST).header(HEADER_ERROR_MESSAGE, exception.getMessage()).build();
    }

    @Override
    public Status getStatus() {
        return Status.BAD_REQUEST;
    }

}
