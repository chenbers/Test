package com.inthinc.pro.dao.hessian.exceptions;

public class DuplicateEmpIDException extends DuplicateEntryException
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public DuplicateEmpIDException(String message, String methodName, int errorCode)
    {
        super(message, methodName, errorCode);
    }

}
