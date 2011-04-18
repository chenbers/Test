package com.inthinc.pro.service.exceptionMappers;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.spi.BadRequestException;
import org.springframework.stereotype.Component;

/**
 * Exception mapper for {@link BadRequestException}.
 */
@Provider
@Component
public class BadRequestExceptionMapper extends BaseExceptionMapper<BadRequestException> implements ExceptionMapper<BadRequestException> {

    /**
     * @see com.inthinc.pro.service.exceptionMappers.BaseExceptionMapper#getStatus()
     */
    @Override
    public Status getStatus() {
        return Status.BAD_REQUEST;
    }

}
