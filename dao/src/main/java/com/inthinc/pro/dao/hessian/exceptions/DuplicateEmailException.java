package com.inthinc.pro.dao.hessian.exceptions;

public class DuplicateEmailException extends DuplicateEntryException
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public DuplicateEmailException(String message, String methodName, int errorCode)
    {
        super(message, methodName, errorCode);
    }

}
