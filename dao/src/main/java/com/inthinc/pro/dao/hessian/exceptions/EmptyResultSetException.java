package com.inthinc.pro.dao.hessian.exceptions;

public class EmptyResultSetException extends RemoteServerException
{

    public EmptyResultSetException(String message, String methodName, int errorCode, Throwable cause)
    {
        super(message, methodName, errorCode, cause);
    }

    public EmptyResultSetException(String message, String methodName, int errorCode)
    {
        super(message, methodName, errorCode);
    }

}
