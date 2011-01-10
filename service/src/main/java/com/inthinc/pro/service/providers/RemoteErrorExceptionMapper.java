package com.inthinc.pro.service.providers;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.spi.BadRequestException;
import org.springframework.stereotype.Component;

import com.inthinc.pro.service.exceptions.RemoteErrorException;

/**
 * Exception mapper for {@link BadRequestException}.
 */
@Provider
@Component
public class RemoteErrorExceptionMapper extends BaseExceptionMapper<RemoteErrorException> implements ExceptionMapper<RemoteErrorException> {

    /**
     * @see com.inthinc.pro.service.providers.BaseExceptionMapper#getStatus()
     */
    @Override
    public Status getStatus() {
        return Status.INTERNAL_SERVER_ERROR;
    }

}
