package com.inthinc.pro.service.exceptions;

/**
 * Exception to represent an error while invoking remote services. Not available from the RestEasy framework.
 */
public class RemoteErrorException extends RuntimeException {

    private static final long serialVersionUID = -649426478907686367L;

    public RemoteErrorException(String message) {
        super(message);
    }

}
