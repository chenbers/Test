package com.inthinc.pro.service.exceptionMappers;

/**
 * TODO This is a sample exception for exception mapping proof of context. Remove when exception mapping framework is in place.
 */
public class InvalidDateException extends RuntimeException {

    public InvalidDateException() {
        super();
    }

    public InvalidDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDateException(String message) {
        super(message);
    }

    public InvalidDateException(Throwable cause) {
        super(cause);
    }

    private static final long serialVersionUID = -4575786200821688363L;
}
