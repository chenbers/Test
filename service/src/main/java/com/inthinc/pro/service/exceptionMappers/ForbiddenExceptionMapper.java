package com.inthinc.pro.service.exceptionMappers;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import com.inthinc.pro.service.exceptions.ForbiddenException;

/**
 * Exception mapper for {@link ForbiddenException}.
 */
@Provider
@Component
public class ForbiddenExceptionMapper extends BaseExceptionMapper<ForbiddenException> implements ExceptionMapper<ForbiddenException> {

    /**
     * @see com.inthinc.pro.service.exceptionMappers.BaseExceptionMapper#getStatus()
     */
    @Override
    public Status getStatus() {
        return Status.FORBIDDEN;
    }

}
