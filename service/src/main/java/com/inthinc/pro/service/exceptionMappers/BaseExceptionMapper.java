package com.inthinc.pro.service.exceptionMappers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * RestEasy base exception mapper template method. The template method will get the {@link Status} object it should return from children classes and wrap it in a {@link Response}
 * object, with the exception stack trace as it's body. It allows children classes to only need to implement the abstract method {@link BaseExceptionMapper#getStatus()}. </p> Note
 * that children classes still have to declare they implement {@link ExceptionMapper} on their source code or else RestEasy will not be able to bind the mapper to the exception due
 * to how reflection works with generics. That means a child class should look like this:
 * 
 * <pre>
 * package com.inthinc.pro.service.exceptionMappers;
 * 
 * import javax.ws.rs.core.Response.Status;
 * import javax.ws.rs.ext.ExceptionMapper;
 * 
 * import org.jboss.resteasy.spi.BadRequestException;
 * 
 * public class BadRequestExceptionMapper extends BaseExceptionMapper&lt;BadRequestException&gt; implements ExceptionMapper&lt;BadRequestException&gt; {
 * 
 *     &#064;Override
 *     public Status getStatus() {
 *         return Status.BAD_REQUEST;
 *     }
 * 
 * }
 * </pre>
 * 
 */
public abstract class BaseExceptionMapper<E extends Throwable> implements ExceptionMapper<E> {

    private static final String ERROR_WRAPPER = "<exception>{0}</exception>";
    public static final String HEADER_ERROR_MESSAGE = "ERROR_MESSAGE";
    /**
     * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
     */
    @Override
    public Response toResponse(E exception) {
        String stackTract = getExceptionStackTrace(exception);
        
        //return Response.status(getStatus()).type(MediaType.TEXT_PLAIN).entity(stackTract).build();
        return Response.status(getStatus()).type(MediaType.APPLICATION_XML).entity(stackTract).build();
        
    }

    private String getExceptionStackTrace(Throwable exception) {
        StringWriter sWriter = new StringWriter();
        PrintWriter pWriter = new PrintWriter(sWriter);

        try {
            exception.printStackTrace(pWriter);
            String stackTrace = MessageFormat.format(ERROR_WRAPPER, sWriter.toString());
            return stackTrace;
        } finally {
            pWriter.close();
        }

    }

    public abstract Status getStatus();
}
