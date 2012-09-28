package com.inthinc.pro.backing;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

public class ErrorBean
{
    protected String errorMessage;
    private static final Logger logger = Logger.getLogger(ErrorBean.class);
    private static final String NOTHING = "";

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

    public String getStackTrace()
    {
        Map<String, Object> request = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
        Throwable exception = (Throwable) request.get("javax.servlet.error.exception");
        if (exception != null)
        {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            logger.debug("An Exception has been thrown: ", exception);
            exception.printStackTrace(pw);
            return sw.toString();
        }
        return NOTHING;
    }

}
