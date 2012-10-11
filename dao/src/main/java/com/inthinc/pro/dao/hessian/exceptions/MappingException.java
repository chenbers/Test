package com.inthinc.pro.dao.hessian.exceptions;

import com.inthinc.pro.ProDAOException;

public class MappingException extends ProDAOException
{
    public MappingException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public MappingException(String message)
    {
        super(message);
    }

    public MappingException(Throwable cause)
    {
        super(cause);
    }
}
