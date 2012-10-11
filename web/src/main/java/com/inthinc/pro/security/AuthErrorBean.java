package com.inthinc.pro.security;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.springframework.security.AccountExpiredException;
import org.springframework.security.AuthenticationServiceException;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.CredentialsExpiredException;
import org.springframework.security.DisabledException;
import org.springframework.security.LockedException;
import org.springframework.security.ui.AbstractProcessingFilter;

import com.inthinc.pro.util.MessageUtil;

public class AuthErrorBean
{
    private String errorMessage;
    private static final Logger logger = Logger.getLogger(AuthErrorBean.class);
    
    
    @SuppressWarnings("unchecked")
    public static Map<Class, String>errorMap = new HashMap<Class, String>();
    
    static
    {
        errorMap.put(BadCredentialsException.class, "login_error_BadCredentialsException");
        errorMap.put(CredentialsExpiredException.class, "login_error_CredentialsExpiredException");
        errorMap.put(AccountExpiredException.class, "login_error_AccountExpiredException");
        errorMap.put(DisabledException.class, "login_error_AccountExpiredException");
        errorMap.put(LockedException.class, "login_error_LockedException");
    }
    
    public AuthErrorBean()
    {
        super();
    }
    
    public void init()
    {
        Object securityException = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY);        
        if(securityException == null)
            return;
        
       String errorMsgKey = errorMap.get(securityException.getClass());
       if (errorMsgKey != null)
       {
           setErrorMessage(MessageUtil.getMessageString(errorMsgKey));
       }
       else if (securityException instanceof AuthenticationServiceException)
       {
            AuthenticationServiceException ase = (AuthenticationServiceException)securityException;
            errorMessage = ase.getMessage();
       }
       else
       {
           logger.debug(securityException);
           setErrorMessage(MessageUtil.getMessageString("login_error_Generic"));
       }

       FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY);
    }
    
    public String getErrorMessage()
    {                      
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }    

}
