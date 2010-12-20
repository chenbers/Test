package com.inthinc.pro.service.providers;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.spi.NotFoundException;
import org.springframework.stereotype.Component;

/**
 * Exception mapper for {@link NotFoundException}.
 */
@Provider
@Component
public class NotFoundExceptionMapper extends BaseExceptionMapper<NotFoundException> implements ExceptionMapper<NotFoundException> {

    /**
     * @see com.inthinc.pro.service.providers.BaseExceptionMapper#getStatus()
     */
    @Override
    public Status getStatus() {
        return Status.NOT_FOUND;
    }

}
