package com.inthinc.emulation.hessian;



public class HessianException extends ProDAOException
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final Object[] EMPTY_ARRAY = new Object[0];

    private String remoteMethodName;
    private Object[] parameters;
    private int errorCode;

    public HessianException(String message, String remoteMethodName, Object[] parameters, int errorCode, Throwable cause)
    {
        super(message, cause);
        this.remoteMethodName = remoteMethodName;
        this.parameters = parameters;
        this.errorCode = errorCode;
    }

    public HessianException(String message, String remoteMethodName, int errorCode, Throwable cause)
    {
        super(message, cause);
        this.remoteMethodName = remoteMethodName;
        this.parameters = EMPTY_ARRAY;
        this.errorCode = errorCode;
    }

    public HessianException(String message, String remoteMethodName, int errorCode)
    {
        super(message);
        this.remoteMethodName = remoteMethodName;
        this.parameters = EMPTY_ARRAY;
        this.errorCode = errorCode;
    }

    public HessianException(String remoteMethodName, Object[] parameters, int errorCode, Throwable cause)
    {
        this("Unknown Error Code", remoteMethodName, parameters, errorCode, cause);
    }

    public HessianException(String remoteMethodName, int errorCode, Throwable cause)
    {
        this("Unknown Error Code", remoteMethodName, errorCode, cause);
    }

    public HessianException(String remoteMethodName, int errorCode)
    {
        this("Unknown Error Code", remoteMethodName, errorCode);
    }

    public String getMessage()
    {
        return "Remote Method: " + remoteMethodName + " | Error Code: " + errorCode + 
        ((parameters == null) ? "" : " | Parameters: " + getParamString()) + 
        " | Message: " + super.getMessage();
    }

    public int getErrorCode()
    {
        return errorCode;
    }

    public String getRemoteMethodName()
    {
        return remoteMethodName;
    }

    public Object[] getParameters()
    {
        return parameters;
    }
    
    private String getParamString()
    {
        StringBuffer buffer = new StringBuffer();
        for(Object o : parameters)
        {
            buffer.append(o.toString());
        }
        return buffer.toString();
    }

}
