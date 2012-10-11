package com.inthinc.pro.dao.hessian.exceptions;

public class DuplicateIMEIException extends DuplicateEntryException
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public DuplicateIMEIException(String message, String methodName, int errorCode)
    {
        super(message, methodName, errorCode);
    }

}
