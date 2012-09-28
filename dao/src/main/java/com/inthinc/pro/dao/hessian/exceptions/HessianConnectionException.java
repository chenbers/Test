package com.inthinc.pro.dao.hessian.exceptions;

public class HessianConnectionException extends HessianException
{

    public HessianConnectionException(String message, String remoteMethodName, int errorCode, Throwable cause)
    {
        super(message, remoteMethodName, errorCode, cause);
        // TODO Auto-generated constructor stub
    }

    public HessianConnectionException(String message, String remoteMethodName, int errorCode)
    {
        super(message, remoteMethodName, errorCode);
        // TODO Auto-generated constructor stub
    }

}
