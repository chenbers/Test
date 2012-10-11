package com.inthinc.pro.service.exceptionMappers;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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


    @Override
    public Response toResponse(NotFoundException exception) {
        return Response.status(getStatus()).type(MediaType.APPLICATION_XML).header(BaseExceptionMapper.HEADER_ERROR_MESSAGE, exception.getMessage()).build();
        
    }
/**
     * @see com.inthinc.pro.service.exceptionMappers.BaseExceptionMapper#getStatus()
     */
    @Override
    public Status getStatus() {
        return Status.NOT_FOUND;
    }

}
