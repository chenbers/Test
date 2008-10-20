package com.inthinc.pro.backing;

public class ErrorBean
{
    protected String errorMessage;
    
    public ErrorBean()
    {
    }
    
    public String getErrorMessage()
    {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    public void clearErrorAction()
    {
        setErrorMessage(null);
        
    }

}
