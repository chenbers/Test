package com.inthinc.pro.dao.hessian.exceptions;

public class DuplicateEntryException extends RemoteServerException
{

    public DuplicateEntryException(String message, String methodName, int errorCode, Throwable cause)
    {
        super(message, methodName, errorCode, cause);
    }

    public DuplicateEntryException(String message, String methodName, int errorCode)
    {
        super(message, methodName, errorCode);
    }

}
