package com.inthinc.pro.backing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;

import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.map.MapType;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.NoAddressFoundException;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.User;
import com.inthinc.pro.security.userdetails.ProUser;
import com.inthinc.pro.util.MessageUtil;

public class BaseBean implements Serializable {
    private static final Logger logger = Logger.getLogger(BaseBean.class);
    private ErrorBean errorBean;
    private AccountDAO accountDAO;
    private String emailAddress;
    protected Integer showExcludedEvents = EventDAO.INCLUDE_FORGIVEN;
	protected AddressLookup addressLookup;
    private static final MapType mapType = MapType.GOOGLE;


    public BaseBean() {
        super();
    }

    public ErrorBean getErrorBean() {
        return errorBean;
    }

    public void setErrorBean(final ErrorBean errorBean) {
        this.errorBean = errorBean;
    }

    public Object getParameter(final String name) {
        return getExternalContext().getRequestParameterMap().get(name);
    }

    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    public ExternalContext getExternalContext() {
        return getFacesContext().getExternalContext();
    }

    public boolean isLoggedIn() {
        return isProUserLoggedIn();
    }

    public Person getPerson() {
        return getProUser().getUser().getPerson();
    }

    public User getUser() {
        return getProUser().getUser();
    }

    public GroupHierarchy getGroupHierarchy() {
        return getProUser().getGroupHierarchy();
    }

    public Integer getAccountID() {
        return getProUser().getGroupHierarchy().getTopGroup().getAccountID();
    }

    public ProUser getProUser() {
        return (ProUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public boolean isProUserLoggedIn() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof ProUser;
    }

    public void addInfoMessage(final String summary) {
        final FacesContext context = FacesContext.getCurrentInstance();
        final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        context.addMessage(null, message);
    }

    public void addErrorMessage(final String summary) {
        final FacesContext context = FacesContext.getCurrentInstance();
        final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR - " + summary, null);
        context.addMessage(null, message);
    }

    public void addWarnMessage(final String summary) {
        final FacesContext context = FacesContext.getCurrentInstance();
        final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING - " + summary, null);
        context.addMessage(null, message);
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    public Locale getLocale() {
        if (getPerson().getLocale() != null)
            return getPerson().getLocale();
        else
            return Locale.US;
    }
    
    public void setLocale(Locale locale) {
        getPerson().setLocale(locale);
    }

    public MeasurementType getMeasurementType() {
        if (getUser().getPerson().getMeasurementType() != null)
            return getUser().getPerson().getMeasurementType();
        else
            return MeasurementType.ENGLISH;
    }

    public FuelEfficiencyType getFuelEfficiencyType() {
        if (getUser().getPerson().getFuelEfficiencyType() != null) {
            return getUser().getPerson().getFuelEfficiencyType();
        }
        if (getUser().getPerson().getMeasurementType() != null) {
            if (getUser().getPerson().getMeasurementType() == MeasurementType.ENGLISH)
                return FuelEfficiencyType.MPG_US;
            else
                return FuelEfficiencyType.KMPL;
        }
        else
            return FuelEfficiencyType.MPG_US;
    }

    public String getAccountName() {
        Account account = getAccountDAO().findByID(getAccountID());
        String name = account.getAcctName();
        return name;
    }

    public MapType getMapType() {
        return mapType;
    }

    public String getEmailAddress() {
        if (emailAddress == null) {
            emailAddress = getProUser().getUser().getPerson().getPriEmail();
        }
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public List<String> getEmailAddressList() {
        String[] emails = getEmailAddress().split(",");
        return Arrays.asList(emails);
    }

    public boolean isShowExcludedEvents() {
        return showExcludedEvents == EventDAO.INCLUDE_FORGIVEN;
    }

    public void setShowExcludedEvents(boolean showExcludedEvents) {
        this.showExcludedEvents = showExcludedEvents ? EventDAO.INCLUDE_FORGIVEN : EventDAO.EXCLUDE_FORGIVEN;
    }

	public AddressLookup getAddressLookup() {
	    return addressLookup;
	}

	public void setAddressLookup(AddressLookup addressLookup) {
	    this.addressLookup = addressLookup;
	}
	
	public String getAddress(LatLng latLng){
		try {
			
			return addressLookup.getAddress(latLng);
		}
		catch(NoAddressFoundException nafe){
			
			return MessageUtil.getMessageString(nafe.getMessage());
		}
	}
	
	public List<Event> loadUnknownDriver(List<Event> warnings) {
	    List<Event> adjusted = new ArrayList<Event>();
	       
        // Get the unknown driver from the account
        Account acct = this.getAccountDAO().findByID(this.getProUser().getUser().getPerson().getAcctID());      

        // Fix the name
        for ( Event e: warnings ) {
            Person p = new Person();
            p.setFirst("Unknown");
            p.setLast("Driver");
            
            if ( e.getDriver() == null ) {
                Driver d = new Driver();
                d.setDriverID(acct.getAcctID());
                d.setPerson(p);
                e.setDriver(d);
            } else {
                e.getDriver().setPerson(p);
            }
            
            adjusted.add(e);
        }
        
        return adjusted;
	}
}
