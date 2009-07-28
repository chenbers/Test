package com.inthinc.pro.service.client.backing;

import java.io.Serializable;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.jboss.resteasy.client.ProxyFactory;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.User;
import com.inthinc.pro.security.userdetails.ProUser;

public class BaseBean implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(BaseBean.class);
    private ErrorBean errorBean;
    private AccountDAO accountDAO;


    public BaseBean()
    {
        super();
    }

    public ErrorBean getErrorBean()
    {
        return errorBean;
    }

    public void setErrorBean(final ErrorBean errorBean)
    {
        this.errorBean = errorBean;
    }

    public Object getParameter(final String name)
    {
        return getExternalContext().getRequestParameterMap().get(name);
    }

    public FacesContext getFacesContext()
    {
        return FacesContext.getCurrentInstance();
    }

    public ExternalContext getExternalContext()
    {
        return getFacesContext().getExternalContext();
    }

    public boolean isLoggedIn()
    {
        return isProUserLoggedIn();
    }

    public Person getPerson()
    {
        return getProUser().getUser().getPerson();
    }

    public User getUser()
    {
        return getProUser().getUser();
    }
    
    public <T> T getRESTClient(Class<T> clazz){
    	return ProxyFactory.create(clazz, getExternalContext().getRequestContextPath());
    }


    public ProUser getProUser()
    {

        return (ProUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }

    public boolean isProUserLoggedIn()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof ProUser;
    }

    public void addInfoMessage(final String summary)
    {
        final FacesContext context = FacesContext.getCurrentInstance();
        final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        context.addMessage(null, message);
    }

    public void addErrorMessage(final String summary)
    {
        final FacesContext context = FacesContext.getCurrentInstance();
        final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR - " + summary, null);
        context.addMessage(null, message);
    }

    public void addWarnMessage(final String summary)
    {
        final FacesContext context = FacesContext.getCurrentInstance();
        final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING - " + summary, null);
        context.addMessage(null, message);
    }

    public void setAccountDAO(AccountDAO accountDAO)
    {
        this.accountDAO = accountDAO;
    }

    public AccountDAO getAccountDAO()
    {
        return accountDAO;
    }

    public Locale getLocale()
    {
        if (getUser().getLocale() != null)
            return getUser().getLocale();
        else
            return Locale.ENGLISH;
    }

    public MeasurementType getMeasurementType()
    {
        if (getUser().getPerson().getMeasurementType() != null)
            return getUser().getPerson().getMeasurementType();
        else
            return MeasurementType.ENGLISH;
    }




}
