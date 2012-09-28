package com.inthinc.pro.dao.hessian.exceptions;

public class DuplicateUsernameException extends DuplicateEntryException
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public DuplicateUsernameException(String message, String methodName, int errorCode)
    {
        super(message, methodName, errorCode);
    }

}
