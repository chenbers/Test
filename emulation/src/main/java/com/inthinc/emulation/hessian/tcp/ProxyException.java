package com.inthinc.emulation.hessian.tcp;

public class ProxyException extends HessianException
{
    public ProxyException(String message, String methodName, int errorCode, Throwable cause)
    {
        super(message, methodName, errorCode, cause);
    }

    public ProxyException(String message, String methodName, int errorCode)
    {
        super(message, methodName, errorCode);
    }

}
