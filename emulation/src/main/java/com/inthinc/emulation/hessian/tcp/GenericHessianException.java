package com.inthinc.emulation.hessian.tcp;

public class GenericHessianException extends HessianException
{

    public GenericHessianException(String remoteMethodName, int errorCode, Throwable cause)
    {
        this("Unknown Error Code", remoteMethodName, errorCode, cause);
    }

    public GenericHessianException(String remoteMethodName, int errorCode)
    {
        this("Unknown Error Code", remoteMethodName, errorCode);
    }

    public GenericHessianException(String message, String remoteMethodName, int errorCode, Throwable cause)
    {
        super(message, remoteMethodName, errorCode, cause);
    }

    public GenericHessianException(String message, String remoteMethodName, int errorCode)
    {
        super(message, remoteMethodName, errorCode);
    }
    
}
