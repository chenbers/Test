package com.inthinc.device.hessian.tcp;

public class RemoteServerException extends HessianException
{

    public RemoteServerException(String message, String methodName, int errorCode, Throwable cause)
    {
        super(message, methodName, errorCode, cause);
    }

    public RemoteServerException(String message, String methodName, int errorCode)
    {
        super(message, methodName, errorCode);
    }

}
