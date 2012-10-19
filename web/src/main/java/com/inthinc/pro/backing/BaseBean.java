package com.inthinc.pro.backing;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.joda.time.DateTimeZone;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.map.MapType;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.AccountHOSType;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.User;
import com.inthinc.pro.security.userdetails.ProUser;

public class BaseBean implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(BaseBean.class);
    private ErrorBean errorBean;
    private AccountDAO accountDAO;
    private String emailAddress;
    protected Integer showExcludedEvents = EventDAO.INCLUDE_FORGIVEN;
	protected AddressLookup addressLookup;
    private static final MapType mapType = MapType.GOOGLE;
    private String noReplyEmailAddress = "noreply@inthinc.com";


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
    public List<Integer> getGroupIDList() {
        return getGroupHierarchy().getSubGroupIDList(getUser().getGroupID());
    }
    public GroupHierarchy getAccountGroupHierarchy() {
        return getProUser().getAccountGroupHierarchy();
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
    public String getLocaleDisplayName(){
    	return getLocale().getDisplayName(getLocale());
    }
    public DateTimeZone getDateTimeZone() {
        return DateTimeZone.forTimeZone(getPerson().getTimeZone());
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

    public String getNoReplyEmailAddress() {

        Account acct = accountDAO.findByID(getProUser().getUser().getPerson().getAcctID());
        String localAddr = acct.getProps().getNoReplyEmail();
            
        if ( localAddr != null && localAddr.trim().length() != 0 ) {
            noReplyEmailAddress = localAddr.trim();
        }       
        
        return noReplyEmailAddress;
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
//		try {
			
			return addressLookup.getAddressOrZoneOrLatLng(latLng,  getProUser().getZones());
//		}
//		catch(NoAddressFoundException nafe){
//			// Couldn't find it, look for zone?
//		    List<Zone> zoneList = getProUser().getZones();
//            return (MiscUtil.findZoneName(zoneList, latLng) == null) ?
//                    MessageUtil.formatMessageString("noAddressFound", nafe.getLat(),nafe.getLng()) :
//                    MiscUtil.findZoneName(zoneList, latLng);		    
////			return MessageUtil.formatMessageString("noAddressFound", nafe.getLat(),nafe.getLng());
//		}
	}

	public int getAddressFormat() {
		
		return addressLookup.getAddressFormat().getCode();
	}
	
	public Integer getUnknownDriverID() {
		return getProUser().getUnknownDriver().getDriverID();
	}
	
	public Driver getUnknownDriver() {
		return getProUser().getUnknownDriver();
	}

	public Integer getShowExcludedEvents() {
		return showExcludedEvents;
	}

	public void setShowExcludedEvents(Integer showExcludedEvents) {
		this.showExcludedEvents = showExcludedEvents;
	}

	protected boolean isEnablePhoneAlerts() {
	    String active = getProUser().getAccountAttributes().getPhoneAlertsActive();
	    // not set defaults to TRUE or any setting other than 0 is TRUE
        return (active == null || !active.trim().equals("0"));
        
    }
	
	public boolean getAccountIsHOS() {
	    return getProUser().getAccountHOSType() != null && getProUser().getAccountHOSType() != AccountHOSType.NONE; 
	}
	
    public boolean getAccountIsWaysmart() {
        String waySmart = getProUser().getAccountAttributes().getWaySmart();
        return (waySmart == null) ? false : Boolean.valueOf(waySmart); 
    }
    public boolean getAccountIsRHAEnabled() {
        String rhaEnabled = getProUser().getAccountAttributes().getRhaEnabled();
        return (rhaEnabled == null) ? false : Boolean.valueOf(rhaEnabled); 
    }
    
    private static final int MILLIS_PER_MINUTE = 1000 * 60;
    private static final int MILLIS_PER_HOUR = MILLIS_PER_MINUTE * 60;
    public String getTimeZoneDisplayName(TimeZone timeZone) {
        if (timeZone == null)
           return "";
        final NumberFormat format = NumberFormat.getIntegerInstance();
        boolean isEnglish = getLocale().getLanguage().equals("en");
        final int offsetHours = timeZone.getRawOffset() / MILLIS_PER_HOUR;
        final int offsetMinutes = Math.abs((timeZone.getRawOffset() % MILLIS_PER_HOUR) / MILLIS_PER_MINUTE);
        String displayName =  isEnglish ? timeZone.getID() : timeZone.getDisplayName(true, TimeZone.LONG, getLocale()); 
        if (offsetHours < 0)
            return displayName + " (GMT" + offsetHours + ':' + format.format(offsetMinutes) + ')';
        else
            return displayName + " (GMT+" + offsetHours + ':' + format.format(offsetMinutes) + ')';
    }
}
